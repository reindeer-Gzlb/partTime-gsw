package com.ruoyi.web.controller.V2.tool.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.pay.domain.PayRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@RestController
@RequestMapping("/account/backlist")
public class BacklistController extends BaseController {
    @Autowired
    private IBacklistService backlistService;
    @Autowired
    private BacklistMapper backlistMapper;

    /**
     * 回收联盟
     */
    @GetMapping("/list")
    public TableDataInfo list(Backlist backlist) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Backlist> list = backlistService.selectBacklistList(backlist, pageDomain);
        return getDataTable(list);
    }

    /**
     * 回收联盟 客服list
     */
    @GetMapping("/kfList")
    public TableDataInfo kfList(Backlist backlist) {
        startPage();
        List<Backlist> list = backlistService.kefuList(backlist);
        return getDataTable(list);
    }

    /**
     * 我的回收_回收链接
     */
    @GetMapping("/myList")
    public TableDataInfo myList(Backlist backlist) {
        startPage();
        List<Backlist> list = backlistService.selectMyBacklistList(backlist);
        return getDataTable(list);
    }

    /**
     * 回显
     */
    @GetMapping("/getAccountInfo")
    public AjaxResult getAccountInfo(Long id) {
        return AjaxResult.success(backlistService.getAccountInfo(id));
    }

    /**
     * 我的账号
     */
    @GetMapping("/allList")
    public TableDataInfo allList(Backlist backlist) {
        startPage();
        List<Backlist> list = backlistService.allList(backlist);
        return getDataTable(list);
    }


    /**
     *
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(backlistService.selectBacklistById(id));
    }

    /**
     *
     */
    @PostMapping
    public AjaxResult add(@RequestBody Backlist backlist) {
        return toAjax(backlistService.insertBacklist(backlist));
    }

    /**
     *
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Backlist backlist) {
        return toAjax(backlistService.updateBacklist(backlist));
    }

    /**
     *
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(backlistService.deleteBacklistByIds(ids));
    }

    /**
     * 编辑
     *
     * @param map
     * @return
     */
    @PutMapping("/editInfo")
    public AjaxResult editInfo(@RequestBody Map map) {
        return AjaxResult.success(backlistService.editCompensateInfo(map));
    }

    /**
     * 一键发布
     *
     * @param backlist
     * @return
     */
    @PutMapping("/submitGoodsInfo")
    public AjaxResult submitGoodsInfo(@RequestBody Backlist backlist) {
        return AjaxResult.success(backlistService.submitGoodsInfo(backlist.getId()));
    }

    /**
     * 拉群   买方  卖方  客服
     * id 群ID
     */
    @GetMapping("/createPublicGroup")
    public AjaxResult create(Long id) {
        return AjaxResult.success(backlistService.createPublicGroup(id));
    }

    /**
     * 拉包赔商进群
     *
     * @param id
     * @return
     */
    @GetMapping("/addGroupMember")
    public AjaxResult addUser(Long id, Long cpsId) {
        backlistService.addGroupMember(id, cpsId);
        return AjaxResult.success();

    }

    /**
     * 拒绝报价
     *
     * @param params
     * @return
     */
    @GetMapping("/refuse")
    public AjaxResult refuse(@RequestParam Map<String, Object> params) {
        backlistService.refuse(params);
        return AjaxResult.success();

    }


    /**
     * 支付方法
     *
     * @param payRecordReq
     * @return
     */
    @RepeatSubmit
    @PostMapping("/payment")
    public AjaxResult payment(@RequestBody PayRecord payRecordReq) {
        Backlist backlist = backlistService.selectBacklistById(payRecordReq.getId());
        if (StringUtils.isNull(backlist)) {
            throw new GlobalException("未找到此回收记录");
        }
        if (StringUtils.isNull(payRecordReq.getSupplyUserId())) {
            throw new GlobalException("接受的用户id不能为空");
        }
        if (StringUtils.isNull(payRecordReq.getPayWay())) {
            throw new GlobalException("参数payWay不能为空");
        }
        if (StringUtils.isNull(payRecordReq.getPayScene())) {
            throw new GlobalException("参数payScene不能为空");
        }
        return AjaxResult.success(backlistService.payment(backlist, payRecordReq));
    }

    /**
     * 查询订单
     *
     * @param backlistReq
     * @return
     */
    @RepeatSubmit(interval = 1000)
    @PostMapping("/queryOrder")
    public AjaxResult queryOrder(@RequestBody Backlist backlistReq) {
        Backlist backlist = backlistService.selectBacklistById(backlistReq.getId());
        if (StringUtils.isNull(backlist)) {
            throw new GlobalException("未找到此回收记录");
        }
        if (backlist.getPayStatus() == 1) {
            if (StringUtils.isNotNull(backlist.getPayRecordId())) {
                backlist = backlistService.queryOrderById(backlist);
            }
        }
        return AjaxResult.success(backlist);
    }

    /**
     * 获取押金支付状态
     *
     * @param backlistId
     * @return
     */
    //    @RepeatSubmit(interval = 2000)
    @GetMapping("/backlistPayStatus")
    public AjaxResult backlistPayStatus(String backlistId) {
        if (StringUtils.isEmpty(backlistId)) {
            throw new GlobalException("backlistId");
        }
        return AjaxResult.success(backlistMapper.getBacklistPayStatus(Long.valueOf(backlistId)));
    }

    /**
     * 取消回收记录
     */
    @RepeatSubmit
    @PostMapping(value = "/cancel")
    public AjaxResult cancel(@RequestBody Backlist backlistReq) {
        Backlist backlist = backlistService.selectBacklistById(backlistReq.getId());
        if (StringUtils.isNull(backlist)) {
            throw new GlobalException("未找到此回收记录");
        }
        SysUser sysUser = getLoginUser().getUser();
        if (sysUser.isAdmin() || backlist.getCreateId().equals(sysUser.getUserId())) {
            backlist.setStatus(4l);
            backlist.setUpdateTime(DateUtils.getNowDate());
            backlist.setUpdateId(sysUser.getUserId());
            backlistMapper.updateBacklist(backlist);
        }
        return AjaxResult.success();
    }

}