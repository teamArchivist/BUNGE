$(document).ready(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");

	const inquiryId = $('#inquiryId').val(); // inquiryId 값 가져오기
	const memberId = $('#loginId').text(); // 로그인된 사용자 아이디 값 가져오기

	// 페이지 로드 시 댓글 목록 가져오기
	function loadComments() {
		$.ajax({
			url: '/comments/' + inquiryId,
			type: 'GET',
			success: function(comments) {
				// 시간 순으로 댓글을 정렬 (오래된 순서대로 정렬)
				comments.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));

				$('#commentsContainer').empty();
				let output = '';
				comments.forEach(function(comment) {
					if (!comment.parentCommentId) {
						// 부모 댓글일 경우
						output += '<div class="d-flex" id="comment-' + comment.commentId + '">'
							+ '    <div class="flex-shrink-0">'
							+ '        <img class="img-sm rounded-circle" src="/img/profile-photos/default.png" alt="Profile Picture" loading="lazy">'
							+ '    </div>'
							+ '    <div class="flex-grow-1 ms-3">'
							+ '        <div class="mb-2">'
							+ '            <a href="#" class="h6 btn-link">' + comment.memberId + '</a>'
							+ '            <small class="ms-2 text-body-secondary">' + comment.createdAt + '</small>'
							+ '            <button class="btn btn-icon btn-sm btn-hover btn-primary dropdown" data-bs-toggle="dropdown"'
							+ '                    aria-expanded="false" style="float: right">'
							+ '                <i class="demo-pli-dot-horizontal fs-4"></i>'
							+ '                <span class="visually-hidden">Toggle Dropdown</span>'
							+ '            </button>'
							+ '            <ul class="dropdown-menu dropdown-menu-end">'
							+ '                <li>'
							+ '                    <a href="#" class="dropdown-item editComment" data-id="' + comment.commentId + '" data-content="' + comment.content + '">'
							+ '                        <i class="demo-pli-pen-5 fs-5 me-2"></i> Edit'
							+ '                    </a>'
							+ '                </li>'
							+ '                <li>'
							+ '                    <a href="#" class="dropdown-item text-danger fw-semibold deleteComment" data-id="' + comment.commentId + '">'
							+ '                        <i class="demo-psi-recycling fs-5 me-2"></i> Remove'
							+ '                    </a>'
							+ '                </li>'
							+ '                <li>'
							+ '                    <hr class="dropdown-divider">'
							+ '                </li>'
							+ '            </ul>'
							+ '        </div>'
							+ '        <p class="comment-content" id="content-'+ comment.commentId +'">' + comment.content + '</p>'
							+ '        <a class="btn btn-xs btn-outline-light replyComment" data-id="' + comment.commentId + '">Reply</a>'
							+ '        <div class="replies ms-4" id="replies-' + comment.commentId + '">'
							+ '        </div>'
							+ '    </div>'
							+ '</div><hr>';
					}
				});

				// 대댓글 추가
				comments.forEach(function(comment) {
					if (comment.parentCommentId) {
						// 대댓글일 경우
						let replyOutput = '<div class="reply d-flex mb-3" id="comment-' + comment.commentId + '">'
							+ '    <div class="flex-shrink-0">'
							+ '        <img class="img-sm rounded-circle" src="/img/profile-photos/default.png" alt="Profile Picture" loading="lazy">'
							+ '    </div>'
							+ '    <div class="flex-grow-1 ms-3">'
							+ '        <div class="mb-2">'
							+ '            <a href="#" class="h6 btn-link">' + comment.memberId + '</a>'
							+ '            <small class="ms-2 text-body-secondary">' + comment.createdAt + '</small>'
							+ '            <button class="btn btn-icon btn-sm btn-hover btn-primary dropdown" data-bs-toggle="dropdown"'
							+ '                    aria-expanded="false" style="float: right">'
							+ '                <i class="demo-pli-dot-horizontal fs-4"></i>'
							+ '                <span class="visually-hidden">Toggle Dropdown</span>'
							+ '            </button>'
							+ '            <ul class="dropdown-menu dropdown-menu-end">'
							+ '                <li>'
							+ '                    <a href="#" class="dropdown-item editComment" data-id="' + comment.commentId + '" data-content="' + comment.content + '">'
							+ '                        <i class="demo-pli-pen-5 fs-5 me-2"></i> Edit'
							+ '                    </a>'
							+ '                </li>'
							+ '                <li>'
							+ '                    <a href="#" class="dropdown-item text-danger fw-semibold deleteComment" data-id="' + comment.commentId + '">'
							+ '                        <i class="demo-psi-recycling fs-5 me-2"></i> Remove'
							+ '                    </a>'
							+ '                </li>'
							+ '                <li>'
							+ '                    <hr class="dropdown-divider">'
							+ '                </li>'
							+ '            </ul>'
							+ '        </div>'
							+ '        <p id="content'+ comment.commentId +'">' + comment.content + '</p>'
							+ '    </div>'
							+ '</div>';
						$(`#replies-${comment.parentCommentId}`).append(replyOutput);
					}
				});

				$('#commentsContainer').html(output);
			},
			error: function(error) {
				console.error('Error fetching comments:', error);
			}
		});
	}

	// 페이지 로드 시 댓글 목록 가져오기 호출
	loadComments();

	// 댓글 작성
	$('#commentForm').submit(function(event) {
		event.preventDefault(); // 기본 폼 제출 동작 방지

		const content = $("#_dm-commentTextarea").val().trim();

		if (!content) {
			alert('내용을 입력하세요');
			return false;
		}

		if (!inquiryId) {
			alert('Inquiry ID가 유효하지 않습니다.');
			return false;
		}

		let commentData = {
			inquiryId: inquiryId,
			memberId: memberId, // 로그인된 사용자 아이디 사용
			content: content,
			parentCommentId: null,
		};

		console.log("Sending AJAX request to /comments/add with data:", commentData);

		$.ajax({
			url: '/comments/add',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(commentData),
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
			},
			success: function(response) {
				loadComments(); // 댓글 목록 다시 로드
				$('#_dm-commentTextarea').val(''); // 입력란 초기화
			},
			error: function(error) {
				console.error('Error:', error);
			}
		});
	});

	// 댓글 삭제
	$(document).on('click', '.deleteComment', function(e) {
		e.preventDefault(); // 기본 동작 방지
		let commentId = $(this).data('id');

		if (confirm("정말 이 댓글을 삭제하시겠습니까?")) {
			$.ajax({
				url: '/comments/delete' + commentId,
				type: 'DELETE',
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
				},
				success: function(response) {
					loadComments(); // 댓글 목록 다시 로드
				},
				error: function(error) {
					console.error('Error:', error);
				}
			});
		}
	});

	// 댓글 수정
	$(document).on('click', '.editComment', function(e) {
		e.preventDefault(); // 기본 동작 방지
		let commentId = $(this).data('id');
		let contentElement = $('#content-' + commentId);
		let originalContent = contentElement.text().trim();

		let textarea = $('<textarea class="form-control" rows="3" style="resize: none;"></textarea>').val(originalContent);
		let saveButton = $('<button class="btn btn-primary btn-sm mt-2" style="float:right">등록</button>');
		let cancelButton = $('<button class="btn btn-secondary btn-sm mt-2" style="float:right">취소</button>');

		contentElement.replaceWith(textarea);
		textarea.after(saveButton).after(cancelButton);

		saveButton.on('click', function() {
			let newContent = textarea.val().trim();
			if (newContent !== originalContent) {
				let commentData = {
					commentId: commentId,
					content: newContent
				};

				$.ajax({
					url: '/comments',
					type: 'PUT',
					contentType: 'application/json',
					data: JSON.stringify(commentData),
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
					},
					success: function(response) {
						let newContentElement = $('<p class="comment-content" id="content-' + commentId + '"></p>').text(newContent);
						textarea.replaceWith(newContentElement);
						saveButton.remove();
						cancelButton.remove();
					},
					error: function(error) {
						console.error('Error:', error);
						let originalContentElement = $('<p class="comment-content" id="content-' + commentId + '"></p>').text(originalContent);
						textarea.replaceWith(originalContentElement);
						saveButton.remove();
						cancelButton.remove();
					}
				});
			} else {
				let originalContentElement = $('<p class="comment-content" id="content-' + commentId + '"></p>').text(originalContent);
				textarea.replaceWith(originalContentElement);
				saveButton.remove();
				cancelButton.remove();
			}
		});

		cancelButton.on('click', function() {
			let originalContentElement = $('<p class="comment-content" id="content-' + commentId + '"></p>').text(originalContent);
			textarea.replaceWith(originalContentElement);
			saveButton.remove();
			cancelButton.remove();
		});
	});
});
