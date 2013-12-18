package com.jgsservice.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jgs.annotation.LoginRequired;
import com.jgs.annotation.Permission;
import com.jgs.controller.AbstractController;
import com.jgs.util.ExcleUtil;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.vo.CategoryVO;
import com.jgsservice.service.IAdvertisementService;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/category")
public class CategoryController extends AbstractController {

	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IAdvertisementService ads;
	
	private static Logger logger = LogManager.getLogger(CategoryController.class);

	@RequestMapping("/save.do")
	@Permission(groupName = PermissionConstants.ADM_CATEGORY_MANAGE, permissionID = PermissionConstants.ADM_CATEGORY_MANAGE)
	public void save(HttpServletRequest request, HttpServletResponse response) {
		Category category = (Category) parserJsonParameters(request, false, Category.class);
		String iconImage = ads.getUploadImage(request, "iconImage", null);
		String showImage = ads.getUploadImage(request, "showImage", null);
		if(iconImage != null){
			category.setIconImage(iconImage);
		}
		if(showImage != null){
			category.setShowImage(showImage);
		}
		categoryService.save(category);
		responseWithKeyValue(Category.ID, category.getId(), request, response);

	}

	@RequestMapping("/delete.do")
	@Permission(groupName = PermissionConstants.ADM_CATEGORY_MANAGE, permissionID = PermissionConstants.ADM_CATEGORY_MANAGE)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Category category = (Category) parserJsonParameters(request, false, Category.class);
		logger.info("delete category. " + category.toString());
		categoryService.delete(category);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	@RequestMapping("/list.do")
	@LoginRequired(required = false)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(categoryService.list(), request, response);
	}
	
	@RequestMapping("/listlevel3.do")
	@LoginRequired(required = false)
	public void listLevel3(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(categoryService.listLevel3(), request, response);
	}
	
	@RequestMapping("/listLevel1.do")
	@LoginRequired(required = false)
	public void listLevel1Categories(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(categoryService.listLevel1Categories(), request, response);
	}


	@RequestMapping("/listbyquery.do")
	@LoginRequired(required = false)
	public void listByQuery(HttpServletRequest request, HttpServletResponse response) {
		CategoryVO vo = (CategoryVO) parserJsonParameters(request, true, CategoryVO.class);
		responseWithListData(categoryService.listByQuery(vo), request, response);
	}
	
	//清理数据用
	@RequestMapping("/clear.do")
	@Permission(groupName = PermissionConstants.ADM_CATEGORY_MANAGE, permissionID = PermissionConstants.ADM_CATEGORY_MANAGE)
	public void clearCategoryData(HttpServletRequest request, HttpServletResponse response) {
		String webPath = request.getSession().getServletContext().getRealPath("/");
		webPath = webPath + "/WEB-INF/classes/exceldemo/category.xls";
		System.out.println(webPath);
		ExcleUtil excleUtil = new ExcleUtil(new File(webPath));
		List<String[]> list = excleUtil.getAllData(0);
		categoryService.importExcelCategory(list);
		responseWithData(null, request, response);

	}
	
	public ICategoryService getCategoryService() {
    	return categoryService;
    }

	public void setCategoryService(ICategoryService categoryService) {
    	this.categoryService = categoryService;
    }
	
	
}