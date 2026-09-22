package com.ruoyi.system.V2.customerAudit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.CustomerAudit;

import java.util.List;

/**
 * @author zyy
 * @date 2023-05-05
 */
public interface ICustomerAuditService extends IService<CustomerAudit> {

    int auditPass(CustomerAudit customerAudit);
    CustomerAudit selectLastOne(Long id);
    List<CustomerAudit> selectByEntity(CustomerAudit customerAudit);
}
