package client;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientAgency {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService bankService = (BankService) registry.lookup("BankService");
            bankService.openAccount("123");
            bankService.deposit("123", 100);
            System.out.println("Balance: " + bankService.consultBalance("123"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
