package com.sh.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;
import com.sh.member.model.exception.MemberException;
import static com.sh.member.common.JdbcTemplate.*;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.load(new FileReader("resources/member-sql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 회원 전체 조회
	 * @param conn
	 * @return
	 */
	public List<Member> findAll(Connection conn) {
		String sql = prop.getProperty("findAll");
		List<Member> members = new ArrayList<>();
		
		// 1. PreparedStatement 생성 - 미완선쿼리 & 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
		// 2. 실행 - ResultSet
			try (ResultSet rset = pstmt.executeQuery()){
				// 3. ResultSet -> List<Member>
				while(rset.next()) {
					members.add(handleMemberResultSet(rset));
				}
			}
			
		} catch (Exception e) {
			throw new MemberException("회원 전체조회 오류!", e); // 호출부한테 던짐(서비스)
		}
			
		return members;
	}

	/**
	 * 회원 정보 가져오기
	 * @param rset
	 * @return
	 * @throws SQLException
	 */
	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBirthday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setPoint(rset.getInt("point"));
		member.setRegDate(rset.getTimestamp("reg_date"));
		return member;
	}

	/**
	 * 아이디 검색
	 * @param conn
	 * @param id
	 * @return
	 */
	public Member findById(Connection conn, String id) {
		String sql = prop.getProperty("findById");
		Member member = null;
		
		// 1. PreparedStatement 생성 
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			// 2. 실행 - ResultSet
			try (ResultSet rset = pstmt.executeQuery()) {
				// 3. rset -> member
				while(rset.next()) {
					member = handleMemberResultSet(rset);
				}
			} 
			
		} catch (SQLException e) {
			throw new MemberException("아이디 조회 오류!");
		}
		return member;
	}

	/**
	 * 이름 검색
	 * @param conn
	 * @param name
	 * @return
	 */
	public List<Member> findByName(Connection conn, String name) {
		String sql = prop.getProperty("findByName");
		List<Member> members = new ArrayList<>();
		
		// 1. PreparedStatement 생성
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");
			// 2. 실행 - ResultSet
			try (ResultSet rset = pstmt.executeQuery()) {
				// 3. ResultSet -> List<Member>
				while(rset.next()) {
					Member member = handleMemberResultSet(rset);
					members.add(member);
				}
			}
			
		} catch (SQLException e) {
			throw new MemberException("이름 조회 오류!");
		}
		return members;
	}

	/**
	 * 회원 가입
	 * @param conn
	 * @param member
	 * @return
	 */
	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		
		// 1. PreparedStatement - 미완성쿼리 & 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			
			// 2. 실행 - int
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new MemberException("회원가입오류!", e);
		}
		return result;
	}

	/**
	 * 회원 정보 수정
	 * @param conn
	 * @param id
	 * @param colName
	 * @param newValue
	 * @return
	 */
	public int updateMemberInfo(Connection conn, String id, String colName, Object newValue) {
		String sql = prop.getProperty("updateMemberInfo");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			sql = sql.replace("#", colName);
			// 1. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, newValue);
			pstmt.setString(2, id);
			
			// 2. 실행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException(colName + " 변경 오류!");
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		return result;
	}

	/**
	 * 회원 탈퇴
	 * @param conn
	 * @param id
	 * @return
	 */
	public int deleteMember(Connection conn, String id) {
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		
		// 1. PreparedStatement 생성
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			
			// 2. 실행 
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new MemberException("회원삭제오류!");
		}
		return result;
	}

	/**
	 * 탈퇴 회원 조회
	 * @param conn
	 * @return
	 */
	public List<MemberDel> findAllMemberDel(Connection conn) {
		String sql = prop.getProperty("findAllMemberDel");
		List<MemberDel> members = new ArrayList<>();
		
		// 1. pstmt - ? 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			// 2. 실행 - rset 반환
			try (ResultSet rset = pstmt.executeQuery()) {
				// 3. rset - > list
				while(rset.next()) {
					MemberDel member = new MemberDel(handleMemberResultSet(rset));
					member.setDelDate(rset.getTimestamp("del_date"));
					members.add(member);
				}
			}
		} catch (SQLException e) {
			throw new MemberException("탈퇴회원 조회 오류!");
		}
		
		return members;
	}

	/**
	 * 중복 검사
	 * @param conn
	 * @param colName
	 * @param value
	 * @return
	 */
	public Member checkForDuplicates(Connection conn, String colName, String value) {
		String sql = prop.getProperty("checkForDuplicates");
		PreparedStatement pstmt = null;
		Member member = null;

		try {
			sql = sql.replace("#", colName); // 연결하기 전에 대입해야 함
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					member = handleMemberResultSet(rset);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MemberException("중복 검사 오류!");
		} finally {
			close(pstmt);
		} 		
		
		return member;
	}

}
