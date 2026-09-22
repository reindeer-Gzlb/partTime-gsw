package com.ruoyi.system.model.service.impl;

/**
 * @author xuhw
 * @Title
 * @Description
 * @date 2021/12/20 16:17
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;


/**
 * file <<>> Base64String
 * 将file文件转换为Base64<br>
 * 将base64编码字符串解码成file
 * @author
 *
 */
public class File2Base64Util {

    public static void main(String[] args) {
        String imgFile = "d:\\1.png";//待处理的图片
        String imgbese=getFileBase64StrByLocalFile(imgFile);
        System.out.println(imgbese.length());
        System.out.println(imgbese);
        String imgFilePath = "d:\\332.png";//新生成的图片
        generateBase64StringToFile(imgbese,imgFilePath);
    }

    /**
     * 将文件转换成Base64编码
     * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param localFilePath 待处理图片
     * @return
     */
    public static String getFileBase64StrByLocalFile(String localFilePath){

        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(localFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * 将文件转换成Base64编码
     * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param fileUrl 待处理文件网络地址
     * @return
     */
    public static String getFileBase64StrByUrl(String fileUrl){
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            URL ur = new URL(fileUrl);
            in = new BufferedInputStream(ur.openStream());
            out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] content = out.toByteArray();
        return new String(Base64.encodeBase64(content));
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
//     * @param imgStr 图片数据
//     * @param imgFilePath 保存图片全路径地址
     * @return
     */
    public static boolean generateBase64StringToFile(String fileStr,String fileFilePath){
        if (fileStr == null) //图像数据为空
            return false;
        try
        {
            //Base64解码
            byte[] b = Base64.decodeBase64(fileStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(fileFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


}

