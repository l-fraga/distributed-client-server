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
            bankService.deposit(accountId, 300.0, transactionId);
            System.out.println("Depósito de 300.0 realizado na conta " + accountId);

            bankService.withdraw(accountId, 100.0, transactionId);
            System.out.println("Saque de 100.0 realizado na conta " + accountId);

            double currentBalance = bankService.consultBalance(accountId);
            System.out.println("Saldo atual da conta " + accountId + ": " + currentBalance);

        } catch (Exception e) {
            System.err.println("Erro no cliente Caixa Automático: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
