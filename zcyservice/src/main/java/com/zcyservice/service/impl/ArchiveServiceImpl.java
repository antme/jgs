package com.zcyservice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.util.DateUtil;
import com.zcy.util.EcUtil;
import com.zcy.util.PdfUtil;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.Archive.ArchiveStatus;
import com.zcyservice.bean.Archive.ProcessStatus;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.bean.ArchiveFile.ArchiveFileProperty;
import com.zcyservice.bean.vo.ArchiveReport;
import com.zcyservice.bean.vo.ArchiveTree;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.AbstractArchiveService;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.ZcyServiceConstants;
import com.zcyservice.util.ZcyUtil;

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

					DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
					query.and(Archive.FOLDER_CODE, subFile.getName());

					if (!this.dao.exists(query)) {
						Archive arc = new Archive();
						arc.setArchiveCode(subFile.getName());
						arc.setFolderCode(subFile.getName());
						arc.setArchiveStatus(ArchiveStatus.ARCHIVED);
						arc.setYear(Calendar.getInstance().get(Calendar.YEAR));
//						arc.setArchiveName(subFile.getName());

						this.dao.insert(arc);

						scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中", arc, ArchiveFileProperty.FIRST);

						scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中", arc, ArchiveFileProperty.SECOND);

						scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中" + File.separator + "附件", arc, ArchiveFileProperty.FIRST_ATTACH);

						scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中" + File.separator + "附件", arc, ArchiveFileProperty.SECOND_ATTACH);
					}

				}
			}

		} else {
			logger.error("请创建好扫描目录: " + scanPath);
		}
	}

	public EntityResults<Archive> listArchives(Archive archive) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		mergeArchiveQuery(query, archive);

		return this.dao.listByQueryWithPagnation(query, Archive.class);
	}

	private void mergeArchiveQuery(DataBaseQueryBuilder query, Archive archive) {

		DataBaseQueryBuilder childQuery = new DataBaseQueryBuilder(Archive.TABLE_NAME);

		if (EcUtil.isValid(archive.getArchiveCode())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_CODE, archive.getArchiveCode());
		}

		if (EcUtil.isValid(archive.getArchiveName())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_NAME, archive.getArchiveName());
		}

		if (EcUtil.isValid(archive.getArchiveStatus())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_STATUS, archive.getArchiveStatus());
		}

		if (EcUtil.isValid(archive.getStartDate())) {
			childQuery.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Archive.ARCHIVE_DATE, archive.getStartDate());
		}

		if (EcUtil.isValid(archive.getEndDate())) {
			childQuery.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Archive.ARCHIVE_DATE, archive.getEndDate());
		}

		if (EcUtil.isValid(archive.getArchiveApplicant())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_APPLICANT, archive.getArchiveApplicant());
		}

		if (EcUtil.isValid(archive.getArchiveOppositeApplicant())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_OPPOSITE_APPLICANT, archive.getArchiveOppositeApplicant());
		}

		if (EcUtil.isValid(archive.getArchiveJudge())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_JUDGE, archive.getArchiveJudge());
		}

		if (EcUtil.isValid(archive.getKeyword())) {

			DataBaseQueryBuilder childKeyWordQuery = new DataBaseQueryBuilder(Archive.TABLE_NAME);
			childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_APPLICANT, archive.getKeyword());
			childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_JUDGE, archive.getKeyword());
			childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_NAME, archive.getKeyword());
			childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_CODE, archive.getKeyword());
			childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_OPPOSITE_APPLICANT, archive.getKeyword());

			// FIXME: 全文搜索

			childQuery.and(childKeyWordQuery);

		}

		if (EcUtil.isValid(childQuery.getQueryStr())) {
			query.and(childQuery);
		}

	}

	public EntityResults<Archive> listNeddApproveArchives(Archive archive) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.or(Archive.ACHIVE_PROCESS_STATUS, ProcessStatus.NEW);

		query.or(Archive.ACHIVE_PROCESS_STATUS, ProcessStatus.DESTROYING);

		mergeArchiveQuery(query, archive);

		return this.dao.listByQueryWithPagnation(query, Archive.class);

	}

	public void approveArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.APPROVED);
		this.dao.updateById(archive);
	}

	public void rejectArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.REJECTED);
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

		if (EcUtil.isValid(archive.getId())) {
			this.dao.updateById(archive);
		} else {

			archive.setArchiveStatus(ArchiveStatus.NEW);
			archive.setArchiveProcessStatus(ProcessStatus.NEW);

			if (EcUtil.isEmpty(archive.getMainFile())) {
				throw new ResponseException("请上传档案");
			}
						
			if(EcUtil.isEmpty(archive.getYear())){				
				Calendar c = Calendar.getInstance();
				c.setTime(archive.getArchiveOpenDate());
				archive.setYear(c.get(Calendar.YEAR));
			}

			archive = (Archive) dao.insert(archive);
			initArchiveFiles(archive);
		}
	}

	private void initArchiveFiles(Archive archive) {

		if (EcUtil.isValid(archive.getMainFile())) {
			moveFile(archive, archive.getMainFile(), "正卷中");
		}

		if (EcUtil.isValid(archive.getMainFilkeAttach())) {
			String files[] = archive.getMainFilkeAttach().split(",");
			for (String fileName : files) {
				moveFile(archive, fileName, "正卷中附件");
			}
		}

		if (EcUtil.isValid(archive.getSecondFile())) {
			moveFile(archive, archive.getSecondFile(), "副卷中");
		}

		if (EcUtil.isValid(archive.getSecondFileAttach())) {
			String files[] = archive.getSecondFileAttach().split(",");
			for (String fileName : files) {
				moveFile(archive, fileName, "副卷中附件");
			}
		}
	}

	public void moveFile(Archive archive, String fileName, String fileType) {
		String path = ZcyUtil.getUploadPath() + File.separator + fileName;
		try {
			File file = new File(path);
			InputStream in = new FileInputStream(file);
			String targetFule = ZcyUtil.getDocumentPath() + File.separator + archive.getArchiveCode() + File.separator + fileType + File.separator + file.getName();
			EcUtil.createFile(targetFule, in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addArchiveBorrowRecord(ArchiveBorrowing archive) {
		if (EcUtil.isValid(archive.getId())) {
			this.dao.updateById(archive);
		} else {
			this.dao.insert(archive);
		}
	}

	public Archive getArchive(Archive archive) {

		return (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);
	}

	public EntityResults<ArchiveBorrowing> listArchiveBorrowRecord(Archive archive) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveBorrowing.TABLE_NAME);
		query.join(ArchiveBorrowing.TABLE_NAME, Archive.TABLE_NAME, ArchiveBorrowing.ARCHIVE_ID, Archive.ID);
		query.joinColumns(Archive.TABLE_NAME, new String[] { Archive.ARCHIVE_CODE, Archive.ARCHIVE_NAME });
		query.limitColumns(new ArchiveBorrowing().getColumnList());

		return this.dao.listByQueryWithPagnation(query, ArchiveBorrowing.class);
	}

	public BaseEntity getArchiveBorrowRecord(ArchiveBorrowing archive) {

		return this.dao.findById(archive.getId(), ArchiveBorrowing.TABLE_NAME, ArchiveBorrowing.class);
	}

	public void destroyArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.DESTROYING);

		this.dao.updateById(archive);

	}
	
	
	public List<ArchiveReport> countArchive(SearchVo searchvo) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.distinct(Archive.YEAR);
		List<ArchiveReport> reports = this.dao.distinctQuery(query, ArchiveReport.class);

		return reports;

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
		// filePath = filePath.replaceAll(scanPath, "");
		filePath = filePath.substring(scanPath.length() + 1);
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
							this.dao.updateById(archive);

						}
					}

					this.dao.insert(arfile);
				}

			}
		}

	}

	public void getDocumentInfo(String absolutePath, Archive archive) {

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

				Date dateTime = DateUtil.getDateTime(line.trim().replaceAll(" ", ""));
				Calendar c = Calendar.getInstance();
				c.setTime(dateTime);
				archive.setYear(c.get(Calendar.YEAR));
				if (dateType == "立案") {

					System.out.println(line);
					archive.setArchiveOpenDate(dateTime);

				} else if (dateType == "结案") {

					System.out.println(line);
					archive.setArchiveCloseDate(dateTime);

				} else if (dateType == "归档") {

					System.out.println(line);
					archive.setArchiveDate(dateTime);

				}

				dateType = "";
			} else if (dateType == "号数") {
				System.out.println(line);
				dateType = "";
			}

		}

		archive.setArchiveCode(code);
		archive.setArchiveName(reason);
		archive.setArchiveResult(results);
		archive.setArchiveApplicant(applicant);
		archive.setArchiveOppositeApplicant(applicantBad);
		archive.setArchiveThirdPerson(thirdApplicant);
		archive.setArchiveJudge(judgePerson);


	}
}
