package com.ruoyi.quartz.task;


import com.ruoyi.framework.config.ThreadExecutorPoolConfig;
import com.ruoyi.quartz.service.impl.TaskService;
import com.ruoyi.system.V2.inter.mapper.SelectPushMapper;
import com.ruoyi.system.model.domain.SelectPush;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
@Slf4j
public class AddTask {

    @Autowired
    private TaskService taskService;
    @Resource
    private SelectPushMapper selectPushMapper;
    @Autowired
    private ThreadExecutorPoolConfig threadExecutorPoolConfig;

    //添加定时任务
    //标签同步
    private void synchronousLable() {
        log.info("标签同步接口");
        taskService.synchronousLable();
    }

//    @Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0/5 * * * * ?")
    private void selectPushTask() {
        SelectPush selectPush = new SelectPush();
        selectPush.setState("0");
        selectPush.setType("1");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        excuteTask(selectPushes);
    }
    @Scheduled(cron = "0/5 * * * * ?")
    private void selectPushTask7881() {
        SelectPush selectPush = new SelectPush();
        selectPush.setState("0");
        selectPush.setType("7881");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        for (SelectPush push : selectPushes) {
            taskService.selectPush(1, push.getId());
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    private void selectPushTaskKL() {
        SelectPush selectPush = new SelectPush();
        selectPush.setState("0");
        selectPush.setType("2");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        excuteTask(selectPushes);
    }
    @Scheduled(cron = "0 0/10 * * * ?")
    private void selectPushTaskWT() {
        SelectPush selectPush = new SelectPush();
        selectPush.setState("0");
        selectPush.setType("3");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        excuteTask(selectPushes);
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    private void selectPushTaskCLL() {
        SelectPush selectPush = new SelectPush();
        selectPush.setState("0");
        selectPush.setType("4");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        excuteTask(selectPushes);
    }

    private void excuteTask(List<SelectPush> selectPushes ){
        for (SelectPush selectPush : selectPushes) {
            taskService.selectPush(10, selectPush.getId());
        }


//        List<Callable<Object>> tasks = new ArrayList<>();
//        for (SelectPush push : selectPushes) {
//            tasks.add(() -> {
//
//                return null;
//            });
//        }
//        try {
//            threadExecutorPoolConfig.threadPoolTaskExecutor().invokeAll(tasks);
//            log.info("执行完成");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    @Scheduled(cron = "0 0/10 * * * ?")
    private void pushSBOGetTokenApi() {

        taskService.pushSBOGetTokenApi(101l, "tokenXyr");
    }
    @Scheduled(cron = "0 0/10 * * * ?")
    private void pushMS_JMYGetTokenApi() {
        taskService.pushJMYGetTokenApi(41l);
    }

    @Scheduled(cron = "0 0 3 * * ?")
    //标签同步
    private void delAll() {

        taskService.delAll();
    }

}
