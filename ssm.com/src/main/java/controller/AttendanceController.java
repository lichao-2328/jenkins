package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Util.AttendanceStatus;
import dao.AttendanceRecordMapper;
import entity.AttendanceRecord;
import entity.CourseSchedule;
import entity.Student;
import service.AttendanceService;
import service.CourseScheduleService;
import service.StudentService;
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRecordMapper attendanceMapper;
    @Autowired
    private AttendanceService attendanceService;
   @Autowired
    private StudentService studentService;
   @Autowired
   private CourseScheduleService courseService;
  
   @GetMapping("/today")
   public String listTodayAttendance(
           @RequestParam(value = "page", defaultValue = "1") int page,
           @RequestParam(value = "size", defaultValue = "10") int size,
           @RequestParam(value = "studentNo", required = false) String studentNo,
           @RequestParam(value = "name", required = false) String name,
           Model model) {

       // 1️⃣ 分页计算
       int offset = (page - 1) * size;
       List<Student> students;
       int totalCount;

       // 2️⃣ 根据是否有搜索条件获取学生列表
       if ((studentNo == null || studentNo.isEmpty()) && (name == null || name.isEmpty())) {
           students = studentService.listPaged(offset, size);
           totalCount = studentService.countAll();
       } else {
           students = studentService.searchPaged(studentNo, name, offset, size);
           totalCount = studentService.countSearch(studentNo, name);
       }

       int totalPages = (int) Math.ceil((double) totalCount / size);

       // 3️⃣ 获取当天日期
       LocalDate today = LocalDate.now();

       // 4️⃣ 获取当天课程表
       List<CourseSchedule> courses = courseService.getAllCourses(); // 如果有按日期方法可替换
       courses.sort(Comparator.comparingInt(CourseSchedule::getOrderNum));

       // 5️⃣ 获取当天所有出勤记录
       List<AttendanceRecord> records = attendanceMapper.selectAttendanceByDate(today);

       // 6️⃣ 构建快速查找 map (studentId_period -> status)
       Map<String, AttendanceStatus> attendanceMap = new HashMap<>();
       for (AttendanceRecord r : records) {
           attendanceMap.put(r.getStudentId() + "_" + r.getPeriod(), r.getStatus());
       }

       // 7️⃣ 构建前端 table 数据
       List<Map<String, Object>> tableData = new ArrayList<>();
       for (Student s : students) {
           Map<String, Object> row = new LinkedHashMap<>();
           row.put("student", s);

           for (CourseSchedule c : courses) {
               String key = s.getId() + "_" + c.getOrderNum();
               AttendanceStatus status = attendanceMap.getOrDefault(key, AttendanceStatus.ABSENT);
               row.put("period_" + c.getOrderNum(), status);
           }

           tableData.add(row);
       }

       // 8️⃣ 传递数据给前端
       model.addAttribute("date", today);
       model.addAttribute("courses", courses);
       model.addAttribute("tableData", tableData);
       model.addAttribute("currentPage", page);
       model.addAttribute("totalPages", totalPages);
       model.addAttribute("size", size);
       model.addAttribute("studentNo", studentNo);
       model.addAttribute("name", name);

       return "/student/attendanceRecord";
   }


    @PostMapping("/markAbsent")
    @ResponseBody
    public String markAbsent(@RequestParam("studentId") int studentId,
                             @RequestParam("period") int period) {
        try {
            LocalDate today = LocalDate.now();
            attendanceService.insertOrUpdateSignIn(studentId, today, period, AttendanceStatus.ABSENT);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    /** 📋 所有出勤率 */
    @GetMapping("/rate")
    public String showAllAttendanceRate(Model model) {
        List<Map<String, Object>> rates = attendanceService.getAllAttendanceRates();
        model.addAttribute("rates", rates);
        return "attendance/attendance-rate";
    }

    /** 📄 单个出勤率 */
    @GetMapping("/rate/{studentId}")
    public String showAttendanceRateByStudent(@PathVariable int studentId, Model model) {
        Map<String, Object> rate = attendanceService.getAttendanceRateByStudentId(studentId);
        model.addAttribute("rate", rate);
        return "attendance/attendance-detail";
    }

    /** 🗓️ 查询学生当天记录 */
    @GetMapping("/records")
    public String showAttendanceByDate(@RequestParam int studentId,
                                       @RequestParam String date,
                                       Model model) {
        LocalDate attendanceDate = LocalDate.parse(date);
        List<AttendanceRecord> records = attendanceService.getAttendanceByDate( attendanceDate);
        model.addAttribute("records", records);
        return "attendance/attendance-records";
    }

    /** ✅ 新增出勤记录 */
    @PostMapping("/add")
    @ResponseBody
    public String addAttendance(@RequestBody AttendanceRecord record) {
        attendanceService.insertAttendanceRecord(record);
        return "success";
    }

 
@PostMapping("/batchUpdate")
@ResponseBody
public Map<String,Object> batchUpdate(@RequestBody Map<String,Object> request,HttpServletRequest httpRequest) {
	 Integer courseId = Integer.valueOf(request.get("courseId").toString());
     String dateStr = request.get("attendanceDate").toString();
     Integer period = Integer.valueOf(request.get("period").toString());
     String statusStr = request.get("status").toString();

     LocalDate attendanceDate = LocalDate.parse(dateStr);
     AttendanceStatus status = AttendanceStatus.valueOf(statusStr);

     List<Student> students = courseService.getStudentsByCourseId(courseId);

     List<AttendanceRecord> records = students.stream().map(s -> {
         AttendanceRecord r = new AttendanceRecord();
         r.setStudentId(s.getId());
         r.setCourseScheduleId(courseId);
         r.setAttendanceDate(attendanceDate);
         r.setPeriod(period);
         r.setStatus(status);
         return r;
     }).collect(Collectors.toList());

     int count = attendanceService.batchUpsert(records);

     String redirectUrl = httpRequest.getContextPath() + "/attendance/batchUpdate-list?date=" + attendanceDate;
    return Map.of("status","success","count",count,"redirectUrl",redirectUrl);
 }

       
    

    /** ❌ 删除出勤记录 */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteAttendance(@PathVariable int id) {
        attendanceService.deleteAttendanceRecord(id);
        return "success";
    }

    /** 🔍 查询单条出勤记录 */
    @GetMapping("/detail/{id}")
    @ResponseBody
    public AttendanceRecord getAttendanceById(@PathVariable int id) {
        return attendanceService.selectAttendanceById(id);
    }

    /** 🕒 最近出勤记录 */
    @GetMapping("/latest")
    @ResponseBody
    public AttendanceRecord getLatestAttendance(@RequestParam int studentId) {
        return attendanceService.getLatestAttendanceByStudentId(studentId);
    }
    @PostMapping("/signs")
    @ResponseBody
    public String signIn(@RequestParam int studentId,
                         @RequestParam String date,
                         @RequestParam int period,
                         @RequestParam AttendanceStatus status) {
        LocalDate attendanceDate = LocalDate.parse(date);
        attendanceService.insertOrUpdateSignIn(studentId, attendanceDate, period, status);
        return "success";
    }
    
    @GetMapping("/batchUpdate-list")
    public String studentList(@RequestParam(required = false) String date,Model model) {
    	 LocalDate queryDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();

         // 查课程
         List<CourseSchedule> courses = courseService.getAllCourses();
         courses.sort(Comparator.comparingInt(CourseSchedule::getOrderNum));

         // 查学生
         List<Student> students = studentService.listAll();

         // 查考勤记录
         List<AttendanceRecord> dbRecords = attendanceMapper.selectAttendanceByDate(queryDate);

         // 组装数据
         Map<String, AttendanceStatus> attendanceMap = new HashMap<>();
         for (AttendanceRecord r : dbRecords) {
             attendanceMap.put(r.getStudentId() + "_" + r.getPeriod(), r.getStatus());
         }

         List<Map<String, Object>> tableData = new ArrayList<>();
         for (Student s : students) {
             Map<String, Object> row = new LinkedHashMap<>();
             row.put("student", s);
             for (CourseSchedule c : courses) {
                 String key = s.getId() + "_" + c.getOrderNum();
                 AttendanceStatus status = attendanceMap.getOrDefault(key, AttendanceStatus.ABSENT);
                 row.put("period_" + c.getOrderNum(), status);
             }
             tableData.add(row);
         }

         model.addAttribute("date", queryDate);
         model.addAttribute("courses", courses);
         model.addAttribute("tableData", tableData);

         return "student/attendanceRecord";}

    }
