package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分片列表对象 upload_chunk
 *
 * @author lz
 * @date 2022-05-31
 */
public class UploadChunk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String chunkName;

    private Integer chunkNumber;

    private Long chunkSize;

    private String createName;

    private String fileHash;

    private String uploadId;

    private String fileType;

    private String chunkEtag;

    private String chunkHash;

    public String getChunkHash() {
        return chunkHash;
    }

    public void setChunkHash(String chunkHash) {
        this.chunkHash = chunkHash;
    }

    public String getChunkEtag() {
        return chunkEtag;
    }

    public void setChunkEtag(String chunkEtag) {
        this.chunkEtag = chunkEtag;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setChunkName(String chunkName)
    {
        this.chunkName = chunkName;
    }

    public String getChunkName()
    {
        return chunkName;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public void setChunkSize(Long chunkSize)
    {
        this.chunkSize = chunkSize;
    }

    public Long getChunkSize()
    {
        return chunkSize;
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
            .append("chunkName", getChunkName())
            .append("chunkNumber", getChunkNumber())
            .append("chunkSize", getChunkSize())
            .append("createTime", getCreateTime())
            .append("createName", getCreateName())
            .append("fileHash", getFileHash())
            .append("uploadId", getUploadId())
            .append("fileType", getFileType())
            .toString();
    }
}
