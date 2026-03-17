package com.unibridge.app.mentorSearch.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.mentorSearch.dao.MentorSearchDAO;
import com.unibridge.app.mentorSearch.dto.MentorSearchDTO;

public class MentorSearchOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MentorSearchDAO dao = new MentorSearchDAO();
		Result result = new Result();

		// 1. DB에서 전체 멘토 목록 조회
		List<MentorSearchDTO> mentorList = dao.selectAllMentors();

		// 2. 조회된 데이터를 request 영역에 저장 (JSP에서 사용하기 위함)
		request.setAttribute("mentorList", mentorList);

		// 3. 이동 경로 설정 (데이터를 들고 가야 하므로 forward 방식)
		result.setPath("/app/user/mentorSearch/mentorSearch.jsp");
		result.setRedirect(false);

		return result;
	}
}