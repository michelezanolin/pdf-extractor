package it.carabinieri.estraipdf;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

public class ShowSpinner {

	public static final String PERCORSO_SPINNER = "spinner.gif";
	public static final String CARICAMENTO = "Caricamento...";

	private JFrame frame;
	
	public ShowSpinner() {
		frame = new JFrame(Costants.EMPTY_STRING);
	}
	
	public void showSpinner() {
		
		// il frame non può essere chiuso dall'utente
		this.getFrame().setUndecorated(true);
		this.getFrame().getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		ImageIcon loading = new ImageIcon(PERCORSO_SPINNER);
		this.getFrame().add(new JLabel(CARICAMENTO, loading, JLabel.CENTER));

		this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getFrame().setSize(400, 300);
		this.getFrame().setVisible(true);
	}
	
	public void hideSpinner() {
		this.getFrame().setVisible(false);
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
