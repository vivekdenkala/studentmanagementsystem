
package com.attendance.teacherattendance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.teacherattendance.entity.Student;
import com.attendance.teacherattendance.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found: " + id));
    }

    public Student addStudent(Student student) {
        if (studentRepository.existsByRollNumber(
                student.getRollNumber())) {
            throw new RuntimeException("Roll number already exists");
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = getStudentById(id);

        existingStudent.setRollNumber(updatedStudent.getRollNumber());
        existingStudent.setFullName(updatedStudent.getFullName());
        existingStudent.setSection(updatedStudent.getSection());

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}

