package com.example.restapi;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginsCSVImporter {
    private static final String CSV_FILE_PATH = "logins.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgre";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             CSVParser csvParser = new CSVParser(new FileReader(CSV_FILE_PATH), CSVFormat.DEFAULT.withHeader())) {

            String insertQuery = "INSERT INTO logins (AppAccountName, IsActive) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (CSVRecord record : csvParser) {
                String appAccountName = record.get("AppAccountName");
                boolean isActive = Boolean.parseBoolean(record.get("IsActive"));

                preparedStatement.setString(1, appAccountName);
                preparedStatement.setBoolean(2, isActive);
                preparedStatement.executeUpdate();
            }
            System.out.println("Data from logins.csv imported successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}