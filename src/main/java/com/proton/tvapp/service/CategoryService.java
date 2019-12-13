package com.proton.tvapp.service;

import java.util.List;
import java.util.Map;

import com.proton.tvapp.dto.CategoryDto;
import com.proton.tvapp.dto.CategorySearchDto;

public interface CategoryService {
	CategoryDto save(CategoryDto categoryDto);
	CategoryDto update(long id, CategoryDto categoryDto);
	boolean delete(long id);
	
	CategoryDto getById(long id);
	CategoryDto findBySeoLink(String seoLink);
	
	List<CategoryDto> getAll();
	Map<String, Object> searchList(CategorySearchDto param);
	
}
