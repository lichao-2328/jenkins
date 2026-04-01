package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StudentAttendanceSummaryMapper;
import entity.StudentAttendanceSummary;
import service.AttendanceSummaryService;

@Service
@Transactional
public class AttendanceSummaryServiceImpl implements AttendanceSummaryService {

    @Autowired
    private StudentAttendanceSummaryMapper attendanceSummaryMapper;

	@Override
	public StudentAttendanceSummary getAllSummary(Integer studentId) {
		
		return attendanceSummaryMapper.selectAllSummary(studentId);
	}
	@Override
	public int getupdateDailySummary() {
				return attendanceSummaryMapper.updateDailySummary();
	}

	@Override
	public int getresetDailyAttendance() {
				return attendanceSummaryMapper.resetDailyAttendance();
	}

	@Override
	public List<StudentAttendanceSummary> getselectUpdatedDailySummary() {
		
		return attendanceSummaryMapper.selectUpdatedDailySummary();
	}

   
    
    
}
