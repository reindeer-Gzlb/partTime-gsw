package com.ruoyi.system.model.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.UploadFile;
import com.ruoyi.system.model.mapper.UploadFileMapper;
import com.ruoyi.system.model.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 上传视频Service业务层处理
 *
 * @author lz
 * @date 2022-05-31
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService
{
    @Autowired
    private UploadFileMapper uploadFileMapper;

    /**
     * 查询上传视频
     *
     * @param id 上传视频主键
     * @return 上传视频
     */
    @Override
    public UploadFile selectUploadFileById(Long id)
    {
        return uploadFileMapper.selectUploadFileById(id);
    }

    /**
     * 查询上传视频列表
     *
     * @param uploadFile 上传视频
     * @return 上传视频
     */
    @Override
    public List<UploadFile> selectUploadFileList(UploadFile uploadFile)
    {
        return uploadFileMapper.selectUploadFileList(uploadFile);
    }

    /**
     * 新增上传视频
     *
     * @param uploadFile 上传视频
     * @return 结果
     */
    @Override
    public int insertUploadFile(UploadFile uploadFile)
    {
        uploadFile.setCreateTime(DateUtils.getNowDate());
        return uploadFileMapper.insertUploadFile(uploadFile);
    }

    /**
     * 修改上传视频
     *
     * @param uploadFile 上传视频
     * @return 结果
     */
    @Override
    public int updateUploadFile(UploadFile uploadFile)
    {
        return uploadFileMapper.updateUploadFile(uploadFile);
    }

    /**
     * 批量删除上传视频
     *
     * @param ids 需要删除的上传视频主键
     * @return 结果
     */
    @Override
    public int deleteUploadFileByIds(Long[] ids)
    {
        return uploadFileMapper.deleteUploadFileByIds(ids);
    }

    /**
     * 删除上传视频信息
     *
     * @param id 上传视频主键
     * @return 结果
     */
    @Override
    public int deleteUploadFileById(Long id)
    {
        return uploadFileMapper.deleteUploadFileById(id);
    }
}
