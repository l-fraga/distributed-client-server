package server;

import interfaces.BankService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            BankService bankService = new BankServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("bankService", bankService);
            System.out.println("Bank service started.");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
