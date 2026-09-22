package com.ruoyi.web.controller.V2.im.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.im.mapper.MsgGroupInfoMapper;
import com.ruoyi.system.V2.im.mapper.SessionListMapper;
import com.ruoyi.system.V2.im.service.ISessionListService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.model.domain.SessionList;
import com.ruoyi.system.model.dto.msghistory.SessionListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会话列表(session_list)表控制层
 *
 * @author zyy
 * @date 2023-08-24 15:42:49
 */
@RestController
@RequestMapping("/sessionList")
public class SessionListController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private ISessionListService sessionListService;

    @Autowired
    private SessionListMapper sessionListMapper;
    @Autowired
    private MsgGroupInfoMapper groupInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 分页查询所有数据
     *
     * @param sessionList 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    public TableDataInfo list(SessionList sessionList) {
        //        startPage();
        if (StringUtils.isEmpty(sessionList.getFormUser())) {
            throw new GlobalException("FormUser为空");
        }
        if (StringUtils.isNull(sessionList.getDay())) {
            sessionList.setDay(2);
        }
        List<SessionListRes> sessionList1 = sessionListMapper.getSessionList(sessionList);
        List<SessionListRes> sessionListTop = sessionListMapper.getSessionListTop(sessionList);
        sessionList1.removeAll(sessionListTop);
        sessionListTop.addAll(sessionList1);
        return getDataTable(sessionListTop);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sessionListService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sessionListReq 实体对象
     * @return 新增结果
     */
    @Log(title = "SessionList", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody SessionList sessionListReq) {
        Map<String, String> sessionIdMap = new HashMap<>();
        SessionList sessionList =
                sessionListMapper.selectOne(new LambdaQueryWrapper<SessionList>().eq(SessionList::getSessionId,
                        sessionListReq.getSessionId()));
        if (StringUtils.isNull(sessionList)) {
            sessionList = new SessionList();
            if (StringUtils.isNotEmpty(sessionListReq.getToUser())) {
                sessionList.setBelongHxIds(sessionListReq.getFormUser() + "," + sessionListReq.getToUser());
                sessionList.setMsgType("1");
            } else {
                MsgGroupInfo msgGroupInfo = groupInfoMapper.selectMsgGroupInfo(sessionListReq.getSessionId());
                if (Objects.nonNull(msgGroupInfo)) {
                    sessionList.setToUser(msgGroupInfo.getToGroup());
                    sessionList.setToUserId(msgGroupInfo.getId());
                    String numbers = msgGroupInfo.getNumbers();
                    if (StringUtils.isNotEmpty(numbers)) {
                        Map<String, String> jsonMap = JSON.parseObject(numbers, new TypeReference<HashMap<String,
                                String>>() {
                        });
                        String belongHxIds = "";
                        for (String key : jsonMap.keySet()) {
                            belongHxIds += key + ",";
                        }
                        if (StringUtils.isNotEmpty(belongHxIds)) {
                            sessionList.setBelongHxIds(belongHxIds.substring(0, belongHxIds.length() - 1));
                        }
                    }
                }
                sessionList.setMsgType("2");
            }
            sessionList.setSessionId(sessionListReq.getSessionId());
            sessionList.setFormUser(sessionListReq.getFormUser());
            sessionListMapper.insert(sessionList);
        }
        sessionIdMap.put("sessionId", sessionList.getSessionId());
        return AjaxResult.success(sessionIdMap);
    }

    /**
     * 修改数据
     *
     * @param sessionList 实体对象
     * @return 修改结果
     */
    @Log(title = "SessionList", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody SessionList sessionList) {
        return toAjax(sessionListService.updateById(sessionList));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Log(title = "SessionList", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sessionListService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 置顶功能
     */
    @PostMapping(value = "/topOrCancel")
    public AjaxResult topSessionList(@RequestBody SessionList sessionList) {
        if (StringUtils.isEmpty(sessionList.getFormUser()) || StringUtils.isEmpty(sessionList.getSessionId())) {
            throw new GlobalException("sessionId不能为空");
        }
        return toAjax(sessionListService.topOrCancel(sessionList));
    }

    public static void main(String[] args) {
        String idCardNumber = "412321199201070011"; // 替换为实际的身份证号码

        try {
            // 获取出生日期
            String birthDateString = getBirthDateFromIdCard(idCardNumber);

            // 将出生日期转换为Date对象
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = dateFormat.parse(birthDateString);

            // 获取当前日期
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // 计算年龄
            int age = getAge(birthDate, currentDate);

            System.out.println("年龄：" + age + "岁");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 从身份证号中提取出生日期
    private static String getBirthDateFromIdCard(String idCardNumber) {
        // 身份证号的出生日期一般位于第7到第14位
        String birthDateStr = idCardNumber.substring(6, 14);
        return birthDateStr;
    }

    // 计算年龄
    private static int getAge(Date birthDate, Date currentDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        int birthYear = birthCalendar.get(Calendar.YEAR);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;

        // 如果当前月份小于出生月份，或者当前月份等于出生月份但是当前日期小于出生日期，则年龄减一
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        return age;
    }
}

