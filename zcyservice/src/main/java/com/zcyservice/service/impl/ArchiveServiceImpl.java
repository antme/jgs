package com.zcyservice.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcUtil;
import com.zcy.util.PdfUtil;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.Archive.ArchiveStatus;
import com.zcyservice.bean.Archive.ProcessStatus;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.bean.ArchiveFile.ArchiveFileProperty;
import com.zcyservice.bean.vo.ArchiveTree;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.AbstractArchiveService;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.ZcyServiceConstants;

@Service(value = "archiveService")
public class ArchiveServiceImpl extends AbstractArchiveService implements IArchiveService {
	private static Logger logger = LogManager.getLogger(ArchiveServiceImpl.class);

	public void scanArchines() {

		String scanPath = CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);
		logger.info("Scan " + scanPath);

		File file = new File(scanPath);

		if (file.exists() && file.isDirectory()) {

			File subFiles[] = file.listFiles();
			for (File subFile : subFiles) {

				if (subFile.isDirectory()) {
					Archive arc = new Archive();
					arc.setArchiveCode(subFile.getName());
					arc.setArchiveStatus(ArchiveStatus.ARCHIVED);

					arc.setArchiveName(subFile.getName());

					this.dao.insert(arc);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中", arc, ArchiveFileProperty.FIRST);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中", arc, ArchiveFileProperty.SECOND);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中" + File.separator + "附件", arc, ArchiveFileProperty.FIRST_ATTACH);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中" + File.separator + "附件", arc, ArchiveFileProperty.SECOND_ATTACH);

				}
			}

		} else {
			logger.error("请创建好扫描目录: " + scanPath);
		}
	}

	public EntityResults<Archive> listArchives(SearchVo vo) {

		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(Archive.TABLE_NAME), Archive.class);
	}

	public EntityResults<Archive> listNewArchives(SearchVo vo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ACHIVE_PROCESS_STATUS, ProcessStatus.NEW);

		return this.dao.listByQueryWithPagnation(query, Archive.class);

	}

	public void approveArchive(Archive archive) {

		archive.setAchiveProcessStatus(ProcessStatus.APPROVED);
		this.dao.updateById(archive);
	}

	public void rejectArchive(Archive archive) {

		archive.setAchiveProcessStatus(ProcessStatus.REJECTED);
		this.dao.updateById(archive);

	}

	public Map<String, Object> listArchiveFiles(Archive archive) {

		if (archive.getId() == null) {
			throw new ResponseException("请输入查询ID");
		}

		archive = (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
		query.and(ArchiveFile.ARCHIVE_ID, archive.getId());

		List<ArchiveFile> fileList = this.dao.listByQuery(query, ArchiveFile.class);

		List<ArchiveTree> firstTrees = new ArrayList<ArchiveTree>();

		List<ArchiveTree> secondTrees = new ArrayList<ArchiveTree>();

		List<ArchiveTree> firstAttachTrees = new ArrayList<ArchiveTree>();

		List<ArchiveTree> secondAttachTrees = new ArrayList<ArchiveTree>();

		createFirstMenuTree(fileList, firstTrees, "正卷中目录", ArchiveFileProperty.FIRST);
		createFirstMenuTree(fileList, secondTrees, "副卷中目录", ArchiveFileProperty.SECOND);

		createAttachTree(fileList, firstAttachTrees, "正卷中附件", ArchiveFileProperty.FIRST_ATTACH);
		createAttachTree(fileList, secondAttachTrees, "副卷中附件", ArchiveFileProperty.SECOND_ATTACH);

		Map<String, Object> results = new HashMap<String, Object>();

		results.put("data", archive);
		results.put("firstTrees", firstTrees);
		results.put("secondTrees", secondTrees);
		results.put("firstAttachTrees", firstAttachTrees);
		results.put("secondAttachTrees", secondAttachTrees);

		System.out.println(EcUtil.toString(results));

		return results;

	}

	public void addArchive(Archive archive) {
		archive.setArchiveStatus(ArchiveStatus.NEW);
		archive.setAchiveProcessStatus(ProcessStatus.NEW);
		this.dao.insert(archive);
	}
	
	
	public void addArchiveBorrowRecord(ArchiveBorrowing archive){
		this.dao.insert(archive);
	}
	
	
	public Archive getArchive(Archive archive){
		
		return (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);
	}

	private void createAttachTree(List<ArchiveFile> fileList, List<ArchiveTree> firstTrees, String text, ArchiveFileProperty type) {
		ArchiveTree attachTreeMenu = new ArchiveTree();
		attachTreeMenu.setText(text);

		List<ArchiveTree> menuTreeChildren = new ArrayList<ArchiveTree>();

		if (fileList != null) {
			for (ArchiveFile file : fileList) {
				if (file.getArchiveFileProperty().equals(type)) {
					ArchiveTree menuTreeChild1 = new ArchiveTree();
					menuTreeChild1.setText(file.getArchiveFileName());
					menuTreeChild1.setId(UUID.randomUUID().toString());
					menuTreeChild1.setFilePath(replaceScanPath(file));
					menuTreeChildren.add(menuTreeChild1);
				}
			}
		}
		attachTreeMenu.setChildren(menuTreeChildren);
		firstTrees.add(attachTreeMenu);
	}

	private void createFirstMenuTree(List<ArchiveFile> fileList, List<ArchiveTree> firstTrees, String text, ArchiveFileProperty type) {
		ArchiveTree firstMenuTree = new ArchiveTree();
		firstMenuTree.setText(text);

		if (fileList != null) {

			for (ArchiveFile file : fileList) {

				if (file.getArchiveFileProperty().equals(type)) {

					List<ArchiveTree> menuTreeChildren = loadDocMenu(file);
					String filePath = replaceScanPath(file);

					firstMenuTree.setFilePath(replaceScanPath(file));
					firstMenuTree.setId(UUID.randomUUID().toString());

					firstMenuTree.setChildren(menuTreeChildren);
					firstTrees.add(firstMenuTree);

					break;
				}
			}
		}
	}

	private String replaceScanPath(ArchiveFile file) {
		String scanPath = CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);

		String filePath = file.getArchiveFilePath();
		filePath = filePath.replaceAll(scanPath, "");
		return filePath;
	}

	private List<ArchiveTree> loadDocMenu(ArchiveFile file) {
		List<ArchiveTree> menuTreeChildren = new ArrayList<ArchiveTree>();

		int totalPdfPages = PdfUtil.getPdfPages(file.getArchiveFilePath());

		int start = 0;

		int menuPage = 0;

		for (int i = 0; i < totalPdfPages; i++) {

			List<String> lines = PdfUtil.getLines(file.getArchiveFilePath(), i, i + 1);

			for (String line : lines) {

				int pageNumber = getDocumentPageNumber(line);

				if (pageNumber > 0) {

					if (start >= pageNumber) {
						break;
					} else {
						start = pageNumber;
						menuPage = i;
					}

					System.out.println(line + " =======" + pageNumber);

					String data[] = line.split(String.valueOf(pageNumber));
					String menu = data[0].trim().replaceAll(" ", "");

					ArchiveTree menuTreeChild1 = new ArchiveTree();
					menuTreeChild1.setText(menu);
					menuTreeChild1.setPdfMenuPage(pageNumber);
					menuTreeChild1.setFilePath(replaceScanPath(file));
					menuTreeChild1.setId(UUID.randomUUID().toString());

					menuTreeChildren.add(menuTreeChild1);
				} else {

					if (start > 0) {
						break;
					}
				}

			}
		}

		for (ArchiveTree tree : menuTreeChildren) {

			tree.setPdfMenuPage(tree.getPdfMenuPage() + menuPage + 1);
		}

		return menuTreeChildren;
	}

	private int getDocumentPageNumber(String line) {
		int page = 0;

		if (EcUtil.isValid(line)) {

			String data[] = line.split(" ");

			if (line.length() > 5 && line.length() < 30 && data.length > 1) {

				for (String d : data) {

					if (EcUtil.isValid(d)) {
						if (d.contains("-")) {

							String numbers[] = d.split("-");

							if (numbers.length > 0) {
								page = EcUtil.getInteger(numbers[0], 0, false);
							}

						} else {
							page = EcUtil.getInteger(d, 0, false);
						}
					}
				}
			}
		}
		return page;
	}

	private void scanMainDocumentFolder(String path, Archive archive, ArchiveFileProperty scanType) {

		String ignoreFile = ".DS_Store";

		File file = new File(path);

		if (file.exists() && file.isDirectory()) {

			File subFiles[] = file.listFiles();
			for (File subFile : subFiles) {

				if (subFile.isFile() && !ignoreFile.contains(subFile.getName()) && !subFile.getName().startsWith(".")) {
					ArchiveFile arfile = new ArchiveFile();
					arfile.setArchiveFileName(subFile.getName());
					arfile.setArchiveFileLastModifyDate(new Date(subFile.lastModified()));
					arfile.setArchiveId(archive.getId());
					arfile.setArchiveFileProperty(scanType);
					arfile.setArchiveFilePath(subFile.getAbsolutePath());

					if (scanType.equals(ArchiveFileProperty.FIRST) || scanType.equals(ArchiveFileProperty.SECOND)) {

						if (EcUtil.isEmpty(archive.getArchiveApplicant())) {

							getDocumentInfo(subFile.getAbsolutePath(), archive);

						}
					}

					this.dao.insert(arfile);
				}

			}
		}

	}

	private void getDocumentInfo(String absolutePath, Archive archive) {

		List<String> lines = PdfUtil.getLines(absolutePath, 0, 1);
		String code = null;
		String reason = null;
		String results = null;
		String applicant = null;
		String applicantBad = null;
		String thirdApplicant = null;
		String judgePerson = null;
		String dateType = "";

		for (String line : lines) {
			line = line.replaceAll(" ", "").trim();

			if (line.contains("年度第")) {
				code = line;
			} else if (line.startsWith("案由")) {
				reason = line.replaceFirst("案由", "");
			} else if (line.startsWith("处理结果")) {
				reason = line.replaceFirst("处理结果", "");
			} else if (line.startsWith("申请人")) {
				applicant = line.replaceFirst("申请人", "");
			} else if (line.startsWith("被申请人")) {
				applicantBad = line.replaceFirst("被申请人", "");
			} else if (line.startsWith("第三人")) {
				thirdApplicant = line.replaceFirst("第三人", "");
			} else if (line.startsWith("承办人")) {
				judgePerson = line.replaceFirst("承办人", "");
			} else if (line.startsWith("立案")) {
				dateType = "立案";
			} else if (line.startsWith("结案")) {
				dateType = "结案";
			} else if (line.startsWith("归档")) {
				dateType = "归档";
			} else if (line.startsWith("号数")) {
				dateType = "号数";
			} else if (line.contains("年") && line.contains("月") && line.contains("日")) {

				if (dateType == "立案") {

					System.out.println(line);
				} else if (dateType == "结案") {
					System.out.println(line);
				} else if (dateType == "归档") {
					System.out.println(line);
				}

				dateType = "";
			} else if (dateType == "号数") {
				System.out.println(line);
				dateType = "";
			}

		}

	}
}
