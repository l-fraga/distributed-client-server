package server;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BankServer {
    public static void main(String[] args) {
        try {
            BankService bankService = new BankServiceImpl();
            BankService stub;
            try {
                stub = (BankService) UnicastRemoteObject.exportObject(bankService, 0);
            } catch (java.rmi.server.ExportException e) {
                System.out.println("Objeto já exportado, usando a instância existente.");
                stub = bankService;
            }

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("BankService", stub);

            System.out.println("Servidor iniciado e BankService registrado no RMI Registry...");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
