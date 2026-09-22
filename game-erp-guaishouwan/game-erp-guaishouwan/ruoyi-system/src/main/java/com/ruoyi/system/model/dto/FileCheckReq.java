package com.ruoyi.system.model.dto;

import lombok.Data;

@Data
public class FileCheckReq {
    /**
     * 文件的MD5值
     */
    private String fileMD5Hash;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小，单位：字节
     */
    private Long fileSize;
    /**
     * 分片数量
     */
    private Integer chunkCount;

}
