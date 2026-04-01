package entity;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import Util.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecord {
	  private Integer id;               // 主键，自增
	    private Integer studentId;        // 外键，关联 Student.id
	    private LocalDate attendanceDate;      // 出勤日期
	    private Integer period;           // 第几节课 1~7
	    private Integer courseId;  //课程
	    private Integer courseScheduleId;
	    private String courseName;
	    private AttendanceStatus  status;            // 出勤状态: PRESENT, ABSENT, LATE, LEAVE
	    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm" )
	    private Date createTime;          // 记录创建时间，默认 CURRENT_TIMESTAMP
	    
}
