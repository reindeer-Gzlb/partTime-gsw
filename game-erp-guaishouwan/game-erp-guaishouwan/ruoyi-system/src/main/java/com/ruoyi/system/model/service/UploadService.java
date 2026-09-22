package com.ruoyi.system.model.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ImgUntilYS;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.service.ISysDictDataService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/28 11:07
 */
@Service
public class UploadService {

    @Autowired
    private ISysDictDataService dictDataService;

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;


    String endpoint;
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String accessKeyId;
    String accessKeySecret;
    String bucketName;
    String folder;


    private void config(String type) {
        if (Objects.equals(Constant.ALIYUN, type)) {
            SysDictData sysDictData = new SysDictData();
//        sysDictData.setDictType("qiniuyun");
            sysDictData.setDictType(type);
            List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
            for (SysDictData dictData : sysDictDataList) {
                if ("endpoint".equals(dictData.getDictLabel())) {
                    endpoint = dictData.getDictValue();
                }
                if ("ACCESS_KEY".equals(dictData.getDictLabel())) {
                    accessKeyId = dictData.getDictValue();
                } else if ("SECRET_KEY".equals(dictData.getDictLabel())) {
                    accessKeySecret = dictData.getDictValue();
                } else if ("bucket".equals(dictData.getDictLabel())) {
                    bucketName = dictData.getDictValue();
                } else if ("folder".equals(dictData.getDictLabel())) {
                    folder = dictData.getDictValue();
                }
            }
        } else {
            SysDictData sysDictData = new SysDictData();
//        sysDictData.setDictType("qiniuyun");
            sysDictData.setDictType(type);
            List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
            for (SysDictData dictData : sysDictDataList) {
                if ("domain".equals(dictData.getDictLabel())) {
                    domain = dictData.getDictValue();
                }
                if ("ACCESS_KEY".equals(dictData.getDictLabel())) {
                    accessKey = dictData.getDictValue();
                } else if ("SECRET_KEY".equals(dictData.getDictLabel())) {
                    secretKey = dictData.getDictValue();
                } else if ("bucket".equals(dictData.getDictLabel())) {
                    bucket = dictData.getDictValue();
                }
            }
        }
    }

    //上传文件
    public String upload2Qiniu(String filePath, String fileName) {
        config(Constant.QINIUQ);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String result = null;
        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(domain + putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    //上传文件
    public String upload2Qiniu(byte[] bytes, String fileName) {

//        long srcSize = bytes.length;
//        if (srcSize / 1024 > 5000) {
//            bytes = ImgUntilYS.compressPicForScale(bytes, 3072l);
//        } else {
//            bytes = ImgUntilYS.compressPicForScale(bytes, 2048l);
//        }


        config(Constant.QINIUQ);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(domain + putRet.key);
            return domain + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public String uploadApp2Qiniu(byte[] bytes, String fileName) {

        long srcSize = bytes.length;
        if (srcSize / 1024 > 5000) {
            bytes = ImgUntilYS.compressPicForScale(bytes, 3072l);
        } else {
            bytes = ImgUntilYS.compressPicForScale(bytes, 2048l);
        }

        config(Constant.QINIUQ);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(domain + putRet.key);
            return domain + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public String upload2aliyun(MultipartFile file, String generate) {

        //LTAI4Fz5S8QLbyB92ZeyHFwz
        //kAzZh0hbiX2hNxpF8ZqD6hDyNzb0uv

        config(Constant.ALIYUN);
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件真实名称
            String originalFilename = file.getOriginalFilename();
            //重命名，防止相同文件出现覆盖
            //生成的f4f2e1a3-391a-4d5a-9438-0c9f5d27708c 需要替换成 f4f2e1a3391a4d5a94380c9f5d27708c
            //新的文件名
            originalFilename = generate + originalFilename;
            //2、把文件按照日期进行分类
            String datePath = DateUtils.dateTimeNow("yyyy/MM/dd");
            //拼接
            originalFilename = folder + "/" + datePath + "/" + originalFilename;
            // oss实现上传文件
            ossClient.putObject(bucketName, originalFilename, inputStream);
            // 关闭OSSClient
            ossClient.shutdown();
            //把上传之后文件路径返回,手动拼接出来
            String url = "https://" + bucketName + "." + endpoint + "/" + originalFilename;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean parseCode(Map<String, Object> params) {
        String idNumber = MapUtils.getString(params, "idNumber");
        String name = MapUtils.getString(params, "name");
        String host = "https://dfidveri.market.alicloudapi.com";
        String path = "/verify_id_name";
        String method = "POST";
        String appcode = "5e02386fac3744d89293fcfd1992c20d";
        Map<String, Object> headers = new HashMap<String, Object>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("id_number", idNumber);
        bodys.put("name", name);

        try {
            String s = HttpUtils.doPostForm(host + path, bodys, headers);
            if (StringUtils.isNotEmpty(s)) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                String status = jsonObject.getString("state");
                if (Objects.equals("1", status)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new GlobalException("格式不对");
        }
    }

    public Boolean parseCodeShuMai(Map<String, Object> params) {
        String idNumber = MapUtils.getString(params, "idNumber");
        String name = MapUtils.getString(params, "name");

        String url = "https://eid.shumaidata.com/eid/check";
        String appCode = "05258b52204b41a3b84065d5bbc7a71e";
        Map<String, String> paramss = new HashMap<>();
        paramss.put("idcard", idNumber);
        paramss.put("name", name);

        try {
            String s = postForm(appCode, url, paramss);
            if (StringUtils.isNotEmpty(s)) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                String result = jsonObject.getString("result");
                JSONObject jsonObjectResult = JSONObject.parseObject(result);
                String res = jsonObjectResult.getString("res");
                if (Objects.equals("1", res)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new GlobalException("格式不对");
        }
    }

    public static String postForm(String appCode, String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            formbuilder.add(key, params.get(key));
        }
        FormBody body = formbuilder.build();
        Request request =
                new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
        okhttp3.Response response = client.newCall(request).execute();
//        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
        String result = response.body().string();
        return result;
    }
}
