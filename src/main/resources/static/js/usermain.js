let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	let id = $('#loginId').text();
	console.log("id:" + id)

	function generateMyStudyHtml(study) {
		return `<tr>
			<td><a href="#" class="btn-link">${study.title}</a></td>
			<td>${study.leaderId}</td>
			<td><span class="text-body"><i class="demo-pli-clock"></i>${study.studyperiod}</span></td>
			<td class="text-center fs-5">
				<div class="progress">
					<div class="progress-bar" role="progressbar" style="width: 81%;" aria-valuenow="81"
						 aria-valuemin="0" aria-valuemax="100"><font style="vertical-align: inherit;"><font
						style="vertical-align: inherit;">${study.status}%</font></font></div>
				</div>
			</td>
			<td>${study.challengestart}</td>
			<td>${study.challengeend}</td>
		</tr>`

	}
	loadMyStudyList();

	function loadMyStudyList() {

		$.ajax({
			url: '/user/user-info',
			method: 'post',
			data: {
				id: id,
			},
			cache: false,
			beforeSend: function (xhr) {
				if (header && token) {
					xhr.setRequestHeader(header, token);
				}
			},
			success: function (response) {
				if (response.studyBoardCount > 0) {
					$('#studylist').empty();
					let output = '';
					response.forEach(function (study) {
						output += generateMyStudyHtml(study);
					});

				} else {
					let sentence = '가입된 스터디가 없습니다. '
					$('#studylist').html(sentence)
				}
			},
			error: function (status, error) {
				console.log("ajax 요청 실패")
				console.log("상태:" + status)
				console.log("오류:" + error)
			}
		})
	}
})
