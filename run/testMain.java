package run;

import Control.Client;
import Control.Server;
import View.Window;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class testMain {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		System.out.println("start server");
		try {
			ServerSocket serverSocket = new ServerSocket(5556,5);
			Server server = new Server(serverSocket);
			server.run();
		}catch (Exception e){
			System.out.println("server:"+e);

		}
	}

}
