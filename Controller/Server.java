package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    @Override
    public void run() {

        try {
            ServerSocket server = new ServerSocket(5555);
            Socket client = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
