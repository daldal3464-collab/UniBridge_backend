package com.unibridge.app.member.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unibridge.api.SmsAuthService;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class SignupController implements Execute {
	MemberDAO memberDAO = new MemberDAO();
	Result outResult = new Result();
	
	// SMS 서비스 객체를 필드로 생성
    private SmsAuthService smsService = new SmsAuthService();
	
	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod().toUpperCase();
		switch (method) {
		case "GET":
			this.doGet(request, response);
			break;
		case "POST":
			this.doPost(request, response);
			break;
		default:
			break;
		}
		return outResult;
	}
	
	private void doGet(HttpServletRequest request, HttpServletResponse response) {
		outResult.setRedirect(true);
		outResult.setPath(request.getContextPath() + "/app/user/signup/signup.jsp");
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		// 인증 관련 요청(mode)이 있는지 확인

		try {
			request.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html; charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");

	        String mode = request.getParameter("mode");
	        
			// 인증번호 발송 단계
			if ("send".equals(mode)) {
				String phone = request.getParameter("userPhone");
				//발송 진행
				boolean isSent = smsService.sendSms(request, phone);
				response.getWriter().write(isSent ? "success" : "fail");
				outResult = null; // Ajax 요청이므로 Result를 null로 만들어 페이지 이동 방지
				return; 
			}

			// 인증번호 확인 단계
			if ("check".equals(mode)) {
				String userCode = parseAuthCode(request);
				//인증
				String result = smsService.verifyCode(request, userCode);
				response.getWriter().write(result);
				outResult = null; // Ajax 요청이므로 Result를 null로 만들어 페이지 이동 방지
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 최종 회원가입 단계 (mode가 없을 때)
		// 세션에서 인증 여부 확인
		HttpSession session = request.getSession();
		Boolean isVerified = (Boolean) session.getAttribute("isPhoneVerified");

		// 인증이 안 된 경우 차단
		if (isVerified == null || !isVerified) {
			outResult.setRedirect(true);
			outResult.setPath(request.getContextPath() + "/signup.mem?error=unverified");
			return;
		}
		
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");
		String userNickname = request.getParameter("userNickname");
		String userPhone = request.getParameter("userPhone");
		String userGender = request.getParameter("userGender");
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(userId);
		memberDTO.setMemberPw(userPw);
		memberDTO.setMemberName(userName);
		memberDTO.setMemberNickname(userNickname);
		memberDTO.setMemberPhone(userPhone);
		memberDTO.setMemberGender(userGender);
		
		int dtoResult = memberDAO.memberSignup(memberDTO);
		
		outResult.setRedirect(true);
		outResult.setPath(request.getContextPath() + "/signin.mem");
	}
	
	// 클라이언트가 보낸 JSON 형식의 데이터를 읽어서 특정 값(authCode)만 추출
    private String parseAuthCode(HttpServletRequest request) throws IOException {
        //문자열을 하나로 합치기 위한 도구
    	StringBuilder sb = new StringBuilder();
        //한 줄씩 읽어올 때 임시로 데이터를 담는 변수
        String line;
        try (BufferedReader reader = request.getReader()) {
        	//한 줄씩 읽어서 합치기
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        //JSON 해석 및 값 추출
        return new Gson().fromJson(sb.toString(), JsonObject.class).get("authCode").getAsString();
    }


}
