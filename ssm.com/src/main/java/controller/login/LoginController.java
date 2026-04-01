package controller.login;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

	  @GetMapping("/login")
	    public String loginPage(@RequestParam(value="role", required=false) String role,
	                            @RequestParam(value="error", required=false) String error,
	                            Model model) {
		  log.info("访问登录页");
	        model.addAttribute("role", role != null ? role : "ADMIN");
	        model.addAttribute("loginError", error != null);
	        return "student/login";  
	    }
	    // 登录成功跳转（POST 或 Spring Security Success Handler）
	    @GetMapping("/login-success")
	    public String loginSuccess(Authentication auth) {
	        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
	        log.info("登录成功，角色: {}", roles);
	         if (roles.contains("ROLE_TEACHER")) {
	            return "redirect:/teacher/home";
	        } else {
	            return "redirect:/student/list";
	        }
	    }
	    
	    // 验证码校验接口（前端在登录前可先调用）
	    @PostMapping("/login-captcha")
	    @ResponseBody
	    public String verifyCaptcha(@RequestParam String captcha, HttpSession session) {
	        String key = "captcha:" + session.getId();
	        String redisCaptcha = redisTemplate.opsForValue().get(key);
	       if (redisCaptcha == null) {
            log.warn("验证码已过期或未生成, sessionId={}", session.getId());
            return "验证码已过期，请重新获取";
        }

        if (!redisCaptcha.equalsIgnoreCase(captcha)) {
            log.warn("验证码不匹配, 输入={}, 正确={}", captcha, redisCaptcha);
            return "验证码错误";
        }

        log.info("验证码正确, sessionId={}", session.getId());
        return "验证码正确";
    }
	    
}
