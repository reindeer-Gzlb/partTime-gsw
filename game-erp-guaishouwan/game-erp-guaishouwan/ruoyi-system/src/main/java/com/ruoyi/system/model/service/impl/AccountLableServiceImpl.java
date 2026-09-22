package com.ruoyi.system.model.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.AccountLable;
import com.ruoyi.system.model.domain.FieldConfig;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.model.mapper.AccountLableMapper;
import com.ruoyi.system.model.mapper.FieldConfigMapper;
import com.ruoyi.system.model.service.IAccountLableService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 账号标签Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-28
 */
@Service
public class AccountLableServiceImpl implements IAccountLableService
{
    @Resource
    private AccountLableMapper accountLableMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private FieldConfigMapper fieldConfigMapper;

    /**
     * 查询账号标签
     *
     * @param id 账号标签主键
     * @return 账号标签
     */
    @Override
    public AccountLable selectAccountLableById(Long id)
    {
        return accountLableMapper.selectAccountLableById(id);
    }

    /**
     * 查询账号标签列表
     *
     * @param accountLable 账号标签
     * @return 账号标签
     */
    @Override
    public List<AccountLable> selectAccountLableList(AccountLable accountLable)
    {
        return accountLableMapper.selectAccountLableList(accountLable);
    }

    /**
     * 新增账号标签
     *
     * @param jsonObject 账号标签
     * @return 结果
     */
    @Override
    @Transactional
    public int insertAccountLable(JSONObject jsonObject,Long userId)
    {

        Long infoId = jsonObject.getLong("infoId");
        String lableList[] = jsonObject.getString("lableList").split(",");
        List<String> strings = Arrays.asList(lableList);
        Set<String> setList = new HashSet<>();
        setList.addAll(strings);

        accountLableMapper.deleteByInfoId(infoId);
        for (String o : setList) {
            AccountLable accountLable = new AccountLable();
            accountLable.setCreateTime(DateUtils.getNowDate());
            accountLable.setCreateId(userId);
            accountLable.setInfoId(infoId);
            accountLable.setLable(o);
            if (!StringUtils.isEmpty(o)){
                accountLableMapper.insertAccountLable(accountLable);
            }
        }
        AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(infoId);
        if (Objects.nonNull(accountInfo)){
            String result = setList.stream().map(String::valueOf).collect(Collectors.joining(","));
            accountInfo.setAcclable(result);
        }
        return 1;
    }

    /**
     * 修改账号标签
     *
     * @param accountLable 账号标签
     * @return 结果
     */
    @Override
    public int updateAccountLable(AccountLable accountLable)
    {
        accountLable.setUpdateTime(DateUtils.getNowDate());
        return accountLableMapper.updateAccountLable(accountLable);
    }

    /**
     * 批量删除账号标签
     *
     * @param ids 需要删除的账号标签主键
     * @return 结果
     */
    @Override
    public int deleteAccountLableByIds(Long[] ids)
    {
        return accountLableMapper.deleteAccountLableByIds(ids);
    }

    /**
     * 删除账号标签信息
     *
     * @param id 账号标签主键
     * @return 结果
     */
    @Override
    public int deleteAccountLableById(Long id)
    {
        return accountLableMapper.deleteAccountLableById(id);
    }

    @Override
    public int oneKeyLable(Long userId) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCreateId(userId);
        accountInfo.setSaleState(1);
        List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
        int cfg[] ={1,3};
        if (!CollectionUtils.isEmpty(accountInfoList)){
            for (AccountInfo info : accountInfoList) {
                AccountLable accountLable = new AccountLable();
                accountLable.setInfoId(info.getId());
                List<AccountLable> accountLables = accountLableMapper.selectAccountLableList(accountLable);
                if (CollectionUtils.isEmpty(accountLables)){
                    List<String> result = new ArrayList<>();
                    if (!Objects.isNull(info) && !Objects.isNull(info.getDes())){
                        String[] desArr = info.getDes().split(" ");
                        if (desArr.length < 1 ){
                            continue;
                        }
                        for(int j=0; j<desArr.length; j++){
                            List<FieldConfig> fieldConfigs = fieldConfigMapper.selectFieldConfigByType(info.getType(), cfg);
                            if (CollectionUtils.isEmpty(fieldConfigs)){
                                break;
                            }
                            for (int i=0; i< fieldConfigs.size(); i++) {
                                if (!StringUtils.isEmpty(fieldConfigs.get(i).getFieldValue())){
                                    String[] fields = fieldConfigs.get(i).getFieldValue().split(",");
                                    for (int n=0; n< fields.length; n++){
                                        if (!StringUtils.isEmpty(desArr[j])){
                                            if (desArr[j] == fields[n] || desArr[j].contains(fields[n])){
                                                result.add(fields[n]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("infoId",info.getId());
                        String collect = result.stream().collect(Collectors.joining(","));
                        jsonObject.put("lableList",collect);
                        this.insertAccountLable(jsonObject,userId);
                    }
                }
            }
        }

        return 1;
    }
}
