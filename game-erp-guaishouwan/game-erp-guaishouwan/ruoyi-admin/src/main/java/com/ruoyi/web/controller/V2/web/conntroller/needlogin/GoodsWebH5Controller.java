package com.ruoyi.web.controller.V2.web.conntroller.needlogin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsBrowseMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IGoodsBrowseService;
import com.ruoyi.system.V2.inter.service.ISysMessageService;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * web 我的收藏 功能
 *
 * @author zyy
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/need")
public class GoodsWebH5Controller extends BaseController {

    @Autowired
    private IGoodsInfoService goodsInfoService;
    @Autowired
    private IGameAttributeService gameAttributeService;
    @Autowired
    private GoodsBrowseMapper goodsBrowseMapper;
    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private OrderRecMapper orderRecMapper;
    @Autowired
    IGoodsBrowseService goodsBrowseService;

    /**
     * 官网代售
     */
    @Log(title = "goods", businessType = BusinessType.INSERT)
    @PostMapping(value = "/consignment")
    public AjaxResult consignment(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        goodsInfoAndAttrValueDto.setSaleState(0);  //官网代收默认 未审核状态
        goodsInfoAndAttrValueDto.setAccountType(2); //代售
        if (Objects.nonNull(goodsInfoAndAttrValueDto.getSystemId())) {
            GameAttribute gameAttribute =
                    gameAttributeService.selectGameAttributeById(goodsInfoAndAttrValueDto.getSystemId());
            if (Objects.nonNull(gameAttribute)) {
                goodsInfoAndAttrValueDto.setSystemName(gameAttribute.getFieldName());
            } else {
                throw new GlobalException("系统字段错误");
            }
        }
        if (Objects.nonNull(goodsInfoAndAttrValueDto.getCarrierId())) {
            GameAttribute gameAttribute =
                    gameAttributeService.selectGameAttributeById(goodsInfoAndAttrValueDto.getCarrierId());
            if (Objects.nonNull(gameAttribute)) {
                goodsInfoAndAttrValueDto.setCarrierName(gameAttribute.getFieldName());
            } else {
                throw new GlobalException("运营商字段错误");
            }
        }
        if (Objects.nonNull(goodsInfoAndAttrValueDto.getGroupId())) {
            GameAttribute gameAttribute =
                    gameAttributeService.selectGameAttributeById(goodsInfoAndAttrValueDto.getGroupId());
            if (Objects.nonNull(gameAttribute)) {
                goodsInfoAndAttrValueDto.setGroupName(gameAttribute.getFieldName());
            } else {
                throw new GlobalException("大区字段错误");
            }
        }
        if (Objects.nonNull(goodsInfoAndAttrValueDto.getServerId())) {
            GameAttribute gameAttribute =
                    gameAttributeService.selectGameAttributeById(goodsInfoAndAttrValueDto.getServerId());
            if (Objects.nonNull(gameAttribute)) {
                goodsInfoAndAttrValueDto.setServerName(gameAttribute.getFieldName());
            } else {
                throw new GlobalException("服务器字段错误");
            }
        }
        GoodsInfo goodsInfo = goodsInfoService.insertGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
//        sendMessage(goodsInfo);
        return AjaxResult.success();
    }

    /**
     * 我的商品
     *
     * @param goodsInfo
     * @return
     */
    @GetMapping("/myConsignment")
    public TableDataInfo myConsignment(GoodsInfo goodsInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        goodsInfo.setCreateId(loginUser.getUserId());
        startPage();
        List<GoodsInfo> goodsInfos = goodsInfoService.selectGoodsInfoList(goodsInfo);
        List<Long> goodsIds = goodsInfos.stream().map(GoodsInfo::getId).collect(Collectors.toList());
        //根据账号list查询账号的浏览记录
        Map<Long, Integer> browsesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            List<GoodsBrowse> browses =
                    goodsBrowseService.list(new LambdaQueryWrapper<GoodsBrowse>().select(GoodsBrowse::getGoodsId,
                            GoodsBrowse::getCount).in(GoodsBrowse::getGoodsId, goodsIds).groupBy(GoodsBrowse::getGoodsId));
            browsesMap = browses.stream().collect(Collectors.toMap(GoodsBrowse::getGoodsId,
                    GoodsBrowse::getCount));
        }
        for (GoodsInfo info : goodsInfos) {
            if (info.getSaleState() == 3) {
                OrderRec orderRec = new OrderRec();
                orderRec.setAccountid(info.getId().toString());
                List<OrderRec> orderRecs = orderRecMapper.selectOrderRecList(orderRec);
                if (CollectionUtils.isNotEmpty(orderRecs)) {
                    info.setOrderRecId(orderRecs.get(0).getId());
                    info.setOrderTime(orderRecs.get(0).getCreateTime());
                }
            }
            if (MapUtils.isNotEmpty(browsesMap)) {
                info.setBrowseNum(browsesMap.get(info.getId()));
            }
        }
        return getDataTable(goodsInfos);
    }

    /**
     * 我的商品
     *
     * @param id
     * @return
     */
    @GetMapping("/myConsignment/{id}")
    public AjaxResult myConsignmentById(@PathVariable("id") Long id) {
        if (StringUtils.isNull(id)) {
            throw new GlobalException("商品id不能为空");
        }
        GoodsInfo goodsInfo1 = goodsInfoService.selectGoodsInfoById(id);
        //根据账号list查询账号的浏览记录
        if (StringUtils.isNull(goodsInfo1)) {
            throw new GlobalException("未找到此商品");
        }
        List<GoodsBrowse> browses =
                goodsBrowseService.list(new LambdaQueryWrapper<GoodsBrowse>().select(GoodsBrowse::getGoodsId,
                        GoodsBrowse::getCount).in(GoodsBrowse::getGoodsId, goodsInfo1.getId()).groupBy(GoodsBrowse::getGoodsId));
        Map<Long, Integer> browsesMap = browses.stream().collect(Collectors.toMap(GoodsBrowse::getGoodsId,
                GoodsBrowse::getCount));
        if (goodsInfo1.getSaleState() == 3) {
            OrderRec orderRec = new OrderRec();
            orderRec.setAccountid(goodsInfo1.getId().toString());
            List<OrderRec> orderRecs = orderRecMapper.selectOrderRecList(orderRec);
            if (CollectionUtils.isNotEmpty(orderRecs)) {
                goodsInfo1.setOrderRecId(orderRecs.get(0).getId());
                goodsInfo1.setOrderTime(orderRecs.get(0).getCreateTime());
            }
            if (MapUtils.isNotEmpty(browsesMap)) {
                goodsInfo1.setBrowseNum(browsesMap.get(goodsInfo1.getId()));
            }
        }
        return AjaxResult.success(goodsInfo1);
    }

    @GetMapping("/myBrowse")
    public TableDataInfo myBrowse() {
        Long userId = SecurityUtils.getUserId();
        return getDataTable(goodsBrowseMapper.myBrowse(userId));
    }

    @GetMapping("/message/list")
    public TableDataInfo messageList(SysMessage sysMessage) {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(sysMessage.getReadState())) {
            queryWrapper.eq(SysMessage::getReadState, sysMessage.getReadState());
        }
        queryWrapper.eq(SysMessage::getReceiveUserId, userId);
        queryWrapper.orderByDesc(SysMessage::getSendTime);
        startPage();
        List<SysMessage> list = sysMessageService.list(queryWrapper);
        return getDataTable(list);
    }

    @GetMapping("/message/changeRead/{id}")
    public AjaxResult messageChangeState(@PathVariable("id") Long id) {
        SysMessage sysMessage = sysMessageService.getById(id);
        if (StringUtils.isNull(sysMessage)) {
            throw new GlobalException("未找到此消息");
        }
        if (sysMessage.getReadState() == 1 && StringUtils.isNull(sysMessage.getReadTime())) {
            sysMessage.setReadState(2);
            sysMessage.setReadTime(DateUtils.getNowDate());
            sysMessageService.updateById(sysMessage);
        }
        return AjaxResult.success(sysMessage);
    }

    void sendMessage(GoodsInfo goodsInfo) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setRelatedDataId(goodsInfo.getId());
        sysMessage.setSenderUserId(1l);
        sysMessage.setSenderUserName("系统");
        sysMessage.setSendTime(DateUtils.getNowDate());
        sysMessage.setSendType(1);
        sysMessage.setSendTypeName("商品发布");
        sysMessage.setSendTitle("您提交的商品已发布,点击查看详情");
        sysMessage.setSendContent("您提交的商品已发布,点击查看详情");
        sysMessage.setReceiveUserId(goodsInfo.getCreateId());
        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (StringUtils.isNull(sysUser)) {
            sysMessage.setReceiveUserName(sysUser.getNickName());
        }
        sysMessageService.save(sysMessage);
    }

    /**
     * 修改价格
     */
    @Log(title = "goods", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/goods/editPrice")
    public AjaxResult editPrice(@RequestBody GoodsInfo goodsInfo) {
        if (StringUtils.isNull(goodsInfo.getId())) {
            throw new GlobalException("商品id不能为空");
        }
        if (StringUtils.isNull(goodsInfo.getPrice())) {
            throw new GlobalException("修改后的价格不能为空");
        }
        GoodsInfo goodsInfoReq = new GoodsInfo();
        goodsInfoReq.setId(goodsInfo.getId());
        goodsInfoReq.setPrice(goodsInfo.getPrice());
        return toAjax(goodsInfoService.updateGoodsInfo(goodsInfoReq));
    }
}
