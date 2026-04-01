package service.impl;



import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dao.UserDAO;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
    private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 entity.User user = userDAO.findByUsername(username);
		  System.out.println(">>>> 进入数据库查询用户名: " + username);  // 调试日志
	        if (user == null) throw new UsernameNotFoundException("用户不存在");

	        return new org.springframework.security.core.userdetails.User(
	                user.getUsername(),
	                user.getPassword(),
	                user.isEnabled(),
	                true, true, true,
	                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
	                
	        );
	    }

}
