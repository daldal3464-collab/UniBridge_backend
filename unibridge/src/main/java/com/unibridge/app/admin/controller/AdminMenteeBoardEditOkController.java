package com.unibridge.app.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdMenteeBoardDAO;
import com.unibridge.app.admin.dto.AdMenteeBoardDTO;


public class AdminMenteeBoardEditOkController implements Execute{

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

	    AdMenteeBoardDAO boardDAO = new AdMenteeBoardDAO();
	    AdMenteeBoardDTO boardDTO = new AdMenteeBoardDTO();
	    Result result = new Result();

	    int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
	    String boardTitle = request.getParameter("boardTitle");
	    String boardContent = request.getParameter("boardContent");

	    boardDTO.setMenteeboardNumber(boardNumber);
	    boardDTO.setBoardTitle(boardTitle);
	    boardDTO.setBoardContent(boardContent);

		// 게시글 업데이트 실행
		boardDTO.setWriteNumber((Integer) request.getSession().getAttribute("adminNumber"));
		boardDAO.updateBoard(boardDTO);
		System.out.println("게시글 수정 완료");

		
		// 수정 완료 후 페이지 이동
		String path = request.getContextPath() + "/menteeBoardList.admin";
		result.setPath(path);
		result.setRedirect(true);
		
		return result;

	

	
}
}