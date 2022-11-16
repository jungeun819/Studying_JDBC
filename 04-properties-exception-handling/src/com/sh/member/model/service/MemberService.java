package com.sh.member.model.service;

import static com.sh.member.common.JdbcTemplate.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();

	/**
	 * 회원 전체 조회
	 * @return
	 */
	public List<Member> findAll() {
		List<Member> members = null;
		try(Connection conn = getConnection()){
			members = memberDao.findAll(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			throw e;
		}
		return members;
	}

	public Member findById(String id) {
		Member member = null;
		Connection conn = getConnection();
		member = memberDao.findById(conn, id);
		close(conn);
		return member;
	}

	/**
	 * 이름 검색
	 * @param name
	 * @return
	 */
	public List<Member> findByName(String name) {
		Connection conn = getConnection();
		List<Member> members = memberDao.findByName(conn, name);
		close(conn);
		return members;
	}

	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public int insertMember(Member member) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.insertMember(conn, member);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
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
		Connection conn = getConnection();
		int result = memberDao.updateMemberInfo(conn, id, colName, newValue);
		close(conn);
		return result;
	}

	/**
	 * 회원 탈퇴
	 * @param id
	 * @return
	 */
	public int deleteMember(String id) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.deleteMember(conn, id);
			commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	/**
	 * 탈퇴 회원 조회
	 * @return
	 */
	public List<MemberDel> findAllMemberDel() {
		Connection conn = getConnection();
		List<MemberDel> members = memberDao.findAllMemberDel(conn);
		close(conn);
		return members;
	}

	/**
	 * 중복 확인
	 * @param colName
	 * @param value
	 * @return
	 */
	public Member checkForDuplicates(String colName, String value) {
		Connection conn = getConnection();
		Member member = memberDao.checkForDuplicates(conn, colName, value);
		close(conn);
		return member;
	}
}
