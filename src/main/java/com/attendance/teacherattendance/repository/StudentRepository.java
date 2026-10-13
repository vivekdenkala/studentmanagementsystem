
package com.attendance.teacherattendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.teacherattendance.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findBySectionIgnoreCase(String section);

    boolean existsByRollNumber(String rollNumber);
}
