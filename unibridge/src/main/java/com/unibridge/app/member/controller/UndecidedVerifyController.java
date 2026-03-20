package com.unibridge.app.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class UndecidedVerifyController implements Execute {
	
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result result = new Result();
        HttpSession session = request.getSession();
		MemberDTO memberNumber = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("MentorMange컨트롤러 : " + memberNumber.getMemberNumber());
        
        // 사용자가 입력한 값
        String inputPw = request.getParameter("memberPw");
        // (전화번호 인증 로직이 있다면 여기서 처리)

        MemberDAO dao = new MemberDAO();
        // DB에서 현재 사용자의 실제 정보를 가져옴
        Map<String, Object> member = dao.selectMember(memberNumber.getMemberNumber());
        String dbPw = (String) member.get("MEMBER_PW"); // Key 이름 주의!

        if (dbPw != null && dbPw.equals(inputPw)) {
            // 인증 성공 -> 실제 수정 페이지로 이동
            request.setAttribute("member", member); // 수정 페이지에 뿌릴 데이터
            result.setPath("/app/user/undetermined/myPage/userManage/userModify.jsp");
            result.setRedirect(false);
        } else {
            // 인증 실패 -> 다시 인증 페이지로 가면서 에러 메시지 전달
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            result.setPath("/app/user/undetermined/myPage/userManage/userModifyCheck.jsp");
            result.setRedirect(false);
        }
        
        return result;
    }
}
