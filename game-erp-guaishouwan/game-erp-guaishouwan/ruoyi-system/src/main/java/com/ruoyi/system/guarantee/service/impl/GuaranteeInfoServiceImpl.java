package com.ruoyi.system.guarantee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.guarantee.domain.GuaranteeInfo;
import com.ruoyi.system.guarantee.mapper.GuaranteeInfoMapper;
import com.ruoyi.system.guarantee.service.IGuaranteeInfoService;
import org.springframework.stereotype.Service;

/**
 * 包赔资料信息表(guarantee_info)表服务实现类
 *
 * @author zyy
 * @date 2023-12-05 18:41:00
 */
@Service("guaranteeInfoService")
public class GuaranteeInfoServiceImpl extends ServiceImpl<GuaranteeInfoMapper, GuaranteeInfo> implements IGuaranteeInfoService {

}

