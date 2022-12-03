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
		this.username = this.frame.getUsername();
		this.frame.AddListeners(this);

	}
	public void closeEverything(Socket socket,PrintWriter out, BufferedReader in ){
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

	@Override
	public void run() {
		String  msgGroupChat;
		out.println(username);

		while(socket.isConnected()){
			try{
				System.out.println(":");
				msgGroupChat = in.readLine();
				System.out.println("-"+msgGroupChat);
			}catch(IOException e){
				closeEverything(socket,out,in);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getPannelloClient().getSendMessageBtn()) {
			System.out.println(this.frame.getPannelloClient().getMessageField().getText());
			this.frame.getPannelloClient().clearMessageField();
			String messaggio = this.frame.getPannelloClient().getMessageField().getText();
			System.out.println(messaggio);

			//TODO: mando il messaggio al server
			sendMessage(messaggio);
			//TODO: il server lo manda agli altri client in FORMATO --> nomeUtente: messaggio --> se il nomeUtente � == a quello del client dove lo invia si scrive You
		}else if(e.getSource() == frame.getPannelloClient().getSendImgBtn()) {
			//TODO: aprire nuova finestra dove scegliere l'immagine da mandare e visualizzarla
			//TODO: gestire l'invio dell'immagine al server
		}
	}


}
