package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.Skin;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-03
 */
public interface SkinMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Skin selectSkinById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param skin 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Skin> selectSkinList(Skin skin);

    /**
     * 新增【请填写功能名称】
     *
     * @param skin 【请填写功能名称】
     * @return 结果
     */
    public int insertSkin(Skin skin);

    /**
     * 修改【请填写功能名称】
     *
     * @param skin 【请填写功能名称】
     * @return 结果
     */
    public int updateSkin(Skin skin);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSkinById(Long id);
    public int deleteSkinByCOnfig(Long config);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkinByIds(Long[] ids);
}
