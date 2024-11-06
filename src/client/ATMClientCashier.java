package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ATMClientCashier {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String accountId = "12345";


            String transactionId = UUID.randomUUID().toString();
            try {
                System.out.println("Iniciando depósito de 300 na conta " + accountId + " com transactionId: " + transactionId);
                bankService.deposit(accountId, 300.0, transactionId);
                System.out.println("Depósito de 300 realizado na conta " + accountId);
            } catch (Exception e) {
                System.out.println("Falha no depósito. Tentando novamente com o mesmo transactionId: " + transactionId);
                bankService.deposit(accountId, 300.0, transactionId);
                System.out.println("Depósito de 300 concluído após reenvio.");
            }

            transactionId = UUID.randomUUID().toString();
            try {
                System.out.println("Iniciando saque de 100 na conta " + accountId + " com transactionId: " + transactionId);
                bankService.withdraw(accountId, 100.0, transactionId);
                System.out.println("Saque de 100 realizado na conta " + accountId);
            } catch (Exception e) {
                System.out.println("Falha no saque. Tentando novamente com o mesmo transactionId: " + transactionId);
                bankService.withdraw(accountId, 100.0, transactionId);
                System.out.println("Saque de 100 concluído após reenvio.");
            }

            double currentBalance = bankService.consultBalance(accountId);
            System.out.println("Saldo final da conta " + accountId + ": " + currentBalance);

        } catch (Exception e) {
            System.err.println("Erro no cliente ATM: " + e.getMessage());
            e.printStackTrace();
        }
    }
}