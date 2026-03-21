package com.unibridge.app.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class MenteeVerifySubmitController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Result result = new Result();
        HttpSession session = request.getSession();
        MemberDAO memberDAO = new MemberDAO();
        
        // 1. 세션에서 로그인 시 저장한 MemberDTO 객체 획득
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        
        // 2. 비로그인 상태 접근 제어
        if (loginUser == null) {
            result.setPath(request.getContextPath() + "/mvc/auth/signin.my"); // 로그인 페이지 경로
            result.setRedirect(true);
            return result;
        }

        // 3. 사용자가 폼에서 입력한 값들
        String inputPw = request.getParameter("password");
        Boolean isPhoneVerified = (Boolean) session.getAttribute("isPhoneVerified");

        // 4. DB 검증 (로그인된 유저의 고유 번호 memberNumber 사용)
        boolean isPwCorrect = memberDAO.checkPassword(loginUser.getMemberNumber(), inputPw);

        // 5. 최종 판단
        if (isPwCorrect && isPhoneVerified != null && isPhoneVerified) {
            // 검증 성공 -> 정보 수정 실제 처리 페이지(UpdateOk)로 이동
            result.setPath(request.getContextPath() + "/mvc/auth/mentee/updateOk.my");
            result.setRedirect(true); 
        } else {
            // 검증 실패 시 에러 메시지 설정
            if (!isPwCorrect) {
                request.setAttribute("pwError", "비밀번호가 일치하지 않습니다.");
            }
            if (isPhoneVerified == null || !isPhoneVerified) {
                request.setAttribute("authError", "휴대폰 인증을 완료해주세요.");
            }
            
            // 입력 폼으로 다시 이동 (Forward 방식이어야 에러 메시지가 유지됨)
            result.setPath("/app/user/mentee/myPage/userManage/userModifyCheck.jsp");
            result.setRedirect(false); 
        }

        return result;
    }
}