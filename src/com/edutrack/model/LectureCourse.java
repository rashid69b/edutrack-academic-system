package com.edutrack.model;

public class LectureCourse extends Course {
    private final int lectureHours;

    public LectureCourse(String code, String title, int credits, int capacity, int lectureHours) {
        super(code, title, credits, capacity);
        if (lectureHours <= 0) throw new IllegalArgumentException("Lecture hours must be positive.");
        this.lectureHours = lectureHours;
    }

    @Override
    public int weeklyWorkloadHours() {
        return lectureHours;
    }

    public int getLectureHours() { return lectureHours; }
}
