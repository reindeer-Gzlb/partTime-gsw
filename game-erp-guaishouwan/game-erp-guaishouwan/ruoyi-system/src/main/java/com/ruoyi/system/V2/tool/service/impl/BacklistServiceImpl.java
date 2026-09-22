package com.ruoyi.system.V2.tool.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMUser;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.system.V2.font.mapper.QuotationMapper;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.hsgame.service.IHsGameService;
import com.ruoyi.system.V2.im.mapper.MsgGroupInfoMapper;
import com.ruoyi.system.V2.recycle.mapper.KrecycleMapper;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.V2.tool.service.IBlackApi;
import com.ruoyi.system.backorder.service.IBackOrderService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.thirdparty.ThirdPartyRecycleReq;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.service.IPayRecordService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Slf4j
@Service
public class BacklistServiceImpl implements IBacklistService {
    @Autowired
    private BacklistMapper backlistMapper;
    @Autowired
    private QuotationMapper quotationMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
//    @Autowired
//    private BacklistHistoryMapper backlistHistoryMapper;
    @Autowired
    private KrecycleMapper krecycleMapper;
    @Autowired
    private EMService service;

//    @Autowired
//    private CompensateMapper compensateMapper;

    @Autowired
    private IHsGameService hsGameService;

    @Autowired
    private MsgGroupInfoMapper msgGroupInfoMapper;

    @Autowired
    private IBlackApi blackApi;
    @Autowired
    IPayRecordService payRecordService;

    @Autowired
    private IBackOrderService backOrderService;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Backlist selectBacklistById(Long id) {
        Backlist backlist = backlistMapper.selectBacklistById(id);
        if (Objects.nonNull(backlist)) {
            backlist.setTel(null);
            backlist.setUserPhone(null);
        }
        return backlist;
    }

    /**
     * 查询【回收联盟】列表
     *
     * @param backlist 【回收池】
     * @return 【结果】
     */
    @Override
    public List<Backlist> selectBacklistList(Backlist backlist, PageDomain pageDomain) {
        backlist.setStatus(0l);
//        Quotation quotation = new Quotation();
//        quotation.setCreateId(SecurityUtils.getUserId());
//        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
//        if (CollectionUtils.isNotEmpty(quotations)) {
//            List<Long> collect = quotations.stream().map(q -> q.getBackId()).collect(Collectors.toList());
//            backlist.setExtIds(collect);
//        }

        HsGame hsGame = new HsGame();
        if (!Objects.equals(1l, SecurityUtils.getUserId())) {
            hsGame = hsGameService.selectHsGameByCreateId(SecurityUtils.getUserId());
            if (Objects.isNull(hsGame)) {
                throw new GlobalException("暂未授权任何游戏");
            }
        }


        String gameName = hsGame.getGameName();
        if (StringUtils.isNotEmpty(gameName)) {
            String[] split = gameName.split(",");
            backlist.setTypes(Arrays.asList(split));
        }


        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }

        backlist.setCreateBojiaId(SecurityUtils.getUserId());
        backlist.setTransfer(2);
        backlist.setState(1);
        List<Backlist> backlists = backlistMapper.selectBaoJiaBacklistList(backlist);
        for (Backlist b : backlists) {
            Quotation quotation = new Quotation();
            quotation.setBackId(b.getId());
            quotation.setNoStatus("5");
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
            b.setBcountt(quotations.size());
            if (CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }
        return backlists;
    }

    /**
     * 查询【我的回收】列表
     *
     * @param backlist 【回收】
     * @return 【结果】
     */
    @Override
    public List<Backlist> selectMyBacklistList(Backlist backlist) {
//        Quotation quotation = new Quotation();
//        quotation.setCreateId(SecurityUtils.getUserId());
//        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
//        if (CollectionUtils.isNotEmpty(quotations)) {
//            List<Long> collect = quotations.stream().map(q -> q.getBackId()).collect(Collectors.toList());
//            backlist.setExtIds(collect);
//        }
//        backlist.setTransfer(1);

        Long status = backlist.getStatus();
        if (!Objects.equals(5l, status)) {
            backlist.setNoStatus(5l);
        }

        backlist.setSupplyUserId(SecurityUtils.getUserId());
        List<Backlist> backlists = backlistMapper.selectBacklistList(backlist);
//        Map<Long, GoodsInfo> collect = null;
//        Map<Long, Krecycle> collect1 = null;
//        if (CollectionUtils.isNotEmpty(backlists)) {
//            List<Long> ids = backlists.stream().map(s -> s.getGid()).collect(Collectors.toList());
//            if (ids.size() > 0) {
//                GoodsInfo info = new GoodsInfo();
//                info.setIds(ids);
//                List<GoodsInfo> infos = goodsInfoMapper.selectGoodsInfoList(info);
//                collect = infos.stream().collect(Collectors.toMap(GoodsInfo::getId, GoodsInfo -> GoodsInfo));
//            }
//            List<Long> krecycleIds = backlists.stream().map(s -> s.getId()).collect(Collectors.toList());
//
//            List<Krecycle> krecycles = krecycleMapper.selectKrecycleByIds(krecycleIds);
//            if (CollectionUtils.isNotEmpty(krecycles)) {
//                collect1 = krecycles.stream().collect(Collectors.toMap(Krecycle::getBackId, Krecycle -> Krecycle));
//            }
//        }
//        for (Backlist b : backlists) {
////            b.setAccount(null);
////            b.setTel(null);
////            b.setUserPhone(null); //隐藏手机号
//            if (collect != null) {
//                if (collect.get(b.getGid()) != null) {
//                    b.setIsState(collect.get(b.getGid()).getSaleState());
//                }
//            }
//            if (collect1 != null && collect1.get(b.getId()) != null) {
//                if (StringUtils.isEmpty(collect1.get(b.getId()).getGroupId())) {
//                    b.setGroupState(1);
//                } else {
//                    if (collect1.get(b.getId()).getContents().contains("包赔")) {
//                        b.setGroupState(3);
//                    } else {
//                        b.setGroupState(2);
//                    }
//                }
//            } else {
//                b.setGroupState(0);
//            }
////            if (b.getGid() != null && b.getBackState() != 1) {
////                GoodsInfo info = collect.get(b.getGid());
////                Map map = new HashMap<>();
////                map.put("accountNumber", info.getAccountNumber());
////                map.put("accountPassword", info.getAccountPassword());
////                map.put("securityPhone", info.getSecurityPhone());
////                map.put("importantRemark", info.getImportantRemark());
////                b.setAccountContent(map);
////            }
////            Krecycle krecycle = krecycleMapper.selectKrecycleByBacklistId(b.getId());
////            if (Objects.nonNull(krecycle)) {
////                GoodsInfo info = collect.get(b.getGid());
////                Map map = new HashMap<>();
////                map.put("accountNumber", krecycle.getAccount());
////                map.put("securityPhone", krecycle.getTel());
////                map.put("importantRemark", info.getImportantRemark());
////                b.setAccountContent(map);
////            }
//
//        }
        return backlists;
    }

    /**
     * 新增【官网回收】
     *
     * @param backlist 【回收】
     * @return 结果
     */
    @Override
    public int insertBacklist(Backlist backlist) {
        backlist.setCreateTime(DateUtils.getNowDate());
        backlist.setCreateId(SecurityUtils.getUserId());
        backlist.setStatus(0l);
        backlist.setPartition(2);   //官网回收
        backlist.setTransfer(2);    //回收池
        return backlistMapper.insertBacklist(backlist);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param backlist 【请填写功能名称】
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBacklist(Backlist backlist) {
        backlist.setUpdateTime(DateUtils.getNowDate());
        backlist.setUpdateId(SecurityUtils.getUserId());
        Backlist info = backlistMapper.selectBacklistById(backlist.getId());
        if (ObjectUtils.isNotEmpty(info) && backlist.getPartition() != null && backlist.getPartition() != 0) {
//            if (backlist.getPartition() != info.getPartition()) {
//                BacklistHistory history = new BacklistHistory();
//                history.setBackListId(backlist.getId());
//                history.setCreateId(SecurityUtils.getUserId());
//                history.setOperation(backlist.getPartition());
//                backlistHistoryMapper.insertBacklistHistory(history);
//            }
        }
        backlist.setExpprice(info.getExpprice());
        return backlistMapper.updateBacklist(backlist);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBacklistByIds(Long[] ids) {
        return backlistMapper.deleteBacklistByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBacklistById(Long id) {
        return backlistMapper.deleteBacklistById(id);
    }

    @Override
    public List<Backlist> allList(Backlist backlist) {
        backlist.setCreateId(SecurityUtils.getUserId());
        List<Backlist> backlistList = backlistMapper.selectBacklistList(backlist);
        for (Backlist b : backlistList) {
            Quotation quotation = new Quotation();
            quotation.setBackId(b.getId());
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
            b.setBcountt(quotations.size());
            if (CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }
        return backlistList;
    }

    @Override
    @Transactional
    public int insertH5Backlist(Backlist backlist) {
        backlist.setCreateTime(DateUtils.getNowDate());
        SysUser user = userMapper.checkPhoneUnique(backlist.getUserPhone());
        if (Objects.isNull(user)) {
            user = new SysUser();
            user.setUserName(backlist.getTel());
            user.setNickName(backlist.getTel());
            user.setPhonenumber(backlist.getTel());
            user.setPassword(SecurityUtils.encryptPassword("123456"));
            user.setUserNewType(2);
            user.setStatus("0");
            userService.insertUser(user);
        }
        backlist.setCreateId(user.getUserId());
        backlist.setStatus(0l);
        return backlistMapper.insertBacklist(backlist);
    }

    @Override
    @Transactional
    public int insertH5BacklistNew(Backlist backlist) {
        if (StringUtils.isNotEmpty(backlist.getAccount())) {
            Map<String, Object> params = new HashMap<>();
            params.put("content", backlist.getAccount());
            List<HBlack> blacks = blackApi.allAcount(params);
            if (CollectionUtils.isNotEmpty(blacks)) {
                throw new GlobalException("您的账号存在交易风险，禁止发布至本平台，谢谢！");
            }
        }
        if (StringUtils.isNull(backlist.getPartition())) {
            backlist.setPartition(2);
        }
        if (backlist.getPartition() == 1) {
            backlist.setTransfer(1);     //我的回收
        } else {
            backlist.setTransfer(2);
        }
        backlist.setBackState(1);
        backlist.setStatus(0l);
        backlist.setCreateId(SecurityUtils.getUserId());
        backlist.setCreateTime(DateUtils.getNowDate());
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        if (StringUtils.isNotEmpty(ip) && !StringUtils.equals("unknown", ip)) {
            backlist.setIp(ip);
            try {
                String rspStr = HttpUtils.sendGet("http://whois.pconline.com.cn/ipJson.jsp", "ip=" + ip + "&json=true"
                        , Constants.GBK);
                if (StringUtils.isNotEmpty(rspStr)) {
                    JSONObject obj = JSONObject.parseObject(rspStr);
                    String addr = obj.getString("addr");
                    backlist.setIpAddr(addr);
                }
            } catch (Exception e) {

            }
        }
        return backlistMapper.insertBacklist(backlist);
    }

    @Override
    public List<Backlist> selectListH5ForTel(String tel) {
        SysUser user = userMapper.checkPhoneUnique(tel);
        Long createId = -1l;
        if (Objects.nonNull(user)) {
            createId = user.getUserId();
        }
        Backlist backlist = new Backlist();
        backlist.setCreateId(createId);
        List<Backlist> backlistList = backlistMapper.selectBacklistList(backlist);
        for (Backlist b : backlistList) {
            Quotation quotation = new Quotation();
            quotation.setBackId(b.getId());
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
            b.setBcountt(quotations.size());
            if (CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }
        return backlistList;
    }

    @Override
    @Transactional
    public int insertThirdBacklist(ThirdPartyRecycleReq thirdPartyRecycleReq) {
        Backlist backlist = new Backlist();
        BeanUtils.copyBeanProp(backlist, thirdPartyRecycleReq);
        backlist.setPartition(3);
        backlist.setTransfer(2);
        backlist.setStatus(0l);
        backlist.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isNotEmpty(thirdPartyRecycleReq.getAccount())) {
            Map<String, Object> params = new HashMap<>();
            params.put("content", thirdPartyRecycleReq.getAccount());
            List<HBlack> blacks = blackApi.allAcount(params);
            if (CollectionUtils.isNotEmpty(blacks)) {
                backlist.setIsBlack(1);
            }
        }
        backlistMapper.insertBacklist(backlist);
        return 1;
    }

    @Override
    public List<Backlist> selectThirdBacklistList(Backlist backlist) {
        List<Backlist> backlistList = backlistMapper.selectBacklistList(backlist);
        for (Backlist b : backlistList) {
            Quotation quotation = new Quotation();
            quotation.setBackId(b.getId());
            quotation.setNoStatus("5");
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
            b.setBcountt(quotations.size());
            if (CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }
        return backlistList;
    }

    @Override
    public Backlist getBackInfoById(Backlist backlist) {
        Backlist b = backlistMapper.selectBacklistByIdAndOtherUserId(backlist);
        if (StringUtils.isNotNull(b)) {
            Quotation quotation = new Quotation();
            quotation.setBackId(b.getId());
            quotation.setNoStatus("5");
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
            b.setBcountt(quotations.size());
            if (CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }
        return b;
    }

    @Transactional
    @Override
    public int editCompensateInfo(Map param) {
        String id = param.get("id").toString();
        Backlist backlist = backlistMapper.selectBacklistById(Long.valueOf(id));
        if (param.get("cpsId") != null && !StringUtils.equals("", param.get("cpsId").toString())) {
            String cpsId = param.get("cpsId").toString();
            String cpsName = param.get("cpsName").toString();
            backlist.setCpsId(Long.valueOf(cpsId));
            backlist.setCpsName(cpsName);
        }
        String hsPrice = param.get("hsPrice").toString();

        backlist.setHsPrice(hsPrice);
        backlist.setBackState(2);
        backlistMapper.updateBacklist(backlist);
        if (backlist.getGid() == null) {
            throw new ServiceException("未获取到商品信息");
        }
        GoodsInfo info = goodsInfoMapper.selectGoodsInfoById(backlist.getGid());

        //goodsInfo 用户账号信息
        if (param.get("accountNumber") != null) {
            String accountNumber = param.get("accountNumber").toString();
            info.setAccountNumber(accountNumber);
        }
        //账号
        if (param.get("accountPassword") != null) {
            String accountPassword = param.get("accountPassword").toString();
            info.setAccountPassword(accountPassword);
        }

        if (param.get("securityPhone") != null) {
            String securityPhone = param.get("securityPhone").toString();//手机
            info.setSecurityPhone(securityPhone);
        }
        if (param.get("importantRemark") != null) {
            String importantRemark = param.get("importantRemark").toString();
            info.setImportantRemark(importantRemark);
        }
        return goodsInfoMapper.updateGoodsInfo(info);
    }

    @Override
    public Map getAccountInfo(Long id) {
        Map map = new HashMap<>();
        Backlist backlist = backlistMapper.selectBacklistById(id);
        map.put("cpsId", backlist.getCpsId());
        map.put("cpsName", backlist.getCpsName());
        map.put("hsPrice", backlist.getHsPrice());
        if (ObjectUtils.isNotEmpty(backlist) && backlist.getGid() != null) {
            GoodsInfo info = goodsInfoMapper.selectGoodsInfoById(backlist.getGid());
            map.put("accountNumber", info.getAccountNumber());
            map.put("accountPassword", info.getAccountPassword());
            map.put("securityPhone", info.getSecurityPhone());
            map.put("importantRemark", info.getImportantRemark());
        }
        return map;
    }

    @Override
    public int submitGoodsInfo(Long backListId) {
        Backlist backlist = backlistMapper.selectBacklistById(backListId);
        if (ObjectUtils.isEmpty(backlist)) {
            throw new ServiceException("为获取到回收信息");
        }
        if (backlist.getGid() == null || backlist.getGid() == 0) {
            throw new ServiceException("未绑定商品信息");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(backlist.getGid());
        if (goodsInfo.getPrice() == null) {
            throw new ServiceException("商品价格未设置");
        }
        GoodsInfo info = new GoodsInfo();
        info.setId(backlist.getGid());
        info.setSaleState(1);
        info.setCreateId(SecurityUtils.getUserId());
        return goodsInfoMapper.updateGoodsInfo(info);
    }

    //创建群 拉上买方 卖方
    @Override
    public JSONObject createPublicGroup(Long id) {
        JSONObject object = new JSONObject();
//        Backlist backlist = backlistMapper.selectBacklistById(id);
//
//        if (ObjectUtils.isEmpty(backlist)) {
//            throw new ServiceException("未获取到backlist  回收信息 ");
//        }
//        Krecycle krecycle = krecycleMapper.selectKrecycleByBacklistId(backlist.getId());
//        if (StringUtils.isNotEmpty(krecycle.getGroupId())) {
//            object.put("groupId", krecycle.getGroupId());
//            object.put("message", "已创建群聊");
//            return object;
//        }
//
//        if (ObjectUtils.isEmpty(krecycle)) {
//            throw new ServiceException("未获取到交易信息");
//        }
//        SysUser user1 = userMapper.selectUserById(SecurityUtils.getUserId());
//        SysUser user2 = null;
//        if (backlist.getPartition() == 3) {
//            String from = "ls";
//            user2 = getOtherUserHX(backlist.getOtherUserId(), from);
//        } else {
//            user2 = userMapper.selectUserById(krecycle.getClientId());
//        }
//        SysUser user3 = userMapper.selectUserById(krecycle.getSuplair());
//
//        String groupName =
//                "回收" + krecycle.getId() + "_" + backlist.getType() + "_" + user2.getNickName() + "_" + user3.getNickName();
//        String description = "本群仅用于账号交易交流,您有什么疑问或者其他问题都可以在这里询问";
//        List<JSONObject> members = new ArrayList<>();
//
//        Map<String, String> map = new HashMap<>();
//        map.put(user1.getHxusername(), "owner");
//        map.put(user2.getHxusername(), "卖家");
//        map.put(user3.getHxusername(), "回收商");
//
//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject.put("hxid", user2.getHxusername());
//        jsonObject.put("type", "卖家");
//        members.add(jsonObject);
//        jsonObject1.put("hxid", user3.getHxusername());
//        jsonObject1.put("type", "回收商");
//        members.add(jsonObject1);
//
//        JSONObject createGroupParams = new JSONObject();
//        createGroupParams.put("owner", user1.getHxusername());
//        createGroupParams.put("groupName", groupName);
//        createGroupParams.put("description", description);
//        createGroupParams.put("members", members);
//
//        String group = imService.createPublicGroup(createGroupParams);
//        krecycle.setGroupId(group);
//        krecycle.setUpdateId(1L);
//
//        krecycle.setContents(JSONObject.toJSONString(map));
//        krecycleMapper.updateKrecycle(krecycle);
//
//        JSONObject params = new JSONObject();
//        params.put("toGroup", group);
//        params.put("content", "本群仅用于账号交易交流,您有什么疑问或者其他问题都可以在这里询问!");
//        params.put("senType", "text");
//        imService.sendMsg(params);
//        // 保存群信息
//        //创建群组的时候已经保存过群信息了。 现在不需要了。
////        MsgGroupInfo groupInfo = new MsgGroupInfo();
////        groupInfo.setCreateId(SecurityUtils.getUserId());
////        groupInfo.setGroupName(groupName);
////        groupInfo.setGroupDesc(description);
////        groupInfo.setToGroup(group);
////        groupInfo.setNumbers(JSONObject.toJSONString(map));
////        msgGroupInfoMapper.insertMsgGroupInfo(groupInfo);
//        object.put("groupId", group);
//        object.put("message", "建群成功");
//        object.put("data", krecycle.getContents());
        return object;
    }

    //
    @Override
    public SysUser getOtherUserHX(String otherUserId, String from) {
        String username = from + otherUserId;
        if (StringUtils.isEmpty(from)) {
            throw new GlobalException("from");
        }
        if (StringUtils.isEmpty(otherUserId)) {
            throw new GlobalException("otheruserid");
        }

        SysUser userInfo = userMapper.selectUserByUserName(username);
        if (userInfo == null) {
            userInfo = new SysUser();
            userInfo.setUserName(username);
            if (StringUtils.equals("ls", from)) {
                from = "雷神用户";
            }
            userInfo.setNickName(from + System.currentTimeMillis());
            userInfo.setPhonenumber(null);
            userInfo.setPassword(SecurityUtils.encryptPassword("123456"));
            userInfo.setUserNewType(2);
            userInfo.setAvatar("https://game.ikbh.top/IMdefaultAvatar.png");
            userInfo.setStatus("0");
            userService.insertUser(userInfo);
        }
        if (StringUtils.isEmpty(userInfo.getAvatar())) {  //给个默认头像
            userInfo.setAvatar("https://game.ikbh.top/IMdefaultAvatar.png");
        }
        if (StringUtils.isEmpty(userInfo.getHxusername())) {  //创建环信用户信息
            EMUser user = null;
            if (user == null) {
                //注册
                username = ToPinYin.toPinyin(username);
                String pwd = ToPinYin.toPinyin(username);
                username = username + System.currentTimeMillis();  //时间戳
                user = service.user().create(username, pwd).block();

                Map<String, String> map = new HashMap<>();
                map.put("nickname", userInfo.getNickName());

                if (StringUtils.isNotEmpty(userInfo.getAvatar())) {
                    map.put("avatar", userInfo.getAvatar());
                    map.put("avatarurl", userInfo.getAvatar());
                }
                try {
                    service.metadata().setMetadataToUser(user.getUsername(), map).block();
                } catch (EMException es) {
                    es.getErrorCode();
                    es.getMessage();
                }
            }
            userInfo.setHxusername(user.getUsername());
            userInfo.setHxuuid(user.getUuid());
            userMapper.updateUser(userInfo);
        }
        return userInfo;
    }

    //添加包赔商
    @Override
    @Transactional
    public void addGroupMember(Long id, Long cpsId) {
//        Compensate compensate = compensateMapper.selectCompensateById(cpsId);
//        Krecycle krecycle = krecycleMapper.selectKrecycleByBacklistId(id);
//
//        MsgGroupInfo groupInfo = msgGroupInfoMapper.selectMsgGroupInfoByGroup(krecycle.getGroupId());
//
//
//        String hxusername = compensate.getHxusername();
//        SysUser sysUser = userMapper.selectUserByHxUserName(hxusername);
//
//        if (StringUtils.isNull(groupInfo)) {
//            throw new GlobalException("获取群信息失败");
//        }
//        if (groupInfo.getNumbers().contains(compensate.getHxusername())) {
//            throw new GlobalException("包赔商已添加");
//        }
//        try {
//            imService.addGroupMember(krecycle.getGroupId(), compensate.getHxusername());
//            JSONObject content = JSONObject.parseObject(groupInfo.getNumbers());
//            content.put(compensate.getHxusername(), "包赔商");
//            krecycle.setContents(JSONObject.toJSONString(content));
//            krecycle.setBpuser(sysUser.getUserId());
//            krecycleMapper.updateKrecycle(krecycle);
//            groupInfo.setNumbers(JSONObject.toJSONString(content));
//            msgGroupInfoMapper.updateMsgGroupInfo(groupInfo);
//        } catch (Exception e) {
//            throw new ServiceException("添加包赔进群失败！");
//        }
    }

//    @Override
//    public JSONObject getOtherGroupInfo(String groupId) {
//        JSONObject object = new JSONObject();
//        Krecycle krecycle = new Krecycle();
//        krecycle.setGroupId(groupId);
//        krecycle.setOdr("id");
//        List<Krecycle> krecycles = krecycleMapper.selectKrecycleList(krecycle);
//        if (CollectionUtils.isNotEmpty(krecycles)) {
//            krecycle = krecycles.get(0);
//            if (StringUtils.isNotEmpty(krecycle.getContents())) {
//                Map<String, String> map = JSONObject.parseObject(krecycle.getContents(), new TypeReference<Map<String
//                        , String>>() {
//                });
//                List<String> collect = map.keySet().stream().collect(Collectors.toList()); //环信id集合
//                List<SysUser> users = userMapper.getHXUserList(collect);
//                for (SysUser user : users) {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("type", map.get(user.getHxusername()));
//                    jsonObject.put("username", user.getNickName());
//                    if (StringUtils.isNotEmpty(user.getAvatar())) {
//                        jsonObject.put("avartarurl", user.getAvatar());
//                    } else {
//                        jsonObject.put("avartarurl", "https://game.ikbh.top/IMdefaultAvatar.png");
//                    }
//                    object.put(user.getHxusername(), jsonObject);
//                }
//            } else {
//                throw new GlobalException("缺少群成员信息");
//            }
//        } else {
//            try {
//                EMGroup group = imService.getGroup(groupId);
//
//                if (group != null) {
//                    String owner = group.getAffiliations().getOwner();
//                    String[] members = group.getAffiliations().getMembers();
//                    List<String> collect = Arrays.stream(members).collect(Collectors.toList());
//                    collect.add(owner);
//
//                    List<SysUser> users = userMapper.getHXUserList(collect);
//                    for (SysUser user : users) {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("username", user.getNickName());
//                        if (StringUtils.isNotEmpty(user.getAvatar())) {
//                            jsonObject.put("avartarurl", user.getAvatar());
//                        } else {
//                            jsonObject.put("avartarurl", "https://game.ikbh.top/IMdefaultAvatar.png");
//                        }
//                        object.put(user.getHxusername(), jsonObject);
//                    }
//                }
//            } catch (Exception e) {
//                throw new GlobalException("群成员信息获取失败");
//            }
//        }
//        return object;
//
//    }

    @Override
    public void refuse(Map<String, Object> params) {

        Long id = MapUtils.getLong(params, "id");
        String rmsg = MapUtils.getString(params, "rmsg");

        if (Objects.isNull(id)) {
            throw new GlobalException("ID is null");
        }
        Backlist backlist = backlistMapper.selectBacklistById(id);
        if (Objects.nonNull(backlist)) {
            backlist.setStatus(5l);
            backlist.setBprice("0");
            backlist.setRmsg(rmsg);
            backlistMapper.updateBacklist(backlist);
        } else {
            throw new GlobalException("data is null");
        }
    }

    @Override
    public List<Backlist> kefuList(Backlist backlist) {
        List<Backlist> backlistList = backlistMapper.selectBacklistList(backlist);
        return backlistList;
    }

    @Override
    public PayRecord payment(Backlist backlist, PayRecord payRecordReq) {

        backlist.setSupplyUserId(payRecordReq.getSupplyUserId());
        BigDecimal depositAmount = new BigDecimal("30.00");
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("backRatio");
        for (SysDictData sysDictData : sysDictDataList) {
            if (sysDictData.getDictLabel().equals("sellerDeposit")) {
                depositAmount = new BigDecimal(sysDictData.getDictValue());
                break;
            }
        }
        backlist.setDepositAmount(depositAmount);

        PayRecord payRecord = new PayRecord();
        payRecord.setPayWay(payRecordReq.getPayWay());
        payRecord.setPayScene(payRecordReq.getPayScene());
        payRecord.setReturnUrl(payRecordReq.getReturnUrl());
        payRecord.setAmountType(payRecordReq.getAmountType());
        payRecord.setGoodsTitle(payRecordReq.getGoodsTitle());
        payRecord.setQrPayMode(payRecordReq.getQrPayMode());
        payRecord.setQrcodeWidth(payRecordReq.getQrcodeWidth());

        payRecord.setPayTotalMoney(backlist.getDepositAmount().doubleValue());
        payRecord.setOrderType(6);
        payRecord.setBusinessId(backlist.getId());
        payRecord.setNo(backlist.getId().toString());

        payRecord = payRecordService.payment(payRecord);

        backlist.setPayRecordId(payRecord.getId());
        backlist.setPayStatus(payRecord.getOrderState());
        backlistMapper.updateBacklist(backlist);
        return payRecord;
    }

    @Override
    public Backlist sucessOrder(PayRecord payRecord) {
        Backlist backlist = backlistMapper.selectBacklistById(payRecord.getBusinessId());
        backlist = changeSome(backlist, payRecord);
        return backlist;
    }

    @Override
    public Backlist queryOrderById(Backlist backlist) {
        PayRecord payRecordReq = new PayRecord();
        payRecordReq.setId(backlist.getPayRecordId());
        PayRecord payRecord = payRecordService.queryOrder(payRecordReq);
        backlist = changeSome(backlist, payRecord);
        return backlist;
    }

    /**
     * 支付成功后需要改的状态
     *
     * @param backlist
     * @param payRecord
     * @return
     */
    public Backlist changeSome(Backlist backlist, PayRecord payRecord) {
        if (StringUtils.isNotNull(payRecord) && StringUtils.isNotNull(backlist)) {
            if (payRecord.getOrderState() == 2) {
                backlist.setPayStatus(payRecord.getOrderState());
                backlist.setPayRecordId(payRecord.getId());
                backlist.setPayTime(payRecord.getPayEndTime());

                backlist.setStatus(2l);
                backlist.setBackState(2);

                //报价单  当前接受的  为已接受 其他情况为已拒绝
                Quotation quotation = new Quotation();
                quotation.setBackId(backlist.getId());
                List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);

                if (CollectionUtils.isNotEmpty(quotations)) {
                    for (Quotation q : quotations) {
                        if (Objects.equals(q.getCreateId(), backlist.getSupplyUserId())) {
                            q.setStatus("1");
                            backlist.setHsPrice(q.getBprice());
                        } else {
                            q.setStatus("2");
                        }
                    }
                    quotationMapper.bacthUpdate(quotations);
                } else {
                    throw new GlobalException("未查询到该回收账号");
                }
                backlistMapper.updateBacklist(backlist);
                backOrderService.createOrder(backlist);
            }
        }
        return backlist;
    }
}
