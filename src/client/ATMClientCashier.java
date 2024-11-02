package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ATMClientCashier {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");

            String contaId = "12345";

            bankService.deposit(contaId, 300.0);
            System.out.println("Depósito de 300.0 realizado na conta " + contaId);

            bankService.withdraw(contaId, 100.0);
            System.out.println("Saque de 100.0 realizado na conta " + contaId);

            double saldo = bankService.consultBalance(contaId);
            System.out.println("Saldo atual da conta " + contaId + ": " + saldo);

        } catch (Exception e) {
            System.err.println("Erro no cliente Caixa Automático: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
