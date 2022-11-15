package com.sh.member.controller;

import java.sql.Date;
import java.util.List;

import com.sh.member.model.dto.Member;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public List<Member> findAll() {
		return memberService.findAll();
	}

	public Member findById(String id) {
		return memberService.findById(id);
	}

	public List<Member> findByName(String name) {
		return memberService.findByName(name);
	}

	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}

	public int updateMemberName(String id, String newName) {
		return memberService.updateMemberName(id, newName);
	}

	public int updateMemberBirthday(String id, Date newBirthday) {
		return memberService.updateMemberBirthday(id, newBirthday);
	}

	public int updateMemberEmail(String id, String newEmail) {
		return memberService.updateMemberEmail(id, newEmail);
	}

	public int deleteMember(String id) {
		return memberService.deleteMember(id);
	}
	
}
