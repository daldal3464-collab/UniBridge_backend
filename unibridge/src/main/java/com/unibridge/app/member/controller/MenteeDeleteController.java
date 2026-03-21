package com.unibridge.app.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unibridge.api.SmsAuthService; // 기존 서비스 활용
import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class MenteeDeleteController implements Execute {
    
    // 공통 서비스 객체 사용
    private final SmsAuthService smsService = new SmsAuthService();

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String method = request.getMethod().toUpperCase();

        if ("GET".equals(method)) {
            Result result = new Result();
            result.setPath("/app/user/mentee/myPage/userDelete/userDelete.jsp");
            result.setRedirect(false);
            return result;
        } else {
            // POST 방식: AJAX 인증 처리 (mode에 따라 분기)
            String mode = request.getParameter("mode");
            response.setContentType("text/plain; charset=utf-8");

            if ("send".equals(mode)) {
                String phoneNumber = request.getParameter("phoneNumber");
                // 서비스의 sendSms 호출 (세션에 serverAuthCode 저장 및 문자 발송)
                boolean isSent = smsService.sendSms(request, phoneNumber);
                response.getWriter().write(isSent ? "success" : "fail");

            } else if ("check".equals(mode)) {
                // JSON에서 인증번호 추출
                String userCode = parseJsonAuthCode(request);
                // 서비스의 verifyCode 호출 (일치 여부 확인 및 세션에 isPhoneVerified 저장)
                String result = smsService.verifyCode(request, userCode);
                
                if ("verified".equals(result)) {
                    // 탈퇴 프로세스 전용 인증 플래그 추가 설정 (선택 사항)
                    request.getSession().setAttribute("isDeleteVerified", true);
                    response.getWriter().write("verified");
                } else {
                    response.getWriter().write("invalid");
                }
            }
            return null;
        }
    }

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