package tests;

import interfaces.BankService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConcurrencyTest {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");
            String accountId = "12345";

            // Open account first
            bankService.openAccount(accountId);

            // Create threads for concurrent deposits
            Thread t1 = new Thread(() -> {
                try {
                    bankService.deposit(accountId, 100);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    bankService.deposit(accountId, 200);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            Thread t3 = new Thread(() -> {
                try {
                    bankService.withdraw(accountId, 150);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            // Start threads
            t1.start();
            t2.start();
            t3.start();

            // Wait for all threads to finish
            t1.join();
            t2.join();
            t3.join();

            // Check final balance
            double finalBalance = bankService.consultBalance(accountId);
            System.out.println("Final balance account" + accountId + ": " + finalBalance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

