package entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	private Integer id;
    private String studentNo;   // 学号
    private String name;
    private String gender;
    private Integer age;
    private String classId;
    private String phone;
    private String address;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date enroll_date;    // 入学时间
    private String avatar; // 新增：头像路径
    private String fdf; 
}
