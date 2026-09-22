package com.ruoyi.system.webReq.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户端请求记录对象 web_req_history
 *
 * @author ruoyi
 * @date 2024-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebReqHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 页面路径
     */
    @Excel(name = "页面路径")
    private String pagePath;

    /**
     * 来源地址
     */
    @Excel(name = "来源地址")
    private String resource;

    /**
     * 备注
     */
    private String remark;



    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

}
