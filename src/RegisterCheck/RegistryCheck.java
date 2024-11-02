package RegisterCheck;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryCheck {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            String[] boundNames = registry.list();
            System.out.println("Serviços registrados no RMI Registry:");
            for (String name : boundNames) {
                System.out.println(name);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar serviços no RMI Registry: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
