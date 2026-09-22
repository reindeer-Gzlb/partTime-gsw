package com.ruoyi.system.V2.api.service;

import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * goodsService接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface IApiGoodsInfoService {

    /**
     * 查询goods
     *
     * @param id goods主键
     * @return goods
     */
    public GoodsInfo selectGoodsInfoById(Long id);

    /**
     * 新增goods
     *
     * @param goodsInfo goods
     * @return 结果
     */
    public int insertGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 修改goods
     *
     * @param goodsInfo goods
     * @return 结果
     */
    public int updateGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 商品上架
     * @param goodsInfo
     * @return
     */
    public int ApiGoodsOn(GoodsInfo goodsInfo);

    /**
     * 商品下架
     * @param goodsInfo
     * @return
     */
    public int ApiGoodsOff(GoodsInfo goodsInfo);



    public GoodsInfoAndAttrValueDto selectGoodsInfoAndAttrValueById(Long id);

    /**
     * 查询goods列表
     *
     * @param goodsInfo goods
     * @return goods集合
     */
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo);


    @Transactional
    public Long insertApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    @Transactional
    public Long updateApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    @Transactional
    public Long insertBDApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    @Transactional
    public int bdupdateGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);
}
