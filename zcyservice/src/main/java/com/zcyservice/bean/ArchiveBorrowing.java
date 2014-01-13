package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveBorrowing.TABLE_NAME)
public class ArchiveBorrowing extends BaseEntity {

	public static final String TABLE_NAME = "ArchiveBorrowing";

	public static final String ARCHIVE_ID = "archiveId";

	@Column(name = ARCHIVE_ID)
	@Expose
	public String archiveId;

	// 调阅人
	@Column(name = "borrowingName")
	@Expose
	public String borrowingName;

	// 调阅单位
	@Column(name = "borrowingOrganization")
	@Expose
	public String borrowingOrganization;

	// 调阅日期
	@Column(name = "borrowingDate")
	@Expose
	public Date borrowingDate;

	// 备注
	@Column(name = "remark")
	@Expose
	public String remark;

}
