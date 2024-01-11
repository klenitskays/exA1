package com.example.restapi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class PostingsCSVImporter {
    private static final String CSV_FILE_PATH = "postings.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgre";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             CSVParser csvParser = new CSVParser(new FileReader(CSV_FILE_PATH), CSVFormat.DEFAULT.withHeader())) {

            String insertQuery = "INSERT INTO postings (MatDoc, Item, DocDate, PstngDate, MaterialDescription, Quantity, BUn, AmountLC, Crcy, UserName, Authorized) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (CSVRecord record : csvParser) {
                String matDoc = record.get("Mat.Doc");
                String item = record.get("Item");
                String docDate = record.get("Doc.Date");
                String pstngDate = record.get("Pstng.Date");
                String materialDescription = record.get("Material Description");
                String quantity = record.get("Quantity");
                String bUn = record.get("BUn");
                String amountLC = record.get("Amount LC");
                String crcy = record.get("Crcy");
                String userName = record.get("User Name");

                boolean isAuthorized = checkAuthorization(userName, connection);

                preparedStatement.setString(1, matDoc);
                preparedStatement.setString(2, item);
                preparedStatement.setString(3, docDate);
                preparedStatement.setString(4, pstngDate);
                preparedStatement.setString(5, materialDescription);
                preparedStatement.setString(6, quantity);
                preparedStatement.setString(7, bUn);
                preparedStatement.setString(8, amountLC);
                preparedStatement.setString(9, crcy);
                preparedStatement.setString(10, userName);
                preparedStatement.setBoolean(11, isAuthorized);

                preparedStatement.executeUpdate();
            }
            System.out.println("Data from postings.csv imported successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkAuthorization(String userName, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM logins WHERE AppAccountName = ? AND IsActive = true";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }
}
