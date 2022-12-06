package Control;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
	
	static class Pair{
		private String Key;
		private int value;
		
		
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
    private static ArrayList<Pair> votationAnswers = new ArrayList<>();
    private static String votationQuestion;
    private Socket socketClient;
    private BufferedReader Reader;
    private BufferedWriter Writer;
    private String clientUsername;


    public ConnectionHandler(Socket socket){
        try {
        	this.socketClient = socket;
            Writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            Reader = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            System.out.println("Connesione richiesta da: "+ socketClient.getInetAddress().toString()+":"+socketClient.getPort());
            this.clientUsername = Reader.readLine();
            System.out.println(clientUsername);
            connectionHandlers.add(this);
            sendTextMessageBroadC("has joined the chat!");
        } catch (IOException e) {
            closeEverything(socket, Writer, Reader);
        }
    }
    
    
    // this function receives all the messages sent from the client
    @Override
    public void run() {
    	String msgFromClient;
        while(socketClient.isConnected()){
         try {
        	System.out.println("WAIT");
			msgFromClient = Reader.readLine(); //operazione bloccante
			System.out.println("FATTO");
			if(msgFromClient != null) {
				if(msgFromClient.startsWith("/nick")) {
					String oldUsername = clientUsername;
					clientUsername = msgFromClient.substring(6);
					sendTextMessageBroadC("SERVER:: "+ oldUsername + " has changed his name to " + clientUsername);	
				}else if(msgFromClient.startsWith("/quit")){
					closeEverything(socketClient, Writer, Reader);
					break;
				}else if(msgFromClient.startsWith("/vote [")) {
					msgFromClient = msgFromClient.substring(6);
					if(!createVotation(msgFromClient)) {
						System.err.println("ERRORE ESISTE GIA UNA VOTAZIONE ATTIVA");
						sendTextMessageBroadC("SERVER: ERRORE VOTAZIONE GIA ATTIVA");
					}else {
						System.out.println(msgFromClient);
						for(int i=0;i<votationAnswers.size();i++) {
							System.out.println(votationAnswers.get(i).Key + " --> " + votationAnswers.get(i).value);
						}
						sendTextMessageBroadC(msgFromClient);
					}
					
					
				}else if(msgFromClient.equals("/vote end")) {
					for(int i=0;i<votationAnswers.size();i++) {
						System.out.println(votationAnswers.get(i).Key + " --> " + votationAnswers.get(i).value);
					}
					String str = endVotation();
					sendTextMessageBroadC(str);
					//setText outside the funtion with the resoult;
				}else if(msgFromClient.startsWith("/v")){
					msgFromClient = msgFromClient.substring(3);
					if(msgFromClient.startsWith("/vote")) {
						msgFromClient = msgFromClient.substring(6);
					}
					addVote(msgFromClient);
					/*
					 * controllo che Text sia presente tra le risposte possibili
					 * nell'array delle risposte se si aumento di 1 quella risposta
					 * */
				}
				else {
					sendTextMessageBroadC(msgFromClient);				
				}
			}else {
				closeEverything(socketClient, Writer, Reader);
				break;
			}
			
		} catch (Exception e) {
			closeEverything(socketClient, Writer, Reader);
			break;
		}
        }
    }
    
    
    
    //TODO: fare ricerca per vedere se esiste già uno username
    //TODO: fare una funzione che invii ad un solo user
    public boolean sendTextMessageBroadC(String messageToSend){


        //connectionHandler.out.println(text);
        for (ConnectionHandler cH : connectionHandlers) {
         try {
        	 
        	 if(messageToSend.startsWith("SERVER")) {
        		cH.Writer.write(messageToSend);
 				cH.Writer.newLine();
 				cH.Writer.flush(); 
        	 }else {
        		 if(!cH.clientUsername.equals(clientUsername)) {
        			 cH.Writer.write(clientUsername + ": " + messageToSend);
        			 cH.Writer.newLine(); // perchè readline aspetta che venga inviata una nuova linea
        			 cH.Writer.flush(); // riempie il buffer
        		 }else {
        			 cH.Writer.write("You" + ": " + messageToSend);
        			 cH.Writer.newLine(); 
        			 cH.Writer.flush(); 
        		 }
        		 
        	 }
		} catch (Exception e) {
			closeEverything(socketClient, Writer, Reader);
		}
        }
        return true;
    }
    


    public String getClientUsername() {
        return clientUsername;
    }

    
    public void removeConnectionHandler(){
    	connectionHandlers.remove(this);
    	 sendTextMessageBroadC("SERVER:"+clientUsername+" has left the chat!");
    	
    }
  
    
   
    public void closeEverything(Socket socket,BufferedWriter write, BufferedReader read ){
    	removeConnectionHandler();
        try {
            if (read != null) {
                read.close();
            }
            if (write != null) {
                write.close();
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
    
    public boolean addVote(String vote) {
    	boolean ret = false;
    	for(Pair vt : votationAnswers) {
    		if(vt.Key.equals(vote)){
    			ret = true;
    			vt.IncrementValue();
    		}
    	}
    	return ret;
    }
    
    public void resetVotation() {
//    	votationAnswers.clear();
//    	votationQuestion = "";
    }
    
    public String endVotation() {
    	String ret = "";
    	ret += "=================================\n";
    	ret += "::" + votationQuestion + "\n";
    	for(Pair vt : votationAnswers) {
    		ret += ":: " + vt.Key + " => " + vt.value + "\n";
     	}
    	ret += "=================================";
    	resetVotation();
    	return ret;
    }
    //TODO: fare funzione che stampa risultati e azzera la votazione
    
    public boolean createVotation(String votationText) {
    	// votationText ==> [domanda] : [rips1,rips2,...,rispn];
    	boolean ret = true;
    	if(votationAnswers.isEmpty()) {
    		String[] res = votationText.split(":");
        	votationQuestion = res[0];
        	votationQuestion = votationQuestion.substring(1);
        	votationQuestion = votationQuestion.substring(votationQuestion.length()-1);
        	System.out.println(votationQuestion);
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
