package controller.login;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dao.UserDAO;
import entity.User;
import service.impl.MyUserDetailsService;
@Component("myAuthenticationProvider")
public class MyAuthenticationProvider implements AuthenticationProvider {
	 @Autowired
	    private MyUserDetailsService myUserDetailsService;

	    @Autowired
	    private RedisTemplate<String, String> redisTemplate;

	    @Autowired
	    private UserDAO userDAO;  

	    @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        // 1️⃣ 获取登录凭据
	        String username = authentication.getName();
	        String password = (authentication.getCredentials() != null)
	                ? authentication.getCredentials().toString()
	                : "";

	        if (!(authentication.getDetails() instanceof MyWebAuthenticationDetails)) {
	            throw new BadCredentialsException("认证请求异常，请重新登录");
	        }

	        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
	        String inputCaptcha = details.getCaptcha();
	        String inputRole = details.getRole();
	        HttpServletRequest request = details.getRequest();

	        // 2️⃣ 校验验证码（从 Redis 中读取）
	        String redisKey = "captcha:" + request.getSession().getId();
	        String redisCaptcha = redisTemplate.opsForValue().get(redisKey);
	        if (redisCaptcha == null) {
	            throw new BadCredentialsException("验证码已过期，请重新获取");
	        }
	        if (!redisCaptcha.equalsIgnoreCase(inputCaptcha)) {
	            throw new BadCredentialsException("验证码错误");
	        }

	        // 3️⃣ 校验用户是否存在
	        User user = userDAO.findByUsername(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("用户不存在");
	        }

	        // 4️⃣ 校验角色一致性
	        String dbRole = user.getRole();
	        if (dbRole == null || !dbRole.equalsIgnoreCase(inputRole)) {
	            throw new BadCredentialsException("身份与用户名不匹配");
	        }

	        // 5️⃣ 调用 UserDetailsService 加载用户信息（自动包含密码和权限）
	        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
	        if (userDetails == null) {
	            throw new UsernameNotFoundException("无法加载用户信息");
	        }

	        // 6️⃣ 校验密码（此处建议使用加密匹配）
	        // ❗如果密码在数据库中是加密的，请使用 PasswordEncoder.matches()
	        if (!userDetails.getPassword().equals(password)) {
	            throw new BadCredentialsException("用户名或密码错误");
	        }

	        // 7️⃣ 构建认证成功的对象（Spring Security 内部使用）
	        UsernamePasswordAuthenticationToken authenticationToken =
	                new UsernamePasswordAuthenticationToken(
	                        userDetails,              // 已认证用户信息
	                        password,                 // 凭据（通常不再使用）
	                        userDetails.getAuthorities() // 授权角色
	                );

	        // ✅ 可选：清除验证码，避免重复使用
	        redisTemplate.delete(redisKey);

	        return authenticationToken;
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        // 指定此 Provider 仅支持 UsernamePasswordAuthenticationToken 类型
	        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	    }
	}