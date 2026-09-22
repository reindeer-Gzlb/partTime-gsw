package com.ruoyi.system.V2.recycle.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ImgUntil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.UUIDGenerator;
import com.ruoyi.system.model.domain.Attribute;
import com.ruoyi.system.V2.recycle.mapper.AttributeMapper;
import com.ruoyi.system.V2.recycle.service.IAttributeService;
import com.ruoyi.system.model.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏配置Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-01
 */
@Slf4j
@Service
public class AttributeServiceImpl implements IAttributeService {
    @Resource
    private AttributeMapper attributeMapper;
    @Autowired
    private UploadService uploadService;

    /**
     * 查询游戏配置
     *
     * @param id 游戏配置主键
     * @return 游戏配置
     */
    @Override
    public Attribute selectAttributeById(Long id) {
        return attributeMapper.selectAttributeById(id);
    }

    /**
     * 查询游戏配置列表
     *
     * @param attribute 游戏配置
     * @return 游戏配置
     */
    @Override
    public List<Attribute> selectAttributeList(Attribute attribute) {
        return attributeMapper.selectAttributeList(attribute);
    }

    /**
     * 新增游戏配置
     *
     * @param attribute 游戏配置
     * @return 结果
     */
    @Override
    public int insertAttribute(Attribute attribute) {
        attribute.setCreateTime(DateUtils.getNowDate());
        return attributeMapper.insertAttribute(attribute);
    }

    /**
     * 修改游戏配置
     *
     * @param attribute 游戏配置
     * @return 结果
     */
    @Override
    public int updateAttribute(Attribute attribute) {
        attribute.setUpdateTime(DateUtils.getNowDate());
        return attributeMapper.updateAttribute(attribute);
    }

    /**
     * 批量删除游戏配置
     *
     * @param ids 需要删除的游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteAttributeByIds(Long[] ids) {
        return attributeMapper.deleteAttributeByIds(ids);
    }

    /**
     * 删除游戏配置信息
     *
     * @param id 游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteAttributeById(Long id) {
        return attributeMapper.deleteAttributeById(id);
    }

    @Override
    public List<Attribute> listTree(Attribute attribute) {

        List<Attribute> attributes = attributeMapper.selectAttributeList(attribute);
        if (!CollectionUtils.isEmpty(attributes)) {
            Attribute prentAttr = attributes.get(0);
            List<Attribute> attributeList = attributeMapper.getByPrentId(prentAttr.getId());
            return attributeList;
        }
        return null;
    }

    @Override
    public List<Attribute> selectEquation(Attribute attribute) {


        List<String> fieldTypes = new ArrayList<>();
        fieldTypes.add("regional");
        fieldTypes.add("dan");
        fieldTypes.add("radio");
        fieldTypes.add("input");
        fieldTypes.add("checkbox");
        attribute.setFieldTypes(fieldTypes);
        List<Attribute> attributeList = attributeMapper.selectAttributeList(attribute);
        return attributeList;
    }

    @Override
    public Map<String, Object> mergeImage(JSONObject jsonObject) {


        Object imgList = jsonObject.get("imgList");
        String mList = JSON.toJSONString(imgList);
        JSONArray objects = JSONArray.parseArray(mList);

        Map<String,Object> result = new HashMap<>();

        String titleImg = null;

        List<String> imgStrList = new ArrayList<>();

        int pointsDataLimit = 12;//限制条数
        Integer size = objects.size();
        if (objects.size() > pointsDataLimit) {

            try {

            int part = size / pointsDataLimit;//分批数
            List<BufferedImage> imgs = new ArrayList<>();

            for (int i = 0; i < part; i++) {
                List<Object> objectsList = objects.subList(0, pointsDataLimit);
                log.info(String.valueOf(objectsList.size()));
                for (Object object : objectsList) {
                    URL url = new URL(object.toString());
                    BufferedImage image = ImageIO.read(url.openStream());
                    imgs.add(image);
                }
                BufferedImage bufferedImage = ImgUntil.mergeImage(imgs);
                byte[] bytes = ImgUntil.bufferedImageToByteArray(bufferedImage);
                String path = uploadService.upload2Qiniu(bytes, "test/" + UUIDGenerator.generate());
                if (StringUtils.isNotEmpty(titleImg)){
                    titleImg = path;
                }
                imgStrList.add(path);
                objects.subList(0, pointsDataLimit).clear();
            }
                imgs.clear();
            if(!objects.isEmpty()){
                for (Object object : objects) {
                    URL url = new URL(object.toString());
                    BufferedImage image = ImageIO.read(url.openStream());
                    imgs.add(image);
                }
                BufferedImage bufferedImage = ImgUntil.mergeImage(imgs);
                byte[] bytes = ImgUntil.bufferedImageToByteArray(bufferedImage);
                String path = uploadService.upload2Qiniu(bytes, "test/" + UUIDGenerator.generate());
                imgStrList.add(path);
            }

            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException("图片通讯失败,请检查");
            }
        }
        result.put("titleImg",titleImg);
        result.put("imgStrList",imgStrList);
        return result;
    }


}
