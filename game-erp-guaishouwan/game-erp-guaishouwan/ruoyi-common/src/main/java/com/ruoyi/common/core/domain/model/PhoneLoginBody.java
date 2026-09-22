package com.ruoyi.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyy
 * @date 2023-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLoginBody {
    /**
     * 登录手机号
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String dxyzm;
    /**
     * 发送短信的时候后台返回的key
     */
    private String yzmRankey;
}
