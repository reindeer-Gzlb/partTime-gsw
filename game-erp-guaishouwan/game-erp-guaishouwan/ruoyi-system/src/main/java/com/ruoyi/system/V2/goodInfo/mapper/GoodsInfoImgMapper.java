package com.ruoyi.system.V2.goodInfo.mapper;

import com.ruoyi.system.model.domain.GoodsInfoImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品图片附Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
public interface GoodsInfoImgMapper 
{
    /**
     * 查询商品图片附
     * 
     * @param id 商品图片附主键
     * @return 商品图片附
     */
    public GoodsInfoImg selectGoodsInfoImgById(Long id);

    /**
     * 查询商品图片附列表
     * 
     * @param goodsInfoImg 商品图片附
     * @return 商品图片附集合
     */
    public List<GoodsInfoImg> selectGoodsInfoImgList(GoodsInfoImg goodsInfoImg);

    /**
     * 新增商品图片附
     * 
     * @param goodsInfoImg 商品图片附
     * @return 结果
     */
    public int insertGoodsInfoImg(GoodsInfoImg goodsInfoImg);

    /**
     * 修改商品图片附
     * 
     * @param goodsInfoImg 商品图片附
     * @return 结果
     */
    public int updateGoodsInfoImg(GoodsInfoImg goodsInfoImg);

    /**
     * 删除商品图片附
     * 
     * @param id 商品图片附主键
     * @return 结果
     */
    public int deleteGoodsInfoImgById(Long id);

    /**
     * 批量删除商品图片附
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsInfoImgByIds(Long[] ids);

    void deleteByGoodsId(@Param("goodsId") Long infoId, @Param("type") String type);

    void insertBacthGoodsInfoImg(@Param("goodsInfoImgList") List<GoodsInfoImg> goodsInfoImgList);
}
