package com.zcy.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonSyntaxException;
import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.OrderBy;
import com.zcy.bean.Pagination;
import com.zcy.bean.User;
import com.zcy.constants.ZcyConstants;
import com.zcy.exception.ResponseException;
import com.zcy.util.DataEncrypt;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;

public abstract class AbstractController {
	public static final String MSG = "msg";
	public static final String CODE = "code";
	private static Logger logger = LogManager.getLogger(AbstractController.class);

	protected <T extends BaseEntity> BaseEntity parserJsonParameters(HttpServletRequest request, boolean emptyParameter, Class<T> claszz) {
		HashMap<String, Object> parametersMap = parserJsonParameters(request, emptyParameter);
		EcUtil.updateJsonFieldWithType(parametersMap, claszz);
		logger.debug(String.format("--------------Client post parameters for path [%s] is [%s]", request.getServletPath(), parametersMap));

		return EcUtil.toEntity(parametersMap, claszz);

	}

	protected <T extends BaseEntity> List<T> parserListJsonParameters(HttpServletRequest request, boolean emptyParameter, Class<T> claszz) {
		Map<String, Object> params = this.parserJsonParameters(request, false);
		List<T> list = EcUtil.toJsonList(params, claszz);

		return list;
	}

	protected HashMap<String, Object> parserJsonParameters(HttpServletRequest request, boolean emptyParameter) {
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();

		String parameters = request.getParameter(ZcyConstants.JSON_PARAMETERS_LABEL);

		int filterLength = 0;
		if (parameters != null) {
			try {
				parametersMap = (HashMap<String, Object>) EcUtil.toMap(parameters);
			} catch (JsonSyntaxException e) {
				// TODO
			}

		}
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String pName = parameterNames.nextElement().toString();

			if (pName.toLowerCase().startsWith("filter[filters]".toLowerCase())) {
				filterLength++;
			} else {

				if (pName.indexOf("[]") != -1) {
					// 数组参数
					String pNameKey = pName.replace("[]", "");
					parametersMap.put(pNameKey, request.getParameterValues(pName));
				} else {
					String parameter = request.getParameter(pName).trim();
					parameter = parameter.replaceAll("<script>", "");
					parameter = parameter.replaceAll("</script>", "");
					parametersMap.put(pName, parameter);
				}
			}
		}

		if (filterLength > 0) {
			// 每三个为一组
			int filters = (int) filterLength / 3;
			for (int i = 0; i < filters; i++) {
				String key = request.getParameter("filter[filters][" + i + "][field]");
				String operator = request.getParameter("filter[filters][" + i + "][operator]");
				String value = request.getParameter("filter[filters][" + i + "][value]");
				// parametersMap.put(key, new
				// DataBaseQuery(DataBaseQueryOpertion.getOperation(operator),
				// value));
			}
		}
		if (EcUtil.isEmpty(parametersMap) && !emptyParameter) {
			logger.error(String.format("Parameters required for path [%s]", request.getPathInfo()));
			throw new ResponseException("参数不能为空");
		}

		parametersMap.remove("_");
		parametersMap.remove("callback");

		parametersMap.remove("filter[logic]");
		parametersMap.remove("filter");

		if (parametersMap.get(ZcyConstants.CURRENT_PAGE) != null && parametersMap.get(ZcyConstants.PAGE_SIZE) != null) {
			Pagination pagination = new Pagination();
			pagination.setPage(EcUtil.getInteger(parametersMap.get(ZcyConstants.CURRENT_PAGE), 0, true));
			// default is 10;
			pagination.setRows(EcUtil.getInteger(parametersMap.get(ZcyConstants.PAGE_SIZE), 10, true));
			EcThreadLocal.set(ZcyConstants.PAGENATION, pagination);
		}

		// FIXME: only support sort by one column
		if (parametersMap.get("sort") != null) {

			OrderBy order = new OrderBy();
			order.setOrder(parametersMap.get("order").toString());
			order.setSort(parametersMap.get("sort").toString());

			EcThreadLocal.set(ZcyConstants.DB_QUERY_ORDER_BY, order);

		}

		parametersMap.remove("createdOn");
		parametersMap.remove("updatedOn");
		parametersMap.remove(ZcyConstants.JSON_PARAMETERS_LABEL);
		return parametersMap;
	}

	protected void responseWithEntity(BaseEntity data, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		responseMsg(map, ResponseStatus.SUCCESS, request, response, null);
	}

	protected void responseWithData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
		responseMsg(data, ResponseStatus.SUCCESS, request, response, null);
	}

	protected <T extends BaseEntity> void responseWithDataPagnation(EntityResults<T> listBean, HttpServletRequest request, HttpServletResponse response) {
		if (listBean != null) {
			Map<String, Object> list = new HashMap<String, Object>();
			list.put("total", listBean.getPagnation().getTotal());
			list.put("rows", listBean.getEntityList());
			responseMsg(list, ResponseStatus.SUCCESS, request, response, null);
		} else {
			responseMsg(null, ResponseStatus.SUCCESS, request, response, null);
		}
	}

	protected <T extends BaseEntity> void responseWithDataPagnation(EntityResults<T> listBean, Map<String, Object> results, HttpServletRequest request, HttpServletResponse response) {
		if (results == null) {
			results = new HashMap<String, Object>();
		}
		if (listBean != null) {
			results.put("total", listBean.getPagnation().getTotal());
			results.put("rows", listBean.getEntityList());
			responseMsg(results, ResponseStatus.SUCCESS, request, response, null);
		} else {
			responseMsg(null, ResponseStatus.SUCCESS, request, response, null);
		}
	}

	protected <T extends BaseEntity> void responseWithListData(List<T> listBean, HttpServletRequest request, HttpServletResponse response) {
		if (listBean != null) {
			Map<String, Object> list = new HashMap<String, Object>();
			list.put("rows", listBean);
			responseMsg(list, ResponseStatus.SUCCESS, request, response, null);
		} else {
			responseMsg(null, ResponseStatus.SUCCESS, request, response, null);
		}
	}

	protected void responseWithKeyValue(String key, String value, HttpServletRequest request, HttpServletResponse response) {
		if (key == null) {
			responseWithData(null, request, response);
		} else {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(key, value);
			responseWithData(temp, request, response);
		}
	}

	protected void forceLogin(HttpServletRequest request, HttpServletResponse response) {
		clearLoginSession(request, response);

		try {
			response.sendRedirect("/login.jsp");
		} catch (IOException e) {
			logger.fatal("Write response data to client failed!", e);
		}
	}

	/**
	 * This function will return JSON data to Client
	 * 
	 * 
	 * @param data
	 *            data to return to client
	 * @param dataKey
	 *            if set dataKey, the JSON format use dataKey as the JSON key,
	 *            data as it's value, and both the dataKey and "code" key are
	 *            child of the JSON root node. If not set dataKey, the data and
	 *            the "code" node are both the child of the JSON root node
	 * @param userStatus
	 *            0:FAIL, 1: SUCCESS
	 * @return
	 */
	private void responseMsg(Map<String, Object> data, ResponseStatus status, HttpServletRequest request, HttpServletResponse response, String msgKey) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(CODE, status.toString());
		response.setContentType("text/plain;charset=UTF-8");
		response.addHeader("Accept-Encoding", "gzip, deflate");
		String jsonReturn = EcUtil.toJson(data);
		String callback = request.getParameter("callback");

		if (callback != null) {
			if (data != null && data instanceof Map) {
				jsonReturn = callback + "(" + jsonReturn + ");";
			} else {
				// 不返回任何数据
				jsonReturn = callback + "();";
			}
		}
		try {
			response.getWriter().write(jsonReturn);
		} catch (IOException e) {
			logger.fatal("Write response data to client failed!", e);
		}

	}

	protected void responseServerError(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(CODE, ResponseStatus.ERROR.toString());
		if (throwable instanceof ResponseException) {
			ResponseException apiException = (ResponseException) throwable;
			temp.put(MSG, apiException.getMessage());
			logger.error(apiException.getMessage());
		} else {
			temp.put(CODE, ResponseStatus.FAIL.toString());
			temp.put(MSG, "服务器错误，请稍后再试");
		}
		responseMsg(temp, ResponseStatus.FAIL, request, response, null);

	}

	protected void setSessionValue(HttpServletRequest request, String key, Object value) {

		request.getSession().setAttribute(key, value);
	}

	protected String getSessionValue(HttpServletRequest request, String key) {

		if (request.getSession().getAttribute(key) != null) {
			return request.getSession().getAttribute(key).toString();
		}

		return null;
	}

	protected void removeSessionInfo(HttpServletRequest request) {

		Enumeration<String> e = request.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String nextElement = e.nextElement();
			if (!nextElement.endsWith("_User")) {
				request.getSession().removeAttribute(nextElement);

			}
		}
	}

	protected void setLoginSessionInfo(HttpServletRequest request, HttpServletResponse response, User user) {
		removeSessionInfo(request);

		setSessionValue(request, User.USER_NAME, user.getUserName());
		setSessionValue(request, User.ID, user.getId());
		setSessionValue(request, User.ROLE_NAME, user.getRoleName());
		
		String path = EcUtil.isEmpty(request.getContextPath()) ? "/" : request.getContextPath();
		if (request.getParameter("remember") != null || request.getAttribute("remember") != null) {
			Cookie account = new Cookie("account", null);
			try {
				account.setValue(URLEncoder.encode(user.getUserName(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			account.setMaxAge(3600 * 24 * 30);
			account.setPath(path);

			Cookie ssid = new Cookie("ssid", DataEncrypt.generatePassword(user.getUserName() + user.getPassword()));
			ssid.setMaxAge(3600 * 24 * 30);
			ssid.setPath(path);

			response.addCookie(account);
			response.addCookie(ssid);
		}
	}

	protected void clearLoginSession(HttpServletRequest request, HttpServletResponse response) {
		removeSessionInfo(request);

		String path = EcUtil.isEmpty(request.getContextPath()) ? "/" : request.getContextPath();
		Cookie account = new Cookie("account", null);
		account.setMaxAge(0);
		account.setPath(path);

		Cookie ssid = new Cookie("ssid", null);
		ssid.setMaxAge(0);
		ssid.setPath(path);

		response.addCookie(account);
		response.addCookie(ssid);
	}

	public String uploadArchiveFile(HttpServletRequest request, String path) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("Filedata");
		String fileName = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");
		String attachFile = path + File.separator + fileName;

		InputStream inputStream = null;
		try {
			inputStream = uploadFile.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EcUtil.createFile(attachFile, inputStream);

		return fileName;
	}

	public String genRandomRelativePath(String fileName) {
		Random r = new Random();
		int n = r.nextInt(101);
		String ms = Long.toString(new Date().getTime());
		StringBuffer sb = new StringBuffer("/");
		sb.append("upload/").append(n).append("/").append(ms).append("/").append(fileName);
		return sb.toString();
	}

	public enum ResponseStatus {

		SUCCESS {
			public String toString() {
				return "200";

			}
		},

		FAIL {
			public String toString() {
				return "0";

			}
		},

		ERROR {
			public String toString() {
				return "100";

			}
		}
	}

}
