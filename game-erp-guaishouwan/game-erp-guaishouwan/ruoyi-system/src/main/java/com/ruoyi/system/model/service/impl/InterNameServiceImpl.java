package com.ruoyi.system.model.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.InterName;
import com.ruoyi.system.model.mapper.InterNameMapper;
import com.ruoyi.system.model.service.IInterNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-30
 */
@Service
public class InterNameServiceImpl implements IInterNameService
{
    @Autowired
    private InterNameMapper interNameMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public InterName selectInterNameById(Long id)
    {
        return interNameMapper.selectInterNameById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param interName 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<InterName> selectInterNameList(InterName interName)
    {

        List<InterName> interNames = interNameMapper.selectInterNameList(interName);
        interNames.stream().forEach(inter ->{

            SysUser sysUser = sysUserMapper.selectUserById(inter.getCreateId());

            if (null != sysUser){
                inter.setCreateName(sysUser.getUserName());
            }
        });

        return interNames;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param interName 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertInterName(InterName interName)
    {
        interName.setCreateTime(DateUtils.getNowDate());
        InterName params = new InterName();
        params.setCode(interName.getCode());
        List<InterName> interNames = interNameMapper.selectInterNameList(interName);
        if (!CollectionUtils.isEmpty(interNames)){
            throw new ServiceException("已存在，只允许编辑");
        }
        Long userId = SecurityUtils.getUserId();
        interName.setCreateId(userId);
        return interNameMapper.insertInterName(interName);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param interName 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateInterName(InterName interName)
    {
        interName.setUpdateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        interName.setUpdateId(userId);
        return interNameMapper.updateInterName(interName);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInterNameByIds(Long[] ids)
    {
        return interNameMapper.deleteInterNameByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInterNameById(Long id)
    {
        return interNameMapper.deleteInterNameById(id);
    }
}
