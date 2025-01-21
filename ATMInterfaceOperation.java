
import java.util.HashMap;
import java.util.Scanner;

class Account {
    private double balance;
    private int pin;

    public Account(double initialBalance, int pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public boolean authenticate(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public boolean changePin(int oldPin, int newPin) {
        if (authenticate(oldPin)) {
            this.pin = newPin;
            return true;
        }
        return false;
    }
}

public class ATM {
    private HashMap<Integer, Account> accounts;
    private Account currentAccount;

    public ATM() {
        accounts = new HashMap<>();
    }

    public void addAccount(int accountNumber, double initialBalance, int pin) {
        accounts.put(accountNumber, new Account(initialBalance, pin));
    }

    public boolean authenticate(int accountNumber, int pin) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            if (account.authenticate(pin)) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your balance is: " + currentAccount.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentAccount.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful! New balance: " + currentAccount.getBalance());
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    System.out.println("Deposit successful! New balance: " + currentAccount.getBalance());
                    break;

                case 4:
                    System.out.print("Enter old PIN: ");
                    int oldPin = scanner.nextInt();
                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();
                    if (currentAccount.changePin(oldPin, newPin)) {
                        System.out.println("PIN changed successfully!");
                    } else {
                        System.out.println("Incorrect old PIN!");
                    }
                    break;

                case 5:
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        atm.addAccount(101, 5000.00, 1234);
        atm.addAccount(102, 10000.00, 5678);
        atm.addAccount(103, 7500.00, 4321);

        System.out.println("Welcome to the ATM!");

        while (true) {
            System.out.print("\nEnter your Account Number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            if (atm.authenticate(accountNumber, pin)) {
                System.out.println("Login Successful!");
                atm.showMenu();
            } else {
                System.out.println("Invalid Account Number or PIN. Try again.");
            }
        }
    }
}
