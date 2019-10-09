package top.wintp.guoqingavatorimg.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: pyfysf
 * <p>
 * @qq: 337081267
 * <p>
 * @CSDN: http://blog.csdn.net/pyfysf
 * <p>
 * @blog: http://wintp.top
 * <p>
 * @email: pyfysf@163.com
 * <p>
 * @time: 2019/9/25
 */
@Slf4j
public class CreateImageUtil {

    private final static List<String> templeIdList = new ArrayList<>();

    static {
        templeIdList.add("template1");
        templeIdList.add("template2");
        templeIdList.add("template3");
    }

    /**
     * 创建头像
     *
     * @param avatarPath
     * @return
     */
    public static Map<String, String> generateImage(String avatarPath) {
        Map<String, String> resultUrlMap = new HashMap<>();

        try {
            for (String templeId : templeIdList) {
                //头像来当背景图 然后将国旗放到头像上面
                ImageUtils iu = new ImageUtils(avatarPath);
                int width = iu.getWidth();
                int height = iu.getHeight();

                String outFilePath = ConstantValue.UPLOADED_FOLDER + System.currentTimeMillis() + ".png";
                iu.setOutFilePath(outFilePath);
                log.info("CreateImageUtil  generateServiceCertificateImage() resultImgFileName " + outFilePath);
                String templatePath = "C:/Project/Java/GuoQingAvator/templateImg/" + templeId + ".png";
                File file = new File(templatePath);
                String absolutePath = file.getAbsolutePath();
                log.info("absolutePath:{}", absolutePath);

                BufferedImage avatorBuffImage = ImageIO.read(file);

                //Image img = Toolkit.getDefaultToolkit().getImage(resource.getFile());
                //BufferedImage avatorBuffImage = ImageRedUtils.toBufferedImage(img);


                iu.drawImage(0, 0, width, height, avatorBuffImage);
                iu.saveFile();

                outFilePath = outFilePath.replaceAll(ConstantValue.UPLOADED_FOLDER, ConstantValue.REQUSET_IMG_URL);

                resultUrlMap.put(templeId, outFilePath);

                //iu.free();
            }

            return resultUrlMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
