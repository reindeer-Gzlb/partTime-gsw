package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.OutRec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推送记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-07
 */
public interface OutRecMapper
{
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
     * 删除推送记录
     *
     * @param id 推送记录主键
     * @return 结果
     */
    public int deleteOutRecById(Long id);

    /**
     * 批量删除推送记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutRecByIds(Long[] ids);

    List<String> selectSaleId();

    void deleteOutRecBySleCode(@Param("saleCode") String sale,@Param("userId") Long userId);

    void deleteOutRecByCode(String saleCode);
    int deleteOutRecBySaleCode(String saleCode);
}
