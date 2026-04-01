package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.CourseSchedule;
import service.CourseScheduleService;

@Controller
@RequestMapping("/course")
public class CourseScheduleController {

    @Autowired
    private CourseScheduleService courseScheduleService;

    @GetMapping("/list")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseScheduleService.getAllCourses());
        
        return "student/course"; // JSP 页面路径
        
    }

    @GetMapping("/add")
    public String addForm(Model model) {
    	 model.addAttribute("course", new CourseSchedule());
        return "student/add-course";
    }
 
    @PostMapping("/add")
	public String addCourse(CourseSchedule course) {
	    courseScheduleService.addCourse(course);
	    return "redirect:/course/list"; 
}
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("course", courseScheduleService.getCourseById(id));
        return "student/add-course";
    }

    @PostMapping("/edit")
    public String editCourse(CourseSchedule course) {
        courseScheduleService.updateCourse(course);
        return "redirect:/course/list"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseScheduleService.deleteCourse(id);
        return "redirect:/course/list"; 
    }
}
