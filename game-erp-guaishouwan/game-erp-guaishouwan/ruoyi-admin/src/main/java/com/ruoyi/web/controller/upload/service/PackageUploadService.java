package com.ruoyi.web.controller.upload.service;

import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.UploadChunk;
import com.ruoyi.system.model.domain.UploadFile;
import com.ruoyi.system.model.dto.*;
import com.ruoyi.system.model.mapper.UploadChunkMapper;
import com.ruoyi.system.model.mapper.UploadFileMapper;
import com.ruoyi.web.controller.upload.utils.FileUtil;
import com.ruoyi.web.controller.upload.utils.UploadFilePathCons;
import com.ruoyi.web.controller.upload.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackageUploadService {
    @Resource
    private UploadFileMapper uploadFileDao;
    @Resource
    private UploadChunkMapper uploadChunkDao;
    @Autowired
    private FileChunkUploader chunkUploader;

    @Transactional(rollbackFor = Exception.class)
    public InterfaceResultData checkFile(FileCheckReq req) {
        FileCheckResp resp = new FileCheckResp();
        resp.setFileName(req.getFileName());
        UploadFile uploadFile = this.getUploadFileWithHash(req);
        //如果不存在，则经行初始化
        if (uploadFile == null) {
            log.info("The file is not found, need to init");
            UploadFile newFile = this.doUploadInit(req);
            resp.setFilePath(newFile.getFilePath());
            resp.setIsInitialized(true);
            resp.setIsCompleted(false);
            resp.setCompletedChunk(new ArrayList<>());
            resp.setUploadId(newFile.getUploadId());
            return InterfaceResultData.succ(resp);
        }
        //文件已经存在
        //判断状态是否未完成，如果是完成状态直接返回，如果是部分完成，则查询所有已经上传的块进行返回
        resp.setIsInitialized(true);
        resp.setUploadId(uploadFile.getUploadId());
        //如果一个分片都没有上传
        if (UploadStatusEnum.isNoneCompleted(uploadFile.getStatus())) {
            resp.setIsCompleted(false);
            resp.setCompletedChunk(new ArrayList<>());
            return InterfaceResultData.succ(resp);
        }
        //部分分片已上传
        if (UploadStatusEnum.isPartCompleted(uploadFile.getStatus())) {
            resp.setIsCompleted(false);
            List<UploadChunk> chunkList = this.getUploadChunkWithFile(uploadFile);
            if (CollectionUtils.isEmpty(chunkList)) {
                resp.setCompletedChunk(new ArrayList<>());
            } else {
                List<Integer> chunkNumberList =
                        chunkList.stream()
                                .map(UploadChunk::getChunkNumber)
                                .collect(Collectors.toList());
                resp.setCompletedChunk(chunkNumberList);
            }
            return InterfaceResultData.succ(resp);
        }

        resp.setIsCompleted(true);//全部分片已上传
        resp.setFilePath(uploadFile.getFilePath());
        return InterfaceResultData.succ(resp);
    }

    private UploadFile getUploadFileWithHash(FileCheckReq req) {
        return this.uploadFileDao.findUploadFileWithHash(req);
    }

    private List<UploadChunk> getUploadChunkWithFile(UploadFile uploadFile) {
        return this.uploadChunkDao.findUploadChunkWithFile(uploadFile.getFileHash());
    }

    private UploadFile doUploadInit(FileCheckReq req) {
        ChunkInitReq chunkInitReq = new ChunkInitReq();
        chunkInitReq.setKey(this.getFileStoreKey(req));
        ChunkInitResp chunkInitResp = this.chunkUploader.init(chunkInitReq);
        UploadFile newFile = new UploadFile();
        newFile.setFileHash(req.getFileMD5Hash());
        newFile.setFileName(req.getFileName());
        newFile.setFileSize(req.getFileSize());
        newFile.setUploadId(chunkInitResp.getUploadId());
        newFile.setCreateTime(new Date());
        String username = SecurityUtils.getUsername();

        newFile.setCreateName(username);
        newFile.setStatus(UploadStatusEnum.NONE.getCode());
        newFile.setChunkCount(req.getChunkCount());
        this.uploadFileDao.insertUploadFile(newFile);
        return newFile;
    }

    @Transactional(rollbackFor = Exception.class)
    public InterfaceResultData uploadChunk(UploadChunkReq uploadChunkReq) throws IOException {
        //log.info("uploadChunk service, thread id=" + Thread.currentThread().getId());
        if (this.isChunkExist(uploadChunkReq)) {
            return InterfaceResultData.succ("File chunk upload success");
        }
        ChunkUploadReq req = new ChunkUploadReq();
        req.setChunkHash(uploadChunkReq.getChunkHash());
        req.setFileHash(uploadChunkReq.getFileHash());
        req.setUploadId(uploadChunkReq.getUploadId());
        req.setChunkSize(uploadChunkReq.getChunkSize());
        req.setChunkNumber(uploadChunkReq.getChunkNumber());
        req.setKey(this.getFileStoreKey(uploadChunkReq));
        MultipartFile chunkBlob = uploadChunkReq.getChunkBlob();
        req.setInputStream(chunkBlob.getInputStream());
        ChunkUploadResp resp = this.chunkUploader.upload(req);
        UploadChunk chunk = new UploadChunk();
        chunk.setFileHash(uploadChunkReq.getFileHash());
        chunk.setChunkNumber(uploadChunkReq.getChunkNumber());
        chunk.setChunkSize(uploadChunkReq.getChunkSize());
        chunk.setChunkHash(uploadChunkReq.getChunkHash());
        chunk.setUploadId(uploadChunkReq.getUploadId());
        String username = SecurityUtils.getUsername();
        chunk.setCreateName(username);
        chunk.setCreateTime(new Date());
        chunk.setChunkEtag(resp.getEtag());
        this.uploadChunkDao.insertUploadChunk(chunk);
        UploadFile uf = new UploadFile();
        uf.setFileHash(chunk.getFileHash());
        uf.setStatus(UploadStatusEnum.PART_COMPLETE.getCode());
        this.uploadFileDao.updateByHash(uf);
        this.cleanStreamResource(chunkBlob);
        return InterfaceResultData.succ("File chunk upload success");
    }

    private void cleanStreamResource(MultipartFile chunkBlob) {
        if (chunkBlob != null) {
            try {
                InputStream input = chunkBlob.getInputStream();
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("upload chunk:the exception thrown when close input stream:" + e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public InterfaceResultData mergeChunk(ChunkMergeReq req) {
        FileCheckReq checkReq = new FileCheckReq();
        checkReq.setFileMD5Hash(req.getFileHash());
        UploadFile uploadFile = this.uploadFileDao.findUploadFileWithHash(checkReq);
        if (uploadFile == null) {
            return new InterfaceResultData().setData(ResultEnum.UPLOAD_FAILED);
        }
        List<UploadChunk> chunkList = this.uploadChunkDao.findUploadChunkWithFile(req.getFileHash());
        //实际上传的分片个数不等于文件的总分片数时不能合并
        if (!uploadFile.getChunkCount().equals(chunkList.size())) {
            return new InterfaceResultData().setData(ResultEnum.UPLOAD_FAILED);
        }
        ChunkCompleteReq completeReq = new ChunkCompleteReq();
        completeReq.setChunkList(chunkList);
        completeReq.setUploadId(req.getUploadId());
        completeReq.setFileHash(req.getFileHash());
        completeReq.setKey(this.getFileStoreKey(uploadFile));
        ChunkCompleteResp resp = this.chunkUploader.complete(completeReq);
        uploadFile.setStatus(UploadStatusEnum.ALL_COMPLETE.getCode());
        uploadFile.setFilePath(Constant.qiniuHost + resp.getKey());
        this.uploadFileDao.updateByHash(uploadFile);
        ChunkMergeResp mergeResp = new ChunkMergeResp();
        mergeResp.setFilePath(uploadFile.getFilePath());
        mergeResp.setChunkCount(uploadFile.getChunkCount());
        return InterfaceResultData.succ(mergeResp);
    }


    public boolean isChunkExist(UploadChunkReq req) {
        Integer chunkCount = this.uploadChunkDao.countChunkByHashAndNumber(req);
        return chunkCount > 0;
    }

    private String getFileStoreKey(UploadFile uploadFile) {
        return UploadFilePathCons.PACKAGE_CHUNK
                + "/"
                + uploadFile.getFileHash()
                + "."
                + FileUtil.getFileExt(uploadFile.getFileName());
    }

    //download/chunk/${fileHash}.${fileExt}
    private String getFileStoreKey(UploadChunkReq req) {
        return UploadFilePathCons.PACKAGE_CHUNK
                + "/"
                + req.getFileHash()
                + "."
                + FileUtil.getFileExt(req.getFileName());
    }

    //download/chunk/${fileHash}.${fileExt}
    private String getFileStoreKey(FileCheckReq req) {
        return UploadFilePathCons.PACKAGE_CHUNK
                + "/"
                + req.getFileMD5Hash()
                + "."
                + FileUtil.getFileExt(req.getFileName());
    }

    private String getFileStoreKey(ChunkMergeReq req) {
        return UploadFilePathCons.PACKAGE_CHUNK
                + "/"
                + req.getFileHash()
                + "."
                + FileUtil.getFileExt(req.getFileName());
    }
}
