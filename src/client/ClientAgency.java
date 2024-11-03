package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ClientAgency {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String accountId = "12345";
            bankService.openAccount(accountId);
            System.out.println("Conta " + accountId + " aberta.");

            String transactionId = UUID.randomUUID().toString();
            bankService.deposit(accountId, 500.0, transactionId);
            System.out.println("Depósito de 500.0 realizado na conta " + accountId);

            transactionId = UUID.randomUUID().toString();
            bankService.withdraw(accountId, 200.0, transactionId);
            System.out.println("Saque de 200.0 realizado na conta " + accountId);

            double currentBalance = bankService.consultBalance(accountId);
            System.out.println("Saldo atual da conta " + accountId + ": " + currentBalance);

        } catch (Exception e) {
            System.err.println("Erro no cliente Agência: " + e.getMessage());

        }
    }
}
