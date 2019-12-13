package com.proton.tvapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proton.tvapp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom{
	Category findBySeoLink(String seoLink);
	boolean existsBySeoLink(String seoLink);
}
