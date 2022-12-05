package Control;

import Model.ConnectionList;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private Socket socketClient;
    public BufferedReader in;
    public PrintWriter out;
    private String clientUsername;
    private MessageHandler messageHandler;

    public ConnectionHandler(Socket socket,MessageHandler messageHandler){
        this.socketClient = socket;
        try {
            out = new PrintWriter(this.socketClient.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            //problema salva solo un connectino handler nella lista
            this.messageHandler = messageHandler;
            this.messageHandler.addConnectionHandler(this);
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this function receives all the messages sent from the client
    @Override
    public void run() {
        boolean flag=true;
        while(socketClient.isConnected()){
            try {
                //out.println("1: "+clientUsername);
                String text = in.readLine();
                if(text!=null&&flag){
                    clientUsername = text;
                    flag=false;
                }else {
                    if(text.equals("/quit")){
                        removeClientHandler();
                        closeSocket();
                        break;
                    }
                    text = clientUsername + ":" + text;
                    messageHandler.sendTextMessageBroadC(text,this);
                }

            } catch (IOException e) {
                closeSocket();
                break;
            }
        }
    }

    public String getClientUsername() {
        return clientUsername;
    }
    public void closeSocket(){
        closeEverything(socketClient,out,in);
    }
    public void removeClientHandler(){
        messageHandler.removeClient(this);
        messageHandler.sendTextMessageBroadC("SERVER:"+clientUsername+" has left the chat!",this);
    }
    public void closeEverything(Socket socket,PrintWriter out, BufferedReader in ){
        removeClientHandler();
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public String toString(){
        return clientUsername;
    }
}
