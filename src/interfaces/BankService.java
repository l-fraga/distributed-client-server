package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankService extends Remote {
    void openAccount(String accountId) throws RemoteException;
    double consultBalance(String accountId) throws RemoteException;
    void deposit(String accountId, double amount, String transactionId) throws RemoteException;
    void withdraw(String accountId, double amount, String transactionId) throws RemoteException;
    void closeAccount(String accountId) throws RemoteException;
    void cleanAccounts() throws RemoteException;
}
