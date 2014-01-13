use ecommerce;
drop table if exists `Archive`;
CREATE TABLE `Archive` (
  `id` varchar(36) NOT NULL,
  `archiveCode` varchar(255) DEFAULT NULL,
  `archiveName` varchar(255) DEFAULT NULL,
  `archiveStatus` varchar(255) DEFAULT NULL,
  `archiveDescription` text DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
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
  `archiveFileLastModifyDate` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

