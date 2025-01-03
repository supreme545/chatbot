package com.chatbot.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            System.out.println("Database connection successful!");
            System.out.println("Connection details: " + conn.getMetaData().getDatabaseProductName() + " " + 
                             conn.getMetaData().getDatabaseProductVersion());
            conn.close();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
