
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>学生出勤记录</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    
       <style>
        body {
            background: linear-gradient(135deg, #f0f4f8, #ffffff);
            font-family: "Segoe UI", "PingFang SC", "Microsoft YaHei", sans-serif;
        }

        .attendance-card {
            background: #fff;
            border-radius: 16px;
            padding: 25px;
            margin-top: 40px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.08);
        }

        h4 {
            font-weight: 600;
            color: #333;
        }

        .table {
            border-radius: 12px;
            overflow: hidden;
        }

        thead th {
            background-color: #f1f3f5;
            text-align: center;
            font-weight: 600;
            vertical-align: middle;
        }

        tbody td {
            text-align: center;
            vertical-align: middle;
        }

        tbody tr:hover {
            background-color: #f8f9fa;
        }

        /* 状态 badge */
        .badge {
            font-size: 0.85rem;
            padding: 6px 10px;
            border-radius: 8px;
        }
        .badge-present { background-color: #d1e7dd; color: #0f5132; }
        .badge-late { background-color: #fff3cd; color: #664d03; }
        .badge-leave { background-color: #cfe2ff; color: #084298; }
        .badge-absent { background-color: #f8d7da; color: #842029; }

        /* 操作按钮组 */
        .btn-group .btn {
            font-size: 0.75rem;
            padding: 2px 6px;
            border-radius: 6px;
        }
 /* 出勤状态颜色 */
        .status-text {
            font-weight: bold;
            margin-left: 8px;
        }
 .status-text.status-PRESENT { color: green; }   /* 出勤 = 绿色 */
        .status-text.status-LATE { color: orange; }     /* 迟到 = 橙色 */
        .status-text.status-LEAVE { color: blue; }      /* 请假 = 蓝色 */
        .status-text.status-ABSENT { color: red; }      /* 缺勤 = 红色 */
        
    </style>
</head>
<body class="container py-4">

    <div class="attendance-card">
        <h4 class="mb-4">📅 今日出勤情况（${date}）</h4>

        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <c:forEach var="c" items="${courses}">
                            <th>
                                <div class="d-flex flex-column align-items-center">
                                    <span>${c.courseName}<br/>第${c.orderNum}节</span>
                                    <!-- ✅ 一键出勤按钮 -->
                                    <button type="button"
								        class="btn btn-sm btn-success mt-1"
								        onclick="markAllPresent(${c.id}, ${c.orderNum})">
								    一键出勤
								</button>
                                </div>
                            </th>
                        </c:forEach>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="row" items="${tableData}">
                        <tr>
                            <td>${row.student.studentNo}</td>
                            <td>${row.student.name}</td>

                            <c:forEach var="c" items="${courses}">
                                <c:set var="key"><c:out value="period_${c.orderNum}" /></c:set>
                                <c:set var="status" value="${row[key] != null ? row[key] : ''}" />

                                <td class="status-cell" 
                                    data-student="${row.student.id}" 
                                    data-period="${c.orderNum}">
                                    
                                    <!-- ✅ 状态选择 -->
                                    <div class="status-options d-flex flex-column align-items-start">
                                        <label>
                                            <input type="radio" 
                                                name="status_${row.student.id}_${c.orderNum}" 
                                                value="PRESENT" 
                                                <c:if test="${status == 'PRESENT'}">checked</c:if> />
                                            ✔ 出勤
                                        </label>
                                        <label>
                                            <input type="radio" 
                                                name="status_${row.student.id}_${c.orderNum}" 
                                                value="LATE" 
                                                <c:if test="${status == 'LATE'}">checked</c:if> />
                                            ⏰ 迟到
                                        </label>
                                        <label>
                                            <input type="radio" 
                                                name="status_${row.student.id}_${c.orderNum}" 
                                                value="LEAVE" 
                                                <c:if test="${status == 'LEAVE'}">checked</c:if> />
                                            🏠 请假
                                        </label>
                                        <label>
                                            <input type="radio" 
                                                name="status_${row.student.id}_${c.orderNum}" 
                                                value="ABSENT" 
                                                <c:if test="${status == 'ABSENT'}">checked</c:if> />
                                            ❌ 缺勤
                                        </label>
                                    </div>
                                    <!-- ✅ 新增状态显示 -->
                                    <span class="status-text">${status}</span>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <div class="d-flex justify-content-between">            
        <a href="${pageContext.request.contextPath}/student/list" class="btn btn-secondary">返回学生主页</a>
    </div>
                        
    <!-- 分页 -->
    <div class="mt-3 text-center">
        <c:if test="${totalPages > 1}">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage -   1}&size=${size}&studentNo=${studentNo}&name=${name}">上一页</a>
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

    <!-- Bootstrap & jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    const statusMap = {
            PRESENT: "✔ 出勤",
            LATE: "⏰ 迟到",
            LEAVE: "🏠 请假",
            ABSENT: "❌ 缺勤"
        };
    function updateAttendance(studentId, period, newStatus, cell) {
        
        return $.ajax({
            url: "${pageContext.request.contextPath}/attendance/signs",
            type: "POST",
            
            data: { studentId: studentId, date: "${date}", period: period, status: newStatus },
           
        })
        .then(function(res) {
            console.log("成功返回：", res, "studentId=", studentId, "period=", period);

            // 兼容字符串或 JSON 两种成功格式
            let isSuccess = false;
            if (typeof res === "string") {
                isSuccess = res.trim() === "success";
            } else if (typeof res === "object") {
                isSuccess = res.status === "success" || res.result === "success";
            }

            if (!isSuccess) {
                // 抛错进入 catch
                throw new Error("接口未返回成功：" + (typeof res === "string" ? res : JSON.stringify(res)));
            }

            

            // 同步单选按钮状态
            cell.find(`input[value='${newStatus}']`).prop("checked", true);

            // 同步文字与样式
            cell.find(".status-text")
                .text(statusMap[newStatus])
                .removeClass()
                .addClass("status-text status-" + newStatus);

            return { ok: true, studentId, period, status: newStatus };
        })
        .catch(function(err) {
            console.error("更新失败：", err, "studentId=", studentId, "period=", period);
            // 可以在 UI 标记失败（可选）
            cell.find(".status-text")
                .text("更新失败")
                .removeClass()
                .addClass("status-text status-ABSENT");
            return { ok: false, studentId, period, status: newStatus, error: err.message };
        });
    }



 // ✅ 一键将该节课所有学生标记为出勤
    async function markAllPresent(courseId, period) {
        if (!confirm("确定将该节课所有学生标记为出勤吗？")) return;

        const res = await $.ajax({
            url: `${pageContext.request.contextPath}/attendance/batchUpdate`,
            type: "POST",
            contentType: "application/json",   
            dataType: "json",                  
            data: JSON.stringify({
                courseId: courseId,
                attendanceDate: "${date}",
                period: period,
                status: "PRESENT"
            }),
            success: function(res) {
                if (res.status === "success") {
                    window.location.href = res.redirectUrl;
                }
            }
        });

        if (res.status === "success") {
            document.querySelectorAll(`.status-cell[data-period='${period}']`).forEach(cellEl => {
                const cell = $(cellEl);
                cell.find(`input[value='PRESENT']`).prop("checked", true);
                cell.find(".status-text").text("✔ 出勤").attr("class", "status-text status-PRESENT");
            });
            alert(`该节课所有学生已标记为出勤，共 ${res.count} 条记录`);
        } else {
            alert("批量更新失败：" + res.message);
        }
    }

    $(".status-text").each(function() {
        const text = $(this).text().trim();
        $(this).addClass("status-" + text);
    });

    // 监听单选按钮变化
    $(document).on("change", ".status-cell input[type=radio]", function () {
        const newStatus = $(this).val();
        const cell = $(this).closest(".status-cell");

        cell.find(".status-text")
            .text(statusMap[newStatus])
            .removeClass("status-PRESENT status-LATE status-LEAVE status-ABSENT")
            .addClass("status-text status-" + newStatus);
    });
    </script>

</body>
</html>
