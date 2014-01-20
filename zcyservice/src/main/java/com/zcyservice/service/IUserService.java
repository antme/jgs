package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.User;
import com.zcyservice.bean.vo.SearchVo;


public interface IUserService{


    public void updateUser(User user);

    public User regUser(User user);
    
    public User login(User user);
    
    public String getRoleByUserId(String id);
    
    public String getRoleNameByUserId(String id);
       
    public EntityResults<User> listForAdmin(SearchVo vo);



	public void resetPwd(User user);

	public User loadUserInfo(User user);
	

	
	public void adminAddUserAsUserRole(User user);
	
	public List<String> listUserAccessMenuIds();

    
	public Map<String, Object> getTodoListInfo();
	
	
	public void checkUserName(String userName);
	
	
	public boolean inRole(String groupIds, String roleId);
	
}
