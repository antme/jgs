package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.User;
import com.zcyservice.bean.vo.SearchVo;


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
	
	public void checkUserMobile(String mobilePhone);
	
	public void checkUserName(String userName);
	
	public String generateCode(String prefix, String db,boolean isCustomerService);
	
	public boolean inRole(String groupIds, String roleId);
	
	public void updateUserMobileNumber(String userId, String mfcContactMobilePhone, String oldPhone); 
}
