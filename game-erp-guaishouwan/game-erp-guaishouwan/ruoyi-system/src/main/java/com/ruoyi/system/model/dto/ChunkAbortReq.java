package com.ruoyi.system.model.dto;

import lombok.Data;

@Data
public class ChunkAbortReq {
    private String key;
    private String uploadId;
}
