package com.ruoyi.system.model.exel;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class FinanceExcel {

    /** 收支类型 */
    @Excel(name = "收支类型")
    private String iotype;

    /** 账单类型 */
    @Excel(name = "账单类型")
    private String billType;

    /** 金额 */
    @Excel(name = "金额")
    private String price;

    /** 附件 */
    @Excel(name = "说明")
    private String atta;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    @Excel(name = "字段1")
    private String field1;
    @Excel(name = "字段2")
    private String field2;
    @Excel(name = "字段3")
    private String field3;
    @Excel(name = "字段4")
    private String field4;
    @Excel(name = "字段5")
    private String field5;
    @Excel(name = "字段6")
    private String field6;
    @Excel(name = "提交人")
    private String registeruser;
}
