package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.AddPrice;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import org.springframework.scheduling.annotation.Async;

public interface AsyncService {
   
    /**
     * 执行异步任务
     * 可以根据需求，自己加参数拟定，我这里就做个测试演示
     */
    @Async
    void excuTaskPool(Long id, Long configId, String type, Long createId, Long userId);
    /**
     * 异步新增
     */
    @Async
    void excuAddTask(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, Long configId);
}
