/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import rmi.common.IClient;
import rmi.common.IServer;

/**
 *
 * @author Michael
 */
public class ServerHandler extends UnicastRemoteObject implements IServer {

    private Vector<IClient> clients;

    public ServerHandler() throws RemoteException {
        clients = new Vector<>();
    }

    @Override
    public void login(IClient client) throws RemoteException {
        client.response("Welcome to the admin Server!");
        clients.add(client);
    }

    @Override
    public void logout(IClient client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Create(String fName, String lName, String ageGroupID, String email, String laneID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
