package com.ruoyi.web.controller.V2.web.conntroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.font.mapper.CheckAccountMapper;
import com.ruoyi.system.V2.font.mapper.QqGroupMapper;
import com.ruoyi.system.V2.order.mapper.SubmitPriceMapper;
import com.ruoyi.system.V2.recycle.mapper.AttributeMapper;
import com.ruoyi.system.V2.recycle.mapper.EquationMapper;
import com.ruoyi.system.V2.recycle.service.IAttributeService;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.gen.IGenAPI;
import com.ruoyi.system.h5.GetGameTypeH5;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysNoticeService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 18:34
 */
@RestController
@RequestMapping("/V2/H5")
public class V2H5Controller extends BaseController {

    @Resource
    private AttributeMapper attributeMapper;
    @Resource
    private IAttributeService attributeService;
    @Resource
    private EquationMapper equationMapper;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private ISysNoticeService noticeService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private QqGroupMapper qqGroupMapper;

    @Resource
    private SubmitPriceMapper submitPriceMapper;

    @Resource
    private CheckAccountMapper accountMapper;

    //    @Autowired
//    private IRentAccountService rentAccountService;
//    @Autowired
//    private IRentOrderService iRentOrderService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;


    @Autowired
    private IGenAPI iGenAPI;

    /**
     * 客服图片
     */
    @ResponseBody
    @GetMapping("/kefuCode")
    public String kefuCode() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("kefuCode");
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);

//        sysDictData.setDictType("gameLogo");
//        List<SysDictData> gameLogos = dictDataService.selectDictDataList(sysDictData);

        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sysDictDataList)) {
            return sysDictDataList.get(0).getDictValue();
        } else {
            return null;
        }
    }

    /**
     * 筛选列表
     */
    @ResponseBody
    @GetMapping("/screenList")
    public List<JSONObject> screenList(@RequestParam Map<String, Objects> params) {
        List<JSONObject> result = new ArrayList<>();

        String type = MapUtils.getString(params, "type");
        Attribute attribute = new Attribute();
        attribute.setType(type);
        attribute.setParentId(0l);
        List<String> fieldTypes = new ArrayList<>();
        fieldTypes.add("checkbox");
        fieldTypes.add("regional");
        fieldTypes.add("interval");
        attribute.setFieldTypes(fieldTypes);
        List<Attribute> attributes = attributeMapper.selectAttributeList(attribute);
        for (Attribute attr : attributes) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("attr", attr.getFieldName());
            jsonObject.put("attrType", attr.getFieldType());
            attribute = new Attribute();
            attribute.setParentId(attr.getId());
            attributes = attributeMapper.selectAttributeList(attribute);

            List<Map<String, String>> attValue = new ArrayList<>();
            for (Attribute att : attributes) {
                Map<String, String> map = new HashMap<>();
                map.put("imgUrl", att.getImgUrl());
                map.put("fieldValue", att.getFieldName());
                attValue.add(map);
            }
            jsonObject.put("attValue", attValue);
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * label列表
     */
    @ResponseBody
    @GetMapping("/hotWord")
    public List<String> hotWord(@RequestParam Map<String, Objects> params) {
        //游戏类型
        String type = MapUtils.getString(params, "type");

        Attribute attribute = new Attribute();
        attribute.setType(type);
        attribute.setFieldType(Constant.hotWord);
        List<Attribute> attributes = attributeMapper.selectAttributeList(attribute);
        if (!CollectionUtils.isEmpty(attributes)) {

            attribute = new Attribute();
            attribute.setParentId(attributes.get(0).getId());
            attributes = attributeMapper.selectAttributeList(attribute);
            List<String> collect = attributes.stream().map(a -> a.getFieldName()).collect(Collectors.toList());
            return collect;
        }
        return new ArrayList<>();
    }


    /**
     * 租号列表
     */
//    @ResponseBody
//    @GetMapping("/zuHaoList")
//    public H5PageDomain GetZHList(Hreq hreq, HttpServletRequest request)
//    {
//        H5PageDomain h5PageDomain = rentAccountService.zuList(hreq, request);
//        return h5PageDomain;
//    }
    /**
     * 查询游戏账号列表
     */
//    @ResponseBody
//    @GetMapping("/getZuHao")
//    public GameDetails getZuHao(Long  zhid)
//    {
//        GameDetails gameDetails = rentAccountService.selectGameDetails(zhid);
//        return gameDetails;
//    }
    /**
     * 提交游戏账号
     */
//    @ResponseBody
//    @PostMapping("/submitZuHaoOrder")
//    public AjaxResult submitZuHaoOrder(@RequestBody RentOrder rentOrder)
//    {
//        rentOrder.setOdrstatus("待出租");
//        int i = iRentOrderService.insertRentOrder(rentOrder);
//        if (i > 0){
//            return  AjaxResult.success();
//        }else {
//            return  AjaxResult.error();
//        }
//    }

    /**
     * 查询游戏配置列表
     */
    @GetMapping("/attrList")
    public List<Attribute> attrList(@RequestParam Map<String, Objects> params) {

        String type = MapUtils.getString(params, "type");

        Attribute attribute = new Attribute();
        attribute.setType(type);
        List<String> fieldTypes = new ArrayList<>();
        fieldTypes.add("regional");
        fieldTypes.add("dan");
        fieldTypes.add("radio");
        fieldTypes.add("input");
        fieldTypes.add("checkbox");
        attribute.setFieldTypes(fieldTypes);
        List<Attribute> attributeList = attributeMapper.selectAttributeList(attribute);
        for (Attribute attr : attributeList) {
            List<Attribute> byPrentId = attributeMapper.getByPrentId(attr.getId());
            attr.setAttributes(byPrentId);
        }
        return attributeList;
    }

    /**
     * 估价计算
     */
    @PostMapping("/valuation")
    public BigDecimal valuation(@RequestBody JSONObject jsonObject) {

      /*
        {
        type: "王者荣耀"
         attr:[{
             id:1,
             fieldName: 王者皮肤
             valuNum: 100

        }]
        }
      */


        String type = MapUtils.getString(jsonObject, "type");

        Equation equation = new Equation();
        equation.setType(type);
        List<Equation> equations = equationMapper.selectEquationList(equation);
        if (!CollectionUtils.isEmpty(equations)) {
            equation = equations.get(0);
        }
        //计算公式
        String formul = equation.getFormul();

        Object attrList = jsonObject.get("attrList");
        String aList = JSON.toJSONString(attrList);

        JSONArray objects = JSONArray.parseArray(aList);
        for (Object object : objects) {
            JSONObject attr = JSONObject.parseObject(JSONObject.toJSONString(object));
            String fieldName = attr.getString("fieldName");
            String valuNum = attr.getString("valuNum");
            if (formul.contains(fieldName)) {
                formul = formul.replace(fieldName, valuNum);
            }
        }
        logger.info(formul);
        boolean checkcountname = checkcountname(formul);
        if (checkcountname) {
            throw new GlobalException("公式错误，请联系管理员");
        }

        //开始计算
        JexlEngine jexl = new JexlEngine();
        Expression expression = jexl.createExpression(formul);
        JexlContext jc = new MapContext();
        String result = expression.evaluate(jc).toString();
        BigDecimal bNum = new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return bNum;
    }


    private boolean checkcountname(String countname) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 估价计算
     */
    @PostMapping("/mergeImage")
    public Map<String, Object> mergeImage(@RequestBody JSONObject jsonObject) {
        Map<String, Object> result = attributeService.mergeImage(jsonObject);
        return result;
    }

    @ResponseBody
    @GetMapping("/roleList")
    public AjaxResult GameType(@RequestParam Map<String, Object> params) {
        //459926613
        List<JSONObject> result = iGenAPI.getRoleId(MapUtils.getString(params, "campId"));
        return AjaxResult.success(result);
    }

    //查询当前登陆人的剩余次数
    @ResponseBody
    @GetMapping("/residueNumber")
    public AjaxResult residueNumber(@RequestParam Map<String, Object> params) {
        Long userid = getUserId();
        SysUser sysUser = sysUserMapper.selectUserById(userid);
        int number = 0;
        if (Objects.nonNull(sysUser.getCampNumber())) {
            number = sysUser.getCampNumber();
        }
        return AjaxResult.success(number);
    }

    //王者营地获取
    @ResponseBody
    @GetMapping("/gengerTitle")
    public AjaxResult gengerTitle(@RequestParam Map<String, Object> params) {
        //459926613
        //459926613
        Map<String, Object> stringObjectMap = iGenAPI.genTitleBack(params);
        return AjaxResult.success(stringObjectMap);
    }

    @ResponseBody
    @GetMapping("/getQRcode")
    public AjaxResult getQRcode(@RequestParam Map<String, Object> params) {
        //459926613
        //459926613
        Map<String, Object> stringObjectMap = iGenAPI.getQRcode(params);
        return AjaxResult.success(stringObjectMap);
    }

    //和平营地获取
    @ResponseBody
    @GetMapping("/getHPDatacode")
    public AjaxResult getHPDatacode(@RequestParam Map<String, Object> params) {
        //459926613
        //459926613
        Map<String, Object> stringObjectMap = iGenAPI.getHPDatacode(params);
        return AjaxResult.success(stringObjectMap);
    }

    @GetMapping("/getToken")
    public AjaxResult getToken() {
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("yingdiToken");
        String token = sysDictDataList.get(0).getDictValue();
        return AjaxResult.success(token);
    }

    //公告列表
    @ResponseBody
    @GetMapping("/gonggaoDetails")
    public List<SysNotice> backDetails(SysNotice sysNotice) {
        sysNotice.setStatus("0");
        List<SysNotice> sysNotices = noticeService.selectNoticeList(sysNotice);
        return sysNotices;
    }


    //找回案例详情
    @ResponseBody
    @GetMapping("/backDetails")
    public List<SysNotice> backDetails() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeType("100");
        sysNotice.setStatus("0");
        List<SysNotice> sysNotices = noticeService.selectNoticeList(sysNotice);
        return sysNotices;
    }

    /**
     * 公告详情
     */
    @ResponseBody
    @GetMapping("/noticeDetails/{id}")
    public SysNotice noticeDetails(@PathVariable("id") String id) {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeType(id);
        sysNotice.setStatus("0");
        List<SysNotice> sysNotices = noticeService.selectNoticeList(sysNotice);
        if (!CollectionUtils.isEmpty(sysNotices)) {
            return sysNotices.get(0);
        } else {
            return null;
        }
    }

    //分销选择的游戏
    @PostMapping("/getGameType")
    public List<GetGameTypeH5> getGameType(@RequestBody Map<String, Object> params) {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("game_type");
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);

        Long id = MapUtils.getLong(params, "id");
        RetailAddPrice addPrice = new RetailAddPrice();
        addPrice.setCreateId(id);
        List<RetailAddPrice> addPrices = retailAddPriceMapper.selectRetailAddPriceList(addPrice);
        Set<String> gameList = new HashSet<>();
        for (RetailAddPrice price : addPrices) {
            String gtype = price.getGametype();
            if (StringUtils.isNotEmpty(gtype)) {
                String[] split = gtype.split(",");
                for (String s : split) {
                    gameList.add(s);
                }
            }
        }

        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        for (SysDictData dictData : sysDictDataList) {
            for (String gameType : gameList) {
                if (Objects.equals(gameType, dictData.getDictLabel())) {
                    GetGameTypeH5 getGameTypeH5 = new GetGameTypeH5();
                    getGameTypeH5.setDataDictionary_ID(dictData.getDictCode());
                    getGameTypeH5.setDetail_Code(dictData.getDictType());
                    getGameTypeH5.setDetail_ID(dictData.getDictCode());
                    getGameTypeH5.setDetail_Name(dictData.getDictLabel());
                    getGameTypeH5.setDetail_Value(dictData.getDictValue());
                    getGameTypeH5.setImg(dictData.getRemark());
                    getGameTypeH5s.add(getGameTypeH5);
                }
            }
        }
        return getGameTypeH5s;
    }

    //交易群
    @ResponseBody
    @GetMapping("/qqGroup")
    public List<QqGroup> qqGroup() {
        QqGroup qqGroup = new QqGroup();
        List<QqGroup> qqGroups = qqGroupMapper.selectQqGroupList(qqGroup);
        return qqGroups;
    }

    //客服
    @ResponseBody
    @GetMapping("/account")
    public List<CheckAccount> account(CheckAccount checkAccount) {
        checkAccount.setState("0");
        List<CheckAccount> checkAccounts = accountMapper.selectCheckAccountNameNotEmptyList(checkAccount);
        checkAccounts.stream().forEach(c -> {
            if (StringUtils.isNotNull(c.getCustomer())) {
                SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(c.getCustomer()));
                if (null != sysUser) {
                    c.setCustomerName(sysUser.getNickName());
                }
            }
        });
        return checkAccounts;
    }

    /**
     * 客服验证
     */
    @ResponseBody
    @GetMapping("/checkAccount")
    public AjaxResult checkAccount(CheckAccount checkAccount) {
        checkAccount.setState("0");
        List<CheckAccount> checkAccounts = accountMapper.selectCheckAccountList(checkAccount);
        if (!CollectionUtils.isEmpty(checkAccounts)) {
            return AjaxResult.success("账号正常");
        } else {
            return AjaxResult.error("账号异常");
        }
    }

    @ResponseBody
    @PostMapping("/submitPrice")
    public AjaxResult submitPrice(@RequestBody SubmitPrice submitPrice) {

        int i = submitPriceMapper.insertSubmitPrice(submitPrice);
        if (i > 0) {
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }
}
