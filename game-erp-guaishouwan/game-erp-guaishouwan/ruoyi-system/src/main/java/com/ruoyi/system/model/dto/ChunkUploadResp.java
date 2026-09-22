package com.ruoyi.system.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkUploadResp {
    private String key;
    private int chunkNumber;
    private String etag;
}
