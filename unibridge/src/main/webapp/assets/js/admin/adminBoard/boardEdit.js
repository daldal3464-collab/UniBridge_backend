document.addEventListener("DOMContentLoaded", () => {

  const path = window.location.pathname;
  const isMentor = path.includes("mentorBoard");
  const boardType = sessionStorage.getItem("currentBoardType") || (isMentor ? "mentor" : "mentee");
  
  const editForm = document.getElementById("editForm");
    editForm.addEventListener("submit", (e) => {
	  e.preventDefault();
      const title = document.getElementById("inputTitle").value.trim();
      const content = document.getElementById("inputContent").value.trim();
      if (!title) { alert("제목을 입력해주세요."); return; }
      if (!content) { alert("내용을 입력해주세요."); return; }
	  
	  editForm.submit();
    });
  


  const btnEditCancel = document.getElementById("btnEditCancel");
  const boardNumber = btnEditCancel.dataset.boardNumber;
    btnEditCancel.addEventListener("click", () => {
      if (confirm("게시글 수정을 취소하시겠습니까?")) {
        location.href = `${contextPath}/${boardType}BoardDetail.admin?boardNumber=${boardNumber}`;
      }
    });

});
