package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ATMClientCashier {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando ao registro RMI na porta 1099...");
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");
            System.out.println("Conexão com o serviço BankService realizada com sucesso.");

            String accountId = "12345";

            String depositTransactionId = UUID.randomUUID().toString();
            try {
                System.out.println("Tentativa de depósito de 300 na conta " + accountId + " com transactionId: " + depositTransactionId);
                bankService.deposit(accountId, 300.0, depositTransactionId);

                throw new Exception("Falha simulada após o depósito de 300.");
            } catch (Exception e) {
                System.err.println("Erro após o depósito: " + e.getMessage());
                e.printStackTrace();
            }

            String withdrawTransactionId = UUID.randomUUID().toString();
            try {
                System.out.println("Tentativa de saque de 100 na conta " + accountId + " com transactionId: " + withdrawTransactionId);
                bankService.withdraw(accountId, 100.0, withdrawTransactionId);


                throw new Exception("Falha simulada após o saque de 100.");
            } catch (Exception e) {
                System.err.println("Erro após o saque: " + e.getMessage());
                e.printStackTrace();
            }


            try {
                double currentBalance = bankService.consultBalance(accountId);
                System.out.println("Saldo final da conta " + accountId + ": " + currentBalance);
            } catch (Exception e) {
                System.err.println("Erro ao consultar o saldo: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("Erro geral no cliente ATM: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
