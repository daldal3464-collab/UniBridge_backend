<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminMatching/matchingDetail.css">
    <script defer src = "${pageContext.request.contextPath}/assets/js/admin/adminMatching/matchingCancel.js"></script>
  
    <title>Document</title>
</head>
<body>
  <div id="root">
    <div class="root-container">
      <div id="header-wrap"></div>
      <div class="content-container">
        <div class="title-container">
          <span>매칭 취소 상세</span>
        </div>
        <div class="matching-user-container">
          <div class="matching-user-container__inner">
            <div class="matching-date">매칭 시작일 : ${matchingCancel.matchingStart}</div>
            <div class="vertical-line"></div>
            <div class="match-config-container">
              <div class="status">${matchingCancel.matchingState}</div>
              <div class="vertical-line"></div>
              <div class="match-config-content">
                <div class="mentee">
                  <div class="mentee-icon"><img src="${pageContext.request.contextPath}/assets/img/admin/matchingDetailIcon.png"></div>
                  <div class="mentee-title">멘티</div>
                  <div class="mentee-title-name">${matchingCancel.menteeName}</div>
                </div>
                <span class="arrow"><img src="${pageContext.request.contextPath}/assets/img/admin/matchingDetailArrow.png"></span>
                <div class="mentor">
                  <div class="mentor-icon"><img src="${pageContext.request.contextPath}/assets/img/admin/matchingDetailIcon.png"></div>
                  <div class="mentor-title">멘토</div>
                  <div class="mentor-title-name">${matchingCancel.mentorName}</div>
                </div>
              </div>
            </div>
            <div class="match-config-detail-container">
              <div class="match-config-detail-title">취소 신청 정보</div>
              <div class="match-config-detail-content">
                <div class="match-config-detail-content__inner">
                  <div class="match-apply-date-container">
                    <div class="match-apply-date-title">신청 일시</div>
                    <div class="match-apply-date-content">${matchingCancel.matchingCancel}</div>
                  </div>
                  <div class="match-cancel-desc-container">
                    <div      class="match-cancel-desc-title">취소 사유</div>
                    <textarea class="match-cancel-desc-content" readonly>${matchingCancel.matchingCanReason}</textarea>
                  </div>
                  <div class="match-cancel-button-container">
                    <div class="match-cancel-button" data-matching-number="${matchingCancel.matchingNumber}">매칭 취소</div>
                  </div>
                </div>
              </div>
            </div>
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
  
  <script>
  const contextPath = "${pageContext.request.contextPath}";
  </script>
  
</body>
</html>