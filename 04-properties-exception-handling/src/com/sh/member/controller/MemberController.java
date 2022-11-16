package com.sh.member.controller;

import java.util.List;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;
import com.sh.member.model.service.MemberService;

public class MemberController {

	private MemberService memberService = new MemberService();

	/**
	 * 회원 전체 조회
	 * @return
	 */
	public List<Member> findAll() {
		List<Member> members = null;
		try {
			members = memberService.findAll();
		} catch (Exception e) {
			// 1. 예외로그 
			e.printStackTrace();
			// 2. 사용자에게 적절한 메세지 전달
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}
	
	/**
	 * 아이디 조회
	 * @param id
	 * @return
	 */
	public Member findById(String id) {
		Member member = null;
		try {
			member = memberService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return member;
	}

	/**
	 * 이름 검색
	 * @param name
	 * @return
	 */
	public List<Member> findByName(String name) {
		List<Member> members = null;
		try {
			members = memberService.findByName(name);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}

	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}
	
	/**
	 * 회원 정보 수정
	 * @param id
	 * @param colName
	 * @param newValue
	 * @return
	 */
	public int updateMemberInfo(String id, String colName, Object newValue) {
		int result = 0;
		try {
			result = memberService.updateMemberInfo(id, colName, newValue);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	/**
	 * 회원 탈퇴
	 * @param id
	 * @return
	 */
	public int deleteMember(String id) {
		int result = 0;
		try {
			result = memberService.deleteMember(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	/**
	 * 탈퇴한 회원 조회
	 * @return
	 */
	public List<MemberDel> findAllMemberDel() {
		List<MemberDel> members = null;
		
		try {
			members = memberService.findAllMemberDel();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}

	/**
	 * 중복 확인
	 * @param colName
	 * @param value
	 * @return
	 */
	public Member checkForDuplicates(String colName, String value) {
		Member member = null;
		try {
			member = memberService.checkForDuplicates(colName, value);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return member;
	}
}
