package com.ruoyi.system.V2.financial.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.BillType;
import com.ruoyi.system.V2.financial.mapper.BillTypeMapper;
import com.ruoyi.system.V2.financial.service.IBillTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账单类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
@Service
public class BillTypeServiceImpl implements IBillTypeService
{
    @Autowired
    private BillTypeMapper billTypeMapper;

    /**
     * 查询账单类型
     * 
     * @param id 账单类型主键
     * @return 账单类型
     */
    @Override
    public BillType selectBillTypeById(Long id)
    {
        return billTypeMapper.selectBillTypeById(id);
    }

    /**
     * 查询账单类型列表
     * 
     * @param billType 账单类型
     * @return 账单类型
     */
    @Override
    public List<BillType> selectBillTypeList(BillType billType)
    {
        Long userId = SecurityUtils.getUserId();
        if (userId != 1l){
            billType.setCreateId(userId);
        }
        return billTypeMapper.selectBillTypeList(billType);
    }
    @Override
    public List<BillType> selectBillTypeListByCW(BillType billType)
    {
        billType.setCreateId(106L);
        return billTypeMapper.selectBillTypeList(billType);
    }
    /**
     * 查询账单类型列表
     *
     * @param billType 账单类型
     * @return 账单类型
     */
    @Override
    public List<BillType> selectH5BillTypeList(BillType billType)
    {
        return billTypeMapper.selectBillTypeList(billType);
    }



    private String getfilds(Integer type) {

        String str = "";
        if (Objects.equals(2,type)){
             str = "[{\"key\":\"field1\",\"name\":\"游戏类型\",\"id\":1,\"isShow\":true,\"isMust\":true},{\"key\":\"field2\",\"name\":\"大区\",\"id\":2,\"isShow\":true,\"isMust\":true},{\"key\":\"field3\",\"name\":\"号商\",\"id\":3,\"isShow\":true,\"isMust\":true},{\"key\":\"field4\",\"name\":\"自定义字段4\",\"id\":4,\"isShow\":false,\"isMust\":false},{\"key\":\"field5\",\"name\":\"自定义字段5\",\"id\":5,\"isShow\":false,\"isMust\":false},{\"key\":\"field6\",\"name\":\"自定义字段6\",\"id\":6,\"isShow\":false,\"isMust\":false}]";

        }
        if (Objects.equals(1,type)){
             str = "[{\"key\":\"field1\",\"name\":\"游戏类型\",\"id\":1,\"isShow\":true,\"isMust\":true},{\"key\":\"field2\",\"name\":\"大区\",\"id\":2,\"isShow\":true,\"isMust\":true},{\"key\":\"field3\",\"name\":\"号商\",\"id\":3,\"isShow\":true,\"isMust\":true},{\"key\":\"field4\",\"name\":\"自定义字段4\",\"id\":4,\"isShow\":false,\"isMust\":false},{\"key\":\"field5\",\"name\":\"自定义字段5\",\"id\":5,\"isShow\":false,\"isMust\":false},{\"key\":\"field6\",\"name\":\"自定义字段6\",\"id\":6,\"isShow\":false,\"isMust\":false}]";

        }
        return str;
  }

    /**
     * 新增账单类型
     * 
     * @param billType 账单类型
     * @return 结果
     */
    @Override
    public int insertBillType(BillType billType)
    {
        Long userId = SecurityUtils.getUserId();
        billType.setCreateId(userId);
        billType.setCreateTime(DateUtils.getNowDate());
        return billTypeMapper.insertBillType(billType);
    }

    /**
     * 修改账单类型
     * 
     * @param billType 账单类型
     * @return 结果
     */
    @Override
    public int updateBillType(BillType billType)
    {
        Long userId = SecurityUtils.getUserId();
        billType.setUpdateId(userId);
        billType.setUpdateTime(DateUtils.getNowDate());
        return billTypeMapper.updateBillType(billType);
    }

    /**
     * 批量删除账单类型
     * 
     * @param ids 需要删除的账单类型主键
     * @return 结果
     */
    @Override
    public int deleteBillTypeByIds(Long[] ids)
    {
        return billTypeMapper.deleteBillTypeByIds(ids);
    }

    /**
     * 删除账单类型信息
     * 
     * @param id 账单类型主键
     * @return 结果
     */
    @Override
    public int deleteBillTypeById(Long id)
    {
        return billTypeMapper.deleteBillTypeById(id);
    }
}
