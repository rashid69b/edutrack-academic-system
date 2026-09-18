package com.edutrack.model;

public abstract class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final int capacity;
    private int enrolledCount;

    protected Course(String code, String title, int credits, int capacity) {
        if (code == null || code.isBlank() || title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course code and title are required.");
        }
        if (credits <= 0 || capacity <= 0) {
            throw new IllegalArgumentException("Credits and capacity must be positive.");
        }
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
    }

    public abstract int weeklyWorkloadHours();

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public int getEnrolledCount() { return enrolledCount; }

    public void incrementEnrollment() {
        if (enrolledCount >= capacity) {
            throw new IllegalStateException("Course is full.");
        }
        enrolledCount++;
    }

    public int availableSeats() {
        return capacity - enrolledCount;
    }
}
