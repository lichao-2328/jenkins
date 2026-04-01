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
            background-color: #f4f6f9;
            font-family: "Segoe UI", "PingFang SC", "Microsoft YaHei", sans-serif;
        }

        .page-title {
            font-weight: 600;
            color: #333;
        }

        .card {
            border-radius: 15px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.05);
        }

        table {
            border-radius: 10px;
            overflow: hidden;
        }

        thead th {
            background-color: #e9ecef !important;
            font-weight: 600;
            text-align: center;
        }

        tbody td {
            text-align: center;
            vertical-align: middle;
        }

        tbody tr:hover td {
            background-color: #f8f9fa;
        }

        .btn-add {
            border-radius: 50px;
            font-weight: 500;
            padding: 6px 16px;
        }

        .btn-edit {
            color: #0d6efd;
            text-decoration: none;
            font-weight: 500;
        }

        .btn-edit:hover {
            text-decoration: underline;
        }

        .btn-delete {
            color: #dc3545;
            text-decoration: none;
            font-weight: 500;
        }

        .btn-delete:hover {
            text-decoration: underline;
        }

        .table-container {
            overflow-x: auto;
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
