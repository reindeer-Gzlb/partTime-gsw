package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 上传视频对象 upload_file
 *
 * @author lz
 * @date 2022-05-31
 */
public class UploadFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    private Integer chunkCount;

    private String createName;

    private String fileHash;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private Integer status;

    private String uploadId;

    private String fileType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public Integer getChunkCount() {
        return chunkCount;
    }
    public void setChunkCount(Integer chunkCount) {
        this.chunkCount = chunkCount;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setFileHash(String fileHash)
    {
        this.fileHash = fileHash;
    }

    public String getFileHash()
    {
        return fileHash;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize()
    {
        return fileSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUploadId(String uploadId)
    {
        this.uploadId = uploadId;
    }

    public String getUploadId()
    {
        return uploadId;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileType()
    {
        return fileType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("chunkCount", getChunkCount())
            .append("createTime", getCreateTime())
            .append("createName", getCreateName())
            .append("fileHash", getFileHash())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .append("fileSize", getFileSize())
            .append("status", getStatus())
            .append("uploadId", getUploadId())
            .append("fileType", getFileType())
            .toString();
    }
}
