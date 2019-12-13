package com.proton.tvapp.api;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proton.tvapp.dto.CategoryDto;
import com.proton.tvapp.dto.CategorySearchDto;
import com.proton.tvapp.dto.DataResponseDto;
import com.proton.tvapp.dto.TvAppResponse;
import com.proton.tvapp.service.CategoryService;
import com.proton.tvapp.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.CategoryCtrl.CTRL)
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StringRedisTemplate redis;
	
	@GetMapping
	public ResponseEntity<TvAppResponse> listCategory(@RequestParam Map<String,String> reqPar){
		CategorySearchDto searchParam = modelMapper.map(reqPar, CategorySearchDto.class);
		
		Map<String, Object> result = categoryService.searchList(searchParam);
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("rowCount", result.get("rowCount"));
		body.put("totalPage", result.get("totalPage"));
		body.put("page", result.get("page"));
		body.put("pageIn", result.get("pageIn"));
		body.put("category", result.get("category"));
		
		DataResponseDto dataResp = new DataResponseDto();
		dataResp.setStatus(HttpStatus.OK.value());
		dataResp.setError(HttpStatus.OK.toString());
		dataResp.setData(body);
			
		return new ResponseEntity<TvAppResponse>(new TvAppResponse(dataResp), HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<TvAppResponse> getByIdCategory(@PathVariable("id") Long id) {
		Map<String, Object> body = new LinkedHashMap<>(); 
		body.put("category", categoryService.getById(id));
		
		DataResponseDto dataResp = new DataResponseDto();
		dataResp.setStatus(HttpStatus.OK.value());
		dataResp.setError(HttpStatus.OK.toString());
		dataResp.setData(body);
		
		return new ResponseEntity<TvAppResponse>(new TvAppResponse(dataResp), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<TvAppResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		CategoryDto category = categoryService.save(categoryDto);
		
		Map<String, Object> body = new LinkedHashMap<>(); 
		body.put("category", category);
		
		DataResponseDto dataResp = new DataResponseDto();
		dataResp.setStatus(HttpStatus.CREATED.value());
		dataResp.setError(HttpStatus.CREATED.toString());
		dataResp.setData(body);
		
		return new ResponseEntity<TvAppResponse>(new TvAppResponse(dataResp), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TvAppResponse> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryDto categoryDto) {
		
		CategoryDto category = categoryService.update(id, categoryDto);
		
		Map<String, Object> body = new LinkedHashMap<>(); 
		body.put("category", category);
		
		DataResponseDto dataResp = new DataResponseDto();
		dataResp.setStatus(HttpStatus.OK.value());
		dataResp.setError(HttpStatus.OK.toString());
		dataResp.setData(body);
		
		return new ResponseEntity<TvAppResponse>(new TvAppResponse(dataResp), HttpStatus.OK);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TvAppResponse> deleteCategory(@PathVariable(value = "id", required = true) Long id){
		categoryService.delete(id);
		
		DataResponseDto dataResp = new DataResponseDto();
		dataResp.setStatus(HttpStatus.NO_CONTENT.value());
		dataResp.setError(HttpStatus.NO_CONTENT.toString());
		
		return new ResponseEntity<TvAppResponse>(new TvAppResponse(dataResp), HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/deneme")
	public String denemeTest(@RequestParam Map<String, String> reqPar) {
		//@RequestParam Map<String,String> reqPar reqPar.get("test")
		//HttpServletRequest request request.getParameter(name);
		
		CategorySearchDto search = modelMapper.map(reqPar, CategorySearchDto.class); 
		
		return "Merhaba dünya "+search.toString();
	}
	
	
}