package com.sh.member.model.dao;

import static com.sh.member.common.JdbcTemplate.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.sh.member.model.dto.Member;


public class MemberDao {

	/**
	 * ▶ 전체 회원 정보 조회
	 * @param conn
	 * @return
	 */
	public List<Member> findAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member";
		List<Member> members = new ArrayList<>();
		
		try {
			// 1. PreparedStatement 생성 
			pstmt = conn.prepareStatement(sql);
			// 2. 실행
			rset = pstmt.executeQuery();
			// 3. ResultSet -> List<Member>
			while(rset.next()) {
				members.add(new Member(
						rset.getString("id"), 
						rset.getString("name"), 
						rset.getString("gender"), 
						rset.getDate("birthday"), 
						rset.getString("email"), 
						rset.getInt("point"), 
						rset.getTimestamp("reg_date")));
			}
			
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			// 4. 자원반납
			close(rset);
			close(pstmt);
		}
		
		return members;
	}

	/**
	 * ▶ 입력받은 아이디의 회원 정보 조회
	 * @param conn
	 * @param id
	 * @return
	 */
	public Member findById(Connection conn, String id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where id = ?";
		Member member = null;
		
		try {
			// 1. PreparedStatement 생성 - 미완성쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 2. 실행 - ResultSet
			rset = pstmt.executeQuery();
			// 3. ResultSet -> Member
			while(rset.next()) {
				member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("id"));
				member.setPoint(rset.getInt("point"));
				member.setRegDate(rset.getTimestamp("reg_date"));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 4. 자원 반납
			close(rset);
			close(pstmt);
		}
		
		return member;
	}
	
	/**
	 * ▶ 입력받은 이름의 회원 정보 조회
	 * @param conn
	 * @param name
	 * @return
	 */
	public List<Member> findByName(Connection conn, String name) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where name like ?";
		List<Member> members = new ArrayList<>();
		
		try {
			// 1. PreparedStatement 생성 - 미완성쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			// 2. 실행 - ResultSet 
			rset = pstmt.executeQuery();
			// 3. Result -> List<Member>
			while(rset.next()) {
				members.add(new Member(
						rset.getString("id"), 
						rset.getString("name"), 
						rset.getString("gender"), 
						rset.getDate("birthday"), 
						rset.getString("email"), 
						rset.getInt("point"), 
						rset.getTimestamp("reg_date")));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 4. 자원반납 (pstmt, rset)
			close(rset);
			close(conn);
		}
		
		return members;
	}

	/**
	 * ▶ 회원 정보 입력
	 * @param conn
	 * @param member
	 * @return
	 */
	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		
		String sql = "insert into member values (?, ?, ?, ?, ?, default, default)";
		int result = 0;
		
		try {
			// 1. PreparedStatement 생성 - 미완성 쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			
			// 2. 실행
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e); // checked -> unchecked
		} finally {
			// 3. 자원 반납(pstmt, rset)
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (이름)
	 * @param newName
	 * @return
	 */
	public int updateMemberName(Connection conn, String id, String newName) {
		PreparedStatement pstmt = null;
		String sql = "update member set name = ? where id = ?";
		int result = 0;
		
		try {
			// 1. PreparedStatement 생성 - 미완성쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setString(2, id);
			// 2. 실행 
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 3 . 자원반납
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (생일)
	 * @param conn
	 * @param id
	 * @param newBirthday
	 * @return
	 */
	public int updateMemberBirthday(Connection conn, String id, Date newBirthday) {
		PreparedStatement pstmt = null;
		String sql = "update member set birthday = ? where id =?";
		int result = 0;
		
		try {
			// 1. PreparedStatement
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, newBirthday);
			pstmt.setString(2, id);
			// 2. 실행 
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 3. 자원반납
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (이메일)
	 * @param conn
	 * @param id
	 * @param newEmail
	 * @return
	 */
	public int updateMemberEmail(Connection conn, String id, String newEmail) {
		PreparedStatement pstmt = null;
		String sql = "update member set email = ? where id = ?";
		int result = 0;
		
		try {
			// 1. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newEmail);
			pstmt.setString(2, id);
			// 2. 실행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * ▶ 회원 정보 삭제
	 * @param conn
	 * @param id
	 * @return
	 */
	public int deleteMember(Connection conn, String id) {
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ?";
		int result = 0;
		
		try {
			// 1. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 2. 실행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		
		return result;
	}

}
