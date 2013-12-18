package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.User;
import com.zcyservice.bean.SiteMessage;
import com.zcyservice.bean.SiteMessageUser;
import com.zcyservice.bean.vo.AddSiteMessageVO;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SiteMessageDetailVO;
import com.zcyservice.bean.vo.SiteMessageSearchUsersVO;
import com.zcyservice.bean.vo.SiteMessageVO;

public interface ISiteMessageService {
	
	public void addSiteMessage(AddSiteMessageVO vo);
	
	public Map<String, Object> getSiteMessage(SiteMessage message);
	
	public EntityResults<SiteMessageVO> listSiteMessage();
	
	public List<User> searchUsers(SiteMessageSearchUsersVO vo);
	
	public Map<String, Object> getCurrentUserMessageCount();
	
	public void deleteSMByUser(IDS ids);
	
	public SiteMessageDetailVO loadSiteMessageDetail(SiteMessageUser smu);
	
	public SiteMessageDetailVO loadSiteMessageDetailForAdmin(SiteMessageUser smu);

	
	public void addMfcRejectSiteMessage(String userId, String rejectReson);
	
	public void addSpRejectSiteMessage(String userId, String rejectReson);

	public EntityResults<SiteMessage> listSiteMessagesForAdmin();
	
}
