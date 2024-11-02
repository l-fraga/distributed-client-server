package tests;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConcurrencyTest {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String accountId = "12345";
            bankService.closeAccount(accountId);
            bankService.openAccount(accountId);

            Thread t1 = new Thread(() -> {
                try {
                    bankService.deposit(accountId, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    bankService.deposit(accountId, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread t3 = new Thread(() -> {
                try {
                    bankService.withdraw(accountId, 150);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();

            double finalBalance = bankService.consultBalance(accountId);
            System.out.println("Saldo final da conta " + accountId + ": " + finalBalance);

        } catch (Exception e) {
            System.err.println("Erro no teste de concorrência: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
