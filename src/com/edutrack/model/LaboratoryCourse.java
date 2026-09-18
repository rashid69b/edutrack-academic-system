package com.edutrack.model;

public class LaboratoryCourse extends Course {
    private final int labHours;

    public LaboratoryCourse(String code, String title, int credits, int capacity, int labHours) {
        super(code, title, credits, capacity);
        if (labHours <= 0) throw new IllegalArgumentException("Lab hours must be positive.");
        this.labHours = labHours;
    }

    @Override
    public int weeklyWorkloadHours() {
        return labHours;
    }

    public int getLabHours() { return labHours; }
}
