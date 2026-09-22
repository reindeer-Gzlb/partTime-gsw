package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-11-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_login_token")
public class SysUserLoginToken {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * userId
     */
    private Long userId;

    /**
     * 登录token
     */
    private String token;

}
