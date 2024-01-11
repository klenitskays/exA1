package com.example.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class RestapiApplication {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgre";

    public static void main(String[] args) {
        SpringApplication.run(RestapiApplication.class, args);
    }

    // Обработчик GET-запроса на /postings
    @GetMapping("/postings")
    public List<Posting> getPostings(@RequestParam("userName") String userName) {
        List<Posting> postings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM postings WHERE UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Posting posting = new Posting();
                posting.setMatDoc(resultSet.getString("MatDoc"));
                posting.setItem(resultSet.getString("Item"));
                posting.setDocDate(resultSet.getString("DocDate"));
                posting.setPstngDate(resultSet.getString("PstngDate"));
                posting.setMaterialDescription(resultSet.getString("MaterialDescription"));
                posting.setQuantity(resultSet.getString("Quantity"));
                posting.setBUn(resultSet.getString("BUn"));
                posting.setAmountLC(resultSet.getString("AmountLC"));
                posting.setCrcy(resultSet.getString("Crcy"));
                posting.setUserName(resultSet.getString("UserName"));
                posting.setAuthorized(resultSet.getBoolean("Authorized"));
                postings.add(posting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postings;
    }
}

class Posting {
    private String matDoc;
    private String item;
    private String docDate;
    private String pstngDate;
    private String materialDescription;
    private String quantity;
    private String bUn;
    private String amountLC;
    private String crcy;
    private String userName;
    private boolean authorized;

    // геттеры и сеттеры
    public void setMatDoc(String matDoc) {
        this.matDoc = matDoc;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public void setPstngDate(String pstngDate) {
        this.pstngDate = pstngDate;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setBUn(String bUn) {
        this.bUn = bUn;
    }

    public void setAmountLC(String amountLC) {
        this.amountLC = amountLC;
    }

    public void setCrcy(String crcy) {
        this.crcy = crcy;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
    public String getMatDoc() {
        return matDoc;
    }

    public String getItem() {
        return item;
    }

    public String getDocDate() {
        return docDate;
    }

    public String getPstngDate() {
        return pstngDate;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBUn() {
        return bUn;
    }

    public String getAmountLC() {
        return amountLC;
    }

    public String getCrcy() {
        return crcy;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAuthorized() {
        return authorized;
    }
}
