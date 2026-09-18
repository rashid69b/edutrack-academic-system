package com.edutrack.test;

import com.edutrack.exception.*;
import com.edutrack.model.*;
import com.edutrack.service.AcademicService;

public class EduTrackTestSuite {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws Exception {
        testStudentValidation();
        testPolymorphism();
        testDuplicateEnrollment();
        testCapacity();
        testCreditLimit();
        testCgpa();
        System.out.println("\nTests run: " + (passed + failed));
        System.out.println("Passed: " + passed + " | Failed: " + failed);
        if (failed > 0) System.exit(1);
    }

    private static void testStudentValidation() {
        try {
            new Student("S1", "Test User", "test@example.com", "ECE", 24);
            pass("Student validation");
        } catch (Exception e) { fail("Student validation"); }
    }

    private static void testPolymorphism() {
        try {
            Course a = new LectureCourse("L1", "OOP", 4, 30, 4);
            Course b = new LaboratoryCourse("B1", "Java Lab", 2, 20, 3);
            if (a.weeklyWorkloadHours() == 4 && b.weeklyWorkloadHours() == 3) pass("Polymorphism");
            else fail("Polymorphism");
        } catch (Exception e) { fail("Polymorphism"); }
    }

    private static void testDuplicateEnrollment() throws Exception {
        AcademicService s = baseService();
        s.enroll("S1", "C1");
        try {
            s.enroll("S1", "C1");
            fail("Duplicate enrollment");
        } catch (DuplicateEnrollmentException e) { pass("Duplicate enrollment"); }
    }

    private static void testCapacity() throws Exception {
        AcademicService s = new AcademicService();
        s.addStudent(new Student("S1", "One", "one@example.com", "ECE", 24));
        s.addStudent(new Student("S2", "Two", "two@example.com", "ECE", 24));
        s.addCourse(new LectureCourse("C1", "OOP", 4, 1, 4));
        s.enroll("S1", "C1");
        try {
            s.enroll("S2", "C1");
            fail("Capacity validation");
        } catch (CourseFullException e) { pass("Capacity validation"); }
    }

    private static void testCreditLimit() throws Exception {
        AcademicService s = new AcademicService();
        s.addStudent(new Student("S1", "One", "one@example.com", "ECE", 4));
        s.addCourse(new LectureCourse("C1", "OOP", 4, 10, 4));
        s.addCourse(new LectureCourse("C2", "Networks", 4, 10, 4));
        s.enroll("S1", "C1");
        try {
            s.enroll("S1", "C2");
            fail("Credit limit");
        } catch (ValidationException e) { pass("Credit limit"); }
    }

    private static void testCgpa() throws Exception {
        AcademicService s = baseService();
        s.enroll("S1", "C1");
        s.recordMarks("S1", "C1", 95);
        double cgpa = s.calculateCgpa("S1");
        if (Math.abs(cgpa - 10.0) < 0.0001) pass("CGPA calculation");
        else fail("CGPA calculation");
    }

    private static AcademicService baseService() throws Exception {
        AcademicService s = new AcademicService();
        s.addStudent(new Student("S1", "One", "one@example.com", "ECE", 24));
        s.addCourse(new LectureCourse("C1", "OOP", 4, 10, 4));
        return s;
    }

    private static void pass(String name) {
        passed++;
        System.out.println("[PASS] " + name);
    }

    private static void fail(String name) {
        failed++;
        System.out.println("[FAIL] " + name);
    }
}
