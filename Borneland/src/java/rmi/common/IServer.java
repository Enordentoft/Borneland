package rmi.common;



import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IServer extends Remote
{
    public void login(IClient client) throws RemoteException;
    public void logout(IClient client) throws RemoteException;
    public void Create(String fName,String lName,String ageGroupID,String email,String laneID) throws RemoteException;
    public void Edit() throws RemoteException;
    public void Delete() throws RemoteException;
}
