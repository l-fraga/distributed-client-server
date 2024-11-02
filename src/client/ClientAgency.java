package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientAgency {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String accountId = "12345";
            bankService.openAccount(accountId);
            System.out.println("Conta " + accountId + " aberta.");

            bankService.deposit(accountId, 500.0);
            System.out.println("Depósito de 500.0 realizado na conta " + accountId);

            bankService.withdraw(accountId, 200.0);
            System.out.println("Saque de 200.0 realizado na conta " + accountId);

            double saldo = bankService.consultBalance(accountId);
            System.out.println("Saldo atual da conta " + accountId + ": " + saldo);

        } catch (Exception e) {
            System.err.println("Erro no cliente Agência: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
