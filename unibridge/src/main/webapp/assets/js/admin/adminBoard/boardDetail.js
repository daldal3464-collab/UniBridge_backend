document.addEventListener("DOMContentLoaded", () => {

  const path = window.location.pathname;
  const isMentor = path.includes("mentorBoard");
  const boardType = sessionStorage.getItem("currentBoardType") || (isMentor ? "mentor" : "mentee");
 
  console.log(writeNumber);
  console.log(sessionNumber);
  
  const postActions = document.getElementById("postActions");
     if (Number(writeNumber) === Number(sessionNumber)){
      postActions.innerHTML = `
        <button class="btn btn-blue" id="btnEdit">수정</button>
        <button class="btn btn-red" id="btnDelete">삭제</button>
      `;
    } else {
      postActions.innerHTML = 
	  `<button class="btn btn-red" id="btnDelete">삭제</button>`;
    }
	

  postActions.addEventListener("click", e => {
    if (e.target.id === "btnEdit") {
      window.location.href = `${boardType}BoardEdit.admin?boardNumber=${boardNumber}`;
    }
    if (e.target.id === "btnDelete") {
      if (confirm("게시글을 삭제하시겠습니까?")) {
        window.location.href = `${boardType}BoardDeleteOk.admin?boardNumber=${boardNumber}`;
      }
    }
  });

  
/*  const commentDelBtn = document.querySelectorAll(".commentDelBtn");
  commentDelBtn.forEach((element)=>{
	const commentNumber = element.dataset.commentNumber;
	element.addEventListener("click", () => {
		
		if(confirm("댓글을 삭제하시겠습니까?")){
			window.location.href = `${boardType}BoardDeleteOk.admin?boardNumber=${boardNumber}&${boardType}ComNumber=${commentNumber}`;
		}
		
	})
  })*/
  
});
