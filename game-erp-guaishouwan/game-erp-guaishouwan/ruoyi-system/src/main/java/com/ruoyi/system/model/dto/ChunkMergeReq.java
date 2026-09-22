package com.ruoyi.system.model.dto;

import lombok.Data;

@Data
public class ChunkMergeReq {
    private String filePath;
    private String fileHash;
    private String fileName;
    private Integer chunkCount;
    private String uploadId;
    private String partNumber;
    private String partSwid;
    private String partDid;

    private String domainCode;

    private String createUname;

}
