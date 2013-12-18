package com.jgsservice.service;

import java.util.List;
import java.util.Map;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.EntityResults;
import com.jgs.bean.User;
import com.jgsservice.bean.ProductOrder;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.vo.SearchVo;


public interface IECommerceUserService{


    public void updateUser(User user);

    public User regUser(User user);
    
    public User login(User user);
    
    public String getRoleByUserId(String id);
    
    public String getRoleNameByUserId(String id);
       
    public EntityResults<User> listForAdmin(SearchVo vo);

	public void getForgotPwdSmsCode(User user, String code);

	public void resetPwdByMobile(User user);

	public void resetPwd(User user);

	public User loadUserInfo(User user);
	
	public void lockUserById(BaseEntity be);
	
	public void unlockUserById(BaseEntity be);
	
	public void adminAddUserAsUserRole(User user);
	
	public List<String> listUserAccessMenuIds();

    
	public Map<String, Object> getTodoListInfo();
	
	public User getManualProductOrderOperator(ProductOrder order);
	
	public User getManualServiceOrderOperator(ServiceOrder order);
	
	public User getServiceOrderNoticeOperator(ServiceOrder order);
	
	public void checkUserMobile(String mobilePhone);
	
	public void checkUserName(String userName);
	
	public String generateCode(String prefix, String db,boolean isCustomerService);
	
	public boolean inRole(String groupIds, String roleId);
	
	public void updateUserMobileNumber(String userId, String mfcContactMobilePhone, String oldPhone); 
}
