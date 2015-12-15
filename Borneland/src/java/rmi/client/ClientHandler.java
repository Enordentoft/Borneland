/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JTextArea;
import rmi.common.IClient;

/**
 *
 * @author Michael
 */
public class ClientHandler extends UnicastRemoteObject implements IClient{
    
    ClientGUI gui;
    JTextArea guiText;
    
    public ClientHandler(ClientGUI gui) throws RemoteException{
        this.gui = gui;    
        guiText = gui.getTextArea();
    }

    @Override
    public void response(String text) throws RemoteException {        
        guiText.append(text+"\n");        
    }
    
    
    
    
}
