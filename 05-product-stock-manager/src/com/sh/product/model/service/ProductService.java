package com.sh.product.model.service;

import java.sql.Connection;
import java.util.List;

import static com.sh.product.common.JdbcTemplate.*;
import com.sh.product.model.dao.ProductDao;
import com.sh.product.model.vo.Product;
import com.sh.product.model.vo.ProductIO;

public class ProductService {
	private ProductDao productDao = new ProductDao();

	public List<Product> viewAllProduct() {
		Connection conn = getConnection();
		List<Product> products = productDao.viewAllProduct(conn);
		close(conn);
		return products;
	}

	public int updateProductInfo(String productId, Product product) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = productDao.updateProductInfo(conn, productId, product);
			commit(conn);
		}catch (Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}

	public List<Product> searchProductBy(String colName, String search) {
		Connection conn = getConnection();
		List<Product> products = productDao.searchProductBy(conn, colName, search);
		close(conn);
		return products;
	}

	public List<ProductIO> viewProductIO(String productId) {
		Connection conn = getConnection();
		List<ProductIO> productIOs = productDao.viewProductIO(conn, productId);
		close(conn);
		return productIOs;
	}

	public int insertProductIO(ProductIO productIO) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = productDao.insertProductIO(conn, productIO);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			close(conn);
		}
		return result;
	}

	public Product searchByProductId(String productId) {
		Connection conn = getConnection();
		Product product = productDao.searchByProductId(conn, productId);
		close(conn);
		return product;
	}

	public int insertProduct(Product product) {
		Connection conn = getConnection();
		int result = 0; 
		try {
			result = productDao.insertProduct(conn, product);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteProduct(String productId) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = productDao.deleteProduct(conn, productId);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			close(conn);
		}
		return result;
	}
}
