package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.InterRec;

import java.util.List;

/**
 * 接口记录Service接口
 *
 * @author ruoyi
 * @date 2022-06-23
 */
public interface IInterRecService
{
    /**
     * 查询接口记录
     *
     * @param id 接口记录主键
     * @return 接口记录
     */
    public InterRec selectInterRecById(Long id);

    /**
     * 查询接口记录列表
     *
     * @param interRec 接口记录
     * @return 接口记录集合
     */
    public List<InterRec> selectInterRecList(InterRec interRec);

    /**
     * 新增接口记录
     *
     * @param interRec 接口记录
     * @return 结果
     */
    public int insertInterRec(InterRec interRec);

    /**
     * 修改接口记录
     *
     * @param interRec 接口记录
     * @return 结果
     */
    public int updateInterRec(InterRec interRec);

    /**
     * 批量删除接口记录
     *
     * @param ids 需要删除的接口记录主键集合
     * @return 结果
     */
    public int deleteInterRecByIds(Long[] ids);

    /**
     * 删除接口记录信息
     *
     * @param id 接口记录主键
     * @return 结果
     */
    public int deleteInterRecById(Long id);

    public boolean checkInter(String saleCode,Long accountId,Long userId);
    public boolean checkInter1(String saleCode,Long accountId,Long userId);

    public boolean save(String saleCode,Long accountId,Long userId);

    int deleteInterRec(Long id, String saleName);
}
