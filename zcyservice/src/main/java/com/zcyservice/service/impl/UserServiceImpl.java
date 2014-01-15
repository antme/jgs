package com.zcyservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.RoleGroup;
import com.zcy.bean.SystemConfig;
import com.zcy.bean.User;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.LoginException;
import com.zcy.exception.ResponseException;
import com.zcy.service.AbstractService;
import com.zcy.util.DataEncrypt;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcy.validators.ValidatorUtil;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.IUserService;
import com.zcyservice.util.PermissionConstants;
import com.zcyservice.util.Role;
import com.zcyservice.util.UserStatus;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService implements IUserService {
	public static final String ADM_ORDER_MANAGE = "adm_order_manage";

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	public void updateUser(User user) {
		user.setId(EcThreadLocal.getCurrentUserId());

		if (EcUtil.isValid(user.getPassword())) {
			user.setPassword(DataEncrypt.generatePassword(user.getPassword()));
		}
		this.dao.updateById(user);

	}

	@Override
	public User regUser(User user) {
		// 手机号码作为默认登录名字
		if (EcUtil.isEmpty(user.getUserName())) {
			user.setUserName(user.getMobileNumber());
		}

		if (EcUtil.isEmpty(user.getPassword())) {
			user.setPassword(DataEncrypt.generatePassword(user.getMobileNumber()));
		} else {
			user.setPassword(DataEncrypt.generatePassword(user.getPassword()));
		}
		ValidatorUtil.validate(user, "user", "userReg", PermissionConstants.validateFiles);
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, user.getUserName());
		if (dao.exists(builder)) {
			throw new ResponseException("此用户名已经被注册");
		}

		builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.MOBILE_NUMBER, user.getMobileNumber());
		if (dao.exists(builder)) {
			throw new ResponseException("此手机号码已经被注册");
		}

		if (user.getRoleName() == null) {
			user.setRoleName(Role.USER.toString());
		}

		if (EcUtil.isEmpty(user.getStatus())) {
			user.setStatus(UserStatus.NORMAL.toString());
		}

		checkUserName(user.getUserName());

		user = (User) dao.insert(user);
		return user;
	}

	public User login(User user) {
		ValidatorUtil.validate(user, "user", "login", PermissionConstants.validateFiles);
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));

		DataBaseQueryBuilder nameQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		nameQuery.or(User.USER_NAME, user.getUserName());
		nameQuery.or(User.MOBILE_NUMBER, user.getUserName());

		builder = builder.and(nameQuery);

		if (!dao.exists(builder)) {
			throw new ResponseException("用户名或密码错误");
		}

		User u = (User) dao.findOneByQuery(nameQuery, User.class);
		if (u.getStatus() != null && UserStatus.LOCKED.toString().equalsIgnoreCase(u.getStatus())) {
			throw new ResponseException("账户已冻结，请联系管理员！");
		}

		if (u.getGroupId() != null) {
			DataBaseQueryBuilder groupQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
			groupQuery.and(RoleGroup.ID, u.getGroupId());
			groupQuery.limitColumns(new String[] { RoleGroup.INDEX_PAGE });
			RoleGroup group = (RoleGroup) this.dao.findOneByQuery(groupQuery, RoleGroup.class);
			if (group != null && group.getIndexPage() != null) {
				u.setIndexPage(group.getIndexPage());
			}
		}
		return u;
	}

	@Override
	public String getRoleByUserId(String id) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, id);

		User user = (User) dao.findOneByQuery(builder, User.class);

		return user.getId();
	}

	@Override
	public String getRoleNameByUserId(String id) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, id);

		User user = (User) dao.findOneByQuery(builder, User.class);

		return user.getRoleName();
	}

	@Override
	public EntityResults<User> listForAdmin(SearchVo vo) {
		String keyword = vo.getKeyword();
		String userStatus = vo.getUserStatus();
		String roleName = vo.getRoleName();

		if (EcUtil.isEmpty(roleName)) {
			roleName = Role.USER.toString();
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ROLE_NAME, roleName.toUpperCase());

		if (!EcUtil.isEmpty(userStatus)) {
			builder.and(User.STATUS, userStatus);
		}

		if (!EcUtil.isEmpty(keyword)) {
			DataBaseQueryBuilder builder2 = new DataBaseQueryBuilder(User.TABLE_NAME);
			builder2.or(DataBaseQueryOpertion.LIKE, User.USER_NAME, keyword);
			builder2.or(DataBaseQueryOpertion.LIKE, "name", keyword);
			builder2.or(DataBaseQueryOpertion.LIKE, User.MOBILE_NUMBER, keyword);
		
			builder.and(builder2);
		}

		return dao.listByQueryWithPagnation(builder, User.class);
	}

	public void resetPwd(User user) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, user.getId());
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));

		if (this.dao.exists(builder)) {
			user.setPassword(DataEncrypt.generatePassword(user.getNewPwd()));
			this.dao.updateById(user);
		} else {
			throw new ResponseException("原始密码错误");
		}

	}

	public User loadUserInfo(User user) {

		return (User) this.dao.findById(user.getId(), User.TABLE_NAME, User.class);
	}

	@Override
	public void lockUserById(BaseEntity be) {
		User user = (User) dao.findById(be.getId(), User.TABLE_NAME, User.class);
		user.setStatus(UserStatus.LOCKED.toString());

		dao.updateById(user);
	}

	@Override
	public void unlockUserById(BaseEntity be) {
		User user = (User) dao.findById(be.getId(), User.TABLE_NAME, User.class);
		user.setStatus(UserStatus.NORMAL.toString());
		dao.updateById(user);

	}

	@Override
	public void adminAddUserAsUserRole(User user) {
		if (EcUtil.isEmpty(user.getId())) {

			if (EcUtil.isEmpty(user.getUserName())) {
				user.setUserName(user.getMobileNumber());
			}

			this.regUser(user);
		} else {


			if (EcUtil.isValid(user.getPassword())) {
				user.setPassword(DataEncrypt.generatePassword(user.getPassword()));
			}
			dao.updateById(user);
		}

	}

	public List<String> listUserAccessMenuIds() {
		User user = (User) this.dao.findById(EcThreadLocal.getCurrentUserId(), User.TABLE_NAME, User.class);

		if (user == null) {
			throw new LoginException();
		}
		String groupId = user.getGroupId();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		List<String> menus = new ArrayList<String>();

		if (!EcUtil.isEmpty(groupId)) {
			String[] groups = groupId.split(",");
			for (String group : groups) {
				builder.or(RoleGroup.ID, group);
			}

			List<RoleGroup> list = this.dao.listByQuery(builder, RoleGroup.class);
			for (RoleGroup rg : list) {
				String premissions = rg.getPermissions();
				if (!EcUtil.isEmpty(premissions)) {

					String[] splitPremissions = premissions.split(",");
					for (String p : splitPremissions) {
						if (!EcUtil.isEmpty(p)) {
							menus.add(p.trim());
						}
					}
				}
			}
		}

		return menus;
	}

	public Map<String, Object> getTodoListInfo() {
		Map<String, Object> result = new HashMap<String, Object>();



		return result;
	}

	public boolean inRole(String groupIds, String roleId) {

		DataBaseQueryBuilder roleQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		roleQuery.and(DataBaseQueryOpertion.LIKE, RoleGroup.PERMISSIONS, roleId);
		List<RoleGroup> groupList = this.dao.listByQuery(roleQuery, RoleGroup.class);

		for (RoleGroup group : groupList) {

			if (groupIds.contains(group.getId())) {
				return true;
			}
		}

		return false;

	}

	public boolean inRole(String roleId) {
		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.limitColumns(new String[] { User.GROUP_ID });
		User user = (User) this.dao.findById(EcThreadLocal.getCurrentUserId(), User.TABLE_NAME, User.class);
		String groupIds = user.getGroupId();
		DataBaseQueryBuilder roleQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		roleQuery.and(DataBaseQueryOpertion.LIKE, RoleGroup.PERMISSIONS, roleId);
		List<RoleGroup> groupList = this.dao.listByQuery(roleQuery, RoleGroup.class);

		for (RoleGroup group : groupList) {

			if (groupIds != null && group != null && groupIds.contains(group.getId())) {
				return true;
			}
		}

		return false;

	}

	public void checkUserName(String userName) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, userName);
		if (dao.exists(builder)) {
			throw new ResponseException("此用户已经注册");
		}
	}

}
