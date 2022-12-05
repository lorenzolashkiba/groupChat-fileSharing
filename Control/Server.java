package Control;

import Model.ConnectionList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private ConnectionList connectionList;
    private MessageHandler messageHandler;
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        connectionList = new ConnectionList();
        messageHandler = new MessageHandler(connectionList);
    }
    @Override
    public void run() {
        System.out.println("SERVER START .... ");
        while(!serverSocket.isClosed()) {
            try {
                //socket bloccante
                System.out.println("waiting");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ConnectionHandler handler = new ConnectionHandler(socket,messageHandler);
                handler.run();
                //non so il perche ma non arriva qui ma dovrebbe, non stampa finished
                //bruh com'è possibile?
                System.out.println("finished");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeServerSocket(){
        try{
            if(!serverSocket.isClosed()){
                serverSocket.close();
            }
        }catch(IOException e ){
            e.printStackTrace();
        }

    }
}
