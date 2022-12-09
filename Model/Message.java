package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	//private static ArrayList<File> files;
	private String username;
	private String Text;
	//img
	private String filename;
	
	public Message(String _username,String _text) {
		this.username = _username;
		this.Text = _text;
		//files = new ArrayList<>();

	}
	
	public void addFile(File file){
		//files.add(file);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String checkForCodeInText() {
		String retCode = "";
		if(Text.startsWith("/n")) {
			retCode = "NICK";
			Text = Text.substring(3);
			if(Text.startsWith("/nick")) {
				Text = Text.substring(6);
			}
		}else if(Text.startsWith("/h")) {
			retCode = "HELP";
			Text = Text.substring(3);
			if(Text.startsWith("/help")) {
				Text = Text.substring(6);
			}
		}else if(Text.startsWith("/q")) {
			retCode = "QUIT";
			Text = Text.substring(3);
			if(Text.startsWith("/quit")) {
				Text = Text.substring(6);
			}
		}else if(Text.startsWith("/l")) {
			retCode = "LIST";
			Text = Text.substring(3);
			if(Text.startsWith("/list")) {
				Text = Text.substring(6);
			}
		}else if(Text.startsWith("@")) {
			String[] ret= Text.split(" ");
			System.out.println(ret[1]);
			retCode = ret[0].substring(1); //username
			System.out.println(retCode);
			Text = ret[1];
		}
			
		return retCode;
	}
}
