
package com.attendance.teacherattendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.teacherattendance.entity.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByAttendanceDateAndStudentId(
            LocalDate attendanceDate, Long studentId);

    Optional<Attendance> findByStudentIdAndAttendanceDate(
            Long studentId, LocalDate attendanceDate);
}
