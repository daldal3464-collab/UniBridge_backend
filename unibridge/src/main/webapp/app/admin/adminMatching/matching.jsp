<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminMatching/matching.css">
  <title>Document</title>
</head>
<body>
  <div id="root">
    <div class="root-container">
      <div id="header-wrap"></div>
      <div class="content-container">
        <div class="title-container">
          <!-- <span>취소 신청 내역</span> -->
        </div>
        <div class="matching-user-container">
          <div class="matching-user-container__inner">
            <div class="matching-user-header-container">
<!--               <div class="all">전체</div>
              <div class="pending-cancel">취소 신청 중</div>
              <div class="cancel">취소 완료</div> -->
              <span>취소 신청 내역</span>
            </div>
            <div class="vertical-line"></div>
            <div class="list-desc-container">
              <span>총 ${total}건</span>
            </div>
            <ul class="list-user-container">
            
		<c:choose>
			<c:when test="${not empty matchingList}">
				<c:forEach var="matching" items="${matchingList}">
				<li class="list-user-content">
	                <div class="user-title-container">
	                  <span class="user-title-date">
	                  	<c:out value ="${matching.matchingStart}" />
	                  </span>
	                  
	                  <span class="user-detail">매칭 상세</span>
	                </div>
	                
	                <div class="user-content-container">
	                  <div class="status">
	                  	<c:out value="${matching.state}" />
	                  </div>
	                  
	                  <div class="vertical-line-container">
	                    <div class="vertical-line"></div>
	                  </div>
	                  
	                  <div class="match-container">
	                    <div class="icon"><img src = "${pageContext.request.contextPath}/assets/img/admin/matchingIcon.png"></div>
	                    
	                    <div class="desc-container">
	                      <div class="mentor-to-mentee">
	                        <div class="mentee">멘티</div>
	                        <span class="arrow">→</span>
	                        <div class="mentor">멘토</div>
	                      </div>
	                      
	                      <div class="mentor-to-mentee-name">
	                        <div class="mentee"><c:out value="${matching.mentorName}" /></div>
	                        <span class="arrow">→</span>
	                        <div class="mentor"><c:out value="${matching.menteeName}" /></div>
	                      </div>
	                      <div class="start-date">매칭 시작 <span><c:out value="${matching.matchingStart}" /></span></div>
	                    </div>
	                  </div>
	                  <div class="vertical-line-container">
	                    <div class="vertical-line"></div>
	                  </div>
	                  <div class="button-container">
	                    <div class="match-cancel-button">매칭 취소</div>
	                  </div>
	                </div>
	              </li>
		       </c:forEach>
		   </c:when>
		</c:choose>
            </ul>
          </div>
        </div>
      </div>
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
</body>
</html>