package com.ruoyi.system.model.service;

import java.util.List;
import com.ruoyi.system.model.domain.AddPrice;

/**
 * 批量加价Service接口
 *
 * @author ruoyi
 * @date 2022-07-17
 */
public interface IAddPriceService
{
    /**
     * 查询批量加价
     *
     * @param id 批量加价主键
     * @return 批量加价
     */
    public AddPrice selectAddPriceById(Long id);

    /**
     * 查询批量加价列表
     *
     * @param addPrice 批量加价
     * @return 批量加价集合
     */
    public List<AddPrice> selectAddPriceList(AddPrice addPrice);

    /**
     * 新增批量加价
     *
     * @param addPrice 批量加价
     * @return 结果
     */
    public int insertAddPrice(AddPrice addPrice);

    /**
     * 修改批量加价
     *
     * @param addPrice 批量加价
     * @return 结果
     */
    public int updateAddPrice(AddPrice addPrice);

    /**
     * 批量删除批量加价
     *
     * @param ids 需要删除的批量加价主键集合
     * @return 结果
     */
    public int deleteAddPriceByIds(Long[] ids);

    /**
     * 删除批量加价信息
     *
     * @param id 批量加价主键
     * @return 结果
     */
    public int deleteAddPriceById(Long id);
}
