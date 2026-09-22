package com.ruoyi.system.model.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.H5PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.h5.Hreq;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.SynchronDto;
import com.ruoyi.system.model.vo.DeleteAllVo;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 游戏账号Service接口
 *
 * @author ruoyi
 * @date 2022-05-04
 */
public interface IAccountInfoService
{
    /**
     * 查询游戏账号
     *
     * @param id 游戏账号主键
     * @return 游戏账号
     */
    public AccountInfo selectAccountInfoById(Long id);

    public List<Long> getAddPrice();
    public void bacthAcc(List<AccountInfo> accountInfos, List<SysUser> sysUserList);

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

    public void delCmdAccount(Long id);

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
     * 回收游戏账号
     * @param ids
     * @return
     */
    public int updateToBeRecycled(Long[] ids);

    /**
     * 操作审核包赔
     * @param accountInfo
     * @return
     */
    public int auditInfo(RecoveryAccountInfo accountInfo);

    /**
     * 操作回收游戏账号
     * @param accountInfo
     * @return
     */
    public int recoveryInfo(AccountInfo accountInfo);


    /**
     * 问题账号重新释放到资源池
     * @param accountInfo
     * @return
     */
    public int norecoveredInfo(RecoveryAccountInfo accountInfo);

    /**
     * 批量删除游戏账号
     *
     * @param ids 需要删除的游戏账号主键集合
     * @return 结果
     */
    public int deleteAccountInfoByIds(Long[] ids);

    /**
     * 删除游戏账号信息
     *
     * @param id 游戏账号主键
     * @return 结果
     */
    public int deleteAccountInfoById(Long id);

    /**
     * 批量上传
     * @param accountInfos
     * @param updateSupport
     * @param operName
     * @return
     */
    String importAccount(List<AccountInfo> accountInfos, boolean updateSupport, String operName);

    /**
     * 审核账号
     * @param ids
     * @return
     */
    int auditAccountInfoByIds(Long[] ids, Map<String,Object> params);


    H5PageDomain accountListLimit(Hreq hreq, HttpServletRequest request);

    public void sensitive(AccountInfo accountInfo);

    /**
     * 查询是否已存在游戏账号
     * @param accountId
     * @return
     */
    public AccountInfo selectAccountInfoByAccountId(String accountId);

    public void addLabel(AccountInfo accountInfo,Long userId);

    /**
     * api 批量导入接口
     * @param accountInfos
     * @param request
     */
    String batchSave(List<AccountInfo> accountInfos, HttpServletRequest request);


    public void update(AccountInfo accountInfo) throws Exception ;

    public void updateApi(AccountInfo accountInfo, HttpServletRequest request) throws Exception ;

    boolean undercarriage(AccountInfo accountInfo, HttpServletRequest request) throws Exception;



    List<Map<String,Object>> byColums(String colum,String type);

    /**
     * 一键更新
     * @param synchronDto
     */
    void synchronousAcount(SynchronDto synchronDto);

    List<String> allSale();

    List<String> getArea(String area);


    public String checkData(AccountInfo accountInfo);

    TableDataInfo cmdPoollist(AccountInfo accountInfo);

    List<JSONObject> supplyIdList();

    String addCmdAccount(Long[] ids);

    String bacthAddAccount(Long suplyId);

    String delAccount(@Param("suplyId") Long suplyId);

    List<InfoImg> getImage(AccountInfo accountInfo);

    void excuResult(String result,Long userId);

    List<Map<String,Object>> onGameType(Long userId);


    public void wyImgBacth(AccountInfo accountInfo);

    public BigDecimal addPrice(BigDecimal price,Long creatrId,List<SysUser>  supplyCustomer);

    int deleteAllAccount(DeleteAllVo params);

    void jymPush(Map<String,Object> params);

    int upAccount(Long id);

    void bacthAccRetail(List<AccountInfo> list, List<SysUser> supplyCustomer);
    void bacthAccRetaiById(List<AccountInfo> list, List<SysUser> supplyCustomer,Long id);

    boolean ifPolish();

    List<Map<String, Object>> userGameCount();

    List<Map<String, Object>> GameTypeCount();

    List<Map<String,Object>> typeCount(AccountInfo accountInfo);
}
