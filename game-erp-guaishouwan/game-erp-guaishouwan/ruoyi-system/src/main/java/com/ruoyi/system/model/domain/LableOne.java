package com.ruoyi.system.model.domain;

import com.ruoyi.system.model.domain.AccountLable;
import lombok.Data;

import java.util.List;

/**
 * @author lz
 * @version 1.0
 * @date 2022/7/31 22:35
 */
@Data
public class LableOne {

    private Long infoId;
    private List<AccountLable> accountLables;

}
