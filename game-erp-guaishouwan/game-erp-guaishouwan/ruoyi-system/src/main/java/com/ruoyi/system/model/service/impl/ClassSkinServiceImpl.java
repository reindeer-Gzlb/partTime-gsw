package com.ruoyi.system.model.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.ClassSkin;
import com.ruoyi.system.model.mapper.ClassSkinMapper;
import com.ruoyi.system.model.service.IClassSkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 皮肤分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@Service
public class ClassSkinServiceImpl implements IClassSkinService
{
    @Autowired
    private ClassSkinMapper classSkinMapper;

    /**
     * 查询皮肤分类
     * 
     * @param id 皮肤分类主键
     * @return 皮肤分类
     */
    @Override
    public ClassSkin selectClassSkinById(Long id)
    {
        return classSkinMapper.selectClassSkinById(id);
    }

    /**
     * 查询皮肤分类列表
     * 
     * @param classSkin 皮肤分类
     * @return 皮肤分类
     */
    @Override
    public List<ClassSkin> selectClassSkinList(ClassSkin classSkin)
    {
        return classSkinMapper.selectClassSkinList(classSkin);
    }

    /**
     * 新增皮肤分类
     * 
     * @param classSkin 皮肤分类
     * @return 结果
     */
    @Override
    public int insertClassSkin(ClassSkin classSkin)
    {
        classSkin.setCreateTime(DateUtils.getNowDate());
        return classSkinMapper.insertClassSkin(classSkin);
    }

    /**
     * 修改皮肤分类
     * 
     * @param classSkin 皮肤分类
     * @return 结果
     */
    @Override
    public int updateClassSkin(ClassSkin classSkin)
    {
        classSkin.setUpdateTime(DateUtils.getNowDate());
        return classSkinMapper.updateClassSkin(classSkin);
    }

    /**
     * 批量删除皮肤分类
     * 
     * @param ids 需要删除的皮肤分类主键
     * @return 结果
     */
    @Override
    public int deleteClassSkinByIds(Long[] ids)
    {
        return classSkinMapper.deleteClassSkinByIds(ids);
    }

    /**
     * 删除皮肤分类信息
     * 
     * @param id 皮肤分类主键
     * @return 结果
     */
    @Override
    public int deleteClassSkinById(Long id)
    {
        return classSkinMapper.deleteClassSkinById(id);
    }
}
