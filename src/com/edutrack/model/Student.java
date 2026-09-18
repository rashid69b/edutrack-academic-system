package com.edutrack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Student {
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final String id;
    private final String name;
    private final String email;
    private final String department;
    private final int maxCredits;
    private final List<String> enrolledCourseCodes = new ArrayList<>();

    public Student(String id, String name, String email, String department, int maxCredits) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student ID and name are required.");
        }
        if (email == null || !EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (department == null || department.isBlank() || maxCredits <= 0) {
            throw new IllegalArgumentException("Invalid student details.");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.maxCredits = maxCredits;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public int getMaxCredits() { return maxCredits; }

    public List<String> getEnrolledCourseCodes() {
        return List.copyOf(enrolledCourseCodes);
    }

    public boolean hasCourse(String courseCode) {
        return enrolledCourseCodes.contains(courseCode);
    }

    public void addCourse(String courseCode) {
        if (!hasCourse(courseCode)) enrolledCourseCodes.add(courseCode);
    }
}
