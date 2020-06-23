package com.anusha.projects.hibernatemappings;

import org.springframework.data.repository.CrudRepository;

import com.anusha.projects.hibernatemappings.model.StockDailyRecord;


public interface StockDailyRecordRepository extends CrudRepository<StockDailyRecord, Integer> {
}
