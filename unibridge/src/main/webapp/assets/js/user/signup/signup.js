
// 상태 플래그
let idChecked = false;
let pwValid = false;
let nicknameChecked = false;
let authVerified = false;

// ── label 텍스트로 input 찾기 ──
function getInputByLabel(labelText) {
  const form = document.getElementById("signUpForm");
  if (!form) return null;
  for (const group of form.querySelectorAll(".inputGroup")) {
    const label = group.querySelector("label");
    if (label && label.textContent.trim() === labelText) {
      return group.querySelector("input");
    }
  }
  return null;
}

function getButtonByLabel(labelText) {
  const form = document.getElementById("signUpForm");
  if (!form) return null;
  for (const group of form.querySelectorAll(".inputGroup")) {
    const label = group.querySelector("label");
    if (label && label.textContent.trim() === labelText) {
      return group.querySelector("button");
    }
  }
  return null;
}

// ── 오류 메시지 표시 ──
function showError(inputEl, message) {
  const group = inputEl.closest(".inputGroup");
  let errorEl = group.querySelector(".errorMsg");

  if (!errorEl) {
    errorEl = document.createElement("div");
    errorEl.classList.add("errorMsg");
    errorEl.style.cssText = `
      color: red;
      font-size: 12px;
      margin-top: 1px;
      display: block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      flex-basis: 100%;
      order: 99;
    `;
    group.appendChild(errorEl);
  }

  errorEl.style.maxWidth = inputEl.offsetWidth + "px";
  errorEl.style.width = inputEl.offsetWidth + "px";
  errorEl.textContent = message;

  updateSignUpBtn();
}

function showConfirm(inputEl, message) {
	const group = inputEl.closest(".inputGroup");
	let confirmMsgEl = group.querySelector(".confirmMsg");

	if (!confirmMsgEl) {
	  confirmMsgEl = document.createElement("div");
	  confirmMsgEl.classList.add("confirmMsg");
	  confirmMsgEl.style.cssText = `
	    color: #6495ed;
	    font-size: 12px;
	    margin-top: 1px;
	    display: block;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    flex-basis: 100%;
	    order: 99;
	  `;
	  group.appendChild(confirmMsgEl);
	}

	confirmMsgEl.style.maxWidth = inputEl.offsetWidth + "px";
	confirmMsgEl.style.width = inputEl.offsetWidth + "px";
	confirmMsgEl.textContent = message;

	updateSignUpBtn();
}

function clearAll(inputEl) {
	clearError(inputEl);
	clearConfirm(inputEl);	
}

// ── 오류 메시지 제거 ──
function clearError(inputEl) {
  const group = inputEl.closest(".inputGroup");
  const errorEl = group ? group.querySelector(".errorMsg") : null;
  if (errorEl) errorEl.textContent = "";
  updateSignUpBtn();
}

// ── Confirm 메시지 제거 ──
function clearConfirm(inputEl) {
  const group = inputEl.closest(".inputGroup");
  const confirmMsgEl = group ? group.querySelector(".confirmMsg") : null;
  if (confirmMsgEl) confirmMsgEl.textContent = "";
  updateSignUpBtn();
}

// ── 모든 필드 입력 여부 확인 ──
function checkAllFilled() {
  const inputs = ["아이디", "비밀번호", "비밀번호 확인", "이름", "닉네임", "전화번호", "인증번호"];
  return inputs.every(label => {
    const el = getInputByLabel(label);
    return el && el.value.trim() !== "";
  });
}

// ── 회원가입 버튼 활성/비활성 판단 ──
function updateSignUpBtn() {
  const signUpBtn = document.querySelector(".signUpBtn");
  if (!signUpBtn) return;

  const hasError = Array.from(document.querySelectorAll(".errorMsg"))
    .some(el => el.textContent.trim() !== "");

  const allFilled = checkAllFilled();

  const canSubmit =
    !hasError &&
    allFilled &&
    idChecked &&
    pwValid &&
    nicknameChecked &&
    authVerified;

  signUpBtn.disabled = !canSubmit;
  signUpBtn.style.opacity = canSubmit ? "1" : "0.5";
  signUpBtn.style.cursor = canSubmit ? "pointer" : "not-allowed";
}

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("signUpForm");
  const signUpBtn = form.querySelector(".signUpBtn");

  if (signUpBtn) {
    signUpBtn.disabled = true;
    signUpBtn.style.opacity = "0.5";
    signUpBtn.style.cursor = "not-allowed";
  }

  const idInput         = getInputByLabel("아이디");
  const pwInput         = getInputByLabel("비밀번호");
  const pwConfirmInput  = getInputByLabel("비밀번호 확인");
  const nameInput       = getInputByLabel("이름");
  const nicknameInput   = getInputByLabel("닉네임");
  const phoneInput      = getInputByLabel("전화번호");
  const authInput       = getInputByLabel("인증번호");

  const idDupBtn        = getButtonByLabel("아이디");
  const nicknameDupBtn  = getButtonByLabel("닉네임");
  const phoneSendBtn    = getButtonByLabel("전화번호");
  const authCheckBtn    = getButtonByLabel("인증번호");

  if (nameInput) {
    nameInput.addEventListener("input", () => updateSignUpBtn());
  }

  // 아이디 중복확인
  if (idDupBtn && idInput) {
    idDupBtn.addEventListener("click", async (e) => {
      e.preventDefault();
      const val = idInput.value.trim();
      if (!val) {
		clearAll(idInput);
        showError(idInput, "아이디를 입력해주세요.");
        idChecked = false;
		updateSignUpBtn();
		return;
      } 
	  
	  const resp = await fetch(`${window.contextPath}/api/user/signin/checkDupID.mem?memberId=${val}`, { method: "GET"} );
	  const isOk = await resp.json();
	  if (isOk.status === "ok") {
		clearAll(idInput);
		showConfirm(idInput, "검증이 완료되었습니다.");
		idChecked = true;
	  } else {
		clearAll(idInput);
        showError(idInput, "중복 아이디가 있습니다.");
        idChecked = false;
      }
	  
      updateSignUpBtn();
    });

    idInput.addEventListener("input", () => {
      idChecked = false;
      clearError(idInput);
    });
  }

  // 비밀번호 확인
  if (pwInput && pwConfirmInput) {
    pwInput.addEventListener("input", () => {
      if (pwConfirmInput.value) {
        if (pwInput.value !== pwConfirmInput.value) {
          showError(pwConfirmInput, "비밀번호가 일치하지 않습니다.");
          pwValid = false;
        } else {
          clearError(pwConfirmInput);
          pwValid = true;
        }
      } else {
        pwValid = false;
        updateSignUpBtn();
      }
    });

    pwConfirmInput.addEventListener("input", () => {
      if (!pwConfirmInput.value) {
        clearError(pwConfirmInput);
        pwValid = false;
      } else if (pwInput.value !== pwConfirmInput.value) {
        showError(pwConfirmInput, "비밀번호가 일치하지 않습니다.");
        pwValid = false;
      } else {
        clearError(pwConfirmInput);
        pwValid = true;
      }
    });
  }

  // 닉네임 중복확인
  if (nicknameDupBtn && nicknameInput) {
    nicknameDupBtn.addEventListener("click", async (e) => {
      e.preventDefault();
	  clearAll(nicknameInput);
	  
      const val = nicknameInput.value.trim();
      if (!val) {
        showError(nicknameInput, "닉네임을 입력해주세요.");
        nicknameChecked = false;
		updateSignUpBtn();
		return;
      } 
	  
	  const resp = await fetch(`${window.contextPath}/api/user/signin/checkDupNickname.mem?memberNickname=${val}`, { method: "GET"} );
	  const isOk = await resp.json();
	  if (isOk.status === "ok") {
		showConfirm(nicknameInput, "검증이 완료되었습니다.");
		nicknameChecked = true;
	  } else {
	  	showError(nicknameInput, "중복된 닉네임이 있습니다.");
	    nicknameChecked = false;
	  }
	  
      updateSignUpBtn();
    });

    nicknameInput.addEventListener("input", () => {
      nicknameChecked = false;
      clearError(nicknameInput);
    });
  }

  	// 인증번호 발송
    if (phoneSendBtn && phoneInput) {
      phoneSendBtn.addEventListener("click", async (e) => { // async 추가
        e.preventDefault();
        const val = phoneInput.value.trim();
        
        if (!val || !/^010\d{8}$/.test(val)) {
          showError(phoneInput, "올바른 전화번호를 입력해주세요.");
          return;
        }
        
        // 서버로 발송 요청 (SignupController의 mode=send 호출)
        const resp = await fetch(`${window.contextPath}/signup.mem?mode=send&userPhone=${val}`, { method: "POST" });
        const result = await resp.text();

        if (result === "success") {
          clearAll(phoneInput);
          showConfirm(phoneInput, "인증번호가 발송되었습니다.");
          if(authInput) authInput.focus();
        } else {
          showError(phoneInput, "발송에 실패했습니다. 다시 시도해주세요.");
        }
      });

      phoneInput.addEventListener("input", () => {
        clearError(phoneInput);
        authVerified = false; // 번호 바뀌면 인증 다시해야함
        updateSignUpBtn();
      });
    }

    // [수정] 인증번호 확인
    if (authCheckBtn && authInput) {
      authCheckBtn.addEventListener("click", async (e) => { // async 추가
        e.preventDefault();
        clearAll(authInput);
  	  
        const code = authInput.value.trim();
        if(!code) {
          showError(authInput, "인증번호를 입력해주세요.");
          return;
        }

        // 서버로 검증 요청 (SignupController의 mode=check 호출)
        const resp = await fetch(`${window.contextPath}/signup.mem?mode=check`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ authCode: code })
        });
        const result = await resp.text();

        if (result === "verified") {
          showConfirm(authInput, "인증이 완료되었습니다.");
          authVerified = true;
          // 인증 완료 시 수정 불가하게 고정
          phoneInput.readOnly = true;
          authInput.readOnly = true;
          phoneSendBtn.disabled = true;
          authCheckBtn.disabled = true;
        } else {
          showError(authInput, "인증번호가 일치하지 않거나 만료되었습니다.");
          authVerified = false;
        }
        updateSignUpBtn();
      });

      authInput.addEventListener("input", () => {
        authVerified = false;
        clearError(authInput);
        updateSignUpBtn();
      });
    }

/*  // 회원가입 전송
  if (form) {
    form.addEventListener("submit", (e) => {
      e.preventDefault();
      if (!signUpBtn.disabled) {
        window.location.href = '/frontend/main.html';
      }
    });
  }*/

  // 리사이즈 시 에러 너비 조정
  window.addEventListener("resize", () => {
    document.querySelectorAll(".errorMsg").forEach(el => {
      const group = el.closest(".inputGroup");
      if (!group) return;
      const input = group.querySelector("input");
      if (input) {
        el.style.maxWidth = input.offsetWidth + "px";
        el.style.width = input.offsetWidth + "px";
      }
    });
  });
});