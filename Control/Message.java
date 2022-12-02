package Control;

import Model.ConnectionList;

import java.util.ArrayList;

public class Message {
    private ConnectionHandler connectionHandler;
    private ConnectionList model;
    public static ArrayList<ConnectionHandler> connectionHandlers;
    public Message(ConnectionHandler connectionHandlers, ConnectionList model) {
        this.connectionHandler = connectionHandlers;
    }


    public boolean sendTextMessageBroadC(String text,String clientUsername){
        for (ConnectionHandler clientHandler : connectionHandlers = model.getConnectionHandlers()) {
            try{
                if(!clientHandler.getClientUsername().equals(clientUsername)){
                    clientHandler.out.println(text);
                }
            }catch (Exception e){
                //clientHandler.closeSocket();
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
