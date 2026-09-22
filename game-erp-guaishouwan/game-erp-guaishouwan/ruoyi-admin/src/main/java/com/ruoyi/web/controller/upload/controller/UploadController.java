package com.ruoyi.web.controller.upload.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.model.dto.ChunkMergeReq;
import com.ruoyi.system.model.dto.UploadChunkReq;
import com.ruoyi.web.controller.upload.service.PackageUploadService;
import com.ruoyi.system.model.dto.FileCheckReq;
import com.ruoyi.system.model.dto.InterfaceResultData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/7 21:15
 */
@Data
@RestController
@Slf4j
@RequestMapping("/uploadFile")
public class UploadController {


    @Resource
    private PackageUploadService packageUploadService;
    /**
     * 分片上传初始化校验
     */
    @PostMapping("/check")
    public AjaxResult fileCheck(@RequestBody FileCheckReq req) {
        log.info("分片上传校验={}", JSONObject.toJSONString(req));
        InterfaceResultData interfaceResultData = packageUploadService.checkFile(req);
        log.info("分片校验返回结果={}", JSONObject.toJSONString(interfaceResultData));
        return AjaxResult.success(interfaceResultData.getData());
    }
    /**
     * 上传分片
     *
     * @param chunk
     * @return
     * @throws IOException
     */
    @PostMapping("/chunk")
    public AjaxResult uploadChunk(UploadChunkReq chunk) throws IOException {
        log.info("uploadChunk request, thread id=" + Thread.currentThread().getId());
        log.info("上传分片请求参数 ={}" ,chunk.getChunkBlob().getSize());
        InterfaceResultData interfaceResultData = this.packageUploadService.uploadChunk(chunk);
        log.info("上传分片返回参数 ={}" ,JSONObject.toJSONString(interfaceResultData));
        return AjaxResult.success(interfaceResultData.getData());
    }

    @PostMapping("/chunk/merge")
    public AjaxResult mergeChunk(@RequestBody ChunkMergeReq req) {
        InterfaceResultData interfaceResultData = packageUploadService.mergeChunk(req);
        log.info("分片合并返回结果={}", JSONObject.toJSONString(interfaceResultData));
        return AjaxResult.success(interfaceResultData.getData());
    }

}
