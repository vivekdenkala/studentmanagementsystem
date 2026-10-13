
package com.attendance.teacherattendance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.teacherattendance.entity.Attendance;
import com.attendance.teacherattendance.service.AttendanceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<Attendance> getAttendanceByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return attendanceService.getAttendanceByDate(date);
    }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getAttendanceByStudent(
            @PathVariable Long studentId) {

        return attendanceService.getAttendanceByStudent(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Attendance markAttendance(
            @Valid @RequestBody AttendanceRequest request) {

        return attendanceService.markAttendance(
                request.getStudentId(),
                request.getDate(),
                request.getStatus());
    }

    @PutMapping("/{attendanceId}")
    public Attendance updateAttendance(
            @PathVariable Long attendanceId,
            @Valid @RequestBody UpdateAttendanceRequest request) {

        return attendanceService.updateAttendance(
                attendanceId, request.getStatus());
    }

    public static class AttendanceRequest {

        @NotNull
        private Long studentId;

        @NotNull
        private LocalDate date;

        @NotBlank
        private String status;

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class UpdateAttendanceRequest {

        @NotBlank
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
