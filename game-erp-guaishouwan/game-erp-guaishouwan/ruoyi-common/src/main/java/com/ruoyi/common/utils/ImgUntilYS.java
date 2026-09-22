package com.ruoyi.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ImgUntilYS {


    /**
     * 写有间距的文字
     *
     * @param str  文字内容
     * @param x    开始x位置
     * @param y    开始y位置
     * @param rate 间距（x倍）
     * @param g    Graphics对象
     */
    public static void myDrawString(String str, int x, int y, double rate, Graphics g) {
        String tempStr;
        int orgStringWight = g.getFontMetrics().stringWidth(str);
        int orgStringLength = str.length();
        int tempx = x;
        int tempy = y;
        while (str.length() > 0) {
            tempStr = str.substring(0, 1);
            str = str.substring(1);
            g.drawString(tempStr, tempx, tempy);
            tempx = (int) (tempx + (double) orgStringWight / (double) orgStringLength * rate);
        }
    }

    public static int drawStringWithFontStyleLineFeedCommon(Graphics g, String strContent, int maxWdith, int loc_X, int loc_Y, Font font, Integer initLeft, Integer lineHeight) {
        if (Objects.isNull(lineHeight)) {
            lineHeight = 20;
        }
        g.setFont(font);
        //获取字符串 字符的总宽度
        int strWidth = getStringLength(g, strContent);
        //每一行字符串宽度
        // System.out.println("每行字符宽度:"+rowWidth);
        //获取字符高度
        int strHeight = getStringHeight(g, lineHeight);
        //字符串总个数
        //  System.out.println("字符串总个数:"+strContent.length());
        int line = 0;
        int rowWidth = maxWdith;
        if (strWidth > rowWidth) {
            char[] strContentArr = strContent.toCharArray();
            int count = 0;
            int conut_value = 0;
            int charWidth = 0;
            for (int j = 0; j < strContentArr.length; j++) {
                strWidth = strWidth + loc_X;
                if (conut_value >= rowWidth) {
                    conut_value = 0;
                    g.drawString(strContent.substring(count, j), loc_X, loc_Y + strHeight * line);
                    count = j;
                    line++;
                    if (Objects.nonNull(initLeft)) {
                        loc_X = initLeft;
                    }
                } else {
                    if (j == strContentArr.length - 1) {
                        g.drawString(strContent.substring(count, j), loc_X, loc_Y + strHeight * line);
                        line++;
                    } else {
                        charWidth = g.getFontMetrics().charWidth(strContentArr[j]);
                        conut_value = charWidth + conut_value;
                    }
                }

            }

        } else {
            //直接绘制
            g.drawString(strContent, loc_X, loc_Y);
            return strHeight;
        }
        return strHeight * line;
    }

    /**
     * 根据指定宽度自动换行
     *
     * @param g
     * @param maxWdith
     * @param strContent
     * @param loc_X
     * @param loc_Y
     * @param font
     */
    public static int drawStringWithFontStyleLineFeed(Graphics g, String strContent, int maxWdith, int loc_X, int loc_Y, Font font, Integer initLeft, Integer lineHeight) {
        if (Objects.isNull(lineHeight)) {
            lineHeight = 20;
        }
        g.setFont(font);
        //获取字符串 字符的总宽度
        int strWidth = getStringLength(g, strContent);
        //每一行字符串宽度
        // System.out.println("每行字符宽度:"+rowWidth);
        //获取字符高度
        int strHeight = getStringHeight(g, lineHeight);
        //字符串总个数
        //  System.out.println("字符串总个数:"+strContent.length());
        int line = 0;
        int rowWidth = maxWdith - loc_X + 100;
        if (strWidth > rowWidth) {
            char[] strContentArr = strContent.toCharArray();
            int count = 0;
            int conut_value = 0;
            int charWidth = 0;
            for (int j = 0; j < strContentArr.length; j++) {
                strWidth = strWidth + loc_X;
                if (conut_value >= rowWidth) {
                    conut_value = 0;
                    g.drawString(strContent.substring(count, j), loc_X, loc_Y + strHeight * line);
                    count = j;
                    line++;
                    if (Objects.nonNull(initLeft)) {
                        loc_X = initLeft;
                    }
                    rowWidth = maxWdith - 100;
                } else {
                    if (j == strContentArr.length - 1) {
                        String substring = strContent.substring(count, j + 1);
                        System.out.println(substring);
                        g.drawString(strContent.substring(count, j + 1), loc_X, loc_Y + strHeight * line);
                        line++;
                    } else {
                        charWidth = g.getFontMetrics().charWidth(strContentArr[j]);
                        conut_value = charWidth + conut_value;
                    }
                }

            }

        } else {
            //直接绘制
            g.drawString(strContent, loc_X, loc_Y);
            return strHeight;
        }
        return strHeight * line;
    }

    private static int getStringLength(Graphics g, String str) {
        char[] strcha = str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        System.out.println("字符总宽度:" + strWidth);
        return strWidth;
    }


    private static int getStringHeight(Graphics g, Integer lineHeight) {
        int height = g.getFontMetrics().getHeight() + lineHeight;
        //System.out.println("字符高度:"+height);
        return height;
    }

    /**
     * 将BufferedImage转换为byte[]
     *
     * @param image
     * @return
     */
    public static byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os.toByteArray();
    }

    /**
     * 合并任数量的图片成一张图片
     * <p>
     * true代表水平合并，fasle代表垂直合并
     *
     * @param imgs 待合并的图片数组
     * @return
     * @throws IOException
     */
    public static BufferedImage mergeImage(List<BufferedImage> imgs) throws IOException {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);

            if (i % 4 == 0) {
                allh += img.getHeight();
            } else {
                allw += img.getWidth();
            }
//            if (img.getWidth() > allwMax) {
//                allwMax = img.getWidth();
//            }
//            if (img.getHeight() > allhMax) {
//                allhMax = img.getHeight();
//            }
        }

        destImage = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
        // 创建新图片
//        if (isHorizontal) {
//            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
//        } else {
//            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
//        }
        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (int i = 1; i < imgs.size()+1; i++) {
            BufferedImage img = imgs.get(i-1);
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
//            if (isHorizontal) { // 水平方向合并
//                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
//            } else { // 垂直方向合并
//                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
//            }
            if (i % 4 != 0) {
                destImage.setRGB(wx, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
                wx += w1;
            }else {
                destImage.setRGB(wx, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
                wy += h1;
                wx = 0;
            }

        }
        return destImage;
    }
    /**
     * 合并任数量的图片成一张图片
     *
     * @param isHorizontal
     *            true代表水平合并，fasle代表垂直合并
     * @param imgs
     *            待合并的图片数组
     * @return
     * @throws IOException
     */
    public static BufferedImage mergeImageV2(boolean isHorizontal, List<BufferedImage> imgs) throws IOException {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            allw += img.getWidth();
            allh += img.getHeight();
            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            if (isHorizontal) { // 水平方向合并
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            } else { // 垂直方向合并
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            }
            wx += w1;
            wy += h1;
        }
        return destImage;
    }

    //以下是常量,按照阿里代码开发规范,不允许代码中出现魔法值
    private static final Integer ZERO = 0;
    private static final Integer ONE_ZERO_TWO_FOUR = 1024;
    private static final Integer NINE_ZERO_ZERO = 900;
    private static final Integer THREE_TWO_SEVEN_FIVE = 3275;
    private static final Integer TWO_ZERO_FOUR_SEVEN = 2047;
    private static final Double ZERO_EIGHT_FIVE = 0.85;
    private static final Double ZERO_SIX = 0.6;
    private static final Double ZERO_FOUR_FOUR = 0.44;
    private static final Double ZERO_FOUR = 0.4;

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= ZERO || imageBytes.length < desFileSize * ONE_ZERO_TWO_FOUR) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / ONE_ZERO_TWO_FOUR);
        try {
            while (imageBytes.length > desFileSize * ONE_ZERO_TWO_FOUR) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            log.info("图片原大小={}kb | 压缩后大小={}kb",
                    srcSize / ONE_ZERO_TWO_FOUR, imageBytes.length / ONE_ZERO_TWO_FOUR);
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < NINE_ZERO_ZERO) {
            accuracy = ZERO_EIGHT_FIVE;
        } else if (size < TWO_ZERO_FOUR_SEVEN) {
            accuracy = ZERO_SIX;
        } else if (size < THREE_TWO_SEVEN_FIVE) {
            accuracy = ZERO_FOUR_FOUR;
        } else {
            accuracy = ZERO_FOUR;
        }
        return accuracy;
    }

}
