package it.carabinieri.estraipdf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

public class EstraiTesto {

	public static void estraiTestoPdf(String sorgente, String destinazione) throws IOException {
		String contenuto = estraiContenutoTestualePdf(sorgente);
		stringToFile(contenuto, destinazione);
	}

	/**
	 * metodo per scrivere una stringa in un file
	 * 
	 * @param contenuto
	 * @param destinazione
	 * @throws IOException
	 */
	public static void stringToFile(String contenuto, String destinazione) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(destinazione));
		out.write(contenuto);
		out.close();
	}

	/**
	 * metodo per estrapolare il testo contenuto in un pdf
	 * 
	 * @param sorgente
	 * @return
	 * @throws IOException
	 */
	public static String estraiContenutoTestualePdf(String sorgente) throws IOException {
		PdfReader pdfReader = new PdfReader(sorgente);
		PdfDocument pdf = new PdfDocument(pdfReader);
		PdfDocument pdfDocument = new PdfDocument(new PdfReader(sorgente));
		PdfDocumentContentParser parser = new PdfDocumentContentParser(pdfDocument);
		StringBuilder sb = new StringBuilder();
		for (int pageNumber = 1; pageNumber <= pdf.getNumberOfPages(); pageNumber++) {
			SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
			sb.append(parser.processContent(pageNumber, strategy).getResultantText());
		}
		pdf.close();
		return sb.toString();
	}
}
