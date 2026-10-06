package com.welltrack.service;

import com.welltrack.dao.WellnessParameterDAO;
import com.welltrack.model.Wellness;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    public List<String> generateRecommendations(
            Wellness wellness) {

        List<String> recommendations =
                new ArrayList<>();

        WellnessParameterDAO parameterDAO =
                new WellnessParameterDAO();

        double maxWorkHours =
                parameterDAO.getMaxWorkHours();

        double minBreakHours =
                parameterDAO.getMinBreakHours();

        int minActivityLevel =
                parameterDAO.getMinActivityLevel();

        // Work hours recommendation
        if (wellness.getWorkHours() > maxWorkHours) {

            recommendations.add(
                "Your work hours are high. " +
                "Take regular breaks and maintain work-life balance."
            );
        }

        // Break recommendation
        if (wellness.getBreakHours() < minBreakHours) {

            recommendations.add(
                "Increase your break time to at least " +
                minBreakHours + " hours."
            );
        }

        // Activity recommendation
        if (wellness.getActivityLevel() < minActivityLevel) {

            recommendations.add(
                "Increase your physical activity to at least " +
                minActivityLevel + " minutes."
            );
        }

        // Good wellness
        if (recommendations.isEmpty()) {

            recommendations.add(
                "Great! Your current routine shows " +
                "a good wellness balance."
            );
        }

        return recommendations;
    }
}