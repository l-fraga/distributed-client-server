package server;

import interfaces.BankService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private ConcurrentHashMap<String, Double> accounts;
    private ReentrantLock lock;
    private ConcurrentHashMap<String, Boolean> processedTransactions;



    public BankServiceImpl() throws RemoteException {
        accounts = new ConcurrentHashMap<>();
        lock = new ReentrantLock();
        processedTransactions = new ConcurrentHashMap<>();
    }

    @Override
    public void openAccount(String accountID) throws RemoteException {
        lock.lock();
        try {
            if (accounts.containsKey(accountID)) {
                System.out.println("Conta " + accountID + " já existente.");
            } else {
                accounts.put(accountID, 0.0);
                System.out.println("Conta " + accountID + " aberta.");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void closeAccount(String accountId) throws RemoteException {
        lock.lock();
        try {
            if (accounts.containsKey(accountId)) {
                accounts.remove(accountId);
                System.out.println("Conta " + accountId + " fechada.");
            } else {
                System.out.println("Conta " + accountId + " não existe.");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double consultBalance(String accountID) throws RemoteException {
        return accounts.getOrDefault(accountID, 0.0);
    }

    @Override
    public void deposit(String accountID, double amount, String transactionId) throws RemoteException {
        if (processedTransactions.putIfAbsent(transactionId, true) != null) {
            System.out.println("Depósito ignorado. Transação já processada: " + transactionId);
            return;
        }

        lock.lock();
        try {
            double currentBalance = accounts.getOrDefault(accountID, 0.0);
            accounts.put(accountID, currentBalance + amount);
            System.out.println("Depositado " + amount + " na conta " + accountID + ".");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(String accountId, double amount, String transactionId) throws RemoteException {
        if (processedTransactions.putIfAbsent(transactionId, true) != null) {
            System.out.println("Saque ignorado. Transação já processada: Transação já processada: " + transactionId);
            return;
        }

        lock.lock();
        try {
            double currentBalance = accounts.getOrDefault(accountId, 0.0);
            if (currentBalance >= amount) {
                accounts.put(accountId, currentBalance - amount);
                System.out.println("Sacado " + amount + " da conta " + accountId + ".");
            } else {
                System.out.println("Saldo insuficiente na conta " + accountId + ".");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void cleanAccounts() throws RemoteException {
        lock.lock();
        try {
            accounts.clear();
            processedTransactions.clear();
            System.out.println("Contas limpas.");
        } finally {
            lock.unlock();
        }
    }
}
