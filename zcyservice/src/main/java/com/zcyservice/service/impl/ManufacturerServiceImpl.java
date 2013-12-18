package com.zcyservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.Pagination;
import com.zcy.bean.User;
import com.zcy.constants.EConstants;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.log.EcJDBCAppender;
import com.zcy.service.AbstractService;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcy.util.HttpClientUtil;
import com.zcy.util.ImgUtil;
import com.zcyservice.bean.AdminApproveHistory;
import com.zcyservice.bean.Manufacturer;
import com.zcyservice.bean.ManufacturerTemp;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.ServiceProviderTemp;
import com.zcyservice.bean.vo.SPInfoWithTempVO;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.ICategoryService;
import com.zcyservice.service.IECommerceUserService;
import com.zcyservice.service.ILocationService;
import com.zcyservice.service.IManufacturerService;
import com.zcyservice.service.ISiteMessageService;
import com.zcyservice.service.ISmsService;
import com.zcyservice.util.ApplyType;
import com.zcyservice.util.ApproveResult;
import com.zcyservice.util.Role;
import com.zcyservice.util.UserStatus;

@Service(value = "manufacturerService")
public class ManufacturerServiceImpl extends AbstractService implements IManufacturerService {
	private static Logger logger = LogManager.getLogger(ManufacturerServiceImpl.class);

	@Autowired
	private IECommerceUserService us;
	
	@Autowired
	private ISmsService sms;

	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private ISiteMessageService siteSms;
	
	@Autowired
	private ICategoryService  cateService;
	
	
	@Override
	public Manufacturer addManufacturer(Manufacturer mfc) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
		builder.and(Manufacturer.MFC_STORE_NAME, mfc.getMfcStoreName());
		if (dao.exists(builder)) {
			throw new ResponseException("此店铺名已经被注册");
		}

		builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, mfc.getMfcStoreName());
		if (dao.exists(builder)) {
			throw new ResponseException("此店铺名已经被注册");
		}

		String mfcContactMobilePhone = mfc.getMfcContactMobilePhone();

		us.checkUserMobile(mfcContactMobilePhone);
		us.checkUserName(mfc.getMfcStoreName());

		setMfcCommonInfo(mfc);
		mfc.setMfcCode(us.generateCode("C", Manufacturer.TABLE_NAME, false));
		return (Manufacturer) dao.insert(mfc);
	}


	public void setMfcCommonInfo(Manufacturer mfc) {
		if(EcUtil.isValid(mfc.getMfcLocationAreaId())){
			String address = locationService.getLocationString(mfc.getMfcLocationAreaId(), "");
			mfc.setMfcLocation(address);

		}

		String[] ids = mfc.getMfcServiceTypeArray();
		String idType = "";

		if (ids != null) {
			for (String id : ids) {

				if (EcUtil.isEmpty(idType)) {
					idType = id;
				} else {
					idType = idType + "," + id;
				}
			}
		}
		mfc.setMfcServiceType(idType);
		if (ids != null) {
			mfc.setMfcServiceTypeStr(cateService.getCateString(ids));
		}
	}


	@Transactional
	public void approveManufacturer(Manufacturer mfcId) {

		ManufacturerTemp mfcTemp = (ManufacturerTemp) this.dao.findById(mfcId.getId(), ManufacturerTemp.TABLE_NAME_TEMP, ManufacturerTemp.class);
		
		
		if (mfcTemp != null) {// update
			Date tempApplyTime = mfcTemp.getCreatedOn();

			Manufacturer mfc = (Manufacturer) EcUtil.toEntity(mfcTemp.toMap(), Manufacturer.class);

			Manufacturer old = (Manufacturer) this.dao.findByKeyValue(Manufacturer.MFC_CODE, mfcTemp.getMfcCode(), Manufacturer.TABLE_NAME, Manufacturer.class);
			mfc.setId(old.getId());
			

			this.dao.updateById(mfc);
			this.dao.deleteById(mfcTemp);
			// 记录审核历史
			addAdminApproveHistoryForMFC(mfcTemp.getId(), mfcTemp.getMfcStoreName(), tempApplyTime, true, ApplyType.UPDATE.toString(), mfcTemp.toString());
			String mfcContactMobilePhone = mfc.getMfcContactMobilePhone();

			//如果有修改手机号码，跟新用户表里面的手机号码
			us.updateUserMobileNumber(old.getUserId(), mfcContactMobilePhone, old.getMfcContactMobilePhone());


		} else {//new

			Manufacturer mfc = (Manufacturer) this.dao.findById(mfcId.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);
			Date applyTime = mfc.getCreatedOn();
			User user = new User();
			user.setUserName(mfc.getMfcStoreName());
			user.setName(mfc.getMfcStoreName());
			user.setMobileNumber(mfc.getMfcContactMobilePhone());
			user.setRoleName(Role.MFC.toString());
			String password = mfc.getPassword();
			 user.setPassword(mfc.getPassword());
			user.setStatus(UserStatus.NORMAL.toString());
			us.regUser(user);

			mfc.setUserId(user.getId());
			dao.updateById(mfc);
			
			//记录审核历史
			mfc.setCreatedOn(applyTime);
			addAdminApproveHistoryForMFC(mfc.getId(), mfc.getMfcStoreName(), applyTime, true, ApplyType.NEW.toString(), mfc.toString());

			//TODO: 暂时已手机号为密码
			sms.sendNewMfcNotice(user.getMobileNumber(), mfc, password);
		}
	}


	
	public void updateMfcInfo(ManufacturerTemp mfc) {

		Manufacturer old = (Manufacturer) this.dao.findById(mfc.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);

		if(old == null){
			throw new ResponseException("提交失败，请刷新页面后重试!");
		}
		if (EcUtil.isEmpty(mfc.getMfcCode())) {
			mfc.setMfcCode(old.getMfcCode());
		}
		if (EcUtil.isEmpty(mfc.getUserId())) {
			mfc.setUserId(old.getUserId());
		}
		
		mfc.setMfcStoreName(old.getMfcStoreName());

		setMfcCommonInfo(mfc);
		
		DataBaseQueryBuilder builder = getUpdateMfcQueryBuilder();
		builder.and(ManufacturerTemp.ID, mfc.getId());
		
		if (dao.exists(builder)) {
			this.dao.updateById(mfc);
		} else {
			dao.insert(mfc);
		}

	}
	
	@Transactional
	public void rejectManufacturer(Manufacturer mfc) {
		// 记录审核历史
		ManufacturerTemp mt = (ManufacturerTemp) dao.findById(mfc.getId(), ManufacturerTemp.TABLE_NAME_TEMP, ManufacturerTemp.class);
		if (mt != null) {// update

			dao.deleteById(mt);
			addAdminApproveHistoryForMFC(mt.getId(), mt.getMfcStoreName(), mt.getCreatedOn(), false, ApplyType.UPDATE.toString(), mt.toString());
			siteSms.addMfcRejectSiteMessage(mt.getUserId(), mfc.getRejectReson());
		} else {// new

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
			query.and(Manufacturer.ID, mfc.getId());
			query.and(DataBaseQueryOpertion.NULL, Manufacturer.USER_ID);

			Manufacturer m = (Manufacturer) dao.findOneByQuery(query, Manufacturer.class);

			if (m == null) {
				throw new ResponseException("非法数据");
			}

			m.setRejectReson(mfc.getRejectReson());
			sms.sendNewMfcRejectNotice(m);
			dao.deleteById(m);
			addAdminApproveHistoryForMFC(m.getId(), m.getMfcStoreName(), m.getCreatedOn(), false, ApplyType.NEW.toString(), m.toString());
		}

	}
	
	private void addAdminApproveHistoryForMFC(String userId, String userName, Date applyTime, boolean approve, String applyType, String data){
		AdminApproveHistory aah = new AdminApproveHistory();
		aah.setUserId(userId);
		aah.setRoleName(Role.MFC.toString());
		aah.setUserName(userName);
		aah.setApplyTime(applyTime);
		aah.setApproveTime(new Date());
		aah.setApplyType(applyType);
		aah.setLogData(data);
		if(approve){
			aah.setApproveResult(ApproveResult.APPROVED.toString());
		}else{
			aah.setApproveResult(ApproveResult.REJECT.toString());
		}
		dao.insert(aah);
	}

	public Manufacturer loadManufacturerInfo(Manufacturer mfc) {
		if (EcUtil.isEmpty(mfc.getId())) {
			return (Manufacturer) this.dao.findByKeyValue(Manufacturer.USER_ID, EcThreadLocal.getCurrentUserId(), Manufacturer.TABLE_NAME, Manufacturer.class);
		}
		return (Manufacturer) this.dao.findById(mfc.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);
	}

	public Manufacturer loadManufacturerDetailInfo(Manufacturer mfc) {
		if (dao.exists(Manufacturer.ID, mfc.getId(), Manufacturer.TABLE_NAME)) {
			return (Manufacturer) this.dao.findById(mfc.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);
		}else{
			throw new ResponseException("该厂商不存在！");
		}
	}

	@Override
    public MFCInfoWithTempVO getMfcInfoWithMFCTempId(Manufacturer mfc) {
		MFCInfoWithTempVO vo = null;
		if (EcUtil.isEmpty(mfc.getId())) {
			vo = (MFCInfoWithTempVO) this.dao.findByKeyValue(Manufacturer.USER_ID, EcThreadLocal.getCurrentUserId(), ManufacturerTemp.TABLE_NAME, MFCInfoWithTempVO.class);
		}else{
			vo = (MFCInfoWithTempVO) this.dao.findById(mfc.getId(), Manufacturer.TABLE_NAME, MFCInfoWithTempVO.class);
		}

		String mfcid = vo.getId();
		
		if(dao.exists(ManufacturerTemp.ID, mfcid, ManufacturerTemp.TABLE_NAME_TEMP)){
			vo.setTempId(mfcid);
		}else{
			vo.setTempId(null);
		}
		return vo;
    }
	
	public Manufacturer loadApproveManufacturerInfo(Manufacturer mfc) {

		Manufacturer manu = (ManufacturerTemp) this.dao.findById(mfc.getId(), ManufacturerTemp.TABLE_NAME_TEMP, ManufacturerTemp.class);
		if (manu != null) {
			return manu;
		}

		return (Manufacturer) this.dao.findById(mfc.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);
	}

	public EntityResults<Manufacturer> listNewManufacturers() {
		DataBaseQueryBuilder builder = getNewMfcQueryBuilder();
		return this.dao.listByQueryWithPagnation(builder, Manufacturer.class);
	}

	public DataBaseQueryBuilder getNewMfcQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NULL, Manufacturer.USER_ID);
	    return builder;
    }
	
	public EntityResults<ManufacturerTemp> listUpdatedManufacturers(){
		DataBaseQueryBuilder builder = getUpdateMfcQueryBuilder();
		return this.dao.listByQueryWithPagnation(builder, ManufacturerTemp.class);
	}

	public DataBaseQueryBuilder getUpdateMfcQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ManufacturerTemp.TABLE_NAME_TEMP);
	    return builder;
    }

	@Override
    public EntityResults<Manufacturer> listForAdmin(SearchVo vo) {
		String keyword = vo.getKeyword();
		String userStatus = vo.getUserStatus();
		
		EntityResults<Manufacturer> result = new EntityResults<Manufacturer>();
		
		Pagination pagination = (Pagination) EcThreadLocal.get(EConstants.PAGENATION);
		int limitStart = 0;
		int limitRows = 10;
		if (pagination != null && pagination.getRows() > 0) {
			int currentPage = pagination.getPage();
			if (currentPage < 1) {
				currentPage = 1;
			}
			limitStart = (currentPage - 1) * pagination.getRows();
			limitRows = pagination.getRows();
		}
		StringBuffer sql = new StringBuffer("select mfc.mfcServiceType, mfc.createdOn, mfc.id,mfc.mfcCode,mfc.mfcStoreName,mfc.mfcCompanyName,mfc.mfcLocation,mfc.mfcCompanyAdress,mfc.mfcContactPerson,mfc.mfcContactPhone,mfc.mfcContactMobilePhone,mfc.mfcQQ,mfc.mfcWangWang,mfc.userId,mfc.mfcLocationProvinceId,mfc.mfcLocationCityId,mfc.mfcLocationAreaId,mfc.mfcCompanyAdressProvinceId,mfc.mfcCompanyAdressAreaId,mfc.mfcCompanyAdressCityId,user.status userStatus from Manufacturer mfc join User user on mfc.userId=user.id where 1=1 ");
		
		if(!EcUtil.isEmpty(keyword)){
			sql.append(" and (mfc.mfcCompanyName like \"%").append(keyword).append("%\"")
			.append(" or mfc.mfcCode like \"%").append(keyword).append("%\"")
			.append(" or mfc.mfcStoreName like \"%").append(keyword).append("%\"")
			.append(" or mfc.mfcContactMobilePhone like \"%").append(keyword).append("%\"")
			.append(" or mfc.mfcContactPerson like \"%").append(keyword).append("%\")");
		}
		
		if (!EcUtil.isEmpty(userStatus)){
			sql.append(" and user.status=\"").append(userStatus).append("\"");
		}
		
		List<Manufacturer> listForCount =  dao.listBySql(sql.toString(), Manufacturer.class);
		Pagination pagnation = new Pagination();
		pagnation.setTotal(listForCount.size());
		result.setPagnation(pagnation);
		
		sql.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
		List<Manufacturer> entityList =  dao.listBySql(sql.toString(), Manufacturer.class);
		result.setEntityList(entityList);
		
		return result;
    }

	@Transactional
	@Override
	public void addManufacturerByAdmin(Manufacturer mfc) {
		if (EcUtil.isEmpty(mfc.getId())) {
			Manufacturer manuf = addManufacturer(mfc);
			approveManufacturer(manuf);

		} else {
			Manufacturer old = (Manufacturer) this.dao.findByKeyValue(Manufacturer.ID, mfc.getId(), Manufacturer.TABLE_NAME, Manufacturer.class);

			setMfcCommonInfo(mfc);
			
			if (!old.getMfcContactMobilePhone().equalsIgnoreCase(mfc.getMfcContactMobilePhone())) {
				us.checkUserMobile(mfc.getMfcContactMobilePhone());
				us.updateUserMobileNumber(old.getUserId(), mfc.getMfcContactMobilePhone(), old.getMfcContactMobilePhone());

			}
			dao.updateById(mfc);
		}

	}


	
	public List<Manufacturer> listForAdminSelect(){
		
		return this.dao.listByQuery(new DataBaseQueryBuilder(Manufacturer.TABLE_NAME), Manufacturer.class);
	}

	@Override
    public void removeMFCTemp(ManufacturerTemp mfct) {
	    dao.deleteById(mfct);
    }

	@Override
    public boolean checkMFCTempIsExist(String tempId) {
		boolean tmp = dao.exists(BaseEntity.ID, tempId, ManufacturerTemp.TABLE_NAME_TEMP);
		if(!tmp){
			Manufacturer mfc = (Manufacturer) dao.findById(tempId, Manufacturer.TABLE_NAME, Manufacturer.class);
			if(mfc.getUserId() == null){
				return true;
			}
		}
	    return tmp;
    }
	
}
