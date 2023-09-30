package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/BAD_LEC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Method to execute SELECT queries and return a ResultSet
    public static ResultSet executeQuery(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a PreparedStatement for the SQL query
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query and store the result
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection and resources in a finally block to ensure they are closed even in case of exceptions
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultSet;
    }

    // Method to execute INSERT, UPDATE, or DELETE queries
    public static int executeUpdate(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a PreparedStatement for the SQL query
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the number of rows affected
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection and resources in a finally block to ensure they are closed even in case of exceptions
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsAffected;
    }

    // ini cara pake nya di controller
    /*
    public static void main(String[] args) {
        // Example usage:
        // Execute a SELECT query
        String selectQuery = "SELECT * FROM your_table";
        ResultSet resultSet = executeQuery(selectQuery);

        // Process the ResultSet, e.g., iterate through rows and retrieve data

        // Execute an INSERT, UPDATE, or DELETE query
        String updateQuery = "UPDATE your_table SET column1 = 'value' WHERE condition";
        int rowsAffected = executeUpdate(updateQuery);

        // Print the number of rows affected
        System.out.println("Rows affected: " + rowsAffected);
    }
   */
}

