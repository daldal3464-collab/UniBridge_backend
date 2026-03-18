/**
 * mentoringView.js 
 */

// 삭제 버튼 클릭 시
function deleteMentoring(mentoringNumber) {
    if(confirm("정말로 이 멘토링을 삭제하시겠습니까? 관련 데이터가 모두 삭제됩니다.")) {
        // 컨트롤러의 삭제 경로로 이동
        location.href = contextPath + "/mentoringDeleteOk.mo?mentoringNumber=" + mentoringNumber;
    }
}

function goToModify(mentoringNumber) {
    location.href = contextPath + "/mentoringModify.mo?mentoringNumber=" + mentoringNumber;
}

// 입력 폼 유효성 검사 (Create/Modify 공통)
const validateForm = () => {
    const title = document.querySelector('input[name="mentoringTitle"]');
    if (title.value.trim() === "") {
        alert("제목을 입력해주세요.");
        title.focus();
        return false;
    }
    return true;
};

document.addEventListener("DOMContentLoaded", function() {
    const delBtn = document.getElementById("delBtn");
    const modifyBtn = document.querySelector(".btn-modify"); // 수정 버튼 클래스명 확인 필요

    // 삭제 버튼 클릭 시
    if(delBtn) {
        delBtn.addEventListener("click", function() {
            // JSP에서 넘겨준 ID값을 가져오거나 함수 호출 방식 사용
            // 예시: deleteMentoring('${mentoring.id}');
        });
    }
});



