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
			//TODO: gestire la parte grafica --> tolgo il messaggio dal textField
			//TODO: mando il messaggio al server 
			//TODO: il server lo manda agli altri client in FORMATO --> nomeUtente: messaggio --> se il nomeUtente è == a quello del client dove lo invia si scrive You
		}
	}

}
