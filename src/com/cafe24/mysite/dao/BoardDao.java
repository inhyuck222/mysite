package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.mvc.util.DBUtil;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.GuestbookVo;

public class BoardDao {
	
	public List<BoardVo> select(){
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = 
					" select " + 
					"	b.title, " + 
					"	u.name, " + 
					"	u.no, " + 
					"	b.hit, " + 
					"	b.regDate " + 
					" from board b join users u on b.user_no = u.no " + 
					" order by group_no desc, order_no asc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString(1);
				String userName = rs.getString(2);
				Long userNo = rs.getLong(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
								
				BoardVo vo = new BoardVo();
				
				vo.setTitle(title);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && conn.isClosed() == false) {
					conn.close();
				}
				if (pstmt != null && pstmt.isClosed() == false) {
					pstmt.close();
				}
				if (rs != null && rs.isClosed() == false) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public boolean insertNewPost(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"insert " + 
					"into board " + 
					"values( " + 
					"	null, " + 
					"	?, " + 		//title
					"   ?, " + 		//content 
					"   ifnull( (select MAX(group_no) from board max_board), 0) + 1, " + //groupNo 
					"   ?, " + 		//orderNo
					"   ?, " + 		//depth	
					"   now(), " + 	//regDate
					"   ?, " + 		//hit
					"   ? " + 		//userNo
					"	) ";
						
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getOrderNo());
			pstmt.setLong(4, vo.getDepth());
			pstmt.setLong(5, vo.getHit());
			pstmt.setLong(6, vo.getUserNo());			
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && conn.isClosed() == false) {
					conn.close();
				}
				if (pstmt != null && pstmt.isClosed() == false) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
