package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CourseScheduleMapper;
import dao.StudentMapper;
import entity.CourseSchedule;
import entity.Student;
import service.CourseScheduleService;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {
    @Autowired
	    private StudentMapper studentMapper;
    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    @Override
    public List<CourseSchedule> getAllCourses() {
        return courseScheduleMapper.selectAll();
    }

    @Override
    public CourseSchedule getCourseById(Integer id) {
        return courseScheduleMapper.selectById(id);
    }

    @Override
    public void addCourse(CourseSchedule course) {
        courseScheduleMapper.insert(course);
    }

    @Override
    public void updateCourse(CourseSchedule course) {
        courseScheduleMapper.update(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseScheduleMapper.deleteById(id);
    }


    @Override
    public List<Student> getStudentsByCourseId(Integer courseScheduleId) {
        return studentMapper.selectByCourseId(courseScheduleId);
    }
    
}
