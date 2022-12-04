package View;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollBar;
import javax.swing.JLabel;

public class PannelloChat extends JPanel {
	private JTextField messageField;
	private JTextArea chatArea;
	private JButton sendMessageBtn;
	private JButton sendImgBtn;
	private JLabel UsernameLabel;

	public JTextField getMessageField() {
		return messageField;
	}

	public void setMessageField(JTextField textField) {
		this.messageField = textField;
	}



	public JLabel getUsernameLabel() {
		return UsernameLabel;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	

	public JButton getSendMessageBtn() {
		return sendMessageBtn;
	}

	public JButton getSendImgBtn() {
		return sendImgBtn;
	}

	
	public void clearMessageField() {
		messageField.setText("");
	}
	/**
	 * Create the panel.
	 */
	public PannelloChat() {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String []fontFamilies = ge.getAvailableFontFamilyNames();
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\marcc\\AppData\\Local\\Microsoft\\Windows\\Fonts\\hacka__.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		chatArea = new JTextArea();
		chatArea.setForeground(Color.GREEN);
		chatArea.setBackground(Color.BLACK);
		chatArea.setLineWrap(true);
		chatArea.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		add(chatArea, BorderLayout.CENTER);
		
		JPanel SendMessagePane = new JPanel();
		SendMessagePane.setBackground(Color.BLACK);
		add(SendMessagePane, BorderLayout.SOUTH);
		GridBagLayout gbl_SendMessagePane = new GridBagLayout();
		gbl_SendMessagePane.columnWidths = new int[] {30, 200, 100, 100, 10};
		gbl_SendMessagePane.rowHeights = new int[]{59, 0};
		gbl_SendMessagePane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_SendMessagePane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		SendMessagePane.setLayout(gbl_SendMessagePane);
		
		
		messageField = new JTextField();
		messageField.setText("|");
		messageField.setForeground(Color.GREEN);
		messageField.setBackground(Color.BLACK);
		messageField.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		SendMessagePane.add(messageField, gbc_textField);
		messageField.setColumns(10);
		
		sendMessageBtn = new JButton("Send");
		sendMessageBtn.setForeground(Color.GREEN);
		sendMessageBtn.setBackground(Color.BLACK);
		sendMessageBtn.setFont(new Font("Source Sans Pro Light", Font.BOLD, 20));
		GridBagConstraints gbc_sendMessageBtn = new GridBagConstraints();
		gbc_sendMessageBtn.fill = GridBagConstraints.BOTH;
		gbc_sendMessageBtn.anchor = GridBagConstraints.WEST;
		gbc_sendMessageBtn.insets = new Insets(0, 0, 0, 5);
		gbc_sendMessageBtn.gridx = 2;
		gbc_sendMessageBtn.gridy = 0;
		SendMessagePane.add(sendMessageBtn, gbc_sendMessageBtn);
		
		sendImgBtn = new JButton("");
		sendImgBtn.setBackground(Color.BLACK);
		sendImgBtn.setFont(new Font("Source Sans Pro Light", Font.BOLD, 20));
		sendImgBtn.setForeground(Color.GREEN);
		sendImgBtn.setIcon(new ImageIcon(PannelloChat.class.getResource("/assets/image-iconGreen.png")));
		GridBagConstraints gbc_sendImgBtn = new GridBagConstraints();
		gbc_sendImgBtn.fill = GridBagConstraints.BOTH;
		gbc_sendImgBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_sendImgBtn.gridx = 3;
		gbc_sendImgBtn.gridy = 0;
		SendMessagePane.add(sendImgBtn, gbc_sendImgBtn);
		
		UsernameLabel = new JLabel("New label");
		UsernameLabel.setForeground(Color.GREEN);
		UsernameLabel.setBackground(Color.BLACK);
		add(UsernameLabel, BorderLayout.NORTH);

	}


}
