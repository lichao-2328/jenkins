<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <title>学生管理系统 - 学生列表</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: "Segoe UI", "PingFang SC", sans-serif;
            overflow-x: hidden;
        }
        .sidebar {
            position: fixed;
            top: 0; left: 0;
            width: 220px;
            height: 100vh;
            background-color: #2f4050;
            color: #fff;
            padding-top: 60px;
        }
        .sidebar h5 {
            text-align: center;
            font-size: 18px;
            margin-bottom: 20px;
        }
        .sidebar a {
            color: #cfd8dc;
            display: block;
            padding: 12px 20px;
            text-decoration: none;
            transition: background 0.2s;
        }
        .sidebar a:hover, .sidebar a.active {
            background-color: #1ab394;
            color: #fff;
        }
        .topbar {
            position: fixed;
            top: 0; left: 220px;
            right: 0;
            height: 60px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
            z-index: 1000;
        }
        .main {
            margin-left: 220px;
            padding: 90px 40px 40px 40px;
        }
        .card {
            border-radius: 10px;
            border: none;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            background-color: #fff;
        }
        .table thead th {
            background-color: #f8f9fc;
            color: #555;
            font-weight: 600;
            white-space: nowrap;
        }
        .table td {
            vertical-align: middle;
            white-space: nowrap;
        }
        .btn {
            border-radius: 6px;
        }
        .pagination .page-link {
            color: #007bff;
            border: none;
            background-color: #f4f6f9;
            margin: 0 2px;
        }
        .pagination .page-item.active .page-link {
            background-color: #007bff;
            color: #fff;
        }
    </style>
</head>
<body>

<!-- 左侧导航栏 -->
<div class="sidebar">
    <h5>学生管理系统</h5>
    <a href="#" class="active">学生管理</a>
    <a href="${pageContext.request.contextPath}/course/list">课程管理</a>
    <a href="${pageContext.request.contextPath }/attendance/today">出勤管理</a>
    <a href="#">系统设置</a>
</div>

<!-- 顶部栏 -->
<div class="topbar d-flex justify-content-between align-items-center px-4 py-2 bg-white text-dark shadow-sm">
    <div class="fw-bold fs-5">
        <i class="bi bi-mortarboard-fill"></i> 学生管理后台
    </div>
    <div class="d-flex align-items-center">
        <img src="${pageContext.request.contextPath}/static/images/student.jpg"
             alt="头像" class="rounded-circle me-2 border border-dark" width="36" height="36">
        <div class="me-3">
            <div class="fw-semibold">${username}</div>
            <small>
                <c:forEach var="r" items="${roles}">
                    <c:choose>
                        <c:when test="${r.authority == 'ROLE_ADMIN'}">管理员</c:when>
                        <c:when test="${r.authority == 'ROLE_TEACHER'}">教师</c:when>
                        <c:when test="${r.authority == 'ROLE_STUDENT'}">学生</c:when>
                        <c:otherwise>${r.authority}</c:otherwise>
                    </c:choose>
                </c:forEach>
            </small>
        </div>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm btn-outline-dark">退出</a>
    </div>
</div>

<!-- 主体内容 -->
<div class="main">
    <div class="card p-4">
        <h5 class="mb-3">学生管理</h5>

        <!-- 搜索表单 -->
        <form class="row mb-3" method="get" action="${pageContext.request.contextPath}/student/list">
            <div class="col-md-3">
                <input type="text" name="studentNo" class="form-control" placeholder="学号" value="${studentNo}">
            </div>
            <div class="col-md-3">
                <input type="text" name="name" class="form-control" placeholder="姓名" value="${name}">
            </div>
            <div class="col-md-6">
                <button type="submit" class="btn btn-primary">查询</button>
                <a href="${pageContext.request.contextPath}/student/list" class="btn btn-secondary">重置</a>
                <a href="${pageContext.request.contextPath}/student/export/pdf" class="btn btn-danger">一键导出PDF</a>
                <a href="${pageContext.request.contextPath}/student/add" class="btn btn-success">新增学生</a>
                <a href="${pageContext.request.contextPath}/summary/update" class="btn btn-success" >同步出勤</a>
            </div>
        </form>

        <!-- 学生表格 -->
        <div class="table-responsive">
            <table class="table table-striped align-middle text-center">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>年龄</th>
                        <th>班级</th>
                        <th>入学时间</th>
                        <th>总课时</th>
                        <th>已出勤课时</th>
                        <th>总出勤率</th>
                        <th>最后更新时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${studentWithAttendance}">
                        <c:set var="s" value="${row.student}"/>
                        <c:set var="summary" value="${row.summary}"/>
                        <c:set var="latest" value="${row.latestAttendance}"/>

                        <tr>
                            <td>${s.id}</td>
                            <td>${s.studentNo}</td>
                            <td>${s.name}</td>
                            <td>${s.age}</td>
                            <td>${s.classId}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty s.enroll_date}">
                                        <fmt:formatDate value="${s.enroll_date}" pattern="yyyy-MM-dd"/>
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>

                            <!-- 出勤数据 -->
                           
					    <td><c:out value="${summary.totalPeriods}" default="0"/></td>
					    <td><c:out value="${summary.attended}" default="0"/></td>
					    <td><fmt:formatNumber value="${summary.attendanceRate != null ? summary.attendanceRate : 0}" pattern="0.00"/>%</td>
					    <td>
					        <c:choose>
					            <c:when test="${not empty summary.lastUpdate}">
					                <fmt:formatDate value="${summary.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					            </c:when>
					            <c:otherwise>-</c:otherwise>
					        </c:choose>
					    </td>
                            <!-- 操作栏 -->
                            <td>
                                
                                <a href="${pageContext.request.contextPath}/student/edit?id=${s.id}" class="btn btn-sm btn-primary me-1">编辑</a>
                                <a href="${pageContext.request.contextPath}/student/delete?id=${s.id}"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('确定要删除学生【${s.name}】吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty studentWithAttendance}">
                        <tr>
                            <td colspan="11" class="text-center text-muted py-4">暂无学生数据</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- 分页 -->
        <div class="mt-3 text-center">
            <c:if test="${totalPages > 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}&size=${size}&studentNo=${studentNo}&name=${name}">上一页</a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&size=${size}&studentNo=${studentNo}&name=${name}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}&size=${size}&studentNo=${studentNo}&name=${name}">下一页</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>

       
        <div class="text-end text-muted">
            共 ${totalCount} 条记录，第 ${pageNum} / ${totalPages} 页
        </div>
    </div>
</div>




</body>
</html>
