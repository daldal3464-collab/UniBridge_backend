<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>공지사항 관리 - 작성</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminNotice/noticeWrite.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="form-wrap">
    <h1 class="page-title">공지사항 관리</h1>
    <br>

    <form id="writeForm" method="post" action ="${pageContext.request.contextPath}/noticeWriteOk.admin" enctype="multipart/form-data">
	
	<div class="form-section-title">
    	<span>공지사항 작성</span>
		<select class="form-section-type" name="boardType">
    		<option value = "공지">공지</option>
    		<option value = "이벤트">이벤트</option>
    	</select>
	</div>

    <!-- 제목 -->
    <input
      type="text"
      id="inputTitle"
      class="form-title-input-wide"
      placeholder="제목을 입력해주세요*(최대 50자)"
      maxlength="50"
      name = "boardTitle"/>

    <!-- 내용 -->
    <textarea
      id="inputContent"
      class="form-content-area"
      name = boardContent></textarea>

    <!-- 파일 첨부 -->
    <div class="form-file-section">
      <span class="form-file-label-text">파일 첨부</span>
      <input type="file" id="fileInput" name="file"/>
    </div>

    <!-- 버튼 -->
    <div class="form-actions">
      <button type="submit" class="btn btn-primary" id="btnSubmit">등록</button>
      <button type="button" class="btn btn-primary" id="btnCancel">취소</button>
    </div>
  </form>
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
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminNotice/noticeWrite.js"></script>
  
  <script>
  const contextPath =  "${pageContext.request.contextPath}";
  </script>
  
</body>
</html>
