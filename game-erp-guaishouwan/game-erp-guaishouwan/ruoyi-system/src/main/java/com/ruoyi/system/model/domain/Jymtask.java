package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 jymtask
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public class Jymtask extends BaseEntity
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

    /** 编号id */
    @Excel(name = "编号id")
    private Long accountid;

    /** 交易猫id */
    @Excel(name = "交易猫id")
    private Long jymId;

    /** 交易猫add_新增任务id */
    @Excel(name = "交易猫add_新增任务id")
    private Long jymAddTaskId;

    /** 交易猫edit_编辑任务id */
    @Excel(name = "交易猫edit_编辑任务id")
    private Long jymEditTaskId;

    /** 交易猫del_删除任务id */
    @Excel(name = "交易猫del_删除任务id")
    private Long jymDelTaskId;

    /** 新增任务状态 */
    @Excel(name = "新增任务状态")
    private String addStatus;
    private String res;

    /** 编辑任务状态 */
    @Excel(name = "编辑任务状态")
    private String editStatus;

    /** 删除任务状态 */
    @Excel(name = "删除任务状态")
    private String delStatus;

    /**
     * code
     */
    private String salecode;

    private Long config;

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

    public String getSalecode() {
        return salecode;
    }

    public void setSalecode(String salecode) {
        this.salecode = salecode;
    }

    public Long getUpdateId()
    {
        return updateId;
    }
    public void setAccountid(Long accountid) 
    {
        this.accountid = accountid;
    }

    public Long getAccountid() 
    {
        return accountid;
    }
    public void setJymId(Long jymId) 
    {
        this.jymId = jymId;
    }

    public Long getJymId() 
    {
        return jymId;
    }
    public void setJymAddTaskId(Long jymAddTaskId) 
    {
        this.jymAddTaskId = jymAddTaskId;
    }

    public Long getJymAddTaskId() 
    {
        return jymAddTaskId;
    }
    public void setJymEditTaskId(Long jymEditTaskId) 
    {
        this.jymEditTaskId = jymEditTaskId;
    }

    public Long getJymEditTaskId() 
    {
        return jymEditTaskId;
    }
    public void setJymDelTaskId(Long jymDelTaskId) 
    {
        this.jymDelTaskId = jymDelTaskId;
    }

    public Long getJymDelTaskId() 
    {
        return jymDelTaskId;
    }
    public void setAddStatus(String addStatus) 
    {
        this.addStatus = addStatus;
    }

    public String getAddStatus() 
    {
        return addStatus;
    }
    public void setEditStatus(String editStatus) 
    {
        this.editStatus = editStatus;
    }

    public String getEditStatus() 
    {
        return editStatus;
    }
    public void setDelStatus(String delStatus) 
    {
        this.delStatus = delStatus;
    }

    public String getDelStatus() 
    {
        return delStatus;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Long getConfig() {
        return config;
    }

    public void setConfig(Long config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("accountid", getAccountid())
            .append("jymId", getJymId())
            .append("jymAddTaskId", getJymAddTaskId())
            .append("jymEditTaskId", getJymEditTaskId())
            .append("jymDelTaskId", getJymDelTaskId())
            .append("addStatus", getAddStatus())
            .append("editStatus", getEditStatus())
            .append("delStatus", getDelStatus())
            .toString();
    }
}
