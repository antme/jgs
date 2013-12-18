package com.zcyservice.service;

import java.util.List;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.AdminApproveHistory;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.ServiceProviderTemp;
import com.zcyservice.bean.ServiceScore;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.SpStore;
import com.zcyservice.bean.Worker;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SPInfoWithTempVO;
import com.zcyservice.bean.vo.SearchVo;

public interface ISProviderService {

	ServiceProvider addServiceProvider(ServiceProvider sper);

	EntityResults<ServiceProvider> listServiceProviders(SearchVo search);
	
	List<ServiceProvider> listServiceProvidersForOrder(ServiceOrder order);

	EntityResults<Worker> listMyWorkers();
	
	void addWorker(Worker worker);

	EntityResults<ServiceProvider> listNewServiceProviders();
	
	public DataBaseQueryBuilder getNewServiceProvidersQueryBuilder();
	
	public DataBaseQueryBuilder getUpdateServiceProvidersQueryBuilder();
	
	EntityResults<ServiceProviderTemp> listUpdatedServiceProviders();

	ServiceProvider getSpInfo(ServiceProvider sp);
	
	public ServiceProvider loadSpDetailInfo(ServiceProvider sp);
	
	SPInfoWithTempVO getSpInfoWithSPTempId(ServiceProvider sp);
	
	ServiceProvider getApproveSpInfo(ServiceProvider sp);

	Worker loadWorkerInfo(Worker worker);
	
	void approveServiceProvider(ServiceProvider sp);

	void rejectServiceProvider(ServiceProvider sp);
	
	void removeSpTemp(ServiceProviderTemp spt);

	EntityResults<ServiceProvider> searchSps(SearchVo search);
	
	EntityResults<ServiceProvider> listForAdmin(SearchVo vo);
	
	EntityResults<Worker> listWorkerForAdmin(SearchVo vo);

	void inactiveWorker(Worker worker);

	void activeWorker(Worker worker);

	EntityResults<Worker> listWorkerForOrderSelect();

	void updateSpInfo(ServiceProviderTemp sper);
	
	ServiceProvider addSpScore(ServiceScore sper);
	
	ServiceProvider getSpScore(ServiceProviderTemp sper);
	
	void addServiceProviderByAdmin(ServiceProvider sper);
	
	void adminAddWorker(Worker worker);
	
	void lockWorkerById(BaseEntity be);
	
	void delWorkerById(Worker worker);
	
	void unlockWorkerById(BaseEntity be);
	
	EntityResults<AdminApproveHistory> listAdminApproveHistory(SearchVo vo);

	List<ServiceProvider> listSpsForAdminsSelect();
	
	public SpStore saveSelfSpStore(SpStore entity);
	
	public EntityResults<SpStore> listSelfSpStore();
	
	public void deleteSelfSpStore(SpStore entity);

	EntityResults<SpCategoryLocation> searchServiceRegional(SearchVo vo);
	
	public List<ServiceProvider> findServiceProvider(ServiceOrder sor, List<SpCategoryLocation> spCateList);
	
	boolean checkSpTempIsExist(String tempId);

	void deleteMyRegionalList(IDS ids);
}
