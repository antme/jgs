package com.zcyservice.bean.vo;

import com.zcy.bean.BaseEntity;

public class ArchiveReport extends BaseEntity {

	public String reportKey;

	public int count;

	public Integer year;

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
