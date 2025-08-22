package com.example.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchRunDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mixer?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Computer@1";

    public List<BatchRun> getAllRuns() {
        String sql = "SELECT * FROM batch_run ORDER BY id";
        List<BatchRun> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new BatchRun(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("monday"),
                        rs.getInt("tuesday"),
                        rs.getInt("wednesday"),
                        rs.getInt("thursday"),
                        rs.getInt("friday"),
                        rs.getInt("saturday"),
                        rs.getInt("sunday")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addRun(BatchRun run) {
        String sql = "INSERT INTO batch_run (name, monday, tuesday, wednesday, thursday, friday, saturday, sunday) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, run.getName());
            ps.setInt(2, run.getMonday());
            ps.setInt(3, run.getTuesday());
            ps.setInt(4, run.getWednesday());
            ps.setInt(5, run.getThursday());
            ps.setInt(6, run.getFriday());
            ps.setInt(7, run.getSaturday());
            ps.setInt(8, run.getSunday());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deleteRun(int id) {
        String sql = "DELETE FROM batch_run WHERE id = ?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
