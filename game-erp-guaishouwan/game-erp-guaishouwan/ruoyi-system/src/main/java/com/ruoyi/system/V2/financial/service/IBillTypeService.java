package com.ruoyi.system.V2.financial.service;

import com.ruoyi.system.model.domain.BillType;

import java.util.List;

/**
 * 账单类型Service接口
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
public interface IBillTypeService 
{
    /**
     * 查询账单类型
     * 
     * @param id 账单类型主键
     * @return 账单类型
     */
    public BillType selectBillTypeById(Long id);

    /**
     * 查询账单类型列表
     * 
     * @param billType 账单类型
     * @return 账单类型集合
     */
    public List<BillType> selectBillTypeList(BillType billType);
    /**
     * 查询账单类型列表
     *
     * @param billType 账单类型
     * @return 账单类型集合
     */
    public List<BillType> selectBillTypeListByCW(BillType billType);

    public List<BillType> selectH5BillTypeList(BillType billType);

    /**
     * 新增账单类型
     * 
     * @param billType 账单类型
     * @return 结果
     */
    public int insertBillType(BillType billType);

    /**
     * 修改账单类型
     * 
     * @param billType 账单类型
     * @return 结果
     */
    public int updateBillType(BillType billType);

    /**
     * 批量删除账单类型
     * 
     * @param ids 需要删除的账单类型主键集合
     * @return 结果
     */
    public int deleteBillTypeByIds(Long[] ids);

    /**
     * 删除账单类型信息
     * 
     * @param id 账单类型主键
     * @return 结果
     */
    public int deleteBillTypeById(Long id);
}
