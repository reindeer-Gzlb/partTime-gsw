package com.ruoyi.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyy
 * @date 2023-09-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserSelectVo {
    private Long userId;
    private String userName;
    private String nickName;
}
