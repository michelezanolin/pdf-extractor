package it.estraidapdf;

public class Utils {
	
	public static String removeExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(Costants.PUNTO)); 
	}

}
