package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFTest {

	public static void main(String[] args) {
		PDDocument pd;
		BufferedWriter wr;
		try {/*
			URL url = PDFTest.class.getClassLoader().getResource("1204_ETC_EAC.pdf");
			
			File input = new File(url.getFile());*/
			
			String phyPath = PDFTest.class.getResource("PDFTest.class").getPath();
			String aimFileName = "test1204_ETC_EAC.pdf";
			String fullFileName = phyPath.replace("PDFTest.class", aimFileName);
			File input = new File("E:\\git\\wx\\src\\test\\1204_ETC_EAC.pdf");
			
			File output = new File("D:\\etc计算.txt"); // The text file where
			if(!output.exists()) {
				output.createNewFile();
			}
			pd = PDDocument.load(input);
			System.out.println(pd.getNumberOfPages());
			System.out.println(pd.isEncrypted());

			//pd.save("CopyOfInvoice.pdf"); // Creates a copy called
			COSDocument doc = pd.getDocument();
			System.out.println(doc.getObjects().size());
			
			for (int i = 0; i < doc.getObjects().size(); i++) {
				List<COSObject> objList = doc.getObjects();
			}
			
			PDFTextStripper stripper = new PDFTextStripper();
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
			stripper.writeText(pd, wr);
			if (pd != null) {
				pd.close();
			}
			wr.close();
			System.out.println("完成");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
