package com.cafe24.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

public class ListAction implements Action {
	private static final Long LIMIT_COUNT = 10L;
	private static final int PAGE_LENGTH = 3;

	// jsp에서 페이지를 파라미터로 넘겨줘야됨
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		String page = request.getParameter("page");
		String keyword = request.getParameter("kwd");
				
		Long recentPage = null;
		try {
			recentPage = Long.parseLong(page);
		} catch (NumberFormatException | NullPointerException e) {
			recentPage = Long.parseLong("1");
		}
		
		
		if(recentPage < 1) {
			recentPage = 1L;
		}

		Long totalCount = dao.getTotalCount(keyword);
		if(totalCount == 0) {
			request.setAttribute("totalCount", totalCount);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			return;
		}
		
		int endPage = 0;
		
		if((int) (totalCount % LIMIT_COUNT) == 0) {
			endPage = (int) (totalCount / LIMIT_COUNT);
		} else {
			endPage = (int) (totalCount / LIMIT_COUNT) + 1;
		}
		
		if(recentPage > endPage) {
			recentPage = (long)endPage;
		}		
		
		List<BoardVo> list = dao.select(recentPage, LIMIT_COUNT, keyword);
		
		
		int[] pages = new int[PAGE_LENGTH];
		
		if (recentPage == 1 || recentPage == 2) {
			for (int i = 0; i < PAGE_LENGTH; i++) {
				pages[i] = i + 1;
			}
		} else if (recentPage == endPage || recentPage == endPage - 1) {
			int start = endPage;
			for (int i = PAGE_LENGTH - 1; i >= 0; i--) {
				pages[i] = start--;
			}
		} else {
			int start = (int) (recentPage - (PAGE_LENGTH / 2 ) );
			for (int i = 0; i < PAGE_LENGTH; i++) {
				pages[i] = start++;
			}
		}
		
		if(keyword == null) {
			keyword = "";
		}
		
		request.setAttribute("kwd", keyword);
		request.setAttribute("list", list);
		request.setAttribute("pages", pages);
		request.setAttribute("recentPage", recentPage);
		request.setAttribute("endPage", endPage);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
