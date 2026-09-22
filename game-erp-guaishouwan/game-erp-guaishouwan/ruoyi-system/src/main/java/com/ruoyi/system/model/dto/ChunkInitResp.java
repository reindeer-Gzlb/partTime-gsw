package com.ruoyi.system.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkInitResp {
    private String uploadId;
    private String chunkDir;
}
