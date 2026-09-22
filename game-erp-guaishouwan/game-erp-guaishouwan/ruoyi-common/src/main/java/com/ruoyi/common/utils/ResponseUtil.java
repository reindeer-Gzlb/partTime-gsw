package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author admin
 * @create 2020-09-08 14:45
 */
public class ResponseUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 根据HttpServletResponse对象和文件名获取输出至浏览器对象
     * @param fileName
     * @param response
     * @return
     * @throws IOException
     */
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置浏览器的输出响应为excel
        response.setContentType("application/x-excel");
        //设置返回文件名的编码格式
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            LOGGER.error("获取输出文件流异常");
        }
        return outputStream;
    }
}
