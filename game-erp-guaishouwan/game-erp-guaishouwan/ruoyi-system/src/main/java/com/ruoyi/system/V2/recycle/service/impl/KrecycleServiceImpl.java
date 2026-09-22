package com.ruoyi.system.V2.recycle.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.V2.font.mapper.QuotationMapper;
import com.ruoyi.system.V2.recycle.mapper.KrecycleMapper;
import com.ruoyi.system.V2.recycle.service.IKrecycleService;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Krecycle;
import com.ruoyi.system.model.domain.Quotation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Service
public class KrecycleServiceImpl implements IKrecycleService {
    @Autowired
    private KrecycleMapper krecycleMapper;
    @Autowired
    private BacklistMapper backlistMapper;
    @Autowired
    private QuotationMapper quotationMapper;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Krecycle selectKrecycleById(Long id) {
        return krecycleMapper.selectKrecycleById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param krecycle 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Krecycle> selectKrecycleList(Krecycle krecycle) {
        return krecycleMapper.selectKrecycleList(krecycle);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param krecycle 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public int insertKrecycle(Krecycle krecycle) {
        krecycle.setCreateTime(DateUtils.getNowDate());
        krecycle.setCreateId(SecurityUtils.getUserId());
        krecycle.setClientId(SecurityUtils.getUserId());
        krecycle.setStatus("2");

        //报价账号   状态修改 为  已接受


        Long backId = krecycle.getBackId();
        Backlist backlist = backlistMapper.selectBacklistById(backId);
        if (Objects.isNull(backlist)) {
            throw new GlobalException("未查询到该回收账号");
        }
        backlist.setStatus(2l);
        krecycle.setTel(backlist.getTel());

        //报价单  当前接受的  为已接受 其他情况为已拒绝
        Quotation quotation = new Quotation();
        quotation.setBackId(backId);
        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);

        if (CollectionUtils.isNotEmpty(quotations)) {
            for (Quotation q : quotations) {
                if (Objects.equals(q.getCreateId(), krecycle.getSuplair())) {
                    q.setStatus("1");
                } else {
                    q.setStatus("2");
                }
            }
            quotationMapper.bacthUpdate(quotations);
        } else {
            throw new GlobalException("未查询到该回收账号");
        }
        return krecycleMapper.insertKrecycle(krecycle);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param krecycle 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateKrecycle(Krecycle krecycle) {
        krecycle.setUpdateTime(DateUtils.getNowDate());

        krecycle.setUpdateId(SecurityUtils.getUserId());

        Long backId = krecycle.getBackId();
        Backlist backlist = backlistMapper.selectBacklistById(backId);
        backlist.setStatus(Long.parseLong(krecycle.getStatus()));
        backlistMapper.updateBacklist(backlist);

        Quotation quotation = new Quotation();
        quotation.setBackId(backId);
        quotation.setStatus("1");
        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
        if (CollectionUtils.isNotEmpty(quotations)) {
            Quotation q = quotations.get(0);
            q.setStatus(krecycle.getStatus());
            quotationMapper.updateQuotation(q);
        }
        return krecycleMapper.updateKrecycle(krecycle);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKrecycleByIds(Long[] ids) {
        return krecycleMapper.deleteKrecycleByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKrecycleById(Long id) {
        return krecycleMapper.deleteKrecycleById(id);
    }

    @Override
    public int insertH5Krecycle(Krecycle krecycle) {
        SysUser sysUser = userMapper.checkPhoneUnique(krecycle.getTel());
        if (Objects.isNull(sysUser)) {
            throw new GlobalException("此手机号未找到用户");
        }
        krecycle.setCreateTime(DateUtils.getNowDate());
        krecycle.setCreateId(sysUser.getUserId());
        krecycle.setClientId(sysUser.getUserId());
        krecycle.setStatus("2");

        //报价账号   状态修改 为  已接受


        Long backId = krecycle.getBackId();
        Backlist backlist = backlistMapper.selectBacklistById(backId);
        if (Objects.isNull(backlist)) {
            throw new GlobalException("未查询到该回收账号");
        }
        backlist.setStatus(2l);

        //报价单  当前接受的  为已接受 其他情况为已拒绝
        Quotation quotation = new Quotation();
        quotation.setBackId(backId);
        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);

        if (CollectionUtils.isNotEmpty(quotations)) {
            for (Quotation q : quotations) {
                if (Objects.equals(q.getCreateId(), krecycle.getSuplair())) {
                    q.setStatus("1");
                } else {
                    q.setStatus("2");
                }
            }
            quotationMapper.bacthUpdate(quotations);
        } else {
            throw new GlobalException("未查询到该回收账号");
        }
        return krecycleMapper.insertKrecycle(krecycle);
    }
}
