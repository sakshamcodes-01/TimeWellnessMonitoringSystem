package com.welltrack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WellnessParameterDAO {

    public boolean saveParameters(
            double maxWorkHours,
            double minBreakHours,
            int minActivityLevel) {

        String sql = "INSERT INTO wellness_parameters " +
                     "(max_work_hours, min_break_hours, min_activity_level) " +
                     "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, maxWorkHours);
            statement.setDouble(2, minBreakHours);
            statement.setInt(3, minActivityLevel);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getMaxWorkHours() {

        String sql =
                "SELECT max_work_hours " +
                "FROM wellness_parameters " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble("max_work_hours");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 8.0;
    }

    public double getMinBreakHours() {

        String sql =
                "SELECT min_break_hours " +
                "FROM wellness_parameters " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble("min_break_hours");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1.0;
    }

    public int getMinActivityLevel() {

        String sql =
                "SELECT min_activity_level " +
                "FROM wellness_parameters " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("min_activity_level");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 60;
    }
}