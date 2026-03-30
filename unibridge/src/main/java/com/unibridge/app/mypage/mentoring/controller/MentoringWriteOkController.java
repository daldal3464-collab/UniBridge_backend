package com.unibridge.app.mypage.mentoring.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

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

		System.out.println("[Log] MentoringWriteOkController 시작");

		MentoringDAO mentoringDAO = new MentoringDAO();
		MentoringDTO mentoringDTO = new MentoringDTO();
		Result result = new Result();

		HttpSession session = request.getSession();
		Object loginUserObj = session.getAttribute("memberNumber");
		long mentorNumber = Long.parseLong(String.valueOf(loginUserObj != null ? loginUserObj : 21L));

		String uploadPath = request.getServletContext().getRealPath("/") + "upload/mentoring/";
		int fileSizeLimit = 1024 * 1024 * 10; // 10MB

		try {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSizeLimit, "UTF-8",
					new DefaultFileRenamePolicy());

			// 3. 파일 정보 추출 및 DTO 세팅
			Enumeration<String> files = multi.getFileNames();

			if (files.hasMoreElements()) {
			    String name = files.nextElement();
			    File file = multi.getFile(name);
			    
			    if (file != null) {
			        // 원본 파일명을 가져와서 세팅
			        String originalFileName = multi.getOriginalFileName(name); 
			        long size = file.length();
			        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			        
			        mentoringDTO.setFileOriginalName(originalFileName); //
			        mentoringDTO.setFileExtension(extension); //
			        mentoringDTO.setFileSize(size); //
			        
			        System.out.println("[Log] 파일 정보 추출 완료: " + originalFileName);
			    }
			}

			// 4. 일반 데이터 세팅 (여기서 다시 덮어쓰지 않도록 주의)
			mentoringDTO.setMentorNumber(mentorNumber); //
			mentoringDTO.setSubjectNumber(Integer.parseInt(multi.getParameter("mentoringSubject"))); //
			mentoringDTO.setMentoringTitle(multi.getParameter("mentoringTitle")); //
			mentoringDTO.setMentoringGoal(multi.getParameter("mentoringPurpose")); //
			mentoringDTO.setMentoringDetail(multi.getParameter("mentoringCurriculum")); //

			// 5. 중복 체크 및 DB Insert
			int existingCount = mentoringDAO.checkAlreadyExists(mentorNumber); //
			if (existingCount > 0) {
				result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my?error=already_exists");
				result.setRedirect(true);
				return result;
			}

			// DAO를 통해 DB 저장 실행
			mentoringDAO.insert(mentoringDTO); 

			// 성공 시 상세보기 페이지로 이동
			long createdNumber = mentoringDTO.getMentoringNumber();
			result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=view&mentoringNumber="
					+ createdNumber);
			result.setRedirect(true);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my");
			result.setRedirect(true);
			return result;
		}
	}
}