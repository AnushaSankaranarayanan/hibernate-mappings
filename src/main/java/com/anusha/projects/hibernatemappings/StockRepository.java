package com.anusha.projects.hibernatemappings;

import org.springframework.data.repository.CrudRepository;

import com.anusha.projects.hibernatemappings.model.Stock;


public interface StockRepository extends CrudRepository<Stock, Integer> {

}
