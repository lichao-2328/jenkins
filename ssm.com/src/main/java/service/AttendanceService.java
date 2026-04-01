package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import Util.AttendanceStatus;
import entity.AttendanceRecord;



public interface AttendanceService{
	 /**
     * 学生签到（插入出勤记录）
     * @param studentId 学生ID
     * @param attendanceDate 出勤日期
     * @param period 节次
     * @param status 出勤状态，如 "present"、"absent"
     */
    void insertOrUpdateSignIn(int studentId, LocalDate attendanceDate, int period, AttendanceStatus status);

    /**
     * 查询所有学生的出勤率信息
     * @return 出勤率列表（每个元素是包含学生姓名、总课时、已出勤数、出勤率等字段的 Map）
     */
    List<Map<String, Object>> getAllAttendanceRates();

    /**
     * 查询指定学生的出勤率信息
     * @param studentId 学生ID
     * @return 出勤率数据（包含总课时、已出勤、出勤率等）
     */
    Map<String, Object> getAttendanceRateByStudentId(int studentId);

    /**
     * 查询某学生在某天的出勤详情（可用于判断当天是否已签到）
     * @param studentId 学生ID
     * @param attendanceDate 出勤日期
     * @return 出勤记录列表（每条包含 period、status 等）
     */
    List<AttendanceRecord>  getAttendanceByDate( LocalDate attendanceDate);

    /**
     * 统计某学生出勤次数
     * @param studentId 学生ID
     * @param status 出勤状态，如 "present"
     * @return 出勤次数
     */
    int countStatusByStudentId(int studentId, AttendanceStatus status);

    /**
     * 查询某学生最近一条出勤记录
     * @param studentId 学生ID
     * @return 最近出勤记录（包含日期、节次、状态）
     */
    AttendanceRecord getLatestAttendanceByStudentId(int studentId);
    int existsRecord( int studentId, LocalDate attendanceDate,int period);
    int insertAttendanceRecord(AttendanceRecord record);
   
    int deleteAttendanceRecord(int id);
    AttendanceRecord selectAttendanceById( int id);
   

    // 批量更新（有则更新，无则插入）
    int batchUpsert(List<AttendanceRecord> records);

	
}