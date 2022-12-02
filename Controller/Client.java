package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public Client(Socket socket,String username){
        try {
            this.socket = socket;
            out = new PrintWriter(this.socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.username = username;
        }catch(IOException e){
            closeEverything(socket,out,in);
        }
    }

    public void sendMessage(){
        try{
            //first message to send is the username
            out.println(username);
            out.flush();

            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                System.out.println(":");
                String messageToSend = scanner.nextLine();
                out.println(username+": "+messageToSend);
                out.flush();
            }
        }catch(Exception e){
            closeEverything(socket,out,in);
        }
    }
    public void listenMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String  msgGroupChat;

                while(socket.isConnected()){
                    try{
                        System.out.println(":");
                        msgGroupChat = in.readLine();
                        System.out.println("-"+msgGroupChat);
                    }catch(IOException e){
                        closeEverything(socket,out,in);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket,PrintWriter out, BufferedReader in ){
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

    public static void main(String args[]) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost",5555);
        System.out.print("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Client client = new Client(socket,username);
        client.sendMessage();
        client.listenMessage();

    }
}
