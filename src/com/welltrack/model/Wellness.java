package com.welltrack.model;

import com.welltrack.dao.WellnessParameterDAO;

public class Wellness {

    private int activityLevel;
    private double workHours;
    private double breakHours;
    private double wellnessScore;

    public Wellness() {
    }

    public Wellness(int activityLevel,
                    double workHours,
                    double breakHours) {

        this.activityLevel = activityLevel;
        this.workHours = workHours;
        this.breakHours = breakHours;
    }

    public double calculateScore() {

        WellnessParameterDAO parameterDAO =
                new WellnessParameterDAO();

        double maxWorkHours =
                parameterDAO.getMaxWorkHours();

        double minBreakHours =
                parameterDAO.getMinBreakHours();

        int minActivityLevel =
                parameterDAO.getMinActivityLevel();

        double score = 0;

        // Work hours
        if (workHours <= maxWorkHours) {
            score += 40;
        }

        // Break time
        if (breakHours >= minBreakHours) {
            score += 25;
        }

        // Physical activity
        if (activityLevel >= minActivityLevel) {
            score += 35;
        }

        wellnessScore = score;

        return wellnessScore;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public double getWorkHours() {
        return workHours;
    }

    public double getBreakHours() {
        return breakHours;
    }

    public double getWellnessScore() {
        return wellnessScore;
    }
}