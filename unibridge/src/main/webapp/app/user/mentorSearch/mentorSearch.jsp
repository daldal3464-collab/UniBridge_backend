<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>멘토 검색</title>
<link href="${pageContext.request.contextPath}/assets/css/fonts.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user/mentorSearch/mentorSearch.css">
<script>
    var globalContextPath = "${pageContext.request.contextPath}";
</script>
<script>
	var REAL_MENTORS = [
	    <c:forEach var="m" items="${mentorList}" varStatus="status">
	    {
	        id: "${m.memberNumber}",
	        name: "${m.memberName}",
	        university: "${m.gradSchool}",
	        major: "${m.gradDepart}",
	        img: "${not empty m.fileName ? m.fileName : ''}", 
	        date: "${m.createdAt}",
	        subject: "${m.subject}" 
	    }${not status.last ? ',' : ''}
	    </c:forEach>
	];
    var globalContextPath = "${pageContext.request.contextPath}";
    console.log("조회된 멘토 수:", REAL_MENTORS.length);
    console.log("데이터 확인:", REAL_MENTORS);
</script>
</head>
<body>
	<jsp:include page="/app/user/header.jsp" />

	<main>
		<div id="mentoTitle">멘토</div>
		<div id="category">직무 카테고리</div>
		<hr>
		<nav>
			<ul>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="전체" id="nowCategory">전체</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="자바">자바</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="파이썬">파이썬</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="국어">국어</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="수학">수학</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="C++">C++</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="C언어">C언어</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="영어">영어</a></li>
				<li><a href="javascript:void(0)" class="category-btn"
					data-name="게임">게임</a></li>
			</ul>
		</nav>

		<%-- JS가 멘토 카드를 그려넣을 영역 --%>
		<div class="contents"></div>

		<%-- JS가 페이지 번호를 생성할 영역 --%>
		<div id="pageNumber">
			<ul></ul>
		</div>
	</main>
	<jsp:include page="/app/user/footer.jsp" />
	<script
	src="${pageContext.request.contextPath}/assets/js/user/mentorSearch/mentorPagenation.js"></script>
</body>
</html>