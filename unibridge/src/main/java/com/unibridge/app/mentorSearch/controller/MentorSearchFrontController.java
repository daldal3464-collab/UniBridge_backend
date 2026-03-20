package com.unibridge.app.mentorSearch.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Result;

public class MentorSearchFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 1. 요청 경로 추출
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String target = requestURI.substring(contextPath.length());

		// [시작 로그] 이 메시지가 안 보이면 여전히 배포/빌드 문제입니다.
		System.out.println("\n========= [UniBridge FrontController Start] =========");
		System.out.println("요청 받은 전체 URI: " + requestURI);
		System.out.println("컨텍스트 경로: " + contextPath);
		System.out.println("최종 타겟 경로 (target): " + target);
		System.out.println("=====================================================");

		Result result = null;

		try {
			// 3. 분기 처리
			switch (target) {
			case "/mentor/mentorSearch.sch":
			case "/mentor/mentorSearchOk.sch":
				System.out.println("[Log] 결과: 멘토 검색 페이지로 단순 이동");
				result = new Result();
				result.setPath("/app/user/mentorSearch/mentorSearch.jsp");
				result = new MentorSearchOkController().execute(request, response);
				result.setRedirect(false);
				break;

			case "/mentor/mentorDetailOk.sch":
				System.out.println("[Log] 결과: MentorDetailOkController 실행 시도...");
				result = new MentorDetailOkController().execute(request, response);
				System.out.println("[Log] 결과: MentorDetailOkController 실행 완료!");
				break;

			default:
				System.out.println("[Warn] 알 수 없는 경로 요청입니다: " + target);
				break;
			}
		} catch (Exception e) {
			// 로직 실행 중 발생하는 모든 예외를 콘솔에 찍어줍니다.
			System.out.println("[Error] 컨트롤러 실행 중 예외 발생!");
			e.printStackTrace();
		}

		// 4. 전송 처리 (공통 로직)
		if (result != null && result.getPath() != null) {
			if (result.isRedirect()) {
				System.out.println("[Log] Redirect 이동: " + result.getPath());
				response.sendRedirect(result.getPath());
			} else {
				System.out.println("[Log] Forward 이동: " + result.getPath());
				request.getRequestDispatcher(result.getPath()).forward(request, response);
			}
		} else {
			System.out.println("[Log] 결과(result)가 null이거나 경로가 설정되지 않았습니다.");
		}

		System.out.println("========= [UniBridge FrontController End] =========\n");
	}
}