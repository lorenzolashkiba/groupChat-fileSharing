package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	//private static ArrayList<File> files;
	private String username;
	private String Text;
	private String directToUser;
	public String getDirectToUser() {
		return directToUser;
	}

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
		if(Text.startsWith("/") || Text.startsWith("@")) {
			if(Text.startsWith("/n")) {
				retCode = "NICK";
				Text = Text.substring(3);
				if(Text.startsWith("ck")) {
					Text = Text.substring(3);
				}
			}else if(Text.startsWith("/h")) {
				retCode = "HELP";
			}else if(Text.startsWith("/q")) {
				retCode = "QUIT";
			}else if(Text.startsWith("/l")) {
				retCode = "LIST";
			}else if(Text.startsWith("@")) {
				String[] ret= Text.split(" ");
				System.out.println(ret[1]);
				directToUser = ret[0].substring(1); //username
				Text = ret[1];
				retCode = "DIRECT";
			}else if(Text.startsWith("/vote [")) {
				retCode = "VOTECREATE";
				Text = Text.substring(6);
		
			}else if(Text.equals("/vote end")) {
				retCode = "VOTEEND";
				//setText outside the funtion with the resoult;
			}else if(Text.startsWith("/v")){
				Text = Text.substring(3);
				if(Text.startsWith("te")) {
					Text = Text.substring(3);
				}
				retCode = "VOTE";
				/*
				 * controllo che Text sia presente tra le risposte possibili
				 * nell'array delle risposte se si aumento di 1 quella risposta
				 * */
			}else {
				retCode = "ERROR incorrect command";
			}
		}else {
			retCode = "NONE";
		}
		
			
		return retCode;
	}
}
