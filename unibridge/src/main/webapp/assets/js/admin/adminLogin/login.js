const loginForm = document.getElementById("loginForm");
const adminId = document.getElementById("adminId");
const adminPw = document.getElementById("adminPw");
const errorText = document.getElementById("errorText");

loginForm.addEventListener("submit", function (event) {
  event.preventDefault();

  const idValue = adminId.value.trim();
  const pwValue = adminPw.value.trim();

 /* errorText.textContent = "";*/
  
  if (!idValue || !pwValue) {
    errorText.textContent = "아이디와 비밀번호를 입력해주세요.";
    return;
  }

	fetch("${pageContext.request.contextPath}/app/admin/loginOk.admin", {
	  method: "POST",
	  headers: {
	    "Content-Type": "application/x-www-form-urlencoded",
	  },
	  body: `adminId=${idValue}&adminPw=${pwValue}`,
	}).then(response => {
      if (!response.ok) {
          errorText.textContent = "아이디와 비밀번호가 맞지 않습니다.";
      }
  });
   /* window.location.href = "${pageContext.request.contextPath}/app/admin/adminMain/main.admin";*/


});