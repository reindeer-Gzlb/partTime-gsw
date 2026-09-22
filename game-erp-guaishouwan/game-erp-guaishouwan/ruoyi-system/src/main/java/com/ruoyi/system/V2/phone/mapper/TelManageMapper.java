package com.ruoyi.system.V2.phone.mapper;

import com.ruoyi.system.model.domain.TelManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 手机管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-05
 */
public interface TelManageMapper 
{
    /**
     * 查询手机管理
     * 
     * @param id 手机管理主键
     * @return 手机管理
     */
    public TelManage selectTelManageById(Long id);

    /**
     * 查询手机管理列表
     * 
     * @param telManage 手机管理
     * @return 手机管理集合
     */
    public List<TelManage> selectTelManageList(TelManage telManage);

    /**
     * 新增手机管理
     * 
     * @param telManage 手机管理
     * @return 结果
     */
    public int insertTelManage(TelManage telManage);

    /**
     * 修改手机管理
     * 
     * @param telManage 手机管理
     * @return 结果
     */
    public int updateTelManage(TelManage telManage);

    /**
     * 删除手机管理
     * 
     * @param id 手机管理主键
     * @return 结果
     */
    public int deleteTelManageById(Long id);

    /**
     * 批量删除手机管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTelManageByIds(Long[] ids);

    List<Map<String, Object>> selectTelManageListMap(@Param("ids") List<Long> ids);
}
