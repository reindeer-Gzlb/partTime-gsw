package com.ruoyi.system.V2.im.mapper;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface MsgGroupInfoMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public MsgGroupInfo selectMsgGroupInfoById(Long id);

    public List<MsgGroupInfo> selectMsgGroupInfoByIds(@Param("ids") List<Long> ids);

    /**
     * 根据群id获取群信息
     *
     * @param groups 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public List<MsgGroupInfo> selectMsgGroupInfoByGroups(@Param("groups") List<String> groups);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param msgGroupInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<MsgGroupInfo> selectMsgGroupInfoList(MsgGroupInfo msgGroupInfo);

    public MsgGroupInfo selectMsgGroupInfo(String group);

    /**
     * 新增【请填写功能名称】
     * 
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertMsgGroupInfo(MsgGroupInfo msgGroupInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateMsgGroupInfo(MsgGroupInfo msgGroupInfo);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteMsgGroupInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMsgGroupInfoByIds(Long[] ids);

    @Select("select to_group from msg_group_info " +
            "where del=1 " +
            "and numbers like concat('%',#{hxUserId},'%') ")
    public List<String> getAllGroup(@Param("hxUserId") String hxUserId);
}
