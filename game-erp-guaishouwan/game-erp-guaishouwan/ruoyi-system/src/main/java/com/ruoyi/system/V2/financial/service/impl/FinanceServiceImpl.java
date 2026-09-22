package com.ruoyi.system.V2.financial.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.financial.service.IFinanceService;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.recovery.mapper.GoodsRecoveryInfoMapper;
import com.ruoyi.system.recovery.service.IGoodsRecoveryInfoService;
import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;
import com.ruoyi.system.saleInfo.mapper.GoodsSaleInfoMapper;
import com.ruoyi.system.saleInfo.service.IGoodsSaleInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 财务报表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Service
public class FinanceServiceImpl implements IFinanceService {
    @Autowired
    private FinanceMapper financeMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private GoodsSaleInfoMapper goodsSaleInfoMapper;
    @Autowired
    private GoodsRecoveryInfoMapper goodsRecoveryInfoMapper;

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询财务报表
     *
     * @param id 财务报表主键
     * @return 财务报表
     */
    @Override
    public Finance selectFinanceById(Long id) {
        return financeMapper.selectFinanceById(id);
    }

    /**
     * 查询财务报表列表
     *
     * @param finance 财务报表
     * @return 财务报表
     */
    @Override
    public List<Finance> selectFinanceList(Finance finance) {

        /**
         * 排序
         */
        if (!StringUtils.isNull(finance.getSort())) {
            String sort = finance.getSort().replace("ending", "");
            finance.setSort(sort);
        }
        if (!StringUtils.isNull(finance.getSortColumn())) {
            finance.setSortColumn(humpToLine(finance.getSortColumn()));
        }

        Long userId = SecurityUtils.getUserId();
        if (userId != 1l) {
            finance.setCreateId(userId);
        }

        return financeMapper.selectFinanceList(finance);
    }

    /**
     * 查询财务报表列表
     *
     * @param finance 财务报表
     * @return 财务报表
     */
    @Override
    public Map<String, Object> data(Finance finance) {

        Long userId = SecurityUtils.getUserId();
        if (Objects.equals(userId,1L)){
            userId = null;
        }
        finance.setCreateId(userId);
        Map<String, Object> data = financeMapper.data(finance);
        int saleInfoCount = goodsSaleInfoMapper.selectGooodsSaleInfoCount(userId);
        data.put("saleInfoCount", saleInfoCount);
        int recoveryInfoCount = goodsRecoveryInfoMapper.selectGooodsRecoveryInfoCount(userId);
        data.put("recoveryInfoCount", recoveryInfoCount);
        GoodsInfo info = new GoodsInfo();

        String username = SecurityUtils.getUsername();
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("local_user_account_config");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData sysDictData : sysDictDataList) {
                if (StringUtils.isNotEmpty(sysDictData.getDictValue())) {
                    if (StringUtils.equals(sysDictData.getDictValue(), username)) {
                        userId = 93L;
                    }
                }
            }
        }

        info.setCreateId(userId);
        info.setSaleState(1);
        Map<String, Object> goodsData = goodsInfoMapper.statisticsData(info);
        data.put("costGoodsCount", goodsData.get("costGoodsCount"));
        data.put("totalGoodsCostPrice", goodsData.get("totalGoodsCostPrice"));
        data.put("totalGoodsPrice", goodsData.get("totalGoodsPrice"));
        return data;
    }

    /**
     * 查询财务报表列表
     *
     * @param finance 财务报表
     * @return 财务报表
     */
    @Override
    public List<Map<String, Object>> statisticsData(Finance finance) {

        List<Map<String, Object>> data = financeMapper.statisticsData(finance);
        return data;
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

    /**
     * 新增财务报表
     *
     * @param finance 财务报表
     * @return 结果
     */
    @Override
    public int insertFinance(Finance finance) {
        if (Objects.isNull(finance.getCreateId())) {
            Long userId = SecurityUtils.getUserId();
            finance.setCreateId(userId);
        }
        finance.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(finance.getFromName())) {
            throw new GlobalException("渠道来源为空!");
        }

        Long status = finance.getStatus();
        if (Objects.equals(status, 1l)){
            SysDictData data = null;
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("total_price");
            if (CollectionUtils.isNotEmpty(sysDictDataList)) {
                data = sysDictDataList.get(0);
            }
            if (Objects.nonNull(data)) {
                String price = data.getDictValue();
                BigDecimal bigDecimal = new BigDecimal(price);
                if (Objects.equals(finance.getIotype(), "2")) {
                    bigDecimal = bigDecimal.add(finance.getPrice());
                } else if (Objects.equals(finance.getIotype(), "1")) {
                    bigDecimal = bigDecimal.subtract(finance.getPrice());
                }
                data.setDictValue(bigDecimal.toPlainString());
                sysDictDataMapper.updateDictData(data);
            }
        }
        return financeMapper.insertFinance(finance);
    }

    @Override
    public int insertFinanceH5(Finance finance) {
        finance.setCreateTime(DateUtils.getNowDate());
        return financeMapper.insertFinance(finance);
    }

    /**
     * 修改财务报表
     *
     * @param finance 财务报表
     * @return 结果
     */
    @Override
    public int updateFinance(Finance finance) {
        Long userId = SecurityUtils.getUserId();
        finance.setUpdateId(userId);
        finance.setUpdateTime(DateUtils.getNowDate());

        Long status = finance.getStatus();
        if (Objects.equals(status, 1l)){
            SysDictData data = null;
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("total_price");
            if (CollectionUtils.isNotEmpty(sysDictDataList)) {
                data = sysDictDataList.get(0);
            }
            if (Objects.nonNull(data)) {
                String price = data.getDictValue();
                BigDecimal bigDecimal = new BigDecimal(price);
                if (Objects.equals(finance.getIotype(), "2")) {
                    bigDecimal = bigDecimal.add(finance.getPrice());
                } else if (Objects.equals(finance.getIotype(), "1")) {
                    bigDecimal = bigDecimal.subtract(finance.getPrice());
                }
                data.setDictValue(bigDecimal.toPlainString());
                sysDictDataMapper.updateDictData(data);
            }
        }

        return financeMapper.updateFinance(finance);
    }

    /**
     * 批量删除财务报表
     *
     * @param ids 需要删除的财务报表主键
     * @return 结果
     */
    @Override
    public int deleteFinanceByIds(Long[] ids, String pwd) {
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        List<String> roleKeys = roles.stream().map(s -> s.getRoleKey()).collect(Collectors.toList());

        if (!roleKeys.contains("22")){
            String securityPwd = "qwerasdfzxcv";
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("securityPwd");
            for (SysDictData dictData : sysDictDataList) {
                if ("securityPwd".equals(dictData.getDictLabel())) {
                    securityPwd = dictData.getDictValue();
                }
            }
            if (StringUtils.isNotEmpty(securityPwd)) {
                if (StringUtils.equals(pwd, securityPwd)) {

                } else {
                    throw new GlobalException("密码错误");
                }
            }
        }

        for (Long id : Arrays.asList(ids)) {
            Finance finance = financeMapper.selectFinanceById(id);
            setPrice(finance);
        }
        return financeMapper.deleteFinanceByIds(ids);
    }


    void setPrice(Finance finance) {

        Long status = finance.getStatus();
        if (Objects.equals(status, 1l)) {
            SysDictData data = null;
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("total_price");
            if (CollectionUtils.isNotEmpty(sysDictDataList)) {
                data = sysDictDataList.get(0);
            }
            if (Objects.nonNull(data)) {
                String price = data.getDictValue();
                BigDecimal bigDecimal = new BigDecimal(price);
                if (Objects.equals(finance.getIotype(), "1")) {
                    bigDecimal = bigDecimal.add(finance.getPrice());
                } else if (Objects.equals(finance.getIotype(), "2")) {
                    bigDecimal = bigDecimal.subtract(finance.getPrice());
                }
                data.setDictValue(bigDecimal.toPlainString());
                sysDictDataMapper.updateDictData(data);
            }
        }
    }

    /**
     * 删除财务报表信息
     *
     * @param id 财务报表主键
     * @return 结果
     */
    @Override
    public int deleteFinanceById(Long id) {
        return financeMapper.deleteFinanceById(id);
    }

    @Override
    public String importAccount(List<Finance> financeList) {
        Long userId = SecurityUtils.getUserId();
        for (Finance finance : financeList) {
            finance.setCreateId(userId);
            finance.setCreateTime(new Date());
            financeMapper.insertFinance(finance);
        }
        return null;
    }
}
