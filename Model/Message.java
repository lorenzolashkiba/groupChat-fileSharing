package Model;

public class Message {

	
	private String username;
	private String Text;
	//img
	
	public Message(String _username,String _text) {
		this.username = _username;
		this.Text = _text;
		//img
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
		}else if(Text.startsWith("/vote [")) {
			retCode = "VOTECREATE";
			Text = Text.substring(6);
			/*
			 * prendo la stringa e la uso per creare un array associativo di int
			 * con risposta->n. votazioni
			 *
			 * */
		}else if(Text.equals("/vote end")) {
			retCode = "VOTEEND";
			//setText outside the funtion with the resoult;
		}else if(Text.startsWith("/v")){
			Text = Text.substring(3);
			if(Text.startsWith("/vote")) {
				Text = Text.substring(6);
			}
			retCode = Text;
			/*
			 * controllo che Text sia presente tra le risposte possibili
			 * nell'array delle risposte se si aumento di 1 quella risposta
			 * */
		}
			
		return retCode;
	}
}
