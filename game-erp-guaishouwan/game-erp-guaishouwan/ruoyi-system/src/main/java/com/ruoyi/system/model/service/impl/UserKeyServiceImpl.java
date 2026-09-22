package com.ruoyi.system.model.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.UserKey;
import com.ruoyi.system.model.mapper.UserKeyMapper;
import com.ruoyi.system.model.service.IUserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 密钥Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@Service
public class UserKeyServiceImpl implements IUserKeyService {
    @Autowired
    private UserKeyMapper userKeyMapper;

    /**
     * 查询密钥
     *
     * @param id 密钥主键
     * @return 密钥
     */
    @Override
    public UserKey selectUserKeyById(Long id) {
        return userKeyMapper.selectUserKeyById(id);
    }

    /**
     * 查询密钥列表
     *
     * @param userKey 密钥
     * @return 密钥
     */
    @Override
    public List<UserKey> selectUserKeyList(UserKey userKey) {
        List<UserKey> userKeys = userKeyMapper.selectUserKeyList(userKey);
        userKeys.stream().forEach(u -> u.setKey("********"));
        return userKeys;
    }


    /**
     * 获取接口联调人
     * @param str
     * @return
     */
    public UserKey getAuthor(String str) {
        UserKey userKey = new UserKey();
        userKey.setEncryption(str);
        List<UserKey> userKeys = userKeyMapper.selectUserKeyList(userKey);
        if (!CollectionUtils.isEmpty(userKeys)) {
            return userKeys.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增密钥
     *
     * @param userKey 密钥
     * @return 结果
     */
    @Override
    public int insertUserKey(UserKey userKey) {
        userKey.setCreateTime(DateUtils.getNowDate());
        return userKeyMapper.insertUserKey(userKey);
    }

    /**
     * 修改密钥
     *
     * @param userKey 密钥
     * @return 结果
     */
    @Override
    public int updateUserKey(UserKey userKey) {
        return userKeyMapper.updateUserKey(userKey);
    }

    /**
     * 批量删除密钥
     *
     * @param ids 需要删除的密钥主键
     * @return 结果
     */
    @Override
    public int deleteUserKeyByIds(Long[] ids) {
        return userKeyMapper.deleteUserKeyByIds(ids);
    }

    /**
     * 删除密钥信息
     *
     * @param id 密钥主键
     * @return 结果
     */
    @Override
    public int deleteUserKeyById(Long id) {
        return userKeyMapper.deleteUserKeyById(id);
    }
}
