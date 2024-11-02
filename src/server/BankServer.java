package server;

import interfaces.BankService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BankServer {
    public static void main(String[] args) {
        try {
            // Criação da instância do serviço
            BankService bankService = new BankServiceImpl();

            // Verifique se o objeto já está exportado
            BankService stub;
            try {
                // Tente exportar o objeto
                stub = (BankService) UnicastRemoteObject.exportObject(bankService, 0);
            } catch (java.rmi.server.ExportException e) {
                // Caso já esteja exportado, use a instância existente
                System.out.println("Objeto já exportado, usando a instância existente.");
                stub = bankService;
            }

            // Cria o RMI Registry
            Registry registry = LocateRegistry.createRegistry(1099);

            // Registra o serviço com o nome "BankService"
            registry.rebind("BankService", stub);

            System.out.println("Servidor iniciado e BankService registrado no RMI Registry...");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
