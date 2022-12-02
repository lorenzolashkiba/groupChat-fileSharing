package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.Window;

public class Client implements ActionListener{

	private Window frame;
	
	public Client(Window _frame) { // poi gli passo anche il model qui, perchè viene gestito dal client
		this.frame = _frame;
		
		this.frame.AddListeners(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getPannelloClient().getSendMessageBtn()) {
			System.out.println(this.frame.getPannelloClient().getMessageField().getText());
			this.frame.getPannelloClient().clearMessageField();
			String messaggio = this.frame.getPannelloClient().getMessageField().getText();
			System.out.println(messaggio);
			this.frame.getPannelloClient().getChatArea().append(":"+messaggio);
			//TODO: mando il messaggio al server 
			//TODO: il server lo manda agli altri client in FORMATO --> nomeUtente: messaggio --> se il nomeUtente è == a quello del client dove lo invia si scrive You
		}else if(e.getSource() == frame.getPannelloClient().getSendImgBtn()) {
			//TODO: aprire nuova finestra dove scegliere l'immagine da mandare e visualizzarla
			//TODO: gestire l'invio dell'immagine al server
		}
	}

}
