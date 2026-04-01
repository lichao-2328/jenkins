<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>登录</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        /* 背景图 */
        body {
            background: url("<%= request.getContextPath() %>/static/images/school001.jpg") no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* 登录框样式，与背景图融合 */
        .login-box {
            width: 360px;
            padding: 40px;
            background: rgba(0, 0, 0, 0.4); /* 半透明黑色，使登录框和背景融合 */
            border-radius: 20px;
            box-shadow: 0 0 40px rgba(0,0,0,0.6); /* 强化阴影，让登录框更立体 */
            backdrop-filter: blur(8px); /* 背景虚化，让框和背景融合 */
            color: #fff; /* 文本白色 */
            text-align: center;
        }

        .login-box h2 {
            margin-bottom: 25px;
            font-size: 24px;
            letter-spacing: 1px;
            color: #fff;
        }

        /* 输入框和下拉框 */
        .login-box input[type="text"],
        .login-box input[type="password"],
        .login-box select {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: none;
            border-radius: 10px;
            background: rgba(255,255,255,0.2); /* 半透明背景，与登录框色调统一 */
             color: #000; /* 黑色字体 */
    background: rgba(255,255,255,0.9); 
            box-sizing: border-box;
        }

        .login-box input::placeholder {
            color: rgba(255,255,255,0.7);
        }

        /* 按钮样式 */
        .login-box button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            border: none;
            border-radius: 10px;
            background: linear-gradient(45deg, #6a11cb, #2575fc); /* 渐变色 */
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: 0.3s;
        }

        .login-box button:hover {
            background: linear-gradient(45deg, #2575fc, #6a11cb);
        }

        /* 登录失败提示 */
        .error-message {
            color: #ff6b6b;
            margin-top: 10px;
        }

        /* 手机端自适应 */
        @media (max-width: 400px) {
            .login-box {
                width: 90%;
                padding: 30px;
            }
        }
        /* 验证码整体布局，与输入框保持一致的上下间距 */
.captcha-group {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 10px;
}

/* 输入框与密码框风格统一 */
.captcha-group input[type="text"] {
    flex: 1;
    padding: 12px;
    border: none;
    border-radius: 10px;
    background: rgba(255, 255, 255, 0.9);
    color: #000;
    font-size: 14px;
    box-sizing: border-box;
    height: 42px;
}

/* 验证码图片与输入框对齐 */
.captcha-group .captcha-img {
    width: 110px;
    height: 42px;
    margin-left: 10px;
    border-radius: 10px;
    cursor: pointer;
    border: 1px solid rgba(255, 255, 255, 0.3);
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: rgba(255, 255, 255, 0.7);
    transition: all 0.3s ease;
}

/* 鼠标悬停效果：轻微放大与亮度变化 */
.captcha-group .captcha-img:hover {
    transform: scale(1.05);
    box-shadow: 0 0 12px rgba(255, 255, 255, 0.7);
}
        
    </style>
</head>
<body>

<div class="login-box">
    <h2>学校管理系统登录</h2>
    <form action="${pageContext.request.contextPath}/do-login" method="post">
        <select name="role" required>
            <option value="" disabled selected>请选择身份</option>
           
            <option value="ROLE_TEACHER">老师</option>
            <option value="ROLE_ADMIN">管理员</option>
        </select>
        <input type="text" name="username" placeholder="用户名" required/>
        <input type="password" name="password" placeholder="密码" required/>
        <div class="form-group captcha-group">
    <input type="text" name="captcha" placeholder="输入验证码" required>
    <img id="captchaImg"
         src="${pageContext.request.contextPath}/captcha?'+Math.random()"
         class="captcha-img"
         title="点击刷新验证码"
         onclick="refreshCaptcha()"/>
</div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit">登录</button>
    </form>
    <c:if test="${param.error != null}">
        <p class="error-message">用户名或密码错误</p>
    </c:if>
</div>
<script>
    function refreshCaptcha() {
        document.getElementById("captchaImg").src =
            "${pageContext.request.contextPath}/captcha?time=" + new Date().getTime();
    }
</script>

</body>
</html>
