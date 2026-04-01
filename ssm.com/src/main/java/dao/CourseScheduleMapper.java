package dao;

import java.util.List;

import entity.CourseSchedule;

public interface CourseScheduleMapper {
    List<CourseSchedule> selectAll();
    CourseSchedule selectById(Integer id);
    int insert(CourseSchedule course);
    int update(CourseSchedule course);
    int deleteById(Integer id);
    
}