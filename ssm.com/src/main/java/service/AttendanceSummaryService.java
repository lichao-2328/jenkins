package service;

import java.util.List;

import entity.StudentAttendanceSummary;

public interface AttendanceSummaryService {

	StudentAttendanceSummary getAllSummary( Integer studentId);
	 int getupdateDailySummary();
	 int getresetDailyAttendance();
	 List<StudentAttendanceSummary> getselectUpdatedDailySummary();
}
