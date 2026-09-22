package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.ClassSkin;

import java.util.List;

/**
 * 皮肤分类Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
public interface ClassSkinMapper 
{
    /**
     * 查询皮肤分类
     * 
     * @param id 皮肤分类主键
     * @return 皮肤分类
     */
    public ClassSkin selectClassSkinById(Long id);

    /**
     * 查询皮肤分类列表
     * 
     * @param classSkin 皮肤分类
     * @return 皮肤分类集合
     */
    public List<ClassSkin> selectClassSkinList(ClassSkin classSkin);

    /**
     * 新增皮肤分类
     * 
     * @param classSkin 皮肤分类
     * @return 结果
     */
    public int insertClassSkin(ClassSkin classSkin);

    /**
     * 修改皮肤分类
     * 
     * @param classSkin 皮肤分类
     * @return 结果
     */
    public int updateClassSkin(ClassSkin classSkin);

    /**
     * 删除皮肤分类
     * 
     * @param id 皮肤分类主键
     * @return 结果
     */
    public int deleteClassSkinById(Long id);

    /**
     * 批量删除皮肤分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClassSkinByIds(Long[] ids);
}
