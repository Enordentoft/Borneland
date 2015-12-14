package rmi.common;



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote
{
    public void response(String text) throws RemoteException; 
}
