package Controller;

import Model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Model model;
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        model = new Model();

    }
    @Override
    public void run() {
        System.out.println("SERVER START .... ");
        while(!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("New client connected");
                ConnectionHandler handler = new ConnectionHandler(client,model);
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
