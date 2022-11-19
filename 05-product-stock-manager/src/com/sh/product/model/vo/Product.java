package com.sh.product.model.vo;

import java.sql.Timestamp;

public class Product {

	// Filed 생성
	private String id;
	private String brand;
	private String name;
	private int price;
	private int monitorSize;
	private String os;
	private int storage;
	private int stock;
	private Timestamp regDateTimestamp;

	// 생성자
	public Product() {}

	public Product(String id, String brand, String name, int price, int monitorSize, String os, int storage, int stock,
			Timestamp regDateTimestamp) {
		super();
		this.id = id;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.monitorSize = monitorSize;
		this.os = os;
		this.storage = storage;
		this.stock = stock;
		this.regDateTimestamp = regDateTimestamp;
	}
	
	public Product(String id, String brand) {
		super();
		this.id = id;
		this.brand = brand;
	}

	
	// getter setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMonitorSize() {
		return monitorSize;
	}

	public void setMonitorSize(int monitorSize) {
		this.monitorSize = monitorSize;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Timestamp getRegDateTimestamp() {
		return regDateTimestamp;
	}

	public void setRegDateTimestamp(Timestamp regDateTimestamp) {
		this.regDateTimestamp = regDateTimestamp;
	}

	@Override
	public String toString() {
		return "id : " + id + "\nbrand : " + brand + "\nname=" + name + 
			 "\nprice : " + price + "\nmonitorSize : " + monitorSize + "\nos : " + os + 
			 "\nstorage : " + storage + "\nstock : " + stock + "\nregDateTimestamp : " + regDateTimestamp;
	}
	
	
	
}
