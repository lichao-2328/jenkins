package controller.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.impl.DefaultKaptcha;
@Controller
public class CaptchaController {
	@Autowired
	@Qualifier("kaptchaProducer")
    private DefaultKaptcha kaptcha;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        // 设置响应头，禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setContentType("image/jpeg");

        // 生成验证码文本和图片
        String text = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(text);

        
        
        String key = "captcha:" + session.getId();
        redisTemplate.opsForValue().set(key, text, 3, TimeUnit.MINUTES);

        // 输出图片到响应
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
            out.flush();
        }
    }
}