package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.CustomerAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyy
 * @date 2023-05-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAuditReq extends CustomerAudit {
    private String dxyzm;
    private String yzmRankey;
}
