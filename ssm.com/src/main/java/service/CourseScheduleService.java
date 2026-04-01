package service;

import java.util.List;

import entity.CourseSchedule;
import entity.Student;

public interface CourseScheduleService {
    List<CourseSchedule> getAllCourses();
    CourseSchedule getCourseById(Integer id);
    void addCourse(CourseSchedule course);
    void updateCourse(CourseSchedule course);
    void deleteCourse(Integer id);
    List<Student> getStudentsByCourseId(Integer courseScheduleId);
   
}