package Control;

import Model.ConnectionList;

import java.util.ArrayList;

public class MessageHandler {
    public ConnectionHandler connectionHandler;
    private ConnectionList connectionList;
    public MessageHandler(ConnectionList connectionList) {
     this.connectionList = connectionList;
    }

    public void addConnectionHandler(ConnectionHandler connectionHandler){
        connectionList.addConnectionHandler(connectionHandler);
    }
    public boolean sendTextMessageBroadC(String text,ConnectionHandler connectionHandler){
    	
    	//connectionHandler.out.println(text);
        ArrayList<ConnectionHandler> connectionHandlers;
        for (ConnectionHandler clientHandler : connectionHandlers = connectionList.getConnectionHandlers()) {
          // stampa di tutti i client salvati dentro l'array
            //
            System.out.println(clientHandler.toString());
            try{
               // if(!clientHandler.getClientUsername().equals(connectionHandler.getClientUsername())){
                    clientHandler.out.println(text);
               // }
            }catch (Exception e){
                clientHandler.out.println("quit");
                clientHandler.closeSocket();
            }
        }
        return true;
    }
    public void removeClient(ConnectionHandler con){
        connectionList.removeClienthandler(con);
    }
    public boolean sendTextMessageFromClient(String text,String clientUsername){
        ArrayList<ConnectionHandler> connectionHandlers;
        for (ConnectionHandler clientHandler : connectionHandlers = connectionList.getConnectionHandlers()) {
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
