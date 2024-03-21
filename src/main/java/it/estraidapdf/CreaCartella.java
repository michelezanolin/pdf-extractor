package it.estraidapdf;

import java.io.File;

public class CreaCartella {

	/**
	 * percorso cartella immagine estrapolato
	 */
	public static final String CARTELLA_IMMAGINI = "/IMMAGINI";

	/**
	 * 
	 */
	public static final String CARTELLA_TESTO = "/TESTO";

	/**
	 * crea cartella con nome specificato
	 * 
	 * @param file
	 * @param nomeCartella
	 */
	public static String creaCartella(File file, String nomeCartella) {

		String fileName = file.getName();
		fileName = Utils.removeExtension(fileName); 
		
		StringBuilder sb = new StringBuilder();
		sb.append(creaPercorsoCartella(file));
		sb.append(nomeCartella);
		sb.append(Costants.UNDERSCORE).append(fileName).append(Costants.SLASH);
		new File(sb.toString()).mkdir();
		return sb.toString(); 
	}

	/**
	 * crea destinazione cartella
	 * 
	 * @param file
	 * @return
	 */
	public static String creaPercorsoCartella(File file) {
		String destinazione = file.getAbsolutePath();
		int n = destinazione.lastIndexOf('\\');
		destinazione = destinazione.substring(0, n);
		return destinazione.replace('\\', '/');
	}
}
