package minibank.service;

import minibank.model.Account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankService {
    private List<Account> accounts;

    public BankService() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Cannot add a null account.");
        }
        for (Account existing : accounts) {
            if (existing.getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) {
                throw new IllegalArgumentException("Account with number " + account.getAccountNumber() + " already exists.");
            }
        }
        accounts.add(account);
    }

    public Account findAccount(String accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("Account number cannot be null.");
        }
        for (Account account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber.trim())) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found with number: " + accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public List<Account> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(accounts));
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            this.accounts = (ArrayList<Account>) ois.readObject();
        }
    }
}
