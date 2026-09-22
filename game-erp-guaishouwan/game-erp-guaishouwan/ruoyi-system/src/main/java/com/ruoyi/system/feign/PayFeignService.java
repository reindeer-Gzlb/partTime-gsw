package com.ruoyi.system.feign;


import com.ruoyi.system.pay.vo.UnifyPayReq;
import com.ruoyi.system.pay.vo.UnifyQueryOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(name = "interface-prd")
@FeignClient(name = "interface-prd", contextId = "prd-payClient")
//@FeignClient(name = "interface-zyy" , contextId = "zyy-payClient")
//@FeignClient(name = "interface-test" , contextId = "test-payClient")
public interface PayFeignService {


    @RequestMapping("/pay/unify")
    public String unify(@RequestBody UnifyPayReq unifyPayReq);

    @RequestMapping("/pay/queryOrder")
    public String queryOrder(@RequestBody UnifyQueryOrderVo unifyQueryOrderVo);

    @RequestMapping("/pay/closeOrder")
    public String closeOrder(@RequestBody UnifyQueryOrderVo unifyQueryOrderVo);

}
