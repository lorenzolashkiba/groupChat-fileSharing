package Execute;

import java.io.IOException;
import java.net.ServerSocket;

import Control.Server;

public class RunServer {

	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3636);
			Server server = new Server(serverSocket);
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

/*
 * interfaccia tipo Messanger (HACKER)
 * Invio Immagini e visualizzarle come messaggi 
 * -> creare una classe Messaggio(Model) che ha come attributi
 * -> nomeUtente, testo, immagine(opzionale) e fa già il controllo
 * -> dei comandi (/nick, /quit, @name ecc...)  
 * 
 * 
 * : COMANDI :
 * : /nick --> controllo che il nick non esista già (anche all'inizio del programma)
 * : /quit
 * : /help
 * : @name per chattare ad un solo user
 * : /userList stampa tutti gli user connessi
 * :-> /vote [question] : [yes,no,maybe] questo genera la votazione
 * :-> /vote yes 
 * :-> /vote end questo finisce la votazione e stampa in broadcast il risultato
 * : fare uno STACK di messaggi con la possibilità di richiamarli con le freccette
 */