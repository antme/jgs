package com.zcyservice.service;

import java.util.List;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Manufacturer;
import com.zcyservice.bean.ManufacturerTemp;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.ServiceProviderTemp;
import com.zcyservice.bean.vo.SPInfoWithTempVO;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.impl.MFCInfoWithTempVO;

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
