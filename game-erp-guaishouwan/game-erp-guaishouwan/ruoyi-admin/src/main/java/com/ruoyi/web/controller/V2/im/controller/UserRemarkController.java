package com.ruoyi.web.controller.V2.im.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.im.mapper.MsgGroupInfoMapper;
import com.ruoyi.system.V2.im.service.IMsgGroupInfoService;
import com.ruoyi.system.V2.im.service.IUserRemarkService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.model.domain.UserRemark;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户备注名字表(user_remark)表控制层
 *
 * @author zyy
 * @date 2023-08-23 17:32:54
 */
@RestController
@RequestMapping("/userRemark")
public class UserRemarkController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IUserRemarkService userRemarkService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IMsgGroupInfoService msgGroupInfoService;

    @Autowired
    private MsgGroupInfoMapper msgGroupInfoMapper;

    /**
     * 分页查询所有数据
     *
     * @param userRemark 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    public TableDataInfo list(UserRemark userRemark) {
//        startPage();
        LambdaQueryWrapper<UserRemark> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserRemark::getFromHxId, userRemark.getFromHxId());
        if (StringUtils.isNotEmpty(userRemark.getToHxId())) {
            queryWrapper.eq(UserRemark::getToHxId, userRemark.getToHxId());
        }
        List<UserRemark> list = userRemarkService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userRemarkService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userRemark 实体对象
     * @return 新增结果
     */
    @Log(title = "UserRemark", businessType = BusinessType.INSERT)
    @PostMapping(value = "/saveOrUpdateOrDel")
    public AjaxResult saveOrUpdate(@RequestBody UserRemark userRemark) {
        if (StringUtils.isEmpty(userRemark.getFromHxId()) || StringUtils.isEmpty(userRemark.getToHxId())) {
            throw new GlobalException("必要参数为空");
        }
        LambdaQueryWrapper<UserRemark> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserRemark::getFromHxId, userRemark.getFromHxId());
        queryWrapper.eq(UserRemark::getToHxId, userRemark.getToHxId());
        List<UserRemark> list = userRemarkService.list(queryWrapper);
        UserRemark userRemarkRes = new UserRemark();
        if (CollectionUtils.isNotEmpty(list)) {
            UserRemark userRemark1 = list.get(0);
            if (StringUtils.isEmpty(userRemark.getRemarkName())) {
                userRemarkService.removeById(userRemark1.getId());
            } else {
                userRemark1.setRemarkName(userRemark.getRemarkName());
                userRemarkService.updateById(userRemark1);
                userRemarkRes = userRemark1;
            }
        } else {
            if (StringUtils.isNotEmpty(userRemark.getRemarkName())) {
                SysUser sysUser = sysUserMapper.selectUserByHxUserName(userRemark.getFromHxId());
                if (StringUtils.isNull(sysUser)) {
                    throw new GlobalException("FromHxId未找到用户");
                }
                userRemark.setFromUserId(sysUser.getUserId());
                if (!Objects.isNull(userRemark.getType())) {
                    if (userRemark.getType() == 1) {
                        SysUser sysUser1 = sysUserMapper.selectUserByHxUserName(userRemark.getToHxId());
                        if (StringUtils.isNull(sysUser1)) {
                            throw new GlobalException("ToHxId未找到用户");
                        }
                        userRemark.setToUserId(String.valueOf(sysUser1.getUserId()));
                    } else if (userRemark.getType() == 2) {
                        MsgGroupInfo info = msgGroupInfoMapper.selectMsgGroupInfo(userRemark.getToHxId());
                        if (StringUtils.isNull(info)) {
                            throw new GlobalException("ToHxId未找到群组");
                        }
                        userRemark.setToUserId(String.valueOf(info.getId()));
                    }
                }
                userRemarkService.save(userRemark);
                userRemarkRes = userRemark;
            } else {
                userRemarkRes = null;
            }
        }
        return AjaxResult.success(userRemarkRes);
    }

    /**
     * 修改数据
     *
     * @param userRemark 实体对象
     * @return 修改结果
     */
    @Log(title = "UserRemark", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody UserRemark userRemark) {
        return toAjax(userRemarkService.updateById(userRemark));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Log(title = "UserRemark", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userRemarkService.removeByIds(Arrays.asList(ids)));
    }
}

