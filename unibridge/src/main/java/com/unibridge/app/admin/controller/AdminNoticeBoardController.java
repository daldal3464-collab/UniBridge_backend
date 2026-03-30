package com.unibridge.app.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdNoticeBoardDAO;
import com.unibridge.app.admin.dto.AdNoticeBoardDTO;

public class AdminNoticeBoardController implements Execute{

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("===AdminNoticeBoardController 실행===");
		AdNoticeBoardDAO boardDAO = new AdNoticeBoardDAO();
		HttpSession session = request.getSession(true);
		Result result = new Result();

		// 페이징 처리
		String temp = request.getParameter("page");	//위치
		int page = (temp == null) ? 1 : Integer.valueOf(temp);
		// 한 페이지당 게시글 수
		int rowCount = 10;
		// 페이지 버튼 수
		int pageCount = 5;

		int startRow = (page - 1) * rowCount + 1;
		int endRow = startRow + rowCount - 1;
		Map<String, String> pageFilter = new HashMap<>();
		pageFilter.put("startRow", Integer.toString(startRow));
		pageFilter.put("endRow", Integer.toString(endRow));
		
		// 페이지 총 개수
		int total = 0;
		
		// 필터링 값
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String boardType = request.getParameter("boardType") == null? "" : request.getParameter("boardType") ;
		 		
		System.out.println("시작일 : "+request.getParameter("dateFrom")+ " 종료일 : "+request.getParameter("dateTo"));
		System.out.println("카테고리 : " + request.getParameter("boardType"));
		System.out.println("");

		// 값을 저장할 DTO
		List<AdNoticeBoardDTO> boardList = null;
		
		if(dateFrom != null && dateTo != null 
				&& dateFrom.length() == 10 && dateTo.length() == 10) {
			
			
			if(!boardType.trim().isEmpty()){
				System.out.println("--날짜 범위, 카테고리가 있는 경우--");
			
				System.out.println("시작일 : "+dateFrom+ " 종료일 : "+dateTo);
				System.out.println("카테고리 : " +boardType);
		
				pageFilter.put("dateFrom", dateFrom);
				pageFilter.put("dateTo", dateTo);
				pageFilter.put("boardType", boardType);
						
				total = boardDAO.getRenderingDateType(pageFilter);

				System.out.println("pageFilter : " + pageFilter);
				
				boardList = boardDAO.selectDateTypeFilter(pageFilter);
				
			}else {
				System.out.println("--날짜 범위만 있는 경우--");
				
				System.out.println("시작일 : "+dateFrom+ " 종료일 : "+dateTo);
				pageFilter.put("dateFrom", dateFrom);
				pageFilter.put("dateTo", dateTo);
				
				total = boardDAO.getRenderingDate(pageFilter);

				System.out.println("pageFilter : " + pageFilter);
				boardList = boardDAO.selectDateFilter(pageFilter);

			}	
		}else{
			
			if(!boardType.trim().isEmpty()){
			System.out.println("--카테고리만 있는 경우--");
			
			pageFilter.put("boardType", boardType);
			total = boardDAO.getRenderingType(pageFilter);
			System.out.println("pageFilter : " + pageFilter);
			boardList = boardDAO.selectTypeFilter(pageFilter);

			}else {
				
			System.out.println("--전체 조회하는 경우--");
			// 게시글 전체 조회
			total = boardDAO.getTotal();
			boardList = boardDAO.selectAll(pageFilter);
			
			}
		}

		System.out.println(boardList);
		
		session.setAttribute("boardType", boardType);
		session.setAttribute("dateFrom", dateFrom);
		session.setAttribute("dateTo", dateTo);
		
		//request에 boardList 저장
		request.setAttribute("boardList", boardList);
		
		System.out.println("total = " + total);
		
		// 실제 마지막 페이지(전체 게시글 기준으로 계산)
		int realEndPage = (int) (Math.ceil(total / (double) rowCount));
		// 현재 페이지 그룹에서의 마지막 페이지
		int endPage = (int) (Math.ceil(page / (double) pageCount) * pageCount);
		// 현재 페이지 그룹에서의 첫 페이지
		int startPage = endPage - (pageCount - 1);

		// endPage가 실제 존재하는 마지막 페이지보다 크면 조정
		endPage = Math.min(endPage, realEndPage);

		// prev, next 버튼 활성화여부 확인
		boolean prev = startPage > 1;
		boolean next = endPage < realEndPage;
		
		System.out.println("realEndPage : " + realEndPage +"endpage : "+ endPage);
		
		request.setAttribute("page", page);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);

		System.out.println("======페이징 정보 확인======");
		System.out.println("pageFilter : " + pageFilter);
		System.out.println("boardList : " + request.getAttribute("boardList"));
		System.out.println(
				"startPage : " + startPage + ", endPage : " + endPage + ", prev : " + prev + ", next : " + next);
		System.out.println("=========================");

		result.setPath("/app/admin/adminNotice/noticeList.jsp");
		result.setRedirect(false);
		
		return result;
	}
	

}
