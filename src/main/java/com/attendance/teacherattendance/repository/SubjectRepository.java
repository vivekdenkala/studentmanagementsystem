
package com.attendance.teacherattendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.teacherattendance.entity.Subject;

public interface SubjectRepository
        extends JpaRepository<Subject, Long> {

    boolean existsBySubjectCode(String subjectCode);
}
