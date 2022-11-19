package com.sh.product.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import com.sh.product.controller.ProductController;
import com.sh.product.model.vo.Product;
import com.sh.product.model.vo.ProductIO;

public class ProductMenu {
	private Scanner sc = new Scanner(System.in);
	private ProductController productController = new ProductController();
	
	/**
	 * 메인메뉴
	 */
	public void mainMenu() {
		String meun = "***** 상품 재고관리 프로그램 *****\r\n"
					+ "1. 전체 상품 조회\r\n"
					+ "2. 상품 검색\r\n"
					+ "3. 상품 등록\r\n"
					+ "4. 상품 정보 변경\r\n"
					+ "5. 상품 삭제\r\n"
					+ "6. 상품 입/출고 \r\n"
					+ "0. 프로그램 종료\r\n"
					+ "********************************\r\n"
					+ "선택 : ";
		
		while(true) {
			System.out.print(meun);
			String choice = sc.next();
			List<Product> products = null;
			Product product = null;
			int result = 0;
			
			switch(choice) {
			case "1" : // 전체 상품 조회
				products = productController.viewAllProduct();
				displayProducts(products);
				break;
			case "2" : // 상품 검색
				searchProductMenu();
				break;
			case "3" : // 상품 등록
				product = inpuetProduct();
				result =  productController.insertProduct(product);
				displayResult("> 상품 등록", result);
				displayProduct(product);
				break;
			case "4" : 
				updateProductMenu(singleoutProduct());
				break;
			case "5" : // 상품 삭제 
				System.out.println("> 삭제할 상품을 선택해 주세요");
				String productId = singleoutProduct();
				System.out.print("> 정말 삭제하시겠습니까?(y/n) : ");
				String yn = sc.next();
				if(yn.equals("n")) {
					break;
				}
				result = productController.deleteProduct(productId);
				displayResult("> 삭제", result);
				break;
			case "6" : // 상품 입출고
				prodcutIOMenu(singleoutProduct());
				break;
			case "0" : return;
			default : System.out.println("> 잘못 입력하셨습니다.");
			}
		}
	}

	/**
	 * 상품 검색 메뉴
	 */
	private void searchProductMenu() {
		String menu = "***** 상품검색 메뉴 *****\r\n"
					+ "1.아이디 검색\r\n"
					+ "2.상품명 검색\r\n"
					+ "0.메인메뉴로 돌아가기\r\n"
					+ "************************\r\n"
					+ "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			List<Product> products = null;
			
			switch(choice) {
			case "1" : 
				System.out.print("> 상품 아이디를 입력해주세요 : ");
				String productId = sc.next();
				products = productController.searchProductBy("id", productId);
				displayProducts(products);
				break;
			case "2" : 
				System.out.print("> 상품명을 입력해주세요 : ");
				String productName = sc.next();
				products = productController.searchProductBy("name", productName);
				displayProducts(products);
				
				break;
			case "0" : return;
			default : System.out.println("> 잘못 입력하셨습니다.");
			}
		}
		
	}

	/**
	 * 입출고 메뉴
	 * @param productId
	 */
	private void prodcutIOMenu(String productId) {
		// product 아이디를 전달받아 입출고
		String menu = "***** 상품입출고 메뉴*****\r\n"
					+ "1. 입출고 내역 조회\r\n"
					+ "2. 상품 입고\r\n"
					+ "3. 상품 출고\r\n"
					+ "0. 메인메뉴로 돌아가기\r\n"
					+ "********************\r\n"
					+ "선택 : ";
		
		while(true) {
			
			System.out.print(menu);
			String choice = sc.next();
			List<ProductIO> productIOs = null;
			ProductIO productIO = null;
			int result = 0;
			
			switch (choice) {
			case "1": // 입출고 내역 조회
				productIOs = productController.viewProductIO(productId);
				displayProductIOs(productIOs);
				break;
			case "2": // 상품 입고
				productIO = inputProductIO(productId, "I");
				result = productController.insertProductIO(productIO);
				displayResult("입고", result);
				break;
			case "3": // 상품 출고
				productIO = inputProductIO(productId, "O");
				result = productController.insertProductIO(productIO);
				displayResult("출고", result);
				break;
			case "0": return;
			default: System.out.println("> 잘못 입력하셨습니다.");
				break;
			}
		}
			
		
	}

	/**
	 * 원하는 상품 번호 선택해 아이디 반환
	 * @return productId
	 */
	private String singleoutProduct() {
		List<Product> products = productController.viewAllProduct();
		displayProducts(products);
		System.out.print("> 선택 : ");
		int index = sc.nextInt() - 1;
		String id = products.get(index).getId();
		return id;    
	}

	/**
	 * 새로운 상품정보 입력받기
	 * @return
	 */
	private Product inpuetProduct() {
		System.out.println("----- 상품 정보를 입력해 주세요 -----");
		System.out.print("> 아이디 : ");
		String id = sc.next();
		System.out.print("> 브랜드 : ");
		String brand = sc.next();
		System.out.print("> 이름 : ");
		sc.nextLine();
		String name = sc.nextLine();
		System.out.print("> 가격 : ");
		int price = sc.nextInt();
		System.out.print("> 모니터 사이즈 : ");
		int monitorSize = sc.nextInt();
		System.out.print("> 운영체제 : ");
		String os = sc.next();
		System.out.print("> 저장 용량 : ");
		int storage = sc.nextInt();
		return new Product(id, brand, name, price, monitorSize, os, storage, 0, null);
	}

	/**
	 * 상품 입고 또는 출고 내역 입력받기
	 * @param productId
	 * @param status
	 * @return
	 */
	private ProductIO inputProductIO(String productId, String status) {
		ProductIO productIO = new ProductIO();
		System.out.print(status.equals("I") ? "입고 수량을 입력해주세요 : " : "출고 수량을 입력해주세요 : ");
		int count = sc.nextInt();
		productIO.setCount(count);
		productIO.setProductId(productId);
		productIO.setStatus(status);
		return productIO;
	}

	/**
	 * 상품 정보 수정 메뉴
	 * @param productId
	 */
	private void updateProductMenu(String productId) {
		String menu = "***** 상품정보 변경 메뉴 *****\r\n"
					+ "1.상품명 변경\r\n"
					+ "2.가격 변경\r\n"
					+ "3.사양 변경 \r\n"
					+ "0.메인메뉴로 돌아가기\r\n"
					+ "************************\r\n"
					+ "선택 : ";
		
		while(true) {
			Product product = productController.searchByProductId(productId);
			displayProduct(product);
			
			System.out.print(menu);
			String choice = sc.next();
			sc.nextLine();
			
			int result = 0;
			
			switch(choice) {
			case "1" : // 상품명 변경
				System.out.print("이름을 입력해주세요 : ");
				String newName = sc.nextLine();
				product.setName(newName);
				result = productController.updateProductInfo(productId, product);
				displayResult("상품명", result);
				break;
			case "2" : // 가격 변경
				System.out.print("가격을 입력해주세요 : ");
				int newPrice = sc.nextInt();
				product.setPrice(newPrice);
				result = productController.updateProductInfo(productId, product);
				displayResult("가격", result);
				break;
			case "3" : // 사양 변경 
				System.out.print("모니터 사이즈를 입력해주세요 : ");
				int newMonitorSize = sc.nextInt();
				product.setMonitorSize(newMonitorSize);
				System.out.print("운영체제를 입력해주세요 : ");
				String newOs = sc.next();
				product.setOs(newOs);
				System.out.print("저장 용량을 입력해주세요 : ");
				int newStorage = sc.nextInt();
				product.setStorage(newStorage);
				result = productController.updateProductInfo(productId, product);
				break;
			case "0" : return;
			default : System.out.println("> 잘못 입력하셨습니다.");
			}
		}
		
		
	}
	
	/**
	 * 상품 조회
	 * @param product
	 */
	private void displayProduct(Product product) {
		if(product == null) {
			System.out.println("> 조회된 결과가 없습니다.");
			return;
		}
		
		DecimalFormat formatter = new DecimalFormat("###,###");
		System.out.println("---------------------------------------");
		System.out.printf("번호 : %s\n브랜드 : %s\n제품명 : %s\n가격 : %s원\n사양 : %s인치/%s/%sGB\n재고 : %s\n",
						  product.getId(), product.getBrand(), product.getName(), formatter.format(product.getPrice()),
						  product.getMonitorSize(), product.getOs(),product.getStorage(), product.getStock());
		System.out.println("---------------------------------------");
	}

	/**
	 * 여러 상품 목록
	 * @param products
	 */
	private void displayProducts(List<Product> products) {
		if(products == null || products.isEmpty()) {
			System.out.println("조회된 결과가 없습니다.");
			return;
		}
		
		DecimalFormat formatter = new DecimalFormat("###,###");
		int index = 1;
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("[%s] %-20s%-10s%-15s%-20s%-20s%s\n", "NO", "ID", "BRAND", "NAME", "PRICE", "SPEC", "STOCK");
		System.out.println("-----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.printf("%d. %-20s%-10s%-15s%10s원%10s인치/%s/%sGB%10s\n",
								index++,
								product.getId(),
								product.getBrand(),
								product.getName(),
								formatter.format(product.getPrice()),
								product.getMonitorSize(), product.getOs(), product.getStorage(),
								product.getStock());
		}
		System.out.println("------------------------------------------------------------------------------------");
	}

	/**
	 * 입출고 내역 목록
	 * @param productIOs
	 */
	private void displayProductIOs(List<ProductIO> productIOs) {
		if(productIOs == null) {
			System.out.println("조회된 결과가 없습니다.");
			return;
		}
		
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.printf("%-5s%-15s%-15s%-10s%-10s%-10s%-10s\n", 
						  "NO", "PRODUCTID", "PRODUCTNAME", "BRAND", "COUNT", "I/0", "IODATETIME");
		for (ProductIO productIO : productIOs) {
			System.out.printf("%-5s%-20s%-10s%-10s%-10s%-10s%-10s\n",
							   productIO.getNo(), productIO.getProductId(), productIO.getName(),
							   productIO.getBrand(), productIO.getCount(), productIO.getStatus(), productIO.getIoDatetime());
		}
		System.out.println("---------------------------------------------------------------------------------------");
	}

	/**
	 * 실행 성공 여부
	 * @param type
	 * @param result
	 */
	private void displayResult(String type, int result) {
		System.out.println(type + (result > 0 ? " 성공!" : " 실패!") );
		
	}
	
}
