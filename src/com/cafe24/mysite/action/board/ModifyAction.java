package com.cafe24.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.forward(request, response, "/WEB-INF/views/users/loginform.jsp");
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			WebUtil.forward(request, response, "/WEB-INF/views/users/loginform.jsp");
			return;
		}
		
		BoardVo boardVo = new BoardVo();
		String newContent = request.getParameter("content");
		String newTitle = request.getParameter("title");
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		
		boardVo.setContent(newContent);
		boardVo.setTitle(newTitle);
		boardVo.setNo(boardNo);
		
		BoardDao dao = new BoardDao();
		dao.update(boardVo);
		
		WebUtil.redirect(request, response, "/mysite/board?a=view&boardNo=" + boardVo.getNo());
	}

}
