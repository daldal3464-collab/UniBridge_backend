<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>멘토링 조회 - UniBridge</title>

<%-- CSS 경로 --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/fonts.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user/mentor/myPage/userMentoring/mentoringForm.css">

<%-- JS 로드 --%>
<script defer
	src="${pageContext.request.contextPath}/assets/js/user/mentor/myPage/userMentoCreate/mentoringView.js"></script>

</head>
<body>
	<jsp:include page="/app/user/header.jsp" />

	<div class="mainContainer">
		<aside>
			<div class="myPageTitle">마이페이지</div>
			<ul>
				<li><a
					href="${pageContext.request.contextPath}/mvc/auth/mentor/myPage.my">계정
						관리</a></li>
				<li><a
					href="${pageContext.request.contextPath}/mvc/auth/mentor/survey.my">설문
						조사</a></li>
				<li><a
					href="${pageContext.request.contextPath}/mvc/auth/mentor/matching.my">매칭
						정보</a></li>
				<li><a
					href="${pageContext.request.contextPath}/mvc/auth/mentor/mentoring.my"
					class="active">멘토링</a></li>
				<li><a
					href="${pageContext.request.contextPath}/mvc/auth/mentor/app/delete.my">회원
						탈퇴</a></li>
			</ul>
		</aside>

		<main>
			<div class="mentoringContainer">
				<%-- Modify와 동일한 제목 및 구분선 구조 --%>
				<div class="userManageTitle_View">
					<img
						src="${pageContext.request.contextPath}/assets/img/user/userMyPageImg/userMentoring.jpg"
						alt="아이콘"> <span class="mentoringTitle">멘토링 관리</span>
				</div>
				<hr class="titleLine">

				<div id="contentsMain">
					<div id="mentoringBackground">
						<div id="mentoringMain">
							<div id="mentoring">
								<div id="mentoringTopics">
									<div class="title">
										<span>학습 주제 및 목표</span>
									</div>
									<div class="subject">
										<label for="mentoringSubject">학습 과목 번호</label> <input
											type="text" id="mentoringSubject"
											value="${mentoring.subjectNumber}" readonly>
									</div>

									<div class="subject">
										<label for="mentoringTitle">멘토링 주제</label> <input type="text"
											id="mentoringTitle" value="${mentoring.mentoringTitle}"
											readonly>
									</div>

									<div id="purpose">
										<label for="mentoringPurpose">멘토링 목적</label>
										<textarea id="mentoringPurpose" readonly>${mentoring.mentoringGoal}</textarea>
									</div>
								</div>

								<div id="curriculum">
									<div id="text">
										<div>
											<label for="mentoringCurriculum">멘토링 커리큘럼 상세</label>
										</div>
										<textarea id="mentoringCurriculum" readonly>${mentoring.mentoringDetail}</textarea>
									</div>
									<div id="file">
										<div id="curriculumFileTitle">첨부 파일</div>
										<div id="curriculumFile">
											<c:choose>
												<c:when test="${not empty mentoring.fileOriginalName}">
													<a
														href="${pageContext.request.contextPath}/download.file?fileName=${mentoring.fileOriginalName}"
														style="color: #4f73e3; font-size: 13px;">
														${mentoring.fileOriginalName} </a>
												</c:when>
												<c:otherwise>
													<span style="font-size: 12px; color: #888;">첨부된 파일이
														없습니다.</span>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</div>

						</div>

						<%-- 하단 버튼: Modify로 이동하거나 삭제 실행 --%>
						<%-- 수정된 버튼 영역 --%>
						<div id="buttons">
							<%-- 1. type=modify 파라미터를 명확히 전달 --%>
							<button type="button" id="modBtn"
								onclick="location.href='${pageContext.request.contextPath}/mvc/auth/mentor/mentoring.my?type=modify&mentoringNumber=${mentoring.mentoringNumber}'">
								수정</button>

							<button type="button" id="delBtn"
								onclick="if(confirm('정말로 삭제하시겠습니까?')) { 
            location.href='${pageContext.request.contextPath}/mvc/auth/mentor/mentoring.my?type=deleteOk&mentoringNumber=${mentoring.mentoringNumber}'; 
        }">
								삭제</button>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>

	<footer>
		<jsp:include page="/app/user/footer.jsp" />
	</footer>
</body>
</html>