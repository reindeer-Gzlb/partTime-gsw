package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.UploadChunk;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChunkCompleteReq {
    private String key;
    private String fileName;
    private String fileHash;
    private String uploadId;
    private List<UploadChunk> chunkList;
}
