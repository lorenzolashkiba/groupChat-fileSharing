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

    public void addConnectionHandlers(ConnectionHandler connectionHandler) {
        connectionHandlers.add(connectionHandler);
    }

    public void removeClienthandler(ConnectionHandler connectionHandler) {
        connectionHandlers.remove(connectionHandler);
    }
    public ArrayList<ConnectionHandler> getHandlers(){
        return  connectionHandlers;
    }

}
