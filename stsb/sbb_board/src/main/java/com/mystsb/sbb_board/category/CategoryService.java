package com.mystsb.sbb_board.category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mystsb.sbb_board.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public Category create(String title) {
		Category category = new Category();
		category.setTitle(title);
		category.setCreateDate(LocalDateTime.now());
		this.categoryRepository.save(category);
		return category;
	}
	
	public Category getCategoryByTitle(String title) {
		Optional<Category> category = this.categoryRepository.findByTitle(title);
		if(category.isEmpty()) {
			category = Optional.ofNullable(create(title));
		}
		if(category.isPresent()) {
			return category.get();
		} else {
			throw new DataNotFoundException("category not found");
		}
	}


}
