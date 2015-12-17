/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server;

import dbhandler.DBHandler;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.common.IClient;
import rmi.common.IServer;

/**
 *
 * @author Michael
 */
public class ServerHandler extends UnicastRemoteObject implements IServer {
    DBHandler db;
   

    private Vector<IClient> clients;

    public ServerHandler() throws RemoteException {
        clients = new Vector<>();
        db = new DBHandler();
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

    /**
     * Create participant using the DBHandler
     * 
     * @param fName
     * @param lName
     * @param ageGroupID
     * @param email
     * @param laneID
     * @param client
     * @throws RemoteException 
     */
    @Override
    public void Create(String fName, String lName, String ageGroupID, String email, String laneID, IClient client) throws RemoteException {
        try {           
            ResultSet rs = db.createParticipant(fName, lName, ageGroupID, email, laneID); 
            while(rs.next()){
                client.response("Participants Number: "+rs.getString(1)+ " Colour: " +rs.getString(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        // next list will be updated from the database.
        db.resetUpdateTimer();
        
               
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
