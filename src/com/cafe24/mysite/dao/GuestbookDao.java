package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.mvc.util.DBUtil;
import com.cafe24.mysite.vo.GuestbookVo;

public class GuestbookDao {
	
	public boolean delete(GuestbookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" delete " + 
					" from guestbook " + 
					" where no = ? " + 
					"	and password = password(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insert(GuestbookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" insert " + 
					"	into guestbook " + 
					" values (null, ?, password(?), ?, now())";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<GuestbookVo> getList(){
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" select no, " + 
					"	name, " + 
					"    password, " + 
					"    content, " + 
					"    reg_date " + 
					" from guestbook " + 
					" order by reg_date desc";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContent(content);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
