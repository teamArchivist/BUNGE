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
				comments.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
				$('#commentsContainer').empty();
				let output = '';
				comments.forEach(function(comment) {
					if (!comment.parentCommentId) {
						output += generateCommentHtml(comment, false);
						output += '<hr>';
					}
				});
				$('#commentsContainer').html(output);
				comments.forEach(function(comment) {
					if (comment.parentCommentId) {
						let replyHtml = generateCommentHtml(comment, true);
						$(`#replies-${comment.parentCommentId}`).append(replyHtml);
					}
				});
			},
			error: function(error) {
				console.error('Error fetching comments:',  error);
			}
		});
	}

	function generateCommentHtml(comment, isReply = false) {
		let replyClass = isReply ? 'reply' : 'comment';
		let replyContainer = isReply ? '' : `<div class="replies ms-4" id="replies-${comment.commentId}"></div>`;
		let replyButton = isReply ? '' : `<a class="btn btn-xs btn-outline-light replyComment" data-id="${comment.commentId}">답글쓰기</a>`;

		return `<div class="${replyClass} mb-4" id="comment-${comment.commentId}">
            <div class="d-flex" style="padding-top: 10px;">
                <div class="flex-shrink-0">
                    <img class="img-sm rounded-circle" src="/img/profile-photos/default.png" alt="Profile Picture" loading="lazy">
                </div>
                <div class="flex-grow-1 ms-3">
                    <div class="mb-2">
                        <a href="#" class="h6 btn-link">${comment.memberId}</a>
                        <small class="ms-2 text-body-secondary">${new Date(comment.createdAt).toLocaleString()}</small>
                        <button class="btn btn-icon btn-sm btn-hover btn-primary dropdown" data-bs-toggle="dropdown"
                                aria-expanded="false" style="float: right">
                            <i class="demo-pli-dot-horizontal fs-4"></i>
                            <span class="visually-hidden">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a href="#" class="dropdown-item editComment" data-id="${comment.commentId}" data-content="${comment.content}">
                                    <i class="demo-pli-pen-5 fs-5 me-2"></i> 수정
                                </a>
                            </li>
                            <li>
                                <a href="#" class="dropdown-item text-danger fw-semibold deleteComment" data-id="${comment.commentId}" data-parent-id="${comment.parentCommentId || ''}">
                                    <i class="demo-psi-recycling fs-5 me-2"></i> 삭제
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                        </ul>
                    </div>
                    <p class="comment-content" id="content-${comment.commentId}" style="margin-bottom: 5px;">${comment.content}</p>
                    ${replyButton}
                    ${replyContainer}
                </div>
            </div>
        </div>`;
	}


	// 페이지 로드 시 댓글 목록 가져오기 호출
	loadComments();

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

	// 대댓글 작성
	$(document).on('click', '.replyComment', function() {
		let parentCommentId = $(this).data('id');
		let contentElement = $(`<textarea class="form-control reply-textarea" rows="3" style="resize: none;"></textarea>`);
		let saveButton = $('<button class="btn btn-primary btn-sm mt-2" style="float:right">등록</button>');
		let cancelButton = $('<button class="btn btn-secondary btn-sm mt-2" style="float:right">취소</button>');
		let replyContainer = $(`#replies-${parentCommentId}`);

		// 이미 존재하는 reply-form을 제거
		replyContainer.find('.reply-form').remove();

		let replyForm = $('<div class="reply-form"></div>').append(contentElement).append(saveButton).append(cancelButton);
		replyContainer.append(replyForm);

		saveButton.on('click', function() {
			let content = contentElement.val().trim();

			if (content) {
				let commentData = {
					inquiryId: inquiryId,
					memberId: memberId, // 로그인된 사용자 아이디 사용
					parentCommentId: parentCommentId,
					content: content,
					isPrivate: false
				};

				$.ajax({
					url: '/comments/add',
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(commentData),
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
					},
					success: function(response) {
						console.log("Response from server: ", response); // 응답 객체의 구조 확인
						let replyHtml = generateCommentHtml(response, true);
						$(`#replies-${parentCommentId}`).append(replyHtml);
						replyForm.remove();
					},
					error: function(error) {
						console.error('Error:', error);
					}
				});
			}
		});

		cancelButton.on('click', function() {
			replyForm.remove();
		});
	});

	// 댓글 삭제
	$(document).on('click', '.deleteComment', function(e) {
		e.preventDefault();
		let commentId = $(this).data('id');
		console.log("commentId:"+commentId);
		let parentCommentId = $(this).data('parent-id');
		console.log("parent:"+parentCommentId);

		if (confirm("정말 이 댓글을 삭제하시겠습니까?")) {
			$.ajax({
				url: '/comments/' + commentId,
				type: 'DELETE',
				data: JSON.stringify({
					"parentCommentId":parentCommentId,
					}
				),
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success: function(response) {
					console.log("Comment deleted: ", response);
					if(response == 1){
						loadComments();
					}
				},
				error: function(error) {
					console.error('Error:', error);
				}
			});
		}
	});

	// 댓글 작성
	$('#commentForm').submit(function(event) {
		event.preventDefault();
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
			memberId: memberId,
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
				xhr.setRequestHeader(header, token);
			},
			success: function(response) {
				// 댓글 목록을 다시 불러와서 새로고침 없이 적용
				loadComments();
				$('#_dm-commentTextarea').val('');
			},
			error: function(error) {
				console.error('Error:', error);
			}
		});
	});

});
