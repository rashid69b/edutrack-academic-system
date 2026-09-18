package com.edutrack.service;

import com.edutrack.model.*;

import java.util.*;

public class AnalyticsService {
    private final AcademicService service;

    public AnalyticsService(AcademicService service) {
        this.service = service;
    }

    public void printReport() {
        Map<String, Integer> departments = new TreeMap<>();
        for (Student s : service.students()) {
            departments.merge(s.getDepartment(), 1, Integer::sum);
        }

        System.out.println("\n--- Department Distribution ---");
        departments.forEach((d, count) -> System.out.println(d + " : " + count));

        System.out.println("\n--- Course Seat Utilization ---");
        for (Course c : service.courses()) {
            double used = c.getCapacity() == 0 ? 0 : 100.0 * c.getEnrolledCount() / c.getCapacity();
            System.out.printf("%s | %s | %.1f%%%n", c.getCode(), c.getTitle(), used);
        }

        Map<String, Integer> grades = new TreeMap<>();
        for (Enrollment e : service.enrollments()) {
            if (!"NA".equals(e.grade())) grades.merge(e.grade(), 1, Integer::sum);
        }

        System.out.println("\n--- Grade Distribution ---");
        grades.forEach((g, count) -> System.out.println(g + " : " + count));
    }
}
