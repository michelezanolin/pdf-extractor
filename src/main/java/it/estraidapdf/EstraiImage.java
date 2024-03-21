package it.estraidapdf;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class EstraiImage {

	
	public static String FORMATO_IMMAGINE = "png";

	public static List<BufferedImage> estraiContenutoImmaginiPdf(String sorgente) throws IOException {
		List<BufferedImage> list = new ArrayList<BufferedImage>();
		File file = new File(sorgente);
		PDDocument document = PDDocument.load(file);
		List<RenderedImage> renderedImages = getImagesFromPDF(document);
		for (RenderedImage img : renderedImages) {
			list.add(drawRenderedImage(img));
		}
		return list;
	}

	public static List<RenderedImage> getImagesFromPDF(PDDocument document) throws IOException {
		List<RenderedImage> images = new ArrayList<RenderedImage>();
		for (PDPage page : document.getPages()) {
			images.addAll(getImagesFromResources(page.getResources()));
		}
		return images;
	}

	private static List<RenderedImage> getImagesFromResources(PDResources resources) throws IOException {
		List<RenderedImage> images = new ArrayList<RenderedImage>();
		Iterator<COSName> iterator = resources.getXObjectNames().iterator();  
		while (iterator.hasNext()) {
			PDXObject xObject = resources.getXObject(iterator.next());
			if (xObject instanceof PDFormXObject) {
				if (((PDFormXObject) xObject).getResources() != null) {
					images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
				}
			} else if (xObject instanceof PDImageXObject) {
				images.add(((PDImageXObject) xObject).getImage());
			}
		}
		return images;
	}

	public static void estraiImmaginiPdf(String sorgente, String destinazione) throws IOException {
		List<BufferedImage> list = estraiContenutoImmaginiPdf(sorgente);
		for (int i = 0; i < list.size(); i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(destinazione).append(i + 1);
			sb.append(Costants.PUNTO).append(FORMATO_IMMAGINE);
			ImageIO.write(list.get(i), FORMATO_IMMAGINE, new File(sb.toString()));
		}
	}

	public static BufferedImage drawRenderedImage(RenderedImage img) {
		BufferedImage image = null;
		if (img instanceof BufferedImage) {
			image = (BufferedImage) img;
		} else {
			ColorModel cm = img.getColorModel();
			int width = img.getWidth();
			int height = img.getHeight();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();

			Hashtable<String, Object> properties = new Hashtable<String, Object>();
			String[] keys = img.getPropertyNames();
			if (keys != null) {
				for (int i = 0; i < keys.length; i++) {
					properties.put(keys[i], img.getProperty(keys[i]));
				}
			}
			BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
			img.copyData(raster);
			image = result;
		}
		return image;
	}
}
