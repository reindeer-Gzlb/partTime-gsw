import request from '@/utils/request'

//检查文件是否已存在
export function api_FileCheck(fileCheckReq) {
  return request({
    url: '/uploadFile/check',
    method: 'post',
    timeout: 300000000000000000,
    data: fileCheckReq,
    // headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" }
  })
}

//上传分片
export function api_UploadChunk(uploadChunkReq) {
  return request({
    url: '/uploadFile/chunk',
    method: 'post',
    data: uploadChunkReq,
    timeout: 300000000000000000,
    headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" }
  })
}
//合并文件
export function api_UploadFile(uploadFileReq) {
  return request({
    url: '/uploadFile/chunk/merge',
    method: 'post',
    timeout: 3000000000000000,
    data: uploadFileReq,
    // headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" }
  })
}
// 文件上传
export function postUpload (file) {
  return request({
    url: '/upload/imageHead',
    method: 'post',
    data: file,
    timeout: 3000000000000000
  })
}




