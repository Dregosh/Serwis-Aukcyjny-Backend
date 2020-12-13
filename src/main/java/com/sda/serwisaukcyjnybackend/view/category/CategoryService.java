package com.sda.serwisaukcyjnybackend.view.category;

import com.sda.serwisaukcyjnybackend.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());
    }
}
