package com.ruoyi.quartz.task;


import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.model.domain.OrderRec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
@Slf4j
public class PayTask {

    @Resource
    IOrderRecService orderRecService;
    @Resource
    OrderRecMapper orderRecMapper;


    @Scheduled(cron = "*/5 * * * * ?")
    private void updateTimeOutOrderRec() {
        List<OrderRec> orderRecs = orderRecMapper.selectTimeOutOrder(new OrderRec());
        for (OrderRec orderRec : orderRecs) {
            if (StringUtils.isNotEmpty(orderRec.getRemake())) {
                orderRec.setRemake(orderRec.getRemake() + "--超时取消订单");
            } else {
                orderRec.setRemake("--超时取消订单");
            }
            orderRec.setChangeGoodsSaleState(1);
            orderRecService.myOrderCancel(orderRec);
        }
//        int i = orderRecMapper.updateTimeOut();
//        log.error("超时关闭的数量" + i);
    }
}
