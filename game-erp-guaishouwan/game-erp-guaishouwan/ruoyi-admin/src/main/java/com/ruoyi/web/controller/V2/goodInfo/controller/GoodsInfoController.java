package com.ruoyi.web.controller.V2.goodInfo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.mapper.ArtRecMapper;
import com.ruoyi.system.V2.inter.service.ISysMessageService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.SysMessage;
import com.ruoyi.system.model.dto.GoodReq;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.model.vo.DeleteAllVo;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * goodsController
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/goods/info")
public class GoodsInfoController extends BaseController {
    @Autowired
    private IGoodsInfoService goodsInfoService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysMessageService sysMessageService;
    @Resource
    private ArtRecMapper artRecMapper;

    /**
     * 查询goods列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GoodsInfo goodsInfo) {
        startPage();
        List<GoodsInfo> list = goodsInfoService.selectGoodsInfoList(goodsInfo);
        return getDataTable(list);
    }

    /**
     * 导出goods列表
     */
    @Log(title = "goods", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GoodsInfo goodsInfo) {
        List<GoodsInfo> list = goodsInfoService.selectGoodsInfoList(goodsInfo);
        ExcelUtil<GoodsInfo> util = new ExcelUtil<GoodsInfo>(GoodsInfo.class);
        return util.exportExcel(list, "goods数据");
    }

    /**
     * 获取goods详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(goodsInfoService.selectGoodsInfoAndAttrValueById(id));
    }

    /**
     * 新增goods
     */
    @Log(title = "goods", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        goodsInfoAndAttrValueDto.setAccountType(1); //走发布的 都属于自营
        goodsInfoAndAttrValueDto.setSaleState(1);  //新增默认为 未出售状态
        goodsInfoService.insertGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
        return AjaxResult.success();
    }

    /**
     * 修改goods
     */
    @Log(title = "goods", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        return toAjax(goodsInfoService.updateGoodsInfoAndAttr(goodsInfoAndAttrValueDto));
    }

    /**
     * 设置特价
     */
    @Log(title = "goods", businessType = BusinessType.UPDATE)
    @PutMapping("/isOnSpecialOffer")
    public AjaxResult isOnSpecialOffer(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        return toAjax(goodsInfoService.isOnSpecialOffer(goodsInfoAndAttrValueDto));
    }

    /**
     * 删除游戏账号
     */
    @Log(title = "全部删除", businessType = BusinessType.DELETE)
    @GetMapping("/deleteAllAccount")
    public AjaxResult deleteAllAccount(DeleteAllVo deleteAllVo) {
        return toAjax(goodsInfoService.deleteAllAccount(deleteAllVo));
    }

    /**
     * 批量删除
     */
    @Log(title = "goods", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids, String pwd) {
        return toAjax(goodsInfoService.deleteGoodsInfoByIds(ids, pwd));
    }

    /**
     * 批量删除
     */
    @Log(title = "goods", businessType = BusinessType.DELETE)
    @GetMapping("down/{ids}")
    public AjaxResult down(@PathVariable Long[] ids) {
        return toAjax(goodsInfoService.down(ids));
    }

    @Log(title = "上架商品", businessType = BusinessType.UPDATE)
    @GetMapping("/upGoods/{id}")
    public AjaxResult upGoods(@PathVariable("id") Long id) {
        return toAjax(goodsInfoService.upGoodsById(id));
    }

    @Log(title = "下架商品", businessType = BusinessType.UPDATE)
    @PostMapping("/downGoods")
    public AjaxResult downGoods(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        return toAjax(goodsInfoService.downGoodsById(goodsInfoAndAttrValueDto));
    }
    @Log(title = "是否推送", businessType = BusinessType.UPDATE)
    @PostMapping("/updateIsPush")
    public AjaxResult updateIsPush(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto){
        return AjaxResult.success(goodsInfoService.updateIsPush(goodsInfoAndAttrValueDto));

    }

    @Log(title = "转为问题号", businessType = BusinessType.UPDATE)
    @PostMapping("/problemGoods")
    public AjaxResult problemGoods(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        return toAjax(goodsInfoService.problemGoods(goodsInfoAndAttrValueDto));
    }

    @Log(title = "转为找回号", businessType = BusinessType.UPDATE)
    @PostMapping("/retrieveGoods")
    public AjaxResult retrieveGoods(@RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        return toAjax(goodsInfoService.retrieveGoods(goodsInfoAndAttrValueDto));
    }
    @Log(title = "转挂售", businessType = BusinessType.UPDATE)
    @PostMapping("/editStateGoods/{id}")
    public AjaxResult editStateGoods(@PathVariable Long id) {
        return toAjax(goodsInfoService.editStateGoods(id));
    }

    @Log(title = "转分期", businessType = BusinessType.UPDATE)
    @PostMapping("/convertFQ/{id}")
    public AjaxResult convertFQ(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertFQ(id));
    }

    @Log(title = "红锁赔付", businessType = BusinessType.UPDATE)
    @PostMapping("/convertHSPF/{id}")
    public AjaxResult convertHSPF(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertHSPF(id));
    }

    @Log(title = "在手封禁", businessType = BusinessType.UPDATE)
    @PostMapping("/convertZSFJ/{id}")
    public AjaxResult convertZSFJ(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertZSFJ(id));
    }

    @Log(title = "已封禁", businessType = BusinessType.UPDATE)
    @PostMapping("/convertYFJ/{id}")
    public AjaxResult convertYFJ(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertYFJ(id));
    }

    @Log(title = "已私下", businessType = BusinessType.UPDATE)
    @PostMapping("/convertYSX/{id}")
    public AjaxResult convertYSX(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertYSX(id));
    }

    @Log(title = "赔付中", businessType = BusinessType.UPDATE)
    @PostMapping("/convertPFZ/{id}")
    public AjaxResult convertPFZ(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertPFZ(id));
    }

    @Log(title = "永久出售", businessType = BusinessType.UPDATE)
    @PostMapping("/convertYJCS/{id}")
    public AjaxResult convertYJCS(@PathVariable Long id) {
        return toAjax(goodsInfoService.convertYJCS(id));
    }
    @Log(title = "审核通过", businessType = BusinessType.UPDATE)
    @GetMapping("/auditGoods/{ids}")
    public AjaxResult auditGoods(@PathVariable Long[] ids) {
        goodsInfoService.auditGoods(ids);
        if (ids != null) {
            if (ids.length == 1) {
                GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(ids[0]);
                if (StringUtils.isNotNull(goodsInfo)) {
                    SysMessage sysMessage = new SysMessage();
                    sysMessage.setSendType(1);
                    sysMessage.setSendTypeName("商品审核通过");
                    sysMessage.setSendTitle("您提交的编号为" + goodsInfo.getId() + "的商品 审核通过");
                    sysMessage.setSendContent("您提交的编号为" + goodsInfo.getId() + "的商品 审核通过");
                    sendMessage(goodsInfo, sysMessage);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "审核不通过", businessType = BusinessType.UPDATE)
    @PostMapping("/auditGoodsFailure")
    public AjaxResult auditGoodsFailure(@RequestBody GoodReq goodReq) {
        if (StringUtils.isEmpty(goodReq.getAuditDes())) {
            throw new GlobalException("审核失败原因不能为空");
        }
        goodsInfoService.auditGoodsFailure(goodReq.getIds(), goodReq.getAuditDes());
        if (goodReq.getIds() != null) {
            if (goodReq.getIds().length == 1) {
                GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(goodReq.getIds()[0]);
                if (StringUtils.isNotNull(goodsInfo)) {
                    SysMessage sysMessage = new SysMessage();
                    sysMessage.setSendType(2);
                    sysMessage.setSendTypeName("商品审核不通过");
                    sysMessage.setSendTitle("您提交的编号为" + goodsInfo.getId() + "的商品 审核不通过");
                    sysMessage.setSendContent("您提交的编号为" + goodsInfo.getId() + "的商品 审核不通过");
                    sendMessage(goodsInfo, sysMessage);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "提号", businessType = BusinessType.UPDATE)
    @GetMapping("/submitAccount/{ids}")
    public AjaxResult submitAccount(@PathVariable Long[] ids) {
        return toAjax(goodsInfoService.submitAccount(ids));
    }

    /**
     * 查询goods列表
     */
    @GetMapping("/allList")
    public TableDataInfo allList(GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq) {

        PageDomain pageDomain = TableSupport.buildPageRequest();

        if (Objects.nonNull(goodsInfoAndAttrValueReq.getSaleState())) {
            if (goodsInfoAndAttrValueReq.getSaleState() == 1) {
                goodsInfoAndAttrValueReq.setGsState("1");
            }
            if (goodsInfoAndAttrValueReq.getSaleState() == 10) {
                goodsInfoAndAttrValueReq.setSaleState(1);
                goodsInfoAndAttrValueReq.setGsState("2");
            }
        }
        List<GoodsInfo> list = goodsInfoService.selectByResList(goodsInfoAndAttrValueReq, pageDomain);
        return getDataTable(list);
    }

    /**
     * 查询goods列表
     */
    @GetMapping("/myAccount")
    public TableDataInfo myAccount(GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(1l, userId)) {
            goodsInfoAndAttrValueReq.setCreateId(SecurityUtils.getUserId());
        }
        if (Objects.nonNull(goodsInfoAndAttrValueReq.getSaleState())) {
            if (goodsInfoAndAttrValueReq.getSaleState() == 1) {
                goodsInfoAndAttrValueReq.setGsState("1");
            }
            if (goodsInfoAndAttrValueReq.getSaleState() == 10) {
                goodsInfoAndAttrValueReq.setSaleState(1);
                goodsInfoAndAttrValueReq.setGsState("2");
            }
        }

        List<GoodsInfo> list = goodsInfoService.selectByResList(goodsInfoAndAttrValueReq, pageDomain);
        return getDataTable(list);
    }


    /**
     * 账号池
     *
     * @param goodsInfo
     * @return
     */
    @GetMapping("/poollist")
    public TableDataInfo poollist(GoodsInfo goodsInfo) {
        //零售客服已添加账号,账号池不需要展示
        startPage();
        goodsInfo.setSaleState(1);
        List<GoodsInfo> list = goodsInfoService.selectGoodsInfoList(goodsInfo);
        for (GoodsInfo info : list) {
            info.setAccountNumber(null);
            info.setAccountPassword(null);
            info.setSecurityPhone(null);
            SysUser sysUser = userService.selectUserById(info.getCreateId());
            if (Objects.nonNull(sysUser)) {
                info.setCreateName(sysUser.getNickName());
            }
        }
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        goodsInfoService.bacthAddPrice(list, supplyCustomer);
        return getDataTable(list);
    }

    @GetMapping("/countForSupply")
    public AjaxResult countForSupply(GoodsInfo goodsInfo) {
        List<Map<String, Object>> list = goodsInfoService.countForSupply(goodsInfo);
        return AjaxResult.success(list);
    }

    @GetMapping(value = "/outId/{id}")
    public AjaxResult out(@PathVariable("id") Long id) {
        return AjaxResult.success(artRecMapper.selectOutIdByGoodsId(id));
    }

    //设置是否为顶级账号
    @PostMapping(value = "/topOnOrOff")
    public AjaxResult topOnOrOff(@RequestBody GoodsInfo goodsInfo) {
        return toAjax(goodsInfoService.topOnOrOff(goodsInfo));
    }

    void sendMessage(GoodsInfo goodsInfo, SysMessage sysMessage) {
        sysMessage.setRelatedDataId(goodsInfo.getId());
        sysMessage.setSenderUserId(1l);
        sysMessage.setSenderUserName("系统");
        sysMessage.setSendTime(DateUtils.getNowDate());
        sysMessage.setReceiveUserId(goodsInfo.getCreateId());
        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (StringUtils.isNull(sysUser)) {
            sysMessage.setReceiveUserName(sysUser.getNickName());
        }
        sysMessageService.save(sysMessage);
    }

}
