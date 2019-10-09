package top.wintp.guoqingavatorimg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import top.wintp.guoqingavatorimg.utils.ConstantValue;
import top.wintp.guoqingavatorimg.utils.CreateImageUtil;

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
@RestController
@Slf4j
public class FrontController {

    @GetMapping("/")
    public String index() {
        return "<a href='http://wintp.top' target='_blank'>你看这里>>http://wintp.top</a>";
    }

    @PostMapping("/front/uploadFile")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(ConstantValue.UPLOADED_FOLDER + System.currentTimeMillis() + "_" + file.getOriginalFilename());

            File destFileDir = path.toFile().getParentFile();
            if (!destFileDir.exists()) {
                destFileDir.mkdirs();
            }

            Files.write(path, bytes);


            Map<String, String> resultUrlList = CreateImageUtil.generateImage(path.toFile().getAbsolutePath());

            log.info("FrontController  uploadFile()   resultUrlList   " + resultUrlList);
            log.info("FrontController  uploadFile()   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ");


            return resultUrlList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
