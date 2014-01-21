use ecommerce;
drop table if exists `Archive`;
CREATE TABLE `Archive` (
  `id` varchar(36) NOT NULL,
  `archiveCode` varchar(255) DEFAULT NULL,
  `folderCode` varchar(255) DEFAULT NULL,
  `archiveName` varchar(255) DEFAULT NULL,
  `archiveStatus` varchar(255) DEFAULT NULL,
  `archiveProcessStatus` varchar(255) DEFAULT NULL,
  `archiveResult` varchar(255) DEFAULT NULL,
  `archiveApplicant` varchar(255) DEFAULT NULL,
  `archiveType` varchar(255) DEFAULT NULL,
  `archiveThirdPerson` varchar(255) DEFAULT NULL,
  `archiveOppositeApplicant` varchar(255) DEFAULT NULL,
  `archiveJudge` varchar(255) DEFAULT NULL,
  `archiveOpenDate` datetime DEFAULT NULL,
  `archiveCloseDate` datetime DEFAULT NULL,
  `archiveDate` datetime DEFAULT NULL,
  `archiveSerialNumber` varchar(255) DEFAULT NULL,
  `archiveDescription` text DEFAULT NULL,
  `destroyComments` text DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `year` int DEFAULT 0,  
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `ArchiveFile`;
CREATE TABLE `ArchiveFile` (
  `id` varchar(36) NOT NULL,
  `archiveId` varchar(36) DEFAULT NULL,
  `archiveFileProperty` varchar(36) DEFAULT NULL,
  `archiveFileType` varchar(36) DEFAULT NULL,
  `archiveFileName` varchar(255) DEFAULT NULL,
  `archiveFilePath` varchar(512) DEFAULT NULL,
  `archiveTextData` text DEFAULT NULL,
  `archiveFileLastModifyDate` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




drop table if exists `ArchiveBorrowing`;
CREATE TABLE `ArchiveBorrowing` (
  `id` varchar(36) NOT NULL,
  `archiveId` varchar(36) DEFAULT NULL,
  `borrowingName` varchar(255) DEFAULT NULL,
  `borrowingOrganization` varchar(255) DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `borrowingDate` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table User add column `userStatus` varchar(36) default null;


