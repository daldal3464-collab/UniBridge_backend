/**
 * 
 */
const DUPLICATE_NICKNAMES = ["test"]; // 실제로는 서버에서 체크하게 됨
const VALID_AUTH_CODE = "12345"; //테스트 용 데이터

document.addEventListener("DOMContentLoaded", () => {
	// ── 요소 참조 ──
    const completeBtn = document.querySelector(".userModifyBtn");
    const profileImg = document.querySelector(".userImg > img");
    const photoBtn = document.querySelector("#imgBtn");
    const photoError = document.querySelector(".userImg .errorMsg");
    
    // JSP에서 contextPath를 가져오기 위한 설정 (없으면 기본값 "" 혹은 "/unibridge")
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)) || "";

    // 상태 변수
    let isNickChecked = false; 
    let isPhoneAuthSent = false; 
    let isPhoneVerified = false; 
    let isPhotoAttached = !!profileImg.getAttribute("src") && !profileImg.src.includes("ex1.png"); 

    // [공통 함수] 서버에 데이터 반영 (Ajax)
    const updateField = (type, value) => {
        fetch(`${contextPath}/auth/undecided/updateOk.my`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `type=${type}&value=${encodeURIComponent(value)}`
        })
        .then(res => res.ok ? alert(`${type} 변경이 완료되었습니다.`) : alert("변경 실패"))
        .catch(err => console.error("Error:", err));
    };

    // ──────────────────────────────────────
    // 1. 프로필 사진 관리
    // ──────────────────────────────────────
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*";

    photoBtn.addEventListener("click", () => fileInput.click());

    fileInput.addEventListener("change", (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                profileImg.src = event.target.result;
                isPhotoAttached = true;
                photoError.textContent = ""; 
            };
            reader.readAsDataURL(file);
        }
    });

    // ──────────────────────────────────────
    // 2. 닉네임 변경
    // ──────────────────────────────────────
	const nickGroup = document.querySelectorAll(".inputGroup")[2];
	const nickInput = nickGroup.querySelector("input");
	const nickChangeBtn = nickGroup.querySelectorAll("button")[1];
	
	nickChangeBtn.addEventListener("click", () => {
	    if (!isNickChecked) {
	        alert("중복 확인이 필요합니다.");
	    } else {
	        // 서버 반영
	        updateField("nickname", nickInput.value.trim());
	    }
	});

	// ──────────────────────────────────────
    // 3. 비밀번호 변경 (Ajax 연결)
    // ──────────────────────────────────────
    const pwInput = document.querySelectorAll(".inputGroup")[3].querySelector("input");
    const pwConfirmGroup = document.querySelectorAll(".inputGroup")[4];
    const pwChangeBtn = pwConfirmGroup.querySelectorAll("button")[1];

    let isPwMatched = false;

    pwChangeBtn.addEventListener("click", () => {
        if (!isPwMatched) {
            alert("비밀번호 확인이 필요합니다.");
        } else {
            // 서버 반영
            updateField("password", pwInput.value.trim());
        }
    });

	// ──────────────────────────────────────
    // 4. 전화번호 및 인증 (Ajax 연결)
    // ──────────────────────────────────────
    const phoneInput = document.querySelectorAll(".inputGroup")[5].querySelector("input");
    const authGroup = document.querySelectorAll(".inputGroup")[6];
    const phoneChangeBtn = authGroup.querySelectorAll("button")[1];

    phoneChangeBtn.addEventListener("click", () => {
        if (!isPhoneVerified) {
            alert("인증번호 확인 절차가 필요합니다.");
        } else {
            // 서버 반영
            updateField("phone", phoneInput.value.trim());
        }
    });

	phoneChangeBtn.addEventListener("click", () => {
	    if (!isPhoneVerified) {
	        authError.style.color = "red";
	        authError.textContent = "인증번호 확인 절차가 필요합니다.";
	    } else {
	        alert("전화번호가 성공적으로 변경되었습니다.");
	        authError.textContent = "";
	    }
	});

	// ──────────────────────────────────────
    // 5. 성별 변경 (Ajax 연결)
    // ──────────────────────────────────────
    const genderGroup = document.querySelectorAll(".inputGroup")[7];
    const genderChangeBtn = genderGroup.querySelector(".change"); // 클래스명 확인필요

    genderChangeBtn.addEventListener("click", () => {
        const selectedGender = genderGroup.querySelector('input[name="role"]:checked');
        if (selectedGender) {
            // 서버 반영 (value: m, f, none)
            updateField("gender", selectedGender.value);
        }
    });

	// ──────────────────────────────────────
    // 6. 완료 버튼 (FrontController의 finishUpdate.my 호출)
    // ──────────────────────────────────────
    completeBtn.addEventListener("click", (e) => {
        if (!isPhotoAttached) {
            photoError.style.color = "red";
            photoError.textContent = "사진 첨부는 필수입니다.";
            window.scrollTo({ top: 0, behavior: 'smooth' });
            return;
        }

        // 수정 완료: FrontController를 거쳐 마이페이지 메인으로 이동
        // 정적 HTML 이동이 아닌 .my 커맨드를 호출합니다.
        window.location.href = `${contextPath}/auth/undecided/finishUpdate.my`;
    });
});