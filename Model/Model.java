package Model;

import Controller.ConnectionHandler;

import java.util.ArrayList;

public class Model {

    public static ArrayList<ConnectionHandler> connectionHandlers;

    public Model(){
        connectionHandlers = new ArrayList<>();
    }

    public static ArrayList<ConnectionHandler> getConnectionHandlers() {
        return connectionHandlers;
    }
    public void addConnectionHandlers(ConnectionHandler connectionHandler) {
        connectionHandlers.add(connectionHandler);
    }
    public void removeClienthandler(ConnectionHandler connectionHandler){
        connectionHandlers.remove(connectionHandler);
    }
}
