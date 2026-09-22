package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.AddPrice;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量加价Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-17
 */
@Component
public interface AddPriceMapper
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
     * 删除批量加价
     *
     * @param id 批量加价主键
     * @return 结果
     */
    public int deleteAddPriceById(Long id);

    /**
     * 批量删除批量加价
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddPriceByIds(Long[] ids);
}
