package com.ruoyi.system.shouFromName.service.impl;

import java.util.List;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.shouFromName.mapper.ShouFromNameMapper;
import com.ruoyi.system.shouFromName.domain.ShouFromName;
import com.ruoyi.system.shouFromName.service.IShouFromNameService;

/**
 * 收入渠道Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-22
 */
@Service
public class ShouFromNameServiceImpl implements IShouFromNameService {
    @Autowired
    private ShouFromNameMapper shouFromNameMapper;

    /**
     * 查询收入渠道
     *
     * @param id 收入渠道主键
     * @return 收入渠道
     */
    @Override
    public ShouFromName selectShouFromNameById(Long id) {
        return shouFromNameMapper.selectShouFromNameById(id);
    }

    /**
     * 查询收入渠道列表
     *
     * @param shouFromName 收入渠道
     * @return 收入渠道
     */
    @Override
    public List<ShouFromName> selectShouFromNameList(ShouFromName shouFromName) {
        return shouFromNameMapper.selectShouFromNameList(shouFromName);
    }

    /**
     * 新增收入渠道
     *
     * @param shouFromName 收入渠道
     * @return 结果
     */
    @Override
    public int insertShouFromName(ShouFromName shouFromName) {
        Long userId = SecurityUtils.getUserId();
        shouFromName.setCreateTime(DateUtils.getNowDate());
        shouFromName.setCreateId(userId);

        ShouFromName fromName = new ShouFromName();
        fromName.setCreateId(userId);
        fromName.setName(shouFromName.getName());
        List<ShouFromName> shouFromNames = selectShouFromNameList(fromName);
        if (CollectionUtils.isNotEmpty(shouFromNames)){
            throw new GlobalException("已存在不可重复添加");
        }
        return shouFromNameMapper.insertShouFromName(shouFromName);
    }

    /**
     * 修改收入渠道
     *
     * @param shouFromName 收入渠道
     * @return 结果
     */
    @Override
    public int updateShouFromName(ShouFromName shouFromName) {
        return shouFromNameMapper.updateShouFromName(shouFromName);
    }

    /**
     * 批量删除收入渠道
     *
     * @param ids 需要删除的收入渠道主键
     * @return 结果
     */
    @Override
    public int deleteShouFromNameByIds(Long[] ids) {
        return shouFromNameMapper.deleteShouFromNameByIds(ids);
    }

    /**
     * 删除收入渠道信息
     *
     * @param id 收入渠道主键
     * @return 结果
     */
    @Override
    public int deleteShouFromNameById(Long id) {
        return shouFromNameMapper.deleteShouFromNameById(id);
    }
}
