package com.cafe24.mysite.action.gusetbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = new GuestbookVo();
		
		vo.setName(request.getParameter("name"));
		vo.setPassword(request.getParameter("pass"));
		vo.setContent(request.getParameter("content"));
		
		dao.insert(vo);
		
		WebUtil.redirect(request, response, "/mysite/guestbook");
	}

}
