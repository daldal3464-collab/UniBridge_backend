document.querySelectorAll(".user-detail").forEach(e => {
	e.addEventListener("click", () => {
		const matchingNumber = e.dataset.matchingNumber;
		window.location.href = `${contextPath}/matchingDetail.admin?matchingNumber=${matchingNumber}`;
	});
});


document.querySelectorAll(".match-cancel-button").forEach(e => {
	e.addEventListener("click",() => {
		const matchingNumber = e.dataset.matchingNumber;
		if(confirm("매칭 취소 하시겠습니까?")){
			
			window.location.href = `${contextPath}/matchingDeleteOk.admin?matchingNumber=${matchingNumber}`;
		}
	})
})


