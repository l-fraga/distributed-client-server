package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankService extends Remote {
    void openAccount(String accountID) throws RemoteException;
    void closeAccount(String accountID) throws RemoteException;
    double consultBalance(String accountID) throws RemoteException;
    void deposit(String accountID, double amount) throws RemoteException;
    void withdraw(String accountId, double amount) throws RemoteException;
}
