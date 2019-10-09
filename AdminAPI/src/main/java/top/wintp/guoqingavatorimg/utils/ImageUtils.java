package top.wintp.guoqingavatorimg.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片工具类
 *
 * @author zhijun
 */
public class ImageUtils {

    // 存储可访问的图片数据
    private BufferedImage bi;

    // 存储写完字后保存的图片路径，默认是原路径
    private String outFilePath;

    public ImageUtils() {
    }

    // 构建BufferedImage
    public ImageUtils(String filePath) throws IOException {
        Image img = Toolkit.getDefaultToolkit().getImage(filePath);
        bi = ImageRedUtils.toBufferedImage(img);
    }


    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    /**
     * <p>(适用条件[可选])
     * <p>(执行流程[可选])
     * <p>(使用方法[可选])
     * <p>(注意事项[可选])
     *
     * @param x    x位置
     * @param y    y位置
     * @param text 内容
     * @Description: 在图上写字
     * @Created:zhouzj 2013-11-18下午2:05:44
     * @Modified:
     */
    public void drawString(int x, int y, String text) {
        Font font = new Font("sansserif", Font.PLAIN, 14);
        this.drawString(x, y, text, font, Color.black);
    }

    public void drawString(int x, int y, String text, Font font, Color color) {
        this.drawString(x, y, text, font, color, 0, 0);
    }

    // 在图上写字
    public void drawString(int x, int y, String text, Font font, Color color, int countPerRow, int lineHeight) {
        Graphics2D graphics2d = bi.createGraphics();
        graphics2d.setFont(font);
        graphics2d.setColor(color);

        if (countPerRow > 0 && text != null && text.length() > countPerRow) {
            String tempStr = "";
            int currIndex = 0;
            int length = text.length();
            do {
                if (length - currIndex > countPerRow) {
                    tempStr = text.substring(currIndex, countPerRow);
                } else {
                    tempStr = text.substring(currIndex);
                }
                graphics2d.drawString(tempStr, x, y + (lineHeight * currIndex / countPerRow));

                currIndex += countPerRow;
            } while (currIndex < length);
        } else {
            graphics2d.drawString(text, x, y);
        }

        graphics2d.dispose();
        bi.flush();
    }

    /**
     * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
     *
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {


        // 这种是黑色底的
//      BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        // 设置“抗锯齿”的属性
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }


    // 在图上画图片
    public void drawImage(int x, int y, int width, int height, BufferedImage bufferedImage) throws IOException {
        Graphics2D graphics2d = bi.createGraphics();
        graphics2d.drawImage(bufferedImage, x, y, width, height, null);
    }

    public void saveFile() {
        FileOutputStream fos = null;

        try {
            if (null != outFilePath && outFilePath.indexOf(".") > 0) {
                File fileDir = new File(outFilePath.substring(0, outFilePath.lastIndexOf("/")));
                if (!fileDir.isDirectory() || !fileDir.exists()) {
                    /*fileDir.mkdir();*/
                    fileDir.mkdirs();
                }
            }

            File outFile = new File(outFilePath);

            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            fos = new FileOutputStream(outFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
            param.setQuality((float) 0.8, true);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bi);
            bos.close();

//			FileImageOutputStream outStream = new FileImageOutputStream(outFile);
//			ImageIO.write(bi, "png", outStream);
//			outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fos)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }

    public void free() {
        bi.flush();
    }

    public int getWidth() {
        return bi.getWidth();
    }

    public int getHeight() {
        return bi.getHeight();
    }




}
