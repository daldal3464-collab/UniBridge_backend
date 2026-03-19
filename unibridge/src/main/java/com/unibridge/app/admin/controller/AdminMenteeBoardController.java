package com.unibridge.app.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdMenteeBoardDAO;
import com.unibridge.app.admin.dto.AdMenteeBoardListDTO;

public class AdminMenteeBoardController implements Execute{

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("===boardListOkController 실행===");
		AdMenteeBoardDAO AdMenteeBoardDAO = new AdMenteeBoardDAO();
		Result result = new Result();

		// 페이징 처리
		String temp = request.getParameter("page");
		int page = (temp == null) ? 1 : Integer.valueOf(temp);
		// 한 페이지당 게시글 수
		int rowCount = 10;
		// 페이지 버튼 수
		int pageCount = 10;

		int dateFrom = Integer.parseInt(request.getParameter("dateFrom"));
		int dateTo = Integer.parseInt(request.getParameter("dateTo"));
		int startRow = (page - 1) * rowCount + 1;
		int endRow = startRow + rowCount - 1;

		Map<String, Integer> pageFilter = new HashMap<>();
		pageFilter.put("dateFrom", dateFrom);
		pageFilter.put("dateTo", dateTo);
		pageFilter.put("startRow", startRow);
		pageFilter.put("endRow", endRow);
		
		System.out.println("필터 날짜, 페이징 확인 : " + pageFilter);

		// 게시글 목록 조회
		List<AdMenteeBoardListDTO> boardList = AdMenteeBoardDAO.selectFilter(pageFilter);
		request.setAttribute("boardList", boardList);

		// 페이징 정보 설정
		// BoardMapper.xml의 getTotal을 이용하여 전체 게시글 개수 조회
		// 실제 마지막 페이지 번호(realEndPage)를 계산함
		int total = AdMenteeBoardDAO.getTotal();
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

		request.setAttribute("page", page);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);

		System.out.println("======페이징 정보 확인======");
		System.out.println("pageFilter : " + pageFilter);
		System.out.println("boardList : " + boardList);
		System.out.println(
				"startPage : " + startPage + ", endPage : " + endPage + ", prev : " + prev + ", next : " + next);
		System.out.println("=========================");

		result.setPath("/app/admin/adminBoard/menteeBoard/menteeBoardList.admin");
		result.setRedirect(false);

		return result;
	}

}
