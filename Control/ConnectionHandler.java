package Control;


import Model.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import Model.Message;

public class ConnectionHandler implements Runnable {
	
	static class Pair{
		private String Key;
		private int value;
		private ArrayList<String> usersAlreadyVoted = new ArrayList<String>();
		
		public ArrayList<String> getUsersAlreadyVoted(){
			return usersAlreadyVoted;
		}
		
		public boolean addUserVote(String username) {
			boolean ret = true;
			for(String s : usersAlreadyVoted) {
				if(s.equals(username)) {
					ret = false;
				}
			}
			if(ret)
				usersAlreadyVoted.add(username);
			return ret;
		}
		public String getKey() {
			return Key;
		}
	
		public Integer getValue() {
			return value;
		}


		public void IncrementValue() {
			value++;
		}


		Pair(String _key){
			this.Key = _key;
			this.value = 0;
		}
	}
	
    private static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();
    private Socket socketClient;
    private String clientUsername;
    private  ObjectInputStream serverInputStream;
    private  ObjectOutputStream serverOutputStream;
    private static ArrayList<Pair> votationAnswers = new ArrayList<>();
    private static String votationQuestion;
    private static String userThatStarted;

    public ConnectionHandler(Socket socket){
        try {
        	this.socketClient = socket;
            serverInputStream = new ObjectInputStream(socket.getInputStream());
            serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connesione richiesta da: "+ socketClient.getInetAddress().toString()+":"+socketClient.getPort());
            Message message = (Message) serverInputStream.readObject();
            this.clientUsername = message.getUsername();
            connectionHandlers.add(this);

			broadcastMessage(new Message(clientUsername,"has joined the chat!"));
        } catch (Exception e) {
			closeEverything(socketClient, serverOutputStream, serverInputStream);
		}
    }


	// this function receives all the messages sent from the client
    @Override
    public void run() {

        Message msgFromClient;
    	String code;
    	String message;
    	while(socketClient.isConnected()){
         try {
        	System.out.println("WAIT");
			 msgFromClient = (Message) serverInputStream.readObject(); //operazione bloccante
        	//READ MESSAGE
			System.out.println("FATTO");
			code = msgFromClient.checkForCodeInText();
			switch (code) {
				case "NICK": {
					this.clientUsername = msgFromClient.getText();
					message = "SERVER " + msgFromClient.getUsername() + " changed his username into " + this.clientUsername;
					msgFromClient.setUsername(this.clientUsername);
					//check if username already exists

				}
				case "HELP": {
					message = "========================\r\n"
							+ "/n o /nick + nickname  \r\n"
							+ "/h o /help for commands\r\n"
							+ "/q o /quit to quit\r\n"
							+ "/l o /list print users\r\n"
							+ "@username to send a \r\n"
							+ "message directly \r\n"
							+ "/vote [quest] + [A,B]\r\n"
							+ "to create a votation\r\n"
							+ "/vote end to end vote\r\n"
							+ "and print results\r\n"
							+ "/v o /vote A to vote\r\n"
							+ "========================";
					//broadcast message
				}
			case "QUIT": {
				message = "SERVER " + this.clientUsername + " left the chat :(";
				//broadcast message
				closeEverything(socketClient, serverOutputStream, serverInputStream);
				break;
			}
			case "LIST": {
				message = printUsernames();
				//broadcast message
			}
			case "DIRECT":{
				message = msgFromClient.getText(); //questo è lo username dello user a cui vogliamo inviare il messagio
				//TODO:funzione che invia un messaggio ad uno user, e controlla che lo user esita
			}
			case "VOTECREATE": {
				if(!createVotation(msgFromClient.getText())) {
					message = "SERVER " + "there's a votation already active";
				}else {
					message = msgFromClient.getText();
				}
				//broadcast message
			}
			case "VOTEEND": {
				message = endVotation();
				if(message == "ERROR") {
					message = "SERVER " + this.clientUsername + " didn't start the votation";
				}
				//broadcast message
			}
			case "VOTE": {
				if(!addVote(msgFromClient.getText(), this.clientUsername)) {
					message = "SERVER " + this.clientUsername + " has already voted this choice";
				}else {
					message = this.clientUsername + " voted " + msgFromClient.getText();
				}
				//broadcast message;
			}
			case "ERROR incorrect command": {
				message = "SERVER "+code;
				//broadcast message
			}
			default:
				broadcastMessage(msgFromClient);
			}
			
		} catch (Exception e) {
             closeEverything(socketClient, serverOutputStream, serverInputStream);
			break;
		}
        }
    }
    

    public void receiveFile(String fileName){
        try {
            int bytes = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        }catch (Exception e){
            System.out.println("errore receving file");
        }
    }
    

    
    
    //TODO: fare ricerca per vedere se esiste gi� uno username
    //TODO: fare una funzione che invii ad un solo user
    
    private String printUsernames() {
    	String ret = "========================\r\n";
    	for (ConnectionHandler cH : connectionHandlers) {   
    		if(!cH.getClientUsername().equals(this.clientUsername)) {
    			ret += "> " + cH.clientUsername + "\r\n";
    		}
   		}
    	ret += "========================";
    	return ret;
    }
    
    private void broadcastMessage(Message message) { //add image

		for (ConnectionHandler cH : connectionHandlers) {
			try {
				if (message.getText().startsWith("SERVER")) {
					message.setText(message.getText().substring(7));
					message.setUsername("SERVER");

				}
				if (!cH.clientUsername.equals(message.getUsername())) {
					//SEND OBJECT MESSAGE
					serverOutputStream.writeObject(message);
				}
			} catch (Exception e) {
				closeEverything(socketClient,serverOutputStream,serverInputStream);
			}
		}
	}
    


    public String getClientUsername() {
        return clientUsername;
    }

    
    public void removeConnectionHandler(){
    	connectionHandlers.remove(this);
    }


    public void closeEverything(Socket socket, ObjectOutputStream writer, ObjectInputStream in ){
        removeConnectionHandler();
        try {
            if (in != null) {
                in.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }




    
    private String createAnswerString(int startingPoint, String risp) {
    	int j=startingPoint+1;
		String answer = "";
		while(risp.charAt(j)!= ',' && risp.charAt(j)!= ']') {
			answer += risp.charAt(j);
			j++;
		}		
		j = 0;
		return answer;
    }
    
    public boolean addVote(String vote, String username) { //lo username arriva dall'oggeto messaggio
    	boolean ret = false;
    	for(Pair vt : votationAnswers) {
    		if(vt.Key.equals(vote)){
    			if(vt.addUserVote(username)) {
    				ret = true;
    				vt.IncrementValue();	
    			}else {
    				System.out.println("alredy voted this choice");
    			}
    			
    		}
    	}
    	return ret;
    }
    
    public void resetVotation() {
    	votationAnswers.clear();
    	votationQuestion = "";
    }
    
    public String endVotation() {
    	String ret = "";
    	if(userThatStarted.equals(this.clientUsername)) {
    		ret += "========================\r\n";
        	ret += ">" + votationQuestion + "\n";
        	for(Pair vt : votationAnswers) {
        		ret += "> " + vt.Key + " = " + vt.value + "\n";
         	}
        	ret += "========================";
        	resetVotation();
    	}else {
    		ret = "ERROR";
    	}
    	return ret;
    }
   
    
    public boolean createVotation(String votationText) {
    	// votationText ==> [domanda] : [rips1,rips2,...,rispn];
    	boolean ret = true;
    	
    	if(votationAnswers.isEmpty()) {
    		userThatStarted = this.clientUsername;
    		String[] res = votationText.split(":");
        	votationQuestion = res[0];
        	//rimuovere parentesi quadre
        	String risp = res[1];
        	risp = risp.substring(1);
        	System.out.println(risp);
        	
        	for(int i=0;i<risp.length();i++) {
        		if(risp.charAt(i) == '[') {
        			String answer = createAnswerString(i, risp);
        			votationAnswers.add(new Pair(answer));
        		}else if(risp.charAt(i) == ',') {
        			String answer = createAnswerString(i, risp);
        			votationAnswers.add(new Pair(answer));
        		}
        	}
        	
        	
    	}else {
    		ret = false;
    	}
    	
    	return ret;
    	
    }

    public String toString(){
        return clientUsername;
    }
}
