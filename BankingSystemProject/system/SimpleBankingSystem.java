package system;

import bank.BankAccount;
import database.DatabaseHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class SimpleBankingSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        setupDatabase();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Simple Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Display Account Info");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> checkBalance();
                case 5 -> displayAccountInfo();
                case 6 -> {
                    running = false;
                    System.out.println("Thank you for using the Simple Banking System!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void setupDatabase() {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS BankAccount (
                        accountNumber VARCHAR(50) PRIMARY KEY,
                        accountHolder VARCHAR(100) NOT NULL,
                        balance DOUBLE NOT NULL DEFAULT 0.0
                    );
                    """;
            try (var stmt = conn.createStatement()) {
                stmt.execute(createTableQuery);
                System.out.println("Database setup complete.");
            }
        } catch (SQLException e) {
            System.out.println("Error setting up the database: " + e.getMessage());
        }
    }

    private static void createAccount() {
        System.out.print("Enter account holder's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter a new account number: ");
        String accountNumber = scanner.nextLine();

        try {
            BankAccount account = new BankAccount(name, accountNumber);
            account.saveToDatabase();
            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static void depositMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        try {
            BankAccount account = new BankAccount(accountNumber);
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        try {
            BankAccount account = new BankAccount(accountNumber);
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        try {
            BankAccount account = new BankAccount(accountNumber);
            System.out.println("Current Balance: $" + account.getBalance());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAccountInfo() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        try {
            BankAccount account = new BankAccount(accountNumber);
            account.displayAccountInfo();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
