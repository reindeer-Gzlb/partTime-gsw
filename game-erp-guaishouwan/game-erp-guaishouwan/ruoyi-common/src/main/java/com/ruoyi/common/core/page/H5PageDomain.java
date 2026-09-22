package com.ruoyi.common.core.page;



import java.util.List;

/**
 * 分页数据
 *
 * @author ruoyi
 */
public class H5PageDomain
{
    /** 当前记录起始索引 */
    private Integer page;

    /** 每页显示记录数 */
    private Integer pageSize;

    private Integer records;

    private List<?> rows;

    private Integer total;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
