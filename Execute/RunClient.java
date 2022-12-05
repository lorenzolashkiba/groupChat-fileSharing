package Execute;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Control.Client;
import View.Window;

public class RunClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window frame = new Window();
		System.out.println("ciao");
		try {
			Socket socket = new Socket("localhost", 3645);
			Client client = new Client(frame, socket );
			client.listenForMessage();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}