package com.cafe24.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.UserDao;
import com.cafe24.mysite.vo.UserVo;

public class ModifyActiion implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String newPassword = request.getParameter("newpassword");
		String gender = request.getParameter("gender");
		String originPassword = request.getParameter("originpassword");
		boolean result = false;
		
		/* 인증처리 (Session 처리) */
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 유저가 있는지 확인
		UserVo userCheckVo = new UserDao().get(authUser.getEmail(), originPassword);
		if (userCheckVo == null) {
			request.setAttribute("name", name);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
			return;
		}
		
		// 비어있는 String 기존 정보로 처리
		if("".equals(name)) {
			name = authUser.getName();	
		}
		if("".equals(newPassword)) {
			newPassword = authUser.getPassword();
		}
		
		// 수정된 내용 적용
		authUser.setName(name);
		authUser.setPassword(newPassword);
		authUser.setGender(gender);
		
		result = new UserDao().update(authUser);
		
		if(result) {
			WebUtil.redirect(request, response, "/mysite/main");	
		}		
	}

}
