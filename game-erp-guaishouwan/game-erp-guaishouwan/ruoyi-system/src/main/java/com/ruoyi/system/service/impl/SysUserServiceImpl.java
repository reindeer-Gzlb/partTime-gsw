package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.easemob.im.server.model.EMUser;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ToPinYin;
import com.ruoyi.common.utils.UUIDGenerator;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.V2.inter.service.ISupplyInfoService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.model.mapper.UserKeyMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserLoginTokenService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISupplyInfoService supplyInfoService;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    private ISysUserLoginTokenService sysUserLoginTokenService;

    @Autowired
    private RedisCache redisCache;


    public int upCampNumber(Integer campNumber, Long userId) {
        Integer i = sysUserMapper.upCampNumber(campNumber, userId);
        return i;

    }
    /**
     * 编辑steam剩余次数
     * @return
     */
    public int upSteamNumber(Integer number, Long userId) {
        Integer i = sysUserMapper.upSteamNumber(number, userId);
        return i;
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
//    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        List<SysUser> sysUsers = new ArrayList<>();
        sysUsers = userMapper.selectUserList(user);
        return sysUsers;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        //如果为api发布用户 则生成api信息 (供货商表信息  appid appSecret等)
        if (Objects.nonNull(user.getUserUse())) {
            if (user.getUserUse().intValue() == 1) {
                insertSupplyInfo(user);
            }
        }
        if (StringUtils.isEmpty(user.getAvatar())) {
            user.setAvatar("http://img.taohaoqu.com/logo/660e3ceee795de82d5dfa64a1168a4a.png");
        }
        //隐藏账号
//        hiddenAccount(user);

//        注册环信
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUserName());
        params.put("pwd", user.getUserName());

        return rows;
    }


    private void hiddenAccount(SysUser user) {
        accountInfoMapper.hiddenAccount(user.getUserId(), user.getIsshow());

    }

    /**
     * 更新本供应商导入的账号
     *
     * @param user
     */
    public void updateAccountOdr(SysUser user) {
        accountInfoMapper.updateOdr(user.getOdr(), user.getUserId());
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        if (userMapper.insertUser(user) > 0) {
            if (StringUtils.isEmpty(user.getAvatar())) {
                user.setAvatar("http://img.taohaoqu.com/logo/660e3ceee795de82d5dfa64a1168a4a.png");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("username", user.getUserName());
            params.put("pwd", user.getUserName());
        } else {
            return false;
        }
        return true;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        updateAccountOdr(user);
        //如果为api发布用户 则生成api信息 (供货商表信息  appid appSecret等)
        if (Objects.nonNull(user.getUserUse())) {
            if (user.getUserUse().intValue() == 1) {
                insertSupplyInfo(user);
            }
        }
        //隐藏账号
//        hiddenAccount(user);
        //修改
        userMapper.updateUser(user);
        SysUser sysUser = userMapper.selectUserById(userId);
        if (StringUtils.isNotEmpty(sysUser.getHxusername())) {
            JSONObject uptParams = new JSONObject();
            uptParams.put("nickname", sysUser.getNickName());
            uptParams.put("avatar", sysUser.getAvatar());
            uptParams.put("phone", sysUser.getPhonenumber());
            uptParams.put("username", sysUser.getHxusername());
        }
        return 1;
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {

        String status = user.getStatus();
        if (Objects.equals(status, "1")) {
            //停用
            String token = SecurityUtils.getLoginUser().getToken();
            System.out.println("token:" + token);
//            redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + token);
            sysUserLoginTokenService.delByUserId(user.getUserId());
        }

        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    public void insertSupplyInfo(SysUser user) {
        List<SupplyInfo> supplyInfos = supplyInfoService.getListForUserId(user.getUserId());
        if (CollectionUtils.isEmpty(supplyInfos)) {
            SupplyInfo supplyInfo = new SupplyInfo();
            supplyInfo.setUserId(user.getUserId());
            supplyInfo.setName(user.getNickName());
            supplyInfo.setAppId(ToPinYin.toPinyin(user.getNickName()));
            supplyInfo.setAppSecret(UUIDGenerator.generate());
            supplyInfo.setIsEnble(0);
            supplyInfoService.save(supplyInfo);
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
//        SysRole role = roleMapper.selectRoleByRoleKey(Constant.supplyAirplane);
//        Arrays.stream(roles).forEach(r -> {
//            if (r == role.getRoleId()) {
//                UserKey userKey = new UserKey();
//                userKey.setUserId(user.getUserId());
//                List<UserKey> userKeys = userKeyMapper.selectUserKeyList(userKey);
//                //新增密钥
//                if (CollectionUtils.isEmpty(userKeys)){
//                    userKey.setSupplyName(user.getUserName());
//                    String key = UUIDGenerator.generate12();
//                    userKey.setKey(key);
//                    String pinyin = ToPinYin.toPinyin(user.getUserName());
//                    try {
//                        Long userId = SecurityUtils.getUserId();
//                        String encrypt = DESUtils.encrypt(pinyin, key);
//                        userKey.setEncryption(encrypt);
//                        userKey.setSpell(pinyin);
//                        userKey.setCreateId(userId);
//                        userKey.setRise(user.getRise());
//                        userKeyMapper.insertUserKey(userKey);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new GlobalException("密钥生成失败，请联系管里员");
//                    }
//                }else {
////                    if (Objects.nonNull(user.getUserId())){
////                        UserKey ukSelect = new UserKey();
////                        ukSelect.setUserId(user.getUserId());
////                        List<UserKey> ukList = userKeyMapper.selectUserKeyList(ukSelect);
////                        if (!CollectionUtils.isEmpty(ukList)){
////                            UserKey uk = ukList.get(0);
////                            AccountInfo ac = new AccountInfo();
////                            ac.setCreateId(uk.getUserId());
////                            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(ac);
////                            for (AccountInfo accountInfo : accountInfoList) {
////                                BigDecimal b = new BigDecimal(100).add(new BigDecimal(uk.getRise()));
////                                BigDecimal newPrice = accountInfo.getPrice().multiply(b).divide(new BigDecimal
// (100));
////                                accountInfo.setNewPrice(newPrice);
////                                accountInfo.setRise(userKey.getRise());
////                                accountInfoMapper.updateAccountInfo(accountInfo);
////                            }
////                            uk.setRise(user.getRise());
////                            userKeyMapper.updateUserKey(uk);
////                        }
////                    }
//                }
//            }
//        });
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<SysUser> getUserByRoleCode(String roleKey) {
        String key = "getUserByRoleCode_" + roleKey;
        List<SysUser> result = redisCache.getCacheObject(key);
        if (CollectionUtils.isNotEmpty(result)) {
            return result;
        }
        SysRole role = roleMapper.selectRoleByRoleKey(roleKey);
        //查詢包含用户id
        result = roleMapper.selectUserByRoleId(role.getRoleId());

        redisCache.setCacheObject(key, result, 1, TimeUnit.MINUTES); // 缓存1分钟
        return result;
    }

    @Override
    public int offAccount(Long userId) {

        int i = accountInfoMapper.delBySuplyId(userId);

        return i;
    }

    @Override
    @Transactional
    public int addprice(SysUser user) {
        BigDecimal rise = new BigDecimal(user.getRise());
        BigDecimal divide = rise.divide(new BigDecimal(100)).add(new BigDecimal(1));

        accountInfoMapper.addprice(user.getUserId(), divide);
        int i = userMapper.updateUser(user);
        return i;
    }

    @Override
    public List<SysUser> getSupplyUser() {

        return userMapper.getSupplyUser();
    }

    @Override
    public SysUser selectUserByPhonenumber(String phoneNumber) {
        return userMapper.selectUserByPhonenumber(phoneNumber);
    }

    @Override
    public String selectTelById(Long userId) {

        String tel = userMapper.selectTelById(userId);
        return tel;

    }

    /**
     * 根据子域名查询用户
     *
     * @param host
     * @return
     */
    @Override
    public Long getUserByHost(String host) {

        Long businessId = userMapper.getUserByHost(host);
        return businessId;

    }
}
