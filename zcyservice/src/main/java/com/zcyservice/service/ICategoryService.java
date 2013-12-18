package com.zcyservice.service;

import java.util.List;

import com.zcyservice.bean.Category;
import com.zcyservice.bean.vo.CategoryVO;

public interface ICategoryService {

	public Category save(Category location);
	
	public List<Category> listByQuery(CategoryVO vo);
	
	public List<Category> list();
	
	public void delete(Category location);

	public void importExcelCategory(List<String[]> data);
	
	public Category searchCategoryByKeyword(String title);
	
	public String getCateStringRecu(String cateId);
	
	public String getCateString(String[] cateIds);

	public List<String> getAllChildren(List<String> ids);

	public List<Category> listLevel1Categories();

	public List<Category> listLevel3();
}
