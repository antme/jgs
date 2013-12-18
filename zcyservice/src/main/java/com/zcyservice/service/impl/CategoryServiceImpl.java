package com.zcyservice.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.service.AbstractService;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Category;
import com.zcyservice.bean.LocationPrice;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.vo.CategoryVO;
import com.zcyservice.service.ICategoryService;

@Service("categoryService")
public class CategoryServiceImpl extends AbstractService implements ICategoryService {
	private static Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

	@Override
	public Category save(Category category) {

		if(category.getSortIndex() == null){
			category.setSortIndex(1);
		}
		if (category.getParent_id() == null) {
			category.setLevel("1");
		} else {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
			builder.and(Category.ID, category.getParent_id());
			Category parent = (Category) dao.findOneByQuery(builder, Category.class);
			Integer level = Integer.valueOf(parent.getLevel()) + 1;
			category.setLevel(level.toString());
		}

		if (EcUtil.isEmpty(category.getId())) {
			// 类别拆单数默认为1
			if (EcUtil.isEmpty(category.getSplitOrderNumber())) {
				category.setSplitOrderNumber(1);
			}
			

			if (category.getIsVisible() == null) {
				category.setIsVisible(true);
			}
			updateCategorySort(category);
			String log = String.format("添加分类【%s】", category.getName());
			dao.insert(category);
	

		} else {
			updateCategorySort(category);
			dao.updateById(category);
			updateChildren(category);

		}
		return category;
	}
	
	public void updateCategorySort(Category category) {

		int finalSort = category.getSortIndex();
		if (category.getParent_id() != null) {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
			builder.and(Category.ID, category.getParent_id());
			builder.limitColumns(new String[] { Category.SORT_INDEX, Category.PARENT_ID });
			Category parent = (Category) this.dao.findOneByQuery(builder, Category.class);

			if (category.getLevel().equalsIgnoreCase("3")) {

				builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
				builder.and(Category.ID, parent.getParent_id());
				builder.limitColumns(new String[] { Category.SORT_INDEX });
				Category grandPar = (Category) this.dao.findOneByQuery(builder, Category.class);
				finalSort = grandPar.getSortIndex() * 100 + parent.getSortIndex() * 10 + category.getSortIndex();

			} else {
				finalSort = parent.getSortIndex() * 100 + category.getSortIndex();

			}

		}
		category.setFinalSort(finalSort);

	}

	/**更新子节点*/
	private void updateChildren(Category category){
		Boolean isVisible = category.getIsVisible();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
		builder.and(Category.PARENT_ID, category.getId());
		List<Category> children = dao.listByQuery(builder, Category.class);
		for (Category lo : children) {
			lo.setIsVisible(isVisible);

			if (isVisible == null) {
				lo.setIsVisible(true);

			}
			dao.updateById(lo);
			updateChildren(lo);
		}
	}
	
	@Override
    public void delete(Category category) {
		String id = category.getId();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
		builder.and(Category.PARENT_ID, id);
		int children = dao.count(builder);
		if(children > 0){
			throw new ResponseException("请先删除子节点");
		}
		dao.deleteById(category);
    }

	@Override
	public List<Category> list() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);

		builder.orderBy(Category.LEVEL, false);
		builder.orderBy(Category.SORT_INDEX, true);
		builder.limitColumns(new String[] { Category.ID, Category.NAME, Category.LEVEL, Category.SORT_INDEX, Category.PARENT_ID, Category.SPLIT_ORDER_NUMBER, Category.KEYWORD,
		        Category.IS_VISIBLE, Category.ICON_IMAGE, Category.SHOW_IMAGE,Category.COLOR });
		List<Category> itemList = dao.listByQuery(builder, Category.class);

		return mergeToTree(itemList);
	}
	
	public List<Category> listLevel3(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);

		builder.and(Category.LEVEL, "3");
		builder.and(DataBaseQueryOpertion.IS_TRUE, Category.IS_VISIBLE);

		builder.orderBy(Category.LEVEL, false);
		builder.orderBy(Category.SORT_INDEX, true);
		builder.limitColumns(new String[] { Category.ID, Category.NAME, Category.LEVEL, Category.SORT_INDEX, Category.PARENT_ID, Category.SPLIT_ORDER_NUMBER, Category.KEYWORD,
		        Category.IS_VISIBLE, Category.ICON_IMAGE, Category.SHOW_IMAGE });
		return dao.listByQuery(builder, Category.class);
	}

	/***整合列表为树形结构 父：子*/
	private List<Category> mergeToTree(List<Category> itemList){
		Map<String,Category> map = new LinkedHashMap<String,Category>();
		for(Category item : itemList){
			map.put(item.getId(), item);
		}
		
		List<Category> list = new ArrayList<Category>();
		for(Category item : itemList){
			if(item.getParent_id() != null){
				if(map.containsKey(item.getParent_id())){
					map.get(item.getParent_id()).getChilden().add(item);
				}
			} else {
				list.add(map.get(item.getId()));
			}
		}

		return list;
	}
	
	@Override
    public List<Category> listByQuery(CategoryVO vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
		builder.orderBy(Category.LEVEL, false);
		builder.orderBy(Category.SORT_INDEX, true);
		if(vo.getName() != null){
			builder.and(Category.NAME, vo.getName());
		}
		if(vo.getIsVisible() != null){
			int flag = vo.getIsVisible() ? 1 :0;
			builder.and(Category.IS_VISIBLE, flag);
		}
		if(vo.getIconImage() != null){
			builder.and(Category.ICON_IMAGE, vo.getIconImage());
		}
		if(vo.getShowImage() != null){
			builder.and(Category.SHOW_IMAGE, vo.getShowImage());
		}
		if(vo.getKeyword() != null){
			builder.and(Category.KEYWORD, vo.getKeyword());
		}
		if(vo.getLevel() != null){
			builder.and(Category.LEVEL, vo.getLevel());
		}
		if(vo.getParent_id() != null){
			builder.and(Category.PARENT_ID, vo.getParent_id());
		}
		if(vo.getSort() != null){
			builder.and(Category.SORT_INDEX, vo.getSort());
		}
		if(vo.getColor() != null){
			builder.and(Category.COLOR, vo.getColor());
		}
		List<Category> result = dao.listByQuery(builder, Category.class);

		if(Boolean.TRUE.equals(vo.getMergeTree())){
			return mergeToTree(result);
		}
		return result;
    }

	@Override
	public void importExcelCategory(List<String[]> data) {


		Set<String> ids = new HashSet<String>();

		for (String[] item : data) {

			if (item[0].isEmpty()) {
				continue;
			}

			if (item[1].isEmpty()) {
				continue;
			}
			if (item[2].isEmpty()) {
				continue;
			}

			String firstChildName = item[0].trim();
			String firstNode = null;

			String secondNode = null;
			String lastNode = null;

			if (!item[0].isEmpty()) {
				Category c0 = new Category();

				DataBaseQueryBuilder query = new DataBaseQueryBuilder(Category.TABLE_NAME);
				query.and(Category.NAME, firstChildName);
				query.and(Category.LEVEL, "1");
				Category old = (Category) this.dao.findOneByQuery(query, Category.class);

				if (old != null) {
					firstNode = old.getId();
				} else {

					c0.setName(firstChildName);
					c0.setSortIndex(1);
					c0.setLevel("1");
					c0.setSplitOrderNumber(1);
					c0.setIsVisible(true);
					dao.insert(c0);
					firstNode = c0.getId();
				}

			}
			String secondChildName = item[1].trim();
			if (!item[1].isEmpty()) {
				Category c1 = new Category();

				DataBaseQueryBuilder query = new DataBaseQueryBuilder(Category.TABLE_NAME);
				query.and(Category.NAME, secondChildName);
				query.and(Category.LEVEL, "2");
				Category old = (Category) this.dao.findOneByQuery(query, Category.class);

				if (old != null) {
					old.setParent_id(firstNode);
					this.dao.updateById(old);
					secondNode = old.getId();
				} else {
					c1.setName(secondChildName);
					c1.setSortIndex(1);
					c1.setLevel("2");
					c1.setParent_id(firstNode);
					c1.setSplitOrderNumber(1);
					c1.setIsVisible(true);
					dao.insert(c1);
					secondNode = c1.getId();
				}
			}
			String lastChildName = item[2].trim();
			if (!item[2].isEmpty()) {
				Category c2 = new Category();

				DataBaseQueryBuilder query = new DataBaseQueryBuilder(Category.TABLE_NAME);
				query.and(Category.NAME, lastChildName);
				query.and(Category.LEVEL, "3");
				Category old = (Category) this.dao.findOneByQuery(query, Category.class);
				if (old != null) {
					lastNode = old.getId();
					old.setParent_id(secondNode);
					this.dao.updateById(old);
				} else {
					c2.setName(lastChildName);
					c2.setSortIndex(1);
					c2.setLevel("3");
					c2.setParent_id(secondNode);
					c2.setSplitOrderNumber(1);
					c2.setIsVisible(true);
					dao.insert(c2);
					lastNode = c2.getId();

				}
			}

			ids.add(firstNode);
			ids.add(secondNode);
			ids.add(lastNode);
		}
		
		DataBaseQueryBuilder cateDelQuery = new DataBaseQueryBuilder(Category.TABLE_NAME);
		cateDelQuery.and(DataBaseQueryOpertion.NOT_IN, Category.ID, ids);
		this.dao.deleteByQuery(cateDelQuery);
		
		
		this.dao.deleteByQuery(new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME).and(DataBaseQueryOpertion.NOT_IN, SpCategoryLocation.CATEGORY_ID, ids));		
		this.dao.deleteByQuery(new DataBaseQueryBuilder(LocationPrice.TABLE_NAME).and(DataBaseQueryOpertion.NOT_IN, LocationPrice.CATEGORY_ID, ids));
		
		
	}
	
	public Category searchCategoryByKeyword(String title) {
		
		if(EcUtil.isEmpty(title)){
			return null;
		}
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NOT_NULL, Category.PARENT_ID);
		builder.and(Category.LEVEL, "3");
		builder.limitColumns(new String[] { Category.ID, Category.NAME, Category.KEYWORD, Category.SPLIT_ORDER_NUMBER, Category.PARENT_ID,Category.COLOR });
		List<Category> itemList = dao.listByQuery(builder, Category.class);
		Category find = null;
		String findTitle = null;
		for (Category cate : itemList) {

			if (!this.dao.exists(Category.PARENT_ID, cate.getId(), Category.TABLE_NAME)) {
				String keywordString = cate.getKeyword();
				if (!EcUtil.isEmpty(keywordString)) {
					String keywords[] = keywordString.split(",");
					for (String keyword : keywords) {
						if (title.contains(keyword)) {
							if(findTitle == null){
								findTitle = keyword;
							}
							
							if(keyword.length() >= findTitle.length()){
								findTitle = keyword;
								find = cate;
							}
						}
					}

					keywords = keywordString.split(" ");
					for (String keyword : keywords) {
						if (title.contains(keyword)) {
							if(findTitle == null){
								findTitle = keyword;
							}
							
							if(keyword.length() >= findTitle.length()){
								findTitle = keyword;
								find = cate;
							}
						}
					}
				}
				
				String keywords[] = cate.getName().split(",");
				for (String keyword : keywords) {
					if (title.contains(keyword)) {
						if(findTitle == null){
							findTitle = keyword;
						}
						
						if(keyword.length() >= findTitle.length()){
							findTitle = keyword;
							find = cate;
						}
					}
				}
				
				keywords = cate.getName().split("\\+");
				for (String keyword : keywords) {
					if (title.contains(keyword)) {
						if(findTitle == null){
							findTitle = keyword;
						}
						
						if(keyword.length() >= findTitle.length()){
							findTitle = keyword;
							find = cate;
						}
					}
				}
				
				keywords = cate.getName().split(" ");
				for (String keyword : keywords) {
					if (title.contains(keyword)) {
						if(findTitle == null){
							findTitle = keyword;
						}
						
						if(keyword.length() >= findTitle.length()){
							findTitle = keyword;
							find = cate;
						}
					}
				}
				
				String name = cate.getName();
				if (!EcUtil.isEmpty(name)) {
					if (title.contains(name)) {
						if (findTitle == null) {
							findTitle = name;
						}

						if (name.length() >= findTitle.length()) {
							findTitle = name;
							find = cate;
						}
					}

				}
				
			}

		}
		return find;

	}
	

	public String getCateString(String[] cateIds) {
		String cateStr = "";
		for (String id : cateIds) {
			Category cate = (Category) this.dao.findById(id, Category.TABLE_NAME, Category.class);
			cateStr = cateStr + "  " + cate.getName();
		}
		return cateStr;

	}


	public String getCateStringRecu(String cateId) {
		String cateStr = "";
		Category cate = (Category) this.dao.findById(cateId, Category.TABLE_NAME, Category.class);

		if (cate != null) {
			cateStr = cate.getName();

			if (cate.getParent_id() != null) {
				String cateParentStr = getCateStringRecu(cate.getParent_id());
				if (!cateStr.contains(cateParentStr)) {
					cateStr = cateParentStr + "," + cateStr;
				}

			}
		}

		return cateStr;
	}

	/**获取所有的子节点以及子子节点的id*/
	public List<String> getAllChildren(List<String> ids){
		List<String> result = new ArrayList<String>();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, Category.PARENT_ID, ids);
		builder.limitColumns(new String[] {Category.ID});
		List<Category> list = dao.listByQuery(builder, Category.class);
		for(Category item : list){
			result.add(item.getId());
		}
		if(result.size() > 0){
			result.addAll(getAllChildren(result));
		}
		return result;
	}
	
	public List<Category> listLevel1Categories() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);

		builder.and(Category.LEVEL, "1");

		builder.orderBy(Category.LEVEL, false);
		builder.orderBy(Category.SORT_INDEX, true);
		builder.limitColumns(new String[] { Category.ID, Category.NAME, Category.LEVEL, Category.SORT_INDEX, Category.PARENT_ID, Category.SPLIT_ORDER_NUMBER, Category.KEYWORD,
		        Category.IS_VISIBLE, Category.ICON_IMAGE, Category.SHOW_IMAGE });
		return dao.listByQuery(builder, Category.class);
	}
	
	
}
