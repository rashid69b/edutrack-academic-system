package com.edutrack;

import com.edutrack.exception.*;
import com.edutrack.model.*;
import com.edutrack.service.*;

import java.util.Scanner;

public class EduTrackApp {
    private static final AcademicService service = new AcademicService();
    private static final AnalyticsService analytics = new AnalyticsService(service);

    public static void main(String[] args) {
        seedDemoData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== EDUTRACK ==========");
            System.out.println("1. View students");
            System.out.println("2. View courses");
            System.out.println("3. Enroll student");
            System.out.println("4. Record marks");
            System.out.println("5. View transcript");
            System.out.println("6. Analytics");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> listStudents();
                    case "2" -> listCourses();
                    case "3" -> enroll(sc);
                    case "4" -> marks(sc);
                    case "5" -> transcript(sc);
                    case "6" -> analytics.printReport();
                    case "7" -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (EduTrackException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void seedDemoData() {
        try {
            service.addStudent(new Student("STU001", "Demo Student", "demo@example.com", "ECE", 24));
            service.addCourse(new LectureCourse("OOP101", "Object Oriented Programming", 4, 30, 4));
            service.addCourse(new LaboratoryCourse("OOP102", "Java Programming Lab", 2, 25, 3));
        } catch (Exception ignored) {
            // Demo data is only inserted once per application run.
        }
    }

    private static void listStudents() {
        System.out.println("\n--- Students ---");
        for (Student s : service.students()) {
            System.out.println(s.getId() + " | " + s.getName() + " | " + s.getDepartment());
        }
    }

    private static void listCourses() {
        System.out.println("\n--- Courses ---");
        for (Course c : service.courses()) {
            System.out.printf("%s | %s | %d credits | seats %d/%d | workload %dh%n",
                    c.getCode(), c.getTitle(), c.getCredits(),
                    c.getEnrolledCount(), c.getCapacity(), c.weeklyWorkloadHours());
        }
    }

    private static void enroll(Scanner sc) throws EduTrackException {
        System.out.print("Student ID: ");
        String student = sc.nextLine();
        System.out.print("Course code: ");
        String course = sc.nextLine();
        service.enroll(student, course);
        System.out.println("Enrollment successful.");
    }

    private static void marks(Scanner sc) throws EduTrackException {
        System.out.print("Student ID: ");
        String student = sc.nextLine();
        System.out.print("Course code: ");
        String course = sc.nextLine();
        System.out.print("Marks (0-100): ");
        int marks = Integer.parseInt(sc.nextLine());
        service.recordMarks(student, course, marks);
        System.out.println("Marks saved.");
    }

    private static void transcript(Scanner sc) throws EduTrackException {
        System.out.print("Student ID: ");
        String student = sc.nextLine();
        System.out.println(service.transcript(student));
    }
}
