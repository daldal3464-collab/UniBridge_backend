package com.unibridge.app.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class UndecidedUpdateOkController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result result = new Result();
        HttpSession session = request.getSession();
		MemberDTO memberNumber = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("MentorMange컨트롤러 : " + memberNumber.getMemberNumber());

        // 1. 파라미터 수집
        String nickname = request.getParameter("memberNickname");
        String pw = request.getParameter("memberPw");
        String phone = request.getParameter("memberPhone");
        String gender = request.getParameter("memberGender");

        // 2. Map에 담기
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("memberNumber", memberNumber);
        updateMap.put("memberNickname", nickname);
        updateMap.put("memberPhone", phone);
        updateMap.put("memberGender", gender);
        
        // 비밀번호는 입력했을 때만 수정되도록 처리
        if (pw != null && !pw.isEmpty()) {
            updateMap.put("memberPw", pw);
        }

        // 3. DAO 호출 (Update 실행)
//        MemberDAO dao = new MemberDAO();
//        dao.updateMember(updateMap);

        // 4. 완료 후 다시 마이페이지 메인으로 이동
        result.setPath("/auth/undecided/myPage.my");
        result.setRedirect(true); // 수정 후에는 반드시 Redirect!
        
        return result;
    }
}
