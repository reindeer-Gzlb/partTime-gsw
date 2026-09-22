package com.ruoyi.system.V2.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.SaleImg;
import com.ruoyi.system.V2.order.mapper.SaleImgMapper;
import com.ruoyi.system.V2.order.service.ISaleImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 备注图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class SaleImgServiceImpl implements ISaleImgService
{
    @Autowired
    private SaleImgMapper saleImgMapper;

    /**
     * 查询备注图片
     * 
     * @param id 备注图片主键
     * @return 备注图片
     */
    @Override
    public SaleImg selectSaleImgById(Long id)
    {
        return saleImgMapper.selectSaleImgById(id);
    }

    /**
     * 查询备注图片列表
     * 
     * @param saleImg 备注图片
     * @return 备注图片
     */
    @Override
    public List<SaleImg> selectSaleImgList(SaleImg saleImg)
    {
        return saleImgMapper.selectSaleImgList(saleImg);
    }

    /**
     * 新增备注图片
     * 
     * @param saleImg 备注图片
     * @return 结果
     */
    @Override
    public int insertSaleImg(SaleImg saleImg)
    {
        saleImg.setCreateTime(DateUtils.getNowDate());
        return saleImgMapper.insertSaleImg(saleImg);
    }

    /**
     * 修改备注图片
     * 
     * @param saleImg 备注图片
     * @return 结果
     */
    @Override
    public int updateSaleImg(SaleImg saleImg)
    {
        saleImg.setUpdateTime(DateUtils.getNowDate());
        return saleImgMapper.updateSaleImg(saleImg);
    }

    /**
     * 批量删除备注图片
     * 
     * @param ids 需要删除的备注图片主键
     * @return 结果
     */
    @Override
    public int deleteSaleImgByIds(Long[] ids)
    {
        return saleImgMapper.deleteSaleImgByIds(ids);
    }

    /**
     * 删除备注图片信息
     * 
     * @param id 备注图片主键
     * @return 结果
     */
    @Override
    public int deleteSaleImgById(Long id)
    {
        return saleImgMapper.deleteSaleImgById(id);
    }
}
