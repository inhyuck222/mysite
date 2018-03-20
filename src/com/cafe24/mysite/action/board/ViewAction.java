package com.cafe24.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		
		BoardVo board = dao.getBoard(boardNo);
		request.setAttribute("board", board);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
