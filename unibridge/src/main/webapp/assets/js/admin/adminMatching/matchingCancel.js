document.querySelector(".match-cancel-button").addEventListener("click",(e) => {
		const matchingNumber = e.target.dataset.matchingNumber;
		if(confirm("매칭 취소 하시겠습니까?")){
			
			window.location.href = `${contextPath}/matchingDeleteOk.admin?matchingNumber=${matchingNumber}`;
		}
	})


document.querySelector(".match-list-button").addEventListener("click",()=>{
	window.location.href = `${contextPath}/matching.admin`;
})