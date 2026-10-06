package com.welltrack.dao;

import com.welltrack.model.TimeEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TimeEntryDAO {

    public boolean addTimeEntry(TimeEntry entry) {

        String sql = "INSERT INTO time_entries " +
                     "(user_id, work_hours, break_hours, description, entry_date) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, entry.getUserId());
            statement.setDouble(2, entry.getWorkHours());
            statement.setDouble(3, entry.getBreakHours());
            statement.setString(4, entry.getDescription());
            statement.setString(5, entry.getEntryDate());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TimeEntry getLatestEntry(int userId) {

        String sql = "SELECT * FROM time_entries " +
                     "WHERE user_id = ? " +
                     "ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                TimeEntry entry = new TimeEntry();

                entry.setId(resultSet.getInt("id"));
                entry.setUserId(resultSet.getInt("user_id"));
                entry.setWorkHours(resultSet.getDouble("work_hours"));
                entry.setBreakHours(resultSet.getDouble("break_hours"));
                entry.setDescription(
                        resultSet.getString("description")
                );
                entry.setEntryDate(
                        resultSet.getString("entry_date")
                );

                return entry;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}