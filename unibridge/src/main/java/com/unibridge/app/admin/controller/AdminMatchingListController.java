package com.unibridge.app.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdMatchingDAO;
import com.unibridge.app.admin.dto.AdMatchingListDTO;

public class AdminMatchingListController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Result result = new Result();
		
		AdMatchingDAO matchingDAO = new AdMatchingDAO();
		
		List<AdMatchingListDTO> matchingList = matchingDAO.getCancelSelect();
		int total = matchingDAO.getCancelTotal();
		
		System.out.println("매칭 수 : " + total);
		System.out.println("매칭 리스트" + matchingList);
		
		request.setAttribute("matchingList", matchingList);
		request.setAttribute("total",total);
		
		result.setPath("/app/admin/adminMatching/matching.jsp");
		result.setRedirect(false);
		return result;
	}
	
}
