package com.sh.product.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.product.model.exception.InsufficientOutputAmountException;
import com.sh.product.model.exception.ProductException;
import com.sh.product.model.vo.Product;
import com.sh.product.model.vo.ProductIO;

import static com.sh.product.common.JdbcTemplate.*;

public class ProductDao {
	
	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.load(new FileReader("resources/product-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전체 상품 조회
	 * @param conn
	 * @return
	 */
	public List<Product> viewAllProduct(Connection conn) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("viewAllProduct");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					Product product = handleProductResultSet(rset);
					products.add(product);
				}
			}
		} catch (Exception e) {
			throw new ProductException("상품 전체 조회 오류!");
		}		
		
		return products;
	}
	
	/**
	 * 상품 검색
	 * @param conn
	 * @param colName
	 * @param search
	 * @return
	 */
	public List<Product> searchProductBy(Connection conn, String colName, String search) {
		String sql = prop.getProperty("searchProductBy");
		sql = sql.replace("#", colName);
		List<Product> products = new ArrayList<Product>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + search + "%");
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					Product product = handleProductResultSet(rset);
					products.add(product);
				}
			}
			
		} catch (Exception e) {
			throw new ProductException("상품명 검색 오류! : " + e.getMessage(), e);	
		}
		
		return products;
	}

	/**
	 * 상품 등록
	 * @param conn
	 * @param product
	 * @return
	 */
	public int insertProduct(Connection conn, Product product) {
		int result = 0;
		String sql = prop.getProperty("insertProduct");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getId());
			pstmt.setString(2, product.getBrand());
			pstmt.setString(3, product.getName());
			pstmt.setInt(4, product.getPrice());
			pstmt.setInt(5, product.getMonitorSize());
			pstmt.setString(6, product.getOs());
			pstmt.setInt(7, product.getStorage());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new ProductException("상품 등록 오류!");
		}
		return result;
	}

	/**
	 * 상품 정보 수정
	 * @param conn
	 * @param productId
	 * @param product
	 * @return
	 */
	public int updateProductInfo(Connection conn, String productId, Product product) {
		String sql = prop.getProperty("updateProductInfo");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getMonitorSize());
			pstmt.setString(4, product.getOs());
			pstmt.setInt(5, product.getStorage());
			pstmt.setString(6, productId);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new ProductException("수정 오류!");
		} finally {
			close(pstmt);
		}
	
		return result;
	}

	/**
	 * 상품 삭제
	 * @param conn
	 * @param productId
	 * @return
	 */
	public int deleteProduct(Connection conn, String productId) {
		int result = 0;
		String sql = prop.getProperty("deleteProduct");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productId);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new ProductException("상품 삭제 오류!");
		}
		return result;
	}

	/**
	 * 입출고 내역 조회
	 * @param conn
	 * @param productId
	 * @return
	 */
	public List<ProductIO> viewProductIO(Connection conn, String productId) {
		String sql = prop.getProperty("viewProductIOList");
		List<ProductIO> productIOs = new ArrayList<>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productId);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					ProductIO productIO = new ProductIO();
					productIO.setNo(rset.getInt("no"));
					productIO.setProductId(rset.getString("product_id"));
					productIO.setName(rset.getString("name"));
					productIO.setBrand(rset.getString("brand"));
					productIO.setCount(rset.getInt("count"));
					productIO.setStatus(rset.getString("status"));
					productIO.setIoDatetime(rset.getTimestamp("io_datetime"));
					productIOs.add(productIO);
				}
			}
		} catch (SQLException e) {
			throw new ProductException("입출고 조회 오류!");
		}
		
		return productIOs;
	}

	/**
	 * 입출고 
	 * @param conn
	 * @param productIO
	 * @return
	 */
	public int insertProductIO(Connection conn, ProductIO productIO) {
		String sql = prop.getProperty("ioManager");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productIO.getProductId());
			pstmt.setInt(2, productIO.getCount());
			pstmt.setString(3, productIO.getStatus());

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			if(e.getMessage().contains("ck_product_stock")) {
				throw new InsufficientOutputAmountException("출고량이  재고량  보다 많습니다.");
			}
			throw new ProductException("입고 오류!");
		} 
		return result;
	}

	/**
	 * 아이디로 상품 조회
	 * @param conn
	 * @param productId
	 * @return
	 */
	public Product searchByProductId(Connection conn, String productId) {
		String sql = prop.getProperty("searchByProductId");
		Product product = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productId);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					product = handleProductResultSet(rset);
				}
			}
		} catch (Exception e) {
			throw new ProductException("아이디 찾기 오류!");
		}
		return product;
	}

	/**
	 * 상품 정보 가져오기
	 * @param rset
	 * @return
	 * @throws SQLException
	 */
	public Product handleProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setId(rset.getString("id"));
		product.setBrand(rset.getString("brand"));
		product.setName(rset.getString("name"));
		product.setPrice(rset.getInt("price"));
		product.setMonitorSize(rset.getInt("monitor_size"));
		product.setOs(rset.getString("os"));
		product.setStorage(rset.getInt("storage"));
		product.setStock(rset.getInt("stock"));
		product.setRegDateTimestamp(rset.getTimestamp("reg_date"));
		return product;
	}
	
}
