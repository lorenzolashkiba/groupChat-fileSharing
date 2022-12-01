package Controller;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionHandler(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("Enter your nickname");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
