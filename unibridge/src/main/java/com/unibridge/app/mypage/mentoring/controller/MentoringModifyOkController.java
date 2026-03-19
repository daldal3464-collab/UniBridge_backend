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

public class MentoringModifyOkController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[Log] MentoringModifyOkController 진입");

        MentoringDAO dao = new MentoringDAO();
        MentoringDTO dto = new MentoringDTO();
        Result result = new Result();

        // 1. 파일 업로드 경로 설정
        String uploadPath = request.getServletContext().getRealPath("/") + "upload/";
        int fileSize = 1024 * 1024 * 5; // 5MB

        try {
            // 2. MultipartRequest 생성
            MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

            // 3. 파라미터 수집 (mentoringNumberStr 확인 중요)
            String mentoringNumberStr = multi.getParameter("mentoringNumber");
            String title = multi.getParameter("mentoringTitle");
            String purpose = multi.getParameter("mentoringPurpose");
            String curriculum = multi.getParameter("mentoringCurriculum");
            String subjectStr = multi.getParameter("mentoringSubject");
            String fileName = multi.getFilesystemName("mentoringFile");

            // [체크] 수정 대상 번호가 없으면 업데이트 불가
            if (mentoringNumberStr == null || mentoringNumberStr.isEmpty()) {
                System.out.println("[Error] mentoringNumber 파라미터가 누락되었습니다!");
                result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my");
                result.setRedirect(true);
                return result;
            }

            // 4. DTO 데이터 세팅 (setInternalId -> setMentoringNumber)
            long mentoringNumber = Long.parseLong(mentoringNumberStr);
            dto.setMentoringNumber(mentoringNumber);
            dto.setMentoringTitle(title);
            dto.setMentoringGoal(purpose);
            dto.setMentoringDetail(curriculum);
            
            if (subjectStr != null) {
                dto.setSubjectNumber(Integer.parseInt(subjectStr));
            }
            
            // 파일 처리 로직 (필요시 추가)
            if(fileName != null) {
                dto.setFileName(fileName);
            }

            // 5. DB 업데이트 실행
            System.out.println("[Log] DB Update 실행 시도 (번호: " + mentoringNumber + ")");
            dao.update(dto);
            System.out.println("[Log] DB Update 완료");

            // 6. 결과 페이지 설정 (상세보기로 리다이렉트)
            // 상세 조회 경로가 /mvc/auth/mentor/mentoringView.my 인지 확인 필요
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoringView.my?mentoringNumber=" + mentoringNumber);
            result.setRedirect(true);
            
        } catch (Exception e) {
            System.out.println("[Error] MentoringModifyOkController 예외 발생!");
            e.printStackTrace();
            // 에러 발생 시 마이페이지로 이동
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my");
            result.setRedirect(true);
        }

        return result;
    }
}