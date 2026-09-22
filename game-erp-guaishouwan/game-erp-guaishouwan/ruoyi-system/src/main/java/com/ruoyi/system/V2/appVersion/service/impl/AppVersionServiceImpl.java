package com.ruoyi.system.V2.appVersion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.V2.appVersion.mapper.AppVersionMapper;
import com.ruoyi.system.V2.appVersion.service.IAppVersionService;
import com.ruoyi.system.model.domain.AppVersion;
import org.springframework.stereotype.Service;

@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements IAppVersionService {
}
