// header.js
(function () {
  // 1. 컨텍스트 루트 추출 (페이지 이동 시 필요)
  const getBase = () => {
    const { origin, pathname } = window.location;
    const contextName = 'unibridge'; // 프로젝트명
    if (pathname.includes(`/${contextName}`)) {
      return origin + `/${contextName}`;
    }
    return origin;
  };

  const base = getBase();
  console.log("UniBridge Header JS Loaded. Base Path:", base);

  // 2. 초기화 함수 (이벤트 바인딩만 담당)
  function initHeaderEvents() {
    // JSP(서버)에서 이미 그려진 로그아웃 버튼을 찾습니다.
    // HTML에 class="logout"이 있는지 확인하세요.
    const logoutBtn = document.querySelector('.headerBtnText.logout');

    if (logoutBtn) {
      logoutBtn.addEventListener('click', (e) => {
        e.preventDefault();
        
        if (confirm('로그아웃 하시겠습니까?')) {
          // 서버의 로그아웃 서블릿으로 이동 (매핑에 맞게 .main 또는 .mem 수정)
          window.location.href = `${base}/logout.mem`; 
        }
      });
      console.log("Logout event bound successfully.");
    }
  }

  // DOM 로드 완료 시 실행
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initHeaderEvents);
  } else {
    initHeaderEvents();
  }
})();