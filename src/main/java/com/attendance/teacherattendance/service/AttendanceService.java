
package com.attendance.teacherattendance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendance.teacherattendance.entity.Attendance;
import com.attendance.teacherattendance.repository.AttendanceRepository;
import com.attendance.teacherattendance.repository.StudentRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found: " + studentId);
        }

        return attendanceRepository.findByStudentId(studentId);
    }

    @Transactional
    public Attendance markAttendance(
            Long studentId, LocalDate date, String status) {

        validateAttendance(studentId, date, status);

        if (attendanceRepository
                .findByStudentIdAndAttendanceDate(studentId, date)
                .isPresent()) {
            throw new RuntimeException(
                    "Attendance already exists for this student and date");
        }

        Attendance attendance = new Attendance(studentId, date, status);
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance updateAttendance(
            Long attendanceId, String newStatus) {

        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException(
                    "Status must be PRESENT or ABSENT");
        }

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException(
                        "Attendance record not found: " + attendanceId));

        attendance.setStatus(newStatus.toUpperCase());
        return attendanceRepository.save(attendance);
    }

    private void validateAttendance(
            Long studentId, LocalDate date, String status) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found: " + studentId);
        }

        if (date == null) {
            throw new IllegalArgumentException(
                    "Attendance date is required");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Future attendance dates are not allowed");
        }

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException(
                    "Status must be PRESENT or ABSENT");
        }
    }

    private boolean isValidStatus(String status) {
        return status != null
                && (status.equalsIgnoreCase("PRESENT")
                || status.equalsIgnoreCase("ABSENT"));
    }
}

