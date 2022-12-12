package Execute;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Control.Client;
import View.Window;

public class RunClient {

	public static void main(String[] args) {
		Socket socket = null;
		boolean scanning = true;
		
		while(scanning) {
		try {
			socket = new Socket("127.0.0.1", 3636);
			scanning = false;
			} catch (Exception e) {	
		    System.out.println("Client waiting for server...");
		    try {
	            Thread.sleep(2000);//2 seconds
	        } catch(InterruptedException ie){
	            ie.printStackTrace();
	        }
			} 
		}
		
		Window frame = new Window();
		System.out.println("WTF");
		Client client = new Client(frame, socket);
		client.listenForMessage();
	}

}
