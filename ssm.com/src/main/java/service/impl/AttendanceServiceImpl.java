package service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Util.AttendanceStatus;
import dao.AttendanceRecordMapper;
import entity.AttendanceRecord;
import service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public void insertOrUpdateSignIn(int studentId, LocalDate attendanceDate, int period, AttendanceStatus status) {
    	 if (attendanceRecordMapper.existsRecord(studentId, attendanceDate, period) == 0) {
    	        attendanceRecordMapper.insertOrUpdateSignIn(studentId, attendanceDate, period, status);
    	    } 
    }

    @Override
    public List<Map<String, Object>> getAllAttendanceRates() {
        return attendanceRecordMapper.selectAttendanceRate();
    }

    @Override
    public Map<String, Object> getAttendanceRateByStudentId(int studentId) {
        return attendanceRecordMapper.selectAttendanceRateByStudentId(studentId);
    }

    @Override
    public List<AttendanceRecord>  getAttendanceByDate( LocalDate attendanceDate) {
        return attendanceRecordMapper.selectAttendanceByDate( attendanceDate);
    }

    @Override
    public int countStatusByStudentId(int studentId, AttendanceStatus status) {
     
        return attendanceRecordMapper.countStatusByStudentId(studentId, status);
    }

    @Override
    public AttendanceRecord getLatestAttendanceByStudentId(int studentId) {
        return attendanceRecordMapper.selectLatestAttendanceByStudentId(studentId);
    }

	@Override
	public int existsRecord(int studentId, LocalDate attendanceDate, int period) {
		
		return attendanceRecordMapper.existsRecord( studentId, attendanceDate,  period);
	}

	@Override
	public int deleteAttendanceRecord(int id) {
		
		return attendanceRecordMapper.deleteAttendanceRecord(id);
	}

	@Override
	public int insertAttendanceRecord(AttendanceRecord record) {
				return attendanceRecordMapper.insertAttendanceRecord(record);
	}

	

	@Override
	public AttendanceRecord selectAttendanceById(int id) {
				return attendanceRecordMapper.selectAttendanceById(id);
	}
	
    // 批量更新（有则更新，无则插入）

    @Transactional
    public int batchUpsert(List<AttendanceRecord> records) {
        return attendanceRecordMapper.batchUpdateAttendance(records);
    }
}
