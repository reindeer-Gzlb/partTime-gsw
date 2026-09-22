package com.ruoyi.system.V2.order.service;

import com.ruoyi.system.model.domain.SaleImg;

import java.util.List;

/**
 * 备注图片Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ISaleImgService 
{
    /**
     * 查询备注图片
     * 
     * @param id 备注图片主键
     * @return 备注图片
     */
    public SaleImg selectSaleImgById(Long id);

    /**
     * 查询备注图片列表
     * 
     * @param saleImg 备注图片
     * @return 备注图片集合
     */
    public List<SaleImg> selectSaleImgList(SaleImg saleImg);

    /**
     * 新增备注图片
     * 
     * @param saleImg 备注图片
     * @return 结果
     */
    public int insertSaleImg(SaleImg saleImg);

    /**
     * 修改备注图片
     * 
     * @param saleImg 备注图片
     * @return 结果
     */
    public int updateSaleImg(SaleImg saleImg);

    /**
     * 批量删除备注图片
     * 
     * @param ids 需要删除的备注图片主键集合
     * @return 结果
     */
    public int deleteSaleImgByIds(Long[] ids);

    /**
     * 删除备注图片信息
     * 
     * @param id 备注图片主键
     * @return 结果
     */
    public int deleteSaleImgById(Long id);
}
