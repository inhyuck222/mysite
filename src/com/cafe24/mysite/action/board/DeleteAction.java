package com.cafe24.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;

public class DeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		BoardDao dao = new BoardDao();
		Long no = null;
		try {
			no = Long.parseLong(request.getParameter("no"));
		} catch (NumberFormatException | NullPointerException e) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			return;
		}
		
		dao.delete(no);
		
		WebUtil.redirect(request, response, "/mysite/board?a=list");
	}

}
