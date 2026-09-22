<template>
  <div
    v-loading="uploading"
    style="width: 100%"
    :element-loading-text="uploadingText"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <el-upload
      ref="uploader"
      class="upload-block"
      accept=".mp4"
      action=""
      :before-upload="beforeUpload"
      :on-change="handleChange"
      :on-remove="removeFile"
      :http-request="handleUploadRequest"
      :file-list="fileList"
      :show-file-list="true"
    >
      <el-button size="small" type="primary" style="width: 200px">上传</el-button>
      <div slot="tip" style="color: #f56c6c" class="el-upload__tip">
      </div>
    </el-upload>
    <div class="upload-process">
      <el-progress
        v-show="showProgress"
        :text-inside="true"
        :stroke-width="18"
        :percentage="uploadPercent"
      ></el-progress>
    </div>
  </div>
</template>
<script>
import SparkMD5 from "spark-md5";
import { translateFileLanguageFromCode } from "@/utils/il8n";
import axios from "axios";
import {
  api_FileCheck,
  api_UploadChunk,
  api_UploadFile,
} from "@/api/upload.js";
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "package-upload",
  props: ["partNumber", "partSwid"],
  data() {
    return {
      fileList: [], //文件列表
      fileMD5Hash: "", //文件MD5
      fileName: "", //文件名称
      fileSize: 0, //文件大小，单位：字节
      fileChunkList: [], //文件所有分片列表
      fileChunkCompletedList: [], //成功上传的分片列表
      fileChunkFailedList: [], //上传失败的分片列表
      fileChunkPendingList: [], //等待上传列表
      fileChunkCount: 0, //总的分片数
      fileIsChunked: false, //是否进行分片上传，当文件总大小小于有个分片大小时不分片
      filePrepared: false, //文件准备是否完成，计算文件hash,进行分片等准备操作
      fileChunkCompleted: false, //分片上传完成
      uploadId: "",
      showProgress: false, //是否展示进度
      uploadPercent: 0, //进度百分比
      uploadResult: "", //上传结果
      uploading: false, //启用上传遮罩
      uploadingText: "上传进度", //上传进度文字
    };
  },
  watch: {
    filePrepared: {
      handler(newVal, oldVal) {
        if (newVal) {
          // this.doFileCheck();
        }
      },
      immediate: false,
    },
  },
  methods: {
    initUploadState() {
      this.fileList = [];
      this.fileChunkList = [];
      this.fileChunkCount = 0;
      this.fileIsChunked = false;
      this.fileMD5Hash = "";
      this.fileName = "";
      this.fileSize = 0;
      this.filePrepared = false;
      this.fileChunkCompletedList = [];
      this.fileChunkPendingList = [];
      this.fileChunkFailedList = [];
      this.uploadId = "";
      this.uploadPercent = 0;
      this.showProgress = false;
      this.uploadResult = "";
    },
    handleChange(file, fileList) {
      this.initUploadState();
      this.fileList = fileList.slice(-1);
    },
    beforeUpload(file) {
      console.log("prepare chunk start, at:" + new Date());
      this.$emit("beforeFileUpload", {
        filePath: this.filePath,
        fileName: this.fileName,
      });
      this.prepareFile(file).then(() => {
        console.log("prepare chunk end,at:" + new Date());
        this.doFileCheck(file);
      });
    },

    handleUploadRequest() {
      console.log("handleUploadRequest");
    },
    removeFile(file, fileList) {
        debugger
      this.initUploadState();
      //send notification to parent component on file removed.
      this.$emit("onFileRemoved", {
        filePath: this.filePath,
        fileSize: this.fileSize,
        fileName: this.fileName,
      });
    },
    //prepare chunk info ,include calculate file md5
    prepareFile(file) {
      return new Promise((resolve, reject) => {
        this.uploading = true;
        this.uploadingText = "uploadingTextForReadying";
        this.filePrepared = false;
        const blobSlice =
          File.prototype.slice ||
          File.prototype.mozSlice ||
          File.prototype.webkitSlice;
        const chunkSize = 4 * 1024 * 1024; //5M一个分片,aws多文件上传接口要求每个分块至少5MB
        const chunkCount = Math.ceil(file.size / chunkSize);
        console.log("chunkSize=" + chunkSize + ",chunkCount=" + chunkCount);
        this.fileChunkCount = chunkCount;
        this.fileIsChunked = chunkCount > 1;
        this.fileName = file.name;
        this.fileSize = file.size;
        const fileSpark = new SparkMD5.ArrayBuffer();
        const chunkSpark = new SparkMD5.ArrayBuffer();
        const fileReader = new FileReader();
        let chunkIndex = 0;
        //文件读取完毕之后的处理
        fileReader.onload = (e) => {
          const chunkBuffer = e.target.result;
          fileSpark.append(chunkBuffer);
          chunkIndex++;
          //{chunkNumber,chunkBlob,chunkMD5Hash}
          let chunkInfo = {};
          chunkInfo.chunkNumber = chunkIndex;
          const chunkBlob = new Blob([chunkBuffer]);
          chunkInfo.chunkBlob = chunkBlob;
          chunkInfo.chunkSize = chunkBlob.size;
          //如果不是最后一个分块
          if (chunkIndex < chunkCount) {
            chunkSpark.reset();
            chunkSpark.append(chunkBuffer);
            const chunkHash = chunkSpark.end();
            chunkInfo.chunkMD5Hash = chunkHash;
            readNext();
          } else {
            //最后一个分片
            this.fileMD5Hash = fileSpark.end();
            //如果不用分片上传
            if (this.fileIsChunked === false) {
              chunkInfo.chunkMD5Hash = this.fileMD5Hash;
            } else {
              chunkSpark.reset();
              chunkSpark.append(chunkBuffer);
              const chunkHash = chunkSpark.end();
              chunkInfo.chunkMD5Hash = chunkHash;
            }
            this.filePrepared = true;
            // this.uploadingText = this.$t("partTable.uploadingTextForReadyDone");
            this.uploadingText = "uploadingTextForReadyDone";
            resolve();
          }
          this.fileChunkList[chunkIndex - 1] = chunkInfo;
        };
        fileReader.onerror = (e) => {
          console.warn("oops, something went wrong when prepare chunk.");
          reject(e);
        };
        function readNext() {
          const start = chunkIndex * chunkSize;
          const end =
            start + chunkSize >= file.size ? file.size : start + chunkSize;
          fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
        }
        readNext();
      });
    },
    //check file after file chunk prepared
    doFileCheck(file) {
      this.uploading = true;
      this.showProgress = true;
      this.uploadingText = 'uploadingTextForFileChecking';

      let fileCheckReq = {};
      fileCheckReq.fileName = this.fileName;
      fileCheckReq.fileSize = this.fileSize;
      fileCheckReq.fileMD5Hash = this.fileMD5Hash;
      fileCheckReq.chunkCount = this.fileChunkCount;
      fileCheckReq.partNumber = this.partNumber;
      fileCheckReq.partSwid = this.partSwid;


      api_FileCheck(fileCheckReq).then((res) => {
          console.log(res)
          const fileCheckResp = res.data;
          const uploadId = fileCheckResp.uploadId;
          this.uploadId = uploadId;
          this.filePath = fileCheckResp.filePath;
          const isInitialized = fileCheckResp.isInitialized;
          const isCompleted = fileCheckResp.isCompleted;
          const completedChunkList = fileCheckResp.completedChunk;
          if (isCompleted) {
            //全部分片上传
            this.fileChunkCompletedList = this.fileChunkList || [];
            this.uploading = false;
            this.uploadingText = 'uploadingTextForFileExist';
            this.uploadResult = 'uploadingTextForFileExist';
            this.calcUploadPercent();
            this.$message.success(
              'uploadingTextForFileExist'
            );
            this.$emit("onUploadCompleted", {
              filePath: this.filePath,
              fileSize: this.fileSize,
              fileName: this.fileName,
            });
          } else {
            //部分分片上传
            if (completedChunkList && completedChunkList.length > 0) {
              for (let i = 0; i < this.fileChunkList.length; i++) {
                const thisChunkNumber = this.fileChunkList[i].chunkNumber;
                //从已上传的分片中查找当前分片
                const chunkIndex = completedChunkList.findIndex((item) => {
                  return item === thisChunkNumber;
                });
                //没有找到及需要上传的分片
                if (chunkIndex === -1) {
                  this.fileChunkPendingList.push(this.fileChunkList[i]);
                } else {
                  this.fileChunkCompletedList.push(this.fileChunkList[i]);
                }
              }
            } else {
              //一个分片都没有上传
              this.fileChunkPendingList = this.fileChunkList || [];
            }
            this.doUploadChunk();
          }
        })
    },
    //upload file chunk after check step finished
    doUploadChunk() {
      this.uploading = true;
      this.showProgress = true;
      this.calcUploadPercent();
      this.uploadingText = "uploadingTextForChunkUploading";
      //考虑场景：所有分片都已经上传完成，但是合并时出错，能够允许再次有合并的机会
      if (this.fileChunkPendingList.length === 0) {
        console.log("this.fileChunkPendingList.length === 0");
        if (this.fileChunkCompletedList.length === this.fileChunkCount) {
          console.log("this.fileChunkCompletedList.length === this.chunkCount");
          this.onUploadChunkCompleted();
        } else {
          this.uploading = false;
          this.uploadingText = "uploadingTextForUploadDone";
        }
        return;
      }
      for (var i = 0; i < this.fileChunkPendingList.length; i++) {
        const chunkInfo = this.fileChunkPendingList[i];
        let uploadChunkReq = new FormData();
        uploadChunkReq.append("fileName", this.fileName);
        uploadChunkReq.append("fileHash", this.fileMD5Hash);
        uploadChunkReq.append("chunkCount", this.fileChunkCount);
        uploadChunkReq.append("uploadId", this.uploadId);
        uploadChunkReq.append("chunkNumber", chunkInfo.chunkNumber);
        uploadChunkReq.append("chunkHash", chunkInfo.chunkMD5Hash);
        uploadChunkReq.append("chunkSize", chunkInfo.chunkSize);
        uploadChunkReq.append("chunkBlob", chunkInfo.chunkBlob);


        const sleep = (time) => {
          return new Promise((resolve) => {
            setTimeout(() => {
              resolve(true);
            }, time * 1000);
          });
        };
        sleep(i)
          .then((res) => {
            console.log(uploadChunkReq);
            api_UploadChunk(uploadChunkReq)
              .then((res) => {
                this.fileChunkCompletedList.push(chunkInfo);
                this.calcUploadPercent();
                this.onUploadChunkCompleted();
              })
              .catch((res) => {
                //分片上传失败
                this.fileChunkFailedList.push(chunkInfo);
                this.onUploadChunkCompleted();
              });

          })
          .finally(() => {
            console.log("upload chunk has send，please waiting...");
          });
      }
    },
    //this method will trigger when all chunk uploaded
    onUploadChunkCompleted() {
      const handledChunkCount =
        this.fileChunkFailedList.length + this.fileChunkCompletedList.length;
      if (handledChunkCount === this.fileChunkCount) {
        //全部上传完成
        if (this.fileChunkCompletedList.length === this.fileChunkCount) {
          this.fileChunkCompleted = true;
          this.doUploadFile();
        } else {
          //有上传失败的分片
          this.uploading = false;
          this.showProgress = false;
          this.uploadResult = "uploadingTextForUploadFailed";
          this.$message.error(
            "uploadingTextForUploadFailed"
          );
        }
      }
    },
    calcUploadPercent() {
      this.uploadPercent = Math.ceil(
        (this.fileChunkCompletedList.length / this.fileChunkCount) * 100
      );
    },
    //send merge chunk command after all chunk uploaded
    doUploadFile() {
      this.uploading = true;
      this.uploadingText = "uploadingTextForChunkMerging";
      if (!this.fileChunkCompleted || this.fileChunkList.length <= 0) {
        this.uploading = false;
        this.uploadingText = "uploadingTextForMergingDone";
        return;
      }

      let uploadFileReq = {};
      uploadFileReq.fileName = this.fileName;
      uploadFileReq.fileHash = this.fileMD5Hash;
      uploadFileReq.chunkCount = this.fileChunkCount;
      uploadFileReq.uploadId = this.uploadId;
      uploadFileReq.partNumber = this.partNumber;
      uploadFileReq.partSwid = this.partSwid;
      api_UploadFile(uploadFileReq)
        .then((res) => {
          this.uploadResult =  this.$modal.msgError("uploadingTextForUploadDone");
          this.uploadingText = this.$modal.msgError("uploadingTextForUploadDone");
          this.$message.success(
            'uploadingTextForUploadDone'
          );
          this.filePath = res.data.filePath;
        })
        .finally(() => {
          this.uploading = false;
        });
    },
  },
};
</script>

<style  lang="scss" scoped>
.upload-block {
  .el-upload {
    width: 100%;
    height: 100%;
    .el-upload-dragger {
      width: 100%;
      height: 100%;
      .el-upload__text {
        position: relative;
        font-size: 1.2em;
        color: #409eff;
        i {
          font-size: 1.5em;
          margin-right: 10px;
        }
      }
    }
  }
}

.upload-process {
  margin-top: 30px;
  display: block;
}
</style>

