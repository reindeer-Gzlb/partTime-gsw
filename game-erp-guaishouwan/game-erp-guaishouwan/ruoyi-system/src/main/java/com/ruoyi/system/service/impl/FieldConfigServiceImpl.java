package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.FieldConfig;
import com.ruoyi.system.model.domain.Skin;
import com.ruoyi.system.model.mapper.FieldConfigMapper;
import com.ruoyi.system.model.mapper.SkinMapper;
import com.ruoyi.system.service.IFieldConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 游戏类型字段配置Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-03
 */
@Service
public class FieldConfigServiceImpl implements IFieldConfigService
{
    @Resource
    private FieldConfigMapper fieldConfigMapper;
    @Resource
    private SkinMapper skinMapper;

    /**
     * 查询游戏类型字段配置
     *
     * @param id 游戏类型字段配置主键
     * @return 游戏类型字段配置
     */
    @Override
    public FieldConfig selectFieldConfigById(Long id)
    {
        FieldConfig fieldConfig = fieldConfigMapper.selectFieldConfigById(id);
        Skin skin = new Skin();
        skin.setConfigid(id);
        List<Skin> skins = skinMapper.selectSkinList(skin);
        List<Map<String,Object>> domains = new ArrayList<>();

        for (Skin sk : skins) {
            Map<String,Object> skMap = new HashMap<>();
            skMap.put("price",sk.getSkinPrice());
            skMap.put("imgUrl",sk.getImg());
            skMap.put("value",sk.getTitle());
            domains.add(skMap);
        }
        fieldConfig.setDomains(domains);
        return fieldConfig;
    }

    /**
     * 查询游戏类型字段配置列表
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 游戏类型字段配置
     */
    @Override
    public List<FieldConfig> selectFieldConfigList(FieldConfig fieldConfig)
    {
        return fieldConfigMapper.selectFieldConfigList(fieldConfig);
    }


    @Override
    public List<FieldConfig> selectFieldConfigByType(String type) {
        int cfg[] ={0,3};
        return fieldConfigMapper.selectFieldConfigByType(type,cfg);
    }

    /**
     * 新增游戏类型字段配置
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 结果
     */
    @Override
    public int insertFieldConfig(FieldConfig fieldConfig)
    {
        fieldConfig.setCreateTime(DateUtils.getNowDate());
        fieldConfig.setCreateBy(SecurityUtils.getUserId().toString());
        return fieldConfigMapper.insertFieldConfig(fieldConfig);
    }

    /**
     * 修改游戏类型字段配置
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 结果
     */
    @Override
    public int updateFieldConfig(FieldConfig fieldConfig)
    {
        fieldConfig.setUpdateTime(DateUtils.getNowDate());
        return fieldConfigMapper.updateFieldConfig(fieldConfig);
    }

    /**
     * 批量删除游戏类型字段配置
     *
     * @param ids 需要删除的游戏类型字段配置主键
     * @return 结果
     */
    @Override
    public int deleteFieldConfigByIds(Long[] ids)
    {
        return fieldConfigMapper.deleteFieldConfigByIds(ids);
    }

    /**
     * 删除游戏类型字段配置信息
     *
     * @param id 游戏类型字段配置主键
     * @return 结果
     */
    @Override
    public int deleteFieldConfigById(Long id)
    {
        return fieldConfigMapper.deleteFieldConfigById(id);
    }

    @Override
    public List<FieldConfig> selectFieldConfigByLbelType(String type) {

        int cfg[] ={1,3};
        List<FieldConfig> fieldConfigs = fieldConfigMapper.selectFieldConfigByType(type, cfg);
        fieldConfigs.stream().forEach(field ->{
            field.setFields(Arrays.asList(field.getFieldValue().split(",")));
        });
        return fieldConfigs;
    }

    @Override
    public int addJson(JSONObject params) {

        /**
         * cfgType
         * domains
         * domains  imgUrl   price   value
         *  fieldType
         *  remark
         *
         */

        String cfgType = params.getString("cfgType");
        String fieldType = params.getString("fieldType");
        String remark = params.getString("remark");
        String type = params.getString("type");
        String fieldName = params.getString("fieldName");

        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setCfgType(cfgType);
        fieldConfig.setType(type);
        fieldConfig.setFieldType(fieldType);
        fieldConfig.setRemark(remark);
        fieldConfig.setFieldName(fieldName);
        int i = fieldConfigMapper.insertFieldConfig(fieldConfig);
//        skinCommon(params,fieldConfig);

        return i;
    }

    @Override
    public int updateJson(JSONObject params) {
        String cfgType = params.getString("cfgType");
        String fieldType = params.getString("fieldType");
        String remark = params.getString("remark");
        String type = params.getString("type");
        String fieldName = params.getString("fieldName");
        Long id = params.getLong("id");


        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setCfgType(cfgType);
        fieldConfig.setType(type);
        fieldConfig.setFieldType(fieldType);
        fieldConfig.setRemark(remark);
        fieldConfig.setFieldName(fieldName);
        FieldConfig insertFild = fieldConfigMapper.selectFieldConfigById(id);

        insertFild.setCfgType(cfgType);
        insertFild.setType(type);
        insertFild.setFieldType(fieldType);
        insertFild.setRemark(remark);
        insertFild.setFieldName(fieldName);
        int i = fieldConfigMapper.updateFieldConfig(insertFild);

//        skinMapper.deleteSkinByCOnfig(insertFild.getId());
//        skinCommon(params,insertFild);
        return i;
    }

    private void skinCommon(JSONObject params,FieldConfig fieldConfig){
        String cfgType = params.getString("cfgType");
        Object domains = params.get("domains");
        String jsonString = JSONObject.toJSONString(domains);
        JSONArray objects = JSON.parseArray(jsonString);
        StringBuffer st = new StringBuffer();
        for (Object object : objects) {
            String domainStr = JSONObject.toJSONString(object);
            JSONObject domain = JSONObject.parseObject(domainStr);
            Double price = domain.getDouble("price");
            Skin skin = new Skin();
            skin.setSkinPrice(new BigDecimal(price));
            String imgUrl = domain.getString("imgUrl");
            skin.setImg(imgUrl);
            String name = domain.getString("value");
            skin.setTitle(name);
            skin.setType(cfgType);
            skin.setConfigid(fieldConfig.getId());
            skinMapper.insertSkin(skin);
            st.append(name+",");
        }
        String fieldValue  = st.toString();
        fieldValue = fieldValue.substring(0,fieldValue.length()-1);
        fieldConfig.setFieldValue(fieldValue);

        FieldConfig fieldConfig1 = fieldConfigMapper.selectFieldConfigById(fieldConfig.getId());
        fieldConfig1.setFieldValue(fieldValue);
        fieldConfigMapper.updateFieldConfig(fieldConfig1);
    }
}
