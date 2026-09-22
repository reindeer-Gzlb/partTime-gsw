package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.InfoImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图片附Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-07
 */
@Component
public interface InfoImgMapper
{
    /**
     * 查询图片附
     *
     * @param id 图片附主键
     * @return 图片附
     */
    public InfoImg selectInfoImgById(Long id);

    /**
     * 查询图片附列表
     *
     * @param infoImg 图片附
     * @return 图片附集合
     */
    public List<InfoImg> selectInfoImgList(InfoImg infoImg);

    /**
     * 新增图片附
     *
     * @param infoImg 图片附
     * @return 结果
     */
    public int insertInfoImg(InfoImg infoImg);

    /**
     * 修改图片附
     *
     * @param infoImg 图片附
     * @return 结果
     */
    public int updateInfoImg(InfoImg infoImg);

    /**
     * 删除图片附
     *
     * @param id 图片附主键
     * @return 结果
     */
    public int deleteInfoImgById(Long id);

    /**
     * 批量删除图片附
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInfoImgByIds(Long[] ids);

    void deleteByInfoId(@Param("infoId") Long infoId,@Param("type") String type);

    void insertBacthInfoImg(@Param("infoImgList") List<InfoImg> infoImgList);
}
