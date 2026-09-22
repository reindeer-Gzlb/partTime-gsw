package com.ruoyi.system.V2.inter.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.InterRec;
import com.ruoyi.system.V2.inter.mapper.InterRecMapper;
import com.ruoyi.system.V2.inter.service.IInterRecService;
import com.ruoyi.system.outto.core.LCConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 接口记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-23
 */
@Service
public class InterRecServiceImpl implements IInterRecService
{
    @Resource
    private InterRecMapper interRecMapper;

    /**
     * 查询接口记录
     *
     * @param id 接口记录主键
     * @return 接口记录
     */
    @Override
    public InterRec selectInterRecById(Long id)
    {
        return interRecMapper.selectInterRecById(id);
    }

    /**
     * 查询接口记录列表
     *
     * @param interRec 接口记录
     * @return 接口记录
     */
    @Override
    public List<InterRec> selectInterRecList(InterRec interRec)
    {
        return interRecMapper.selectInterRecList(interRec);
    }

    /**
     * 新增接口记录
     *
     * @param interRec 接口记录
     * @return 结果
     */
    @Override
    public int insertInterRec(InterRec interRec)
    {
        interRec.setCreateTime(DateUtils.getNowDate());
        return interRecMapper.insertInterRec(interRec);
    }

    /**
     * 修改接口记录
     *
     * @param interRec 接口记录
     * @return 结果
     */
    @Override
    public int updateInterRec(InterRec interRec)
    {
        interRec.setUpdateTime(DateUtils.getNowDate());
        return interRecMapper.updateInterRec(interRec);
    }

    /**
     * 批量删除接口记录
     *
     * @param ids 需要删除的接口记录主键
     * @return 结果
     */
    @Override
    public int deleteInterRecByIds(Long[] ids)
    {
        return interRecMapper.deleteInterRecByIds(ids);
    }

    /**
     * 删除接口记录信息
     *
     * @param id 接口记录主键
     * @return 结果
     */
    @Override
    public int deleteInterRecById(Long id)
    {
        return interRecMapper.deleteInterRecById(id);
    }

    @Override
    public boolean checkInter(String saleCode, Long accountId,Long userId) {
//        InterRec interRec = new InterRec();
//        interRec.setAccountId(accountId);
//        interRec.setSaleCode(saleCode);
//        interRec.setStatus(Constants.INT_1);
////        interRec.setCreateBy(String.valueOf(userId));
//        List<InterRec> interRecs = this.interRecMapper.selectInterRecList(interRec);
//        if (CollectionUtils.isEmpty(interRecs)){
//            return true;
//        }
        return true;
    }
    @Override
    public boolean checkInter1(String saleCode, Long accountId,Long userId) {
        InterRec interRec = new InterRec();
        interRec.setAccountId(accountId);
        interRec.setSaleCode(saleCode);
        interRec.setStatus(Constants.INT_1);
//        interRec.setCreateBy(String.valueOf(userId));
        List<InterRec> interRecs = this.interRecMapper.selectInterRecList(interRec);
        if (CollectionUtils.isEmpty(interRecs)){
            return false;
        }
        return true;
    }

    @Override
    public boolean save(String saleCode, Long accountId,Long userId) {
        InterRec interRec = new InterRec();
        interRec.setAccountId(accountId);
        interRec.setSaleCode(saleCode);
        if (saleCode.contains(LCConstant.saleNameJYM)){
            interRec.setStatus(Constants.INT_0);
        }else {
            interRec.setStatus(Constants.INT_1);
        }
        interRec.setCreateId(userId);
        interRec.setCreateTime(new Date());
        this.interRecMapper.insertInterRec(interRec);
        return true;
    }

    @Override
    public int deleteInterRec(Long id, String saleName) {
         interRecMapper.deleteInterAccount(String.valueOf(id),saleName);
        return 1;
    }

}
