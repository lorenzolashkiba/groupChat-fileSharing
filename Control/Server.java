package Control;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    
    public void startServer() {
        System.out.println("SERVER START .... ");
        while(!serverSocket.isClosed()) {
            try {
                //socket bloccante
                System.out.println("waiting");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");


                ConnectionHandler handler = new ConnectionHandler(socket);
                Thread th = new Thread(handler);
                th.start();
             
                System.out.println("finished");
                
            } catch (IOException e) {
               this.closeServerSocket();
            }
        }
    }

    public void closeServerSocket(){
        try{
        	if(serverSocket != null) {
        		serverSocket.close();
        	}
        }catch(IOException e ){
            e.printStackTrace();
        }

    }
    
  
}
