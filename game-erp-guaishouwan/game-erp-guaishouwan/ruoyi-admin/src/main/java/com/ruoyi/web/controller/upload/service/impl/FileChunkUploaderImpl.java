package com.ruoyi.web.controller.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.sms.Configuration;
import com.qiniu.storage.ApiUploadV2CompleteUpload;
import com.qiniu.storage.ApiUploadV2InitUpload;
import com.qiniu.storage.ApiUploadV2UploadPart;
import com.qiniu.util.Auth;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.model.domain.UploadChunk;
import com.ruoyi.system.model.dto.*;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.web.controller.upload.service.FileChunkUploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/1 11:36
 */
@Service
@Slf4j
public class FileChunkUploaderImpl implements FileChunkUploader {

    @Autowired
    private ISysDictDataService dictDataService;


//    public static final String ACCESS_KEY = "w29QLuJqjE1X0FS-0c79rV1y9JII7OsgS7lnoqHf";
//    public static final String SECRET_KEY = "dvFLRmXaSSpzEvnUL3PRwUdprEbqxmtM0r9woyPI";
//    public static final String bucket = "stu-test";
//    public static final String domain = "http://upload-z2.qiniup.com";

//    public static final String ACCESS_KEY = "ACCESS_KEY";
//    public static final String SECRET_KEY = "dvFLRmXaSSpzEvnUL3PRwUdprEbqxmtM0r9woyPI";
//    public static final String bucket = "stu-test";
//    public static final String domain = "http://upload-z2.qiniup.com";


    private Client getClient(){
        Configuration configuration = new Configuration();
        return new Client(configuration);
    }

    private String getDomain(){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("qiniuyun");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        String domain = null;
        for (SysDictData dictData : sysDictDataList) {
            if ("domain".equals(dictData.getDictLabel())){
                domain = dictData.getDictValue();
            }
        }
        return domain;
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("qiniuyun");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        String ACCESS_KEY = "";
        String SECRET_KEY = "";
        String bucket = "";
        for (SysDictData dictData : sysDictDataList) {
            if ("ACCESS_KEY".equals(dictData.getDictLabel())){
                ACCESS_KEY = dictData.getDictValue();
            }else if ("SECRET_KEY".equals(dictData)){
                SECRET_KEY = dictData.getDictValue();
            }else if ("bucket".equals(dictData.getDictLabel())){
                bucket = dictData.getDictValue();
            }
        }
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(bucket);
    }


    @Override
    public ChunkInitResp init(ChunkInitReq req) {
        // 1. init upload
        String uploadId = null;
        ApiUploadV2InitUpload initUploadApi = new ApiUploadV2InitUpload(getClient());
        ApiUploadV2InitUpload.Request initUploadRequest = new ApiUploadV2InitUpload.Request(getDomain(), getUpToken())
                .setKey(req.getKey());
        try {
            ApiUploadV2InitUpload.Response initUploadResponse = initUploadApi.request(initUploadRequest);
            uploadId = initUploadResponse.getUploadId();
            log.info("init upload ={}",initUploadResponse.getResponse());
            log.info("init upload id ={}",initUploadResponse.getUploadId());
            ChunkInitResp chunkInitResp = ChunkInitResp.builder()
                    .uploadId(uploadId)
                    .build();
            return chunkInitResp;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new ServiceException("分片上传初始化失败");
        }
    }

    @Override
    public ChunkUploadResp upload(ChunkUploadReq req) {
        InputStream inputStream = req.getInputStream();
        byte[] bytes = new byte[0];
        try {
            bytes = readStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1.2.2 上传 part 数据
        ApiUploadV2UploadPart uploadPartApi = new ApiUploadV2UploadPart(getClient());
        ApiUploadV2UploadPart.Request uploadPartRequest = new ApiUploadV2UploadPart.Request(getDomain(), getUpToken(), req.getUploadId(), req.getChunkNumber())
                .setKey(req.getKey())
                .setUploadData(bytes, 0, bytes.length, null);
        try {
            ApiUploadV2UploadPart.Response uploadPartResponse = uploadPartApi.request(uploadPartRequest);
            String etag = uploadPartResponse.getEtag();
            log.info("etag = {}", etag);
            ChunkUploadResp chunkUploadResp = ChunkUploadResp.builder()
                    .etag(etag).build();
            return chunkUploadResp;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    @Override
    public ChunkCompleteResp complete(ChunkCompleteReq req) {
        List<UploadChunk> chunkList = req.getChunkList();
        List<Map<String, Object>> partsInfo = new ArrayList<>();
        chunkList.sort(Comparator.comparing(UploadChunk::getChunkNumber));
        for (UploadChunk uploadChunk : chunkList) {
            Map<String, Object> partInfo = new HashMap<>();
            partInfo.put(ApiUploadV2CompleteUpload.Request.PART_NUMBER, uploadChunk.getChunkNumber());
            partInfo.put(ApiUploadV2CompleteUpload.Request.PART_ETG, uploadChunk.getChunkEtag());
            partInfo.put("partSize", uploadChunk.getChunkSize());
            partsInfo.add(partInfo);
        }
        log.info("partsInfo = {}", JSON.toJSONString(partsInfo));
        String upToken = getUpToken();
        log.info("upToken = {}", upToken);
        ApiUploadV2CompleteUpload completeUploadApi = new ApiUploadV2CompleteUpload(getClient());
        ApiUploadV2CompleteUpload.Request completeUploadRequest = new ApiUploadV2CompleteUpload
                .Request(getDomain(), upToken, req.getUploadId(),partsInfo)
                .setKey(req.getKey())
                .setFileName(req.getFileName());
        try {
            ApiUploadV2CompleteUpload.Response completeUploadResponse = completeUploadApi.request(completeUploadRequest);
            log.info("complete upload={}",completeUploadResponse.getResponse());
            ChunkCompleteResp chunkCompleteResp = ChunkCompleteResp.builder()
                    .key(req.getKey())
                    .build();
            return chunkCompleteResp;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new ServiceException("分片文件合并失败");
        }
    }
}
