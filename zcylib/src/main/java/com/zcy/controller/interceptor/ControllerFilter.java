package com.zcy.controller.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.util.NestedServletException;

import com.zcy.annotation.InitialService;
import com.zcy.bean.RoleGroup;
import com.zcy.bean.User;
import com.zcy.controller.AbstractController;
import com.zcy.dao.IQueryDao;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.LoginException;
import com.zcy.exception.ResponseException;
import com.zcy.util.DataEncrypt;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;

public class ControllerFilter extends AbstractController implements Filter {
	
	public static final String URL_PATH = "urlPath";
	@Autowired
	private  IQueryDao queryDao;

	@Override
	public void destroy() {

	}

	private static Logger logger = LogManager.getLogger(ControllerFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest srequest = (HttpServletRequest) request;
		HttpServletResponse sresponse = (HttpServletResponse)response;
		EcThreadLocal.set(URL_PATH, srequest.getServletPath());
		cookieCheck(srequest, sresponse);
		if (srequest.getSession().getAttribute(User.ID) != null) {
			EcThreadLocal.set(User.ID, srequest.getSession().getAttribute(User.ID));
		}

		DataBaseQueryBuilder loginExistsBuilder = new DataBaseQueryBuilder(User.TABLE_NAME);
		loginExistsBuilder.and(User.ID, EcThreadLocal.getCurrentUserId());
		
		DataBaseQueryBuilder statusQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		statusQuery.or(User.STATUS, "NORMAL");
		statusQuery.or(DataBaseQueryOpertion.NULL, User.STATUS);
		
		loginExistsBuilder = loginExistsBuilder.and(statusQuery);
		
		if (!EcUtil.isEmpty(EcThreadLocal.getCurrentUserId()) && !queryDao.exists(loginExistsBuilder)) {
			forceLogin((HttpServletRequest) request, (HttpServletResponse) response);
		} else {

			try {
				loginCheck((HttpServletRequest) request);
				roleCheck((HttpServletRequest) request);
				filterChain.doFilter(request, response);
			} catch (Exception e) {

				if (e instanceof NestedServletException) {
					Throwable t = e.getCause();

					if (t instanceof ResponseException) {
						responseServerError(t, (HttpServletRequest) request, (HttpServletResponse) response);
					} else if (e.getCause() instanceof SizeLimitExceededException || e.getCause() instanceof MaxUploadSizeExceededException) {
						responseWithKeyValue("msg", "上传文件不能超过10M", srequest, sresponse);
					} else {
						logger.error("Fatal error when user try to call API ", e);
						responseServerError(e, (HttpServletRequest) request, (HttpServletResponse) response);

					}
				} else if (e instanceof LoginException) {
					forceLogin((HttpServletRequest) request, (HttpServletResponse) response);
				}else {
					logger.error("Fatal error when user try to call API ", e);
					responseServerError(e, (HttpServletRequest) request, (HttpServletResponse) response);
				}

			}
		}

		EcThreadLocal.removeAll();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private void cookieCheck(HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession().getAttribute(User.ID) == null && request.getCookies() != null) {
			String account = null;
			String ssid = null;
			for(Cookie cookie : request.getCookies()){
				if(cookie.getName().equals("account")){
					try {
	                    account = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
	                    e.printStackTrace();
                    }
				} else if(cookie.getName().equals("ssid")){
					ssid = cookie.getValue();
				}
			}
			if(EcUtil.isValid(account) && EcUtil.isValid(ssid)){
				User user = (User) queryDao.findByKeyValue(User.USER_NAME, account, User.TABLE_NAME, User.class);
				if(user != null && DataEncrypt.generatePassword(user.getUserName() + user.getPassword()).equals(ssid)){
					request.setAttribute("remember", "on");
					setLoginSessionInfo(request, response, user);
				}
			}
		}
	}
	
	private void roleCheck(HttpServletRequest request) {

		if (InitialService.rolesValidationMap.get(request.getServletPath()) != null) {
			boolean find = false;

			if (EcThreadLocal.getCurrentUserId() != null) {

				DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
				builder.and(User.ID, EcThreadLocal.getCurrentUserId());
				builder.limitColumns(new String[] { User.GROUP_ID });

				User user = (User) queryDao.findOneByQuery(builder, User.class);

				if (user != null) {
					String groupIds = user.getGroupId();

					if (groupIds != null) {
						String[] ids = groupIds.split(",");

						DataBaseQueryBuilder groupQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
						groupQuery.and(DataBaseQueryOpertion.IN, RoleGroup.ID, ids);
						groupQuery.limitColumns(new String[] { RoleGroup.PERMISSIONS });

						List<RoleGroup> groupList = queryDao.listByQuery(groupQuery, RoleGroup.class);
						for (RoleGroup group : groupList) {
							if (group.getPermissions().contains(InitialService.rolesValidationMap.get(request.getServletPath()))) {
								find = true;
								break;
							}
						}

					}

				}

			}

			if (!find) {
				throw new ResponseException("无权限操作");
			}

		}

	}

	private void loginCheck(HttpServletRequest request) {
	
      if (InitialService.loginPath.contains(request.getServletPath())) {
        if (request.getSession().getAttribute(User.ID) == null) {
            logger.debug("Login requried for path : " + request.getPathInfo());
            throw new LoginException();
        }
    }
	}

	


}
