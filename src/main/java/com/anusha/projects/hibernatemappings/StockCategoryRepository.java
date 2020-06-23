package com.anusha.projects.hibernatemappings;

import org.springframework.data.repository.CrudRepository;

import com.anusha.projects.hibernatemappings.model.Category;


public interface StockCategoryRepository extends CrudRepository<Category, Integer> {

}
