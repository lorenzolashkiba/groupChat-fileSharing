package Model;


import Control.ConnectionHandler;

import java.util.ArrayList;

public class ConnectionList {

    public static ArrayList<ConnectionHandler> connectionHandlers;

    public ConnectionList() {
        connectionHandlers = new ArrayList<>();
    }

    public static ArrayList<ConnectionHandler> getConnectionHandlers() {
        return connectionHandlers;
    }

    public void addConnectionHandler(ConnectionHandler connectionHandler) {
        connectionHandlers.add(connectionHandler);
    }

    public void removeClienthandler(ConnectionHandler connectionHandler) {
        connectionHandlers.remove(connectionHandler);
    }

    public ConnectionHandler getHandler(int x){
        try {
            ConnectionHandler con = connectionHandlers.get(x);
            return con;
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }

}
