package entity;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSchedule {
	    private Integer id;
	    private String courseName;
	    private String courseNumber;
	     @DateTimeFormat(pattern = "HH:mm")
	    private LocalTime startTime;
	      @DateTimeFormat(pattern = "HH:mm")
	    private LocalTime endTime;
	    private Integer orderNum;
	    private Integer termId;
	    
}
