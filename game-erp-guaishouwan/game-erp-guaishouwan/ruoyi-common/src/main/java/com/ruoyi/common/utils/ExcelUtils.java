package com.ruoyi.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-09-08 11:42
 */
public class ExcelUtils {


    /**
     * 单例模式每次只实例化一个对象,必须是私有方法
     * @return
     */
    private ExcelUtils getInstance(){
        return new ExcelUtils();
    }

    /**
     * 导出Excel通用工具类
     * @param titiles:标题数组
     * @param lists:数据
     * @param workbook:工作簿对象
     * @param outputStream:输出流
     */
    public static void exportExcel(String sheetName, String[] titiles, List<List<String>> lists, XSSFWorkbook workbook, OutputStream outputStream) throws IOException {
        try {
            //1、由工作簿对象创建sheet页,且创建字体样式对象
            XSSFSheet sheet = workbook.createSheet(sheetName);
            XSSFCellStyle cellStyle = createCellStyle(workbook);
            //2、创建标题行,由第一行作为标题
            XSSFRow rowTitile = sheet.createRow(0);
            for (int i = 0; i < titiles.length; i++) {
                XSSFCell cell = rowTitile.createCell(i);
                cell.setCellStyle(cellStyle);
                //将标题值设置到第一行的单元格中
                cell.setCellValue(titiles[i]);
            }
            //3、循环遍历数据的长度决定创建多少个单元格
            for (int i = 0; i < lists.size(); i++) {
                XSSFRow rowData = sheet.createRow(i + 1);
                //获取每一行对象创建单元格
                List <String> list = lists.get(i);
                for (int j = 0; j < list.size(); j++) {
                    XSSFCell cell = rowData.createCell(j);
                    /**
                     * 此处暂时省略字段类型值的判断,以后想起来再进行完善
                     */
                    cell.setCellValue(list.get(j));
                }
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //无论成功与否就关闭所有流
            workbook.close();
            outputStream.close();
        }
    }

    /**
     * 创建单元格样式,暂时只对单元格和单元格背景色进行操作,后期扩展字体操作
     * @param workbook
     * @return
     */
    private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建字体
        return style;
    }
}
