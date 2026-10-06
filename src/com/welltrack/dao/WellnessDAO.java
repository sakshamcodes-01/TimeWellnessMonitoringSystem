package com.welltrack.dao;

import com.welltrack.model.Wellness;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WellnessDAO {

    public boolean saveWellness(int userId, Wellness wellness) {

        String sql = "INSERT INTO wellness_metrics " +
                     "(user_id, activity_level, work_hours, break_hours, wellness_score, recorded_date) " +
                     "VALUES (?, ?, ?, ?, ?, CURDATE())";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, wellness.getActivityLevel());
            statement.setDouble(3, wellness.getWorkHours());
            statement.setDouble(4, wellness.getBreakHours());
            statement.setDouble(5, wellness.getWellnessScore());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Wellness getLatestWellness(int userId) {

        String sql = "SELECT * FROM wellness_metrics " +
                     "WHERE user_id = ? " +
                     "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Wellness wellness = new Wellness(
                        resultSet.getInt("activity_level"),
                        resultSet.getDouble("work_hours"),
                        resultSet.getDouble("break_hours")
                );

                return wellness;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getLatestWellnessScore(int userId) {

        String sql = "SELECT wellness_score " +
                     "FROM wellness_metrics " +
                     "WHERE user_id = ? " +
                     "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("wellness_score");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}