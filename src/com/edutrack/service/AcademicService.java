package com.edutrack.service;

import com.edutrack.exception.*;
import com.edutrack.model.*;

import java.util.*;

public class AcademicService {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, Enrollment> enrollments = new HashMap<>();

    public void addStudent(Student student) throws ValidationException {
        if (students.containsKey(student.getId())) {
            throw new ValidationException("Student ID already exists.");
        }
        students.put(student.getId(), student);
    }

    public void addCourse(Course course) throws ValidationException {
        if (courses.containsKey(course.getCode())) {
            throw new ValidationException("Course code already exists.");
        }
        courses.put(course.getCode(), course);
    }

    public void enroll(String studentId, String courseCode)
            throws ValidationException, DuplicateEnrollmentException, CourseFullException {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student == null) throw new ValidationException("Student not found.");
        if (course == null) throw new ValidationException("Course not found.");
        if (student.hasCourse(courseCode)) {
            throw new DuplicateEnrollmentException("Student is already enrolled in this course.");
        }

        int currentCredits = currentCredits(student);
        if (currentCredits + course.getCredits() > student.getMaxCredits()) {
            throw new ValidationException("Semester credit limit would be exceeded.");
        }
        if (course.availableSeats() <= 0) {
            throw new CourseFullException("No seats are available in this course.");
        }

        course.incrementEnrollment();
        student.addCourse(courseCode);
        enrollments.put(key(studentId, courseCode), new Enrollment(studentId, courseCode));
    }

    public void recordMarks(String studentId, String courseCode, int marks) throws ValidationException {
        Enrollment e = enrollments.get(key(studentId, courseCode));
        if (e == null) throw new ValidationException("Enrollment not found.");
        e.setMarks(marks);
    }

    public double calculateCgpa(String studentId) throws ValidationException {
        Student student = students.get(studentId);
        if (student == null) throw new ValidationException("Student not found.");

        double points = 0;
        int credits = 0;
        for (Enrollment e : enrollments.values()) {
            if (e.getStudentId().equals(studentId) && e.getMarks() != null) {
                Course c = courses.get(e.getCourseCode());
                points += e.gradePoint() * c.getCredits();
                credits += c.getCredits();
            }
        }
        return credits == 0 ? 0.0 : points / credits;
    }

    public String transcript(String studentId) throws ValidationException {
        Student s = students.get(studentId);
        if (s == null) throw new ValidationException("Student not found.");

        StringBuilder out = new StringBuilder();
        out.append("\n========== ACADEMIC TRANSCRIPT ==========\n");
        out.append("Student ID : ").append(s.getId()).append('\n');
        out.append("Name       : ").append(s.getName()).append('\n');
        out.append("Department : ").append(s.getDepartment()).append('\n');
        out.append("------------------------------------------\n");

        for (String code : s.getEnrolledCourseCodes()) {
            Course c = courses.get(code);
            Enrollment e = enrollments.get(key(studentId, code));
            out.append(code).append(" | ").append(c.getTitle())
               .append(" | ").append(c.getCredits()).append(" cr | Grade: ")
               .append(e.grade()).append('\n');
        }

        out.append("------------------------------------------\n");
        out.append(String.format("CGPA: %.2f / 10.00%n", calculateCgpa(studentId)));
        out.append("==========================================\n");
        return out.toString();
    }

    public Collection<Student> students() { return students.values(); }
    public Collection<Course> courses() { return courses.values(); }
    public Collection<Enrollment> enrollments() { return enrollments.values(); }

    private int currentCredits(Student s) {
        int total = 0;
        for (String code : s.getEnrolledCourseCodes()) {
            total += courses.get(code).getCredits();
        }
        return total;
    }

    private String key(String studentId, String courseCode) {
        return studentId + "::" + courseCode;
    }
}
