package com.anusha.projects.hibernatemappings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.anusha.projects.hibernatemappings.model.Category;
import com.anusha.projects.hibernatemappings.model.Stock;
import com.anusha.projects.hibernatemappings.model.StockDailyRecord;
import com.anusha.projects.hibernatemappings.model.StockDetail;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private StockDetailRepository stockDetailRepository;

	@Autowired
	private StockDailyRecordRepository stockDailyRecordRepository;

	@Autowired
	private StockCategoryRepository stockCategoryRepository;

	@Test
	public void testOneToOneMapping() {
		// Prepare Objects and set them
		Stock stock = new Stock();

		stock.setStockCode("7052");
		stock.setStockName("PADINI");

		StockDetail stockDetail = new StockDetail();
		stockDetail.setCompName("PADINI Holding Malaysia");
		stockDetail.setCompDesc("One stop shopping");
		stockDetail.setRemark("Initial Offering");
		stockDetail.setListedDate(new Date());

		stock.setStockDetail(stockDetail);
		stockDetail.setStock(stock);
		Stock savedStock = stockRepository.save(stock);

		// assert that the id is the same and that the mapping is established.
		int stockId = savedStock.getStockId();
		assertThat(stockId).isGreaterThan(0);
		assertThat(stockDetailRepository.findOne(stockId).getRemark()).isEqualTo("Initial Offering");

	}

	@Test
	public void testOneToManyAndManyToOneMapping() {
		// Prepare Objects and set them
		Stock stock = new Stock();

		stock.setStockCode("7052");
		stock.setStockName("PADINI");

		StockDailyRecord stockDailyRecords = new StockDailyRecord();
		stockDailyRecords.setPriceOpen(new Float("1.2"));
		stockDailyRecords.setPriceClose(new Float("1.1"));
		stockDailyRecords.setPriceChange(new Float("10.0"));
		stockDailyRecords.setVolume(3000000L);
		stockDailyRecords.setDate(new Date());

		stockDailyRecords.setStock(stock);
		stock.getStockDailyRecords().add(stockDailyRecords);

		Stock savedStock = stockRepository.save(stock);

		// assert that the id is the same and that the mapping is established from one
		// to many side and the many to one side..
		int stockId = savedStock.getStockId();
		int recordId = savedStock.getStockDailyRecords().get(0).getRecordId();
		assertThat(stockId).isGreaterThan(0);
		assertThat(recordId).isGreaterThan(0);
		assertThat(stockRepository.findOne(stockId).getStockDailyRecords().get(0).getRecordId()).isEqualTo(recordId);
		assertThat(stockDailyRecordRepository.findOne(recordId).getStock().getStockId()).isEqualTo(stockId);

	}

	@Test
	public void testManyToManyMapping() {
		// Prepare Objects.
		Stock stock = new Stock();

		stock.setStockCode("7052");
		stock.setStockName("PADINI");

		Category category1 = new Category("CONSUMER", "CONSUMER COMPANY");
		Category category2 = new Category("INVESTMENT", "INVESTMENT COMPANY");

		category1.getStocks().add(stock);
		category2.getStocks().add(stock);

		stock.getCategories().add(category1);
		stock.getCategories().add(category2);

		Stock savedStock = stockRepository.save(stock);

		// assert that the id is the same on both sides of many to many mapping.
		int stockId = savedStock.getStockId();
		int categoryId1 = savedStock.getCategories().get(0).getCategoryId();
		assertThat(stockId).isGreaterThan(0);
		assertThat(categoryId1).isGreaterThan(0);
		assertThat(stockRepository.findOne(stockId).getCategories().size()).isEqualTo(2);
		assertThat(stockRepository.findOne(stockId).getCategories().get(0).getCategoryId()).isEqualTo(categoryId1);
		assertThat(stockCategoryRepository.findOne(categoryId1).getStocks().get(0).getStockId()).isEqualTo(stockId);

	}


}
