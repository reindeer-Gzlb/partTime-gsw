package com.ruoyi.quartz.task;

import com.ruoyi.common.enums.Constant;
import com.ruoyi.framework.config.ThreadExecutorPoolConfig;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.inter.mapper.ApiTaskPoolMapper;
import com.ruoyi.system.V2.inter.mapper.ArtRecMapper;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.model.domain.ApiTaskPool;
import com.ruoyi.system.model.domain.ArtRec;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.outto.service.IOutApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DelScheduleTask {


    @Resource
    private ApiTaskPoolMapper apiTaskPoolMapper;
    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private IOutApi iOutApi;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Resource
    private ArtRecMapper artRecMapper;


    @Autowired
    private ThreadExecutorPoolConfig threadExecutorPoolConfig;

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {

        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

        /**
         * 1、循环任务池信息
         * 2、调用接口任务，成功的，修改状态
         */
        ApiTaskPool apiTaskPool = new ApiTaskPool();
        apiTaskPool.setStatus(3l);
        apiTaskPool.setCountt(1);
        apiTaskPool.setSize(100l);
        apiTaskPool.setType("edit");
        apiTaskPool.setNotPartCode("JYM");


        List<ApiTaskPool> apiTaskPoolList = apiTaskPoolMapper.selectApiTaskPoolList(apiTaskPool);

        List<Callable<Object>> tasks = new ArrayList<>();

        for (ApiTaskPool taskPool : apiTaskPoolList) {
            tasks.add(() -> {
                String type = taskPool.getType();
                if (true) {
                    if (Constant.edit.equals(type)) {
                        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(taskPool.getAccountId());
                        if (Objects.nonNull(goodsInfo)) {

                            iOutApi.edit(taskPool.getAccountId(), Long.parseLong(taskPool.getConfig()), taskPool.getId());
                        } else {
                            taskPool.setStatus(0l);
                            apiTaskPoolMapper.updateApiTaskPool(taskPool);
                        }
                    }
                } else {
                    taskPool.setStatus(0l);
                    apiTaskPoolMapper.updateApiTaskPool(taskPool);
                }
                return null;
            });
        }
        try {
            threadExecutorPoolConfig.threadPoolTaskExecutor().invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    public void configureTasksDel() {

        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

        List<Callable<Object>> tasks = new ArrayList<>();
        List<Map<String, Object>> accountInfoList = accountInfoMapper.buchang();
        for (Map map : accountInfoList) {
            tasks.add(() -> {
                Long id = MapUtils.getLong(map, "id");
                ArtRec artRec = new ArtRec();
                artRec.setAccountId(id);
                List<ArtRec> artRecs = artRecMapper.selectArtRecList(artRec);
                for (ArtRec rec : artRecs) {
                    iOutApi.del(id, rec.getConfig(), 1, null);
                    apiTaskPoolMapper.deleteApiTaskPoolByConfigAndId(id,rec.getConfig(),"del");
                }
                return null;
            });
        }
        try {
            threadExecutorPoolConfig.threadPoolTaskExecutor().invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //交易猫任务
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasksJYM() {

        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

        /**
         * 1、循环任务池信息
         * 2、调用接口任务，成功的，修改状态
         */
        ApiTaskPool apiTaskPool = new ApiTaskPool();
        apiTaskPool.setStatus(3l);
        apiTaskPool.setCountt(1);
        apiTaskPool.setSize(100l);
        apiTaskPool.setPartCode("JYM");

        List<ApiTaskPool> apiTaskPoolList = apiTaskPoolMapper.selectApiTaskPoolList(apiTaskPool);

        List<Callable<Object>> tasks = new ArrayList<>();
        for (ApiTaskPool taskPool : apiTaskPoolList) {
            tasks.add(() -> {
                String type = taskPool.getType();
                if (true) {
                    GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(taskPool.getAccountId());
                    if (Objects.nonNull(goodsInfo)) {

                        ArtRec artRec = new ArtRec();
                        artRec.setAccountId(goodsInfo.getId());
                        artRec.setConfig(Long.parseLong(taskPool.getConfig()));
                        List<ArtRec> artRecs = artRecMapper.selectArtRecList(artRec);
                        if (CollectionUtils.isNotEmpty(artRecs)) {
                            iOutApi.del(goodsInfo.getId(), Long.parseLong(taskPool.getConfig()), 1, taskPool.getId());
                        }
                    } else {
                        taskPool.setStatus(0l);
                        apiTaskPoolMapper.updateApiTaskPool(taskPool);
                    }
                } else {
                    taskPool.setStatus(0l);
                    apiTaskPoolMapper.updateApiTaskPool(taskPool);
                }
                return null;
            });
        }
        try {
            threadExecutorPoolConfig.threadPoolTaskExecutor().invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}