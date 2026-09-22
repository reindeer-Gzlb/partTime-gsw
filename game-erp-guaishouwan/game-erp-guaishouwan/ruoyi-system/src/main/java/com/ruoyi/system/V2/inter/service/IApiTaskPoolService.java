package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.ApiTaskPool;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/22 14:47
 */
public interface IApiTaskPoolService {

    void insertBacth(Long accountId,String type);

    void insertBacth(Long accountId,Long configId,String type,Long userId);

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
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteApiTaskPoolByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteApiTaskPoolById(Long id);

    List<Map<String, Object>> taskCount();
}
