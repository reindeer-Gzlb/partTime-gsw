package com.ruoyi.system.V2.goodInfo.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.model.vo.DeleteAllVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * goodsService接口
 *
 * @author ruoyi
 * @date 2023-04-06
 */
public interface IGoodsInfoService {
    /**
     * 查询goods
     *
     * @param id goods主键
     * @return goods
     */
    public GoodsInfo selectGoodsInfoById(Long id);

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

    public void bacthAddPrice(List<GoodsInfo> goodsInfos, List<SysUser> sysUserList);

    /**
     * 修改goods
     *
     * @param goodsInfo goods
     * @return 结果
     */
    public int updateGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 批量删除goods
     *
     * @param ids 需要删除的goods主键集合
     * @return 结果
     */
    public int deleteGoodsInfoByIds(Long[] ids, String pwd);

    /**
     * 删除goods信息
     *
     * @param id goods主键
     * @return 结果
     */
    public int deleteGoodsInfoById(Long id);

    public GoodsInfoAndAttrValueDto selectGoodsInfoAndAttrValueById(Long id);

    /**
     * 新增goodsAndAttr
     *
     * @param goodsInfoAndAttrValueDto
     * @return 结果
     */
    public GoodsInfo insertGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 修改goods
     *
     * @param goodsInfoAndAttrValueDto
     * @return 结果
     */
    @Transactional
    public int updateGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);
    /**
     * 修改goods
     *
     * @param goodsInfoAndAttrValueDto
     * @return 结果
     */
    @Transactional
    public int isOnSpecialOffer(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 查询goods列表
     *
     * @param goodsInfoAndAttrValueDto
     * @return goods集合
     */
    public List<GoodsInfoAndAttrValueDto> selectGoodsInfoAndAttrList(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 商品上架
     *
     * @param id
     * @return
     */
    public int upGoodsById(Long id);

    /**
     * 商品下架
     *
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    public int downGoodsById(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);
    /**
     * 商品下架
     *
     * @param  goodsInfoAndAttrValueDto
     * @return
     */
    public int updateIsPush(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 商品转问题
     *
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    public int problemGoods(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 商品转找回
     *
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    public int retrieveGoods(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    public List<GoodsInfo> selectByResList(GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq, PageDomain pageDomain);

    int auditGoods(Long[] ids);

    int deleteAllAccount(DeleteAllVo deleteAllVo);

    int down(Long[] ids);

    void bacthAccRetail(List<GoodsInfo> goodsInfos, List<SysUser> supplyCustomer, Long userId);

    void bacthAccRetailByUserId(List<GoodsInfo> list, List<SysUser> supplyCustomer, Long userId);

    void sensitive(GoodsInfo goodsInfo);

    int submitAccount(Long[] ids);

    List<Map<String, Object>> countForSupply(GoodsInfo goodsInfo);

    List<Map<String, Object>> userGameCount();

    Map<String, Object> pcIndexData();

    int auditGoodsFailure(Long[] ids, String auditDes);

    int topOnOrOff(GoodsInfo goodsInfo);

    int editStateGoods(Long id);

    /**
     * 转分期
     * @param id
     * @return
     */
    int convertFQ(Long id);
    /**
     * 已封禁
     * @param id
     * @return
     */
    int convertYFJ(Long id);
    /**
     * 已私下
     * @param id
     * @return
     */
    int convertYSX(Long id);
    /**
     * 赔付中
     * @param id
     * @return
     */
    int convertPFZ(Long id);
    /**
     * 永久出售
     * @param id
     * @return
     */
    int convertYJCS(Long id);

    /**
     * 红锁赔付
     * @param id
     * @return
     */
    int convertHSPF(Long id);

    /**
     * 在手封禁
     * @param id
     * @return
     */
    int convertZSFJ(Long id);
}
