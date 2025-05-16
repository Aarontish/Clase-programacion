package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersModel {
    private Connection conn;
    
    public UsersModel() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/MySQL93", 
                "root", 
                "root"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<User> getAll() {
        List<User> usuarios = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                usuarios.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("phone"),
                    rs.getString("update at"),
                    rs.getString("create at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    public boolean remove(int id) {
        if (!isConnected()) {
            System.err.println("No hay conexión a la base de datos");
            return false;
        }
        
        String query = "DELETE FROM users WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}