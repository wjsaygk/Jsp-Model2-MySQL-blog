package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.model.User;
import com.cos.util.DBClose;
import com.sun.crypto.provider.RSACipher;

public class UserDao {
	//싱글톤으로 만들어야 하는데 그냥 함.
	private Connection conn; //MySQL과 연결하기 위해 필요
	private PreparedStatement pstmt; //쿼리문을 작성 -실행하기 위해 필요
	private ResultSet cursor;//결과를 보관할 커서

	
	public int profileUpdate(User user, String path) {
		final String SQL= "UPDATE user SET userProfile = ? WHERE id = ?"; 
		conn=DBConn.getConnection();
		
		try {
			pstmt=conn.prepareStatement(SQL);
			
			pstmt.setString(1, path);
			pstmt.setInt(2, user.getId());
			int result=pstmt.executeUpdate(); //변경된 튜플의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt);
		}
		
		
		return -1;
	}
	
	
	public User findByUsername(String username) {
		final String SQL = "SELECT * FROM user Where username=?";
		
		conn=DBConn.getConnection();
		try {
		pstmt=conn.prepareStatement(SQL);
		pstmt.setString(1, username);
		cursor=pstmt.executeQuery();
		if(cursor.next()) {
			User user = new User();
			user.setId(cursor.getInt("id"));
			user.setUsername(cursor.getString("username"));
			user.setEmail(cursor.getString("email"));
			user.setCreateDate(cursor.getTimestamp("createDate"));
			user.setAddress(cursor.getString("address"));
			user.setUserProfile(cursor.getString("userProfile"));
			return user;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt, cursor);
		}
		
		return null;
	}
	
	public int update(User user) {
		final String SQL= "UPDATE user SET password = ?, address = ? WHERE id = ?"; 
		conn=DBConn.getConnection();
		
		try {
			pstmt=conn.prepareStatement(SQL);
			
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getAddress());
			pstmt.setInt(3, user.getId());
			int result=pstmt.executeUpdate(); //변경된 튜플의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt);
		}
		
		
		return -1;
	}
	
	
	public int save(User user) {
		final String SQL="insert into user(username,password,email, address, createDate) values(?, ?, ?, ?,now())";
		conn=DBConn.getConnection();
		
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			int result=pstmt.executeUpdate(); //변경된 튜플의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt);
		}
		
		
		return -1;
	}
	
	//로그인 인증 성공 :1 , DB오류 : -1 , 미인증 :0
	public int findByUsernameAndPassword(String username , String password) {
		final String SQL = "select count(id) from user where username=? and password=?";
		conn=DBConn.getConnection();
		
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			cursor=pstmt.executeQuery();
			
			if(cursor.next()) {
				int result = cursor.getInt(1);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DBClose.close(conn, pstmt, cursor);
		}
		return -1;
	}

}
