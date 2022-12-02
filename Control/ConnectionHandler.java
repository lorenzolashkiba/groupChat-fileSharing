package Control;

import Model.ConnectionList;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private Socket socketClient;
    public BufferedReader in;
    public PrintWriter out;
    private String clientUsername;
    private Message message;
    private ConnectionList list;

    public ConnectionHandler(Socket socket, ConnectionList list){
        this.socketClient = socket;
        try {
            out = new PrintWriter(this.socketClient.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            list.addConnectionHandlers(this);
            message = new Message(this,list);
            clientUsername = in.readLine();
            message.sendTextMessageBroadC("nickname : "+clientUsername,clientUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        while(socketClient.isConnected()){
            try {
                //out.println("1: "+clientUsername);
                String text = in.readLine();
                text = clientUsername+text;
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
        closeEverything(socketClient,out,in);
    }
    public void removeClientHandler(){
        list.removeClienthandler(this);
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
