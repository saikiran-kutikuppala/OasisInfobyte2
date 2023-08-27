import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
        if (withdraw(amount)) {
            recipient.deposit(amount);
        }
    }

    public void addTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount));
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:\n");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + " Rs." + transaction.getAmount());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Account account = new Account("user1234", "1234", 1000.0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM system!");

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (account.authenticate(pin) && account.getUserId().equals(userId)) {
            System.out.println("Authentication successful!");
            showMenu(account, scanner);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    public static void showMenu(Account account, Scanner scanner) {
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Quit\n");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\n--------------------");
                    System.out.println("Your balance: Rs." + account.getBalance());
                    System.out.println("--------------------");
                    break;
                case 2:
                    System.out.println("\n--------------------");
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    account.addTransaction("Deposit", depositAmount);
                    System.out.println("--------------------");
                    break;
                case 3:
                    System.out.println("\n--------------------");
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawalAmount)) {
                        account.addTransaction("Withdrawal", withdrawalAmount);
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    System.out.println("--------------------");
                    break;
                case 4:
                    System.out.println("\n--------------------");
                    System.out.print("Enter recipient's User ID: ");
                    String recipientUserId = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    Account recipientAccount = account;
                    account.transfer(recipientAccount, transferAmount);
                    account.addTransaction("Transfer to " + recipientUserId, transferAmount);
                    System.out.println("--------------------");
                    break;
                case 5:
                    System.out.println("\n--------------------");
                    account.printTransactionHistory();
                    System.out.println("--------------------");
                    break;
                case 6:
                    System.out.println("\n--------------------");
                    System.out.println("Exiting ATM...");
                    System.out.println("--------------------");
                    return;
                default:
                    System.out.println("\n--------------------");
                    System.out.println("Invalid choice. Try again.");
                    System.out.println("--------------------");
            }
        }
    }
}
