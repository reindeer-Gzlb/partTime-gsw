package com.ruoyi.system.model.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.h5.Hreq;
import com.ruoyi.system.model.domain.AccountInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏账号Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Component
public interface AccountInfoMapper
{
    public List<AccountInfo> selectAccountInfoByMigrate(@Param("types")String[] types,@Param("createIds")String[] createIds);

    /**
     * 查询游戏账号
     *
     * @param id 游戏账号主键
     * @return 游戏账号
     */
    public AccountInfo selectAccountInfoById(Long id);
    public AccountInfo selectAccountInfoCorrespondjym(String correspondjym);

    /**
     * 查询是否已存在游戏账号
     * @param accountId
     * @return
     */
    public AccountInfo selectAccountInfoByAccountId(String accountId);

    /**
     * 查询游戏账号列表
     *
     * @param accountInfo 游戏账号
     * @return 游戏账号集合
     */
    public List<AccountInfo> selectAccountInfoList(AccountInfo accountInfo);

    /**
     * 查询回收客服列表
     * @param accountInfo
     * @return
     */
    public List<AccountInfo> selectRecoveryAccountInfoList(AccountInfo accountInfo);

    /**
     * 查询审核包赔列表
     * @param accountInfo
     * @return
     */
    public List<AccountInfo> selectAuditAccountInfoList(AccountInfo accountInfo);

    /**
     * 新增游戏账号
     *
     * @param accountInfo 游戏账号
     * @return 结果
     */
    public int insertAccountInfo(AccountInfo accountInfo);

    /**
     * 修改游戏账号
     *
     * @param accountInfo 游戏账号
     * @return 结果
     */
    public int updateAccountInfo(AccountInfo accountInfo);

    /**
     * 删除游戏账号
     *
     * @param id 游戏账号主键
     * @return 结果
     */
    public int deleteAccountInfoById(Long id);

    /**
     * 批量删除游戏账号
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountInfoByIds(Long[] ids);

    /**
     * 审核账号
     * @param ids
     * @return
     */
    int auditAccountInfoByIds(@Param("ids") Long[] ids,@Param("type") Integer type,
                              @Param("operType") String operType,
                              @Param("userId") Long userId,
                              @Param("buyId") String buyId,
                              @Param("remake") String remake,
                              @Param("realPrice") BigDecimal realPrice,
                              @Param("note") String note,
                              @Param("saleDate") Date saleDate,
                              @Param("offres") String offres,
                              @Param("offtime") Date offtime
    );


    List<AccountInfo> accountListLimit(Hreq hreq);

    Integer accountListLimitCount(Hreq hreq);

    void updateOdrByIds(@Param("odr") int odr,@Param("ids") List<Long> ids);

    void batchSave(@Param("accountInfos") List<AccountInfo> accountInfos);

    List<AccountInfo> selectByAccountList(@Param("accountList") List<String> accountList, @Param("retailLogin") Long retailLogin);

    List<String> byColums(@Param("colum") String colum,@Param("type") String type);

    void updatePrice(AccountInfo info);

    int delByCmdSuplyId(Long suplyId);

    List<AccountInfo> selectJymData(Long jimId);

    List<AccountInfo> slectPushAcc(AccountInfo accountInfo);

    List<Long> selectOffAcc(AccountInfo accParams);

    List<Long> selectByDel();

    Integer todayCount();

    Integer weekCount();

    Integer monthCount();

    Integer yearCount();

    Integer todaySum();

    Integer weekSum();

    Integer monthSum();

    Integer yearSum();

    List<Map<String, Object>> gameTypeFor();

    List<String> sevenDaysCount(String type);

    List<String> getGameTpes();

    List<Integer> yearCountGroup(@Param("type") String type);

    Integer stateCount(Integer state);

    List<String> yearDate();

    String salePrice(String type);

    String profitsPrice(String type);

    String saleCount(String type);

    List<String> sevenDayDate();

    void updateOdr(@Param("odr") int odr,@Param("userId") Long userId);

    int delBySuplyId(@Param("userId") Long userId);

    int addprice(@Param("userId")Long userId,@Param("divide") BigDecimal divide);

    List<Map<String,Object>> onGameType(@Param("userId") Long userId);

    List<SysUser> getSupplyUser();

    void hiddenAccount(@Param("userId")Long userId,@Param("isshow") String isshow);

    List<Map<String, Object>> gameUser();

    void deleteImg();

    void updateAccountInfoByCreate(@Param("userId")Long userId);

    List<AccountInfo> selectAccountInfo77img();

    List<AccountInfo> zhuanzhuanZid();

    List<Long> twoOutZz();

    List<Long> accIds(AccountInfo accountInfo);

    List<Long> bzon();

    List<AccountInfo> polish(AccountInfo accountInfo);


    List<AccountInfo> kjscheck();

    AccountInfo firstAccount(AccountInfo params);

    void deleteAccountInfo(AccountInfo params);

    List<AccountInfo> allAccount(AccountInfo params);

    List<AccountInfo> jiaoyimao();

    List<Map<String, Object>> userGameCount();

    List<Map<String, Object>> GameTypeCount();

    List<AccountInfo> selectList(AccountInfo accountInfo);

    List<Map<String,Object>> stateCountByUserId(Long userId);

    List<Map<String,Object>> buchang();
}
