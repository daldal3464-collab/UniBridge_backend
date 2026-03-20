/**
 * 매칭 정보 관리 JavaScript
 * JSP의 동적 ID(matchingModal_번호)와 연동됩니다.
 */

// 1. 매칭 취소 모달 열기
function openCancelModal(matchNum) {
    console.log("모달 열기 시도 - 매칭번호: " + matchNum);
    const modal = document.getElementById('matchingModal_' + matchNum);
    
    if (modal) {
        // 배경을 포함한 모달 전체를 화면에 표시
        modal.style.display = "flex"; 
    } else {
        console.error("해당 모달을 찾을 수 없습니다: matchingModal_" + matchNum);
    }
}

// 2. 매칭 취소 모달 닫기
function closeCancelModal(matchNum) {
    const modal = document.getElementById('matchingModal_' + matchNum);
    if (modal) {
        modal.style.display = "none";
    }
}

// 3. 매칭 취소 신청 제출 처리
function submitCancel(matchNum) {
    const modal = document.getElementById('matchingModal_' + matchNum);
    const reasonArea = modal.querySelector('textarea[name="matchingCanReason"]');
    const reason = reasonArea ? reasonArea.value.trim() : "";

    // 1. 유효성 검사
    if (reason === "") {
        alert("취소 사유를 입력해주세요.");
        if(reasonArea) reasonArea.focus();
        return false; // HTML 폼 전송을 중단함
    }

    if (reason.length > 1024) {
        alert("취소 사유는 1024자를 초과할 수 없습니다.");
        return false; // HTML 폼 전송을 중단함
    }

    // 2. 최종 확인
    if (confirm("정말로 매칭을 취소 신청하시겠습니까?")) {
        // [중요] 여기서 별도로 form을 만들지 않습니다.
        // true를 반환하면 HTML에 작성된 <form>이 자동으로 제출됩니다.
        alert("매칭 취소 신청을 처리합니다.");
        return true; 
    }

    return false; // 취소 버튼 누를 시 전송 중단
}

// 4. 모달 바깥쪽(어두운 배경) 클릭 시 닫기 처리
window.onclick = function(event) {
    // 클릭한 대상의 클래스가 'matingCancel'(배경 레이어)이면 닫기
    if (event.target.classList.contains('matingCancel')) {
        event.target.style.display = "none";
    }
};