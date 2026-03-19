<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>게시판 관리 - 멘티</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminBoard/menteeBoard/boardList.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="page-wrap">
    <h1 class="page-title">게시판 관리</h1>

    <!-- 탭 -->
    <div class="board-tabs">
      <a id="tab-mentee" href="${pageContext.request.contextPath}/app/admin/adminBoard/menteeBoard/menteeBoardList.jsp" class="board-tab">멘티 게시판</a>
      <a id="tab-mentor" href="${pageContext.request.contextPath}/app/admin/adminBoard/mentorBoard/mentorBoardList.jsp" class="board-tab">멘토 게시판</a>
    </div>

    <!-- 필터 행 -->
    <div class="filter-row">
      <div class="filter-date">
        <input type="date" class="input-date" id="dateFrom" />
        <span class="tilde">~</span>
        <input type="date" class="input-date" id="dateTo" />
      </div>
      <button class="btn" id="btnSearch">조회</button>
      <button class="btn btn-primary" id="btnWrite">+ 게시글 작성</button>
    </div>

    <!-- 테이블 -->
    <div class="board-table">
      <div class="board-table-head">
        <div class="col-no">번호</div>
        <div class="col-title">제목</div>
        <div class="col-author">작성자</div>
        <div class="col-date">작성일</div>
        <div class="col-views">조회수</div>
      </div>
      <div id="boardTableBody"></div>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination" id="pagination"></div>
  </div>

<script>
  fetch("${pageContext.request.contextPath}/header/adminHeader.jsp")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "${pageContext.request.contextPath}/assets/js/header/adminHeader.js";
      document.body.appendChild(s);
    });
</script>
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminBoard/boardData.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminBoard/menteeBoard/boardList.js"></script>
</body>
</html>
