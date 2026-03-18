package com.unibridge.app.mypage.mentoring.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class MentoringFrontController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
	    String target = requestURI.substring(contextPath.length());
	    
	    Result result = null;

	    System.out.println("[Log] 요청 들어옴: " + target);

	    try {
	        // 3. 분기 처리
	        switch (target) {
	            case "/mentoringWriteOk.my":
	                System.out.println("[Log] 결과: MentoringWriteOkController 실행 시도...");
	                result = new MentoringWriteOkController().execute(request, response);
	                System.out.println("[Log] 결과: MentoringWriteOkController 실행 완료!");
	                break;

	            case "/mentoringView.my":
	                System.out.println("[Log] 결과: MentoringViewController 실행 시도...");
	                result = new MentoringViewController().execute(request, response);
	                System.out.println("[Log] 결과: MentoringViewController 실행 완료!");
	                break;

	            case "/mentoringModify.my":
	                System.out.println("[Log] 결과: MentoringModifyController 실행 시도...");
	                result = new MentoringModifyController().execute(request, response);
	                System.out.println("[Log] 결과: MentoringModifyController 실행 완료!");
	                break;

	            case "/mentoringModifyOk.my":
	                System.out.println("[Log] 결과: MentoringModifyOkController 실행 시도...");
	                result = new MentoringModifyOkController().execute(request, response);
	                System.out.println("[Log] 결과: MentoringModifyOkController 실행 완료!");
	                break;

	            case "/mentoringDeleteOk.my":
	                System.out.println("[Log] 결과: MentoringDeleteOkController 실행 시도...");
	                result = new MentoringDeleteOkController().execute(request, response);
	                System.out.println("[Log] 결과: MentoringDeleteOkController 실행 완료!");
	                break;

	            default:
	                System.out.println("[Warn] 알 수 없는 경로 요청입니다: " + target);
	                // 기본적으로 에러 페이지나 메인으로 보낼 수 있습니다.
	                break;
	        }
	    } catch (Exception e) {
	        // 로직 실행 중 발생하는 모든 예외를 콘솔에 찍어줍니다.
	        System.out.println("[Error] 컨트롤러 실행 중 예외 발생!");
	        e.printStackTrace();
	    }

	    // 4. 전송 처리
	    if (result != null) {
	        if (result.isRedirect()) {
	            response.sendRedirect(result.getPath());
	        } else {
	            request.getRequestDispatcher(result.getPath()).forward(request, response);
	        }
	    }
	}
}