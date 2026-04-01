package Util;

public enum AttendanceStatus {
 PRESENT,   // 出勤
    ABSENT,    // 缺席
    LATE,      // 迟到
    LEAVE;     // 请假

    /** 是否算作出勤 */
    public boolean isPresent() {
        return this == PRESENT || this == LATE || this == LEAVE;
    }

    /** 是否算作缺席 */
    public boolean isAbsent() {
        return this == ABSENT;
    }
}
