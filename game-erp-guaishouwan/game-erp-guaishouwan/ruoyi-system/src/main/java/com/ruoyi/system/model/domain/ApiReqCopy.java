package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 api_req_copy
 * 
 * @author ruoyi
 * @date 2023-02-16
 */
public class ApiReqCopy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 用户名 */
    @Excel(name = "用户名")
    private Long userId;

    /** 加价百分比 */
    @Excel(name = "加价百分比")
    private String author;

    /** 请求参数 */
    @Excel(name = "请求参数")
    private String req;

    /** 相应参数 */
    @Excel(name = "相应参数")
    private String res;

    /** del 、 add 、 edit */
    @Excel(name = "del 、 add 、 edit")
    private String type;

    /** 请求ip */
    @Excel(name = "请求ip")
    private String reqIp;

    /** 请求ip */
    @Excel(name = "请求ip")
    private String reqServer;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setUpdateId(Long updateId) 
    {
        this.updateId = updateId;
    }

    public Long getUpdateId() 
    {
        return updateId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setReq(String req) 
    {
        this.req = req;
    }

    public String getReq() 
    {
        return req;
    }
    public void setRes(String res) 
    {
        this.res = res;
    }

    public String getRes() 
    {
        return res;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setReqIp(String reqIp) 
    {
        this.reqIp = reqIp;
    }

    public String getReqIp() 
    {
        return reqIp;
    }
    public void setReqServer(String reqServer) 
    {
        this.reqServer = reqServer;
    }

    public String getReqServer() 
    {
        return reqServer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("userId", getUserId())
            .append("author", getAuthor())
            .append("req", getReq())
            .append("res", getRes())
            .append("type", getType())
            .append("reqIp", getReqIp())
            .append("reqServer", getReqServer())
            .toString();
    }
}
