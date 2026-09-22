package com.ruoyi.web.controller.upload.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class FileUtil extends FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private final static int METE_FILE_SIZE = 1024 * 1024 * 50;


    /**
     * 获取文件后缀
     *
     * @param fileName 文件名称
     * @return
     */
    public static String getFileExt(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);//从最后一个点之后截取字符串
    }

    /**
     * 文件写入到输出流中
     * <p>
     * user：Administrator
     * methodName：writeToOutputStreamFromFile
     *
     * @param out return void 2017年5月5日 下午4:20:56
     */
    public static void writeToOutputStreamFromFile(String source, OutputStream out) throws Exception {
        FileInputStream inputStream = new FileInputStream(new File(source));
        writeToOutputStreamFromFile(inputStream,out);
    }
    public static void writeToOutputStreamFromFile(byte[] file, OutputStream out) throws Exception {
        out.write(file, 0, file.length);
        out.close();
    }
    public static void writeToOutputStreamFromFile(InputStream inputStream, OutputStream out) throws Exception {
        byte[] b = new byte[METE_FILE_SIZE];
        int len = 0;
        int index = 0;
        while ((len = inputStream.read(b)) > 0) {
            out.write(b, 0, len);
            index++;
        }
        out.close();
        inputStream.close();
    }
    public static void writeToOutputStreamFromFile(FileInputStream inputStream, OutputStream out) throws Exception {
        byte[] b = new byte[METE_FILE_SIZE];
        int len = 0;
        int index = 0;
        while ((len = inputStream.read(b)) > 0) {
            out.write(b, 0, len);
            index++;
        }
        out.close();
        inputStream.close();
    }

    /**
     * 将文件名重命名为 UUID
     * <p>
     * user：Administrator
     * methodName：renameWithUUID
     *
     * @return return String 2017年5月7日 上午10:50:46
     */
    public static String renameWithUUID(String fileName) {
        if (fileName.indexOf(".") == -1) {
            return UUID.randomUUID() + "";
        } else {
            //String  prefix = fileName.substring(0, fileName.indexOf("."));
            //String  prefix = System.currentTimeMillis()+"";
            String prefix = UUID.randomUUID().toString().replaceAll("-", "");
            String subfix = fileName.substring(fileName.lastIndexOf("."));
            return prefix + subfix;
        }
    }

    public static String getCompress(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }


    /**
     * 文件复制
     */
    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), 30 * 1024);
            out = new BufferedOutputStream(new FileOutputStream(dst), 30 * 1024);
            byte[] buff = new byte[30 * 1024];

            int rc = 0;
            while ((rc = in.read(buff)) > 0) {
                out.write(buff, 0, rc);
            }
            logger.info("文件复制完毕");
        } catch (Exception e) {
            logger.error("文件复制失败，失败原因：" + e.getMessage());
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public final static int SHARE_FILE_SIZE = 1024 * 1024 * 50;
    public static  byte[] data=new byte[SHARE_FILE_SIZE];
    public synchronized static byte[] getBytes(String filePath,long skipSize,int readSize){
        try {
            FileInputStream in =new FileInputStream(new File(filePath));
            in.skip(skipSize);
            //当文件没有结束时，每次读取一个字节显示
            if(readSize==SHARE_FILE_SIZE){
                if((in.read(data))!=-1){
                    in.close();
                    return data;
                }else{
                    in.close();
                    return null;
                }
            }else{
                byte[] last = new byte[readSize];
                if((in.read(last))!=-1){
                    in.close();
                    return last;
                }else{
                    in.close();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
        }
    }
    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        try {
            byteArrayOutputStream.write(bfile);
            FileOutputStream fileOutputStream = new FileOutputStream(new File( filePath),true) ;
            byteArrayOutputStream.writeTo( fileOutputStream ) ;
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 删除目录及目录下文件
     * @param path 文件路径
     */
    public static  void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+"/"+tmp[i].getName());
                }
                else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }

    public static String getTodayToSecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getDate(new Date(), 0));
    }
    public static Date getDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return date;
    }


    public static void main(String[] args) throws Exception {
    }
}
