package com.anusha.projects.hibernatemappings.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category", catalog = "testdb")
public class Category {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CATEGORY_ID", unique = true, nullable = false)
	private Integer categoryId;

	@Column(name = "NAME", nullable = false, length = 10)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private List<Stock> stocks = new ArrayList<>();

	public Category() {
	}

	public Category(String name, String desc) {
		this.name = name;
		this.description = desc;
	}

	public Category(String name, String desc, List<Stock> stocks) {
		this.name = name;
		this.description = desc;
		this.stocks = stocks;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

}