import SparkMD5 from "spark-md5";
// import {
//   requestHandle
// } from "./requestHandle";
import {api_FileCheck,api_UploadChunk,api_UploadFile} from '@/api/upload'

let chunkSize = 1024 * 1024 * 5; /* 分片大小 默认10M */
const blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice; // 兼容firefox 兼容webkit
const fileReader = new FileReader();
const maxCount = 3;
let uploadErrorSum = 0 // 上传失败次数

export const spliceUploadHandle = async (file, callback, fileType) => {
  let currentChunk = 0;
  const chunks = Math.ceil(file.size / chunkSize); // 总片数
  const spark = new SparkMD5.ArrayBuffer();
  const chunkBlobs = []; // 分片后的文件集合
  // debugger
  // 文件读取成功完成时触发
  fileReader.onload = async (e) => {
    // console.log("e=", e.target.result);
    spark.append(e.target.result);
    currentChunk++;
    if (currentChunk < chunks) {
      loadNext();
    } else {
      try {
        const fileHash = spark.end(); // md5文件内容唯一值
        const checkRes = await fileCheck(file, chunks, fileHash, fileType);
        if (checkRes.filePath) {
          callback(checkRes);
        } else if (checkRes && checkRes.uploadId) {
          await uploadFile(file.name, fileHash, checkRes, chunkBlobs, fileType);
          // console.log('uploadErrorSum',uploadErrorSum);
          if (uploadErrorSum) {
            callback(undefined);
          } else {
            const result = await mergeChunk(fileHash, checkRes.uploadId, fileType);
            callback(result);
          }
        } else {
          callback(undefined);
        }
      } catch (e) {
        // console.log('e',e);
        callback(undefined);
      }
    }
  };

  const loadNext = () => {
    const start = currentChunk * chunkSize;
    const end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
    const blob = blobSlice.call(file, start, end);
    chunkBlobs.push(blob);
    fileReader.readAsArrayBuffer(blob); // 启动读取指定的 Blob 或 File 内容
  };
  loadNext();
};

// 处理多个文件
export const handleFiles = async (files, callback) => {
  let chunkSize = 1024 * 1024 * 15; /* 多个上传 默认10M */
  let currentChunk = 0;
  let pathArr = [];

  if (files.length !== 0) {
    files.forEach(fileObj => {
      const fileReaderObj = new FileReader();
      let file = fileObj.value;
      const chunks = Math.ceil(file.size / chunkSize);
      const spark = new SparkMD5.ArrayBuffer();
      const chunkBlobs = [];

      let tempObj;

      fileReaderObj.onload = async (e) => {
        spark.append(e.target.result);
        currentChunk++;
        if (currentChunk < chunks) {
          loadNext();
        } else {
          try {
            const fileHash = spark.end();
            const checkRes = await fileCheck(file, chunks, fileHash);
            if (checkRes.filePath) {
              tempObj = checkRes
            } else if (checkRes && checkRes.uploadId) {
              await uploadFile(file.name, fileHash, checkRes, chunkBlobs);
              tempObj = await mergeChunk(fileHash, checkRes.uploadId);
            }
          } catch (e) {
            console.log(e);
          }
        }
        tempObj.fileName = fileObj.name;
        tempObj.type = fileObj.type;
        pathArr.push(tempObj);
      };

      const loadNext = () => {
        const start = currentChunk * chunkSize;
        const end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
        const blob = blobSlice.call(file, start, end);
        chunkBlobs.push(blob);
        fileReaderObj.readAsArrayBuffer(blob);
      };
      loadNext();
    });

    setTimeout(function () {
      callback(pathArr)
    }, 3000)
  }
};

/* 文件查重 */
const fileCheck = async (file, chunks, md5) => {
  try {
    const {
      data
    } = await api_FileCheck({
      fileMD5Hash: md5,
      fileName: file.name,
      fileSize: file.size,
      chunkCount: chunks,
    });
    return data;
  } catch (error) {
    console.log(`fileCheck:${error}`);
  }
};

/* 上传文件 */
const uploadFile = async (fileName, fileHash, checkResult, chunkBlobs) => {
  const params = [
    []
  ];
  let k = 0;
  const url = 'uploadFile/chunk';
  for (let i = 0; i < chunkBlobs.length; i++) {
    // 断点续传
    if (checkResult.completedChunk && checkResult.completedChunk.indexOf(i + 1) > -1) {
      continue;
    }
    const paramItem = {
      fileName: fileName,
      fileHash,
      uploadId: checkResult.uploadId,
      chunkNumber: i + 1,
      chunkBlob: chunkBlobs[i],
      chunkSize: chunkBlobs[i].size,
    };
    // 控制多分片上传的并发数量
    if (params[k].length > maxCount) {
      k = k + 1;
      params[k] = [paramItem];
    } else {
      params[k].push(paramItem);
    }
  }
  for (let i = 0; i < params.length; i++) {
    await Promise.all(params[i].map(item => uploadChunk(item, url)));
  }
};

/* 上传分块 */
const uploadChunk = async (param, url) => {
  try {
    if (!param) {
      return false;
    }
    let fd = new FormData();
    for (let key in param) {
      if (param.hasOwnProperty(key) && param[key]) {
        fd.append(key, param[key]);
      }
    }
    await api_UploadChunk(param);
  } catch (error) {
    uploadErrorSum++
    console.log(`uploadChunk:${error}`);
  }
};

/* 合并文件块 */
const mergeChunk = async (fileHash, uploadId) => {
  try {
    const {
      data
    } = await api_UploadFile({
      fileHash,
      uploadId
    });
    return data;
  } catch (error) {
    console.log(`mergeChunk:${error}`);
  }
};
