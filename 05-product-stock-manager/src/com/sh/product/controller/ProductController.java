package com.sh.product.controller;

import java.util.List;

import com.sh.product.model.exception.InsufficientOutputAmountException;
import com.sh.product.model.exception.ProductException;
import com.sh.product.model.service.ProductService;
import com.sh.product.model.vo.Product;
import com.sh.product.model.vo.ProductIO;

public class ProductController {
	
	private ProductService productService = new ProductService();

	public List<Product> viewAllProduct() {
		List<Product> products = null;
		try {
			products = productService.viewAllProduct();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "]");
		}
		return products;
	}

	public int updateProductInfo(String productId, Product product) {
		int result = 0;
		try {
			result = productService.updateProductInfo(productId, product);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return result;
	}

	public List<Product> searchProductBy(String colName, String search) {
		List<Product> products = null;
		try {
			products = productService.searchProductBy(colName, search);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return products;
	}

	public List<ProductIO> viewProductIO(String productId) {
		List<ProductIO> productIOs = null;
		try {
			productIOs = productService.viewProductIO(productId);
		} catch (InsufficientOutputAmountException e) {
			System.err.println("[" + e.getMessage() + "]");
		} catch (ProductException e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return productIOs;
	}

	public int insertProductIO(ProductIO productIO) {
		int result = 0;
		try {
			result = productService.insertProductIO(productIO);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return result;
	}

	public Product searchByProductId(String productId) {
		Product product = null;
		try {
			product = productService.searchByProductId(productId);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return product;
	}

	public int insertProduct(Product product) {
		int result = 0;
		try {
			result = productService.insertProduct(product);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return result;
	}

	public int deleteProduct(String productId) {
		int result = 0;
		try {
			result = productService.deleteProduct(productId);
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "]");
		}
		return result;
	}
	
}
