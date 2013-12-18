package com.jgsservice.service;

import java.util.List;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.EntityResults;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.ManufacturerTemp;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceProviderTemp;
import com.jgsservice.bean.vo.SPInfoWithTempVO;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.impl.MFCInfoWithTempVO;

public interface IManufacturerService {

	public Manufacturer addManufacturer(Manufacturer mfc);
	
	public void approveManufacturer(Manufacturer mfc);
		
	public void rejectManufacturer(Manufacturer mfc);
	
	void removeMFCTemp(ManufacturerTemp mfct);
	
	public Manufacturer loadManufacturerInfo(Manufacturer mfc);
	
	public Manufacturer loadManufacturerDetailInfo(Manufacturer mfc);
	
	public Manufacturer loadApproveManufacturerInfo(Manufacturer mfc);

	public EntityResults<Manufacturer> listNewManufacturers();
	
	public DataBaseQueryBuilder getNewMfcQueryBuilder();
	
	public DataBaseQueryBuilder getUpdateMfcQueryBuilder();
	
	public EntityResults<ManufacturerTemp> listUpdatedManufacturers();
	
	public EntityResults<Manufacturer> listForAdmin(SearchVo vo);
	
	public void addManufacturerByAdmin(Manufacturer mfc);
	

	public void updateMfcInfo(ManufacturerTemp mfc);

	public List<Manufacturer> listForAdminSelect();
	
	public MFCInfoWithTempVO getMfcInfoWithMFCTempId(Manufacturer sp);
	
	public boolean checkMFCTempIsExist(String tempId);
}
