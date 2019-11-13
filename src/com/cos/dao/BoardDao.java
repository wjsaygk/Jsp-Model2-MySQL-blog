package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.model.Board;
import com.cos.util.DBClose;

public class BoardDao {
	//
	private Connection conn; //
	private PreparedStatement pstmt; //
	private ResultSet rs;//
	// 삭제

	public int delete(int id) {
		final String SQL = "DELETE FROM board WHERE id =?";
		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		return -1;

	}

	// 인기리스트 3건 가져오기
	public List<Board> findOrderByReadCountDesc() {
		final String SQL = "SELECT * FROM board ORDER BY readCount DESC limit 3";
		conn = DBConn.getConnection();
		try {
			List<Board> boards = new ArrayList<Board>();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setUserId(rs.getInt("userId"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreateDate(rs.getTimestamp("createDate"));
				board.setReadCount(rs.getInt("readCount"));

				boards.add(board);
			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	// 조회수 증가
	public int increaseReadCount(int id) {
		final String SQL = "UPDATE board SET readCount = readCount+1 WHERE id = ?";
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate(); // 변경된 튜플의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}

	public int save(Board board) {
		final String SQL = "insert into board(userId,title,content,createDate) values(?,?,?,now())";
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, board.getUserId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			int result = pstmt.executeUpdate(); // 蹂�寃쎈맂 �뒠�뵆�쓽 媛쒖닔瑜� 由ы꽩
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}

	// 由ъ뒪�듃蹂닿린
	public List<Board> findAll() {
		final String SQL = "SELECT * FROM board ORDER BY id DESC";
		conn = DBConn.getConnection();
		try {
			List<Board> boards = new ArrayList<Board>();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setUserId(rs.getInt("userId"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreateDate(rs.getTimestamp("createDate"));
				board.setReadCount(rs.getInt("readCount"));

				boards.add(board);
			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	public List<Board> findAll(int page) {
		final String SQL = "select * from board b, user u  where b.userId = u.id order by b.id desc limit ?, 3;";
		conn = DBConn.getConnection();
		try {
			List<Board> boards = new ArrayList<Board>();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, (page - 1) * 3);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content") + " ");
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				board.setReadCount(rs.getInt("b.readCount"));
				// (2) board에 user 객체에 username 저장(추후 : userprofile에 저장)
				board.getUser().setUsername(rs.getString("u.username"));
				/* board.getUser().setUsername(rs.getString("u.userProfile")); */
				boards.add(board);
			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	public List<Board> findAll(int page, String search){
		//1 조인 쿼리로 변경
				
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM board b, user u ");
		sb.append("WHERE b.userId = u.id and ");
		sb.append("(b.content like ? or b.title like ?) ");
		sb.append("ORDER BY b.id DESC limit ?, 3");
				
		conn = DBConn.getConnection();
		final String SQL = sb.toString();
		
		try {
			List<Board> boards = new ArrayList<Board>();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, (page-1)*3);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content")+" ");
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				board.setReadCount(rs.getInt("b.readCount"));				
				//(2) board에 user 객체에 username 저장(추후 : userprofile에 저장)
				board.getUser().setUsername(rs.getString("u.username"));
				/* board.getUser().setUsername(rs.getString("u.userProfile")); */
				boards.add(board);				
			}
			return boards;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}

	// �긽�꽭蹂닿린
	public Board findById(int id) {
		final String SQL = "select * from board b, user u where b.userId = u.id and b.id = ?";
		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				board.setReadCount(rs.getInt("b.readCount"));
				board.getUser().setUsername(rs.getString("u.username"));
				board.getUser().setUserProfile(rs.getString("u.userProfile"));

				return board;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		return null;
	}

	public int Update(Board board) {
		final String SQL = "UPDATE board SET title = ?, content = ?, createDate=now() WHERE id = ?";
		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
			int result = pstmt.executeUpdate();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		return -1;

	}
}
