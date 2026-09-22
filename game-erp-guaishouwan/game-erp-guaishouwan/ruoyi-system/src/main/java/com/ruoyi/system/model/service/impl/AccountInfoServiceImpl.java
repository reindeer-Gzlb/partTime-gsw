package com.ruoyi.system.model.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.H5PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.inter.mapper.SelectPushMapper;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
import com.ruoyi.system.V2.inter.service.IOutRecService;
import com.ruoyi.system.V2.inter.service.ISalePartnerService;
import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.V2.operation.service.IUplodHistoryService;
import com.ruoyi.system.V2.order.mapper.AccountOrderMapper;
import com.ruoyi.system.V2.retail.mapper.RetailAccountMapper;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.h5.GameList;
import com.ruoyi.system.h5.Hreq;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.SynchronDto;
import com.ruoyi.system.model.mapper.*;
import com.ruoyi.system.model.service.*;
import com.ruoyi.system.model.vo.DeleteAllVo;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.outto.game.CommonApi;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 游戏账号Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Slf4j
@Service
public class AccountInfoServiceImpl implements IAccountInfoService {
    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private AccountOrderMapper accountOrderMapper;

    @Resource
    private SelectPushMapper selectPushMapper;


    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private InfoImgMapper infoImgMapper;

    @Autowired
    private IUplodHistoryService uplodHistoryService;

    @Autowired
    private IOperationHistoryService operationHistoryService;

    @Resource
    private SysDictDataMapper sysDictDataMapper;


    @Autowired
    private IUserKeyService userKeyService;

    @Autowired
    private IImportHistoryService importHistoryService;
    @Resource
    private RetailAccountMapper retailAccountMapper;

    @Resource
    private AccountPersonalDetailsMapper accountPersonalDetailsMapper;

    @Resource
    private RecoveryAccountInfoMapper recoveryAccountInfoMapper;

    @Autowired
    private IOutRecService outRecService;

    @Resource
    private AddPriceMapper addPriceMapper;

    @Autowired
    private IAccountLableService insertAccountLable;

    @Resource
    private FieldConfigMapper fieldConfigMapper;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private CommonApi commonApi;

    @Resource
    private CmdAccountMapper cmdAccountMapper;
    @Resource
    private FinanceMapper financeMapper;

    @Resource
    private ISysConfigService iSysConfigService;

    private static String genPushOn = "genPushOn";

    @Resource
    private ISalePartnerService salePartnerService;

    @Resource
    private SalePartnerMapper salePartnerMapper;

    @Resource
    private PolishMapper polishMapper;

    @Resource
    private SuprAuthMapper suprAuthMapper;

    @Autowired
    private IApiTaskPoolService apiTaskPoolService;

    @Value("${cmd.service.host}")
    private String cmdHost;

    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;

    @Autowired
    private ISysUserService userService;


    /**
     * 查询游戏账号
     *
     * @param id 游戏账号主键
     * @return 游戏账号
     */
    @Override
    public AccountInfo selectAccountInfoById(Long id) {
        AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
        InfoImg infoImg = new InfoImg();
        infoImg.setInfoId(accountInfo.getId());
        infoImg.setType("1");
        List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
        List<Map<String, Object>> imgMaps = new ArrayList<>();
        for (InfoImg img : infoImgs) {
            Map<String, Object> imgMap = new HashMap<>();
            if (!StringUtils.isNull(img.getFileName()) && !StringUtils.isNull(img.getImgUrl())) {
                imgMap.put("name", img.getFileName());
                imgMap.put("url", img.getImgUrl());
                imgMaps.add(imgMap);
            }
        }
        if (!CollectionUtils.isEmpty(imgMaps)) {
            accountInfo.setUrlArr(imgMaps);
        }
        infoImg.setType("2");
        List<InfoImg> video = infoImgMapper.selectInfoImgList(infoImg);
        if (!CollectionUtils.isEmpty(video)) {
            accountInfo.setVideoName(video.get(0).getFileName());
            accountInfo.setVideoUrl(video.get(0).getImgUrl());
        }
        return accountInfo;
    }

    @Override
    public List<Long> getAddPrice() {
        AddPrice addPrice = new AddPrice();
        addPrice.setIson("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);

        return addPrices.stream().map(price -> price.getSuperId()).collect(Collectors.toList());
    }


    @Override
    public void bacthAcc(List<AccountInfo> accountInfos, List<SysUser> sysUserList) {

        for (AccountInfo accountInfo : accountInfos) {
            BigDecimal bigDecimal = addPrice(accountInfo.getPrice(), accountInfo.getCreateId(), sysUserList);
            accountInfo.setNewPrice(bigDecimal);
        }

//        Long userId = SecurityUtils.getUserId();
//        AddPrice addPriceSelect = new AddPrice();
//        addPriceSelect.setCreateId(userId);
//        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPriceSelect);

//        for (AccountInfo accountInfo : accountInfos) {
//
//            accountInfo.setNewPrice(accountInfo.getPrice());
//
//            for (AddPrice addPrice : addPrices) {
//
//                if (Objects.nonNull(addPrice.getRise().toString())) {
//                    accountInfo.setRise(Integer.valueOf(addPrice.getRise().toString()));
//                }
//
//                if (Objects.equals("1", addPrice.getRoleType()) && (Objects.equals(accountInfo.getAccountType(), "2") || Objects.equals(accountInfo.getAccountType(), "1"))) {
//                    if (Objects.equals(accountInfo.getCreateId(), addPrice.getSuperId())) {
//                        Integer rise = addPrice.getRise();
//                        BigDecimal price = accountInfo.getPrice();
//                        BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
//                        add = add.divide(new BigDecimal(100));
//                        BigDecimal multiply = price.multiply(add);
//                        if (Objects.nonNull(multiply)) {
//                            accountInfo.setNewPrice(multiply.setScale(0, BigDecimal.ROUND_UP));
//                        } else {
//                            accountInfo.setNewPrice(price);
//                        }
//                    }
//                }
//
//            }
//        }
    }
    /**
     * 查询游戏账号列表
     *
     * @param accountInfo 游戏账号
     * @return 游戏账号
     */
    @Override
    public List<AccountInfo> selectAccountInfoList(AccountInfo accountInfo) {
        //超时时间数据
        if (null != accountInfo.getTimeout()) {
            String addDayTime = DateUtils.getAddDayTime(-accountInfo.getTimeout());
            accountInfo.setAddDayTime(addDayTime);
        }
        if (Objects.nonNull(accountInfo.getLoginId())) {
            accountInfo.setLoginId(null);
        } else {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            accountInfo.setLoginId(loginUser.getUserId());
        }

        /**
         * 排序
         */
        if (!StringUtils.isNull(accountInfo.getSort())) {
            String sort = accountInfo.getSort().replace("ending", "");
            accountInfo.setSort(sort);
        }
        if (!StringUtils.isNull(accountInfo.getSortColumn())) {
            accountInfo.setSortColumn(humpToLine(accountInfo.getSortColumn()));
        }
        String[] desArr = {};
        if (!StringUtils.isNull(accountInfo.getDesQur())) {
            desArr = accountInfo.getDesQur().split(" ");
            accountInfo.setDesArr(Arrays.asList(desArr));
        }
        if (!StringUtils.isEmpty(accountInfo.getAccountType())) {
            String[] split = accountInfo.getAccountType().split(",");
            accountInfo.setAccpuntTypes(Arrays.asList(split));
            accountInfo.setAccountType(null);
        }

        List<String> sensitive = sensitive();

        List<AccountInfo> accountInfos = accountInfoMapper.selectAccountInfoList(accountInfo);
        if (!CollectionUtils.isEmpty(accountInfos)) {

            for (AccountInfo acc : accountInfos) {
                sensitive(acc, sensitive);
                Integer saleState = acc.getSaleState();
                String info = SalerStatus.getInfo(saleState);
                acc.setSaleStateName(info);
                if (Objects.nonNull(acc.getOrderNo())) {
                    acc.setGameName(acc.getOrderNo());
                }
                if (Objects.nonNull(acc.getCmdSupplyId())) {
                    acc.setSupplyName(acc.getCmdSupplyName());
                }

                for (String skin : desArr) {
                    String des = acc.getDes();
                    if (des.contains(skin)) {
                        des = des.replace(skin, "<span style='color:red'>" + skin + "</span>");
                        accountInfo.setDes(des);
                    }
                }
                if (StringUtils.isNotEmpty(accountInfo.getDes())) {
                    String des = acc.getDes();
                    if (des.contains(accountInfo.getDes())) {
                        des = des.replace(accountInfo.getDes(), "<span style='color:red'>" + accountInfo.getDes() + "</span>");
                        accountInfo.setDes(des);
                    }
                }


//            if (null != acc.getUpdateId()) {
//                SysUser sysUser = sysUserMapper.selectUserById(acc.getUpdateId());
//                if (null != sysUser) {
//                    acc.setUpdateName(sysUser.getNickName());
//                }
//            }
//            if (null != acc.getSupplyId()) {
//                SysUser supperUser = sysUserMapper.selectUserById(acc.getSupplyId());
//                if (null != supperUser) {
//                    acc.setSupplyName(supperUser.getNickName());
//                }
//            }
//
//            if (null != acc.getAuditId()) {
//                SysUser auditUser = sysUserMapper.selectUserById(acc.getAuditId());
//                if (null != auditUser) {
//                    acc.setAuditName(auditUser.getNickName());
//                }
//            }
//            if (null != acc.getSalesId()) {
//                SysUser saleUser = sysUserMapper.selectUserById(acc.getSalesId());
//                if (null != saleUser) {
//                    acc.setSalesName(saleUser.getNickName());
//                }
//            }
                if (0 == acc.getSaleState()) {
                    acc.setAuditState("待审核");
                }
                if (1 == acc.getSaleState()) {
                    acc.setAuditState("已审核");
                }

                if (Objects.isNull(acc.getNewPrice())) {
                    acc.setNewPrice(acc.getPrice());
                }

//            AccountLable accountLable = new AccountLable();
//            accountLable.setInfoId(acc.getId());
//            List<AccountLable> accountLables = accountLableMapper.selectAccountLableList(accountLable);
                if (!Objects.isNull(acc.getBid())) {
                    acc.setLable(1);
                } else {
                    acc.setLable(0);
                }

            }

//            accountInfos.stream().forEach(acc -> {
//                sensitive(acc, sensitive);
//                Integer saleState = acc.getSaleState();
//                String info = SalerStatus.getInfo(saleState);
//                acc.setSaleStateName(info);
//                if (Objects.nonNull(acc.getOrderNo())) {
//                    acc.setGameName(acc.getOrderNo());
//                }
//                if (Objects.nonNull(acc.getCmdSupplyId())) {
//                    acc.setSupplyName(acc.getCmdSupplyName());
//                }
//
//                for (String skin : desArr) {
//                    String des = accountInfo.getDes();
//                    if (des.contains(skin)) {
//                        des = des.replace(skin, "<span style='color:red'>" + skin + "</span>");
//                        accountInfo.setDes(des);
//                    }
//                }
//                if (StringUtils.isNotEmpty(hreq.getZDYGJZ())) {
//                    String des = accountInfo.getDes();
//                    if (des.contains(hreq.getZDYGJZ())) {
//                        des = des.replace(hreq.getZDYGJZ(), "<span style='color:red'>" + hreq.getZDYGJZ() + "</span>");
//                        accountInfo.setDes(des);
//                    }
//                }
//
//
////            if (null != acc.getUpdateId()) {
////                SysUser sysUser = sysUserMapper.selectUserById(acc.getUpdateId());
////                if (null != sysUser) {
////                    acc.setUpdateName(sysUser.getNickName());
////                }
////            }
////            if (null != acc.getSupplyId()) {
////                SysUser supperUser = sysUserMapper.selectUserById(acc.getSupplyId());
////                if (null != supperUser) {
////                    acc.setSupplyName(supperUser.getNickName());
////                }
////            }
////
////            if (null != acc.getAuditId()) {
////                SysUser auditUser = sysUserMapper.selectUserById(acc.getAuditId());
////                if (null != auditUser) {
////                    acc.setAuditName(auditUser.getNickName());
////                }
////            }
////            if (null != acc.getSalesId()) {
////                SysUser saleUser = sysUserMapper.selectUserById(acc.getSalesId());
////                if (null != saleUser) {
////                    acc.setSalesName(saleUser.getNickName());
////                }
////            }
//                if (0 == acc.getSaleState()) {
//                    acc.setAuditState("待审核");
//                }
//                if (1 == acc.getSaleState()) {
//                    acc.setAuditState("已审核");
//                }
//
//                if (Objects.isNull(acc.getNewPrice())) {
//                    acc.setNewPrice(acc.getPrice());
//                }
//
////            AccountLable accountLable = new AccountLable();
////            accountLable.setInfoId(acc.getId());
////            List<AccountLable> accountLables = accountLableMapper.selectAccountLableList(accountLable);
//                if (!Objects.isNull(acc.getBid())) {
//                    acc.setLable(1);
//                } else {
//                    acc.setLable(0);
//                }
//
//            });
        }
        return accountInfos;
    }


    @Override
    public List<AccountInfo> selectRecoveryAccountInfoList(AccountInfo accountInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountInfo.setLoginId(loginUser.getUserId());
        /**
         * 排序
         */

        if (!StringUtils.isNull(accountInfo.getSort())) {
            String sort = accountInfo.getSort().replace("ending", "");
            accountInfo.setSort(sort);
        }
        if (!StringUtils.isNull(accountInfo.getSortColumn())) {
            accountInfo.setSortColumn(humpToLine(accountInfo.getSortColumn()));
        }
        List<AccountInfo> accountInfos = accountInfoMapper.selectRecoveryAccountInfoList(accountInfo);
        return accountInfos;
    }

    @Override
    public void delCmdAccount(Long id) {
        cmdAccountMapper.deleteCmdAccountByAccountId(id);
    }


    @Override
    public List<AccountInfo> selectAuditAccountInfoList(AccountInfo accountInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountInfo.setLoginId(loginUser.getUserId());
        /**
         * 排序
         */

        if (!StringUtils.isNull(accountInfo.getSort())) {
            String sort = accountInfo.getSort().replace("ending", "");
            accountInfo.setSort(sort);
        }
        if (!StringUtils.isNull(accountInfo.getSortColumn())) {
            accountInfo.setSortColumn(humpToLine(accountInfo.getSortColumn()));
        }
        List<AccountInfo> accountInfos = accountInfoMapper.selectAuditAccountInfoList(accountInfo);
        return accountInfos;
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");


    private String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }

    /**
     * 新增游戏账号
     *
     * @param accountInfo 游戏账号
     * @return 结果
     */
    @Override
    public int insertAccountInfo(AccountInfo accountInfo) {
        accountInfo.setCreateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        int odr = 0;
        if (null != sysUser) {
            odr = sysUser.getOdr();
        }

        if (!StringUtils.isNull(accountInfo.getVideoUrl())) {
            addVideo(accountInfo.getVideoUrl(), accountInfo.getVideoName(), accountInfo.getId());
        }

        //校验数据
        String checkData = checkData(accountInfo);
        if (Objects.nonNull(checkData)) {
            throw new ServiceException(checkData);
        }

        accountInfo.setAccountType(Constants.STR_1);
        accountInfo.setOdr(odr);
        accountInfo.setAccType(Constants.STR_0);
        accountInfo.setSaleState(Constants.INT_1);
        //通过账号，是否已经存在此游戏账号
        AccountInfo info = new AccountInfo();
        info.setExtractAccount(accountInfo.getExtractAccount());
        List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(info);
        //初始账号待审核状态
        accountInfo.setSupplyId(userId);
        int i = 0;
        if (CollectionUtils.isEmpty(accountInfoList)) {
            accountInfo.setCreateId(userId);
            accountInfo.setCreateTime(new Date());
            i = accountInfoMapper.insertAccountInfo(accountInfo);
            if (!CollectionUtils.isEmpty(accountInfo.getUrlArr())) {
                String s = bathAddImg(accountInfo.getUrlArr(), accountInfo.getId());
                accountInfo.setImgList(s);
            }
            //同步财务
            if(i>0){
                pushFinance(accountInfo);
            }
        } else {
            throw new GlobalException("该账号已存在");
        }
        return i;
    }

    @Transactional
    public String bathAddImg(List<Map<String, Object>> imgs, Long infoId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        infoImgMapper.deleteByInfoId(infoId, Constants.STR_1);
        List<String> result = new ArrayList<>();
        List<InfoImg> infoImgList = new ArrayList<>();

        for (Map img : imgs) {
            String filePath = MapUtils.getString(img, "url");
            result.add(filePath);
            if (!StringUtils.isEmpty(filePath)) {
                InfoImg infoImg = new InfoImg();
                infoImg.setInfoId(infoId);
                String fileName = MapUtils.getString(img, "name");
                infoImg.setImgUrl(filePath);
                infoImg.setCreateId(loginUser.getUserId());
                infoImg.setFileName(fileName);
                infoImg.setType(Constants.STR_1);
                infoImgList.add(infoImg);
            }
        }
        infoImgMapper.insertBacthInfoImg(infoImgList);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        } else {
            String join = StringUtils.join(result.toArray(), ",");
            return join;
        }

    }

    /**
     * 修改游戏账号
     *
     * @param accountInfo 游戏账号
     * @return 结果
     */
    @Override
    @Transactional
    public int updateAccountInfo(AccountInfo accountInfo) {
        accountInfo.setUpdateTime(DateUtils.getNowDate());
        if (!CollectionUtils.isEmpty(accountInfo.getUrlArr())) {
            String s = bathAddImg(accountInfo.getUrlArr(), accountInfo.getId());
            accountInfo.setImgList(s);
        } else {
            infoImgMapper.deleteByInfoId(accountInfo.getId(), Constants.STR_1);
        }
        if (!StringUtils.isNull(accountInfo.getVideoUrl())) {
            addVideo(accountInfo.getVideoUrl(), accountInfo.getVideoName(), accountInfo.getId());
        }
        recOperHistory(accountInfo);

        Long createId = accountInfo.getCreateId();
        AddPrice addPrice = new AddPrice();
        addPrice.setSuperId(createId);
        addPrice.setPriceType("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);
        if (!CollectionUtils.isEmpty(addPrices)) {
            AddPrice ap = addPrices.get(0);
            BigDecimal b = new BigDecimal(100).add(new BigDecimal(ap.getRise()));
            BigDecimal divide = b.divide(new BigDecimal(100));
            BigDecimal newPrice = accountInfo.getPrice().multiply(divide);
            accountInfo.setNewPrice(newPrice);
            accountInfo.setRise(ap.getRise());
        } else {
            accountInfo.setNewPrice(accountInfo.getPrice());
            accountInfo.setRise(0);
        }
        int i = accountInfoMapper.updateAccountInfo(accountInfo);
        uptOutRec(accountInfo);
//        cmdApi.edit(accountInfo);
        return i;
    }


    /**
     * 先判断此账号是否新增
     *
     * @param info
     */
    private void uptOutRec(AccountInfo info) {

        String interType = info.getInterType();
        Long userId = SecurityUtils.getUserId();
        if (Objects.nonNull(interType)) {
            if (Objects.equals(Constant.edit, interType)) {
//                outApi.edit(info, taskPool.getId());
                apiTaskPoolService.insertBacth(info.getId(), null, Constant.edit, info.getCreateId());
            } else {
            }
        } else {
//            outApi.edit(info, taskPool.getId());
            apiTaskPoolService.insertBacth(info.getId(), null, Constant.edit, info.getCreateId());
        }

    }

    /**
     * 回收游戏账号
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int updateToBeRecycled(Long[] ids) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        int i = 0;
        for (Long id : ids) {
            RecoveryAccountInfo accountInfo = new RecoveryAccountInfo();
            accountInfo.setId(id);
            //标记为待回收状态
            accountInfo.setRecoveryState(0);
            //设置回收客服id
            accountInfo.setRecoveryId(loginUser.getUserId());
            //设置回收人
            accountInfo.setRecoveryName(loginUser.getUsername());
            accountInfo.setUpdateId(loginUser.getUserId());
            accountInfo.setUpdateBy(loginUser.getUsername());
            accountInfo.setUpdateTime(new Date());
            recoveryAccountInfoMapper.updateRecoveryAccountInfo(accountInfo);
            i = i + 1;
        }
        return i;
    }

    @Override
    @Transactional
    public int auditInfo(RecoveryAccountInfo accountInfo) {
        OperationHistory operationHistory = new OperationHistory();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        operationHistory.setOperator(loginUser.getUsername());
        operationHistory.setAccountId(accountInfo.getId());
        operationHistory.setReason(loginUser.getUsername() + accountInfo.getAuditNote());
        operationHistory.setStatus("审核包赔");
        operationHistory.setOperatTime(new Date());
        operationHistoryService.insertOperationHistory(operationHistory);
        //设置审核人id
        accountInfo.setVerifyId(loginUser.getUserId());
        //当前状态设置为审核包赔状态
        accountInfo.setAuditFlag(0);
        accountInfo.setVerifyName(loginUser.getUsername());
        accountInfo.setUpdateId(loginUser.getUserId());
        accountInfo.setUpdateBy(loginUser.getUsername());
        accountInfo.setUpdateTime(new Date());
        accountInfo.setAuditTime(new Date());
        return recoveryAccountInfoMapper.updateRecoveryAccountInfo(accountInfo);
    }

    @Override
    public int recoveryInfo(AccountInfo accountInfo) {
        return accountInfoMapper.updateAccountInfo(accountInfo);
    }

    @Transactional
    @Override
    public int norecoveredInfo(RecoveryAccountInfo accountInfo) {
        //同时将客户填写的个人信息和绑定的图片删除
        accountPersonalDetailsMapper.deleteAccountPersonalDetailsById(accountInfo.getId());
        //删除个人信息绑定的图片
        infoImgMapper.deleteByInfoId(accountInfo.getId(), "4");
        return recoveryAccountInfoMapper.updateRecoveryAccountInfo(accountInfo);
    }

    private void addVideo(String videoUrl, String videoName, Long infoId) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        infoImgMapper.deleteByInfoId(infoId, Constants.STR_2);
        if (!StringUtils.isEmpty(videoUrl)) {
            InfoImg infoImg = new InfoImg();
            infoImg.setInfoId(infoId);
            infoImg.setImgUrl(videoUrl);
            infoImg.setCreateId(loginUser.getUserId());
            infoImg.setFileName(videoName);
            infoImg.setType(Constants.STR_2);
            infoImgMapper.insertInfoImg(infoImg);
        }
    }

    /**
     * 编辑，记录数据变动
     *
     * @param accountInfo
     */
    private void recOperHistory(AccountInfo accountInfo) {
        if (null != accountInfo.getId()) {
            AccountInfo info = accountInfoMapper.selectAccountInfoById(accountInfo.getId());
            if (null != info) {
                StringBuffer st = new StringBuffer();
                if (!Objects.equals(info.getType(), accountInfo.getType())) {
                    st.append("\n" + "游戏类型：" + info.getType() + "---" + accountInfo.getType() + '\n');
                }
                if (!Objects.equals(info.getRegional(), accountInfo.getRegional())) {
                    st.append("\n" + "游戏大区：" + info.getRegional() + "---" + accountInfo.getRegional() + '\n');
                }
                if (!Objects.equals(info.getLevel(), accountInfo.getLevel())) {
                    st.append("\n" + "游戏level：" + info.getLevel() + "---" + accountInfo.getLevel() + '\n');
                }
                if (!Objects.equals(info.getHeroes(), accountInfo.getHeroes())) {
                    st.append("\n" + "英雄数量：" + info.getHeroes() + "---" + accountInfo.getHeroes() + '\n');
                }
                if (!Objects.equals(info.getSkins(), accountInfo.getSkins())) {
                    st.append("\n" + "皮肤数量：" + info.getSkins() + "---" + accountInfo.getSkins() + '\n');
                }
                if (!Objects.equals(info.getSuit(), accountInfo.getSuit())) {
                    st.append("\n" + "套装数量：" + info.getSuit() + "---" + accountInfo.getSuit() + '\n');
                }
                if (!Objects.equals(info.getGuns(), accountInfo.getGuns())) {
                    st.append("\n" + "枪皮数量：" + info.getGuns() + "---" + accountInfo.getGuns() + '\n');
                }
                if (!Objects.equals(info.getDes(), accountInfo.getDes())) {
                    st.append("\n" + "简介：" + info.getDes() + "---" + accountInfo.getDes() + '\n');
                }
                if (!Objects.equals(info.getExtractAccount(), accountInfo.getExtractAccount())) {
                    st.append("\n" + "账号：" + info.getExtractAccount() + "---" + accountInfo.getExtractAccount() + '\n');
                }
                if (!Objects.equals(info.getExtractPwd(), accountInfo.getExtractPwd())) {
                    st.append("\n" + "密码：" + info.getExtractPwd() + "---" + accountInfo.getExtractPwd() + '\n');
                }
                if (Objects.equals(info.getCostPrice(), accountInfo.getCostPrice())) {
                    st.append("\n" + "成本价格：" + info.getCostPrice() + "---" + accountInfo.getCostPrice() + '\n');
                }
                if (!Objects.equals(accountInfo.getPrice(), info.getPrice())) {
                    st.append("\n" + "出售价格：" + info.getCostPrice() + "---" + accountInfo.getCostPrice() + '\n');
                }
                if (!Objects.equals(info.getGameName(), accountInfo.getGameName())) {
                    st.append("\n" + "游戏名称：" + info.getGameName() + "---" + accountInfo.getGameName() + '\n');
                }
                if (!Objects.equals(info.getExtractTel(), accountInfo.getExtractTel())) {
                    st.append("\n" + "提取手机号：" + info.getExtractTel() + "---" + accountInfo.getExtractTel() + '\n');
                }
                log.info("st = {}", st);
                if (!StringUtils.isNull(st.toString())) {
                    OperationHistory operationHistory = new OperationHistory();
                    operationHistory.setReason(st.toString());
                    LoginUser loginUser = SecurityUtils.getLoginUser();
                    operationHistory.setOperator(loginUser.getUsername());
                    operationHistory.setAccountId(accountInfo.getId());
                    operationHistory.setStatus("更新");
                    operationHistory.setCreateTime(new Date());
                    operationHistory.setOperatTime(new Date());
                    operationHistoryService.insertOperationHistory(operationHistory);

                }
            }
        }
    }

    /**
     * 批量删除游戏账号
     *
     * @param ids 需要删除的游戏账号主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteAccountInfoByIds(Long[] ids) {

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setIds(Arrays.asList(ids));
        accountInfo.setSaleState(1);

//        outApi.del(ids, null, 0, null);
        Arrays.asList(ids).stream().forEach(id -> {
            asyncService.excuTaskPool(id, null, Constant.del, accountInfo.getCreateId(), 1l);
        });

        return accountInfoMapper.deleteAccountInfoByIds(ids);
    }

    /**
     * 删除游戏账号信息
     *
     * @param id 游戏账号主键
     * @return 结果
     */
    @Override
    public int deleteAccountInfoById(Long id) {
        return accountInfoMapper.deleteAccountInfoById(id);
    }

    @Override
    @Transactional
    public String importAccount(List<AccountInfo> accountInfos, boolean updateSupport, String operName) {

        if (StringUtils.isNull(accountInfos) || accountInfos.size() == 0) {
            throw new ServiceException("导入游戏账号数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        successMsg.append("<div style='line-height: 40px;height: 700px;overflow: auto'>");
        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUserId());
        int odr = 0;
        if (null != sysUser) {
            odr = sysUser.getOdr();
        }
        List<Long> ids = new ArrayList<>();


        AddPrice addPriceParam = new AddPrice();
        addPriceParam.setSuperId(loginUser.getUserId());
        addPriceParam.setPriceType("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPriceParam);

        for (AccountInfo accountInfo : accountInfos) {
            try {


                //批量加价
                if (!CollectionUtils.isEmpty(addPrices)) {
                    AddPrice addPrice = addPrices.get(0);
                    Integer rise = addPrice.getRise();

                    if (null == addPrice.getRise()) rise = 0;
                    BigDecimal b = new BigDecimal(0);
                    if (Objects.equals("1", addPrice.getType())) {
                        b = new BigDecimal(100).add(new BigDecimal(rise));
                    } else {
                        b = new BigDecimal(100).subtract(new BigDecimal(rise));
                    }
                    BigDecimal newPrice = accountInfo.getPrice().multiply(b).divide(new BigDecimal(100));
                    accountInfo.setNewPrice(newPrice);
                    accountInfo.setRise(rise);
                }
                //校验数据
                String checkData = checkData(accountInfo);
                if (Objects.nonNull(checkData)) {
                    throw new ServiceException(checkData);
                }

                accountInfo.setAccountType(Constants.STR_1);
                accountInfo.setOdr(odr);
                accountInfo.setAccType(Constants.STR_0);
                accountInfo.setSaleState(Constants.INT_1);
                //通过账号，是否已经存在此游戏账号
                AccountInfo info = new AccountInfo();
                info.setExtractAccount(accountInfo.getExtractAccount());
                info.setSaleState(SalerStatus.unsold.getCode());
                List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(info);
                //初始账号待审核状态
                accountInfo.setSupplyId(loginUser.getUserId());
                if (CollectionUtils.isEmpty(accountInfoList)) {
                    accountInfo.setCreateId(loginUser.getUserId());
                    accountInfo.setCreateTime(new Date());
                    int i = accountInfoMapper.insertAccountInfo(accountInfo);
                    //推送财务支出
                    if(i>0){
                        pushFinance(accountInfo);
                    }
//                    addLabel(accountInfo, loginUser.getUserId());
//                    cmdApi.add(accountInfo);
                    //自动推送
//                    String config = iSysConfigService.selectConfigByKey(genPushOn);
//                    if (Boolean.valueOf(config)) {
//                        SalePartner salePartner = commonApi.get(LCConstant.saleNameYDD, loginUser.getUserId());
//                        if (Objects.nonNull(salePartner)) {
//                            Long id[] = {accountInfo.getId()};
////                            outApi.add(id);
//                            ydddApi.addGame(accountInfo, loginUser.getUserId(), salePartner);
//                        }
//                    }
                    ids.add(accountInfo.getId());
                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + accountInfo.getId() + " 导入成功");
                } else if (false) {
                    AccountInfo accountInfo1 = accountInfoList.get(0);
                    accountInfo.setId(accountInfo1.getId());
                    accountInfo.setUpdateId(loginUser.getUserId());
                    accountInfo.setUpdateTime(new Date());
                    successNum++;
                    accountInfoMapper.updateAccountInfo(accountInfo);
//                    successMsg.append("<br/>" + successNum + "、账号 " + accountInfo.getId() + " 更新成功");
                } else {
                    failureNum++;
                    String extractAccount = accountInfo.getExtractAccount();
                    if (StringUtils.isNull(extractAccount)) {
                        failureMsg.append("<br/>" + failureNum + "、手机号为" + accountInfo.getExtractTel() + "的账号已存在");
                    } else {
                        failureMsg.append("<br/>" + failureNum + "、账号 " + accountInfo.getExtractAccount() + " 已存在");
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + accountInfo.getExtractAccount() + " 导入失败：";

                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        //推送数据
//        outApi.add(ids.toArray(new Long[ids.size()]));

        successMsg.insert(0, "数据导入成功" + successNum + "条，失败" + failureNum + "条" + "<br/>");
        successMsg.append("导入失败数据如下:" + "<br/>");
        successMsg.append(failureMsg.toString() + "</div>");

        //导入统计
        UplodHistory uplodHistory = new UplodHistory();
        uplodHistory.setNum(successNum);
        uplodHistory.setCreateTime(new Date());
        uplodHistory.setCreateId(loginUser.getUserId());
        uplodHistoryService.insertUplodHistory(uplodHistory);

        return successMsg.toString();
    }

    @Override
    public int auditAccountInfoByIds(Long[] ids, Map<String, Object> params) {

        String operation = MapUtils.getString(params, "operation");
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Arrays.stream(ids).forEach(id -> {
            OperationHistory operationHistory = new OperationHistory();
            operationHistory.setAccountId(id);
            operationHistory.setOperator(loginUser.getUsername());
            operationHistory.setOperatTime(new Date());
            operationHistory.setStatus(operation);
            operationHistory.setReason(String.valueOf(id));
            operationHistoryService.insertOperationHistory(operationHistory);
        });

        Integer type = MapUtils.getInteger(params, "type");

        String operType = null;
        String note = null;
        String remake = null;
        String buyId = null;
        BigDecimal realPrice = null;
        String offres = null;
        Date offtime = null;
        if (SalerStatus.shelves.getCode() == type) {

//            this.accountInfoMapper.auditAccountInfoByIds(ids)
//
//            AjaxResult ajaxResult = AjaxResult.error(annotation.message());
//            ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));

        }
        if ("审核".equals(operation)) {
            //修改审核人id
            operType = "auit";
            //推送数据
//            outApi.add(ids);
        } else if ("提取密码".equals(operation)) {
            //修改销售客服id
            operType = "sale";
        } else if ("取消售中".equals(operation)) {
            //置空销售客服
            operType = "saleNull";
            //添加备注
            note = MapUtils.getString(params, "note");
        } else if ("转为问题号".equals(operation)) {
            operType = "problem";
            note = MapUtils.getString(params, "note");
            //删除推送数据
//            outApi.del(ids, null,1, taskPool.getId());
            for (Long id : ids) {
                AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
                apiTaskPoolService.insertBacth(id, null, "del", accountInfo.getCreateId());
            }

        } else if ("提取手机号".equals(operation)) {
            //置
            operType = "extractTel";
            remake = MapUtils.getString(params, "remake");
            buyId = MapUtils.getString(params, "buyId");
            String realPrice1 = MapUtils.getString(params, "realPrice");
            try {
                if (!StringUtils.isEmpty(realPrice1)) {
                    realPrice = new BigDecimal(realPrice1);
                }
            } catch (Exception e) {
                return 0;
            }
            //删除推送数据
//            outApi.del(ids, null,1, taskPool.getId());
            for (Long id : ids) {
                AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
                apiTaskPoolService.insertBacth(id, null, "del", accountInfo.getCreateId());
            }

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setIds(Arrays.asList(ids));
            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
            List<Long> cmdIds = new ArrayList<>();
            for (AccountInfo info : accountInfoList) {
                if (Objects.equals(info.getAccountType(), "3")) {
                    cmdIds.add(info.getId());
                    int i = accountInfoMapper.auditAccountInfoByIds(ids, 9, operType,
                            loginUser.getUserId(),
                            buyId,
                            remake,
                            realPrice,
                            note, new Date(), offres, offtime);
                    if (0 != i) {
                        ArrayRemove(ids, info.getId());
                    }
                }
            }


        } else if ("转自售".equals(operation)) {
            //删除推送数据
//            outApi.del(ids, null,1, taskPool.getId());
            for (Long id : ids) {
                AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
                apiTaskPoolService.insertBacth(id, null, "del", accountInfo.getCreateId());
            }
            offres = MapUtils.getString(params, "offres");
            offtime = new Date();
        } else {
            //更新人修改
            operType = "other";
        }
        if (!CollectionUtils.isEmpty(Arrays.asList(ids))) {
            accountInfoMapper.auditAccountInfoByIds(ids, type, operType,
                    loginUser.getUserId(),
                    buyId,
                    remake,
                    realPrice,
                    note, new Date(), offres, offtime);
        }
        return 1;
    }

    public void ArrayRemove(Object[] os, Object o) {
        int index = 0;
        for (index = 0; index < os.length; index++) {
            if (os[index].equals(o)) {
                break;
            }
        }
        int movenum = os.length - index - 1;
        System.arraycopy(os, index + 1, os, index, movenum);
        os[os.length - 1] = null;
    }

    @Override
    public H5PageDomain accountListLimit(Hreq hreq, HttpServletRequest request) {
        Integer oldPage = hreq.getPage();
        int page = (hreq.getPage() - 1) * hreq.getRows();
        hreq.setPage(page);
        List<GameList> gameLists = new ArrayList<>();
        Integer count = 0;

        H5PageDomain h5PageDomain = new H5PageDomain();


        String jjgjz = hreq.getJJGJZ();
        if (Objects.nonNull(jjgjz)) {
            String[] split = jjgjz.split("-");
            if (split.length > 1) {
                hreq.setCSJGMin(split[0]);
                hreq.setCSJGMax(split[1]);
                hreq.setJJGJZ(null);
            }
        }

        if (null != hreq.getId()) {
            RetailAddPrice retailAddPrice = new RetailAddPrice();
            retailAddPrice.setCreateId(Long.parseLong(hreq.getId().toString()));
            List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);
            if (CollectionUtils.isEmpty(retailAddPrices)) {
                return h5PageDomain;
            }
            List<String> collect = retailAddPrices.stream().map(re -> re.getSuperId()).collect(Collectors.toList());
            hreq.setCreateIds(collect);
            List<String> cmdids = retailAddPrices.stream().map(re -> re.getCmdsuper()).collect(Collectors.toList());
            hreq.setCmdSupplyIds(cmdids);
            hreq.setRetailAddPrices(retailAddPrices);
        } else {
            String serverName = request.getServerName();
            if (serverName.contains("rt.")) {
                return h5PageDomain;
            }
            AddPrice addPrice = new AddPrice();
            List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);
            hreq.setAddPriceList(addPrices);
        }
        String zdygjz = hreq.getZDYGJZ();
        if (StringUtils.isNotEmpty(zdygjz)) {
            String[] s = zdygjz.split(" ");
            List<String> strings = Arrays.asList(s);
            if (s.length > 1) {
                hreq.setGjzList(s);
                hreq.setZDYGJZ(null);
            }
        }


        gameLists = getProprietary(hreq, request);
        count = accountInfoMapper.accountListLimitCount(hreq);


        h5PageDomain.setPage(oldPage);
        h5PageDomain.setPageSize(hreq.getRows());
        h5PageDomain.setRows(gameLists);

        int totalPage = (int) Math.ceil(count / hreq.getRows()) + 1;
        h5PageDomain.setTotal(totalPage);
        h5PageDomain.setRecords(count);
        return h5PageDomain;
    }

    @Override
    public void sensitive(AccountInfo accountInfo) {

        List<String> sensitive = sensitive();
        for (String vocabulary : sensitive) {
            String orderNo = accountInfo.getOrderNo();
            if (Objects.nonNull(orderNo)) {
                orderNo = orderNo.replace(vocabulary, "");
                accountInfo.setOrderNo(orderNo);
            }

            String gameName = accountInfo.getGameName();
            if (Objects.nonNull(gameName)) {
                gameName = gameName.replace(vocabulary, "");
                accountInfo.setGameName(gameName);
            }

            String des = accountInfo.getDes();
            if (Objects.nonNull(des)) {
                des = des.replace(vocabulary, "");
                accountInfo.setDes(des);
            }
        }
    }


    private List<GameList> getRetail(Hreq hreq) {

        AddPrice addPrice = new AddPrice();
        addPrice.setPriceType("2");
        addPrice.setCreateId(Long.valueOf(hreq.getId()));
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);

        List<RetailAccount> retailAccountList = retailAccountMapper.retailAccountList(hreq);
        List<Long> ids = retailAccountList.stream().map(retail -> retail.getAccountId()).collect(Collectors.toList());
        AccountInfo infoPrams = new AccountInfo();
        infoPrams.setIds(ids);
        List<AccountInfo> accs = accountInfoMapper.selectAccountInfoList(infoPrams);
        List<GameList> gameLists = new ArrayList<>();
        int i = 1;
        for (RetailAccount accountInfo : retailAccountList) {
            GameList gameList = new GameList();

            InfoImg infoImg = new InfoImg();
            infoImg.setInfoId(accountInfo.getAccountId());
            infoImg.setType(Constants.STR_1);
            String titleImg = accountInfo.getTitleImg();
            if (!StringUtils.isNull(titleImg)) {
                gameList.setTitleImg(titleImg);
            } else {
                List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
                if (!CollectionUtils.isEmpty(infoImgs)) {
                    gameList.setTitleImg(infoImgs.get(0).getImgUrl());
                }
            }
            gameList.setRownum(i);
            i++;
            Long accountId = accountInfo.getAccountId();
            gameList.setCsjg(accountInfo.getPrice());

            gameList.setZhid(accountInfo.getAccountId());
            gameList.setYxmz(accountInfo.getGameName());
            gameList.setYxdq(accountInfo.getRegional());
            gameList.setDw(accountInfo.getLevel());
            gameList.setYx(accountInfo.getHeroes());
            gameList.setPf(accountInfo.getSkins());
            gameList.setJj(accountInfo.getDes());
            gameList.setSuit(accountInfo.getSuit());
            gameList.setGunskin(accountInfo.getGuns());
            gameList.setYdsj(accountInfo.getCampId());
            gameLists.add(gameList);
        }
        return gameLists;
    }

    private List<GameList> getProprietary(Hreq hreq, HttpServletRequest request) {

        List<AccountInfo> accountInfoList = accountInfoMapper.accountListLimit(hreq);

        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");

//        this.bacthAccRetaiById(accountInfoList, supplyCustomer,Long.parseLong(hreq.getId().toString()));
        if (Objects.nonNull(hreq.getId())) {
            this.bacthAccRetaiById(accountInfoList, supplyCustomer, Long.parseLong(hreq.getId().toString()));
        } else {
            this.bacthAcc(accountInfoList, supplyCustomer);
        }
        for (AccountInfo info : accountInfoList) {
            info.setPrice(info.getNewPrice());
        }

        List<GameList> gameLists = new ArrayList<>();
        List<String> sensitive = sensitive();
        int i = 1;
        List<String> skinList = hreq.getSkinList();
        for (AccountInfo accountInfo : accountInfoList) {

//            if (Objects.nonNull(skinList)) {
//                for (String skin : skinList) {
//                    String des = accountInfo.getDes();
//                    if (des.contains(skin)) {
//                        des = des.replace(skin, "<span style='color:red'>" + skin + "</span>");
//                        accountInfo.setDes(des);
//                    }
//                }
//            }
//
//            if (StringUtils.isNotEmpty(hreq.getZDYGJZ())) {
//                String des = accountInfo.getDes();
//                if (des.contains(hreq.getZDYGJZ())) {
//                    des = des.replace(hreq.getZDYGJZ(), "<span style='color:red'>" + hreq.getZDYGJZ() + "</span>");
//                    accountInfo.setDes(des);
//                }
//            }

            sensitive(accountInfo, sensitive);

            GameList gameList = new GameList();
            InfoImg infoImg = new InfoImg();
            infoImg.setInfoId(accountInfo.getId());
            infoImg.setType(Constants.STR_1);
            String titleImg = accountInfo.getTitleImg();
            if (!StringUtils.isNull(titleImg)) {
                gameList.setTitleImg(titleImg);
            }
            List<String> lables = new ArrayList<>();
            if ("王者荣耀".equals(accountInfo.getType())) {
                lables.add("大区 " + accountInfo.getRegional());
                lables.add("英雄 " + accountInfo.getHeroes());
                lables.add("皮肤 " + accountInfo.getSkins());
            } else if ("和平精英".equals(accountInfo.getType())) {
                lables.add("大区 " + accountInfo.getRegional());
                lables.add("套装 " + accountInfo.getSuit());
                lables.add("枪皮 " + accountInfo.getGuns());
            }
            gameList.setLables(lables);
            String s = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", accountInfo.getCreateTime());
            gameList.setCreateDate(s);

            //优化成自动调价
            gameList.setCsjg(accountInfo.getPrice());

            List<RetailAddPrice> retailAddPrices = hreq.getRetailAddPrices();
            if (!CollectionUtils.isEmpty(retailAddPrices)) {
                for (RetailAddPrice retailAddPrice : retailAddPrices) {

                    if (Objects.nonNull(retailAddPrice.getSuperId())) {
                        if (Objects.equals(accountInfo.getCreateId() + "", retailAddPrice.getSuperId())) {
                            Long rise = retailAddPrice.getRrise();
                            if (Objects.nonNull(rise)) {

                                /**
                                 * 供货商价 * 折扣  == 给供货商的钱
                                 * 供货商价 * 0.05 == 利润
                                 * （利润 + 供货价的钱） / 0.9
                                 */

                                BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
                                BigDecimal divide = add.divide(new BigDecimal(100));
                                BigDecimal price = accountInfo.getPrice();
                                BigDecimal multiply = price.multiply(divide);
                                if (Objects.nonNull(multiply)) {
                                    gameList.setCsjg(multiply.setScale(0, BigDecimal.ROUND_UP));
                                } else {
                                    gameList.setCsjg(accountInfo.getPrice().setScale(0, BigDecimal.ROUND_UP));
                                }
                            }
                        }
                    }
                }
            } else if (!CollectionUtils.isEmpty(hreq.getAddPriceList())) {
            } else {
                String serverName = request.getServerName();
                SysUser sysUser = new SysUser();
                sysUser.setDomain(serverName);
                List<SysUser> sysUsers = sysUserMapper.selectUserList(sysUser);
                if (!CollectionUtils.isEmpty(sysUsers)) {
                    sysUser = sysUsers.get(0);
                    Integer rise = sysUser.getRise();
                    if (Objects.isNull(rise)) {
                        rise = 0;
                    }
                    BigDecimal price = accountInfo.getPrice();
                    BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
                    add = add.divide(new BigDecimal(100));
                    BigDecimal multiply = price.multiply(add);
                    gameList.setCsjg(multiply.setScale(0, BigDecimal.ROUND_UP));
                }
            }

            gameList.setRownum(i);
            i++;
            gameList.setZhid(accountInfo.getId());
            gameList.setYxmz(accountInfo.getGameName());
            gameList.setYxdq(accountInfo.getRegional());
            gameList.setDw(accountInfo.getLevel());
            gameList.setYx(accountInfo.getHeroes());
            gameList.setPf(accountInfo.getSkins());
            gameList.setJj(accountInfo.getDes());
            gameList.setSuit(accountInfo.getSuit());
            gameList.setGunskin(accountInfo.getGuns());
            gameList.setAccType(accountInfo.getAccType());
            gameList.setSpecialPrice(accountInfo.getSpecialPrice());
            gameList.setYdsj(accountInfo.getCampId());
            gameLists.add(gameList);
        }
        return gameLists;
    }

    private void sensitive(AccountInfo accountInfo, List<String> sensitive) {
        for (String vocabulary : sensitive) {
            String orderNo = accountInfo.getOrderNo();
            if (Objects.nonNull(orderNo)) {
                orderNo = orderNo.replace(vocabulary, "");
                accountInfo.setOrderNo(orderNo);
            }

            String gameName = accountInfo.getGameName();
            if (Objects.nonNull(gameName)) {
                gameName = gameName.replace(vocabulary, "");
                accountInfo.setGameName(gameName);
            }

            String des = accountInfo.getDes();
            if (Objects.nonNull(des)) {
                des = des.replace(vocabulary, "");
                accountInfo.setDes(des);
            }
        }
    }

    private List<String> sensitive() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(LCConstant.sensitive);
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataList(sysDictData);
        return sysDictDataList.stream().map(s -> s.getDictValue()).collect(Collectors.toList());
    }


    @Override
    public AccountInfo selectAccountInfoByAccountId(String accountId) {
        return accountInfoMapper.selectAccountInfoByAccountId(accountId);
    }

    private Long bookingId(HttpServletRequest request) {

        String author = request.getHeader(Constant.author);
        if (!StringUtils.isEmpty(author)) {
            UserKey userKey = userKeyService.getAuthor(author);
            return userKey.getUserId();
        } else {
            throw new GlobalException("未识别到密钥");
        }
    }

    private void saveimportCount(Integer count, Long authorId) {
        ImportHistory importHistory = new ImportHistory();
        SysUser sysUser = sysUserMapper.selectUserById(authorId);
        if (null != sysUser) {
            importHistory.setSupplyName(sysUser.getUserName());
        }
        importHistory.setImportCount(count);
        importHistory.setCreateId(authorId);
        importHistory.setUserId(authorId);
        importHistoryService.insertImportHistory(importHistory);
    }


    public void addLabel(AccountInfo accountInfo, Long userId) {
        int cfg[] = {1, 3};
        List<String> result = new ArrayList<>();
        String[] desArr = accountInfo.getDes().split(" ");
        for (int j = 0; j < desArr.length; j++) {
            List<FieldConfig> fieldConfigs = fieldConfigMapper.selectFieldConfigByType(accountInfo.getType(), cfg);
            for (int i = 0; i < fieldConfigs.size(); i++) {
                if (!StringUtils.isEmpty(fieldConfigs.get(i).getFieldValue())) {
                    String[] fields = fieldConfigs.get(i).getFieldValue().split(",");
                    for (int n = 0; n < fields.length; n++) {
                        if (!StringUtils.isEmpty(desArr[j])) {
                            if (desArr[j] == fields[n] || desArr[j].contains(fields[n])) {
                                result.add(fields[n]);
                            }
                        }
                    }
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("infoId", accountInfo.getId());
        String collect = result.stream().collect(Collectors.joining(","));
        jsonObject.put("lableList", collect);
        insertAccountLable.insertAccountLable(jsonObject, userId);
    }

    @Override
    @Transactional
    public String batchSave(List<AccountInfo> accountInfos, HttpServletRequest request) {
        accountInfos.stream().forEach(a -> a.setAccountType(Constants.STR_2));
        accountInfos.stream().forEach(a -> a.setAccType(Constants.STR_0));
        accountInfos.stream().forEach(a -> a.setOdr(0));
        accountInfos.stream().forEach(a -> a.setSaleState(1));
        accountInfos.stream().forEach(a -> a.setAuthor(request.getHeader(Constant.author)));
        if (accountInfos.size() > 500) {
            throw new ServiceException("一次性导入不能超过500条", 500);
        }
        Long authorId = bookingId(request);
        if (null == authorId) {
            throw new ServiceException("获取供应商信息失败", 403);
        }
        accountInfos.stream().forEach(a -> a.setCreateId(authorId));
        accountInfos.stream().forEach(a -> a.setSupplyId(authorId));
        //判断账号是否存在
        List<String> accountList = accountInfos.stream().map(a -> a.getExtractAccount()).collect(Collectors.toList());
        List<AccountInfo> accountInfoList = accountInfoMapper.selectByAccountList(accountList,1l);
        List<String> exeAccountList = accountInfoList.stream().map(a -> a.getExtractAccount()).collect(Collectors.toList());
        String exeAccountStr = exeAccountList.stream().map(String::valueOf).collect(Collectors.joining(","));

        List<String> ornerList = accountInfoList.stream().map(a -> a.getOrderNo()).collect(Collectors.toList());

        if (!StringUtils.isEmpty(exeAccountStr)) {
            throw new ServiceException("账号：" + exeAccountStr + "已存在，无法导入", 500);
        }
        if (!StringUtils.isEmpty(ornerList)) {
            throw new ServiceException("自编号：" + ornerList + "已存在，无法导入", 500);
        }

        for (String s : exeAccountList) {
            if (!CollectionUtils.isEmpty(accountInfos)) {
                accountInfos = accountInfos.stream().filter(a -> (!a.getExtractAccount().equals(s))).collect(Collectors.toList());
            }
        }
        UserKey userKey = userKeyService.getAuthor(request.getHeader(Constant.author));
        SysUser sysUser = sysUserMapper.selectUserById(userKey.getUserId());
        //创建人
        if (!CollectionUtils.isEmpty(accountInfos)) {

            for (AccountInfo accountInfo : accountInfos) {
                String type = accountInfo.getType();
                if (Objects.equals("穿越火线", type)) {
                    type = "CF端游";
                    accountInfo.setType(type);
                }
                if (Objects.equals("枪战王者", type)) {
                    type = "CF手游";
                    accountInfo.setType(type);
                }
            }
            for (AccountInfo accountInfo : accountInfos) {
                String checkData = checkData(accountInfo);
                if (Objects.nonNull(checkData)) {
                    throw new ServiceException(checkData, 500);
                }
            }
            saveimportCount(accountInfos.size(), authorId);
            for (AccountInfo accountInfo : accountInfos) {
                accountInfo.setCreateTime(new Date());
                accountInfo.setOdr(sysUser.getOdr());

                BigDecimal b = new BigDecimal(100).add(new BigDecimal(userKey.getRise()));
                BigDecimal newPrice = accountInfo.getPrice().multiply(b).divide(new BigDecimal(100));
                accountInfo.setNewPrice(newPrice);
                accountInfo.setRise(userKey.getRise());

                List<InfoImg> imgList = new ArrayList<>();
                AccountInfo params = new AccountInfo();
                params.setOrderNo(accountInfo.getOrderNo());
                params.setAuthor(accountInfo.getAuthor());
                params.setSaleState(1);
                List<AccountInfo> accountInfosResult = accountInfoMapper.selectAuditAccountInfoList(params);
                if (Objects.isNull(accountInfosResult) || accountInfosResult.size() == 0) {
                    //如果数据库中没有相同订单号 和 相同author 标识的记录才进行插入
                    accountInfoMapper.insertAccountInfo(accountInfo);
//                    addLabel(accountInfo, authorId);
                } else {
                    throw new GlobalException("订单编号已存在,编号为" + params.getOrderNo());
                }
                String[] imageUrl = accountInfo.getImgList().split(",");
                for (String url : imageUrl) {

                    InfoImg infoImg = new InfoImg();
                    infoImg.setInfoId(accountInfo.getId());
                    infoImg.setImgUrl(url);
                    infoImg.setType("1");
                    UserKey author = userKeyService.getAuthor(accountInfo.getAuthor());
                    infoImg.setCreateId(author.getCreateId());
                    infoImg.setFileName("虞都科技.jpg");
                    infoImg.setCreateTime(new Date());
                    imgList.add(infoImg);
//
                }
                infoImgMapper.insertBacthInfoImg(imgList);
                return accountInfo.getId().toString();
            }
        }
        return null;
    }


    @Override
    public void update(AccountInfo accountInfo) throws Exception {
        AccountInfo accountInfoById = accountInfoMapper.selectAccountInfoById(accountInfo.getId());
        BeanUtils.copyProperties(accountInfo, accountInfoById);
        int i = accountInfoMapper.updateAccountInfo(accountInfoById);
        if (i != 1) {
            throw new GlobalException("编辑失败");
        }
    }

    @Transactional
    @Override
    public void updateApi(AccountInfo accountInfo, HttpServletRequest request) throws Exception {

        Long authorId = bookingId(request);
        AccountInfo accountInfoParams = new AccountInfo();
        if (Objects.nonNull(accountInfo.getId())) {
            accountInfoParams = accountInfoMapper.selectAccountInfoById(accountInfo.getId());
        } else {
            AccountInfo params = new AccountInfo();
            params.setOrderNo(accountInfo.getOrderNo());
            params.setAuthor(request.getHeader(Constant.author));
            params.setSaleState(1);
            List<AccountInfo> accountInfosResult = accountInfoMapper.selectAccountInfoList(params);
            if (!CollectionUtils.isEmpty(accountInfosResult)) {
                accountInfoParams = accountInfosResult.get(0);
            } else {
                throw new GlobalException("未找到该账号");
            }
        }
        infoImgMapper.deleteByInfoId(accountInfoParams.getId(), "1");

        List<InfoImg> infoImgList = new ArrayList<>();
        UserKey author = userKeyService.getAuthor(accountInfoParams.getAuthor());

        if (accountInfo.getImgList() != null && !"".equals(accountInfo.getImgList())) {
            String[] imageUrl = accountInfo.getImgList().split(",");
            for (String url : imageUrl) {
                InfoImg infoImg1 = new InfoImg();
                infoImg1.setInfoId(accountInfoParams.getId());
                infoImg1.setImgUrl(url);
                infoImg1.setType("1");
                infoImg1.setCreateId(author.getCreateId());
                infoImg1.setCreateTime(new Date());
                infoImg1.setFileName("虞都科技.jpg");
                infoImgList.add(infoImg1);
            }
        }
        if (!CollectionUtils.isEmpty(infoImgList)) {
            infoImgMapper.insertBacthInfoImg(infoImgList);
        }

        accountInfo.setId(accountInfoParams.getId());

        int i = accountInfoMapper.updateAccountInfo(accountInfo);
        apiTaskPoolService.insertBacth(accountInfo.getId(), null, Constant.edit, authorId);
        if (i != 1) {
            throw new GlobalException("编辑失败");
        }
    }

    @Override
    @Transactional
    public boolean undercarriage(AccountInfo accountInfo, HttpServletRequest request) throws Exception {

        if (Objects.nonNull(accountInfo.getId())) {
            Long authorId = bookingId(request);
            AccountInfo info = accountInfoMapper.selectAccountInfoById(accountInfo.getId());
            if (Objects.equals(SalerStatus.unsold.getCode(), info.getSaleState())) {
                info.setSaleState(7);
                int i = accountInfoMapper.updateAccountInfo(info);
                apiTaskPoolService.insertBacth(info.getId(), null, "del", authorId);
                if (i != 1) {
                    throw new GlobalException("删除失败");
                }
            } else {
                throw new GlobalException("当前状态已锁定，不可更新");
            }
        } else {
            AccountInfo params = new AccountInfo();
            params.setOrderNo(accountInfo.getOrderNo());
            log.info("当前请求参数={}", accountInfo.getOrderNo());
            Long authorId = bookingId(request);
            params.setSaleState(1);
            params.setCreateId(authorId);
            List<AccountInfo> accountInfosResult = accountInfoMapper.selectAuditAccountInfoList(params);
            if (!CollectionUtils.isEmpty(accountInfosResult)) {
                for (AccountInfo info : accountInfosResult) {
                    AccountInfo accountInfoById = accountInfosResult.get(0);
                    accountInfoById.setUpdateTime(new Date());
                    //设置为下架将状态设置为7
                    if (Objects.equals(SalerStatus.unsold.getCode(), info.getSaleState())) {
                        accountInfoById.setSaleState(7);
                        int i = accountInfoMapper.updateAccountInfo(accountInfoById);
                        apiTaskPoolService.insertBacth(accountInfoById.getId(), null, "del", authorId);
                        if (i < 1) {
                            throw new GlobalException("删除失败");
                        }
                    } else {
                        throw new GlobalException("当前状态已锁定，不可更新");
                    }
                }
            } else {
                throw new GlobalException("未查询到该记录");
            }

        }
        return true;
    }

    @Override
    public List<Map<String, Object>> byColums(String colum, String type) {
        List<String> strings = accountInfoMapper.byColums(colum, type);
        List<Map<String, Object>> result = new ArrayList<>();
        for (String s : strings) {
            Map<String, Object> m = new HashMap<>();
            m.put("dictValue", s);
            m.put("dictLabel", s);
            result.add(m);
        }
        return result;
    }

    @Override
    public void synchronousAcount(SynchronDto synchronDto) {

        String brandPrice = synchronDto.getBrandPrice();//价格
        String jobName = synchronDto.getJobName();//段位
        String ppxiangqing = synchronDto.getPpxiangqing();//标题
        String yingdiIdsd = synchronDto.getYingdiIdsd();//营地ID
        String gameCode = synchronDto.getGameCode();//价格
        String skinNum = synchronDto.getSkinNum();//皮肤数量
        String heroNum = synchronDto.getHeroNum();//英雄数量
        String areaId = synchronDto.getAreaId();//大区
        String titleImge = synchronDto.getTitleImge();//标题图
        String detailsImge = synchronDto.getDetailsImge();//详情图
        Long accountId = synchronDto.getAccountId();
        String qq = synchronDto.getQq();
        String author = synchronDto.getAuthor();

        //1安卓q 2 苹果q 3安卓v 4苹果v
        if (Objects.equals("1", areaId)) {
            areaId = "安卓Q";
        } else if (Objects.equals("2", areaId)) {
            areaId = "苹果Q";
        } else if (Objects.equals("3", areaId)) {
            areaId = "安卓WX";
        } else if (Objects.equals("4", areaId)) {
            areaId = "苹果WX";
        }

        List<AccountInfo> accountInfoList = new ArrayList<>();
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(accountId);
        accountInfo.setSaleState(1);
        if (Objects.nonNull(accountId)) {
            accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
        }
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        int odr = 0;
        if (null != sysUser) {
            odr = sysUser.getOdr();
        }

        if (!CollectionUtils.isEmpty(accountInfoList)) {
            if (!Objects.isNull(brandPrice)) {
                accountInfo.setPrice(new BigDecimal(brandPrice));
                accountInfo.setNewPrice(new BigDecimal(brandPrice));
            }
            accountInfo.setLevel(jobName);
            accountInfo.setDes(ppxiangqing);
            accountInfo.setTitleImg(titleImge);
            accountInfo.setCampId(yingdiIdsd);
            accountInfo.setGameName(gameCode);
            accountInfo.setHeroes(Integer.valueOf(heroNum));
            accountInfo.setSkins(Integer.valueOf(skinNum));
            accountInfo.setRegional(areaId);
            accountInfo.setType("王者荣耀");
            accountInfo.setSupplyId(userId);
            accountInfo.setSaleState(1);
            accountInfo.setOdr(odr);
            accountInfo.setAccType("0");
            accountInfo.setAccountType("1");
            accountInfo.setUpdateId(userId);
            accountInfo.setUpdateTime(new Date());
            accountInfo.setExtractAccount(qq);
            accountInfo.setGameName(author);

            InfoImg infoImg = new InfoImg();
            infoImg.setImgUrl(detailsImge);
            infoImg.setFileName("详情");
            infoImg.setInfoId(accountInfo.getId());
            infoImg.setType("1");
            int i = infoImgMapper.insertInfoImg(infoImg);
            accountInfoMapper.updateAccountInfo(accountInfo);
            addLabel(accountInfo, userId);
        } else {
            AccountInfo newA = new AccountInfo();
            newA.setPrice(new BigDecimal(brandPrice));
            newA.setNewPrice(new BigDecimal(brandPrice));
            newA.setLevel(jobName);
            newA.setDes(ppxiangqing);
            newA.setTitleImg(titleImge);
            newA.setCampId(yingdiIdsd);
            newA.setGameName(gameCode);
            newA.setHeroes(Integer.valueOf(heroNum));
            newA.setSkins(Integer.valueOf(skinNum));
            newA.setRegional(areaId);
            newA.setType("王者荣耀");
            newA.setSupplyId(userId);
            newA.setSaleState(1);
            newA.setOdr(odr);
            newA.setAccType("0");
            newA.setAccountType("1");
            newA.setCreateId(userId);
            newA.setCreateTime(new Date());
            accountInfo.setExtractAccount(qq);
            accountInfo.setGameName(author);

            accountInfoMapper.insertAccountInfo(newA);
            InfoImg infoImg = new InfoImg();
            infoImg.setImgUrl(detailsImge);
            infoImg.setFileName("详情");
            infoImg.setInfoId(newA.getId());
            infoImg.setType("1");
            int i = infoImgMapper.insertInfoImg(infoImg);
            addLabel(newA, userId);
        }
    }

    @Override
    public List<String> allSale() {

        List<String> result = outRecService.selectSaleId();

        return result;
    }

    @Override
    public List<String> getArea(String area) {


        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(area);

        List<SysDictData> list = sysDictDataMapper.selectDictDataList(sysDictData);

        List<String> collect = list.stream().map(s -> s.getDictLabel()).collect(Collectors.toList());

        return collect;
    }

    @Override
    public String checkData(AccountInfo accountInfo) {

        /**
         * 1、游戏类型
         * 2、区服
         * 3、实名认证
         */
        String type = accountInfo.getType();
        List<String> typeList = getArea("game_type");
        if (typeList.contains(typeList)) {
            return "当前不支持对的游戏类型：" + type;
        }
        List<String> regionalList = getArea(type);
        if (CollectionUtils.isEmpty(regionalList)) {
            return "当前游戏暂未配置相关区服 ：" + type;
        }

        if (!regionalList.contains(accountInfo.getRegional())) {
            return "未匹配到正确区服 ：" + accountInfo.getRegional();
        }

        if (!Objects.equals("英雄联盟", type)) {
            List<String> levelList = getArea(type + "段位");
            if (CollectionUtils.isEmpty(levelList)) {
                return "当前游戏暂未配置实名认证 ：" + type;
            }

            if (!levelList.contains(accountInfo.getLevel())) {
                return "未匹配到正确实名认证 ：" + accountInfo.getLevel();
            }
        }

        if (Objects.isNull(accountInfo.getPrice())) {
            return "价格不能为空 ：" + accountInfo.getGameName();
        }

        return null;
    }

    @Override
    public TableDataInfo cmdPoollist(AccountInfo accountInfo) {


        CmdAccount cmdAccount = new CmdAccount();
        List<CmdAccount> cmdAccounts = cmdAccountMapper.selectCmdAccountList(cmdAccount);
        List<Long> exeIds = cmdAccounts.stream().map(r -> r.getAccountId()).collect(Collectors.toList());
        accountInfo.setExeIds(exeIds);

        Long userId = SecurityUtils.getUserId();
        SalePartner salePartner = commonApi.get(LCConstant.saleNameYDD, userId);
        if (Objects.nonNull(salePartner)) {
            accountInfo.setUserKey(salePartner.getSaleKey());
        }
        String path = "cmdApi/poollist";
        String result = "";
        try {
            result = HttpUtils.post(JSONObject.parseObject(JSONObject.toJSONString(accountInfo)), cmdHost + path);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("请求异常", 500);
        }

        TableDataInfo tableDataInfo = JSONObject.parseObject(result, TableDataInfo.class);
        return tableDataInfo;
    }

    @Override
    public List<JSONObject> supplyIdList() {
        String path = "cmdApi/supplyIdList";
        String result = "";
        String userKey = null;

        Long userId = SecurityUtils.getUserId();
        SalePartner salePartner = commonApi.get(LCConstant.saleNameYDD, userId);
        if (!Objects.isNull(salePartner)) {
            userKey = salePartner.getSaleKey();
        }

        try {
//            result = HttpUtils.sendGet(cmdHost + path + "?userKey=" + userKey, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("请求异常", 500);
        }
        return JSONArray.parseArray(result, JSONObject.class);
    }

    @Override
    @Transactional
    public String addCmdAccount(Long[] ids) {


//        AccountInfo accountInfo = new AccountInfo();
//        accountInfo.setIds(Arrays.asList(ids));
//        String path = "cmdApi/allList";
//        String result = "";
//
//        CmdAccount esxCmd = new CmdAccount();
//        List<CmdAccount> esccmdAccountList = cmdAccountMapper.selectCmdAccountList(esxCmd);
//        List<Long> exsIds = esccmdAccountList.stream().map(cmd -> cmd.getAccountId()).collect(Collectors.toList());
//        accountInfo.setExeIds(exsIds);
//        try {
//            result = HttpUtils.post(JSONObject.parseObject(JSONObject.toJSONString(accountInfo)),cmdHost + path);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new ServiceException("请求异常",500);
//        }
//        List<AccountInfo> accountInfoList = JSONArray.parseArray(result, AccountInfo.class);
//
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        List<CmdAccount> cmdAccountList = new ArrayList<>();
//
//        BigDecimal b = null;
//        BigDecimal newPrice = null;
//        AddPrice priceParams = new AddPrice();
//        priceParams.setRoleType("2");
//        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(priceParams);
//        if (!CollectionUtils.isEmpty(accountInfoList)){
//            for (AccountInfo info : accountInfoList) {
//                CmdAccount cmdAccount = new CmdAccount();
//                for (AddPrice addPrice : addPrices) {
//                    if (Objects.equals(info.getSupplyId(),addPrice.getSuperId())){
//                        Integer rise = addPrice.getRise();
//                        if (null == addPrice.getRise()) rise = 0;
//                        if (Objects.equals(1l,addPrice.getType())){
//                            b = new BigDecimal(100).add(new BigDecimal(rise));
//                        }else {
//                            b = new BigDecimal(100).subtract(new BigDecimal(rise));
//                        }
//
//                        newPrice = info.getPrice().multiply(b).divide(new BigDecimal(100));
//                        info.setRise(rise);
//                        cmdAccount.setRise(rise);
//                        if (Objects.isNull(b)){
//                            cmdAccount.setNewPrice(info.getPrice());
//                            info.setNewPrice(info.getPrice());
//                        }else {
//                            cmdAccount.setNewPrice(newPrice);
//                            info.setNewPrice(newPrice);
//                        }
//                    }
//                }
//                //终端数据赋值
//                info.setCmdSupplyId(info.getSupplyId());
//                info.setCmdSupplyName(info.getSupplyName());
//                cmdAccount.setSuplyId(info.getSupplyId());
//                //清空终端供货人名称
//                info.setSupplyId(loginUser.getUserId());
//                info.setSupplyName(null);
//
//                cmdAccount.setAccountId(info.getId());
//                cmdAccountList.add(cmdAccount);
//
//                //设置入库默认值
//                info.setAccountType("3");
//                info.setSaleState(1);
//                info.setCreateId(loginUser.getUserId());
//                info.setId(null);
//                cmdAccount.setUuid(info.getUuid());
//                cmdAccount.setMainId(info.getId());
//                cmdAccount.setId(null);
//            }
//        }
//        accountInfoList.stream().forEach(acc -> acc.setCreateTime(new Date()));
////        for (AccountInfo acc : accountInfoList) {
////            accountInfoMapper.insertAccountInfo(acc);
////        }
//        accountInfoMapper.batchSave(accountInfoList);
//        cmdAccountMapper.saveBatch(cmdAccountList);
////        for (CmdAccount cmdAccount : cmdAccountList) {
////            cmdAccountMapper.insertCmdAccount(cmdAccount);
////        }
//
//        for (AccountInfo acc : accountInfoList) {
//            if (acc.getImgList() != null && !"".equals(acc.getImgList())) {
//                //确认修改后删除所有之前该订单的
////                InfoImg infoImg = new InfoImg();
////                infoImg.setInfoId(acc.getId());
////                List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
//
//                infoImgMapper.deleteByInfoId(acc.getId(), "1");
////                for (InfoImg img : infoImgs) {
////
////                }
//                String[] imageUrl = acc.getImgList().split(",");
//                for (String url : imageUrl) {
//                    InfoImg infoImg1 = new InfoImg();
//                    infoImg1.setInfoId(acc.getId());
//                    infoImg1.setImgUrl(url);
//                    infoImg1.setType("1");
//                    infoImg1.setCreateId(acc.getSupplyId());
//                    infoImg1.setFileName("虞都科技.jpg");
//                    infoImgMapper.insertInfoImg(infoImg1);
//                }
//            }
//        }
//        return null;

        return null;
    }

    @Override
    public String bacthAddAccount(Long suplyId) {

        String path = "cmdApi/idsBySuperId";
        String result = "";
        try {
            JSONObject params = new JSONObject();
            params.put("superId", suplyId);
//            result = HttpUtils.sendGet(cmdHost + path + "?superId=" + suplyId, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("请求异常", 500);
        }

        List<Long> ids = JSONObject.parseArray(result, Long.class);

//        addCmdAccount((ids.toArray(new Long[ids.size()])));


        return null;
    }

    @Override
    @Transactional
    public String delAccount(Long suplyId) {
        //删除账号数据
        if (null != suplyId) {
            int i = accountInfoMapper.delByCmdSuplyId(suplyId);
            //删除终端数据
            cmdAccountMapper.deleteCmdAccountBySuplyId(suplyId);
        }
        return null;
    }

    @Override
    public List<InfoImg> getImage(AccountInfo accountInfo) {

        InfoImg infoImg = new InfoImg();
        infoImg.setInfoId(accountInfo.getId());
        List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
        AccountInfo info = accountInfoMapper.selectAccountInfoById(accountInfo.getId());
        if (Objects.nonNull(info)) {
            InfoImg titleImg = new InfoImg();
            titleImg.setImgUrl(info.getTitleImg());
            titleImg.setFileName("虞都科技.jpg");
            infoImgs.add(titleImg);
        }


        return infoImgs;
    }

    /**
     * 终端新增账号处理
     *
     * @param result
     */
    @Override
    public void excuResult(String result, Long userId) {
        List<AccountInfo> accountInfoList = JSONArray.parseArray(result, AccountInfo.class);

//        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<CmdAccount> cmdAccountList = new ArrayList<>();

        BigDecimal b = null;
        BigDecimal newPrice = null;
        AddPrice priceParams = new AddPrice();
        priceParams.setRoleType("2");
        priceParams.setPriceType("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(priceParams);
        if (!CollectionUtils.isEmpty(accountInfoList)) {
            accountInfoList.stream().forEach(acc -> acc.setCreateTime(new Date()));
            for (AccountInfo info : accountInfoList) {
                CmdAccount cmdAccount = new CmdAccount();
                for (AddPrice addPrice : addPrices) {
                    if (Objects.equals(info.getSupplyId(), addPrice.getSuperId())) {
                        Integer rise = addPrice.getRise();
                        if (null == addPrice.getRise()) rise = 0;
                        if (Objects.equals("1", addPrice.getType())) {
                            b = new BigDecimal(100).add(new BigDecimal(rise));
                        } else {
                            b = new BigDecimal(100).subtract(new BigDecimal(rise));
                        }

                        newPrice = info.getPrice().multiply(b).divide(new BigDecimal(100));
                        info.setRise(rise);
                        cmdAccount.setRise(rise);
                        if (Objects.isNull(b)) {
                            cmdAccount.setNewPrice(info.getPrice());
                            info.setNewPrice(info.getPrice());
                        } else {
                            cmdAccount.setNewPrice(newPrice);
                            info.setNewPrice(newPrice);
                        }
                    }
                }
                //终端数据赋值
                info.setCmdSupplyId(info.getSupplyId());
                info.setCmdSupplyName(info.getSupplyName());
                cmdAccount.setSuplyId(info.getSupplyId());
                //清空终端供货人名称
                info.setSupplyId(userId);
                info.setSupplyName(null);

                cmdAccount.setAccountId(info.getId());
                cmdAccountList.add(cmdAccount);

                //设置入库默认值
                info.setAccountType("3");
                info.setSaleState(1);
                info.setCreateId(userId);
                info.setId(null);
                cmdAccount.setUuid(info.getUuid());
                cmdAccount.setMainId(info.getId());
                cmdAccount.setId(null);
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setUuid(info.getUuid());
                accountInfo.setDel(0);
                List<AccountInfo> accList = accountInfoMapper.selectAccountInfoList(accountInfo);
                if (CollectionUtils.isEmpty(accList)) {
                    cmdAccountMapper.insertCmdAccount(cmdAccount);
                    accountInfoMapper.insertAccountInfo(info);

                    if (info.getImgList() != null && !"".equals(info.getImgList())) {
                        //确认修改后删除所有之前该订单的
//                InfoImg infoImg = new InfoImg();
//                infoImg.setInfoId(acc.getId());
//                List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);

                        infoImgMapper.deleteByInfoId(info.getId(), "1");
//                for (InfoImg img : infoImgs) {
//
//                }
                        String[] imageUrl = info.getImgList().split(",");
                        for (String url : imageUrl) {
                            InfoImg infoImg1 = new InfoImg();
                            infoImg1.setInfoId(info.getId());
                            infoImg1.setImgUrl(url);
                            infoImg1.setType("1");
                            infoImg1.setCreateId(info.getSupplyId());
                            infoImg1.setFileName("虞都科技.jpg");
                            infoImgMapper.insertInfoImg(infoImg1);
                        }
                    }
                }

            }
        }
    }

    @Override
    public List<Map<String, Object>> onGameType(Long userId) {

        List<Map<String, Object>> result = accountInfoMapper.onGameType(userId);

        return result;
    }

    @Autowired
    private UploadService uploadService;

    @Override
    public void wyImgBacth(AccountInfo acc) {


        if (Objects.isNull(acc)) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setCreateId(475l);
//            accountInfo.setId(21927l);
            accountInfo.setSaleState(1);
            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
            for (AccountInfo info : accountInfoList) {
                uploadImg(info);
            }
        } else {
            uploadImg(acc);
        }

    }

    @Override
    public BigDecimal addPrice(BigDecimal price, Long createId, List<SysUser> supplyCustomer) {

        /**
         * 供货商价 * 折扣  == 给供货商的钱
         * 供货商价 * 0.05 == 利润
         * （利润 + 供货价的钱） / 0.9
         * 供货商价 * 0.1 = 利润
         * 供货价 *
         *
         */
        for (SysUser sysUser : supplyCustomer) {
            if (Objects.nonNull(sysUser) && Objects.equals(sysUser.getUserId(), createId)) {

                //给供货商的钱
                Integer discountt = sysUser.getDiscountt();
                BigDecimal zhekou = new BigDecimal(discountt).divide(new BigDecimal(100));
                BigDecimal multiply = price.multiply(zhekou);

                //利润
                BigDecimal bigDecimal1 = new BigDecimal(sysUser.getRise()).divide(new BigDecimal(100));
                BigDecimal lirun = price.multiply(bigDecimal1);

                //利润 + 供货价
                BigDecimal add = lirun.add(multiply);

                //供货价  / 0.9 = 售价
                BigDecimal bigDecimal = add.divide(new BigDecimal(0.9), 0, BigDecimal.ROUND_UP);
                return bigDecimal;
            }
        }
        return null;
    }

    @Override
    public int deleteAllAccount(DeleteAllVo params) {

        //供货人
        //配置
        List<String> supplyIdAddr = params.getSupplyIdAddr();
        List<Long> configArr = params.getConfigArr();

        if (!CollectionUtils.isEmpty(supplyIdAddr)) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setCreateIds(supplyIdAddr);
            accountInfo.setSaleState(SalerStatus.unsold.getCode());
            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
            if (!CollectionUtils.isEmpty(accountInfoList)) {

                for (AccountInfo info : accountInfoList) {
                    Long userId = info.getCreateId();
                    if (!CollectionUtils.isEmpty(configArr)) {
                        configArr.stream().forEach(a -> {
                            asyncService.excuTaskPool(info.getId(), a, Constant.del, accountInfo.getCreateId(), userId);
                        });
                    } else {
                        asyncService.excuTaskPool(info.getId(), null, Constant.del, accountInfo.getCreateId(), userId);
                    }
                    info.setSaleState(SalerStatus.sinceSale.getCode());
                    accountInfoMapper.updateAccountInfo(info);
                }
            }
        }
        return 1;
    }

    @Override
    public void jymPush(Map<String, Object> params) {

        //1、查询对应账号
        //2、查询对应的配置
        //3、开始推送
        Long id = MapUtils.getLong(params, "id");
        AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(82l);
    }

    @Override
    public int upAccount(Long id) {

        AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
        if (Objects.isNull(accountInfo)) {
            throw new GlobalException("ID错误");
        }
        accountInfo.setSaleState(1);

        return accountInfoMapper.updateAccountInfo(accountInfo);
    }

    @Override
    public void bacthAccRetail(List<AccountInfo> list, List<SysUser> supplyCustomer) {
        Long userId = SecurityUtils.getUserId();

        for (AccountInfo accountInfo : list) {
            if (Objects.equals(userId, accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            } else {
                BigDecimal bigDecimal = addPrice(accountInfo.getPrice(), accountInfo.getCreateId(), supplyCustomer);
                accountInfo.setNewPrice(bigDecimal);
            }
        }
    }

    public void bacthAccRetaiById(List<AccountInfo> list, List<SysUser> supplyCustomer, Long id) {

        for (AccountInfo accountInfo : list) {
            if (Objects.equals(id, accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            } else {
                BigDecimal bigDecimal = addPrice(accountInfo.getPrice(), accountInfo.getCreateId(), supplyCustomer);
                accountInfo.setNewPrice(bigDecimal);
            }
        }
    }

    @Override
    public boolean ifPolish() {

        //获取当前登录账号
        Long userId = SecurityUtils.getUserId();


        Polish polish = new Polish();
        polish.setCreateId(userId);
        polish.setCreateTime(new Date());

        Polish po = polishMapper.selectDataNow(polish);
        if (Objects.nonNull(po)) {
            return false;
        }

        return true;

    }

    @Override
    public List<Map<String, Object>> userGameCount() {

        return  accountInfoMapper.userGameCount();
    }

    @Override
    public List<Map<String, Object>> GameTypeCount() {
        return accountInfoMapper.GameTypeCount();
    }

    @Override
    public List<Map<String,Object>> typeCount(AccountInfo accountInfo) {

        List<Map<String,Object>> list =  accountInfoMapper.stateCountByUserId(accountInfo.getCreateId());

        return list;
    }

    private void uploadImg(AccountInfo info) {
        try {
            InfoImg infoImg = new InfoImg();
            infoImg.setType("1");
            infoImg.setInfoId(info.getId());
            List<InfoImg> infoImgList = infoImgMapper.selectInfoImgList(infoImg);
            List<InfoImg> imgList = new ArrayList<>();
            for (InfoImg img : infoImgList) {
                if (StringUtils.isNotEmpty(img.getImgUrl())) {
//                    if (!img.getImgUrl().contains("https") && img.getImgUrl().contains("穿越火线")){
                    try {
//                            String replace = img.getImgUrl().replace("穿越火线", URLEncoder.encode("穿越火线", "utf-8"));
                        byte[] bytes = ImgUtils.onlineImage2byte(img.getImgUrl());
                        String qnurl = uploadService.upload2Qiniu(bytes, UUIDGenerator.generate());
                        img.setImgUrl(qnurl);
                        imgList.add(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }

//                    }
                }
            }
            if (!CollectionUtils.isEmpty(imgList)) {
                infoImgMapper.deleteByInfoId(info.getId(), "1");
                infoImgMapper.insertBacthInfoImg(imgList);
            }

            String titleImg = info.getTitleImg();
//            if (StringUtils.isNotEmpty(titleImg) && !titleImg.contains("https") && titleImg.contains("穿越火线")){
            try {
//                    titleImg = titleImg.replace("穿越火线", URLEncoder.encode("穿越火线", "utf-8"));
                byte[] bytes = ImgUtils.onlineImage2byte(titleImg);
                String qnurl = uploadService.upload2Qiniu(bytes, UUIDGenerator.generate());
                info.setTitleImg(qnurl);
                accountInfoMapper.updateAccountInfo(info);
            } catch (Exception e) {
                log.info(e.getMessage());
            }

//            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return;
        }

    }
    //推送财务
    public int pushFinance(AccountInfo accountInfo){
        Finance finance = new Finance();
        finance.setCreateId(SecurityUtils.getUserId());
        finance.setCreateTime(DateUtils.getNowDate());
        finance.setIotype("1");
        long l = 13;
        finance.setBillType(l);
        finance.setPrice(accountInfo.getCostPrice());
        finance.setField1(accountInfo.getExtractAccount());
        finance.setRemark(accountInfo.getSinNum());
        //平台编号
        finance.setField4(String.valueOf(accountInfo.getId()));
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        finance.setRegisteruser(sysUser.getNickName());
        return financeMapper.insertFinance(finance);
    }
}
