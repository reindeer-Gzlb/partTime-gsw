package com.ruoyi.system.checkReal.domain;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实名记录对象 check_real_info
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@Data
public class CheckRealInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;
    /** 手机号 */
    @Excel(name = "手机号")
    private String inputMobile;
    /** 实名 */
    @Excel(name = "实名")
    private String inputName;
    /** carrier */
    @Excel(name = "carrier")
    private String carrier;

    /** result_message */
    @Excel(name = "result_message")
    private String resultMessage;

    /** result_code */
    @Excel(name = "result_code")
    private String resultCode;

    /** state */
    @Excel(name = "state")
    private String state;

    /** request_id */
    @Excel(name = "request_id")
    private String requestId;

    /** status */
    @Excel(name = "status")
    private String status;

}
