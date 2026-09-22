package com.ruoyi.system.model.untl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 图片添加水印
 *
 * @author qzz
 */
public class ImageUtils {

    public static BufferedImage merImg(List<Attribute> imglist) throws IOException {


        URL url = new URL("https://game.ikbh.top/logo/back-img.jpg");
        BufferedImage image = ImageIO.read(url.openStream());
        List<String> imgs = imglist.stream().map(m -> m.getImgUrl()).collect(Collectors.toList());
        List<String> titles = imglist.stream().map(m -> m.getFieldName()).collect(Collectors.toList());

        URL urlfirst = new URL(imgs.get(0));
        BufferedImage srcWaterMarkFirst = ImageIO.read(urlfirst.openStream());
        int heightWaterMarkFirst = srcWaterMarkFirst.getHeight(null);
        int i = 0;
        int line = 1;
        int y  = 80 + 210 + 15;
        int imgy  = 60;
        int index = 0;
        for (String img : imgs) {
            URL url1 = null;
            try {
                String waterMarkContent = titles.get(index);
                url1 = new URL(img);
                BufferedImage srcWaterMark = ImageIO.read(url1.openStream());
                //获取水印图片的宽度
                int widthWaterMark = srcWaterMarkFirst.getWidth();
//                int widthWaterMark = 158;
//                System.out.println(widthWaterMark);
                int heightWaterMark = srcWaterMarkFirst.getHeight(null);
//                int heightWaterMark = 210;
//                System.out.println(heightWaterMark);

                int initWith = 158;
                int height = 210;

                Graphics2D g = image.createGraphics();
                //绘制原始图片
                //根据图片的背景设置水印颜色
                g.setColor(new Color(255, 255, 255, 255));
                //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
                g.setFont(new Font("微软雅黑", Font.BOLD, 30));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));

                if (i == 0){

                    g.drawImage(srcWaterMark, 150,
                            imgy, initWith, height, null);
                    g.drawString(waterMarkContent, initWith / 10 + 150, y);

                }else {
                    g.drawImage(srcWaterMark, (initWith * i) + 150 + 60 * i,
                            imgy, initWith, height, null);

                    g.drawString(waterMarkContent, (initWith / 10) * (i * 4 - 1) + 30 + 150 + initWith * i, y);

                }
                i ++;

                if (i != 0 && i % 7 == 0){
                    if (line == 1){
                        imgy = 80 + (height * line) + 30;
                        y  = 80 + (height * 2) + 60;
                    }else if (line == 2){
                        imgy = 80 + (height * line) + (40 * line);
                        y  = 80 + (height * (line + 1)) + (55 * line);
                    }else if (line == 3){
                        imgy = 80 + (height * line) + 700;
                        y  = 80 + (height * (line + 1)) + 70;
                    }
                    line ++;
                    i = 0;
                }
                index++;
                g.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        //待存储的地址
//        String tarImgPath = "E:\\img\\3.jpg";
//        // 输出图片
//        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
//        ImageIO.write(image, "png", outImgStream);
//        System.out.println("添加水印完成");
//        outImgStream.flush();
//        outImgStream.close();


        return image;

    }

    /**
     * 获取水印文字的长度
     *
     * @param waterMarkContent
     * @param g
     * @return
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}

