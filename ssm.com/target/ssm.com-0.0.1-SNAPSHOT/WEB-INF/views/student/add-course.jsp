<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${course.id != null ? '✏ 编辑课程' : '➕ 新增课程'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #e0f2ff, #f9f9f9);
            font-family: "Segoe UI", "PingFang SC", "Microsoft YaHei", sans-serif;
        }

        .card {
            border-radius: 16px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            max-width: 650px;
            margin: 60px auto;
            border: none;
        }

        .card-header {
            background: linear-gradient(90deg, #0d6efd, #4dabf7);
            color: white;
            border-radius: 16px 16px 0 0;
            padding: 18px 24px;
        }

        .card-header h5 {
            margin: 0;
            font-weight: 600;
        }

        .form-label {
            font-weight: 500;
            color: #444;
        }

        .form-control {
            border-radius: 10px;
            padding: 10px 14px;
        }

        .btn {
            border-radius: 10px;
            padding: 8px 18px;
            font-weight: 500;
        }

        .btn-primary {
            background: linear-gradient(90deg, #0d6efd, #4dabf7);
            border: none;
        }

        .btn-primary:hover {
            background: linear-gradient(90deg, #0b5ed7, #339af0);
        }

        .btn-secondary {
            background: #dee2e6;
            border: none;
            color: #333;
        }

        .btn-secondary:hover {
            background: #ced4da;
        }
    </style>
</head>

<body>
    <div class="card">
        <div class="card-header">
            <h5>${course.id != null ? '✏ 编辑课程信息' : '➕ 新增课程'}</h5>
        </div>

        <div class="card-body p-4">
            <form method="post" action="${pageContext.request.contextPath}/course/${course.id != null ? 'edit' : 'add'}">
                <input type="hidden" name="id" value="${course.id}" />

                <div class="mb-3">
                    <label for="courseName" class="form-label">课程名</label>
                    <input type="text" id="courseName" name="courseName" 
                           value="${course.courseName}" 
                           class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="courseNumber" class="form-label">课程总数</label>
                    <input type="number" id="courseNumber" name="courseNumber" 
                           value="${course.courseNumber}" 
                           class="form-control" min="1" required>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="startTime" class="form-label">开始时间</label>
                        <input type="time" id="startTime" name="startTime" 
                               value="${course.startTime}" 
                               class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="endTime" class="form-label">结束时间</label>
                        <input type="time" id="endTime" name="endTime" 
                               value="${course.endTime}" 
                               class="form-control" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="orderNum" class="form-label">课程顺序</label>
                        <input type="number" id="orderNum" name="orderNum" 
                               value="${course.orderNum}" 
                               class="form-control" min="1" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="termId" class="form-label">第几学期</label>
                        <input type="number" id="termId" name="termId" 
                               value="${course.termId}" 
                               class="form-control" min="1" required>
                    </div>
                </div>

                <div class="d-flex justify-content-between mt-4">
                    <a href="${pageContext.request.contextPath}/course/list" class="btn btn-secondary">⬅ 返回</a>
                    <button type="submit" class="btn btn-primary">💾 提交保存</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
