package com.zcyservice.service.impl;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveFile;
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
		scanDocumentFolder(scanPath, null);

	}
	
	public EntityResults<Archive> listArchives(SearchVo vo){
		
		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(Archive.TABLE_NAME), Archive.class);
	}

	private void scanDocumentFolder(String path, Archive archive) {

		File file = new File(path);

		if (file.exists()) {

			if (file.isFile()) {

				if (archive != null) {
					ArchiveFile arfile = new ArchiveFile();
					arfile.setArchiveFileName(file.getName());
					arfile.setArchiveFileLastModifyDate(new Date(file.lastModified()));
					arfile.setArchiveId(archive.getId());
					this.dao.insert(arfile);
				}

			} else if (file.isDirectory()) {

				File subFiles[] = file.listFiles();
				for (File subFile : subFiles) {

					Archive arc = new Archive();
					arc.setArchiveCode(generateCode("BH", Archive.TABLE_NAME));
					arc.setArchiveStatus("已归档");
					
					arc.setArchiveName(subFile.getName());
					this.dao.insert(arc);
					scanDocumentFolder(subFile.getAbsolutePath(), arc);
				}
			}

		}
	}
}
