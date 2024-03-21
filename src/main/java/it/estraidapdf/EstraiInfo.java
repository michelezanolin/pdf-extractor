package it.estraidapdf;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfReader;

public class EstraiInfo {
	
	// chiavi mappa informazioni pdf
	public static final String AUTORE = "AUTORE";
	public static final String CREATORE = "CREATORE";
	public static final String PAROLECHIAVE = "PAROLECHIAVE";
	public static final String PRODUTTORE = "PRODUTTORE";
	public static final String SOGGETTO = "SOGGETTO";
	public static final String TITOLO = "TITOLO";
	
	public static final String PAGINE = "PAGINE";
	
	/**
	 * metodo per estrapolare il numero di pagine di un pdf
	 * 
	 * @param sorgente
	 * @return
	 * @throws IOException
	 */
	public static int estraiNumeroPaginePdf(String sorgente) throws IOException {
		PdfReader pdfReader = new PdfReader(sorgente);
		PdfDocument pdf = new PdfDocument(pdfReader);
		int num = pdf.getNumberOfPages();
		pdf.close();
		return num;
	}

	/**
	 * metodo per estrapolare le informazioni relative ad un pdf
	 * 
	 * @param sorgente
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> estraiInformazioniPdf(String sorgente) throws IOException {
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(sorgente));
		PdfDocumentInfo pdfDocInfo = pdfDoc.getDocumentInfo();

		Map<String, String> mappaInformazioni = new HashMap<String, String>();
		mappaInformazioni.put(AUTORE, pdfDocInfo.getAuthor());
		mappaInformazioni.put(CREATORE, pdfDocInfo.getCreator());
		mappaInformazioni.put(PAROLECHIAVE, pdfDocInfo.getKeywords());
		mappaInformazioni.put(PRODUTTORE, pdfDocInfo.getProducer());
		mappaInformazioni.put(SOGGETTO, pdfDocInfo.getSubject());
		mappaInformazioni.put(TITOLO, pdfDocInfo.getTitle());
		
		pdfDoc.close();
		
		// aggiungi numero di pagine
		int numPagine = estraiNumeroPaginePdf(sorgente); 
		StringBuilder sb = new StringBuilder(); 
		sb.append(numPagine); 
		mappaInformazioni.put(PAGINE, sb.toString()); 

		return mappaInformazioni;
	}

}
