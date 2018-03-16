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

public class NewPostWriteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null) {
			System.out.println("NewPostWriteAction.excute() : session is null");
			WebUtil.redirect(request, response, "/mysite/user?a=loginform");
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			System.out.println("NewPostWriteAction.excute() : authUser is null");
			WebUtil.redirect(request, response, "/mysite/user?a=loginform");
			return;
		}
		
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Long userNo = authUser.getNo();
		
		vo.setTitle(title);
		vo.setContent(content);		
		vo.setOrderNo(1L);
		vo.setDepth(0L);
		vo.setUserNo(userNo);
		vo.setHit(0L);
		
		dao.insertNewPost(vo);
		
		WebUtil.redirect(request, response, "/mysite/board");
	}
}
