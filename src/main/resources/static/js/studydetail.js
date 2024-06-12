$(function() {
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
});