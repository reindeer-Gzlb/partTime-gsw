package com.ruoyi.quartz.task;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.steam.domain.SteamCollectTask;
import com.ruoyi.system.steam.service.ISteamCollectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SteamTask {
    @Autowired
    private ISteamCollectTaskService steamCollectTaskService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void steamTask() {

        List<Long> tasks = steamCollectTaskService.selectSteamCollectTaskIds();
        int error_count = 0;
        for (Long id : tasks) {
            // 如果连续失败五次以上 结束任务
            if (error_count > 5) {
                break;
            }
            int task = 0;
            try {
                task = steamCollectTaskService.restartTask(id);
            } catch (Exception e) {
            }
            if (task == 2 || task == 0) {
                error_count++;
            } else {
                error_count = 0;
            }
        }
    }
}