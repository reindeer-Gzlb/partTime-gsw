package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.SkinImg;

import java.util.List;

/**
 * 皮肤图片Mapper接口
 *
 * @author lz
 * @date 2022-06-28
 */
public interface SkinImgMapper
{
    /**
     * 查询皮肤图片
     *
     * @param id 皮肤图片主键
     * @return 皮肤图片
     */
    public SkinImg selectSkinImgById(Long id);

    /**
     * 查询皮肤图片列表
     *
     * @param skinImg 皮肤图片
     * @return 皮肤图片集合
     */
    public List<SkinImg> selectSkinImgList(SkinImg skinImg);

    /**
     * 新增皮肤图片
     *
     * @param skinImg 皮肤图片
     * @return 结果
     */
    public int insertSkinImg(SkinImg skinImg);

    /**
     * 修改皮肤图片
     *
     * @param skinImg 皮肤图片
     * @return 结果
     */
    public int updateSkinImg(SkinImg skinImg);

    /**
     * 删除皮肤图片
     *
     * @param id 皮肤图片主键
     * @return 结果
     */
    public int deleteSkinImgById(Long id);

    /**
     * 批量删除皮肤图片
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkinImgByIds(Long[] ids);
}
