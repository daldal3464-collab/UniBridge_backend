package com.unibridge.app.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unibridge.api.SmsAuthService; // 서비스 임포트
import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class MenteeVerifyActionController implements Execute {

    // 비즈니스 로직을 담당하는 서비스 객체 생성
    private final SmsAuthService smsService = new SmsAuthService();

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	System.out.println("전화번호 인증 시작");
        
        String mode = request.getParameter("mode");
        response.setContentType("text/plain; charset=utf-8");

        if ("send".equals(mode)) {
            // 문자 발송: 서비스 호출
            String phoneNumber = request.getParameter("phoneNumber");
            System.out.println("[Log] 수신 번호: " + phoneNumber);
            boolean isSent = smsService.sendSms(request, phoneNumber);
            System.out.println("[Log] 발송 결과: " + isSent);
            response.getWriter().write(isSent ? "success" : "fail");

        } else if ("check".equals(mode)) {
            // 인증번호 확인: JSON 파싱 후 서비스 호출
            String userCode = parseJsonAuthCode(request);
            String result = smsService.verifyCode(request, userCode);
            response.getWriter().write(result);
        }

        return null; // AJAX 요청이므로 이동 경로 없음
    }

    // JSON 본문에서 authCode만 추출하는 헬퍼 메서드
    private String parseJsonAuthCode(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return new Gson().fromJson(sb.toString(), JsonObject.class).get("authCode").getAsString();
    }
}