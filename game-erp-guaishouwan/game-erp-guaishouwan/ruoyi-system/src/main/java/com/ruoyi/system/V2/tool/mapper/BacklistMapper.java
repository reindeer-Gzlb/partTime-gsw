package com.ruoyi.system.V2.tool.mapper;

import com.ruoyi.system.model.domain.Backlist;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface BacklistMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Backlist selectBacklistById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param backlist 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Backlist> selectBacklistList(Backlist backlist);

    /**
     * 新增【请填写功能名称】
     *
     * @param backlist 【请填写功能名称】
     * @return 结果
     */
    public int insertBacklist(Backlist backlist);

    /**
     * 修改【请填写功能名称】
     *
     * @param backlist 【请填写功能名称】
     * @return 结果
     */
    public int updateBacklist(Backlist backlist);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBacklistById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBacklistByIds(Long[] ids);


    public Backlist selectBacklistByIdAndOtherUserId(Backlist backlist);

    public Backlist selectBacklistByIdAndOtherUserIdNoState(Backlist backlist);

    List<Backlist> selectBaoJiaBacklistList(Backlist backlist);

    Integer getBackCount(Integer day);

    Integer getQuaCount(Integer day);

    @Select("select pay_status as payStatus from backlist where id=#{id}")
    Map<String,Object> getBacklistPayStatus(@Param("id") Long id);

}
