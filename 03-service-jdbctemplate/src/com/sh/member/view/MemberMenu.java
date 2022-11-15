package com.sh.member.view;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


import com.sh.member.controller.MemberController;
import com.sh.member.model.dto.Member;

public class MemberMenu {
	
	private MemberController memberController = new MemberController();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * ▶ 메인 메뉴
	 */
	public void mainMenu() {
		String menu = "===== 회원관리프로그램 =====\n"
				+ "1. 회원 전체 조회\n"
				+ "2. 아이디 조회\n"
				+ "3. 이름 검색\n"
				+ "4. 회원 가입\n"
				+ "5. 회원 정보 수정\n"
				+ "6. 회원 탈퇴\n"
				+ "7. 전체 회원수 조회\n"
				+ "0. 프로그램 종료\n"
				+ "=======================\n"
				+ "선택 : ";
		
		do {
			System.out.print(menu);
			String choice = sc.next();
			
			Member member = null;
			int result = 0;
			String name = null;
			String id = null;
			List<Member> members = null;
			
			switch (choice) {
			case "1": // 회원 전체 조회
				members = memberController.findAll();
				displayMembers(members);
				break;
			case "2": // 아이디 조회
				id = inputId();
				member = memberController.findById(id);
				displayMember(member);
				break;
			case "3": // 이름 검색
				name = inputName();
				members = memberController.findByName(name);
				displayMembers(members);
				break;
			case "4": // 회원 가입
				member = inputMember();
				System.out.println("> 입력정보 확인 : " + member);
				result = memberController.insertMember(member);
				displayResult("> 회원가입 ", result);
				break;
			case "5": // 회원 정보 수정 
				id = inputId();
				updateMenu(id);
				break;
			case "6": // 회원 탈퇴
				id = inputId();
				result = memberController.deleteMember(id);
				if(result == 0) {
					System.err.println("> 회원정보를 찾을 수 없습니다.");
				}
				displayResult("> 회원 탈퇴 ", result);
				break;
			case "0": return;
			default: System.out.println("> 잘못 입력하셨습니다.");
			}
			
		}while(true);
		
	}

	/**
	 * ▶ 회원 정보 수정 메뉴
	 * @param id
	 */
	private void updateMenu(String id) {
		String survMenu = "===== 회원정보변경매뉴 =====\n" 
				+ "1. 이름 변경\n" 
				+ "2. 생일 변경\n" 
				+ "3. 이메일 변경\n" 
				+ "0. 메인메뉴 돌아가기\n"
				+ "=======================\n"
				+ "선택 : ";
		
		do {
			Member member = memberController.findById(id);
			displayMember(member);
			
			System.out.print(survMenu);
			String choice = sc.next();
			int result = 0;
			
			switch (choice) {
			case "1": // 이름 변경
				System.out.print("> 변경할 이름을 입력해 주세요 : ");
				String newName = sc.next();
				result = memberController.updateMemberName(id, newName);
				displayResult("> 이름 변경 ", result);
				break;
			case "2": // 생일 변경
				System.out.print("> 변경할 날짜를 입력해 주세요(19990909) : ");
				LocalDate birthday = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("yyyyMMdd"));
				Date newBirthday = Date.valueOf(birthday);
				result = memberController.updateMemberBirthday(id, newBirthday);
				displayResult("> 생일 변경 ", result);
				break;
			case "3": // 이메일 변경
				System.out.print("> 변경할 이메일을 입력해 주세요 : ");
				String newEmail = sc.next();
				result = memberController.updateMemberEmail(id, newEmail);
				displayResult("> 이메일 변경 ", result);
				break;
			case "0": // 메인메뉴로 돌아가기
				return;
			default: System.err.println("> 잘못 입력하셨습니다.");
			} 
		} while (true);
		
	}

	/**
	 * ▶ 아이디 검색
	 * @return
	 */
	private String inputId() {
		while(true) {
			System.out.print("> 아이디를 입력해주세요 : ");
			String id = sc.next();
			System.out.print("찾으시는 아이디가 " + id + " 맞습니까?(y|n) : ");
			String yn = sc.next();
			if(yn.equals("y"))
				return id;
		}
	}

	/**
	 * ▶ 이름 검색
	 * @return
	 */
	private String inputName() {
		while(true) {
			System.out.print("> 이름을 입력해주세요 : ");
			String id = sc.next();
			System.out.print("찾으시는 이름이 " + id + " 맞습니까?(y|n) : ");
			String yn = sc.next();
			if(yn.equals("y"))
				return id;
		}
	}

	/**
	 * ▶ 회원가입 정보 입력받기
	 * @return
	 */
	private Member inputMember() {
		System.out.println("> 새 회원정보를 입력하세요.");
		System.out.print("> 아이디 : ");
		String id = sc.next();
		System.out.print("> 이름 : ");
		String name = sc.next();
		System.out.print("> 성별(M/F) : ");
		String gender = sc.next();
		System.out.print("> 생일(19990909) : ");
		LocalDate _birthday = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("yyyyMMdd"));
		Date birthday = Date.valueOf(_birthday);
		System.out.print("> 이메일 : ");
		String email = sc.next();
		return new Member(id, name, gender, birthday, email, 0, null);
	}

	/**
	 * ▶ 두 명 이상 회원 조회
	 * @param members
	 */
	private void displayMembers(List<Member> members) {
		if(members.isEmpty() || members == null) {
			// 조회결과가 없는 경우
			System.out.println("> 조회된 결과가 없습니다.");
		}
		else {
			// 조회결과가 있는 경우
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s\n", 
							  "id", "name", "gender", "birthday", "email", "point", "regDate");
			System.out.println("----------------------------------------------------------------------------------------------");
			for(Member member : members) {
				System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s\n", 
						  		member.getId(), 
						  		member.getName(), 
						  		member.getGender(), 
						  		member.getBirthday(), 
						  		member.getEmail(), 
						  		member.getPoint(), 
						  		member.getRegDate());
			}
			System.out.println("----------------------------------------------------------------------------------------------");
		}	
	}

	/**
	 * ▶ 한 명 회원 조회
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 결과가 없습니다.");
		}
		else {
			System.out.println("-------------------------------");
			System.out.println("ID 		: " + member.getId());
			System.out.println("NAME 		: " + member.getName());
			System.out.println("GENDER 		: " + member.getGender());
			System.out.println("BIRTHDAY 	: " + member.getBirthday());
			System.out.println("EMAIL 		: " + member.getEmail());
			System.out.println("POINT 		: " + member.getPoint());
			System.out.println("REGDATE 	: " + member.getRegDate());
			System.out.println("-------------------------------");
		}
	}

	/**
	 * ▶ 실행 성공 여부 확인
	 * @param mode
	 * @param result
	 */
	private void displayResult(String mode, int result) {
		System.out.printf("%S %S\n", mode, result > 0 ? "성공!" : "실패!");
	}

}
