package com.ruoyi.web.controller.model.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.ImgUntil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.UUIDGenerator;
import com.ruoyi.system.gen.IGenAPI;
import com.ruoyi.system.model.service.UploadService;
import com.ruoyi.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/genImg")
@Slf4j
public class GengerImageController {


    private static String gengerImg = "genger-img/";

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IGenAPI iGenAPI;

    @ResponseBody
    @PostMapping("/model1")
    public AjaxResult model1(@RequestParam Map<String,Object> params)
    {

        try{
            // 读取本地图片文件
            // File file = new File("E:/imgs/test.png");
            // System.out.println("图片名称：" + file.getName());
            // System.out.println("图片大小：" + file.length() / 1024 + " kb");
            // 将文件对象转化为图片对象
            // BufferedImage image = ImageIO.read(file);
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/back-img.jpg");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 250, 255, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 55);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 120);

            //内容
            font = new Font("微软雅黑", Font.PLAIN, 40);
            pen.setColor(new Color(255, 250, 0, 255));
            String strContent = MapUtils.getString(params,"strContent");
            ImgUntil.drawStringWithFontStyleLineFeed(pen,strContent,image.getWidth() - 200,100,190,font,null,null);

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes, gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }
    @ResponseBody
    @PostMapping("/modelTest")
    public AjaxResult modelTest(@RequestParam Map<String,Object> params)
    {

        try{
            // 读取本地图片文件
            // File file = new File("E:/imgs/test.png");
            // System.out.println("图片名称：" + file.getName());
            // System.out.println("图片大小：" + file.length() / 1024 + " kb");
            // 将文件对象转化为图片对象
            // BufferedImage image = ImageIO.read(file);
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/0230201105505.jpg");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 250, 255, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 55);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 120);

            //内容
            font = new Font("华文中宋", Font.PLAIN, 80);
            pen.setColor(new Color(255, 250, 0, 255));
            String strContent = MapUtils.getString(params,"strContent");
            ImgUntil.drawStringWithFontStyleLineFeedTitle(pen,strContent,image.getWidth() - 200,100,190,font,null,null);

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes, gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }

    @ResponseBody
    @PostMapping("/model2")
    public AjaxResult model2(@RequestParam Map<String,Object> params)
    {

        try{
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/back-img.jpg");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 250, 255, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 55);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 120);

            //基本信息
            //英雄数量
            String cotent1 = MapUtils.getString(params,"cotent1");
            //皮肤数量
            String cotent2 = MapUtils.getString(params,"cotent2");
            //贵族等级
            String cotent3 = MapUtils.getString(params,"cotent3");
            //贵族积分
            String cotent4 = MapUtils.getString(params,"cotent4");

            String title1 = MapUtils.getString(params,"title1");
            String title2 = MapUtils.getString(params,"title2");
            String title3 = MapUtils.getString(params,"title3");
            String title4 = MapUtils.getString(params,"title4");


            font = new Font("微软雅黑", Font.BOLD, 45);
            pen.setFont(font);
            String cotent = title1 + "：" + cotent1 + "       " + title2 + "：" + cotent2 + "      " + title3 + "：" + cotent3 + "       " + title4 + "：" + cotent4;
            fontMetrics = pen.getFontMetrics(font);
            int cotentWith = fontMetrics.stringWidth(cotent);

            pen.setColor(new Color(255, 250, 0, 255));
            pen.drawString(cotent,(image.getWidth() - cotentWith) / 2,180);

            //内容
            font = new Font("微软雅黑", Font.PLAIN, 40);
            pen.setColor(new Color(255, 250, 0, 255));
            String strContent = MapUtils.getString(params,"strContent");
            ImgUntil.drawStringWithFontStyleLineFeed(pen,strContent,image.getWidth() - 200,100,250,font,null,null);

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes, gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }
    @ResponseBody
    @PostMapping("/model3")
    public AjaxResult model3(@RequestParam Map<String,Object> params)
    {

        int initLeft = 50;

        try{
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/model1-1.png");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 255, 0, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 45);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 70);

            //基本信息
            //英雄数量
            String cotent1 = MapUtils.getString(params,"cotent1");
            //皮肤数量
            String cotent2 = MapUtils.getString(params,"cotent2");
            //贵族等级
            String cotent3 = MapUtils.getString(params,"cotent3");
            //贵族积分
            String cotent4 = MapUtils.getString(params,"cotent4");
            String cotent5 = MapUtils.getString(params,"cotent5");
            String cotent6 = MapUtils.getString(params,"cotent6");

            String title1 = MapUtils.getString(params,"title1");
            String title2 = MapUtils.getString(params,"title2");
            String title3 = MapUtils.getString(params,"title3");
            String title4 = MapUtils.getString(params,"title4");
            String title5 = MapUtils.getString(params,"title5");
            String title6 = MapUtils.getString(params,"title6");


            font = new Font("微软雅黑", Font.BOLD, 30);
            pen.setFont(font);
            pen.setColor(new Color(255, 250, 255, 255));
            pen.drawString(title1 + ": ",initLeft,195);
            pen.drawString(title2 + ": ",initLeft,235);
            pen.drawString(title3 + ": ",initLeft,275);

            pen.drawString(title4 + ": ",initLeft + 400,195);
            pen.drawString(title5 + ": ",initLeft + 400,235);
            pen.drawString(title6 + ": ",initLeft + 400,275);

//            pen.drawString(title4,initLeft,230);
            fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            int title1Width = fontMetrics.stringWidth(title1 + ": ");
            pen.setColor(new Color(255, 250, 0, 255));
            pen.drawString(cotent1,initLeft + title1Width + 20,195);
            int title2Width = fontMetrics.stringWidth(title2 + ": ");
            pen.drawString(cotent2,initLeft + title2Width + 20,235);
            int title3Width = fontMetrics.stringWidth(title3 + ": ");
            pen.drawString(cotent3,initLeft + title3Width + 20,275);

            int title4Width = fontMetrics.stringWidth(title4 + ": ");
            pen.drawString(cotent4,initLeft + title4Width + 20 + 400,195);
            int title5Width = fontMetrics.stringWidth(title5 + ": ");
            pen.drawString(cotent5,initLeft + title5Width + 20 + 400,235);
            int title6Width = fontMetrics.stringWidth(title6 + ": ");
            pen.drawString(cotent6,initLeft + title6Width + 20 + 400,275);


            //内容
            font = new Font("微软雅黑", Font.BOLD, 40);
            pen.setColor(new Color(255, 250, 0, 255));
            String strContent = MapUtils.getString(params,"strContent");
            ImgUntil.drawStringWithFontStyleLineFeedCommon(pen,strContent,image.getWidth() - 120,40,400,font,null,5);


            String remake = MapUtils.getString(params,"remake");
            pen.setColor(new Color(255, 250, 0, 255));
            int remakeWidth = fontMetrics.stringWidth(remake + ": ");
            pen.drawString(remake,(image.getWidth() - remakeWidth) / 2,772);

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes, gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }
//    https://game.ikbh.top/logo/model4.jpg
//    https://game.ikbh.top/logo/model5.jpg

    /**
     * 火影生成
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/model4")
    public AjaxResult model4(@RequestParam Map<String,Object> params)
    {
        int initLeft = 200;

        try{
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/model4.jpg");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 250, 255, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 130);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 300);

            //基本信息
            //英雄数量
            String cotent1 = MapUtils.getString(params,"cotent1");
            //皮肤数量
            String cotent2 = MapUtils.getString(params,"cotent2");
            //贵族等级
            String cotent3 = MapUtils.getString(params,"cotent3");
            //贵族积分
            String cotent4 = MapUtils.getString(params,"cotent4");

            String title1 = MapUtils.getString(params,"title1");
            String title2 = MapUtils.getString(params,"title2");
            String title3 = MapUtils.getString(params,"title3");
            String title4 = MapUtils.getString(params,"title4");


            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
//            String cotent = title1 + "：" + cotent1 + "       " + title2 + "：" + cotent2 + "      " + title3 + "：" + cotent3 + "       " + title4 + "：" + cotent4;

            pen.setColor(new Color(255, 250, 255, 255));
            pen.drawString(title1 + "  " + cotent1,initLeft,500);

            pen.drawString(title2 ,initLeft,700);

            fontMetrics = pen.getFontMetrics(font);
            int title2With = fontMetrics.stringWidth(title2);

            pen.setColor(new Color(255, 0, 30, 255));
            pen.drawString(cotent2,title2With+initLeft+50,700);

            pen.setColor(new Color(255, 255, 255, 255));
            pen.drawString(title3,title2With+initLeft+600,700);

            pen.setColor(new Color(255, 0, 30, 255));
            fontMetrics = pen.getFontMetrics(font);
            int title3With = fontMetrics.stringWidth(title3);
            pen.drawString(cotent3,title2With+initLeft+600+title3With+50,700);

            pen.setColor(new Color(255, 255, 255, 255));
            fontMetrics = pen.getFontMetrics(font);
            int cotent3With = fontMetrics.stringWidth(cotent3);
            pen.drawString(title4,title2With+initLeft+800+title3With+160+cotent3With,700);

            pen.setColor(new Color(255, 0, 30, 255));
            fontMetrics = pen.getFontMetrics(font);
            int title4With = fontMetrics.stringWidth(title4);
            pen.drawString(cotent4,title2With+initLeft+800+title3With+160+cotent3With+title4With+50,700);

            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            String des1Title = MapUtils.getString(params, "des1Title");
            String strContent1 = MapUtils.getString(params, "strContent1");
            pen.setColor(new Color(255, 255, 255, 255));
            pen.drawString(des1Title,initLeft,1250);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des1TitleWith = fontMetrics.stringWidth(des1Title);
            int height = ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent1, image.getWidth() - 300, des1TitleWith + 170 + initLeft, 1240, font, initLeft,null);

            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 255, 255));
            String des2Title = MapUtils.getString(params, "des2Title");
            pen.drawString(des2Title,initLeft,1250+height+30);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 255, 0, 255));
            String strContent2 = MapUtils.getString(params, "strContent2");
            fontMetrics = pen.getFontMetrics(font);
            int des2TitleWith = fontMetrics.stringWidth(des2Title);
            ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent2, image.getWidth() - 300, des2TitleWith + 170 + initLeft, 1250+height+30, font, initLeft,null);

            //备注
            String remake = MapUtils.getString(params, "remake");
            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int remakeWith = fontMetrics.stringWidth(remake);
            pen.drawString(remake,(image.getWidth() - remakeWith) / 2,2550);

            //内容

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes,gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            throw new GlobalException("请输入完整信息");
        }
    }
    /**
     * 永杰生成
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/model5")
    public AjaxResult model5(@RequestParam Map<String,Object> params)
    {
        int initLeft = 200;
        int desHeight = 700;

        try{
            // 获取网络图片
            URL url = new URL("https://game.ikbh.top/logo/model5.jpg");
            BufferedImage image = ImageIO.read(url.openStream());
            // 创建画笔
            Graphics2D pen = image.createGraphics();
            // 设置画笔颜色为白色
            // pen.setColor(Color.WHITE);
            pen.setColor(new Color(255, 250, 255, 255));
            // 设置画笔字体样式为微软雅黑，斜体，文字大小为20px
            Font font = new Font("微软雅黑", Font.BOLD, 130);
            pen.setFont(font);
            FontMetrics fontMetrics = pen.getFontMetrics(font);
            // 写上水印文字和坐标
            String titleName = MapUtils.getString(params,"titleName");
            int textWidth = fontMetrics.stringWidth(titleName);
            pen.drawString(titleName, (image.getWidth() - textWidth) / 2, 200);


            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            String des1Title = MapUtils.getString(params, "des1Title");
            String strContent1 = MapUtils.getString(params, "strContent1");
            pen.setColor(new Color(255, 255, 255, 255));
            pen.drawString(des1Title,initLeft,desHeight);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 0, 1, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des1TitleWith = fontMetrics.stringWidth(des1Title);
            int height = ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent1, image.getWidth() - 300, des1TitleWith + 170 + initLeft, desHeight, font, initLeft,null);


            //第二行
            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 255, 255));
            String des2Title = MapUtils.getString(params, "des2Title");
            pen.drawString(des2Title,initLeft,desHeight+height+60);
            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 0, 1, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des2TitleWith = fontMetrics.stringWidth(des2Title);
            String strContent2 = MapUtils.getString(params, "strContent2");
            int twoheight = ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent2, image.getWidth() - 300, des2TitleWith + 170 + initLeft, desHeight + height + 60, font, initLeft,null);

            //第三行
            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 255, 255));
            String des3Title = MapUtils.getString(params, "des3Title");
            pen.drawString(des3Title,initLeft,desHeight+height+120+twoheight);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des3TitleWith = fontMetrics.stringWidth(des3Title);
            String strContent3 = MapUtils.getString(params, "strContent3");
            int threeheight = ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent3, image.getWidth() - 300, des3TitleWith + 170 + initLeft, desHeight + height + 120 + twoheight, font, initLeft,null);


            //第四行
            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 255, 255));
            String des4Title = MapUtils.getString(params, "des4Title");
            pen.drawString(des4Title,initLeft,desHeight+height+180+twoheight+threeheight);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des4TitleWith = fontMetrics.stringWidth(des4Title);
            String strContent4 = MapUtils.getString(params, "strContent4");
            int fourheight = ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent4, image.getWidth() - 300, des4TitleWith + 170 + initLeft, desHeight + height + 180 + twoheight + threeheight, font, initLeft,null);

            //第五行
            font = new Font("微软雅黑", Font.BOLD, 90);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 255, 255));
            String des5Title = MapUtils.getString(params, "des5Title");
            pen.drawString(des5Title,initLeft,desHeight+height+240+twoheight+threeheight+fourheight);

            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int des5TitleWith = fontMetrics.stringWidth(des5Title);
            String strContent5 = MapUtils.getString(params, "strContent5");
            ImgUntil.drawStringWithFontStyleLineFeed(pen, strContent5, image.getWidth() - 300, des5TitleWith + 170 + initLeft, desHeight+height+240+twoheight+threeheight+fourheight, font, initLeft,null);



            //备注
            String remake = MapUtils.getString(params, "remake");
            font = new Font("微软雅黑", Font.BOLD, 70);
            pen.setFont(font);
            pen.setColor(new Color(255, 255, 0, 255));
            fontMetrics = pen.getFontMetrics(font);
            int remakeWith = fontMetrics.stringWidth(remake);
            pen.drawString(remake,(image.getWidth() - remakeWith) / 2,2650);

            //内容

            byte[] bytes = ImgUntil.bufferedImageToByteArray(image);
            String path = uploadService.upload2Qiniu(bytes,gengerImg + UUIDGenerator.generate());
            log.info("path={}",path);
            return AjaxResult.success(path);
//            // 创建新图片文件
//            File file = new File("E:/img/test2.png");
//            // 将处理好的图片数据写入到新图片文件中
//            FileOutputStream fos = new FileOutputStream(file);
//            ImageIO.write(image, "png", fos);

        }catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("请输入完整信息");
        }
    }


    /**
     * 计数器
     * @param params
     * @return
     */

    @ResponseBody
    @PostMapping("/addNum")
    public AjaxResult addNum(@RequestParam Map<String,Object> params)
    {

        String type = MapUtils.getString(params, "type");

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("genger-img");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        for (SysDictData dictData : sysDictDataList) {
            if (Objects.equals(dictData.getDictLabel(),type)){
                int n = 0;
                if (StringUtils.isNotEmpty(dictData.getDictValue())){
                    Integer dictValue = Integer.valueOf(dictData.getDictValue());
                    dictValue+=1;
                    dictData.setDictValue(dictValue+"");
                }else {
                    dictData.setDictValue(n+"");
                }
                dictDataService.updateDictData(dictData);
            }
        }
        return AjaxResult.success();
    }



    public static void main(String[] args) {
        Map<String,Object> params = new HashMap<>();
//        params.put("titleName","安卓官服");
//        params.put("strContent1","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子 矮人面具 黑脸 箬笠  宴会围巾 毛绒白领 小鸟发饰 单边羽毛 双边羽毛 毕业羽毛 彩虹耳坠 蝴蝶发饰 太阳帽 钢琴 沙滩椅 屁垫 ");
//        params.put("cotent1","V11");
//        params.put("cotent2","100");
//        params.put("cotent3","100");
//        params.put("cotent4","100");
//        params.put("title1","贵族等级");
//        params.put("title2","S级忍者数量");
//        params.put("title3","A级忍者数量");
//        params.put("title4","战力");
//        params.put("des1Title","S忍者简介");
//        params.put("des2Title","A忍者简介");
//        params.put("remake","A忍者简介");
//        params.put("strContent2","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子 矮人面具 黑脸 箬笠  宴会围巾 毛绒白领 小鸟发饰 单边羽毛 双边羽毛 毕业羽毛 彩虹耳坠 蝴蝶发饰 太阳帽 钢琴 沙滩椅 屁垫 ");

        params.put("titleName","安卓官服");
        params.put("strContent","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤, 小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤, 小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤");
        params.put("cotent1","V11");
        params.put("cotent2","100");
        params.put("cotent3","100");
        params.put("cotent4","100");
        params.put("cotent5","100");
        params.put("cotent6","100");
        params.put("title1","贵族等级");
        params.put("title2","皮肤数量");
        params.put("title3","英雄数量");
        params.put("title4","战力");
        params.put("title5","英雄数量");
        params.put("title6","英雄数量");
        params.put("des1Title","红色兵器皮肤");
        params.put("des2Title","红色人物皮肤");
        params.put("des3Title","金色兵器皮肤");
        params.put("des4Title","金色人物皮肤");
        params.put("des5Title","金色人物挂饰");
        params.put("remake","A忍者简介");
        params.put("strContent2","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子");
        params.put("strContent3","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子 矮人面具 黑脸 箬笠  宴会围巾 毛绒白领 小鸟发饰 单边羽毛 双边羽毛 毕业羽毛 彩虹耳坠 蝴蝶发饰 太阳帽 钢琴 沙滩椅 屁垫 ");
        params.put("strContent4","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子 矮人面具 黑脸 箬笠  宴会围巾 毛绒白领 小鸟发饰 单边羽毛 双边羽毛 毕业羽毛 彩虹耳坠 蝴蝶发饰 太阳帽 钢琴 沙滩椅 屁垫 ");
        params.put("strContent5","小王子裤， 蓝黄羽袖， 蓝胖裤， 袋鼠裤 ，击剑裤 匠人护裙 半袖袍子 矮人面具 黑脸 箬笠  宴会围巾 毛绒白领 小鸟发饰 单边羽毛 双边羽毛 毕业羽毛 彩虹耳坠 蝴蝶发饰 太阳帽 钢琴 沙滩椅 屁垫 ");

//        model2(params);
//        model3(params);
//        model4(params);
//        model5(params);

//        [
//             {
//              images:["path","path2","path3"],
//              column:"3"
//             }
//         ]

    }
}
