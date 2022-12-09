package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import View.Window;

public class Client implements ActionListener,Runnable{

	private Window frame;
	private Socket socket;
	private BufferedReader Reader;
	private BufferedWriter Writer;
	private String startUsername;

	public Client(Window _frame, Socket socket) { 
		this.frame = _frame;
		try {
			this.socket = socket;
			Writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			Reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e){
			closeEverything(socket,Writer,Reader);
		}
		this.frame.setClient(this);
		this.frame.AddListeners(this);

	}

	//TODO: controllo che lo user non esista gia
	public void setUsername(String username) {
		this.startUsername = username;
		try {
			Writer.write(username);
			Writer.newLine();
			Writer.flush();
		} catch (IOException e) {
			closeEverything(socket,Writer,Reader);
		}
		

	}

	public void closeEverything(Socket socket, BufferedWriter writer, BufferedReader in ){
		try {
			if (in != null) {
				in.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String messageToSend){
		try{
			
			Writer.write(messageToSend);
			Writer.newLine();
			Writer.flush();
			
		}catch(IOException e){
			closeEverything(socket,Writer,Reader);
		}
	}

	public void listenForMessage() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String msgFromBroadcast;
				
				while(socket.isConnected()) {
					try {
						msgFromBroadcast = Reader.readLine();
						System.out.println(msgFromBroadcast);
						frame.getPannelloClient().addMessageFromServer("username", msgFromBroadcast); //poi passando l'oggeto message riusciamo a capire a se è fromServer o Client
					} catch (Exception e) {
						closeEverything(socket, Writer, Reader);
						break;
					}
				}
				
			};
		}).start();
		
	}
	
	
	
	
	// this function runs only when clicked the send button
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getPannelloClient().getSendMessageBtn()) {
			String msgToSend= this.frame.getPannelloClient().getMessageField().getText();
			sendMessage(msgToSend);
			frame.getPannelloClient().addMessageFromClient("You", msgToSend);
			if(msgToSend.startsWith("/quit")) {
				System.exit(0);
			}
			
			this.frame.getPannelloClient().clearMessageField();
			
		}else if(e.getSource() == frame.getPannelloClient().getSendImgBtn()) {
			//TODO: aprire nuova finestra dove scegliere l'immagine da mandare e visualizzarla
			//TODO: gestire l'invio dell'immagine al server
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}




}
