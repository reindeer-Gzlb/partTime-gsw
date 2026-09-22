package com.ruoyi.system.model.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.InterName;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.domain.SuprAuth;
import com.ruoyi.system.model.mapper.InterNameMapper;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.model.mapper.SuprAuthMapper;
import com.ruoyi.system.model.service.ISuprAuthService;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 供货商授权表Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-14
 */
@Service
public class SuprAuthServiceImpl implements ISuprAuthService
{
    @Resource
    private SuprAuthMapper suprAuthMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Resource
    private InterNameMapper interNameMapper;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询供货商授权表
     *
     * @param id 供货商授权表主键
     * @return 供货商授权表
     */
    @Override
    public SuprAuth selectSuprAuthById(Long id)
    {
        return suprAuthMapper.selectSuprAuthById(id);
    }

    /**
     * 查询供货商授权表列表
     *
     * @param suprAuth 供货商授权表
     * @return 供货商授权表
     */
    @Override
    public List<SuprAuth> selectSuprAuthList(SuprAuth suprAuth)
    {
        List<SuprAuth> suprAuths = suprAuthMapper.selectSuprAuthList(suprAuth);
        suprAuths.stream().forEach(su ->{
            SysUser sysUser = sysUserMapper.selectUserById(su.getCreateId());
            if (null != sysUser){
                su.setCreateName(sysUser.getNickName());
            }
            SysUser superUser = sysUserMapper.selectUserById(su.getSuprId());
            if (null != superUser){
                su.setSuprName(superUser.getNickName());
            }
            InterName interName = interNameMapper.selectInterNameById(Long.parseLong(su.getSaleId()));
            if (null != interName){
                su.setSaleId(interName.getName());
            }

            SalePartner salePartner = salePartnerMapper.selectSalePartnerById(su.getConfigId());
            if(salePartner!=null){
                su.setConfigName(salePartner.getName());
            }

//            String saleId = su.getSaleId();
//            StringBuffer sb = new StringBuffer();
//            if (!StringUtils.isNull(saleId)) {
//                String[] split = saleId.split(",");
//                if (!CollectionUtils.isEmpty(Arrays.asList(split))) {
//                    for (String id : split) {
//                        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(Long.parseLong(id));
//                        if(salePartner!=null){
//                            sb.append(salePartner.getSaleName()+" ");
//                        }
//                    }
//                }
//            }
//            su.setSaleId(sb.toString());
        });

        return suprAuths;
    }

    /**
     * 新增供货商授权表
     *
     * @param suprAuth 供货商授权表
     * @return 结果
     */
    @Override
    public int insertSuprAuth(SuprAuth suprAuth)
    {
        suprAuth.setCreateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        suprAuth.setCreateId(userId);
//        String saleId = getSaleId(suprAuth.getSaleIdAddr());
//        suprAuth.setSaleId(saleId);
        if (checkSuprExit(suprAuth.getSuprId(),suprAuth.getSaleId(),suprAuth.getConfigId())){
            throw new ServiceException("该供货商已分配接口权限");
        }
        return suprAuthMapper.insertSuprAuth(suprAuth);
    }


    private boolean checkSuprExit(Long suprId,String saleId,Long configId){

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(LCConstant.saleNameYDD);
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        List<String> saleList = sysDictDataList.stream().map(s -> s.getDictLabel()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(saleList)){
            saleList = Arrays.asList(LCConstant.saleNameYDDS);
        }

        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(configId);
        if (Objects.isNull(salePartner)){
            throw new ServiceException("该供货商已删除");
        }

        if (saleList.contains(salePartner.getSaleName())){
            return false;
        }
        SuprAuth suprAuth = new SuprAuth();
        suprAuth.setSuprId(suprId);
        suprAuth.setSaleId(saleId);
        List<SuprAuth> suprAuths = suprAuthMapper.selectSuprAuthList(suprAuth);
        if (CollectionUtils.isEmpty(suprAuths)){
            return false;
        }else {
            return true;
        }
    }

    private String getSaleId(List<Long> saleIdAddr){
        StringBuffer sale = new StringBuffer();
        if (!CollectionUtils.isEmpty(Arrays.asList(saleIdAddr))){
            for (Long aLong : saleIdAddr) {
                sale.append(aLong+",");
            }
            return sale.toString();
        }
        return null;
    }

    /**
     * 修改供货商授权表
     *
     * @param suprAuth 供货商授权表
     * @return 结果
     */
    @Override
    public int updateSuprAuth(SuprAuth suprAuth)
    {
        suprAuth.setUpdateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        suprAuth.setUpdateId(userId);
//        String saleId = getSaleId(suprAuth.getSaleIdAddr());
        suprAuth.setSaleId(suprAuth.getSaleId());
        return suprAuthMapper.updateSuprAuth(suprAuth);
    }

    /**
     * 批量删除供货商授权表
     *
     * @param ids 需要删除的供货商授权表主键
     * @return 结果
     */
    @Override
    public int deleteSuprAuthByIds(Long[] ids)
    {
        return suprAuthMapper.deleteSuprAuthByIds(ids);
    }

    /**
     * 删除供货商授权表信息
     *
     * @param id 供货商授权表主键
     * @return 结果
     */
    @Override
    public int deleteSuprAuthById(Long id)
    {
        return suprAuthMapper.deleteSuprAuthById(id);
    }

    @Override
    public SuprAuth getSupeAuthByUserId(SuprAuth suprAuth) {
        List<SuprAuth> suprAuths = suprAuthMapper.selectSuprAuthList(suprAuth);
        if (!CollectionUtils.isEmpty(suprAuths)){
            return suprAuths.get(0);
        }else {
            return new SuprAuth();
        }
    }
}
