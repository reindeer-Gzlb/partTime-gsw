package com.ruoyi.system.V2.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.V2.im.mapper.UserRemarkMapper;
import com.ruoyi.system.model.domain.UserRemark;
import com.ruoyi.system.V2.im.service.IUserRemarkService;
import org.springframework.stereotype.Service;

/**
 * 用户备注名字表(user_remark)表服务实现类
 *
 * @author zyy
 * @date 2023-08-23 17:32:54
 */
@Service("userRemarkService")
public class UserRemarkServiceImpl extends ServiceImpl<UserRemarkMapper, UserRemark> implements IUserRemarkService {

}

