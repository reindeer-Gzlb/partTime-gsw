package com.ruoyi.web.controller.upload.service;



import com.ruoyi.system.model.dto.*;

public interface FileChunkUploader {

    ChunkInitResp init(ChunkInitReq req);

    ChunkUploadResp upload(ChunkUploadReq req);

    ChunkCompleteResp complete(ChunkCompleteReq req);

}
