package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Student;

public interface StudentMapper {
    Student selectById(Integer id);
    List<Student> selectAll();
    int insert(Student student);
    int update(Student student);
    int deleteById(Integer id);
    List<Student>search(@Param("studentNo")String studnetNo,@Param("name")String name);
    List<Student>listPaged(@Param("offset")int offset,@Param("size")int size);
    List<Student> searchPaged(@Param("studentNo")String studentNo,@Param("name") String name, @Param("offset")int offset, @Param("size")int size);
    int countAll();
    int countSearch(@Param("studentNo")String studentNo, @Param("name")String name);
    List<Student> selectByCourseId(@Param("courseScheduleId") Integer courseScheduleId);
}