package com.zcyservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.bean.BaseEntity;
import com.zcy.controller.AbstractController;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.ServiceProviderTemp;
import com.zcyservice.bean.ServiceScore;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.SpStore;
import com.zcyservice.bean.Worker;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.bean.vo.SpCLVo;
import com.zcyservice.service.IAdvertisementService;
import com.zcyservice.service.ISProviderService;
import com.zcyservice.service.ISpCategoryLocationService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/sp")
@Permission()
@LoginRequired()
public class ServiceProviderController extends AbstractController {

	@Autowired
	private ISProviderService isp;

	@Autowired
	private ISpCategoryLocationService spLocation;
	
	@Autowired
	private IAdvertisementService ads;
	
	@RequestMapping("/reg.do")
	@LoginRequired(required = false)
	public void registerUser(HttpServletRequest request, HttpServletResponse response) {


		ServiceProvider sper = (ServiceProvider) parserJsonParameters(request, false, ServiceProvider.class);
		
		uploadLicenseNoFile(request, sper, false);
		uploadStoreImageFile(request, sper, false);
		isp.addServiceProvider(sper);
		clearLoginSession(request, response);
		responseWithData(null, request, response);
	}

	private void uploadLicenseNoFile(HttpServletRequest request, ServiceProvider sper, boolean isAdmin) {
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("spLicenseNo");

		String imgFileName = uploadFile.getOriginalFilename().toLowerCase();
		if (EcUtil.isEmpty(imgFileName)) {
			if (!isAdmin) {
				throw new ResponseException("请上传营业执照号!");
			}
		}else{
			if( uploadFile.getSize() > 512 * 1024){
				throw new ResponseException("营业执照图片大小不能超过512K");
			}
			imgFileName = imgFileName.replaceAll(" ", "");
			if (imgFileName.endsWith("gif") || imgFileName.endsWith("jpg") || imgFileName.endsWith("png") | imgFileName.endsWith("jpeg")) {
				String webPath = request.getSession().getServletContext().getRealPath("/");

				InputStream inputStream = null;
				try {
					inputStream = uploadFile.getInputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("inputStream", inputStream);
				map.put("webPath",  webPath);

				map.put("fileName", uploadFile.getOriginalFilename().trim().replaceAll(" ", ""));

				String path = ads.upload(map);
				sper.setSpLicenseNo(path);

			} else {
				throw new ResponseException("请上传营业执照照片! 只支持（GIF|JPG|PNG|JPEG）格式!");
			}
		}
		
    }
	
	private void uploadStoreImageFile(HttpServletRequest request, ServiceProvider sper, boolean isAdmin) {
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("storeImage");
		String imgFileName = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");
		if (EcUtil.isEmpty(imgFileName)){
			if (!isAdmin) {//不是必填
//				throw new ResponseException("请上传店铺图片!");
			}
		}else{
			
			if( uploadFile.getSize() > 512 * 1024){
				throw new ResponseException("店铺图片大小不能超过512K");
			}
			if (imgFileName.endsWith("gif") || imgFileName.endsWith("jpg") || imgFileName.endsWith("png") | imgFileName.endsWith("jpeg")) {
				String webPath = request.getSession().getServletContext().getRealPath("/");

				InputStream inputStream = null;
				try {
					inputStream = uploadFile.getInputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("inputStream", inputStream);
				map.put("webPath",  webPath);
				map.put("fileName", uploadFile.getOriginalFilename().trim().replaceAll(" ", ""));

				String path = ads.upload(map);
				sper.setStoreImage(path);
			} else {
				throw new ResponseException("请上传店铺图片照片! 只支持（GIF|JPG|PNG|JPEG）格式!");
			}
		}
		
    }
	
	@RequestMapping("/update.do")
	@LoginRequired
	public void updateSpInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceProviderTemp sper = (ServiceProviderTemp) parserJsonParameters(request, false, ServiceProviderTemp.class);
		
		String spLicenseNo = ads.getUploadImage(request, "spLicenseNo", null);
		String storeImage = ads.getUploadImage(request, "storeImage", null);
		if(spLicenseNo != null){
			sper.setSpLicenseNo(spLicenseNo);
		}
		if(storeImage != null){
			sper.setStoreImage(storeImage);
		}
		
		isp.updateSpInfo(sper);
		responseWithData(null, request, response);
	}

	@RequestMapping("/addscore.do")
	public void addSpScore(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sper = (ServiceProvider) parserJsonParameters(request, false, ServiceProvider.class);
		ServiceScore score = (ServiceScore) parserJsonParameters(request, false, ServiceScore.class);
		score.setSpId(sper.getId());
		responseWithEntity(isp.addSpScore(score), request, response);
	}
	
	@RequestMapping("/getscore.do")
	public void getSpScore(HttpServletRequest request, HttpServletResponse response) {
		ServiceProviderTemp sper = (ServiceProviderTemp) parserJsonParameters(request, true, ServiceProviderTemp.class);
		responseWithEntity(isp.getSpScore(sper), request, response);
	}
	
	@RequestMapping("/list.do")
	public void listSps(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);

		responseWithDataPagnation(isp.listServiceProviders(search), request, response);
	}

	
	@RequestMapping("/select.do")
	public void listSpsForAdminsSelect(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(isp.listSpsForAdminsSelect(), request, response);
	}
	
	
	@RequestMapping("/for/order/selectsp.do")
	public void listSpsForOrder(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, true, ServiceOrder.class);
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("rows", isp.listServiceProvidersForOrder(order));
		responseWithData(listMap, request, response);
	}
	
	@RequestMapping("/for/order/selectspbyaddress.do")
	public void listSpsForOrderByAddress(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, true, ServiceOrder.class);
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("rows", isp.listServiceProvidersForOrder(order));
		responseWithData(listMap, request, response);
	}
	
	
	@RequestMapping("/mfcsearch.do")
	public void searchSps(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(isp.searchSps(search), request, response);
	}


	@RequestMapping("/workerlist.do")
	public void listMyWorkers(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(isp.listMyWorkers(), request, response);
	}

	@RequestMapping("/addWorker.do")
	public void addWorker(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		isp.addWorker(worker);
		responseWithDataPagnation(null, request, response);
	}

	@RequestMapping("/adminaddWorker.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void adminAddWorker(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		isp.adminAddWorker(worker);
		responseWithDataPagnation(null, request, response);
	}
	
	@RequestMapping("/get.do")
	@LoginRequired
	public void getSpInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sp = (ServiceProvider) parserJsonParameters(request, true, ServiceProvider.class);
//		responseWithEntity(isp.getSpInfo(sp), request, response);
		responseWithEntity(isp.getSpInfoWithSPTempId(sp), request, response);
	}
	
	@RequestMapping("/detail.do")
	@LoginRequired
	public void loadSpDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sp = (ServiceProvider) parserJsonParameters(request, true, ServiceProvider.class);
		responseWithEntity(isp.loadSpDetailInfo(sp), request, response);
	}
	
	@RequestMapping("/removeTemp.do")
	@LoginRequired
	public void removeMySpTemp(HttpServletRequest request, HttpServletResponse response) {
		ServiceProviderTemp spt = (ServiceProviderTemp) parserJsonParameters(request, true, ServiceProviderTemp.class);
		isp.removeSpTemp(spt);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/admin/approve/get.do")
	public void getApproveSpInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sp = (ServiceProvider) parserJsonParameters(request, true, ServiceProvider.class);
		responseWithEntity(isp.getApproveSpInfo(sp), request, response);
	}
	
	@RequestMapping("/listnew.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void listNewSp(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(isp.listNewServiceProviders(), request, response);
	}
	
	@RequestMapping("/listupdate.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void listUpdatedSp(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(isp.listUpdatedServiceProviders(), request, response);
	}

	@RequestMapping("/worker/get.do")
	public void loadWorkerInfo(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		responseWithEntity(isp.loadWorkerInfo(worker), request, response);
	}
	
	
	@RequestMapping("/worker/inactive.do")
	public void inactiveWorker(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		isp.inactiveWorker(worker);
		responseWithEntity(null, request, response);
	}
	
	@RequestMapping("/worker/active.do")
	public void activeWorker(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		isp.activeWorker(worker);
		responseWithEntity(null, request, response);
	}


	@RequestMapping("/approve.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void approveSp(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sp = (ServiceProvider) parserJsonParameters(request, false, ServiceProvider.class);	
		isp.approveServiceProvider(sp);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/reject.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void rejectSp(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sp = (ServiceProvider) parserJsonParameters(request, false, ServiceProvider.class);

		isp.rejectServiceProvider(sp);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/manage.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(isp.listForAdmin(vo), request, response);
	}
	
	@RequestMapping("/worker/manage.do")
	public void listWorkerForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(isp.listWorkerForAdmin(vo), request, response);
	}
	
	@RequestMapping("/worker/select.do")
	public void listWorkerForOrderSelect(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(isp.listWorkerForOrderSelect(), request, response);
	}

	@RequestMapping("/worker/lock.do")
	public void lockUser(HttpServletRequest request, HttpServletResponse response) {
		BaseEntity be = (BaseEntity) parserJsonParameters(request, false, BaseEntity.class);
		isp.lockWorkerById(be);
		responseWithData(null, request, response);
	}

	@RequestMapping("/worker/del.do")
	public void delWorker(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = (Worker) parserJsonParameters(request, false, Worker.class);
		isp.delWorkerById(worker);
		responseWithData(null, request, response);
	}

	@RequestMapping("/worker/unlock.do")
	public void unlockUser(HttpServletRequest request, HttpServletResponse response) {
		BaseEntity be = (BaseEntity) parserJsonParameters(request, false, BaseEntity.class);
		isp.unlockWorkerById(be);
		responseWithData(null, request, response);
	}

	@RequestMapping("/adminadd.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addSPByAdmin(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider sper = (ServiceProvider) parserJsonParameters(request, false, ServiceProvider.class);
		
		uploadLicenseNoFile(request, sper, true);
		uploadStoreImageFile(request, sper, true);
		isp.addServiceProviderByAdmin(sper);
		responseWithData(null, request, response);
	}
	
	/**SP category location CRUD**/
	@RequestMapping("/location/add.do")
	public void saveCategoryLocation(HttpServletRequest request, HttpServletResponse response) {
		SpCategoryLocation entity = (SpCategoryLocation) parserJsonParameters(request, false, SpCategoryLocation.class);
		spLocation.save(entity);
		responseWithKeyValue(SpCategoryLocation.ID, entity.getId(), request, response);
	}
	
	
	/**SP category location CRUD**/
	@RequestMapping("/search/keyword.do")
	public void listSearchSpKeywords(HttpServletRequest request, HttpServletResponse response) {
	
		responseWithData(spLocation.listSearchSpKeywords(),request, response);
	}
	
	@RequestMapping("/location/delete.do")
	public void deleteCategoryLocation(HttpServletRequest request, HttpServletResponse response) {
		SpCategoryLocation entity = (SpCategoryLocation) parserJsonParameters(request, false, SpCategoryLocation.class);
		spLocation.delete(entity);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	/**添加或删除***/
	@RequestMapping("/location/updatebatch.do")
	public void saveBatchCategoryLocation(HttpServletRequest request, HttpServletResponse response) {
		List<SpCLVo> list=  parserListJsonParameters(request, false, SpCLVo.class);
		spLocation.saveOrDeleteBatchSelf(list);
		responseWithDataPagnation(null, request, response);
	}
	
	@RequestMapping("/location/listids.do")
	public void listMyCategoryLocation(HttpServletRequest request, HttpServletResponse response) {
		SpCategoryLocation entity = (SpCategoryLocation) parserJsonParameters(request, true, SpCategoryLocation.class);
		responseWithData(spLocation.listMy(entity), request, response);
	}
	
	@RequestMapping("/searchsp.do")
	public void searchSp(HttpServletRequest request, HttpServletResponse response) {
		SpCategoryLocation entity = (SpCategoryLocation) parserJsonParameters(request, true, SpCategoryLocation.class);
		responseWithListData(spLocation.searchSp(entity), request, response);
	}
	
	@RequestMapping("/admin/approve/history.do")
	public void listAdminApproveHistoryList(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(isp.listAdminApproveHistory(vo), request, response);
	}
	
	public String genRandomRelativePath(String fileName){
		Random r = new Random();
		int n = r.nextInt(101);
		String ms = Long.toString(new Date().getTime());
		StringBuffer sb = new StringBuffer("/");
		sb.append(n).append("/").append(ms).append("/").append(fileName);
		return sb.toString();
	}
	//服务网点CRUD
	@RequestMapping("/spstore/saveself.do")
	public void save(HttpServletRequest request, HttpServletResponse response) {
		SpStore entity = (SpStore) parserJsonParameters(request, false, SpStore.class);
		isp.saveSelfSpStore(entity);
		responseWithKeyValue(SpStore.ID, entity.getId(), request, response);
	}

	@RequestMapping("/spstore/deleteself.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		SpStore entity = (SpStore) parserJsonParameters(request, false, SpStore.class);
		isp.deleteSelfSpStore(entity);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	@RequestMapping("/spstore/listself.do")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(isp.listSelfSpStore(), request, response);
	}
	
	@RequestMapping("/regionallist.do")
	public void listMyServiceRegional(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(isp.searchServiceRegional(vo), request, response);
	}
	
	@RequestMapping("/regionallist/del.do")
	public void deleteMyRegionalList(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		isp.deleteMyRegionalList(ids);
		responseWithData(null, request, response);
	}
	
	
	
}
