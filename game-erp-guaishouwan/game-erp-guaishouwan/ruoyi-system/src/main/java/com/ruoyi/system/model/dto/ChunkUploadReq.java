package com.ruoyi.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
public class ChunkUploadReq {
    private String key;
    private String uploadId;
    private int chunkNumber;
    private long chunkSize;
    private String chunkHash;
    private String fileHash;
    private transient InputStream inputStream;
}
