package com.zcy.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.textmining.extraction.word.WordTextExtractorFactory;

import com.zcy.cfg.CFGManager;
import com.zcy.util.PdfUtil;

public class IndexFiles {

	public static final String DOCUMENT_SCAN_DIR = "document_scan_dir";
	public static final String LUCENE_INDEX_DIR = "lucene_index_dir";

	// -index F:\Lucene_index -docs F:\Lucene_data
	public void runIndex() {
		String usage = "java org.apache.lucene.demo.IndexFiles [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\nThis indexes the documents in DOCS_PATH, creating a Lucene indexin INDEX_PATH that can be searched with SearchFiles";

		String indexPath = CFGManager.getProperty(LUCENE_INDEX_DIR); // 索引存放的路径
		String docsPath = CFGManager.getProperty(DOCUMENT_SCAN_DIR); // 需要给哪些文件建立索引（即资源库的地址）
		boolean create = true; // 是否新建索引

		if (docsPath == null) {
			System.err.println("Usage: " + usage);
			System.exit(1);
		}

		// 验证资源文件地址
		File docDir = new File(docsPath);
		if ((!docDir.exists()) || (!docDir.canRead())) {
			System.out.println("Document directory '" + docDir.getAbsolutePath() + "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		// 开始建立索引
		Date start = new Date();
		try {
			System.out.println("Indexing to directory '" + indexPath + "'...");

			// 根据索引存放地址，创建目录
			Directory dir = FSDirectory.open(new File(indexPath));
			// 初始化分析器
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			// 索引配置
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);

			if (create) {
				iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			} else {
				iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			}

			// 初始化索引
			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);

			// 关闭索引
			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}

	// 递归的方式，遍历每一个文件
	public void indexDocs(IndexWriter writer, File file) throws IOException {

		System.out.println(file.getName());
		if (file.canRead())
			if (file.isDirectory()) {
				String[] files = file.list();

				if (files != null)
					for (int i = 0; i < files.length; i++)
						indexDocs(writer, new File(file, files[i]));
			} else {

				Document doc = new Document();

				Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
				doc.add(pathField);

				doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));

				try {
					String docString = getDocString(file.getAbsolutePath());
					// System.out.println(docString);
					doc.add(new TextField("contents", docString, Field.Store.YES));
				} catch (Exception e) {
					doc.add(new TextField("contents", "", Field.Store.YES));
				}

				if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
					System.out.println("adding " + file);
					writer.addDocument(doc);
				} else {
					System.out.println("updating " + file);
					writer.updateDocument(new Term("path", file.getPath()), doc);
				}

			}
	}

	public String getDocString(String fileName) {

		String fileExtension = "*.avi *.rmvb *.rm *.asf *.divx *.mpg *.mpeg *.mpe *.wmv *.mp4 *.mkv *.vob";
		String extension = fileName.substring(fileName.lastIndexOf("."));

		if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".jpeg") || fileExtension.contains(extension)) {
			return "";
		}
		if (fileName.endsWith(".pdf")) {

			try {
				return PdfUtil.PdfboxFileReader(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {

			try {
				return WordFileReader(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			return FileReaderAll(fileName, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	/**
	 * Excel表格提取数据
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public String ExcelFileReader(String fileName) throws IOException {
		InputStream path = new FileInputStream(fileName);
		String content = null;
		HSSFWorkbook wb = new HSSFWorkbook(path);
		ExcelExtractor extractor = new ExcelExtractor(wb);
		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(false);
		content = extractor.getText();
		return content;
	}

	/**
	 * 提取TXT文档
	 * 
	 * @param 路径
	 * @param 编码
	 * @return
	 * @throws IOException
	 */
	public String FileReaderAll(String fileName, String charSet) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
		String line = new String();
		String temp = new String();
		while ((line = reader.readLine()) != null) {
			temp += line;
		}
		reader.close();
		return temp;
	}

	/**
	 * 提取word文档
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public String WordFileReader(String fileName) throws IOException {

		FileInputStream in = new FileInputStream(new File(fileName));
		WordTextExtractorFactory extractor = new WordTextExtractorFactory();
		String text = null;
		try {
			text = extractor.textExtractor(in).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
		return text;

	}
}