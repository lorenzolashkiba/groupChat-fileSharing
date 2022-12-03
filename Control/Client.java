package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import View.Window;

public class Client implements ActionListener,Runnable{

	private Window frame;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String username;

	public Client(Window _frame, Socket socket) { // poi gli passo anche il model qui, perch� viene gestito dal client
		this.frame = _frame;
		try {
			this.socket = socket;
			out = new PrintWriter(this.socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		}catch(IOException e){
			closeEverything(socket,out,in);
		}
		this.frame.setClient(this);
		System.out.println("username:"+this.username);
		this.frame.AddListeners(this);

	}

	public void setUsername(String username) {
		this.username = username;

	}

	public void closeEverything(Socket socket, PrintWriter out, BufferedReader in ){
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
	public void sendMessage(String messageToSend){
		try{
			//first message to send is the username
			out.println(messageToSend);

		}catch(Exception e){
			closeEverything(socket,out,in);
		}
	}

	// this function receive msg from connection handler and update the text area
	@Override
	public void run() {
		String  msgGroupChat;
		boolean flag=true;
		while(socket.isConnected()){
			try{
				if(username==null) {
					Thread.sleep(500);
				}else {
					if(flag){
						out.println(username);
						flag=false;
					}

					msgGroupChat = in.readLine();
					if(msgGroupChat!=null) {
						System.out.println("mgg:"+msgGroupChat);
						frame.getPannelloClient().getChatArea().append(msgGroupChat);
					}
				}
			}catch(IOException | InterruptedException e){
				closeEverything(socket,out,in);
			}
		}
	}
	// this function runs only when clicked the send button
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getPannelloClient().getSendMessageBtn()) {

			String messaggio = this.frame.getPannelloClient().getMessageField().getText();
			System.out.println("messaggio da mandare:"+messaggio);
			//TODO: mando il messaggio al server
			sendMessage(messaggio);
			this.frame.getPannelloClient().clearMessageField();
			//TODO: il server lo manda agli altri client in FORMATO --> nomeUtente: messaggio --> se il nomeUtente � == a quello del client dove lo invia si scrive You
		}else if(e.getSource() == frame.getPannelloClient().getSendImgBtn()) {
			//TODO: aprire nuova finestra dove scegliere l'immagine da mandare e visualizzarla
			//TODO: gestire l'invio dell'immagine al server
		}
	}


}
