package com.zcy.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfUtil {

	public static String PdfboxFileReader(String fileName) throws Exception {
		try {
			PDFTextStripper ts = new PDFTextStripper();
			return extractPdfText(fileName, ts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static int getPdfPages(String fileName) {
		int pages = 0;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			PDFParser p = new PDFParser(fis);
			p.parse();
			PDDocument pdDocument = p.getPDDocument();

			pages = pdDocument.getNumberOfPages();
			pdDocument.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pages;

	}

	public static String extractPdfText(String fileName, PDFTextStripper ts) {
		StringBuffer content = new StringBuffer("");// 文档内容

		try {
			FileInputStream fis = new FileInputStream(fileName);
			PDFParser p = new PDFParser(fis);
			p.parse();
			PDDocument pdDocument = p.getPDDocument();
			pdDocument.getNumberOfPages();

			content.append(ts.getText(pdDocument));
			pdDocument.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString().trim();
	}

	public static List<String> getLines(String fileName, int startPage, int endPage) {
		List<String> lines = new ArrayList<String>();

		try {

			String pdfboxFileReader = PdfboxFileReader(fileName, startPage, endPage);
			BufferedReader buff = new BufferedReader(new StringReader(pdfboxFileReader));
			String line = buff.readLine();

			if (EcUtil.isValid(line)) {
				lines.add(line);
			}
			while (line != null) {

				if (EcUtil.isValid(line)) {
					lines.add(line);
				}
				line = buff.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	public static String PdfboxFileReader(String fileName, int startPage, int endPage) {
		try {
			PDFTextStripper ts = new PDFTextStripper();
			ts.setStartPage(startPage);
			ts.setEndPage(endPage);
			return extractPdfText(fileName, ts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
}
