package com.ruoyi.system.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkCompleteResp {
    private String key;
    private String location;
    private String eTag;
}
