package com.unibridge.app.mypage.mentoring.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;
import com.unibridge.app.mypage.mentoring.dto.MentoringDTO;

public class MentoringWriteOkController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[Log] MentoringWriteOkController 진입");
        
        MentoringDAO mentoringDAO = new MentoringDAO();
        MentoringDTO mentoringDTO = new MentoringDTO();
        Result result = new Result();

        // 1. 파일 업로드 경로 설정 및 확인
        String uploadPath = request.getServletContext().getRealPath("/") + "upload/";
        System.out.println("[Log] 업로드 실제 경로: " + uploadPath);
        
        int fileSize = 1024 * 1024 * 5; // 5MB

        try {
            // 2. MultipartRequest 객체 생성 (이 시점에 파일이 서버에 저장됨)
            MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
            System.out.println("[Log] MultipartRequest 객체 생성 성공");

            // 3. 데이터 수집 로그 출력 (JSP name 속성 매칭 확인)
            String title = multi.getParameter("mentoringTitle");
            String subject = multi.getParameter("mentoringSubject");
            String curriculum = multi.getParameter("mentoringCurriculum");
            String fileName = multi.getFilesystemName("mentoringFile"); // 업로드된 파일명 확인

            System.out.println("[Log] 수집 데이터 - 제목: " + title);
            System.out.println("[Log] 수집 데이터 - 과목: " + subject);
            System.out.println("[Log] 수집 데이터 - 상세: " + curriculum);
            System.out.println("[Log] 수집 데이터 - 파일명: " + fileName);
            
            // 4. DTO 데이터 세팅
            mentoringDTO.setMentoringTitle(title);
            mentoringDTO.setMentoringGoal(subject); 
            mentoringDTO.setMentoringDetail(curriculum);
            // mentoringDTO.setFileName(fileName); // DTO에 파일명 필드가 있다면 추가
            
            // TODO: 실제 세션 연동 시 아래 주석 해제
            // int mentorNumber = (Integer)request.getSession().getAttribute("mentorNumber");
            int mentorNumber = 1; 
            mentoringDTO.setMentorNumber(mentorNumber);
            System.out.println("[Log] 멘토 번호 세팅: " + mentorNumber);

            // 5. DAO 실행 및 로그
            System.out.println("[Log] DB Insert 시도...");
            mentoringDAO.insert(mentoringDTO);
            System.out.println("[Log] DB Insert 완료");
            
            // 6. 이동 경로 설정
            result.setPath(request.getContextPath() + "/mentoringList.mo");
            result.setRedirect(true);
            System.out.println("[Log] 리다이렉트 경로 설정: " + result.getPath());
            
        } catch (Exception e) {
            System.out.println("[Error] MentoringWriteOkController 실행 중 예외 발생!");
            System.out.println("[Error] 메시지: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}