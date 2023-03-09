package it.carabinieri.estraipdf;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileChooser extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String SELEZIONA = "Seleziona";
	public static final String ESTENSIONE_TESTO = ".txt";

	private JTextField filename = new JTextField();
	private JTextField dir = new JTextField();

	private JButton open = new JButton(SELEZIONA);

	public FileChooser() {
		JPanel p = new JPanel();		
		open.addActionListener(new OpenL());
		p.add(open);

		Container cp = getContentPane();
		cp.add(p, BorderLayout.SOUTH);
		dir.setEditable(false);
		filename.setEditable(false);
		
		p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(filename);
		p.add(dir);
		cp.add(p, BorderLayout.NORTH);
	}

	class OpenL implements ActionListener {
		
		private ShowSpinner spinner = new ShowSpinner(); 
	
		public void actionPerformed(ActionEvent e) {
			this.getSpinner().showSpinner();
			
			JFileChooser c = new JFileChooser();
			int rVal = c.showOpenDialog(FileChooser.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				filename.setText(c.getSelectedFile().getName());
				dir.setText(c.getCurrentDirectory().toString());
				
				File file = c.getSelectedFile(); 
				String fileName = file.getName();
				fileName = Utils.removeExtension(fileName); 
				
				// estrai testo 
				try {
					String percorsoTesto = CreaCartella.creaCartella(file, CreaCartella.CARTELLA_TESTO);
					StringBuilder sb = new StringBuilder(); 
					sb.append(percorsoTesto).append(fileName).append(ESTENSIONE_TESTO);
					EstraiTesto.estraiTestoPdf(file.getAbsolutePath(), sb.toString());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				
				// estrai immagini 
				try {
					String percorsoImmagini = CreaCartella.creaCartella(file, CreaCartella.CARTELLA_IMMAGINI);
					EstraiImage.estraiImmaginiPdf(file.getAbsolutePath(), percorsoImmagini);
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				
				// visualizza informazioni
				try {
					ShowInfo.creaFinestraInformazioni(EstraiInfo.estraiInformazioniPdf(file.getAbsolutePath()));
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				
				this.getSpinner().hideSpinner();
			}
			
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText(SELEZIONA);
				dir.setText(Costants.EMPTY_STRING);
			}
		}
		
		public ShowSpinner getSpinner() {
			return spinner;
		}

		public void setSpinner(ShowSpinner spinner) {
			this.spinner = spinner;
		}
	}

	public static void run(JFrame frame, int width, int height) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
}
