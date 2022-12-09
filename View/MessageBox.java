package View;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextArea;

public class MessageBox extends JPanel {

	private String username;
	private String message;
	//img
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public MessageBox(String _username, String _message) {
		this.setSize(180, 80);
		setBackground(Color.GREEN);
		setLayout(new MigLayout("", "[grow,sizegroup main]", "[20px:30px:50px,grow][grow,fill]"));
		this.username = _username;
		this.message = _message;
		
		JLabel labelUsername = new JLabel(username);
		labelUsername.setForeground(Color.BLACK);
		labelUsername.setBackground(Color.BLACK);
		add(labelUsername, "cell 0 0");
		
		JTextArea txtArea = new JTextArea();
		txtArea.setSize(175, 60);
		txtArea.setText(message);
		txtArea.setForeground(Color.BLACK);
		txtArea.setBackground(Color.GREEN);
		txtArea.setLineWrap(true);
		add(txtArea, "cell 0 1");
		
		
	}

	
	

	
}
