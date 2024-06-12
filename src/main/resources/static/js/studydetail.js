$(function () {
    let studyboardno = $("#commentAddBtn").data("studyboardno")

    getStudyCommentList(studyboardno)

    let countStudyComm = $("#countStudyComm").val();
    console.log(countStudyComm)

    $("#toggleComments").on("click", function() {
        let commentsSection = $("#commentsSection");
        if (commentsSection.is(":visible")) {
            commentsSection.hide();
            getStudyCommentList()
        } else {
            commentsSection.show();
        }
    });

    window.addComment = function(button) {

        let studyboardno = $(button).data("studyboardno");
        let contentselector = $("#commentInput");
        let content = contentselector.val();
        let id = $("#loginId").text()

        if (content) {
            $.ajax({
                url: "/study/add-board-comment",
                method: "post",
                data: {
                    id: id,
                    studyboardno: studyboardno,
                    content: content
                },
                cache: false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    //console.log(response)
                    if (response.status === "success") {
                        getStudyCommentList(studyboardno);
                        alert(response.message)
                        contentselector.val('')
                    } else {
                        alert(response.message)
                    }
                },
                error: function (status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("댓글 입력 과정 중 에러 발생했습니다. 다시 시도해주세요")
                }
            })
        }
    } // window.addComment = function(button) end

    function getStudyCommentList(studyboardno) {
        $.ajax({
            url: "/study/get-board-comment",
            method: "post",
            data: { no : studyboardno },
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (response) {
                if (response.studyCommList) {
                    renderComments(response.studyCommList)
                    $("#toggleComments").text("댓글 보기 (" + response.studyCommListCount + ")");
                }
            },
            error: function (status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
            }
        })
    }

    function renderComments(comments) {
        let commentList = $('#commentList');
        commentList.empty(); // 기존 댓글 삭제

        comments.forEach(comment => {
            let commentHtml = `
                <div class="d-flex mb-2">
                    <div class="flex-shrink-0">
                        <img class="img-sm rounded-circle" src="/img/profile-photos/10.png" alt="Profile" loading="lazy">
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <div class="mb-1">
                            <a class="h6 btn-link">${comment.id}</a>
                            <small class="ms-2 text-body-secondary mb-0">${comment.created}</small>
                            &nbsp;
                            <button class="btn btn-icon btn-outline-primary btn-xs rounded-circle">
                                <ion-icon name="settings-outline"></ion-icon>
                            </button>
                            <button class="btn btn-icon btn-outline-warning btn-xs rounded-circle">
                                <ion-icon name="trash-outline"></ion-icon>
                            </button>
                        </div>
                        <span>${comment.content}</span>
                        <a class="btn btn-xs btn-outline-light">Reply</a>
                    </div>
                </div>`;

            commentList.append(commentHtml);
        });
    }

}) //ready end

