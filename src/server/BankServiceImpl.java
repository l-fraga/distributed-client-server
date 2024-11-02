package server;

import interfaces.BankService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private ConcurrentHashMap<String, Double> accounts;
    private ReentrantLock lock;


    public BankServiceImpl() throws RemoteException {
        accounts = new ConcurrentHashMap<>();
        lock = new ReentrantLock();
    }

    @Override
    public void openAccount(String accountID) throws RemoteException {
        lock.lock();
        try {
            if (accounts.containsKey(accountID)) {
                System.out.println("Account " + accountID + " already exists.");
            } else {
                accounts.put(accountID, 0.0);
                System.out.println("Account " + accountID + " opened.");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void closeAccount(String accountID) throws RemoteException {
        lock.lock();
        try {
            if (accounts.containsKey(accountID)) {
                accounts.remove(accountID);
                System.out.println("Account " + accountID + " closed.");
            } else {
                System.out.println("Account " + accountID + " does not exist.");
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
    public void deposit(String accountID, double amount) throws RemoteException {
        lock.lock();
        try {
            double currentBalance = accounts.getOrDefault(accountID, 0.0);
            accounts.put(accountID, currentBalance + amount);
            System.out.println("Deposited " + amount + " to account " + accountID + ".");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(String accountId, double amount) throws RemoteException {
        lock.lock();
        try {
            double currentBalance = accounts.getOrDefault(accountId, 0.0);
            if (currentBalance >= amount) {
                accounts.put(accountId, currentBalance - amount);
                System.out.println("Withdrew " + amount + " from account " + accountId + ".");
            } else {
                System.out.println("Insufficient funds in account " + accountId + ".");
            }
        } finally {
            lock.unlock();
        }
    }
}
