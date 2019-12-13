package com.proton.tvapp.repository.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.proton.tvapp.dto.CategorySearchDto;
import com.proton.tvapp.model.Category;
import com.proton.tvapp.repository.CategoryRepositoryCustom;
import com.proton.tvapp.util.PageableUtil;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	
	//Delete method old version
	public List<Category> searchList2(CategorySearchDto param) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);
		criteriaQuery.select(root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		//Search
		if(param.getName() != null && param.getName() != "") {
			predicates.add(builder.equal(root.get("name"), param.getName()));
		}
		
		if(param.getSeoLink() != null) {
			predicates.add(builder.equal(root.get("seoLink"), param.getSeoLink()));
		}
		
		if(param.getKeywords() != null) {
			predicates.add(builder.like(root.get("keywords"), "%"+param.getKeywords()+"%"));
		}
		
		if(param.getDescription() != null) {
			predicates.add(builder.like(root.get("description"), "%"+param.getDescription()+"%"));
		}	
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		//Sorting
		List<javax.persistence.criteria.Order> order = new ArrayList<javax.persistence.criteria.Order>(); 
		if(param.getNameSort() != null) {
			if(param.getNameSort().contentEquals("desc")) order.add(builder.desc(root.get("name")));
			if(param.getNameSort().contentEquals("asc")) order.add(builder.asc(root.get("name")));
		}
		if(param.getSeoLinkSort() != null ) {
			if(param.getSeoLinkSort().contentEquals("desc")) order.add(builder.desc(root.get("seoLink")));
			if(param.getSeoLinkSort().contentEquals("asc")) order.add(builder.asc(root.get("seoLink")));
		}
		if(order.size() > 0) {
			criteriaQuery.orderBy(order);
		}
		
		//Pageable
		long rowCount = 0; //count işlemi yapılacak
		int page = PageableUtil.page(param.getPage());
		int limit = PageableUtil.limit(param.getLimit());
		int skip = PageableUtil.skip(page, limit);
		long totalPage = PageableUtil.totalPage(rowCount, limit);
		
		//Get
		Query<Category> createQuery =  (Query<Category>) em.createQuery(criteriaQuery);
		createQuery.setFirstResult(skip);
		createQuery.setMaxResults(limit);
		List<Category> category = createQuery.list();
		
		//Result
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("rowCount",rowCount);
		result.put("totalPage",totalPage);
		result.put("page",page);
		result.put("pageIn",category.size());
		result.put("category",category);
		
		System.err.println(result);
		
		return null;
	}
	
	@Override
	public Map<String, Object> searchList(CategorySearchDto param) {
		//this.searchList2(param);
		Session session = em.unwrap(Session.class);
	
		Criteria criteria = session.createCriteria(Category.class);
			
		//Search
		if(param.getName() != null && param.getName() != "") {
			criteria.add(Restrictions.eq("name",param.getName()));
		}
		
		if(param.getSeoLink() != null) {
			criteria.add(Restrictions.eq("seoLink",param.getSeoLink()));
		}
		
		if(param.getKeywords() != null) {
			criteria.add(Restrictions.like("keywords", "%"+param.getKeywords()+"%"));
		}

		if(param.getDescription() != null) {
			criteria.add(Restrictions.like("description", "%"+param.getDescription()+"%"));
		}
		
		//Record Count
		long rowCount = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		
		//Sorting
		if(param.getNameSort() != null) {			
			if(param.getNameSort().contentEquals("desc")) criteria.addOrder(Order.desc("name"));
			if(param.getNameSort().contentEquals("asc")) criteria.addOrder(Order.asc("name"));
		}
		
		if(param.getSeoLinkSort() != null) {
			if(param.getSeoLinkSort().contentEquals("desc")) criteria.addOrder(Order.desc("seoLink"));
			if(param.getSeoLinkSort().contentEquals("asc")) criteria.addOrder(Order.asc("seoLink"));
		}
		
		//Pageable
		int page = PageableUtil.page(param.getPage());
		int limit = PageableUtil.limit(param.getLimit());
		int skip = PageableUtil.skip(page, limit);
		long totalPage = PageableUtil.totalPage(rowCount, limit);
			
		criteria.setFirstResult(skip);
		criteria.setMaxResults(limit);
		List<Category> category = criteria.list();
		
		//Result
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("rowCount",rowCount);
		result.put("totalPage",totalPage);
		result.put("page",page);
		result.put("pageIn",category.size());
		result.put("category",category);
		
		return result;
	}

}