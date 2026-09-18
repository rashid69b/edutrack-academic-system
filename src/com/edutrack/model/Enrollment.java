package com.edutrack.model;

public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private Integer marks;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public Integer getMarks() { return marks; }

    public void setMarks(int marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        this.marks = marks;
    }

    public String grade() {
        if (marks == null) return "NA";
        if (marks >= 90) return "S";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        if (marks >= 40) return "E";
        return "F";
    }

    public double gradePoint() {
        return switch (grade()) {
            case "S" -> 10.0;
            case "A" -> 9.0;
            case "B" -> 8.0;
            case "C" -> 7.0;
            case "D" -> 6.0;
            case "E" -> 5.0;
            case "F" -> 0.0;
            default -> 0.0;
        };
    }
}
