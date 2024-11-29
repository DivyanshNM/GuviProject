package bank;

import database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccount {
    private String accountHolder;
    private String accountNumber;
    private double balance;

    // Constructor for creating a new account
    public BankAccount(String accountHolder, String accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    // Constructor to load an account from the database
    public BankAccount(String accountNumber) throws SQLException {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM BankAccount WHERE accountNumber = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    this.accountHolder = rs.getString("accountHolder");
                    this.accountNumber = rs.getString("accountNumber");
                    this.balance = rs.getDouble("balance");
                } else {
                    throw new SQLException("Account not found.");
                }
            }
        }
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Save a new account to the database
    public void saveToDatabase() throws SQLException {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "INSERT INTO BankAccount (accountNumber, accountHolder, balance) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, accountNumber);
                stmt.setString(2, accountHolder);
                stmt.setDouble(3, balance);
                stmt.executeUpdate();
            }
        }
    }

    // Deposit money
    public void deposit(double amount) throws SQLException {
        if (amount > 0) {
            balance += amount;
            updateBalance();
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw money
    public void withdraw(double amount) throws SQLException {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalance();
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid withdrawal amount.");
        }
    }

    // Update balance in the database
    private void updateBalance() throws SQLException {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "UPDATE BankAccount SET balance = ? WHERE accountNumber = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, balance);
                stmt.setString(2, accountNumber);
                stmt.executeUpdate();
            }
        }
    }

    // Display account details
    public void displayAccountInfo() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: $" + balance);
    }
}
