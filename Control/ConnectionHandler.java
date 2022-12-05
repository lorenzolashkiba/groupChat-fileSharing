package Control;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
    public static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();
    private Socket socketClient;
    public BufferedReader Reader;
    public BufferedWriter Writer;
    private String clientUsername;


    public ConnectionHandler(Socket socket){
        try {
        	this.socketClient = socket;
            Writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            Reader = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            System.out.println("Connesione richiesta da: "+ socketClient.getInetAddress().toString()+":"+socketClient.getPort());
            this.clientUsername = Reader.readLine();
            System.out.println(clientUsername);
            connectionHandlers.add(this);
            sendTextMessageBroadC("has joined the chat!");
        } catch (IOException e) {
            closeEverything(socket, Writer, Reader);
        }
    }
    
    
    // this function receives all the messages sent from the client
    @Override
    public void run() {
    	String msgFromClient;
        while(socketClient.isConnected()){
         try {
        	System.out.println("WAIT");
			msgFromClient = Reader.readLine(); //operazione bloccante
			System.out.println("FATTO");
			if(msgFromClient != null) {
				if(msgFromClient.startsWith("/nick")) {
					String oldUsername = clientUsername;
					clientUsername = msgFromClient.substring(6);
					sendTextMessageBroadC("SERVER:: "+ oldUsername + " has changed his name to " + clientUsername);	
				}else if(msgFromClient.startsWith("/quit")){
					closeEverything(socketClient, Writer, Reader);
					break;
				}
				else {
					sendTextMessageBroadC(msgFromClient);				
				}
			}else {
				closeEverything(socketClient, Writer, Reader);
				break;
			}
			
		} catch (Exception e) {
			closeEverything(socketClient, Writer, Reader);
			break;
		}
        }
    }
    
    
    
    public boolean sendTextMessageBroadC(String messageToSend){

        //connectionHandler.out.println(text);
        for (ConnectionHandler cH : connectionHandlers) {
         try {
        	 
        	 if(messageToSend.startsWith("SERVER")) {
        		cH.Writer.write(messageToSend);
 				cH.Writer.newLine();
 				cH.Writer.flush(); 
        	 }else {
        		 if(!cH.clientUsername.equals(clientUsername)) {
        			 cH.Writer.write(clientUsername + ": " + messageToSend);
        			 cH.Writer.newLine(); // perchè readline aspetta che venga inviata una nuova linea
        			 cH.Writer.flush(); // riempie il buffer
        		 }else {
        			 cH.Writer.write("You" + ": " + messageToSend);
        			 cH.Writer.newLine(); 
        			 cH.Writer.flush(); 
        		 }
        		 
        	 }
		} catch (Exception e) {
			closeEverything(socketClient, Writer, Reader);
		}
        }
        return true;
    }
    


    public String getClientUsername() {
        return clientUsername;
    }
   
    public void removeConnectionHandler() {
    	connectionHandlers.remove(this);
    	sendTextMessageBroadC("/SERVER: " + clientUsername + ": has left the chat :(");
    }
    
   
    public void closeEverything(Socket socket,BufferedWriter write, BufferedReader read ){
    	removeConnectionHandler();
        try {
            if (read != null) {
                read.close();
            }
            if (write != null) {
                write.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString(){
        return clientUsername;
    }
}
