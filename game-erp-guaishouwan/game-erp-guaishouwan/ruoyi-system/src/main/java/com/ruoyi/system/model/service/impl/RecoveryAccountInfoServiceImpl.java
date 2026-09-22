package com.ruoyi.system.model.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.InfoImg;
import com.ruoyi.system.model.domain.RecoveryAccountInfo;
import com.ruoyi.system.model.mapper.InfoImgMapper;
import com.ruoyi.system.model.mapper.RecoveryAccountInfoMapper;
import com.ruoyi.system.model.service.IRecoveryAccountInfoService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 回收游戏账号Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-28
 */
@Service
public class RecoveryAccountInfoServiceImpl implements IRecoveryAccountInfoService
{
    @Autowired
    private RecoveryAccountInfoMapper recoveryAccountInfoMapper;

    @Autowired
    private InfoImgMapper infoImgMapper;

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 查询回收游戏账号
     * 
     * @param id 回收游戏账号主键
     * @return 回收游戏账号
     */
    @Override
    public RecoveryAccountInfo selectRecoveryAccountInfoById(Long id)
    {
        RecoveryAccountInfo accountInfo = recoveryAccountInfoMapper.selectRecoveryAccountInfoById(id);
        InfoImg infoImg = new InfoImg();
        infoImg.setInfoId(accountInfo.getId());
        infoImg.setType("3");
        List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
        List<Map<String, Object>> imgMaps = new ArrayList<>();
        for (InfoImg img : infoImgs) {
            Map<String, Object> imgMap = new HashMap<>();
            if (!StringUtils.isNull(img.getFileName()) && !StringUtils.isNull(img.getImgUrl())) {
                imgMap.put("name", img.getFileName());
                imgMap.put("url", img.getImgUrl());
                imgMaps.add(imgMap);
            }
        }
        if (!CollectionUtils.isEmpty(imgMaps)) {
            accountInfo.setUrlArr(imgMaps);
        }
        return accountInfo;
    }

    /**
     * 查询回收游戏账号列表
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 回收游戏账号
     */
    @Override
    public List<RecoveryAccountInfo> selectRecoveryAccountInfoList(RecoveryAccountInfo recoveryAccountInfo)
    {
        /**
         * 排序
         */
        if (!StringUtils.isNull(recoveryAccountInfo.getSort())) {
            String sort = recoveryAccountInfo.getSort().replace("ending", "");
            recoveryAccountInfo.setSort(sort);
        }
        if (!StringUtils.isNull(recoveryAccountInfo.getSortColumn())) {
            recoveryAccountInfo.setSortColumn(humpToLine(recoveryAccountInfo.getSortColumn()));
        }

        if (!StringUtils.isNull(recoveryAccountInfo.getDesQur())){
            String[] desArr = recoveryAccountInfo.getDesQur().split(" ");
            recoveryAccountInfo.setDesArr(Arrays.asList(desArr));
        }
        return recoveryAccountInfoMapper.selectRecoveryAccountInfoList(recoveryAccountInfo);
    }

    /**
     * 新增回收游戏账号
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 结果
     */
    @Override
    public int insertRecoveryAccountInfo(RecoveryAccountInfo recoveryAccountInfo)
    {
        int i = recoveryAccountInfoMapper.insertRecoveryAccountInfo(recoveryAccountInfo);
        //新增个人信息保存移动端批量图片
        List<InfoImg> infoImgs = recoveryAccountInfo.getInfoImgs();
        //此处选择循环插入的方式,对性能损耗不大
        for (InfoImg infoImg : infoImgs) {
            infoImg.setInfoId(recoveryAccountInfo.getId());
            infoImg.setType("3");
            infoImgMapper.insertInfoImg(infoImg);
        }
        recoveryAccountInfo.setCreateTime(DateUtils.getNowDate());
        return i;
    }

    /**
     * 修改回收游戏账号
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 结果
     */
    @Override
    public int updateRecoveryAccountInfo(RecoveryAccountInfo recoveryAccountInfo)
    {
        if (!CollectionUtils.isEmpty(recoveryAccountInfo.getUrlArr())) {
            bathAddImg(recoveryAccountInfo.getUrlArr(), recoveryAccountInfo.getId());
        }
        recoveryAccountInfo.setUpdateTime(DateUtils.getNowDate());
        return recoveryAccountInfoMapper.updateRecoveryAccountInfo(recoveryAccountInfo);
    }

    /**
     * 批量删除回收游戏账号
     * 
     * @param ids 需要删除的回收游戏账号主键
     * @return 结果
     */
    @Override
    public int deleteRecoveryAccountInfoByIds(Long[] ids)
    {
        return recoveryAccountInfoMapper.deleteRecoveryAccountInfoByIds(ids);
    }

    /**
     * 删除回收游戏账号信息
     * 
     * @param id 回收游戏账号主键
     * @return 结果
     */
    @Override
    public int deleteRecoveryAccountInfoById(Long id)
    {
        return recoveryAccountInfoMapper.deleteRecoveryAccountInfoById(id);
    }

    @Override
    public RecoveryAccountInfo selectRecoveryAccountInfoById(String accountId) {
        return recoveryAccountInfoMapper.selectAccountInfoByAccountId(accountId);
    }

    private String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }

    @Transactional
    public void bathAddImg(List<Map<String, Object>> imgs, Long infoId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        infoImgMapper.deleteByInfoId(infoId, "3");
        for (Map img : imgs) {
            String filePath = MapUtils.getString(img, "url");
            if (!StringUtils.isEmpty(filePath)) {
                InfoImg infoImg = new InfoImg();
                infoImg.setInfoId(infoId);
                String fileName = MapUtils.getString(img, "name");
                infoImg.setImgUrl(filePath);
                infoImg.setCreateId(loginUser.getUserId());
                infoImg.setFileName(fileName);
                infoImg.setType("3");
                infoImgMapper.insertInfoImg(infoImg);
            }
        }
    }
}
