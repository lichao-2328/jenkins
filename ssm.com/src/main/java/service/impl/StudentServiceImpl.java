package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.StudentMapper;
import entity.Student;
import service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    public List<Student> listAll() { return studentMapper.selectAll(); }
    public Student getById(Integer id) { return studentMapper.selectById(id); }
    public void add(Student s) { studentMapper.insert(s); }
    public void update(Student s) { studentMapper.update(s); }
    public void delete(Integer id) { studentMapper.deleteById(id); }
	@Override
	public List<Student> search(String studnetNo, String name) {
		return studentMapper.search(studnetNo, name);
	}
	@Override
	public List<Student> searchPaged(String studentNo, String name, int offset, int size) {
		return studentMapper.searchPaged(studentNo,name,offset,size);}
	@Override
	public int countAll() {
		return studentMapper.countAll();
	}
	@Override
	public int countSearch(String studentNo, String name) {
		
		return studentMapper.countSearch(studentNo, name);
	}
	@Override
	public List<Student> listPaged(int offset, int size) {
		return studentMapper.listPaged(offset, size);
	}
}