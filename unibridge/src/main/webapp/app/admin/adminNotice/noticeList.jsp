<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>공지사항 관리</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminNotice/noticeList.css" />

</head>
<body>

  <div id="header-wrap"></div>

  <div class="page-wrap">
    <h1 class="page-title">공지사항 관리</h1>

    <!-- 필터 행 -->
    <div class="filter-row">
      <!-- 왼쪽: 종류 -->
      <div class="filter-left">
       	<div class="type-wrap">
          <button class="btn-type" id="btnType">종류 ▼</button>
        <div id="typeDropdown">
		  <button class="board_type" onclick="location.href='${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=1&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType='">전체</button>
		  <button class="board_type" onclick="location.href='${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=1&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType=공지'">공지</button>
		  <button class="board_type" onclick="location.href='${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=1&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType=이벤트'">이벤트</button>
		</div>
        </div>
      </div>

      <!-- 오른쪽: 날짜 + 조회 + 등록 -->
      <div class="filter-right">
        <input type="date" class="input-date" id="dateFrom" value = "${sessionScope.dateFrom}">       
        <span class="tilde">~</span>
        <input type="date" class="input-date" id="dateTo" value = "${sessionScope.dateTo}" />
        <button class="btn" id="btnSearch">조회</button>
        <button class="btn btn-primary" id="btnWrite">+ 새 공지사항 등록</button>
      </div>
    </div>

    <!-- 테이블 -->
    <div class="notice-table">
      <div class="notice-table-head">
        <div class="col-no">번호</div>
        <div class="col-title">제목</div>
        <div class="col-date">작성일</div>
        <div class="col-views">조회수</div>
      </div>
      <div id="noticeTableBody">     
      <c:choose>
			<c:when test="${not empty boardList}">
		      <c:forEach var="board" items="${boardList}">
		          <a class="notice-table-row" href="${pageContext.request.contextPath}/app/admin/adminNotice/noticeDetail.admin?boardNumber=${board.noticeboardNumber}" >
		             <div class="col col-no">
		                <c:out value="${board.getNoticeboardNumber()}" />
		             </div>
		             <div class="col col-title">
		                <div>
		                   <c:out value="${board.boardType}) ${board.getBoardTitle()}" />
		                </div>
		             </div>
		             <div class="col col-date text-muted">
		                <c:out value="${board.getBoardDate() }" />
		             </div>
		             <div class="col col-views text-muted">
		                <c:out value="${board.getBoardClick() }" />
		             </div>
		          </a>
		       </c:forEach>
		   </c:when>
		   <c:otherwise>
		      <div>
		         <div style="text-align:center; padding:40px; color:#aaa; font-size:16px;">조회된 공지사항이 없습니다.</div>
		      </div>
		   </c:otherwise>
		</c:choose>
      
    
      
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination" id="pagination">
    <c:if test="${prev}">
      	<button onclick= 'location.href = "${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=${startPage - 1}&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType=${boardType}"' class="page-btn page-btn-nav">&lsaquo;</button>
      </c:if>
      <c:set var="realStartPage" value="${startPage < 0 ? 0:startPage}" />
      <c:forEach var="i" begin="${realStartPage}" end="${endPage}">
      	<c:choose>
      		<c:when test="${!(i == page)}">
      			<button class="page-btn" onclick='location.href = "${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=${i}&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType=${boardType}"' >
      				<c:out value="${i}" />
      			</button>
      		</c:when>
      		<c:otherwise>
      			<button onclick="#" class="page-btn" class="is-active">
      				<c:out value="${i}" />
      			</button>
      		</c:otherwise>
      	</c:choose>
      </c:forEach>
      <c:if test="${next}">
      	<button onclick='location.href ="${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.admin?page=${endPage + 1}&dateFrom=${dateFrom}&dateTo=${dateTo}&boardType=${boardType}"' class="page-btn page-btn-nav">&gt;</button>
      </c:if>
    
    
    </div>
  </div>

  <script>
  fetch("${pageContext.request.contextPath}/header/adminHeader.jsp")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "${pageContext.request.contextPath}/header/adminHeader.js";
      document.body.appendChild(s);
    });
  </script>

	<script>
	contextPath = "${pageContext.request.contextPath}";
	</script>
	<script src="${pageContext.request.contextPath}/assets/js/admin/adminNotice/noticeList.js"></script>
</body>
</html>
