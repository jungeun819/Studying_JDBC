package com.sh.member.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class MemberDel extends Member {
	
	private Timestamp delDate;
	
	// 생성자
	public MemberDel() {
		super();
	}

	public MemberDel(String id, String name, String gender, Date birthday, String email, int point, Timestamp regDate,
			Timestamp delDate) {
		super(id, name, gender, birthday, email, point, regDate);
		this.delDate = delDate;
	}

	// getter/setter
	public Timestamp getDelDate() {
		return delDate;
	}

	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}

	// toString
	@Override
	public String toString() {
		return "MemberDel [delDate=" + delDate + ", toString()=" + super.toString() + "]";
	}
	
	


	
	
	
}
