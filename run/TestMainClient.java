package run;

import Control.Client;
import Control.Server;
import View.Window;

import java.net.ServerSocket;
import java.net.Socket;

public class TestMainClient {

        public static void main(String[] args)  {
            // TODO Auto-generated method stub
            System.out.println("start Client");
            Window frame = new Window();
            try {
                System.out.println("1");
                Socket socketClient = new Socket("localhost", 5556);
                System.out.println("2");
                Client client = new Client(frame, socketClient);
                Thread ct = new Thread(client);
                ct.start();
            }catch (Exception e){
                System.out.println("client"+e);
            }
        }


}
