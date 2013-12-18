package com.zcyservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.Pagination;
import com.zcy.bean.User;
import com.zcy.constants.EConstants;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.service.AbstractService;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Manufacturer;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.SiteMessage;
import com.zcyservice.bean.SiteMessageUser;
import com.zcyservice.bean.vo.AddSiteMessageVO;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SiteMessageDetailVO;
import com.zcyservice.bean.vo.SiteMessageSearchUsersVO;
import com.zcyservice.bean.vo.SiteMessageVO;
import com.zcyservice.service.IECommerceUserService;
import com.zcyservice.service.ISiteMessageService;
import com.zcyservice.util.Role;

@Service(value = "siteMessageService")
public class SiteMessageServiceImpl extends AbstractService implements ISiteMessageService {

	private static Logger logger = LogManager.getLogger(SiteMessageServiceImpl.class);
	
	@Autowired
	private IECommerceUserService userService;

	@Override
	public void addSiteMessage(AddSiteMessageVO vo) {

		SiteMessage sm = new SiteMessage();
		sm.setTitle(vo.getTitle());
		sm.setContent(vo.getContent());

		sm = (SiteMessage) dao.insert(sm);

		String siteMessageId = sm.getId();

		List<String> userIds = vo.getUserIds();

		if (!EcUtil.isEmpty(userIds)) {
			for (String uid : userIds) {
				SiteMessageUser smu = new SiteMessageUser();
				smu.setSiteMessageId(siteMessageId);
				smu.setUserId(uid);
				smu.setStatus(true);
				smu.setTitle(sm.getTitle());
				smu.setContent(sm.getContent());
				dao.insert(smu);
			}
		}
	}

	@Override
    public Map<String, Object> getSiteMessage(SiteMessage message) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SiteMessage.TABLE_NAME);
		builder.and(SiteMessage.ID, message.getId());
		
		SiteMessage sm = (SiteMessage) dao.findOneByQuery(builder, SiteMessage.class);
		
		Map<String, Object> re = new HashMap<String, Object>();
		re.put(SiteMessage.TITLE, sm.getTitle());
		re.put(SiteMessage.CONTENT, sm.getContent());
	    return re;
    }

	@Override
	public EntityResults<SiteMessageVO> listSiteMessage() {

		String cuid = EcThreadLocal.getCurrentUserId();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SiteMessageUser.TABLE_NAME);
		query.and(SiteMessageUser.USER_ID, cuid);

		return this.dao.listByQueryWithPagnation(query, SiteMessageVO.class);

	}
	public EntityResults<SiteMessage> listSiteMessagesForAdmin(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SiteMessage.TABLE_NAME);
		return dao.listByQueryWithPagnation(builder, SiteMessage.class);

	}
	
	private List<String> getReiceiverIds(){
		// TODO Add user filter conditions 
		List<String> userIds = new ArrayList<String>();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, User.ROLE_NAME, new String[] {Role.SP.toString(), Role.MFC.toString()});
		
		List<User> userList = dao.listByQuery(builder, User.class);
		for(User user : userList){
			userIds.add(user.getId());
		}
		return userIds;
	}

	@Override
    public List<User> searchUsers(SiteMessageSearchUsersVO vo) {
		List<User> result = new ArrayList<User>();
		String roleName = vo.getGroup();
		List<String> userIds = new ArrayList<String>();
		if (EcUtil.isEmpty(roleName)){
			userIds.addAll(getUserIdsBySearch(vo, Manufacturer.TABLE_NAME));
			userIds.addAll(getUserIdsBySearch(vo, ServiceProvider.TABLE_NAME));
		}else if (Role.MFC.toString().equals(roleName)){//mfc
			userIds.addAll(getUserIdsBySearch(vo, Manufacturer.TABLE_NAME));
		}else if (Role.SP.toString().equals(roleName)){//sp
			userIds.addAll(getUserIdsBySearch(vo, ServiceProvider.TABLE_NAME));
		}
		
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
	    builder.and(DataBaseQueryOpertion.IN, User.ID, userIds);
	    if (userIds.size()>0){//Bug if userIds is empty the listByQuery will error
	    	result =  dao.listByQuery(builder, User.class);
	    }
	    
	    return result;
	    
    }

	private List<String> getUserIdsBySearch(SiteMessageSearchUsersVO vo, String table){
		String province = vo.getProvince();
		String city = vo.getCity();
		String dis = vo.getDistrict();
		String status = vo.getStatus();
		String keyword = vo.getKeyword();
		
		StringBuffer sql = null;
		if (Manufacturer.TABLE_NAME.equals(table)){
			sql = new StringBuffer("select user.id from Manufacturer mfc join User user on mfc.userId=user.id where 1=1");
			if(!EcUtil.isEmpty(province)){
				sql.append(" and mfc.mfcLocationProvinceId=\"").append(province).append("\"");
			}
			if(!EcUtil.isEmpty(city)){
				sql.append(" and mfc.mfcLocationCityId=\"").append(city).append("\"");
			}
			if(!EcUtil.isEmpty(dis)){
				sql.append(" and mfc.mfcLocationAreaId=\"").append(dis).append("\"");
			}
			if(!EcUtil.isEmpty(status)){
				sql.append(" and user.status=\"").append(status).append("\"");
			}
			if(!EcUtil.isEmpty(keyword)){
				sql.append(" and user.userName like \"%").append(keyword).append("%\"");
			}
		}else if (ServiceProvider.TABLE_NAME.equals(table)){

			sql = new StringBuffer("select user.id from ServiceProvider sp join User user on sp.userId=user.id where 1=1");
			if(!EcUtil.isEmpty(province)){
				sql.append(" and sp.spLocationProvinceId=\"").append(province).append("\"");
			}
			if(!EcUtil.isEmpty(city)){
				sql.append(" and sp.spLocationCityId=\"").append(city).append("\"");
			}
			if(!EcUtil.isEmpty(dis)){
				sql.append(" and sp.spLocationAreaId=\"").append(dis).append("\"");
			}
			if(!EcUtil.isEmpty(status)){
				sql.append(" and user.status=\"").append(status).append("\"");
			}
			if(!EcUtil.isEmpty(keyword)){
				sql.append(" and user.userName like \"%").append(keyword).append("%\"");
			}
		}
		
		sql.append(";");
		
		List<BaseEntity> entities = dao.listBySql(sql.toString(), BaseEntity.class);
		List<String> result = new ArrayList<String>();
		for(BaseEntity be : entities){
			result.add(be.getId());
		}
		
		return result;
	}
	@Override
    public Map<String, Object> getCurrentUserMessageCount() {
		String cuid = EcThreadLocal.getCurrentUserId();
		Map<String, Object> re = new HashMap<String, Object>();
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SiteMessageUser.TABLE_NAME);
		builder.and(SiteMessageUser.USER_ID, cuid);
		builder.and(SiteMessageUser.STATUS, 1);
		re.put("count", dao.count(builder));
	    return re;
    }

	@Override
    public void deleteSMByUser(IDS ids) {
		List<String> idlist = ids.getIds();
		for(String id:idlist){
			SiteMessageUser smu = new SiteMessageUser();
			smu.setId(id);
			dao.deleteById(smu);
		}
    }

	@Override
	public SiteMessageDetailVO loadSiteMessageDetail(SiteMessageUser smu) {

		smu.setStatus(false);
		dao.updateById(smu);
		return (SiteMessageDetailVO) dao.findById(smu.getId(), SiteMessageUser.TABLE_NAME, SiteMessageDetailVO.class);

	}
	
	public SiteMessageDetailVO loadSiteMessageDetailForAdmin(SiteMessageUser smu){
		String smid = smu.getId();

		StringBuffer sql = new StringBuffer("select u.userName from SiteMessageUser smu join User u on smu.userId=u.id where smu.siteMessageId=\"")
		.append(smid).append("\"");
		List<User> userList = dao.listBySql(sql.toString(), User.class);
		StringBuffer userNames = new StringBuffer("");
		for(int i=0;i<userList.size();i++){
			if(i==0){
				userNames.append(userList.get(i).getUserName());
			}else{
				userNames.append(",").append(userList.get(i).getUserName());
			}
		}
		SiteMessageDetailVO vo = (SiteMessageDetailVO)dao.findById(smid, SiteMessage.TABLE_NAME, SiteMessageDetailVO.class);
		vo.setReceivers(userNames.toString());
		return vo;
	}
	
	
	public void addMfcRejectSiteMessage(String userId, String rejectReson){
		SiteMessageUser smu = new SiteMessageUser();
		smu.setSiteMessageId(null);
		smu.setUserId(userId);
		smu.setStatus(true);
		smu.setTitle("厂商信息审核拒绝");
		smu.setContent(String.format("你申请修改的厂商信息已被审核拒绝，请重新提交审核信息，拒绝原因:【%s】", rejectReson));
		dao.insert(smu);
	}
	
	public void addSpRejectSiteMessage(String userId, String rejectReson){
		SiteMessageUser smu = new SiteMessageUser();
		smu.setSiteMessageId(null);
		smu.setUserId(userId);
		smu.setStatus(true);
		smu.setTitle("服务商信息审核拒绝");
		smu.setContent(String.format("你申请修改的服务商信息已被审核拒绝，请重新提交审核信息，拒绝原因:【%s】", rejectReson));
		dao.insert(smu);
	}

}
