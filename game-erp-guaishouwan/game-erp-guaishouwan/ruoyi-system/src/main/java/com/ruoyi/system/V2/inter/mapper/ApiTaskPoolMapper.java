package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.ApiTaskPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2022-09-05
 */
public interface ApiTaskPoolMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ApiTaskPool selectApiTaskPoolById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ApiTaskPool> selectApiTaskPoolList(ApiTaskPool apiTaskPool);

    /**
     * 新增【请填写功能名称】
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 结果
     */
    public int insertApiTaskPool(ApiTaskPool apiTaskPool);

    /**
     * 修改【请填写功能名称】
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 结果
     */
    public int updateApiTaskPool(ApiTaskPool apiTaskPool);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteApiTaskPoolById(Long id);

    public int deleteApiTaskPoolByPartCode(String saleCode);

    public int updateStatus(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApiTaskPoolByIds(Long[] ids);

    void insertBatch(List<ApiTaskPool> apiTaskPoolList);

    List<Map<String, Object>> taskCount();

    void deleteApiTaskPoolByConfigAndId(@Param("accountId") Long id, @Param("config") Long config, @Param("type") String del);
}
