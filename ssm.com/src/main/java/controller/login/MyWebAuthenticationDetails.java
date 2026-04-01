package controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

	  private final String captcha;
	    private final HttpServletRequest request; // ✅ 保存 request 引用
	    private final String role;
	    public MyWebAuthenticationDetails(HttpServletRequest request) {
	        super(request);
	        this.request = request;
	        this.captcha = request.getParameter("captcha");
	        this.role = request.getParameter("role");
	    }

	    public String getCaptcha() {
	        return captcha;
	    }

	    public HttpServletRequest getRequest() {
	        return request;
	    }
	    public String getRole() {
	        return role;
	    }
	}