package Execute;

import java.io.IOException;
import java.net.ServerSocket;

import Control.Server;

public class RunServer {

	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3645);
			Server server = new Server(serverSocket);
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
