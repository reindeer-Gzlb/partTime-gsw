package com.ruoyi.system.model.service.impl;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.AccountPersonalDetails;
import com.ruoyi.system.model.domain.InfoImg;
import com.ruoyi.system.model.domain.RecoveryAccountInfo;
import com.ruoyi.system.model.mapper.AccountPersonalDetailsMapper;
import com.ruoyi.system.model.mapper.InfoImgMapper;
import com.ruoyi.system.model.mapper.RecoveryAccountInfoMapper;
import com.ruoyi.system.model.service.IAccountPersonalDetailsService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.info;

/**
 * 游戏账号审核包赔个人详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-08
 */
@Service
public class AccountPersonalDetailsServiceImpl implements IAccountPersonalDetailsService
{
    @Autowired(required = false)
    private AccountPersonalDetailsMapper accountPersonalDetailsMapper;

    @Autowired(required = false)
    private InfoImgMapper infoImgMapper;

    @Autowired(required = false)
    private RecoveryAccountInfoMapper recoveryAccountInfoMapper;

    /**
     * 查询游戏账号审核包赔个人详情
     *
     * @param id 游戏账号审核包赔个人详情主键
     * @return 游戏账号审核包赔个人详情
     */
    @Override
    public AccountPersonalDetails selectAccountPersonalDetailsById(Long id)
    {
        AccountPersonalDetails details = accountPersonalDetailsMapper.selectAccountPersonalDetailsById(id);
        if (null != details){
            InfoImg infoImg = new InfoImg();
            infoImg.setInfoId(details.getAccountId());
            infoImg.setType("4");
            List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
            details.setInfoImgs(infoImgs);
        }
        return details;
    }

    /**
     * 查询游戏账号审核包赔个人详情列表
     *
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 游戏账号审核包赔个人详情
     */
    @Override
    public List<AccountPersonalDetails> selectAccountPersonalDetailsList(AccountPersonalDetails accountPersonalDetails)
    {
        return accountPersonalDetailsMapper.selectAccountPersonalDetailsList(accountPersonalDetails);
    }

    /**
     * 新增游戏账号审核包赔个人详情
     *
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 结果
     */
    @Override
    public int insertAccountPersonalDetails(AccountPersonalDetails accountPersonalDetails)
    {

        //审核包赔校验游戏id是否存在,如果不再存在则提示
        RecoveryAccountInfo info = recoveryAccountInfoMapper.selectRecoveryAccountInfoById(accountPersonalDetails.getAccountId());
        if (null == info){
            throw new ServiceException("游戏id有误,请联系管理员重新获取!");
        }
        //根据游戏账号校验是否重复提交审核包赔
        AccountPersonalDetails details = new AccountPersonalDetails();
        details.setAccountId(accountPersonalDetails.getAccountId());
        AccountPersonalDetails personalDetails = accountPersonalDetailsMapper.selectAccountPersonalDetails(details);
        if (null != personalDetails){
            throw new ServiceException("您已提交审核包赔信息,请勿重复提交!");
        }
        accountPersonalDetails.setCreateTime(DateUtils.getNowDate());
        //新增个人信息保存移动端批量图片
        List<InfoImg> infoImgs = accountPersonalDetails.getInfoImgs();
        if (!CollectionUtils.isEmpty(infoImgs)){
            //此处选择循环插入的方式,对性能损耗不大
            for (InfoImg infoImg : infoImgs) {
                infoImg.setInfoId(info.getId());
                infoImg.setType("4");
                infoImgMapper.insertInfoImg(infoImg);
            }
        }
        return accountPersonalDetailsMapper.insertAccountPersonalDetails(accountPersonalDetails);
    }

    /**
     * 修改游戏账号审核包赔个人详情
     *
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 结果
     */
    @Override
    public int updateAccountPersonalDetails(AccountPersonalDetails accountPersonalDetails)
    {
        accountPersonalDetails.setUpdateTime(DateUtils.getNowDate());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountPersonalDetails.setUpdateBy(loginUser.getUsername());
        if (!CollectionUtils.isEmpty(accountPersonalDetails.getUrlArr())) {
            bathAddImg(accountPersonalDetails.getUrlArr(), accountPersonalDetails.getAccountId());
        }
        return accountPersonalDetailsMapper.updateAccountPersonalDetails(accountPersonalDetails);
    }

    /**
     * 批量删除游戏账号审核包赔个人详情
     *
     * @param ids 需要删除的游戏账号审核包赔个人详情主键
     * @return 结果
     */
    @Override
    public int deleteAccountPersonalDetailsByIds(Long[] ids)
    {
        return accountPersonalDetailsMapper.deleteAccountPersonalDetailsByIds(ids);
    }

    /**
     * 删除游戏账号审核包赔个人详情信息
     *
     * @param id 游戏账号审核包赔个人详情主键
     * @return 结果
     */
    @Override
    public int deleteAccountPersonalDetailsById(Long id)
    {
        return accountPersonalDetailsMapper.deleteAccountPersonalDetailsById(id);
    }

    @Transactional
    public void bathAddImg(List<Map<String, Object>> imgs, Long infoId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        infoImgMapper.deleteByInfoId(infoId, "4");
        for (Map img : imgs) {
            String filePath = MapUtils.getString(img, "url");
            if (!StringUtils.isEmpty(filePath)) {
                InfoImg infoImg = new InfoImg();
                infoImg.setInfoId(infoId);
                String fileName = MapUtils.getString(img, "name");
                infoImg.setImgUrl(filePath);
                infoImg.setCreateId(loginUser.getUserId());
                infoImg.setFileName(fileName);
                infoImg.setType("4");
                infoImgMapper.insertInfoImg(infoImg);
            }
        }
    }
}
