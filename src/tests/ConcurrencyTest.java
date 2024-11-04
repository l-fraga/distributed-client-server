package tests;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ConcurrencyTest {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String accountId = "12345";
            System.out.println("Abrindo conta com ID: " + accountId);
            bankService.openAccount(accountId);

            Thread t1 = new Thread(() -> {
                try {
                    String transactionId = UUID.randomUUID().toString();
                    System.out.println("[T1] Iniciando depósito de 100 com transactionId: " + transactionId);
                    bankService.deposit(accountId, 100, transactionId);
                    System.out.println("[T1] Depósito de 100 concluído para transactionId: " + transactionId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    String transactionId = UUID.randomUUID().toString();
                    System.out.println("[T2] Iniciando depósito de 200 com transactionId: " + transactionId);
                    bankService.deposit(accountId, 200, transactionId);
                    System.out.println("[T2] Depósito de 200 concluído para transactionId: " + transactionId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread t3 = new Thread(() -> {
                try {
                    String transactionId = UUID.randomUUID().toString();
                    System.out.println("[T3] Iniciando saque de 150 com transactionId: " + transactionId);
                    bankService.withdraw(accountId, 150, transactionId);
                    System.out.println("[T3] Saque de 150 concluído para transactionId: " + transactionId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Iniciando operações concorrentes...");
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
