package com.zcyservice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.ConvertThread;
import lib.pdf2swf;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.lucene.IndexFiles;
import com.zcy.util.DateUtil;
import com.zcy.util.EcUtil;
import com.zcy.util.PdfUtil;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.Archive.ArchiveStatus;
import com.zcyservice.bean.Archive.ProcessStatus;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.bean.ArchiveFile.ArchiveFileProperty;
import com.zcyservice.bean.vo.ArchiveTree;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.AbstractArchiveService;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.ZcyServiceConstants;
import com.zcyservice.util.ZcyUtil;

@Service(value = "archiveService")
public class ArchiveServiceImpl extends AbstractArchiveService implements IArchiveService {
	private static Logger logger = LogManager.getLogger(ArchiveServiceImpl.class);

	@Transactional
	public void scanArchines() {

		String scanPath = CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);
		logger.info("Scan " + scanPath);

		File file = new File(scanPath);

		if (file.exists() && file.isDirectory()) {

			File subFiles[] = file.listFiles();
			for (File subFile : subFiles) {
				if (subFile.isDirectory()) {
					scanArchiveFiles(subFile, Archive.ARCHIVE_TYPE_MAIN);
					scanArchiveFiles(subFile, Archive.ARCHIVE_TYPE_SECOND);
				}
			}

		} else {
			logger.error("请创建好扫描目录: " + scanPath);
		}
		
		new ConvertThread(true).start();
	}

	private void scanArchiveFiles(File subFile, String archiveType) {
		Archive arc = new Archive();
		arc.setArchiveCode(subFile.getName());
		arc.setFolderCode(subFile.getName());
		arc.setArchiveStatus(ArchiveStatus.ARCHIVED);
		arc.setArchiveProcessStatus(ProcessStatus.SCAN_IMPORTED);
		arc.setArchiveType(archiveType);
		List<ArchiveFile> mainFiles = null;
		List<ArchiveFile> attachFiles = null;
		if (archiveType.equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {
			mainFiles = scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷宗", arc, ArchiveFileProperty.MAIN_FILE);
			attachFiles = scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷宗附件", arc, ArchiveFileProperty.ATTACH_FILE);

		} else if (archiveType.equalsIgnoreCase(Archive.ARCHIVE_TYPE_SECOND)) {

			mainFiles = scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷宗", arc, ArchiveFileProperty.MAIN_FILE);
			attachFiles = scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷宗附件", arc, ArchiveFileProperty.ATTACH_FILE);

		}
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ARCHIVE_CODE, arc.getArchiveCode());
		query.and(Archive.ARCHIVE_TYPE, archiveType);

		if (!this.dao.exists(query)) {

			if (EcUtil.isValid(arc.getArchiveCode())) {

				if (mainFiles != null && mainFiles.size() == 1) {
					arc.setIsNew(true);

					this.dao.insert(arc);

					if (attachFiles != null) {

						for (ArchiveFile file : attachFiles) {
							file.setArchiveId(arc.getId());

							saveArchiveFile(file);
						}
					}

					for (ArchiveFile file : mainFiles) {
						file.setArchiveId(arc.getId());
						
						saveArchiveFile(file);
					}

				} else {

					if (mainFiles.isEmpty()) {
						logger.info("主文件为空: " + arc.getFolderCode());

					} else {
						logger.info("主文件不止一个: " + arc.getFolderCode());
					}

				}

			} else {
				logger.info("Ignore archive folder due to cannot get code: " + arc.getFolderCode());
			}

		} else {
			logger.debug("Ignore archive folder due to archive exists: " + arc.getFolderCode());
		}

	}

	public void saveArchiveFile(ArchiveFile file) {

		try {
			this.dao.insert(file);
		} catch (Exception e) {
			// 大数据乱码情况，过滤掉

			if (file.getArchiveTextData() != null) {

				try {
					file.setArchiveTextData(new String(file.getArchiveTextData().getBytes(), "UTF-8"));
					this.dao.insert(file);
				} catch (Exception e1) {
					file.setArchiveTextData(null);
					this.dao.insert(file);
				}

			} else {
				throw e;
			}

		}

		if(EcUtil.isValid(file.getArchiveFilePath())){
			ConvertThread.queue.offer(file.getArchiveFilePath());
		}
		
	}

	public EntityResults<Archive> listArchives(Archive archive) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		mergeArchiveQuery(query, archive);

		return this.dao.listByQueryWithPagnation(query, Archive.class);
	}

	public EntityResults<Archive> listNewArchives(Archive archive) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, Archive.ARCHIVE_PROCESS_STATUS,
		        new String[] { ProcessStatus.DRAFT.toString(), ProcessStatus.NEW.toString(), ProcessStatus.DESTROYING.toString(), ProcessStatus.REJECTED.toString() });

		mergeArchiveQuery(query, archive);

		return this.dao.listByQueryWithPagnation(query, Archive.class);
	}

	public EntityResults<Archive> listPubArchives(Archive archive) {

		DataBaseQueryBuilder query = getPubQuery();

		mergeArchiveQuery(query, archive);

		return this.dao.listByQueryWithPagnation(query, Archive.class);

	}

	public DataBaseQueryBuilder getPubQuery() {
	    DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(DataBaseQueryOpertion.NOT_IN, Archive.ARCHIVE_PROCESS_STATUS, new String[] { ProcessStatus.DRAFT.toString(), ProcessStatus.NEW.toString(),
		        ProcessStatus.DESTROYING.toString(), ProcessStatus.REJECTED.toString() });
	    return query;
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
			childQuery.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Archive.CREATED_ON, archive.getStartDate());
		}

		if (EcUtil.isValid(archive.getEndDate())) {
			childQuery.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Archive.CREATED_ON, archive.getEndDate());
		}

		if (EcUtil.isValid(archive.getArchiveApplicant())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_APPLICANT, archive.getArchiveApplicant());
		}
		
		if (EcUtil.isValid(archive.getArchiveType())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_TYPE, archive.getArchiveType());
		}

		if (EcUtil.isValid(archive.getArchiveOppositeApplicant())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_OPPOSITE_APPLICANT, archive.getArchiveOppositeApplicant());
		}

		if (EcUtil.isValid(archive.getArchiveJudge())) {
			childQuery.and(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_JUDGE, archive.getArchiveJudge());
		}

		String keyword = archive.getKeyword();
		if (EcUtil.isValid(keyword)) {

			DataBaseQueryBuilder childKeyWordQuery = new DataBaseQueryBuilder(Archive.TABLE_NAME);

			String[] keyWordItems = keyword.split(" ");
			for (String wordItem : keyWordItems) {

				childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_APPLICANT, wordItem);
				childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_JUDGE, wordItem);
				childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_NAME, wordItem);
				childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_CODE, wordItem);
				childKeyWordQuery.or(DataBaseQueryOpertion.LIKE, Archive.ARCHIVE_OPPOSITE_APPLICANT, wordItem);

			}

			DataBaseQueryBuilder fileQuery = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
			fileQuery.limitColumns(new String[] { ArchiveFile.ARCHIVE_ID });

			for (String wordItem : keyWordItems) {
				fileQuery.or(DataBaseQueryOpertion.LIKE, ArchiveFile.ARCHIVE_TEXT_DATA, wordItem);
			}

			List<ArchiveFile> files = this.dao.listByQuery(fileQuery, ArchiveFile.class);
			Set<String> ids = new HashSet<String>();

			for (ArchiveFile file : files) {
				if (EcUtil.isValid(file.getArchiveId())) {
					ids.add(file.getArchiveId());
				}
			}

			if (EcUtil.isValid(ids)) {
				childKeyWordQuery.or(DataBaseQueryOpertion.IN, Archive.ID, ids);
			}

			childQuery.and(childKeyWordQuery);

		}

		if (EcUtil.isValid(childQuery.getQueryStr())) {
			query.and(childQuery);
		}

	}

	public EntityResults<Archive> listNeddApproveArchives(Archive archive) {
		DataBaseQueryBuilder query = getNewApproveArchiveBuilder();
		mergeArchiveQuery(query, archive);
		return this.dao.listByQueryWithPagnation(query, Archive.class);

	}

	public DataBaseQueryBuilder getNewApproveArchiveBuilder() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ARCHIVE_PROCESS_STATUS, ProcessStatus.NEW);
		return query;
	}

	public EntityResults<Archive> listNeedDestoryApproveArchives(Archive archive) {
		DataBaseQueryBuilder query = getNeedDestroyApproveBuilder();
		mergeArchiveQuery(query, archive);
		return this.dao.listByQueryWithPagnation(query, Archive.class);
	}

	public DataBaseQueryBuilder getNeedDestroyApproveBuilder() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ARCHIVE_PROCESS_STATUS, ProcessStatus.DESTROYING);
		return query;
	}

	public DataBaseQueryBuilder getNeedRejectBuilder() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ARCHIVE_PROCESS_STATUS, ProcessStatus.REJECTED);
		return query;
	}

	public void approveArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.APPROVED);
		this.dao.updateById(archive);
	}

	public void approveDestroyArchive(Archive archive) {

		if (EcUtil.isEmpty(archive.getId())) {
			throw new ResponseException("请选择要批准的卷宗");
		}

		archive = (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);

		String folderCode = archive.getFolderCode();

		String archiveType = archive.getArchiveType();

		deleteArchiveFiles(folderCode, archiveType);
		checkValue(archive, Archive.ARCHIVE_PROCESS_STATUS, ProcessStatus.DESTROYING);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
		query.and(ArchiveFile.ARCHIVE_ID, archive.getId());
		this.dao.deleteByQuery(query);

		DataBaseQueryBuilder bquery = new DataBaseQueryBuilder(ArchiveBorrowing.TABLE_NAME);
		bquery.and(ArchiveFile.ARCHIVE_ID, archive.getId());
		this.dao.deleteByQuery(bquery);

		this.dao.deleteById(archive);

	}

	public void deleteArchiveFiles(String folderCode, String archiveType) {
		if (archiveType.equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {

			deleteFiles(new File(ZcyUtil.getDocumentPath() + File.separator + folderCode + File.separator + "正卷宗" + File.separator));
			deleteFiles(new File(ZcyUtil.getDocumentPath() + File.separator + folderCode + File.separator + "正卷宗附件" + File.separator));

		} else if (archiveType.equalsIgnoreCase(Archive.ARCHIVE_TYPE_SECOND)) {
			deleteFiles(new File(ZcyUtil.getDocumentPath() + File.separator + folderCode + File.separator + "副卷宗" + File.separator));
			deleteFiles(new File(ZcyUtil.getDocumentPath() + File.separator + folderCode + File.separator + "副卷宗附件" + File.separator));

		}
	}

	private void deleteFiles(File file) {

		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					deleteFiles(f);
				}
			} else {
				file.delete();
			}
		}
	}

	public void rejectArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.REJECTED);
		this.dao.updateById(archive);

	}

	public void rejectDestoryArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.DESTROYED_REJECTED);
		this.dao.updateById(archive);
	}

	public Map<String, Object> listArchiveFiles(Archive archive) {

		if (archive.getId() == null) {
			throw new ResponseException("请输入查询ID");
		}

		archive = (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);

		List<ArchiveTree> firstTrees = new ArrayList<ArchiveTree>();
		List<ArchiveTree> firstAttachTrees = new ArrayList<ArchiveTree>();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
		query.and(ArchiveFile.ARCHIVE_ID, archive.getId());
		query.and(ArchiveFile.ARCHIVE_FILE_PROPERTY, ArchiveFileProperty.MAIN_FILE);
		List<ArchiveFile> fileList = this.dao.listByQuery(query, ArchiveFile.class);
		createFirstMenuTree(fileList, firstTrees, "目录", true);

		query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
		query.and(ArchiveFile.ARCHIVE_ID, archive.getId());
		query.and(ArchiveFile.ARCHIVE_FILE_PROPERTY, ArchiveFileProperty.ATTACH_FILE);
		fileList = this.dao.listByQuery(query, ArchiveFile.class);
		createAttachTree(fileList, firstAttachTrees, "附件");

		Map<String, Object> results = new HashMap<String, Object>();

		results.put("data", archive);
		results.put("firstTrees", firstTrees);
		results.put("firstAttachTrees", firstAttachTrees);

		return results;

	}

	public Archive addArchive(Archive archive) {

		if (EcUtil.isValid(archive.getId())) {

			DataBaseQueryBuilder existsQuery = new DataBaseQueryBuilder(Archive.TABLE_NAME);
			existsQuery.and(DataBaseQueryOpertion.NOT_EQUALS, ArchiveFile.ID, archive.getId());
			existsQuery.and(Archive.ARCHIVE_TYPE, archive.getArchiveType());
			existsQuery.and(Archive.ARCHIVE_CODE, archive.getArchiveCode());

			if (this.dao.exists(existsQuery)) {
				throw new ResponseException("此卷宗已经存在，请不要重复上传");
			}

			Archive oldArchive = (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);

			if (oldArchive.getArchiveProcessStatus().equals(ProcessStatus.REJECTED)) {
				archive.setArchiveProcessStatus(ProcessStatus.NEW);
			} else if (oldArchive.getArchiveProcessStatus().equals(ProcessStatus.DESTROYED_REJECTED)) {
				archive.setArchiveProcessStatus(ProcessStatus.DESTROYING);
			}

			List<ArchiveFile> newArfiles = createArchiveFiles(archive);

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
			query.and(ArchiveFile.ARCHIVE_ID, archive.getId());
			List<ArchiveFile> dlist = this.dao.listByQuery(query, ArchiveFile.class);

			Set<String> pathSet = new HashSet<String>();
			for (ArchiveFile naf : newArfiles) {
				pathSet.add(ZcyUtil.getDocumentPath() + File.separator + naf.getArchiveFilePath());

			}
			for (ArchiveFile af : dlist) {
				String filePath = ZcyUtil.getDocumentPath() + File.separator + af.getArchiveFilePath();
				if (!pathSet.contains(filePath)) {
					deleteFiles(new File(filePath));
				}
			}

			this.dao.deleteByQuery(query);

			for (ArchiveFile naf : newArfiles) {

				if (EcUtil.isEmpty(naf.getId())) {
					naf.setArchiveId(archive.getId());
					saveArchiveFile(naf);
				}

			}

			this.dao.updateById(archive);
			

		} else {

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
			query.and(Archive.ARCHIVE_CODE, archive.getArchiveCode());
			query.and(Archive.ARCHIVE_TYPE, archive.getArchiveType());

			if (this.dao.exists(query)) {
				throw new ResponseException(String.format("此档案【%s】已经存在，请不要重复上传", archive.getArchiveCode()));
			}

			archive.setArchiveStatus(ArchiveStatus.NEW);
			archive.setArchiveProcessStatus(ProcessStatus.NEW);
			archive.setFolderCode(archive.getArchiveCode());

			if (EcUtil.isEmpty(archive.getMainFile())) {
				throw new ResponseException("请上传档案");
			}



			archive.setIsNew(true);
			deleteArchiveFiles(archive.getFolderCode(), archive.getArchiveType());
			List<ArchiveFile> files = createArchiveFiles(archive);
			
			if (files.size() > 0 && EcUtil.isEmpty(archive.getId())) {
				dao.insert(archive);
				for (ArchiveFile afile : files) {
					afile.setArchiveId(archive.getId());
					saveArchiveFile(afile);
				}
			}
		}
		new ConvertThread(true).start();
		return archive;
	}

	private List<ArchiveFile> createArchiveFiles(Archive archive) {

		if (EcUtil.isValid(archive.getMainFile())) {

			if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {
				moveFile(archive, archive.getMainFile(), "正卷宗");
			}
			if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_SECOND)) {
				moveFile(archive, archive.getMainFile(), "副卷宗");
			}
		}

		if (EcUtil.isValid(archive.getMainFileAttach())) {
			String files[] = archive.getMainFileAttach().split(",");

			for (String fileName : files) {

				if (EcUtil.isValid(fileName)) {
					if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {
						moveFile(archive, fileName, "正卷宗附件");
					}
					if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_SECOND)) {
						moveFile(archive, fileName, "副卷宗附件");
					}
				}

			}
		}

		File file = new File(ZcyUtil.getDocumentPath() + File.separator + archive.getArchiveCode());

		List<ArchiveFile> files = new ArrayList<ArchiveFile>();
		if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {
			files.addAll(scanMainDocumentFolder(file.getAbsolutePath() + File.separator + "正卷宗", archive, ArchiveFileProperty.MAIN_FILE));
			files.addAll(scanMainDocumentFolder(file.getAbsolutePath() + File.separator + "正卷宗附件", archive, ArchiveFileProperty.ATTACH_FILE));
		}

		if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_SECOND)) {
			files.addAll(scanMainDocumentFolder(file.getAbsolutePath() + File.separator + "副卷宗", archive, ArchiveFileProperty.MAIN_FILE));
			files.addAll(scanMainDocumentFolder(file.getAbsolutePath() + File.separator + "副卷宗附件", archive, ArchiveFileProperty.ATTACH_FILE));
		}



		return files;

	}

	public void moveFile(Archive archive, String fileName, String fileType) {
		String path = ZcyUtil.getUploadPath() + File.separator + fileName;
		try {
			File file = new File(path);

			if (file.exists()) {
				InputStream in = new FileInputStream(file);
				String targetFule = ZcyUtil.getDocumentPath() + File.separator + archive.getArchiveCode() + File.separator + fileType + File.separator + file.getName();
				EcUtil.createFile(targetFule, in);
			}
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

		archive = (Archive) this.dao.findById(archive.getId(), Archive.TABLE_NAME, Archive.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveFile.TABLE_NAME);
		query.and(ArchiveFile.ARCHIVE_ID, archive.getId());
		query.limitColumns(new String[] { ArchiveFile.ID, ArchiveFile.ARCHIVE_FILE_PROPERTY, ArchiveFile.ARCHIVE_FILE_PATH, ArchiveFile.ARCHIVE_FILE_NAME });

		archive.setFiles(this.dao.listByQuery(query, ArchiveFile.class));

		return archive;

	}

	public EntityResults<ArchiveBorrowing> listArchiveBorrowRecord(Archive archive) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ArchiveBorrowing.TABLE_NAME);
		query.join(ArchiveBorrowing.TABLE_NAME, Archive.TABLE_NAME, ArchiveBorrowing.ARCHIVE_ID, Archive.ID);

		DataBaseQueryBuilder archiveQuery = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		mergeArchiveQuery(archiveQuery, archive);
		List<Archive> arList = this.dao.listByQuery(archiveQuery, Archive.class);

		Set<String> ids = new HashSet<String>();
		for (Archive ac : arList) {
			ids.add(ac.getId());

		}


		List<String> columnList = new Archive().getColumnList();
		columnList.remove(Archive.CREATED_ON);
		columnList.remove(Archive.UPDATED_ON);
		columnList.remove(Archive.ID);
		columnList.remove(Archive.CREATOR_ID);
		query.joinColumns(Archive.TABLE_NAME, columnList);
		query.limitColumns(new ArchiveBorrowing().getColumnList());

		if (archiveQuery.getQueryStr() != null) {
			query.and(DataBaseQueryOpertion.IN, ArchiveBorrowing.TABLE_NAME + "." + ArchiveBorrowing.ARCHIVE_ID, ids);
		}
		
		return this.dao.listByQueryWithPagnation(query, ArchiveBorrowing.class);
	}

	public BaseEntity getArchiveBorrowRecord(ArchiveBorrowing archive) {

		return this.dao.findById(archive.getId(), ArchiveBorrowing.TABLE_NAME, ArchiveBorrowing.class);
	}

	public void destroyArchive(Archive archive) {

		archive.setArchiveProcessStatus(ProcessStatus.DESTROYING);

		this.dao.updateById(archive);

	}

	public EntityResults<Archive> countArchive(SearchVo searchvo) {

		if (searchvo.getReportType() == null) {
			searchvo.setReportType(Archive.YEAR);
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.groupBy(searchvo.getReportType(), false);
		query.and(Archive.ARCHIVE_TYPE, Archive.ARCHIVE_TYPE_MAIN);

		List<Archive> allReports = this.dao.listByQuery(query, Archive.class);

		EntityResults<Archive> reports = this.dao.listByQueryWithPagnation(query, Archive.class);

		List<Archive> list = reports.getEntityList();
		reports.getPagnation().setTotal(allReports.size());

		for (Archive report : list) {

			if (searchvo.getReportType().equalsIgnoreCase(Archive.YEAR)) {
				report.setReportKey(report.getYear().toString());

			} else if (searchvo.getReportType().equalsIgnoreCase(Archive.ARCHIVE_OPPOSITE_APPLICANT)) {
				report.setReportKey(report.getArchiveOppositeApplicant());

			} else if (searchvo.getReportType().equalsIgnoreCase(Archive.ARCHIVE_APPLICANT)) {
				report.setReportKey(report.getArchiveApplicant());

			}
		}

		return reports;

	}

	public Map<String, Object> listArchiveReportByYear() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		
		query.distinct(Archive.YEAR);
		List<Archive> reports = this.dao.distinctQuery(query, Archive.class);



		Map<String, Object> yearsCountMap = new HashMap<String, Object>();
		for (Archive archive : reports) {

			DataBaseQueryBuilder cquery = getPubQuery();
			cquery.and(Archive.YEAR, archive.getYear());
			
			
			int count = this.dao.count(cquery);
			yearsCountMap.put("year" + archive.getYear(), count);

			Map<String, Object> colorMap = new HashMap<String, Object>();
			colorMap.put("y", count);

		}

		return yearsCountMap;

	}

	public String downloadArchiveFile(Archive archive, HttpServletRequest request, HttpServletResponse response) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(Archive.ID, archive.getId());
		query.limitColumns(new String[] { Archive.FOLDER_CODE, Archive.ARCHIVE_TYPE, Archive.ID });

		archive = (Archive) this.dao.findOneByQuery(query, Archive.class);

		String sourcePath = ZcyUtil.getDocumentPath() + File.separator + archive.getFolderCode();

		String targetName = archive.getFolderCode() + archive.getArchiveType() + ".zip";

		if (archive.getArchiveType().equalsIgnoreCase(Archive.ARCHIVE_TYPE_MAIN)) {
			new IndexFiles().compressedFile(sourcePath, ZcyUtil.getUploadPath(), targetName, "正卷宗");
		} else {
			new IndexFiles().compressedFile(sourcePath, ZcyUtil.getUploadPath(), targetName, "副卷宗");
		}

		return targetName;

	}

	private void createAttachTree(List<ArchiveFile> fileList, List<ArchiveTree> firstTrees, String text) {
		ArchiveTree attachTreeMenu = new ArchiveTree();
		attachTreeMenu.setText(text);

		List<ArchiveTree> menuTreeChildren = new ArrayList<ArchiveTree>();

		if (fileList != null) {
			for (ArchiveFile file : fileList) {

				ArchiveTree menuTreeChild1 = new ArchiveTree();
				menuTreeChild1.setText(file.getArchiveFileName());
				menuTreeChild1.setId(UUID.randomUUID().toString());
				menuTreeChild1.setFilePath(replaceScanPath(file));
				menuTreeChildren.add(menuTreeChild1);

			}
		}
		attachTreeMenu.setChildren(menuTreeChildren);
		firstTrees.add(attachTreeMenu);
	}

	private void createFirstMenuTree(List<ArchiveFile> fileList, List<ArchiveTree> firstTrees, String text, boolean menuDoc) {
		ArchiveTree tree = new ArchiveTree();
		tree.setText(text);

		if (fileList != null) {

			for (ArchiveFile file : fileList) {

				if (menuDoc) {
					List<ArchiveTree> menuTreeChildren = loadDocMenu(file);

					tree.setFilePath(replaceScanPath(file));
					tree.setId(UUID.randomUUID().toString());

					tree.setChildren(menuTreeChildren);
				}
				firstTrees.add(tree);

			}
		}
	}

	private String replaceScanPath(ArchiveFile file) {
		String scanPath = CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);

		String filePath = file.getArchiveFilePath();
		// filePath = filePath.replaceAll(scanPath, "");
		if (filePath.contains(scanPath)) {
			filePath = filePath.substring(scanPath.length() + 1);
		}
		return filePath;
	}

	private List<ArchiveTree> loadDocMenu(ArchiveFile file) {
		List<ArchiveTree> menuTreeChildren = new ArrayList<ArchiveTree>();

		String archiveFilePath = ZcyUtil.getDocumentPath() + File.separator + file.getArchiveFilePath();
		int totalPdfPages = PdfUtil.getPdfPages(archiveFilePath);

		int start = 0;

		int menuPage = 0;

		for (int i = 0; i < totalPdfPages; i++) {

			List<String> lines = PdfUtil.getLines(archiveFilePath, i, i + 1);

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

	private List<ArchiveFile> scanMainDocumentFolder(String path, Archive archive, ArchiveFileProperty scanType) {

		String ignoreFile = ".DS_Store";

		File file = new File(path);

		List<ArchiveFile> files = new ArrayList<ArchiveFile>();

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
					arfile.setArchiveFilePath(replaceScanPath(arfile));

					arfile.setArchiveTextData(new IndexFiles().getDocString(subFile.getAbsolutePath()));

					if (scanType.equals(ArchiveFileProperty.MAIN_FILE)) {

						if (EcUtil.isEmpty(archive.getArchiveApplicant())) {

							getDocumentInfo(subFile.getAbsolutePath(), archive);

						}

						if (subFile.getName().toLowerCase().contains(".pdf")) {
							
							
							files.add(arfile);
						}
					} else {

						files.add(arfile);
					}

				}

			}
		}

		return files;

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
		String dateType = null;

		for (String line : lines) {
			line = line.replaceAll(" ", "").trim();
			line = line.replaceAll("'", "");
			line = line.replaceAll("\\.", "");

			if (line.contains("年度第") || line.contains("度第") || line.contains("年度")) {
				code = line;
				code = code.replaceAll("二0一三", "二〇一三");
				code = code.replaceAll("二0一四", "二〇一四");
				code = code.replaceAll("◦", "");
				code = code.replaceAll("号-", "号");
			} else if (line.startsWith("案由")) {
				reason = line.replaceFirst("案由", "");
			} else if (line.startsWith("处理结果")) {
				results = line.replaceFirst("处理结果", "");
			} else if (line.startsWith("申请人")) {
				applicant = line.replaceFirst("申请人", "");
			} else if (line.startsWith("被申请人")) {
				applicantBad = line.replaceFirst("被申请人", "");
			} else if (line.startsWith("第三人")) {
				thirdApplicant = line.replaceFirst("第三人", "");
			} else if (line.startsWith("承办人")) {
				judgePerson = line.replaceFirst("承办人", "");
			} else if (line.startsWith("立案") || (line.contains("案日期") && dateType == null)) {
				dateType = "立案";
			} else if (line.startsWith("结案") || (line.contains("案日期") && dateType == "立案")) {
				dateType = "结案";
			} else if (line.startsWith("归档") || (line.contains("档日期") && dateType == "结案")) {
				dateType = "归档";
			} else if (line.contains("号数")) {
				dateType = "号数";
			} else if ((line.contains("年") && line.contains("月") && line.contains("日")) && dateType != null) {

				line = line.replaceAll("丨", "");
				Date dateTime = DateUtil.getDateTime(line.trim().replaceAll(" ", ""));

				if (dateTime != null) {

					if (dateType == "立案") {

						archive.setArchiveOpenDate(dateTime);

					} else if (dateType == "结案") {
						archive.setArchiveCloseDate(dateTime);

					} else if (dateType == "归档") {
						archive.setArchiveDate(dateTime);

					}

					if (EcUtil.isEmpty(archive.getYear())) {
						Calendar c = Calendar.getInstance();

						c.setTime(dateTime);
						archive.setYear(c.get(Calendar.YEAR));
					}

				}
				dateType = "";
			} else if (dateType == "号数") {

				archive.setArchiveSerialNumber(line);
				dateType = "";
			}
	

			// System.out.println(line);

		}

		if (EcUtil.isEmpty(archive.getYear())) {
			Calendar c = Calendar.getInstance();

			archive.setYear(c.get(Calendar.YEAR));
		}
		
		archive.setArchiveCode(relaceArchiveText(code));
		archive.setArchiveName(relaceArchiveText(reason));
		archive.setArchiveResult(relaceArchiveText(results));
		archive.setArchiveApplicant(relaceArchiveText(applicant));
		archive.setArchiveOppositeApplicant(relaceArchiveText(applicantBad));
		archive.setArchiveThirdPerson(relaceArchiveText(thirdApplicant));
		archive.setArchiveJudge(relaceArchiveText(judgePerson));

	}
	
	private String relaceArchiveText(String text){
		
		if(text == null){
			return text;
		}
		text =  text.replaceAll("：", "");
		return text.replaceAll("\\*", "");
	}

}
