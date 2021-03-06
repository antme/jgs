package com.zcyservice.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.bean.User;
import com.zcy.controller.AbstractController;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcy.util.ImgUtil;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.IUserService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/user")
@Permission()
@LoginRequired()
public class ZcyUserController extends AbstractController {

	//_User used in AbstractController.removeSessionInfo
	public static final String IMG_CODE = "imgCode_User";
	public static final String REG_CODE = "regCode_User";
	private static final String FORGET_PWD_IMG_CODE = "pwdImgCode_User";

	@Autowired
	private IUserService userService;

	private static Logger logger = LogManager.getLogger(ZcyUserController.class);

	@RequestMapping("/login.do")
	@LoginRequired(required = false)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
			user = userService.login(user);
			setLoginSessionInfo(request, response, user);
			EcThreadLocal.set(User.ID, user.getId());
			responseWithData(null, request, response);
		} else {
			throw new ResponseException("请输入正确验证码");
		}

	}

	@RequestMapping("/logout.do")
	@LoginRequired(required = false)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		clearLoginSession(request, response);
		responseWithData(null, request, response);
	}

	@RequestMapping("/reg/getCode.do")
	@LoginRequired(required = false)
	public void getMobileValidCode(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);

		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {

			String regCode = ImgUtil.getRandomWord(4);
			setSessionValue(request, REG_CODE, regCode);
			responseWithData(null, request, response);
		} else {
			throw new ResponseException("请输入正确验证码");
		}
	}

	@RequestMapping("/reg.do")
	@LoginRequired(required = false)
	public void registerUser(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		
		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {

			user = userService.regUser(user);

			setLoginSessionInfo(request, response, user);
			responseWithData(null, request, response);
		} else {
			throw new ResponseException("请输入正确验证码");
		}


	}

	@RequestMapping("/img.do")
	@LoginRequired(required = false)
	public void loadLoginImg(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, IMG_CODE, word);
		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);

		try {
			ImageIO.write(image, "png", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/forgot/pwd/img.do")
	@LoginRequired(required = false)
	public void loadForgetPwdImg(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, FORGET_PWD_IMG_CODE, word);
		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);
		try {
			ImageIO.write(image, "png", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	

	@RequestMapping("/pwd/reset.do")
	@LoginRequired(required = false)
	public void resetPwd(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		user.setId(EcThreadLocal.getCurrentUserId());
		userService.resetPwd(user);

		responseWithDataPagnation(null, request, response);
	}

	@RequestMapping("/manage.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listForAdmin(vo), request, response);
	}

	@RequestMapping("/info.do")
	public void loadUserInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);

		if (EcUtil.isEmpty(user.getId())) {
			user.setId(EcThreadLocal.getCurrentUserId());
		}
		responseWithEntity(userService.loadUserInfo(user), request, response);
	}

	@RequestMapping("/update.do")
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);

		userService.updateUser(user);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/admin/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void adminAddUserForCustomer(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);

		userService.adminAddUserAsUserRole(user);
		responseWithEntity(null, request, response);
	}



	@RequestMapping("/access.do")
	public void listUserAccessMenuIds(HttpServletRequest request, HttpServletResponse response) {		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", userService.listUserAccessMenuIds());
		responseWithData(result, request, response);
	}
	

	//FIXME 临时解决方案
	@RequestMapping("/todolist.do")
	public void logincheck(HttpServletRequest request, HttpServletResponse response) {
		responseWithData(userService.getTodoListInfo(), request, response);
	}
	

}
