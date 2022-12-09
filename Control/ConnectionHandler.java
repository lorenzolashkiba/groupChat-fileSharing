package Control;


import Model.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
    public static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();
    private Socket socketClient;
    public BufferedReader Reader;
    public BufferedWriter Writer;
    private DataInputStream ReaderD;
    private DataOutputStream WriterD;
    private String clientUsername;
    private  ObjectInputStream serverInputStream;
    private  ObjectOutputStream serverOutputStream;
    public ConnectionHandler(Socket socket){
        try {
        	this.socketClient = socket;
            Writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            Reader = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            ReaderD = new DataInputStream(socket.getInputStream());
            WriterD = new DataOutputStream(socket.getOutputStream());
            serverInputStream = new ObjectInputStream(socket.getInputStream());
            serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connesione richiesta da: "+ socketClient.getInetAddress().toString()+":"+socketClient.getPort());
            Message message = (Message) serverInputStream.readObject();
            this.clientUsername = message.getUsername();
            //this.clientUsername = Reader.readLine();
            //System.out.println(clientUsername);
            connectionHandlers.add(this);
            sendTextMessageBroadC(new Message(clientUsername,"has joined the chat!"));
        } catch (Exception e) {
            closeEverything(socketClient, serverOutputStream, serverInputStream);
        }
    }
    
    
    // this function receives all the messages sent from the client
    @Override
    public void run() {
    	String msgFromClient;
        Boolean sendingFile = false;
        while(socketClient.isConnected()){
         try {
        	System.out.println("WAIT");
             Message message = (Message) serverInputStream.readObject(); //operazione bloccante
             msgFromClient = message.getText();
			System.out.println("FATTO");
			if(msgFromClient != null) {

				if(msgFromClient.startsWith("/nick")) {
					String oldUsername = clientUsername;
					clientUsername = msgFromClient.substring(6);
					sendTextMessageBroadC(new Message(clientUsername,"SERVER:: "+ oldUsername + " has changed his name to " + clientUsername));
				}else if(msgFromClient.startsWith("/quit")){
                    closeEverything(socketClient, serverOutputStream, serverInputStream);
					break;
				}
				else if(msgFromClient.endsWith(".png")||msgFromClient.endsWith(".jpg")||msgFromClient.endsWith(".txt")) {
							receiveFile(msgFromClient);
                            sendingFile = true;
				}else{
                    sendTextMessageBroadC(new Message(clientUsername,msgFromClient));
                }
			}else {
                closeEverything(socketClient, serverOutputStream, serverInputStream);
				break;
			}
			
		} catch (Exception e) {
             closeEverything(socketClient, serverOutputStream, serverInputStream);
			break;
		}
        }
    }
    
    public void receiveFile(String fileName){
        try {
            int bytes = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        }catch (Exception e){
            System.out.println("errore receving file");
        }
    }
    
    public void sendTextMessageBroadC(Message message){
        String messageToSend = message.getText();
        //connectionHandler.out.println(text);
        for (ConnectionHandler cH : connectionHandlers) {
         try {
        	 
        	 if(messageToSend.startsWith("SERVER")) {
        		cH.serverOutputStream.writeObject(new Message(cH.getClientUsername(),messageToSend));
 				cH.Writer.newLine();
 				cH.Writer.flush(); 
        	 }else {
        		 if(!cH.clientUsername.equals(clientUsername)) {
        			 cH.Writer.write(clientUsername + ": " + messageToSend);
        			 cH.Writer.newLine(); // perch� readline aspetta che venga inviata una nuova linea
        			 cH.Writer.flush(); // riempie il buffer
        		 }else {
        			 cH.Writer.write("You" + ": " + messageToSend);
        			 cH.Writer.newLine(); 
        			 cH.Writer.flush(); 
        		 }
        		 
        	 }
		} catch (Exception e) {
             closeEverything(socketClient, serverOutputStream, serverInputStream);
		}
        }
    }
    


    public String getClientUsername() {
        return clientUsername;
    }
   
    public void removeConnectionHandler() {
    	connectionHandlers.remove(this);
    	sendTextMessageBroadC(new Message(clientUsername,"/SERVER: " + clientUsername + ": has left the chat :("));
    }


    public void closeEverything(Socket socket, ObjectOutputStream writer, ObjectInputStream in ){
        removeConnectionHandler();
        try {
            if (in != null) {
                in.close();
            }
            if (writer != null) {
                writer.close();
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
