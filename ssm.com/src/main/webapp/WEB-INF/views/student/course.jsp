<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>课程列表</title>

    <!-- ✅ Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ✅ 自定义样式 -->
    <style>
       body {
    background: linear-gradient(135deg, #f8f9fa, #eef1f6);
    font-family: "Segoe UI", "PingFang SC", "Microsoft YaHei", sans-serif;
}

.page-title {
    font-weight: 700;
    color: #0d6efd;
    border-left: 6px solid #0d6efd;
    padding-left: 10px;
}

.card {
    border-radius: 15px;
    box-shadow: 0 6px 20px rgba(0,0,0,0.08);
    border: none;
}

.table {
    border-radius: 10px;
    overflow: hidden;
}

.table thead th {
    background: linear-gradient(90deg, #0d6efd, #0dcaf0);
    color: #fff;
    text-align: center;
    font-weight: 600;
}

.table tbody tr:nth-child(odd) {
    background-color: #fdfdfd;
}

.table tbody tr:hover {
    background-color: #f1f7ff;
    transform: scale(1.01);
    transition: 0.2s;
}

.btn-add {
    border-radius: 50px;
    font-weight: 600;
    padding: 8px 18px;
    background: linear-gradient(90deg, #0d6efd, #0dcaf0);
    border: none;
    box-shadow: 0 3px 6px rgba(0,0,0,0.1);
}

.btn-edit, .btn-delete {
    font-size: 0.9rem;
    padding: 4px 8px;
    border-radius: 6px;
    transition: 0.2s;
}

.btn-edit {
    background-color: #e7f1ff;
    color: #0d6efd;
}

.btn-edit:hover {
    background-color: #cfe2ff;
}

.btn-delete {
    background-color: #fde2e2;
    color: #dc3545;
}

.btn-delete:hover {
    background-color: #f8d7da;
}

    </style>
</head>

<body class="container py-4">

    <div class="card p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="page-title mb-0">📘 课程列表</h4>
            <a href="${pageContext.request.contextPath}/course/add" class="btn btn-primary btn-add">
                ➕ 新增课程
            </a>
        </div>

        <div class="table-container">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>课程名</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>${course.id}</td>
                            <td>${course.courseName}</td>
                            <td>${course.startTime}</td>
                            <td>${course.endTime}</td>
                            <td>${course.orderNum}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/course/edit/${course.id}" class="btn-edit">✏ 编辑</a>
                                <span class="text-muted">|</span>
                                <a href="${pageContext.request.contextPath}/course/delete/${course.id}" class="btn-delete"
                                   onclick="return confirm('确定要删除该课程吗？');">🗑 删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
 <div class="d-flex justify-content-between">		    
						    <a href="${pageContext.request.contextPath}/student/list" class="btn btn-secondary">返回学生主页</a>
						  
						</div>
    <!-- ✅ Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
