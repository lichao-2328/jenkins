package dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import Util.AttendanceStatus;
import entity.AttendanceRecord;

public interface AttendanceRecordMapper {

	List<Map<String, Object>> selectAttendanceRate();
	Map<String, Object> selectAttendanceRateByStudentId(@Param("studentId") int studentId);
	List<AttendanceRecord> selectAttendanceByDate( @Param("attendanceDate") LocalDate attendanceDate);
	List<AttendanceRecord> selectAttendanceByDates(@Param("studentId") int studentId,
            @Param("attendanceDate") LocalDate attendanceDate);
	int countStatusByStudentId(@Param("studentId") int studentId,
	                           @Param("status") AttendanceStatus status);
	AttendanceRecord selectLatestAttendanceByStudentId(@Param("studentId") int studentId);
	void insertOrUpdateSignIn(@Param("studentId") int studentId,
	                          @Param("attendanceDate") LocalDate attendanceDate,
	                          @Param("period") int period,
	                          @Param("status") AttendanceStatus status);
	int existsRecord(@Param("studentId") int studentId,
	                 @Param("attendanceDate") LocalDate attendanceDate,
	                 @Param("period") int period);
	
	 int insertAttendanceRecord(AttendanceRecord record);

	    // 批量更新
	 int batchUpdateAttendance(List<AttendanceRecord> records);

	    int deleteAttendanceRecord(@Param("id") int id);
	    AttendanceRecord selectAttendanceById(@Param("id") int id);
}
