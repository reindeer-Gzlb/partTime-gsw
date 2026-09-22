package com.ruoyi.system.V2.reply.mapper;


import com.ruoyi.system.model.domain.Reply;

import java.util.List;

/**
 * 快捷回复Mapper接口
 * 
 * @author ruoyi
 * @date 2023-08-01
 */
public interface ReplyMapper 
{
    /**
     * 查询快捷回复
     * 
     * @param id 快捷回复主键
     * @return 快捷回复
     */
    public Reply selectReplyById(Long id);

    /**
     * 查询快捷回复列表
     * 
     * @param reply 快捷回复
     * @return 快捷回复集合
     */
    public List<Reply> selectReplyList(Reply reply);

    /**
     * 新增快捷回复
     * 
     * @param reply 快捷回复
     * @return 结果
     */
    public int insertReply(Reply reply);

    /**
     * 修改快捷回复
     * 
     * @param reply 快捷回复
     * @return 结果
     */
    public int updateReply(Reply reply);

    /**
     * 删除快捷回复
     * 
     * @param id 快捷回复主键
     * @return 结果
     */
    public int deleteReplyById(Long id);

    /**
     * 批量删除快捷回复
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReplyByIds(Long[] ids);
}
