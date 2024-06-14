let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {

	let id = $('#loginId').text();
	console.log("id:" + id)


	$.ajax({
		url: '/user/user-info',
		method: 'post',
		data: {
			id: id,
		},
		cache: false,
		beforeSend : function (xhr) {
			if (header && token) {
				xhr.setRequestHeader(header, token);
			}
		},
		success: function(response) {
			$(response).each(function () {
				let sentence = '';
				//console.log(response)
				if (response.studyBoardCount > 0) {
					let output = '';
					$('#studylist').html(output);
				} else {
					sentence += '가입된 스터디가 없습니다. '
					$('#studylist').html(sentence)
				}
			})
		},
		error: function (status, error) {
			console.log("ajax 요청 실패")
			console.log("상태:" + status)
			console.log("오류:" + error)
			alert("댓글 입력 과정 중 에러 발생했습니다. 다시 시도해주세요")
		}
	})
})
