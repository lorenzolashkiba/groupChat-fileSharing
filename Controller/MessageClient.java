package Controller;

import Model.Model;

import java.util.ArrayList;

public class MessageClient {
    private ConnectionHandler connectionHandler;
    private Model model;
    public static ArrayList<ConnectionHandler> connectionHandlers;
    public MessageClient(ConnectionHandler connectionHandlers, Model model) {
        this.connectionHandler = connectionHandlers;
    }


    public boolean sendTextMessageBroadC(String text,String clientUsername){
        for (ConnectionHandler clientHandler : connectionHandlers = model.getConnectionHandlers()) {
            try{
                if(!clientHandler.getClientUsername().equals(clientUsername)){
                    clientHandler.out.println(text);
                    clientHandler.out.flush();
                }
            }catch (Exception e){
                clientHandler.closeSocket();
            }
        }
        return true;
    }
    public boolean sendTextMessageFromClient(String text,String clientUsername){
        for (ConnectionHandler clientHandler : connectionHandlers = model.getConnectionHandlers()) {
            try{
                if(!clientHandler.getClientUsername().equals(clientUsername)){
                    clientHandler.out.println(text);
                    clientHandler.out.flush();
                }
            }catch (Exception e){
                clientHandler.closeSocket();
            }
        }
        return true;
    }

}
