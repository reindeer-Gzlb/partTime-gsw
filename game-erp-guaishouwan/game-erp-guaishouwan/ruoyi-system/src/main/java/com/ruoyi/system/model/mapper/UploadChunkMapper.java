package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.UploadChunk;
import com.ruoyi.system.model.dto.UploadChunkReq;

import java.util.List;

/**
 * 分片列表Mapper接口
 *
 * @author lz
 * @date 2022-05-31
 */
public interface UploadChunkMapper
{
    /**
     * 查询分片列表
     *
     * @param id 分片列表主键
     * @return 分片列表
     */
    public UploadChunk selectUploadChunkById(Long id);

    /**
     * 查询分片列表列表
     *
     * @param uploadChunk 分片列表
     * @return 分片列表集合
     */
    public List<UploadChunk> selectUploadChunkList(UploadChunk uploadChunk);

    /**
     * 新增分片列表
     *
     * @param uploadChunk 分片列表
     * @return 结果
     */
    public int insertUploadChunk(UploadChunk uploadChunk);

    /**
     * 修改分片列表
     *
     * @param uploadChunk 分片列表
     * @return 结果
     */
    public int updateUploadChunk(UploadChunk uploadChunk);

    /**
     * 删除分片列表
     *
     * @param id 分片列表主键
     * @return 结果
     */
    public int deleteUploadChunkById(Long id);

    /**
     * 批量删除分片列表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUploadChunkByIds(Long[] ids);

    List<UploadChunk> findUploadChunkWithFile(String fileHash);

    Integer countChunkByHashAndNumber(UploadChunkReq req);
}
