package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.UserKey;

import java.util.List;

/**
 * 密钥Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-26
 */
public interface UserKeyMapper
{
    /**
     * 查询密钥
     *
     * @param id 密钥主键
     * @return 密钥
     */
    public UserKey selectUserKeyById(Long id);

    /**
     * 查询密钥列表
     *
     * @param userKey 密钥
     * @return 密钥集合
     */
    public List<UserKey> selectUserKeyList(UserKey userKey);

    /**
     * 新增密钥
     *
     * @param userKey 密钥
     * @return 结果
     */
    public int insertUserKey(UserKey userKey);

    /**
     * 修改密钥
     *
     * @param userKey 密钥
     * @return 结果
     */
    public int updateUserKey(UserKey userKey);

    /**
     * 删除密钥
     *
     * @param id 密钥主键
     * @return 结果
     */
    public int deleteUserKeyById(Long id);

    /**
     * 批量删除密钥
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserKeyByIds(Long[] ids);
}
