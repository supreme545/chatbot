package com.user.dao;
import com.user.database.DatabaseConnection;
import com.user.model.User;
import java.sql.*;


public class UserDAO {
	 public User getUserById(int userId) {
	        String query = "SELECT * FROM Users WHERE id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            
	            stmt.setInt(1, userId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("role"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
