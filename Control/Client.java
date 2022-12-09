package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import Model.Message;
import View.Window;

public class Client implements ActionListener,Runnable{

	private Window frame;
	private Socket socket;
	private ObjectOutputStream clientOutputStream;
	private ObjectInputStream clientInputStream;
	private String startUsername;

	public Client(Window _frame, Socket socket) { 
		this.frame = _frame;
		this.frame.setClient(this);
		try {
			this.socket = socket;
			clientOutputStream = new ObjectOutputStream(socket.getOutputStream());
			clientInputStream = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e){
			closeEverything(this.socket, clientOutputStream, clientInputStream);
		}
		this.frame.AddListeners(this);

	}

	//TODO: controllo che lo user non esista gia
	public void setUsername(String username) {
		this.startUsername = username;
		try {
			Message message = new Message(username,"");
			clientOutputStream.writeObject(message);
			clientOutputStream.flush();
		} catch (Exception e) {
			closeEverything(socket, clientOutputStream, clientInputStream);
		}
	}

	public void closeEverything(Socket socket, ObjectOutputStream writer, ObjectInputStream in ){
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
			Message message = new Message(startUsername,messageToSend);
			clientOutputStream.writeObject(message);
			clientOutputStream.flush();

		}catch(IOException e){
			closeEverything(socket,clientOutputStream,clientInputStream);
		}
	}

	/*
	public boolean sendFile(String path){
		try {
			int bytes = 0;
			File file = new File(path);
			FileInputStream fileInputStream = new FileInputStream(file);
			WriterD.writeLong(file.length());

			//get name and save it in bytes
			String filename = file.getName();
			byte[] fileNameBytes = filename.getBytes();

			//contains all the file content but it needs to be declared with the size of the content
			byte[] fileContentBytes = new byte[(int) file.length()];
			//this obj reads the file and save it in the fileContentBytes
			fileInputStream.read(fileContentBytes);
			System.out.println(fileContentBytes);

			//every time a new object will be sent it starts with sending the size and then wht whole content
			//send the name of the file first
			WriterD.writeInt(fileNameBytes.length);
			WriterD.write(fileNameBytes);

			//send the content of the file
			WriterD.writeInt(fileContentBytes.length);
			WriterD.write(fileContentBytes);


		//	byte[] buffer = new byte[4*1024];
		//	while ((bytes=fileInputStream.read(buffer))!=-1){
		//		WriterD.write(buffer,0,bytes);
		//		WriterD.flush();
		//	}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
*/

	public void listenForMessage() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String msgFromBroadcast;
				
				while(socket.isConnected()) {
					try {
						Message message = (Message) clientInputStream.readObject();
						System.out.println(message.getText());
						frame.getPannelloClient().addMessageFromServer(message.getUsername(), message.getText()); //poi passando l'oggeto message riusciamo a capire a se � fromServer o Client
					} catch (Exception e) {
						closeEverything(socket, clientOutputStream, clientInputStream);
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
			String filePath = frame.openFileSelecterDialog();
			/*if(sendFile(filePath)){
				System.out.println("Aggiunto correttamente");
			}else
				System.out.println("Errore aggiungione file");
			System.out.println(filePath);
			*/
			//TODO: aprire nuova finestra dove scegliere l'imm
			// agine da mandare e visualizzarla
			//TODO: gestire l'invio dell'immagine al server
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}




}
