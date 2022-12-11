package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Control.Client;

public class Window extends JFrame implements WindowListener{

	private PannelloChat pannelloClient;
	private String username;
	public PannelloChat getPannelloClient() {
		return pannelloClient;
	}
	private Client client;
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 800);
		pannelloClient = new PannelloChat(this);
		setContentPane(pannelloClient);
		addWindowListener(this);
		this.setVisible(true);
	}
	public void setClient(Client client){
		this.client = client;
	}
	public String SendMessage() {
		String str = pannelloClient.getMessageField().getText();
		return str;
	}
	

	public void AddListeners(Client c) {
		pannelloClient.getSendMessageBtn().addActionListener(c);
		pannelloClient.getSendImgBtn().addActionListener(c);
	}

	
	public String getUsername(){
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String openFileSelecterDialog(){
		JFileChooser jFileChooser = new JFileChooser();
		int response = jFileChooser.showOpenDialog(null);
		if(response==JFileChooser.APPROVE_OPTION){
			File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
			return jFileChooser.getSelectedFile().getAbsolutePath();
		}


		return null;
	}
	@Override
	public void windowOpened(WindowEvent e) {
		int max = 999;
		int min = 10;
		username = "Guest" + ((int)(Math.random() * max - min + 1) + min) + (int) java.time.LocalTime.now().getSecond();
		pannelloClient.getUsernameLabel().setText("You are: "+username);
		client.setUsername(username);
		
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int control=JOptionPane.showConfirmDialog(this, "Do you want to leave the session?", "Confirm", JOptionPane.YES_NO_OPTION);
		if(control==JOptionPane.YES_OPTION){
			client.sendMessage("/quit");
			System.exit(0);
		}		
		else{
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}
	

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	


}
