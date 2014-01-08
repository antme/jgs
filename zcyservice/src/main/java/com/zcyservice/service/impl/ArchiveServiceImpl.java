package com.zcyservice.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.bean.ArchiveFile.ArchiveType;
import com.zcyservice.bean.vo.ArvhiveTree;
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
					arc.setArchiveStatus("已归档");

					arc.setArchiveName(subFile.getName());
					this.dao.insert(arc);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中", arc, ArchiveType.FIRST);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中", arc, ArchiveType.SECOND);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "正卷中" + File.separator + "附件", arc, ArchiveType.FIRST_ATTACH);

					scanMainDocumentFolder(subFile.getAbsolutePath() + File.separator + "副卷中" + File.separator + "附件", arc, ArchiveType.SECOND_ATTACH);

				}
			}

		} else {
			logger.error("请创建好扫描目录: " + scanPath);
		}
	}

	public EntityResults<Archive> listArchives(SearchVo vo) {

		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(Archive.TABLE_NAME), Archive.class);
	}

	public List<ArvhiveTree> listArchiveFiles(SearchVo vo) {

		List<ArvhiveTree> trees = new ArrayList<ArvhiveTree>();
		ArvhiveTree menuTree = new ArvhiveTree();
		menuTree.setText("目录");

		List<ArvhiveTree> menuTreeChildren = new ArrayList<ArvhiveTree>();
		ArvhiveTree menuTreeChild1 = new ArvhiveTree();
		menuTreeChild1.setText("目录1");

		ArvhiveTree menuTreeChild2 = new ArvhiveTree();
		menuTreeChild2.setText("目录2");

		menuTreeChildren.add(menuTreeChild1);
		menuTreeChildren.add(menuTreeChild2);

		menuTree.setChildren(menuTreeChildren);

		trees.add(menuTree);
		return trees;

	}

	private void scanMainDocumentFolder(String path, Archive archive, ArchiveType scanType) {

		File file = new File(path);

		if (file.exists()) {

			if (file.isDirectory()) {

				File subFiles[] = file.listFiles();
				for (File subFile : subFiles) {

					if (subFile.isFile()) {
						ArchiveFile arfile = new ArchiveFile();
						arfile.setArchiveFileName(subFile.getName());
						arfile.setArchiveFileLastModifyDate(new Date(subFile.lastModified()));
						arfile.setArchiveId(archive.getId());
						arfile.setArchiveType(scanType);

						this.dao.insert(arfile);
					}

				}
			}

		}
	}
}
