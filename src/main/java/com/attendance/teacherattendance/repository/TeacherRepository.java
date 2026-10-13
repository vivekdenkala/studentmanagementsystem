
package com.attendance.teacherattendance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.teacherattendance.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUsername(String username);

    boolean existsByUsername(String username);
}
