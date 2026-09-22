package com.ruoyi.system.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadChunkReq {
    private String domainCode;


    private String uploadId;
    private int chunkNumber;
    private long chunkSize;
    private String chunkHash;
    private String fileHash;
    private String fileName;
    private MultipartFile chunkBlob;
}
