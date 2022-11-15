package com.sh.member.model.service;

import static com.sh.member.common.JdbcTemplate.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;

/**
 * 원래 DAO 하던일
 * 1. 클래스등록
 * 2. Connection 생성
 * 3. PreparedStatement 생성 - 미완성쿼리 & 값대입
 * 4. 실행
 * 5. 트랜잭션처리
 * 6. 자원 반납(conn, pstmt)
 * 
 * 이제는 나눠서!
 * 
 * Service
 * 1. 클래스등록
 * 2. Connection 생성
 * 3. Dao 위임
 * 4. 트랜잭션처리
 * 5. 자원반납(conn)
 * 
 * Dao
 * 1. PreparedStatement 생성 - 미완성쿼리&값대입
 * 2. 실행
 * 3. 자원반납(pstmt, rset)
 */
public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	/**
	 * ▶ 전체 회원 조회
	 * @return
	 */
	public List<Member> findAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findAll(conn);
		close(conn);
		return members;
	}

	/**
	 * ▶ 입력받은 아이디의 회원 정보 조회
	 * @param id
	 * @return
	 */
	public Member findById(String id) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn, id);
		close(conn);
		return member;
	}

	/**
	 * 1. Connection 생성
	 * 2. PreparedStatement 생성 - 미완성쿼리 & 값대입
	 * 3. 실행 - ResultSet 
	 * 4. Result -> List<Member>
	 * 5. 자원반납
	 * 
	 * service
	 * 1. Connection 생성
	 * 2. Dao 위임 
	 * 3. 자원반납(conn)
	 * 
	 * dao
	 * 1. PreparedStatement 생성 - 미완성쿼리 & 값대입
	 * 2. 실행 - ResultSet 
	 * 3. Result -> List<Member>
	 * 4. 자원반납 (pstmt, rset)
	 * 
	 * ▶ 입력받은 이름의 회원 정보 조회
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
	 * ▶ 회원 정보 입력
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
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}
	
	/**
	 * ▷ 회원 정보 입력
	 * @param member
	 * @return
	 */
	public int _insertMember(Member member) {
		Connection conn = null;
		int result = 0;
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. Dao 위임
			result = memberDao.insertMember(conn, member);
			// 4. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. 자원반납
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (이름)
	 * @param id
	 * @param newName
	 * @return
	 */
	public int updateMemberName(String id, String newName) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		try {
			// 2. Dao 위임
			result = memberDao.updateMemberName(conn, id, newName);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			// 4. 자원반납
			close(conn);
		}
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (생일)
	 * @param id
	 * @param newBirthday
	 * @return
	 */
	public int updateMemberBirthday(String id, Date newBirthday) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		try {
			// 2. Dao 위임
			result = memberDao.updateMemberBirthday(conn, id, newBirthday);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			// 4. 자원 반납
			close(conn);
		}
		return result;
	}

	/**
	 * ▶ 회원 정보 수정 (이메일)
	 * @param id
	 * @param newEmail
	 * @return
	 */
	public int updateMemberEmail(String id, String newEmail) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		try {
			// 2. Dao 위임
			result = memberDao.updateMemberEmail(conn, id, newEmail);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			// 4. 자원 반납
			close(conn);
		}
		return result;
	}

	/**
	 * ▶ 회원 정보 삭제
	 * @param id
	 * @return
	 */
	public int deleteMember(String id) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		try {
			// 2. Dao 위임
			result = memberDao.deleteMember(conn, id);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			// 4. 자원 반납
			close(conn);
		}
		
		return result;
	}

}
