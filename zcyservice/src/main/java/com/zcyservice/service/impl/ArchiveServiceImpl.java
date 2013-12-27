package com.zcyservice.service.impl;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zcy.cfg.CFGManager;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.service.AbstractArchiveService;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.ZcyServiceConstants;

@Service(value = "archiveService")
public class ArchiveServiceImpl extends AbstractArchiveService implements IArchiveService {

	public void scanArchines() {

		String scanPath = CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);

		scanDocumentFolder(scanPath, null);

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
					arc.setArchiveCode(UUID.randomUUID().toString());
					arc.setArchiveName(subFile.getName());
					this.dao.insert(arc);
					scanDocumentFolder(subFile.getAbsolutePath(), arc);
				}
			}

		}
	}
}
