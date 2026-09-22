package com.ruoyi.system.V2.goodInfo.mapper;

import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.dto.GoodReq;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * goodsMapper接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface GoodsInfoMapper {
    /**
     * 查询goods
     *
     * @param id goods主键
     * @return goods
     */
    public GoodsInfo selectGoodsInfoById(Long id);

    public List<GoodsInfo> leftDateGoodsDetail(Long businessId);

    public GoodsInfoAndAttrValueDto selectGoodsInfoAndAttrValueById(Long id);

    /**
     * 查询goods列表
     *
     * @param goodsInfo goods
     * @return goods集合
     */
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo);

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
     * 删除goods
     *
     * @param id goods主键
     * @return 结果
     */
    public int deleteGoodsInfoById(Long id);

    /**
     * 批量删除goods
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsInfoByIds(Long[] ids);


    public List<GoodsInfoAndAttrValueDto> selectGoodsInfoAndAttrList(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    List<GoodsInfo> selectByResList(GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq);

    Integer apiSelectGoodsCountBySupply(GoodsInfo goodsInfo);

    int auditGoods(@Param("ids") Long[] ids);

    int down(@Param("ids") Long[] ids);

    int submitAccount(@Param("ids") Long[] ids);

    List<GoodsInfo> selectPush(GoodReq goodReq);

    List<Long> accIds(GoodsInfo goodsInfo);

    GoodsInfo selectGoodsInfoByCorrespondjym(String correspondjym);

    GoodsInfo selectGoodsInfoByThirdId(String thirdId);

    List<Map<String, Object>> countForSupply(Long userId);

    List<Map<String, Object>> userGameCount();

    Map<String, Object> statistics(GoodsInfo goodsInfo);

    Integer getYesterdaySold();

    Integer getGoodsInfoCount(GoodsInfo goodsInfo);

    int auditGoodsFailure(@Param("ids") Long[] ids, @Param("auditDes") String auditDes);

    List<GoodsInfo> recentlydeal();

    List<Map<String,Object>> goodsCount(GoodsInfo goodsInfo);

    List<Map<String,Object>> hotGame(GoodsInfo goodsInfo);

    Map<String, Object> statisticsData(GoodsInfo info);
}
