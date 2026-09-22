package com.ruoyi.web.controller.V2.customerAudit.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.system.V2.customerAudit.service.ICustomerAuditService;
import com.ruoyi.system.model.domain.CustomerAudit;
import com.ruoyi.system.model.dto.CustomerAuditReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customer/audit")
public class CustomerAuditController extends BaseController {
    @Autowired
    ICustomerAuditService customerAuditService;
    @Autowired
    private RedisCache redisCache;

    @GetMapping("/list")
    public TableDataInfo list(CustomerAudit customerAudit) {
        List<CustomerAudit> customerAuditList = customerAuditService.selectByEntity(customerAudit);
        return getDataTable(customerAuditList);
    }

    /**
     * 获取customerAudit详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(customerAuditService.getById(id));
    }

    /**
     * 新增customerAudit
     */
    @Log(title = "customerAudit", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerAuditReq customerAuditReq) {
        if (customerAuditReq != null) {
            boolean flag = redisCache.checkYZM(customerAuditReq.getDxyzm(), customerAuditReq.getYzmRankey(),
                    customerAuditReq.getPhoneNumber());
            if (!flag) {
                throw new GlobalException("验证码无效");
            }
        } else {
            throw new GlobalException("提交数据有误");
        }
        CustomerAudit customerAudit = new CustomerAudit();
        BeanUtils.copyProperties(customerAuditReq, customerAudit);
        customerAudit.setCreateId(getLoginUser().getUserId());
        boolean count = customerAuditService.save(customerAudit);
        redisCache.deleteObject(customerAuditReq.getYzmRankey());
        return toAjax(count);
    }

//    /**
//     * 修改customerAudit
//     */
//    @Log(title = "customerAudit", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody CustomerAudit customerAudit) {
//        return toAjax(customerAuditService.updateById(customerAudit));
//    }
//
//    /**
//     * 删除customerAudit
//     */
//    @Log(title = "customerAudit", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(customerAuditService.removeByIds(Arrays.asList(ids)));
//    }

    /**
     * 审核不通过
     */
    @Log(title = "customerAudit", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/refuse")
    public AjaxResult refuse(@RequestBody CustomerAudit customerAudit) {
        if (Objects.isNull(customerAudit)) {
            throw new GlobalException("传入参数为null");
        }
        if (Objects.isNull(customerAudit.getId())) {
            throw new GlobalException("传入参数id为null");
        }
        CustomerAudit customerAuditRK = customerAuditService.getById(customerAudit.getId());
        LoginUser loginUser = getLoginUser();
        customerAuditRK.setAuditId(loginUser.getUserId());
        customerAuditRK.setAuditName(loginUser.getUsername());
        customerAuditRK.setAuditState(2);
        customerAuditRK.setAuditRemark(customerAudit.getAuditRemark());
        return toAjax(customerAuditService.updateById(customerAuditRK));
    }

    /**
     * 审核通过
     */
    @Log(title = "customerAudit", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/pass")
    public AjaxResult pass(@RequestBody CustomerAudit customerAudit) {
        if (Objects.isNull(customerAudit)) {
            throw new GlobalException("传入参数为null");
        }
        if (Objects.isNull(customerAudit.getId())) {
            throw new GlobalException("传入参数id为空");
        }
        if (Objects.isNull(customerAudit.getRise())) {
            throw new GlobalException("利润比例不能为空");
        }
        if (Objects.isNull(customerAudit.getDiscountt())) {
            throw new GlobalException("供货商折扣不能为空");
        }
        return toAjax(customerAuditService.auditPass(customerAudit));
    }

    /**
     * 获取当前用户最后提交的记录
     */
    @GetMapping(value = "/lastOne")
    public AjaxResult lastOne() {
        return AjaxResult.success(customerAuditService.selectLastOne(getLoginUser().getUserId()));
    }

}
