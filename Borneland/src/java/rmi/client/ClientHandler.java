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
    
    public ClientHandler(ClientGUI gui) throws RemoteException{
        this.gui = gui;        
    }

    @Override
    public void response(String text) throws RemoteException {
        JTextArea guiText = gui.getTextArea();
        guiText.append(text+"\n");
    }
    
}
