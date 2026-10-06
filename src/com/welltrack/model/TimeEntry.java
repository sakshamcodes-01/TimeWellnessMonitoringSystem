package com.welltrack.model;

public class TimeEntry {

    private int id;
    private int userId;
    private double workHours;
    private double breakHours;
    private String description;
    private String entryDate;

    public TimeEntry() {
    }

    public TimeEntry(int userId, double workHours,
                     double breakHours, String description,
                     String entryDate) {

        this.userId = userId;
        this.workHours = workHours;
        this.breakHours = breakHours;
        this.description = description;
        this.entryDate = entryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getBreakHours() {
        return breakHours;
    }

    public void setBreakHours(double breakHours) {
        this.breakHours = breakHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}