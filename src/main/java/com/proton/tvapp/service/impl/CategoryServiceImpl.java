package com.proton.tvapp.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.proton.tvapp.advice.RecordNotFoundException;
import com.proton.tvapp.dto.CategoryDto;
import com.proton.tvapp.dto.CategorySearchDto;
import com.proton.tvapp.model.Category;
import com.proton.tvapp.repository.CategoryRepository;
import com.proton.tvapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	//@CachePut(value = "category", key = "#result.id")
	@Caching(
		put = {@CachePut(value = "category", key = "#result.id")},
		evict = {@CacheEvict(value = "allCategory", allEntries = true)}
	)
	public CategoryDto save(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		
		if(categoryRepository.existsBySeoLink(category.getSeoLink())) {
			throw new RecordNotFoundException("Category is exists seoLink : "+ category.getSeoLink() +" - not created");
		}
		
		//tarih ve oluşturan user bilgileri set edilmeli
		category = categoryRepository.save(category);
		System.out.println(category.getId());
		
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	//@CachePut(value = "category", key = "#id")
	@Caching(
		put = {@CachePut(value = "category", key = "#id")},
		evict = {@CacheEvict(value = "allCategory", allEntries = true)}
	)
	public CategoryDto update(long id, CategoryDto categoryDto) {
		if(!categoryRepository.findById(id).isPresent()) {
			throw new RecordNotFoundException("Category Does not exsist Id : "+ id +" - not updated");
		}
		
		Category dbCategory = categoryRepository.getOne(id);
		
		Category category = modelMapper.map(categoryDto, Category.class);
		category.setId(id);
		dbCategory =  categoryRepository.save(category);
		
		return modelMapper.map(dbCategory, CategoryDto.class);
	}

	@Override
	@CacheEvict(value = "category", key = "#id")
	public boolean delete(long id) {
		if(!categoryRepository.findById(id).isPresent()) {
			throw new RecordNotFoundException("Category Does not exsist Id : "+ id +" - not deleted");
		}
		categoryRepository.deleteById(id);
		return true;
	}
	
	@Override
	@Cacheable(value= "category", key = "#id")
	public CategoryDto getById(long id) {
		if(!categoryRepository.findById(id).isPresent()) {
			throw new RecordNotFoundException("Category Does not exsist Id : "+ id);
		}	
		Category category = categoryRepository.getOne(id);
		return modelMapper.map(category, CategoryDto.class);
	}
	
	@Override
	@Cacheable(value = "category", key="#seoLink")
	public CategoryDto findBySeoLink(String seoLink) {
		Category category = categoryRepository.findBySeoLink(seoLink);
		if(category==null) {
			throw new RecordNotFoundException("Category Does not exsist seoLink : "+ seoLink);
		}
		return modelMapper.map(category, CategoryDto.class);
	}
	
	@Override
	@Cacheable(value = "allCategory", unless = "#result.size() == 0")
	public List<CategoryDto> getAll(){	
		List<Category> category = categoryRepository.findAll();
		if(category.size()==0) {
			throw new RecordNotFoundException("Category Does not exsist");
		}
		return Arrays.asList(modelMapper.map(category,CategoryDto[].class));
	}

	@Override
	@Cacheable(value = "allCategory", unless = "#result.size() == 0")
	public Map<String, Object> searchList(CategorySearchDto param) {
		Map<String, Object> result = categoryRepository.searchList(param);
		List<Category> category = (List<Category>) result.get("category");
		if(category.size()==0) {
			throw new RecordNotFoundException("No result found in category search");
		}
		
		List<CategoryDto> categoryDto = Arrays.asList(modelMapper.map(category, CategoryDto[].class));
		result.put("category", categoryDto);
		
 		return result;
	}

}
