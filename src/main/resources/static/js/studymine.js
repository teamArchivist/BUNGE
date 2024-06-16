window.showReportModal = function (memberId) {
    const reporterId = $("#loginId").text();
    const currentDate = new Date().toISOString().slice(0, 10);

    $("#reportMemberId").val(memberId);
    $("#reporterId").val(reporterId);
    $("#reportDate").val(currentDate);

    $("#reportModal").modal("show");
}

window.resetReportModal = function () {
    $("#reportReason").val("")
}

window.showModifyBookModal = function () {
    $("#modifyBookModal").modal("show");
}

window.resetModifyBookModal = function () {
    $("#inputBookTitle").val("");
    $("#inputAuthor").val("");
    $("#searchCondition").empty();
    $("#bookCover").hide();
    $("#searchBookInfo").css("display", "none");
    $("#searchCategoryName").css("display", "none");
    $("#searchPage").css("display", "none");
    $("#searchPubDate").css("display", "none");
    $("#searchCondition").css("display", "none")
}

$(function () {

    let urlParams = new URLSearchParams(window.location.search);
    let studyboardno = urlParams.get("studyboardno");

    $("#reportModal").on("hidden.bs.modal", function () {
        resetReportModal();
    })

    $("#modifyBookModal").on("hidden.bs.modal", function () {
        resetModifyBookModal();
    });

    $("body").on("click", "#searchBook", function() {

        $("#searchCondition").empty();
        $("#bookCover").hide();
        let title = $("#inputBookTitle").val()
        let author = $("#inputAuthor").val()

        $.ajax({
            url: "search-book",
            method: "post",
            data: {title, author},
            cache: false,
            beforeSend: function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (rdata) {
                //console.log(rdata);
                $("#searchCondition").css("display", "block");
                if (rdata.length != 0) {
                    rdata.forEach(subject => {
                        console.log(subject)
                        let option = $("<option>").val(subject.title).text(subject.title).data("cover", subject.cover);
                        $("#searchCondition").append(option);
                        $("#bookCover").attr("src", subject.cover).css("display", "block")

                        $("#searchAuthor").text("지은이 : " + subject.author).css("display", "block")
                        $("#searchCategoryName").text("카테고리 : " + subject.categoryName).css("display", "block")
                        $("#searchPage").text("페이지 : " + subject.page).css("display", "block")
                        $("#searchPubDate").text("출간일 : " + subject.pubDate).css("display", "block")
                        $("#searchBookInfo").css("display", "block")

                    })

                    $("#searchCondition").change(function() {
                        let selectedOption = $(this).find("option:selected");
                        let selectedCover = selectedOption.data("cover");
                        //console.log(selectedCover)
                        if (selectedCover) {
                            $("#bookCover").attr("src", selectedCover).show()
                            $("#inputBookCover").val(selectedCover);
                        } else {
                            $("#bookCover").hide();
                        }
                    })
                } else if (rdata.length == 0) {
                    let option = $("<option>").text("검색 결과가 없습니다").val(null)
                    $("#searchCondition").append(option)
                }
            }, //success end
            error: function(status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
                alert("책 검색 중 오류가 발생했습니다. 다시 시도해주세요")
            }
        })
    })  //$("body").on("click", "#searchBook", function() end

    $("body").on("click", "#submitChangeBook", function () {
        alert("책 수정 요청이 전송되었습니다. 리더가 승인하면 도전 책이 변경됩니다.");
        let approvalContent = $("#approvalContent").val();

        $.ajax({
            url: "/study/submit-change-book",
            method: "post",
            cache: false,
            beforeSend: function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            data: {
                studyboardno: studyboardno,
                sort: "수정",
                approvalContent: approvalContent,
                proposer: $("#loginId").text(),
            },
            success: function (data) {
                    if (data.status === "success") {
                        location.reload();
                    }
            },
            error: function(status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
                alert("도전 책 변경 요청 중 오류 발생. 다시 시도해주세요")
            }
        })
    })
})
