package Controller;

import Model.Model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
    private Socket client;
    public BufferedReader in;
    public PrintWriter out;
    private String clientUsername;
    private Message message;
    private Model model;
    public ConnectionHandler(Socket client,Model model){
        this.client = client;
        try {
            out = new PrintWriter(this.client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));


            model.addConnectionHandlers(this);
            message = new Message(this,model);
            clientUsername = in.readLine();
            message.sendTextMessageBroadC("nickname : "+clientUsername,clientUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        while(client.isConnected()){
            try {
                //out.println("1: "+clientUsername);
                String text = in.readLine();
                System.out.println(text);
                message.sendTextMessageBroadC(text,clientUsername);

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
        closeEverything(client,out,in);
    }
    public void removeClientHandler(){
        model.removeClienthandler(this);
        message.sendTextMessageBroadC("SERVER:"+clientUsername+" has left the chat!",clientUsername);
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
}
