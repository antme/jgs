package com.jgsservice.service;

import java.util.List;
import java.util.Map;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.EntityResults;
import com.jgs.bean.User;
import com.jgsservice.bean.SiteMessage;
import com.jgsservice.bean.SiteMessageUser;
import com.jgsservice.bean.vo.AddSiteMessageVO;
import com.jgsservice.bean.vo.IDS;
import com.jgsservice.bean.vo.SiteMessageDetailVO;
import com.jgsservice.bean.vo.SiteMessageSearchUsersVO;
import com.jgsservice.bean.vo.SiteMessageVO;

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
