package service;

import java.util.List;

import entity.Student;

public interface StudentService {
    List<Student> listAll();
    Student getById(Integer id);
    void add(Student s);
    void update(Student s);
    void delete(Integer id);
    List<Student>search(String studnetNo,String name);
    List<Student>listPaged(int offset,int size);
    List<Student> searchPaged(String studentNo, String name, int offset, int size);
    int countAll();
    int countSearch(String studentNo, String name);
}
