package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.UploadFile;
import com.ruoyi.system.model.dto.FileCheckReq;

import java.util.List;

/**
 * 上传视频Mapper接口
 *
 * @author lz
 * @date 2022-05-31
 */
public interface UploadFileMapper
{
    /**
     * 查询上传视频
     *
     * @param id 上传视频主键
     * @return 上传视频
     */
    public UploadFile selectUploadFileById(Long id);

    /**
     * 查询上传视频列表
     *
     * @param uploadFile 上传视频
     * @return 上传视频集合
     */
    public List<UploadFile> selectUploadFileList(UploadFile uploadFile);

    /**
     * 新增上传视频
     *
     * @param uploadFile 上传视频
     * @return 结果
     */
    public int insertUploadFile(UploadFile uploadFile);

    /**
     * 修改上传视频
     *
     * @param uploadFile 上传视频
     * @return 结果
     */
    public int updateUploadFile(UploadFile uploadFile);

    /**
     * 删除上传视频
     *
     * @param id 上传视频主键
     * @return 结果
     */
    public int deleteUploadFileById(Long id);

    /**
     * 批量删除上传视频
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUploadFileByIds(Long[] ids);

    UploadFile findUploadFileWithHash(FileCheckReq req);

    void updateByHash(UploadFile uf);
}
