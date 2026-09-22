package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.OutRec;

import java.util.List;

/**
 * 推送记录Service接口
 *
 * @author ruoyi
 * @date 2022-06-07
 */
public interface IOutRecService
{

    /**
     * 清空推送记录
     */
    public void delAllOut(String saleCode);

    /**
     * 查询推送记录
     *
     * @param id 推送记录主键
     * @return 推送记录
     */
    public OutRec selectOutRecById(Long id);

    /**
     * 查询推送记录列表
     *
     * @param outRec 推送记录
     * @return 推送记录集合
     */
    public List<OutRec> selectOutRecList(OutRec outRec);

    /**
     * 新增推送记录
     *
     * @param outRec 推送记录
     * @return 结果
     */
    public int insertOutRec(OutRec outRec);

    /**
     * 修改推送记录
     *
     * @param outRec 推送记录
     * @return 结果
     */
    public int updateOutRec(OutRec outRec);

    /**
     * 批量删除推送记录
     *
     * @param ids 需要删除的推送记录主键集合
     * @return 结果
     */
    public int deleteOutRecByIds(String[] ids);

    /**
     * 删除推送记录信息
     *
     * @param id 推送记录主键
     * @return 结果
     */
    public int deleteOutRecById(Long id);

    List<String> selectSaleId();
}
