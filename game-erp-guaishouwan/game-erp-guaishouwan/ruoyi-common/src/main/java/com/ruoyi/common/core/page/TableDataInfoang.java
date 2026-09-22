package com.ruoyi.common.core.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 表格分页数据对象
 * 
 * @author ruoyi
 */
public class TableDataInfoang extends TableDataInfo
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private Map<String,Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
