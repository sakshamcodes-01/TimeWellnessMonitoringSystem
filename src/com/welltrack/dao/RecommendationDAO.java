package com.welltrack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {

    public boolean saveRecommendation(
            int userId,
            String recommendation,
            String status) {

        String sql = "INSERT INTO recommendations " +
                     "(user_id, recommendation, status, created_date) " +
                     "VALUES (?, ?, ?, CURDATE())";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, recommendation);
            statement.setString(3, status);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getRecommendations(int userId) {

        List<String> recommendations =
                new ArrayList<>();

        String sql =
                "SELECT recommendation " +
                "FROM recommendations " +
                "WHERE user_id = ? " +
                "ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                recommendations.add(
                    resultSet.getString("recommendation")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recommendations;
    }
}