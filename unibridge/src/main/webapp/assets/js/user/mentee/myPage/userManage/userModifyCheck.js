document.addEventListener('DOMContentLoaded', () => {
    const sendBtn = document.querySelector('#sendSmsBtn');
    const verifyBtn = document.querySelector('#verifySmsBtn');
    const modifyForm = document.getElementById('goUserModify');
    const verifyFailMsg = document.getElementById('verifyFailMsg');
    
    let isAuthVerified = false; 

    // 1. 인증번호 전송
    sendBtn.addEventListener('click', () => {
        const phone = document.getElementById('phoneNum').value.trim();
        if (!/^010\d{8}$/.test(phone)) {
            alert("올바른 번호를 입력하세요."); return;
        }

        fetch('/unibridge/mvc/auth/mentee/verifyAction.my?mode=send', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `phoneNumber=${encodeURIComponent(phone)}`
        })
        .then(res => res.text())
        .then(data => {
            alert(data === "success" ? "인증번호가 발송되었습니다." : "발송 실패.");
        });
    });

    // 2. 인증번호 확인 (AJAX)
    verifyBtn.addEventListener('click', () => {
        const code = document.getElementById('authNum').value.trim();

        fetch('/unibridge/mvc/auth/mentee/verifyAction.my?mode=check', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ authCode: code })
        })
        .then(res => res.text())
        .then(data => {
            if(data.trim() === "verified") {
                alert("인증 성공!");
                isAuthVerified = true;
                verifyFailMsg.style.display = 'none';
                verifyBtn.disabled = true;
            } else {
                verifyFailMsg.style.display = 'block';
            }
        });
    });

    // 3. 최종 제출 전 체크
    modifyForm.addEventListener('submit', (e) => {
        if (!isAuthVerified) {
            e.preventDefault();
            alert('휴대폰 인증을 먼저 완료해주세요.');
        }
    });
});