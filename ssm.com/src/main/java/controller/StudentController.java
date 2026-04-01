package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Util.StudentPdfExporter;
import entity.Student;
import entity.StudentAttendanceSummary;
import service.AttendanceSummaryService;
import service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;
   
    @Autowired
   private AttendanceSummaryService attendanceSummaryService;
    

    @GetMapping("/list")
    public String list(@RequestParam(value="studentNo",required=false)String studentNo,
    		           @RequestParam(value="name",required=false)String name,
    		           @RequestParam(value="page",defaultValue="1")int page,
    		           @RequestParam(value="size",defaultValue="10")int size,
    		           Model model) {
    	 // 当前用户信息
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

    // 分页计算
    int offset = (page - 1) * size;
    List<Student> students;
    int totalCount;

    if ((studentNo == null || studentNo.isEmpty()) && (name == null || name.isEmpty())) {
        students = studentService.listPaged(offset, size);
        totalCount = studentService.countAll();
    } else {
        students = studentService.searchPaged(studentNo, name, offset, size);
        totalCount = studentService.countSearch(studentNo, name);
    }

    int totalPages = (int) Math.ceil((double) totalCount / size);

    // 构建学生 + 出勤汇总 + 最近签到的数据列表
    List<Map<String, Object>> studentWithAttendance = new ArrayList<>();
    for (Student s : students) {
        Map<String, Object> row = new HashMap<>();
        row.put("student", s);

        // 出勤汇总
        StudentAttendanceSummary summary = attendanceSummaryService.getAllSummary(s.getId());
        row.put  ("summary", summary != null ? summary : new StudentAttendanceSummary());

     
        studentWithAttendance.add(row);
    }

   

    // 模型数据
    model.addAttribute("studentWithAttendance", studentWithAttendance);
   
    model.addAttribute("username", username);
    model.addAttribute("roles", roles);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("size", size);
    model.addAttribute("studentNo", studentNo);
    model.addAttribute("name", name);

    return "/student/student-list";
}

    @GetMapping("/add")
    public String toAdd(Model model) {
        model.addAttribute("student", new Student());
        
        return "/student/student-edit";
    }
    @PostMapping("/add")
    public String add(Student s) {
        studentService.add(s);
        return "redirect:/student/list";
    }

    @GetMapping("/edit")
    public String toEdit(@RequestParam Integer id, Model model) {
    	 Student s = studentService.getById(id);
         model.addAttribute("student", s);
        
        return "/student/student-edit";
    }

    @PostMapping("/save")
    public String edit(@RequestParam("avatarFile") MultipartFile avatarFile,
                       @RequestParam("fdfFile") MultipartFile fdfFile,
                       Student student) throws IOException {

        Path uploadDir = Paths.get("C:\\java\\avatars");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        if (!avatarFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);
            avatarFile.transferTo(filePath);
            student.setAvatar("/uploads/" + fileName);
        }

        if (!fdfFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + fdfFile.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);
            fdfFile.transferTo(filePath);
            student.setFdf("/uploads/" + fileName);
        }

        studentService.update(student);
        return "redirect:/student/list";
    }
    @GetMapping("/export/pdf")
    public void exportStudentPdf(HttpServletResponse response) throws com.itextpdf.io.exceptions.IOException, IOException {
    	response.setContentType("application/pdf");
    String fileName="studnets"+new java.text.SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()) + ".pdf";
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

    List<Student> students = studentService.listAll();

    StudentPdfExporter exporter = new StudentPdfExporter(students);
    exporter.export(response);
    	
    }
    
    
    
    
    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        studentService.delete(id);
        return "redirect:/student/list";
    }
}
