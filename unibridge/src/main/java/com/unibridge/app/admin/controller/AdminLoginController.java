package com.unibridge.app.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class AdminLoginController implements Execute{

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Result result = new Result();
//		Cookie[] cookies = request.getCookies();
//		
//		if(cookies != null) {
//			for(Cookie cookie : cookies) {
//				if(cookie.getName().equals("adminId")) {
//					request.setAttribute("adminId", cookie.getValue());
//				}
//			}
//		}
//		
		result.setPath("/app/admin/adminLogin/login.jsp");
		result.setRedirect(false);
		return result;
	}

}
