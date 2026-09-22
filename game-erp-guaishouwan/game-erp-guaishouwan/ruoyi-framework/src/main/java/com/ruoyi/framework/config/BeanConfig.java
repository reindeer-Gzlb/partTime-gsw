package com.ruoyi.framework.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * @Author lz
 * @Date 2023/8/0 10:18
 * @description
 */
 
@Component
public class BeanConfig {
 
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(200);//我这里设置的线程数是2,可以根据需求调整
        return taskScheduler;
    }
}