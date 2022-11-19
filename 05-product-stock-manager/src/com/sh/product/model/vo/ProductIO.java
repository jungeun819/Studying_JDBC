package com.sh.product.model.vo;

import java.sql.Timestamp;

public class ProductIO extends Product {

	// Filed 생성
	private int no;
	private String productId;
	private int count;
	private String status;
	private Timestamp ioDatetime;

	// 생성자
	public ProductIO() {
		super();
	}
	
	public ProductIO(int no, String productId, int count, String status, Timestamp ioDatetime) {
		super();
		this.no = no;
		this.productId = productId;
		this.count = count;
		this.status = status;
		this.ioDatetime = ioDatetime;
	}

	public ProductIO(String brand, String name, int no, String productId, int count, 
			String status, Timestamp ioDatetime) {
		super(brand, name);
		this.no = no;
		this.productId = productId;
		this.count = count;
		this.status = status;
		this.ioDatetime = ioDatetime;
	}

	// getter setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getIoDatetime() {
		return ioDatetime;
	}

	public void setIoDatetime(Timestamp ioDatetime) {
		this.ioDatetime = ioDatetime;
	}
	
}
