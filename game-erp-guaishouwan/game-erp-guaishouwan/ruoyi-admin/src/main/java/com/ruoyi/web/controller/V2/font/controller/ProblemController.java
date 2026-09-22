package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.Problem;
import com.ruoyi.system.V2.font.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/account/problem")
public class ProblemController extends BaseController
{
    @Autowired
    private IProblemService problemService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Problem problem)
    {
        startPage();
        List<Problem> list = problemService.selectProblemList(problem);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @GetMapping("/export")
    public AjaxResult export(Problem problem)
    {
        List<Problem> list = problemService.selectProblemList(problem);
        ExcelUtil<Problem> util = new ExcelUtil<Problem>(Problem.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(problemService.selectProblemById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PostMapping
    public AjaxResult add(@RequestBody Problem problem)
    {
        return toAjax(problemService.insertProblem(problem));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Problem problem)
    {
        return toAjax(problemService.updateProblem(problem));
    }

    /**
     * 删除【请填写功能名称】
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(problemService.deleteProblemByIds(ids));
    }
}
