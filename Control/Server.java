package Control;

import Model.ConnectionList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private ConnectionList model;
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        model = new ConnectionList();

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
                ConnectionHandler handler = new ConnectionHandler(socket,model);
                handler.run();

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
