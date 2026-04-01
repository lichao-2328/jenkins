package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.AttendanceSummaryService;

@Controller
@RequestMapping("/summary")
public class StudentAttendanceSummary {
	 @Autowired
	    private AttendanceSummaryService summaryService;

	   

	    // 手动触发更新
	    @GetMapping("/update")
	    public String updateSummary() {
	        summaryService.getupdateDailySummary();
	        return "redirect:/student/list";
	    }
}
