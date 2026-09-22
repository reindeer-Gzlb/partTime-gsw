package com.ruoyi.system.V2.operatorTwoElements.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.OperatorTwoElements;

/**
 * @author zyy
 * @date 2023-05-20
 */
public interface IOperatorTwoElementsService extends IService<OperatorTwoElements> {

    /**
     * 保存查询信息并保存返回过来的信息
     *
     * @param operatorTwoElements
     * @return
     */
    boolean saveAndGetRresult(OperatorTwoElements operatorTwoElements);
}
