let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	let id = $('#loginId').text();
	console.log("id:" + id)

	let params = new URLSearchParams(window.location.search);
	let page = params.get('page') || 1;
	let size = params.get('size') || 5;
	let sort = params.get('sort') || 'studystart';

	function generateMyStudyHtml(study) {
		let startDate = new Date(study.studystart);
		let endDate = new Date(study.studyend);
		let currentDate = new Date();
		let totalDuration = endDate - startDate;
		let elapsedDuration = currentDate - startDate;
		let progress = (elapsedDuration / totalDuration) * 100;

		// Ensure progress is between 0 and 100
		progress = Math.max(0, Math.min(100, progress));

		// studystatus 값에 따른 표시 텍스트 설정
		let statusText = study.studystatus === 'progress' ? '진행중' : (study.studystatus === 'end' ? '종료' : study.studystatus);

		// 카테고리 이름이 너무 길 경우 자르기
		let categoryName = study.categoryName;
		if (categoryName.length > 17) { // 원하는 길이로 설정
			categoryName = categoryName.substring(0, 17) + '...';
		}

		let studyTitle = study.title;
		if (studyTitle.length > 17) { // 원하는 길이로 설정
			studyTitle = studyTitle.substring(0, 17) + '...';
		}

		return `<tr>
			<td><a href="/study/detail?no=${study.no}" class="btn-link">${studyTitle}</a></td>
			<td>${study.leaderId}</td>
			<td><span class="text-body"><i class="demo-pli-clock"></i>&nbsp;${statusText}</span></td>
			<td class="text-center fs-5">
				<div class="progress">
					<div class="progress-bar bg-cyan-600" role="progressbar" style="width: ${progress}%;" aria-valuenow="${progress}"
                     aria-valuemin="0" aria-valuemax="100"><font style="vertical-align: inherit;"><font
						style="vertical-align: inherit;">${progress.toFixed(2)}%</font></font></div>
				</div>
			</td>
			<td>${study.studystart}</td>
			<td>${study.studyend}</td>
			<td class="text-center">${categoryName}</td>
		</tr>`;
	}

	function StudyPagination(totalCount, currentPage, pageSize, sort) {
		const totalPage = Math.ceil(totalCount / pageSize);
		const startPage = Math.max(1, currentPage - 5);
		const endPage = Math.min(totalPage, currentPage + 4);

		$("#studyPagination").empty();

		if (totalCount != 0) {
			if (currentPage == 1) {
				$("#studyPagination").append(`<li class="page-item"><a class="page-link disabled">이전</a></li>`);
			} else {
				$("#studyPagination").append(`<li class="page-item"><a href="#" data-page="${currentPage - 1}" class="page-link">이전</a></li>`);
			}

			for (let i = startPage; i <= endPage; i++) {
				$("#studyPagination").append(`<li class="page-item ${i === currentPage ? 'active' : ''}"><a href="#" data-page="${i}" class="page-link">${i}</a></li>`);
			}

			if (currentPage < totalPage) {
				$("#studyPagination").append(`<li class="page-item"><a href="#" data-page="${currentPage + 1}" class="page-link">다음</a></li>`);
			} else {
				$("#studyPagination").append(`<li class="page-item"><a class="page-link disabled">다음</a></li>`);
			}
		}

		// 페이지 링크 클릭 이벤트
		$("#studyPagination a").click(function(event) {
			event.preventDefault();
			let selectedPage = $(this).data("page");
			loadMyStudyList(selectedPage, pageSize, sort);
		});
	}

	//스터디 목록
	function loadMyStudyList(page = 1, size = 5, sort = 'studystart') {

		$.ajax({
			url: '/user/user-studyList',
			method: 'GET',
			data: { id: id,
					page: page,
					size: size,
					sort: sort},
			cache: false,
			beforeSend: function (xhr) {
				if (header && token) {
					xhr.setRequestHeader(header, token);
				}
			},
			success: function (response) {
//				console.log("response.studyListCount:"+ response.studyListCount)
//				console.log("response.studyMyList:"+ response.studyMyList)
				if (response.studyListCount == 0) {
					let sentence = '가입된 스터디가 없습니다. '
					$('#studylist').html(sentence)

				} else {
					$('#studylist').empty();
					let output = '';

					response.studyMyList.forEach(function (study) {
//						console.log("study object: ", study);
						output += generateMyStudyHtml(study);
					});
					$('#studylist').html(output);

					// 페이징 처리
					StudyPagination(response.studyListCount, page, size, sort);
				}
			},
			error: function (xhr,status, error) {
				console.log("ajax 요청 실패")
				console.log("상태:" + status)
				console.log("오류:" + error)
			}
		})
	}

	loadMyStudyList(page,size, sort);

	// 정렬 옵션 클릭 시 이벤트 처리
	$('.dropdown-item').on('click', function(event) {
		event.preventDefault();
		const sort = $(this).attr('id');
		const sortText = $(this).text();

		// 선택된 옵션을 버튼 텍스트로 설정
		$('#selectedOptionText').text(sortText);

		loadMyStudyList(1, 5, sort); // 정렬 변경 시 1페이지부터 다시 로드
	});


	//일정 리스트
	loadMyEventList(page, size);

	function EventPagination(totalCount, currentPage, pageSize) {
		const totalPage = Math.ceil(totalCount / pageSize);
		const startPage = Math.max(1, currentPage - 5);
		const endPage = Math.min(totalPage, currentPage + 4);

		$("#eventPagination").empty();

		if (totalCount != 0) {
			if (currentPage == 1) {
				$("#eventPagination").append(`<li class="page-item"><a class="page-link disabled">이전</a></li>`);
			} else {
				$("#eventPagination").append(`<li class="page-item"><a href="#" data-page="${currentPage - 1}" class="page-link">이전</a></li>`);
			}

			for (let i = startPage; i <= endPage; i++) {
				$("#eventPagination").append(`<li class="page-item ${i === currentPage ? 'active' : ''}"><a href="#" data-page="${i}" class="page-link">${i}</a></li>`);
			}

			if (currentPage < totalPage) {
				$("#eventPagination").append(`<li class="page-item"><a href="#" data-page="${currentPage + 1}" class="page-link">다음</a></li>`);
			} else {
				$("#eventPagination").append(`<li class="page-item"><a class="page-link disabled">다음</a></li>`);
			}
		}

		// 페이지 링크 클릭 이벤트
		$("#eventPagination a").click(function(event) {
			event.preventDefault();
			let selectedPage = $(this).data("page");
			loadMyEventList(selectedPage, pageSize);
		});
	}

	function loadMyEventList(page = 1, size = 5) {

		$.ajax({
			url: '/user/user-eventList',
			method: 'GET',
			data: { id: id,
				page: page,
				size: size
				},
			cache: false,
			beforeSend: function (xhr) {
				if (header && token) {
					xhr.setRequestHeader(header, token);
				}
			},
			success: function (response) {
				console.log("response.studyEvent:"+ response.studyEvent)
				console.log("response.studyEventCount:"+ response.eventCount)
				if (response.eventCount == 0) {
					let sentence = '일정이 없습니다. '
					$('#eventList').html(sentence)

				} else {
					$('#eventList').empty();
					response.studyEvent.forEach(study => {

						let studySubject = study.studyTitle;
						if (studySubject.length > 5) { // 원하는 길이로 설정
							studySubject = studySubject.substring(0, 5) + '...';
						}

							console.log("study object: ", study);
							let output = ` <tr class="gridjs-tr">
                                                <td data-column-id="id" class="gridjs-td"><a href="/study/mine?studyboardno=${study.studyBoardNo}" class="btn-link">${studySubject}</a></td>
                                                <td data-column-id="name" class="gridjs-td">${study.eventTitle}</td>
                                                <td data-column-id="lastLogin" class="gridjs-td">${study.start}</td>
                                                <td data-column-id="amount" class="gridjs-td">${study.start}</td>
                                                <td data-column-id="amount" class="gridjs-td">${study.end}</td>
                                            </tr>`;
							console.log("output: ", output);
							$('#eventList').append(output);

							// 페이징 처리
							EventPagination(response.eventCount, page, size);

					});
				}
			},
			error: function (xhr,status, error) {
				console.log("ajax 요청 실패")
				console.log("상태:" + status)
				console.log("오류:" + error)
			}
		})
	}
})
