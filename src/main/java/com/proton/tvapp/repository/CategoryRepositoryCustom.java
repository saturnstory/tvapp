package com.proton.tvapp.repository;

import java.util.List;
import java.util.Map;

import com.proton.tvapp.dto.CategorySearchDto;
import com.proton.tvapp.model.Category;

public interface CategoryRepositoryCustom {
	Map<String, Object> searchList(CategorySearchDto param);
}
