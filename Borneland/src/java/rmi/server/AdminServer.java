/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import rmi.common.IServer;

/**
 *
 * @author Michael
 */
public class AdminServer {

    public static void main(String[] args){
        AdminServer server = new AdminServer();
        server.setup();
    }

    /**
     * setup server
     */
    public void setup(){
        try {
            //Create a registry
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099); 
            System.setSecurityManager(new SecurityManager());            
            IServer server = new ServerHandler();
            //Bind the server to the registry
            registry.rebind("rmi://localhost/adminServer", server); 

        } catch (AccessException ex) {
            Logger.getLogger(AdminServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AdminServer.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
