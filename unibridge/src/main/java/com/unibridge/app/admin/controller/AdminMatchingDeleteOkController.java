package com.unibridge.app.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdMatchingDAO;

public class AdminMatchingDeleteOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Result result = new Result();
		AdMatchingDAO matchingDAO = new AdMatchingDAO();
		
		String matchingNumber = request.getParameter("matchingNumber");
		System.out.println("삭제 매칭 번호 : " + matchingNumber);
		
		matchingDAO.delete(Integer.parseInt(matchingNumber));
		
		
		result.setPath(request.getContextPath()+"/app/admin/adminMatching/matching.admin");
		result.setRedirect(true);
		return result;
	}

}
