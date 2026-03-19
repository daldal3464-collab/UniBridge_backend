package com.unibridge.app.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Result;

public class MypageFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MypageFrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		System.out.println("\n=== [MypageFrontController] ===");

		String requestURI = request.getRequestURI();
		String target = extractUserRole(requestURI);
		
		System.out.println("기본 URI: " + requestURI); 
		System.out.println("추출된 Target: " + target);
		
		Result result = null;

		switch (target) {
		case "/auth/mentor":
			result = new MentorFrontController().execute(request, response);
			break;
		case "/auth/mentee":
			result = new MenteeFrontController().execute(request, response);
			break;
		case "/auth/undecided": // 이제 주소가 일치하므로 이 case를 탑니다.
			System.out.println("미정(Undecided) 마이페이지 이동");
			result = new UndecidedFrontController().execute(request, response);
			break;
		default:
			// 여전히 404가 난다면 target에 어떤 문자열이 담기는지 콘솔에 찍어보세요.
			System.out.println("[Error] 매칭 안됨. target값: " + target);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (result != null) {
			if (result.isRedirect()) {
				response.sendRedirect(result.getPath());
			} else {
				request.getRequestDispatcher(result.getPath()).forward(request, response);
			}
		}
	}

	private String extractUserRole(String requestUri) {
		if (requestUri.contains("/auth/mentor"))
			return "/auth/mentor";
		if (requestUri.contains("/auth/mentee"))
			return "/auth/mentee";
		if (requestUri.contains("/auth/undecided"))
			return "/auth/undecided";
		return "";
	}
}
