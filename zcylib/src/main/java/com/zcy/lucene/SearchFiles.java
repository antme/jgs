package com.zcy.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.zcy.cfg.CFGManager;

public class SearchFiles {

	public void search(String queryString, boolean raw) throws Exception {
		// 对参数的一些处理

		String indexPath = CFGManager.getProperty("lucene_index_dir"); // 索引存放的路径

		String index = indexPath;
		String field = "contents";
		int repeat = 0;
		int hitsPerPage = 10;

		// 读取索引
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		// 查询索引
		IndexSearcher searcher = new IndexSearcher(reader);
		// 分析器
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);

		// 解析器
		QueryParser parser = new QueryParser(Version.LUCENE_45, field, analyzer);

		Query query = parser.parse(queryString);
		System.out.println("Searching for: " + query.toString(field));

		if (repeat > 0) {
			Date start = new Date();
			for (int i = 0; i < repeat; i++) {
				searcher.search(query, null, 100);
			}
			Date end = new Date();
			System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");
		}

		doPagingSearch(searcher, query, hitsPerPage, raw);

		reader.close();
	}

	public static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage, boolean raw) throws IOException {
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		if (end > hits.length) {
			System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits + " total matching documents collected.");
			System.out.println("Collect more (y/n) ?");

			hits = searcher.search(query, numTotalHits).scoreDocs;
		}

		end = Math.min(hits.length, start + hitsPerPage);

		for (int i = start; i < end; i++) {
			if (raw) {
				System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
			} else {
				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
					System.out.println(i + 1 + ". " + path);
					String title = doc.get("title");
					if (title != null)
						System.out.println("   Title: " + doc.get("title"));
				} else {
					System.out.println(i + 1 + ". " + "No path for this document");
				}
			}
		}

		if (numTotalHits >= end) {
			if (start - hitsPerPage >= 0) {
				System.out.print("(p)revious page, ");
			}
			if (start + hitsPerPage < numTotalHits) {
				System.out.print("(n)ext page, ");
			}

		}

		end = Math.min(numTotalHits, start + hitsPerPage);

	}

}