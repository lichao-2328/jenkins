package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseSchedule {
	
    private int id; 
    private int studentId; 
    private int courseScheduleId;
}
