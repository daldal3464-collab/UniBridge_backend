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

public class UndecidedDeleteController implements Execute {

    private Result outResult = new Result();

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	System.out.println("===UndecidedDeleteController==");
    	
        String method = request.getMethod().toUpperCase();

        switch (method) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
                break;
        }

        return outResult;
    }

    // 탈퇴 페이지 이동
    private void doGet(HttpServletRequest request, HttpServletResponse response) {
    	
    	// 회원 탈퇴 페이지
    	outResult.setPath("/app/user/undetermined/myPage/userDelete/userDelete.jsp");
        outResult.setRedirect(false);
        
    }

    // 실제 탈퇴 처리
    private void doPost(HttpServletRequest request, HttpServletResponse response) {

    	System.out.println("[DeleteController] POST - 회원 탈퇴 진행");

    	HttpSession session = request.getSession();
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("MenteeMange컨트롤러 : " + loginUser.getMemberNumber());
	    
	    // 1. JSP에서 입력한 데이터(비밀번호 등) 받아오기
	    String inputId = request.getParameter("memberId");
	    String inputPw = request.getParameter("memberPw");
	    
	    System.out.println("입력받은 ID : "+ inputId);
	    System.out.println("입력받은 PW : "+ inputPw);

	    // 3. 검증 로직 (세션 정보와 입력 정보 비교)
	    // 예: 로그인된 아이디와 입력한 아이디가 같은지, 비밀번호가 DB와 일치하는지 등
	    if (loginUser.getMemberId().equals(inputId)) {
	        MemberDAO memberDAO = new MemberDAO();
	        
	        // DTO에 입력받은 비밀번호를 담아서 DAO로 전달 (DB 비밀번호 확인용)
	        MemberDTO checkDto = new MemberDTO();
	        checkDto.setMemberNumber(loginUser.getMemberNumber());
	        checkDto.setMemberPw(inputPw);
	
	        boolean isValid = memberDAO.checkMember(checkDto);
	
	        if (isValid) {
	            // 탈퇴 처리
	            memberDAO.deleteMember(loginUser.getMemberNumber());
	            session.invalidate();
	            outResult.setRedirect(true);
	            outResult.setPath(request.getContextPath() + "/index.main");
	            return;
	        }
	        // 검증 실패 시
	        request.setAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
	    }

//	    outResult.setPath("/app/user/undetermined/myPage/userDelete/userDelete.jsp");
//	    outResult.setRedirect(false);
        
    }
}