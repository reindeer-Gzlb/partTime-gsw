package com.ruoyi.system.V2.inter.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.inter.mapper.ApiTaskPoolMapper;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.inter.mapper.SelectPushMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.mapper.*;
import com.ruoyi.system.model.service.AsyncService;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/22 15:03
 */
@Service
public class ApiTaskPoolServiceImpl implements IApiTaskPoolService {


    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private ApiTaskPoolMapper apiTaskPoolMapper;

    @Resource
    ThreadPoolTaskExecutor executor;

    @Resource
    private SuprAuthMapper suprAuthMapper;

    @Resource
    private SalePartnerMapper salePartnerMapper;

    @Autowired
    private AsyncService asyncService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SelectPushMapper selectPushMapper;

    @Override
    public void insertBacth(Long accountId, String type) {
        Long userId = SecurityUtils.getUserId();
        getAuth(accountId, type, userId);
    }

    @Override
    @Async
    public void insertBacth(Long accountId,Long configId ,String type, Long userId) {


        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(accountId);
        if (!Objects.isNull(goodsInfo)) {

            asyncService.excuTaskPool(accountId, configId , type, goodsInfo.getCreateId(), userId);


//            SelectPush selectPush = new SelectPush();
//            List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);

//            for (SelectPush push : selectPushes) {
//                String superId = push.getSuperId();
//                if (StringUtils.isNotEmpty(superId)) {
//                    String[] split = superId.split(",");
//                    if (Arrays.asList(split).contains(userId + "")) {
//                        String config = push.getConfig();
//                        if (StringUtils.isNotEmpty(config)) {
//                            String[] split1 = config.split(",");
//                            Arrays.asList(split1).stream().forEach(c -> {
//                                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//
//
//                                }, myThreadPoolTaskExecutor);
//                            });
//                        }
//
//                    }
//                }
//            }
        }

    }


    private void getAuth(Long accountId, String type, Long userId) {
        /**
         * 1、获取到该供货商授权的接口
         * 2、便利接口拼装保存集合
         */
//        AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(accountId);
//        if (!Objects.isNull(accountInfo)) {
//            SuprAuth suprAuth = new SuprAuth();
//            suprAuth.setSuprId(accountInfo.getCreateId());
//            List<SuprAuth> suprAuths = suprAuthMapper.selectSuprAuthList(suprAuth);
//            for (SuprAuth auth : suprAuths) {
//                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//
//                    asyncService.excuTaskPool(accountId, auth.getConfigId(), type, accountInfo.getCreateId(), userId);
//
//                }, executor);
//            }
//        }
    }


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ApiTaskPool selectApiTaskPoolById(Long id) {

        return apiTaskPoolMapper.selectApiTaskPoolById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ApiTaskPool> selectApiTaskPoolList(ApiTaskPool apiTaskPool) {
        apiTaskPool.setMaxCount(10); //最大执行次数
        apiTaskPool.setStatus(0l);

        List<ApiTaskPool> apiTaskPoolList = apiTaskPoolMapper.selectApiTaskPoolList(apiTaskPool);
        for (ApiTaskPool taskPool : apiTaskPoolList) {
            if (Objects.nonNull(taskPool.getSuperId())) {
                SysUser sysUser = sysUserMapper.selectUserById(taskPool.getSuperId());
                if (Objects.nonNull(sysUser)) {
                    taskPool.setSuperName(sysUser.getUserName());
                }
            }
            if (StringUtils.isNotEmpty(taskPool.getConfig())) {
                SalePartner salePartner = salePartnerMapper.selectSalePartnerById(Long.parseLong(taskPool.getConfig()));
                if (Objects.nonNull(salePartner)) {
                    taskPool.setConfig(salePartner.getName());
                }
            }
            Long status = taskPool.getStatus();
            if (Objects.nonNull(status) && "1".equals(status + "")) {
                taskPool.setSttatusName("未运行");
            }
            if (Objects.nonNull(status) && "0".equals("" + status)) {
                taskPool.setSttatusName("已运行");
            }
        }
        return apiTaskPoolList;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertApiTaskPool(ApiTaskPool apiTaskPool) {
        apiTaskPool.setCreateTime(DateUtils.getNowDate());
        return apiTaskPoolMapper.insertApiTaskPool(apiTaskPool);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param apiTaskPool 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateApiTaskPool(ApiTaskPool apiTaskPool) {
        apiTaskPool.setUpdateTime(DateUtils.getNowDate());
        return apiTaskPoolMapper.updateApiTaskPool(apiTaskPool);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteApiTaskPoolByIds(Long[] ids) {
        return apiTaskPoolMapper.deleteApiTaskPoolByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteApiTaskPoolById(Long id) {
        return apiTaskPoolMapper.deleteApiTaskPoolById(id);
    }

    @Override
    public List<Map<String, Object>> taskCount() {
        return apiTaskPoolMapper.taskCount();
    }
}
