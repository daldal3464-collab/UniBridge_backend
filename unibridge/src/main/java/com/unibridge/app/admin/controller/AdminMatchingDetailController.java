package com.unibridge.app.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdMatchingDAO;
import com.unibridge.app.admin.dto.AdMatchingCancelDTO;

public class AdminMatchingDetailController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Result result = new Result();
		AdMatchingDAO matchingDAO = new AdMatchingDAO();
		int matchingNumber = Integer.parseInt(request.getParameter("matchingNumber"));
		
		System.out.println("매칭 넘버 : " + matchingNumber);
		
		AdMatchingCancelDTO matchingDTO = matchingDAO.getCancelDetail(matchingNumber);
		
		request.setAttribute("matchingCancel", matchingDTO);
		
		System.out.println("매칭 상세 보기 :" + matchingDTO);
		
		result.setPath("/app/admin/adminMatching/matchingDetail.jsp");
		result.setRedirect(false);
		return result;
	}

}
