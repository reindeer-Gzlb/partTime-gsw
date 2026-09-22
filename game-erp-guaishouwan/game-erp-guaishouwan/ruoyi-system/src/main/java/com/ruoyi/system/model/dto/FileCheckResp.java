package com.ruoyi.system.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileCheckResp {
    /**
     * 是否存在
     */
    private Boolean isInitialized;
    /**
     * 是否存在
     */
    private Boolean isCompleted;
    /**
     * 已经上传的块
     */
    private List<Integer> completedChunk;
    /**
     * 上传ID
     */
    private String uploadId;
    /**
     * 文件在存储设备上的路径
     */
    private String filePath;

    private String fileName;
}
