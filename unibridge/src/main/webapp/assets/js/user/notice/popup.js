/**
 * 
 */
import { createDetailPopup, createModifyPopup, createWritePopup } from "./popupUtils.js"

// 상세보기 버튼 클릭 시 팝업 생성
const detailButton = document.querySelectorAll(
    ".list-container__inner " + 
    ".list-content-container " + 
    ".list-content " + 
    ".list-content__body " + 
    ".list-content__body-content " + 
    ".list-learning-content " + 
    ".actions " + 
    ".detail"
);
detailButton.forEach(function(item) {
    item.addEventListener('click', (e) => createDetailPopup(e));
})

// 수정 버튼 클릭 시 팝업 생성
const modifyButtons = document.querySelectorAll(
    ".list-container__inner " + 
    ".list-content-container " + 
    ".list-content " + 
    ".list-content__body " + 
    ".list-content__body-content " + 
    ".list-learning-content " + 
    ".actions " + 
    ".modify"
);
modifyButtons.forEach(function(item) {
    item.addEventListener('click', createModifyPopup);
})

const writeButton = document.querySelector(
    ".main-container " +
    ".filter-container " +
    ".filter-container__inner " +
    ".filter-container__inner__right " +
    ".filter-create-container"
);
writeButton.addEventListener('click', createWritePopup);

