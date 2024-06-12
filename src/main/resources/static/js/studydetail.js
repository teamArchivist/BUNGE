$(function () {

    $("#toggleComments").on("click", function() {
        let commentsSection = $("#commentsSection");
        if (commentsSection.is(":visible")) {
            commentsSection.hide();
            $(this).text("댓글 보기");
        } else {
            commentsSection.show();
            $(this).text("댓글 가리기");
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
                    console.log(response)
                },
                error: function (status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                }
            })
        }
    }
})

