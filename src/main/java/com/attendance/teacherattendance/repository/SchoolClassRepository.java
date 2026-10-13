
package com.attendance.teacherattendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.teacherattendance.entity.SchoolClass;

public interface SchoolClassRepository
        extends JpaRepository<SchoolClass, Long> {
}

