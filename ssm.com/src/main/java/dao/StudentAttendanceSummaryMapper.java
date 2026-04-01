package dao;

import java.util.List;

import entity.StudentAttendanceSummary;

public interface StudentAttendanceSummaryMapper {
	
	StudentAttendanceSummary selectAllSummary(Integer studentId);
	 int updateDailySummary();
	 int resetDailyAttendance();
	 List<StudentAttendanceSummary> selectUpdatedDailySummary();

     
}
