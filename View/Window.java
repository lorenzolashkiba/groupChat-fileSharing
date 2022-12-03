package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.ModuleLayer.Controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Control.Client;

public class Window extends JFrame implements WindowListener{

	private PannelloChat pannelloClient;
	private String username;
	public PannelloChat getPannelloClient() {
		return pannelloClient;
	}

	public Window() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 800);
		pannelloClient = new PannelloChat();
		setContentPane(pannelloClient);
		addWindowListener(this);
		this.setVisible(true);
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

	@Override
	public void windowOpened(WindowEvent e) {
		username = JOptionPane.showInputDialog(getParent(),
                "Enter a username: ", "guest");

		pannelloClient.setToolBarText("User:"+username);
		//TODO: mettere in chat il messaggio che il client si � collegato
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		int control=JOptionPane.showConfirmDialog(this, "Do you want to leave the session?", "Confirm", JOptionPane.YES_NO_OPTION);
		if(control==JOptionPane.YES_OPTION){
			System.exit(0);
			//TODO: scollegare il client e mettere in chat il messaggio che si � collegato
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
