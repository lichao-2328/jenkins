<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <title>学生管理系统 - 编辑学生</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; font-family: "Segoe UI", "PingFang SC", sans-serif; }
        .sidebar { position: fixed; top: 0; left: 0; width: 220px; height: 100vh; background-color: #2f4050; color: #fff; padding-top: 60px; }
        .sidebar a { color: #cfd8dc; display: block; padding: 12px 20px; text-decoration: none; transition: background 0.2s; }
        .sidebar a:hover, .sidebar a.active { background-color: #1ab394; color: #fff; }
        .topbar { position: fixed; top: 0; left: 220px; right: 0; height: 60px; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); display: flex; align-items: center; justify-content: space-between; padding: 0 30px; z-index: 1000; }
        .main { margin-left: 220px; padding: 90px 40px 40px 40px; }
        .card { border-radius: 10px; border: none; box-shadow: 0 2px 8px rgba(0,0,0,0.05); background-color: #fff; max-width: 800px; margin: 0 auto; }
        .form-control { border-radius: 8px; }
        .btn { border-radius: 8px; }
        .avatar-preview { width: 150px; height: 150px; object-fit: cover; border-radius: 10px; border: 1px solid #ddd; }
        .avatar-container { text-align: center; }
       
    </style>
</head>
<body>

<!-- 左侧导航 -->
<div class="sidebar">
    <h5 class="text-center">学生管理系统</h5>
    <a href="${pageContext.request.contextPath}/student/list" class="active">学生管理</a>
    <a href="#">班级管理</a>
    <a href="#">教师管理</a>
    <a href="#">系统设置</a>
</div>

<!-- 顶部栏 -->
<div class="topbar">
    <div class="fw-bold">编辑学生</div>
    <div>管理员：admin</div>
</div>

<!-- 主体内容 -->
<div class="main">
    <div class="card p-4">
        <h5 class="mb-4 text-center">
            ${student.id == null ? '新增学生' : '编辑学生'}
        </h5>

        <form:form action="${pageContext.request.contextPath}/student/${student.id == null ? 'add' : 'save'}"
                   method="post" modelAttribute="student" enctype="multipart/form-data">

            <c:if test="${not empty student}">
                <input type="hidden" name="id" value="${student.id}"/>
            </c:if>
<div class="d-flex align-items-stretch justify-content-between mb-4" style="gap: 1.5rem;">
    
    <!-- 左侧：三个输入框 -->
    <div class="flex-grow-1 d-flex flex-column justify-content-between">
        <div class="mb-3">
            <label class="form-label">学号</label>
            <input type="text" name="studentNo" value="${student.studentNo}" class="form-control" required/>
        </div>

        <div class="mb-3">
            <label class="form-label">姓名</label>
            <input type="text" name="name" value="${student.name}" class="form-control" required/>
        </div>

        <div class="mb-3">
            <label class="form-label">上传头像</label>
            <input type="file" name="avatarFile" class="form-control" accept="image/*"/>
        </div>
    </div>

    <!-- 右侧：头像预览 -->
    <div class="avatar-box d-flex align-items-center justify-content-center"
         style="width: 200px; height: 235px; border: 1px solid #ccc; border-radius: 8px; background-color: #fafafa;">
        <c:if test="${not empty student.avatar}">
            <img src="${pageContext.request.contextPath}${student.avatar}" 
                 alt="头像"
                 style="max-width: 100%; max-height: 100%; object-fit: cover; border-radius: 8px;"/>
        </c:if>
        <c:if test="${empty student.avatar}">
            <span class="text-muted">暂无头像</span>
        </c:if>
    </div>
</div>

            <div class="mb-3">
                <label class="form-label">性别</label>
                <select name="gender" class="form-control" required>
                    <option value="">请选择</option>
                    <option value="男" <c:if test="${student.gender == '男'}">selected</c:if>>男</option>
                    <option value="女" <c:if test="${student.gender == '女'}">selected</c:if>>女</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">年龄</label>
                <input type="number" name="age" value="${student.age}" class="form-control" required/>
            </div>

            <div class="mb-3">
                <label class="form-label">班级编号</label>
                <input type="text" name="classId" value="${student.classId}" class="form-control" placeholder="输入班级编号"/>
            </div>

            <div class="mb-3">
                <label class="form-label">联系电话</label>
                <input type="text" name="phone" value="${student.phone}" class="form-control" placeholder="例如：090-1234-5678"/>
            </div>

            <div class="mb-3">
                <label class="form-label">家庭住址</label>
                <input type="text" name="address" value="${student.address}" class="form-control" placeholder="输入详细地址"/>
            </div>

            <div class="mb-3">
                <label class="form-label">入学日期</label>
                <input type="datetime-local" name="enroll_date"
                       value="<fmt:formatDate value='${student.enroll_date}' pattern='yyyy-MM-dd\'T\'HH:mm'/>"
                       class="form-control"/>
            </div>

            <div class="mb-3">
                <label class="form-label">学生资料文件</label>
                <input type="file" name="fdfFile" class="form-control" accept=".pdf,.doc,.docx,.zip"/>
                <c:if test="${not empty student.fdf}">
                    <a href="${pageContext.request.contextPath}${student.fdf}" target="_blank" class="mt-2 d-block">📄 查看已上传文件</a>
                </c:if>
            </div>

            <div class="d-flex justify-content-between mt-4">
                <a href="${pageContext.request.contextPath}/student/list" class="btn btn-outline-secondary">返回列表</a>
                <button type="submit" class="btn btn-primary px-4">${student.id == null ? '保存' : '更新'}</button>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
