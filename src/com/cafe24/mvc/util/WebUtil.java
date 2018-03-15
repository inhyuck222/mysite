package com.cafe24.mvc.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	
	public static int checkParameter(String value, int defaultValue) {
		return defaultValue;
	}
	
	public static long checkParameter(String value, long defaultValue) {
		return defaultValue;
	}
	
	public static String checkParameter(String value, String defaultValue) {
		return defaultValue;
	}
	
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
		response.sendRedirect(url);
	}
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		// forwarding
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
