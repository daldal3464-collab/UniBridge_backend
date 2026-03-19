package com.unibridge.app.mypage.mentoring.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;
import com.unibridge.app.mypage.mentoring.dto.MentoringDTO;

public class MentoringWriteOkController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=========================================");
        System.out.println("[Log] MentoringWriteOkController 시작");
        
        MentoringDAO mentoringDAO = new MentoringDAO();
        MentoringDTO mentoringDTO = new MentoringDTO();
        Result result = new Result();
        
        HttpSession session = request.getSession();
        // [수정] 세션의 memberNumber는 보통 Long으로 관리되므로 타입을 맞춥니다.
        Object loginUserObj = session.getAttribute("memberNumber");

        String uploadPath = request.getServletContext().getRealPath("/") + "upload/";
        int fileSize = 1024 * 1024 * 5; // 5MB

        try {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, "UTF-8",
                    new DefaultFileRenamePolicy());

            String title = multi.getParameter("mentoringTitle");
            String subject = multi.getParameter("mentoringSubject");
            String goal = multi.getParameter("mentoringPurpose");
            String detail = multi.getParameter("mentoringCurriculum");

            long mentorNumber;
            if (loginUserObj == null) {
                System.out.println("[Warn] 세션에 유저 정보가 없음 - 테스트용 21번 세팅");
                mentorNumber = 21L; 
            } else {
                // 세션 값이 Integer일 수도 있으므로 안전하게 파싱
                mentorNumber = Long.parseLong(String.valueOf(loginUserObj));
            }
            mentoringDTO.setMentorNumber(mentorNumber);

            // [수정] DB Insert 전 중복 등록 여부 체크 (파라미터 타입 long으로 대응)
            int existingCount = mentoringDAO.checkAlreadyExists(mentorNumber);

            if (existingCount > 0) {
                System.out.println("[Warn] 멘토 " + mentorNumber + "는 이미 등록된 멘토링이 있음. 등록 중단.");
                result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my?error=already_exists");
                result.setRedirect(true);
                return result; 
            }

            mentoringDTO.setSubjectNumber(Integer.parseInt(subject));
            mentoringDTO.setMentoringTitle(title);
            mentoringDTO.setMentoringGoal(goal);
            mentoringDTO.setMentoringDetail(detail);
            mentoringDTO.setFileNumber(1L); // DTO 수정에 맞춰 Long 타입 전달

            System.out.println("[Step 6] 중복 없음. DB Insert 실행 중...");
            mentoringDAO.insert(mentoringDTO); 
            
            // [수정] getInternalId() -> getMentoringNumber()로 변경
            long createdNumber = mentoringDTO.getMentoringNumber();
            System.out.println("[Step 6] DB Insert 성공! 생성된 번호: " + createdNumber);

            // 상세 페이지 이동 경로 수정
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoringView.my?mentoringNumber=" + createdNumber);
            result.setRedirect(true);
            
            System.out.println("[Log] 이동 경로 설정 완료: " + result.getPath());
            return result;

        } catch (Exception e) {
            System.out.println("[Error] 예외 발생: " + e.getMessage());
            e.printStackTrace();
            
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my");
            result.setRedirect(true);
            return result;
        } finally {
            System.out.println("[Log] MentoringWriteOkController 종료");
            System.out.println("=========================================");
        }
    }
}