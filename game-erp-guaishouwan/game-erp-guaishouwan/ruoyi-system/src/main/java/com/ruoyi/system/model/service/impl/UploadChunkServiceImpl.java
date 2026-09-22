package com.ruoyi.system.model.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.UploadChunk;
import com.ruoyi.system.model.mapper.UploadChunkMapper;
import com.ruoyi.system.model.service.IUploadChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分片列表Service业务层处理
 *
 * @author lz
 * @date 2022-05-31
 */
@Service
public class UploadChunkServiceImpl implements IUploadChunkService
{
    @Autowired
    private UploadChunkMapper uploadChunkMapper;

    /**
     * 查询分片列表
     *
     * @param id 分片列表主键
     * @return 分片列表
     */
    @Override
    public UploadChunk selectUploadChunkById(Long id)
    {
        return uploadChunkMapper.selectUploadChunkById(id);
    }

    /**
     * 查询分片列表列表
     *
     * @param uploadChunk 分片列表
     * @return 分片列表
     */
    @Override
    public List<UploadChunk> selectUploadChunkList(UploadChunk uploadChunk)
    {
        return uploadChunkMapper.selectUploadChunkList(uploadChunk);
    }

    /**
     * 新增分片列表
     *
     * @param uploadChunk 分片列表
     * @return 结果
     */
    @Override
    public int insertUploadChunk(UploadChunk uploadChunk)
    {
        uploadChunk.setCreateTime(DateUtils.getNowDate());
        return uploadChunkMapper.insertUploadChunk(uploadChunk);
    }

    /**
     * 修改分片列表
     *
     * @param uploadChunk 分片列表
     * @return 结果
     */
    @Override
    public int updateUploadChunk(UploadChunk uploadChunk)
    {
        return uploadChunkMapper.updateUploadChunk(uploadChunk);
    }

    /**
     * 批量删除分片列表
     *
     * @param ids 需要删除的分片列表主键
     * @return 结果
     */
    @Override
    public int deleteUploadChunkByIds(Long[] ids)
    {
        return uploadChunkMapper.deleteUploadChunkByIds(ids);
    }

    /**
     * 删除分片列表信息
     *
     * @param id 分片列表主键
     * @return 结果
     */
    @Override
    public int deleteUploadChunkById(Long id)
    {
        return uploadChunkMapper.deleteUploadChunkById(id);
    }
}
