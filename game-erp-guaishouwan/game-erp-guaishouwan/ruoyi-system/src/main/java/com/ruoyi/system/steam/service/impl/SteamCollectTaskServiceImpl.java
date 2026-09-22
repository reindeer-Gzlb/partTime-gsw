package com.ruoyi.system.steam.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.steam.SteamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.system.steam.mapper.SteamCollectTaskMapper;
import com.ruoyi.system.steam.domain.SteamCollectTask;
import com.ruoyi.system.steam.service.ISteamCollectTaskService;
//import sun.util.resources.ga.LocaleNames_ga;

/**
 * steam任务执行Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-21
 */
@Service
public class SteamCollectTaskServiceImpl implements ISteamCollectTaskService {
    @Autowired
    private SteamCollectTaskMapper steamCollectTaskMapper;

    @Autowired
    private SteamService steamService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询steam任务执行
     *
     * @param id steam任务执行主键
     * @return steam任务执行
     */
    @Override
    public SteamCollectTask selectSteamCollectTaskById(Long id) {
        return steamCollectTaskMapper.selectSteamCollectTaskById(id);
    }

    /**
     * 查询steam任务执行列表
     *
     * @param steamCollectTask steam任务执行
     * @return steam任务执行
     */
    @Override
    public List<SteamCollectTask> selectSteamCollectTaskList(SteamCollectTask steamCollectTask) {
        return steamCollectTaskMapper.selectSteamCollectTaskList(steamCollectTask);
    }

    /**
     * 查询steam任务执行列表
     *
     * @return steam任务执行
     */
    @Override
    public List<Long> selectSteamCollectTaskIds() {
        return steamCollectTaskMapper.selectSteamCollectTaskIds();
    }

    /**
     * 新增steam任务执行
     *
     * @param steamCollectTask steam任务执行
     * @return 结果
     */
    @Override
    public int insertSteamCollectTask(SteamCollectTask steamCollectTask) {

        // steam 剩余次数
        {
            SysUser user = userService.selectUserById(steamCollectTask.getCreateId());
            Integer steamNumber = user.getSteamNumber();
            if (steamNumber < 1) {
                throw new GlobalException("steam绑定帐号次数不足,请联系管理员");
            }
            steamNumber = steamNumber - 1;
            SysUser sysUser = new SysUser();
            sysUser.setUserId(user.getUserId());
            sysUser.setSteamNumber(steamNumber);
            userService.updateUserProfile(sysUser);
        }
        steamCollectTask.setCreateTime(DateUtils.getNowDate());
        return steamCollectTaskMapper.insertSteamCollectTask(steamCollectTask);
    }

    /**
     * 修改steam任务执行
     *
     * @param steamCollectTask steam任务执行
     * @return 结果
     */
    @Override
    public int updateSteamCollectTask(SteamCollectTask steamCollectTask) {
        return steamCollectTaskMapper.updateSteamCollectTask(steamCollectTask);
    }

    /**
     * 回收
     *
     * @param steamCollectTask
     * @return
     */

    @Override
    public int backList(SteamCollectTask steamCollectTask) {
        Long id = steamCollectTask.getId();
        SteamCollectTask task = new SteamCollectTask();
        task.setId(id);
        task.setStatus(3);
        task.setPhoneStatus(4);
        task.setEmailStatus(4);
        return updateSteamCollectTask(task);
    }

    /**
     * 重新校验
     *
     * @param id
     * @return
     */
    @Override
    public int restart(Long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                restartTask(id);
            }
        }).start();
        return 1;
    }

    @Override
    @Async
    public int restartTask(Long id) {
        SteamCollectTask collectTask = selectSteamCollectTaskById(id);
        collectTask.setPhoneStatus(2);
        collectTask.setEmailStatus(2);
        String steamEmailReq = collectTask.getSteamEmailReq();
        JSONObject result = steamService.getStr(steamEmailReq);
        // 如果是网络延迟错误 重新调用
        if (StringUtils.equals(result.getString("code"), "408")) {
            int count = 0;
            while (3 > count) {
                result = steamService.getStr(steamEmailReq);
                if (StringUtils.equals(result.getString("code"), "200")) {
                    break;
                }
                count++;
            }
        }

        if (StringUtils.equals(result.getString("code"), "200")) {
            String phone = result.getString("phone");
            if (StringUtils.isEmpty(phone)) {
                collectTask.setPhoneStatus(2);
            } else {
                if (StringUtils.isNotEmpty(collectTask.getSteamPhone()) && collectTask.getSteamPhone().length() > 1) {
                    String sbPhone = collectTask.getSteamPhone().substring(collectTask.getSteamPhone().length() - 2);
                    phone = phone.replace("“", "");
                    if (StringUtils.equals(phone, sbPhone)) {
                        collectTask.setPhoneStatus(1);
                    } else {
                        collectTask.setPhoneStatus(3);
                    }
                } else {
                    collectTask.setPhoneStatus(2);
                }
            }

            String email = result.getString("email");
            if (StringUtils.isEmpty(email)) {
                collectTask.setEmailStatus(2);
            } else {
                if (StringUtils.isNotEmpty(collectTask.getSteamEmail())) {
                    if (matchEmail(email, collectTask.getSteamEmail())) {
                        collectTask.setEmailStatus(1);
                    } else {
                        collectTask.setEmailStatus(3);
                    }
                } else {
                    collectTask.setEmailStatus(2);
                }
            }
            if (collectTask.getPhoneStatus() != 1 || collectTask.getEmailStatus() != 1) {
                collectTask.setStatus(2);
            } else {
                collectTask.setStatus(1);
            }
        } else {
            collectTask.setStatus(4);
        }
        collectTask.setLastTime(new Date());
        updateSteamCollectTask(collectTask);
        return collectTask.getStatus();
    }

    public static boolean matchEmail(String pattern, String email) {
        // 将模式中的 "*" 替换为正则表达式中的 ".*"
        String regex = pattern.replace("*", ".+");
        // 将整个模式转换为正则表达式
        regex = "^" + regex + "$";

        // 使用正则表达式匹配
        return email.matches(regex);
    }

    /**
     * 批量删除steam任务执行
     *
     * @param ids 需要删除的steam任务执行主键
     * @return 结果
     */
    @Override
    public int deleteSteamCollectTaskByIds(Long[] ids) {
        return steamCollectTaskMapper.deleteSteamCollectTaskByIds(ids);
    }

    /**
     * 删除steam任务执行信息
     *
     * @param id steam任务执行主键
     * @return 结果
     */
    @Override
    public int deleteSteamCollectTaskById(Long id) {
        return steamCollectTaskMapper.deleteSteamCollectTaskById(id);
    }
}
