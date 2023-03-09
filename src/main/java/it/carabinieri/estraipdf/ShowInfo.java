package it.carabinieri.estraipdf;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowInfo extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI(Map<String, String> info) {
		// Create and set up the window.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 1 riga 
		Object rowData[][] = { 
				{	info.get(EstraiInfo.AUTORE), 
					info.get(EstraiInfo.CREATORE),
					info.get(EstraiInfo.PAROLECHIAVE), 
					info.get(EstraiInfo.PRODUTTORE), 
					info.get(EstraiInfo.SOGGETTO),
					info.get(EstraiInfo.TITOLO),
					info.get(EstraiInfo.PAGINE)} 
				};
		
		// 7 colonne 
		Object columnNames[] = { 
				EstraiInfo.AUTORE, 
				EstraiInfo.CREATORE, 
				EstraiInfo.PAROLECHIAVE, 
				EstraiInfo.PRODUTTORE,
				EstraiInfo.SOGGETTO,
				EstraiInfo.TITOLO,
				EstraiInfo.PAGINE
				};

		JTable table = new JTable(rowData, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(300, 150);
	    frame.setVisible(true);
	}

	public static void creaFinestraInformazioni(final Map<String, String> info) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(info);
			}
		});
	}

}
