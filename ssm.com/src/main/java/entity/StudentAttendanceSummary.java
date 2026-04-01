package entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceSummary {
	    private Integer id;                // 主键，自增
	    private Integer studentId;         // 外键，关联 Student.id
	    private Integer totalPeriods;      // 总科目
	    private Integer attended;   // 已出天数
	    private BigDecimal attendanceRate; // 出勤率，保留两位小数
	    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	    private Date lastUpdate;           // 最后更新时间，自动更新
}
