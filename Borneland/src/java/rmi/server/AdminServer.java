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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import rmi.common.IServer;

/**
 *
 * @author Michael
 */
public class AdminServer {

    public static void main(String[] args) {
        AdminServer server = new AdminServer();
        server.createConnection();
    }

    public void createConnection() {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099); //Create a registry, this is the first server that starts up
            System.setSecurityManager(new SecurityManager());
            //Context namingContext = new InitialContext();
            IServer server = new ServerHandler();
            registry.rebind("rmi://localhost/adminServer", server); //Bind the server to the registry

        } catch (AccessException ex) {
            Logger.getLogger(AdminServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AdminServer.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
