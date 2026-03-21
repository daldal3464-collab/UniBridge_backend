document.addEventListener('DOMContentLoaded', () => {
    const sendBtn = document.getElementById('sendSms');
    const verifyBtn = document.getElementById('verifySms');
    const submitBtn = document.getElementById('submitBtn');
    const phoneInput = document.getElementById('userPhone');
    const authInput = document.getElementById('authCode');

    // 인증번호 전송 클릭 시
    sendBtn.addEventListener('click', () => {
        const phone = phoneInput.value.trim();
        
        if (!/^010\d{8}$/.test(phone)) {
            alert("하이픈(-) 없이 숫자 11자리를 입력해주세요.");
            return;
        }

        // 컨테이너 경로 확인 (JSP 상단에 선언한 contextPath 사용)
        fetch(`${contextPath}/mvc/auth/mentee/delete.my?mode=send`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `phoneNumber=${encodeURIComponent(phone)}`
        })
        .then(res => res.text())
        .then(data => {
            if (data.trim() === "success") {
                // 전송 성공 시 알람
                alert("인증번호가 발송되었습니다. 콘솔창 혹은 휴대폰을 확인해주세요.");
            } else {
                // 전송 실패 시 알람 (잔액 부족 등)
                alert("인증번호 발송에 실패하였습니다. 다시 시도해주세요.");
            }
        })
        .catch(err => console.error("Error:", err));
    });

    // 인증 확인 클릭 시
	verifyBtn.addEventListener('click', () => {
	    const code = authInput.value.trim();
	    const authErrorArea = document.getElementById('authError'); // 에러 메시지 출력 영역

	    fetch(`${contextPath}/mvc/auth/mentee/delete.my?mode=check`, {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify({ authCode: code })
	    })
	    .then(res => res.text())
	    .then(data => {
	        if (data.trim() === "verified") {
	            alert("인증에 성공하였습니다!"); // 요구사항: 기존 성공 메시지 유지
	            authErrorArea.innerText = "";    // 에러 메시지 초기화
	            submitBtn.disabled = false;
	            verifyBtn.disabled = true;
	            authInput.readOnly = true;
	        } else {
	            // 요구사항: "인증번호가 일치하지 않습니다." 출력
	            authErrorArea.innerText = "인증번호가 일치하지 않습니다.";
	        }
	    });
	});
});