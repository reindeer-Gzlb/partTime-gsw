package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.ArtRec;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-17
 */
public interface ArtRecMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ArtRec selectArtRecById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param artRec 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ArtRec> selectArtRecList(ArtRec artRec);

    /**
     * 新增【请填写功能名称】
     * 
     * @param artRec 【请填写功能名称】
     * @return 结果
     */
    public int insertArtRec(ArtRec artRec);

    /**
     * 修改【请填写功能名称】
     * 
     * @param artRec 【请填写功能名称】
     * @return 结果
     */
    public int updateArtRec(ArtRec artRec);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteArtRecById(Long id);
    public int deleteArtRecBySaleCode(String saleCode);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArtRecByIds(Long[] ids);

    void deleteArtRecCode(@Param("id") Long id,@Param("config") Long config);

    List<Map<String, Object>> selectOutIdByGoodsId(Long id);
}
