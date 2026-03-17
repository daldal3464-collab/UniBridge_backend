package com.unibridge.app.pay.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class PaymentController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();

		try {
			// 멘토 번호를 받아서 상세 정보를 다시 조회할 수도 있습니다.
			String mentorNumber = request.getParameter("mentorNumber");
			System.out.println(">>> [DEBUG] 결제 대상 멘토 번호: " + mentorNumber);

			// 결제 페이지에 필요한 데이터 세팅
			request.setAttribute("mentorNumber", mentorNumber);
			request.setAttribute("totalAmount", 10000); // 임시 금액

			// [중요] 이동할 경로 설정 (실제 결제 확인 JSP 경로)
			result.setPath("/app/user/mentorSearch/payment/payment.jsp");
			result.setRedirect(false); // 포워드 방식

			System.out.println(">>> [DEBUG] PaymentController 실행 완료, 경로 설정됨");

		} catch (Exception e) {
			e.printStackTrace();
			// 에러 발생 시 처리 (예: 에러 페이지 이동)
		}

		return result;
	}

}
