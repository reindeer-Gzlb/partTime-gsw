package com.ruoyi.system.V2.operation.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.UplodHistory;
import com.ruoyi.system.V2.operation.mapper.UplodHistoryMapper;
import com.ruoyi.system.V2.operation.service.IUplodHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 导入记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-16
 */
@Service
public class UplodHistoryServiceImpl implements IUplodHistoryService
{
    @Autowired
    private UplodHistoryMapper uplodHistoryMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询导入记录
     *
     * @param id 导入记录主键
     * @return 导入记录
     */
    @Override
    public UplodHistory selectUplodHistoryById(Long id)
    {
        return uplodHistoryMapper.selectUplodHistoryById(id);
    }

    /**
     * 查询导入记录列表
     *
     * @param uplodHistory 导入记录
     * @return 导入记录
     */
    @Override
    public List<UplodHistory> selectUplodHistoryList(UplodHistory uplodHistory)
    {
        List<UplodHistory> uplodHistories = uplodHistoryMapper.selectUplodHistoryList(uplodHistory);
        uplodHistories.stream().forEach(uolod ->{
            if (null != uolod.getCreateId()){
                SysUser sysUser = sysUserMapper.selectUserById(uolod.getCreateId());
                uolod.setCreateBy(sysUser.getNickName());
            }
        });
        return uplodHistories;
    }

    /**
     * 新增导入记录
     *
     * @param uplodHistory 导入记录
     * @return 结果
     */
    @Override
    public int insertUplodHistory(UplodHistory uplodHistory)
    {
        uplodHistory.setCreateTime(DateUtils.getNowDate());
        return uplodHistoryMapper.insertUplodHistory(uplodHistory);
    }

    /**
     * 修改导入记录
     *
     * @param uplodHistory 导入记录
     * @return 结果
     */
    @Override
    public int updateUplodHistory(UplodHistory uplodHistory)
    {
        return uplodHistoryMapper.updateUplodHistory(uplodHistory);
    }

    /**
     * 批量删除导入记录
     *
     * @param ids 需要删除的导入记录主键
     * @return 结果
     */
    @Override
    public int deleteUplodHistoryByIds(Long[] ids)
    {
        return uplodHistoryMapper.deleteUplodHistoryByIds(ids);
    }

    /**
     * 删除导入记录信息
     *
     * @param id 导入记录主键
     * @return 结果
     */
    @Override
    public int deleteUplodHistoryById(Long id)
    {
        return uplodHistoryMapper.deleteUplodHistoryById(id);
    }
}
