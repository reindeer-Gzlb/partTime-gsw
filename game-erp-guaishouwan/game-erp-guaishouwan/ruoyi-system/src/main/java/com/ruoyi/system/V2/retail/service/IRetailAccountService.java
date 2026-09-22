package com.ruoyi.system.V2.retail.service;

import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.RetailAccount;
import com.ruoyi.system.model.domain.RetailAddPrice;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 零售号Service接口
 *
 * @author ruoyi
 * @date 2022-05-27
 */
public interface IRetailAccountService
{
    /**
     * 查询零售号
     *
     * @param id 零售号主键
     * @return 零售号
     */
    public RetailAccount selectRetailAccountById(Long id);

    public void bacthAcc(List<GoodsInfo> goodsInfos, List<RetailAddPrice> retailAddPrices);

    /**
     * 设置分销请求参数
     * @param goodsInfo
     * @return
     */
    public List<RetailAddPrice> param(GoodsInfoAndAttrValueReq goodsInfo,Long userId);

    /**
     * 查询零售号列表
     *
     * @param retailAccount 零售号
     * @return 零售号集合
     */
    public List<RetailAccount> selectRetailAccountList(RetailAccount retailAccount);

    /**
     * 新增零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    public int insertRetailAccount(RetailAccount retailAccount);

    /**
     * 修改零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    public int updateRetailAccount(RetailAccount retailAccount);

    /**
     * 批量删除零售号
     *
     * @param ids 需要删除的零售号主键集合
     * @return 结果
     */
    public int deleteRetailAccountByIds(Long[] ids);

    /**
     * 删除零售号信息
     *
     * @param id 零售号主键
     * @return 结果
     */
    public int deleteRetailAccountById(Long id);

    String addMyAcount(Long[] ids);

    public BigDecimal getNewPrice(Long accountId, Long id, BigDecimal price);

    void bacthAddPrice(Map<String,Object> params);

    Map<String, Object> bacthAddPriceDetails(Map<String, Object> params);

    List<GameInfo> getGameType(Long userId,Integer type);
}
