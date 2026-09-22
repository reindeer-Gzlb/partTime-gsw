package com.ruoyi.web.controller.V2.web.conntroller.font;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.recycle.service.IKrecycleService;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Krecycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * web 我的收藏 功能
 *
 * @author zyy
 * @date 2023-05-03
 */
@Slf4j
@RestController
@RequestMapping("/h5/goods/back")
public class H5BackController extends BaseController {

    @Autowired
    private IBacklistService backlistService;
    @Autowired
    private IKrecycleService krecycleService;

    /**
     * @param tel
     * @return
     */
    @GetMapping(value = "/listForTel")
    public TableDataInfo listForTel(String tel) {
        if (StringUtils.isEmpty(tel)) {
            throw new GlobalException("用户手机号不能为空");
        }
        List<Backlist> list = backlistService.selectListH5ForTel(tel);
        return getDataTable(list);
    }

    /**
     * 本站回收链接提交账号回收信息
     */

    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody Backlist backlist) {
        backlistService.insertH5BacklistNew(backlist);
        return AjaxResult.success();
    }


    @PostMapping(value = "/saveKrecycle")
    public AjaxResult saveKrecycle(@RequestBody Krecycle krecycle) {
        return toAjax(krecycleService.insertKrecycle(krecycle));
    }
}


