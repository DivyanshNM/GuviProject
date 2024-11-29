CREATE DATABASE SimpleBankingSystem;


USE SimpleBankingSystem;


CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15),
    Address TEXT,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE BankAccounts (
    AccountID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    AccountNumber VARCHAR(20) UNIQUE NOT NULL,
    AccountType ENUM('Savings', 'Current', 'Fixed Deposit') NOT NULL,
    Balance DECIMAL(15, 2) DEFAULT 0.00,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    AccountID INT NOT NULL,
    TransactionType ENUM('Deposit', 'Withdrawal', 'Transfer') NOT NULL,
    Amount DECIMAL(15, 2) NOT NULL,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Remarks VARCHAR(255),
    FOREIGN KEY (AccountID) REFERENCES BankAccounts(AccountID) ON DELETE CASCADE
);
INSERT INTO Transactions (AccountID, TransactionType, Amount, Remarks)
VALUES 
(101, 'Deposit', 2000.00, 'Monthly savings deposit'), 
(102, 'Withdrawal', 1000.00, 'Office rent payment'),  
(103, 'Deposit', 5000.00, 'Salary credited'),         
(104, 'Deposit', 10000.00, 'Initial fixed deposit');  
SELECT * FROM BankAccounts;

INSERT INTO Customers (FirstName, LastName, Email, PhoneNumber, Address)
VALUES 
('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Main Street, Cityville'),
('Jane', 'Smith', 'jane.smith@example.com', '9876543210', '456 Elm Street, Townsville'),
('Emily', 'Johnson', 'emily.johnson@example.com', '5551234567', '789 Pine Avenue, Villageton');
INSERT INTO BankAccounts (CustomerID, AccountNumber, AccountType, Balance)
VALUES 
(1, 'ACC1001', 'Savings', 10000.00), 
(2, 'ACC1002', 'Current', 5000.00),  
(3, 'ACC1003', 'Savings', 25000.00), 
(1, 'ACC1004', 'Fixed Deposit', 50000.00); 




