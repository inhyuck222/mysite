package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.mvc.util.DBUtil;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

public class BoardDao {
	
	public boolean delete(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = "update board set is_deleted = 1 where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
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
	
	public Long getTotalCount(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long totalCount = 0L;
		
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" select " +
					" 	count(*) " +
					" from board " + 
					" where content LIKE ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getLong(1);
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
		
		return totalCount;
	}
	
	public boolean respostUpdate(Long pGroupNo, Long pOrderNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = "update board set order_no = order_no + 1 where group_no = ? and order_no >= ? + 1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, pGroupNo);
			pstmt.setLong(2, pOrderNo);
			
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

	/*
	 * g_no(2) = 유지 => 2
	 * o_no(1) = o_no(1) + 1 => 2
	 * d(0) = d + 1 => 1
	 * update o_no = o_no+1 where g_no = 2 and o_no >= 2
	 * insert (~~~~ d(d(0) + 1) ~~~~~~)
	 */
	public boolean insertRepost(Long pGroupNo, Long pOrderNo, Long pDepth, UserVo writerVo, BoardVo reBoard) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		respostUpdate(pGroupNo, pOrderNo);
				
		try {
			conn = DBUtil.getConnection();
						
			String sql = 
					"insert " + 
					"into board " + 
					"values( " + 
					"	null, " + 
					"    ?, " + 			//title
					"    ?, " + 			//content 
					"    ?, " + 			//groupNo 
					"    ?, " + 			//orderNo
					"    ?, " + 			//depth	
					"    now(), " + 		//regDate
					"    0, " + 			//hit
					"    ?," +				//userNo 
					"	 ?)";				//isDeleted
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reBoard.getTitle());
			pstmt.setString(2, reBoard.getContent());
			pstmt.setLong(3, pGroupNo);
			pstmt.setLong(4, pOrderNo + 1);
			pstmt.setLong(5, pDepth + 1);
			pstmt.setLong(6, writerVo.getNo());
			pstmt.setBoolean(7, reBoard.isDeleted());
			
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
	
	public boolean update(BoardVo newVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"update board set title=?, content=? where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newVo.getTitle());
			pstmt.setString(2, newVo.getContent());
			pstmt.setLong(3, newVo.getNo());
			
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
	
	public boolean updateHit(Long boardNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"update board set hit = hit + 1 where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, boardNo);
			
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
	
	public BoardVo getBoard(Long boardNo) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = 
					" select " +
					" 	no, " + 
					"	title, " + 
					"	content, " + 
					"	group_no, " + 
					"	order_no, " + 
					"	depth, " + 
					"	regDate, " + 
					"	hit, " + 
					"	user_no " + 
					" from board " + 
					" where no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setGroupNo(rs.getLong(4));
				vo.setOrderNo(rs.getLong(5));
				vo.setDepth(rs.getLong(6));
				vo.setRegDate(rs.getString(7));
				vo.setHit(rs.getLong(8));
				vo.setUserNo(rs.getLong(9));
			}
			
			updateHit(boardNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;		
	}
	
	public List<BoardVo> select(Long recentPage, Long limitCount, String keyword){
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
			
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" select " + 
					"	b.no, " + 
					"	b.title, " + 
					"	u.name, " + 
					"	u.no, " + 
					"	b.hit, " + 
					"	b.regDate, " + 
					"	b.depth, " +
					"	b.is_deleted " +
					" from board b join users u on b.user_no = u.no " +
					" where content LIKE ? " + 
					" order by group_no desc, order_no asc " + 
					" LIMIT ?, ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			pstmt.setLong(2, (recentPage - 1) * 10);
			pstmt.setLong(3, limitCount);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String userName = rs.getString(3);
				Long userNo = rs.getLong(4);
				Long hit = rs.getLong(5);
				String regDate = rs.getString(6);
				Long depth = rs.getLong(7);
				Boolean deleted = rs.getBoolean(8);
				
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setDeleted(deleted);
				
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
					"   ?, " + 		//userNo
					"   ? " + 		//isdeleted
					"	) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getOrderNo());
			pstmt.setLong(4, vo.getDepth());
			pstmt.setLong(5, vo.getHit());
			pstmt.setLong(6, vo.getUserNo());	
			pstmt.setBoolean(7, vo.isDeleted());
			
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
}
