package com.ruoyi.web.controller.upload.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.UUIDGenerator;
import com.ruoyi.system.model.service.UploadService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/7 21:15
 */
@Data
@RestController
@RequestMapping("/upload")
public class UploadImgController {

    @Value("${ruoyi.profile}")
    private String profile;
    @Value("${ruoyi.profile}")
    private String linuxProfile;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private UploadService uploadService;

    /**
     * 头像上传
     */
    @PostMapping("/image")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = null;
        String generate = UUIDGenerator.generate() + ".jpg";
        String filename = file.getResource().getFilename();
        String[] split = filename.split("//.");
        Map<String, Object> resultMap = new HashMap<>();
        if (!file.isEmpty()) {
            String type = configService.selectConfigByKey("upType");
            //默认七牛云
            if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
                filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
            } else if (Objects.equals(Constant.ALIYUN, type)) {
                //阿里云
                filePath = uploadService.upload2aliyun(file, generate);
            } else {
                filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
            }

            String originalFilename = file.getOriginalFilename();
            resultMap.put("fileName", originalFilename);
            resultMap.put("filePath", filePath);
        }
        return AjaxResult.success(resultMap);
    }

    @PostMapping("/app")
    public AjaxResult uploadApp(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = null;


        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            extension = extension.toLowerCase();
        }
        String generate = "app/" + UUIDGenerator.generate() + "." + extension;
        Map<String, Object> resultMap = new HashMap<>();
        if (!file.isEmpty()) {
            String type = configService.selectConfigByKey("upType");
            //默认七牛云
            if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
                filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
            } else if (Objects.equals(Constant.ALIYUN, type)) {
                //阿里云
                filePath = uploadService.upload2aliyun(file, generate);
            } else {
                filePath = uploadService.uploadApp2Qiniu(file.getBytes(), generate);
            }

            resultMap.put("fileName", originalFilename);
            resultMap.put("filePath", filePath);
        }
        return AjaxResult.success(resultMap);
    }

    @PostMapping("/video")
    public AjaxResult uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = null;


        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            extension = extension.toLowerCase();
        }
        String generate = "video/" + UUIDGenerator.generate() + "." + extension;
        Map<String, Object> resultMap = new HashMap<>();
        if (!file.isEmpty()) {
            String type = configService.selectConfigByKey("upType");
            //默认七牛云
            if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
                filePath = uploadService.uploadApp2Qiniu(file.getBytes(), generate);
            } else if (Objects.equals(Constant.ALIYUN, type)) {
                //阿里云
                filePath = uploadService.upload2aliyun(file, generate);
            } else {
                filePath = uploadService.uploadApp2Qiniu(file.getBytes(), generate);
            }

            resultMap.put("fileName", originalFilename);
            resultMap.put("filePath", filePath);
        }
        return AjaxResult.success(resultMap);
    }

    @PostMapping("/upHx")
    public AjaxResult upHx(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = null;

        Long userId = SecurityUtils.getUserId();

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            extension = extension.toLowerCase();
        }
        if (StringUtils.contains(contentType, "audio")) {
            extension = "amr";
        }

        String generate = "/upHx/" + userId + "/" + extension + "/" + UUIDGenerator.generate() + "." + extension;

        Map<String, Object> resultMap = new HashMap<>();
        if (!file.isEmpty()) {
            String type = configService.selectConfigByKey("upType");
            //默认七牛云
            if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
                filePath = uploadService.uploadApp2Qiniu(file.getBytes(), generate);
            } else if (Objects.equals(Constant.ALIYUN, type)) {
                //阿里云
                filePath = uploadService.upload2aliyun(file, generate);
            } else {
                filePath = uploadService.uploadApp2Qiniu(file.getBytes(), generate);
            }

            resultMap.put("fileName", originalFilename);
            resultMap.put("filePath", filePath);
        }
        return AjaxResult.success(resultMap);
    }

    /**
     * 头像上传
     */
    @PostMapping("/v2/image")
    public String yupload(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = null;
        String generate = UUIDGenerator.generate();
        Map<String, Object> resultMap = new HashMap<>();

        if (!file.isEmpty()) {
            String type = configService.selectConfigByKey("upType");
            //默认七牛云
            if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
                filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
            } else if (Objects.equals(Constant.ALIYUN, type)) {
                //阿里云
                filePath = uploadService.upload2aliyun(file, generate + ".jpg");
            } else {
                filePath = uploadService.upload2Qiniu(file.getBytes(), generate + ".jpg");
            }

            String originalFilename = file.getOriginalFilename();
            resultMap.put("fileName", originalFilename);
            resultMap.put("filePath", filePath);
        }
        return filePath;
    }

    @PostMapping("/imageHead")
    public AjaxResult imageHead(@RequestParam("file") MultipartFile file) throws IOException {

        String filePath = "";
        String type = configService.selectConfigByKey("upType");
        //默认七牛云
        String generate = UUIDGenerator.generate() + ".jpg";
        if (StringUtils.isEmpty(type) || Objects.equals(Constant.QINIUQ, type)) {
            filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
        } else if (Objects.equals(Constant.ALIYUN, type)) {
            //阿里云
            filePath = uploadService.upload2aliyun(file, generate);
        } else {
            filePath = uploadService.upload2Qiniu(file.getBytes(), generate);
        }
        return AjaxResult.success(filePath);
    }

    private byte[] getByte(File f) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        try {
            fis = new FileInputStream(f);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            throw new GlobalException("图片上传失败");
        }
    }


    private String getPath() {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("windows")) {
            return profile;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            return linuxProfile;
        } else { //其它操作系统
            return null;
        }
    }

    private String imgPath(String name, String uuid) {
        String[] split = name.split("\\.");
        if (split.length > 0) {
            return getPath() + uuid + "." + split[1];
        }
        return null;
    }

}
