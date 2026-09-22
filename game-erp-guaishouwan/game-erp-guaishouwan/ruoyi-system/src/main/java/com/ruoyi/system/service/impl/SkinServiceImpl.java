package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.ClassSkin;
import com.ruoyi.system.model.mapper.ClassSkinMapper;
import com.ruoyi.system.model.mapper.SkinMapper;
import com.ruoyi.system.model.domain.Skin;
import com.ruoyi.system.service.ISkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-03
 */
@Service
public class SkinServiceImpl implements ISkinService
{
    @Autowired
    private SkinMapper skinMapper;
    @Autowired
    private ClassSkinMapper classSkinMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Skin selectSkinById(Long id)
    {
        return skinMapper.selectSkinById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param skin 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Skin> selectSkinList(Skin skin)
    {
        return skinMapper.selectSkinList(skin);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param skin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSkin(Skin skin)
    {
        skin.setCreateTime(DateUtils.getNowDate());
        return skinMapper.insertSkin(skin);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param skin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSkin(Skin skin)
    {
        skin.setUpdateTime(DateUtils.getNowDate());
        return skinMapper.updateSkin(skin);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSkinByIds(Long[] ids)
    {
        return skinMapper.deleteSkinByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSkinById(Long id)
    {
        return skinMapper.deleteSkinById(id);
    }

    @Override
    public  List<Map<String,Object>> selectSkinTypeList(ClassSkin classSkin) {

        classSkin.setParentId(1l);

        List<Map<String,Object>> classTypeList = new ArrayList<>();
        List<ClassSkin> classSkins = classSkinMapper.selectClassSkinList(classSkin);
        for (ClassSkin cs : classSkins) {

            Map<String,Object> classSkinMap = new HashMap<>();
            Skin ski = new Skin();
            ski.setParentId(cs.getSkinId());
            List<Map<String,Object>> skinMapList = new ArrayList<>();

            List<Skin> skins = skinMapper.selectSkinList(ski);
            for (Skin skin : skins) {
                Map<String,Object> skinMap = new HashMap<>();
                skinMap.put("skinName",skin.getTitle());
                skinMap.put("skinId",skin.getSkinId());
                skinMapList.add(skinMap);
            }
            classSkinMap.put("child",skinMapList);
            classSkinMap.put("typeName",cs.getBrandname());
            classSkinMap.put("prentId",cs.getSkinId());
            classTypeList.add(classSkinMap);
        }


        return classTypeList;
    }
}
