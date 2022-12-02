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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PannelloChat extends JPanel {
	private JTextField messageField;
	private JTextPane toolBarText;
	private JTextArea chatArea;
	private JButton sendMessageBtn;
	private JButton sendImgBtn;

	public JTextField getMessageField() {
		return messageField;
	}

	public void setMessageField(JTextField textField) {
		this.messageField = textField;
	}

	public JTextPane getToolBarText() {
		return toolBarText;
	}

	public void setToolBarText(String str) {
		this.toolBarText.setText(str);;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	public void setChatArea(String str) {
		this.chatArea.setText(str);;
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
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		toolBarText = new JTextPane();
		toolBarText.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		toolBarText.setEditable(false);
		toolBarText.setText("test");
		toolBar.add(toolBarText);
		
		chatArea = new JTextArea();
		chatArea.setText("prova");
		chatArea.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		chatArea.setEditable(false);
		add(chatArea, BorderLayout.CENTER);
		
		JPanel SendMessagePane = new JPanel();
		add(SendMessagePane, BorderLayout.SOUTH);
		GridBagLayout gbl_SendMessagePane = new GridBagLayout();
		gbl_SendMessagePane.columnWidths = new int[] {30, 200, 100, 100, 10};
		gbl_SendMessagePane.rowHeights = new int[]{59, 0};
		gbl_SendMessagePane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_SendMessagePane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		SendMessagePane.setLayout(gbl_SendMessagePane);
		
		
		messageField = new JTextField();
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
		sendMessageBtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		GridBagConstraints gbc_sendMessageBtn = new GridBagConstraints();
		gbc_sendMessageBtn.fill = GridBagConstraints.BOTH;
		gbc_sendMessageBtn.anchor = GridBagConstraints.WEST;
		gbc_sendMessageBtn.insets = new Insets(0, 0, 0, 5);
		gbc_sendMessageBtn.gridx = 2;
		gbc_sendMessageBtn.gridy = 0;
		SendMessagePane.add(sendMessageBtn, gbc_sendMessageBtn);
		
		sendImgBtn = new JButton("");
		sendImgBtn.setIcon(new ImageIcon(PannelloChat.class.getResource("/assets/image-iconSmall.png")));
		GridBagConstraints gbc_sendImgBtn = new GridBagConstraints();
		gbc_sendImgBtn.fill = GridBagConstraints.BOTH;
		gbc_sendImgBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_sendImgBtn.gridx = 3;
		gbc_sendImgBtn.gridy = 0;
		SendMessagePane.add(sendImgBtn, gbc_sendImgBtn);

	}

}
