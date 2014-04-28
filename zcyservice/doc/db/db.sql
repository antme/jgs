
drop database if exists `zcy`;
create database `zcy`;
use zcy;

CREATE TABLE `User` (
  `id` varchar(36) NOT NULL,
  `userName` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `mobileNumber` varchar(12) DEFAULT NULL,
  `roleName` varchar(36) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `addresses` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `groupId` text DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `userStatus` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
  `isNew` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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

CREATE TABLE `Log` (
  `id` varchar(36) NOT NULL,
  `message` text NOT NULL,
  `thread` varchar(255) DEFAULT NULL,
  `operatorId` varchar(36) DEFAULT NULL,
  `urlPath` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `data` text DEFAULT NULL,
  `tableName` varchar(36) DEFAULT NULL,
  `dataId`  varchar(36) DEFAULT NULL,
  `searchValue` text DEFAULT NULL,
  `displayValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `RoleGroup` (
  `id` varchar(36) NOT NULL,
  `groupName` varchar(36) NOT NULL,
  `permissions` text NOT NULL,
  `description` text,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `indexPage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SystemConfig` (
  `id` varchar(36) NOT NULL,
  `configId` varchar(255) NOT NULL,
  `cfgValue` varchar(255) NOT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `RoleGroup` VALUES ('b3ff6db9-8fc7-41fa-b656-38bcf5b91eb4','管理员','adm_user_manage,adm_sys_settings,adm_role_manage,adm_archive_download,adm_archive_print,adm_archive_manage,adm_archive_approve,adm_archive_destory_approve,adm_archive_borrow_manager,adm_archive_query,adm_archive_report','系统管理员','2013-10-18 15:51:17','2014-01-23 00:45:44',NULL,'');

INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`,`groupId`,`userStatus`) VALUES ('05c07bcc-833e-4b22-a8be-3c3a63609ac8','admin','96e79218965eb72c92a549dd5a330112',now(),now(),'b3ff6db9-8fc7-41fa-b656-38bcf5b91eb4','NORMAL');
