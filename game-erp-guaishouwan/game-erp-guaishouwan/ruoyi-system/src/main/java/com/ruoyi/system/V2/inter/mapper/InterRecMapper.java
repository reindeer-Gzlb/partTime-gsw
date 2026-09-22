package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.InterRec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-23
 */
public interface InterRecMapper
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
     * 删除接口记录
     *
     * @param id 接口记录主键
     * @return 结果
     */
    public int deleteInterRecById(Long id);

    /**
     * 批量删除接口记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInterRecByIds(Long[] ids);

    void deleteInterRecBySleCode(@Param("sale") String sale,@Param("userId") Long userId);

    void deleteInterRecBySaleCode(@Param("sale") String sale);

    void deleteInterAccount(@Param("accountId") String accountId,@Param("saleCode") String saleCode);

}
