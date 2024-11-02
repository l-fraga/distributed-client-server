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
            String transactionId = UUID.randomUUID().toString();
            String accountId = UUID.randomUUID().toString();
            bankService.deposit(accountId, 500.0, transactionId);
            System.out.println("Depósito de 500.0 realizado na conta " + accountId + " com ID de transação: " + transactionId);

        } catch (Exception e) {
            System.out.println("Erro no cliente Agência: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
