let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {

    let i = 1;
    let title = "";
    let author = "";
    let loginId = $("#loginId").text();

    $("#studyLeader").val(loginId)

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
                if (rdata.length != 0) {
                    rdata.forEach(subject => {
                        //console.log(subject)
                        let option = $("<option>").val(subject.title).text(subject.title).data("cover", subject.cover);
                        $("#searchCondition").append(option);
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

    $("body").on("change", "#studyStartDate", function() {
        let studyStartDate =  $(this).val()
        let enddate = $("#endDate").val()
        if (studyStartDate < enddate) {
            alert("도전기간 시작일은 모집기간 뒤에 있어야 합니다");
            $(this).val("");
        }
    })

    $("body").on("change", "#endDate", function () {
        let startDate = $("#startDate").val()
        let endDate = $(this).val()

        if (startDate && endDate) {
            let start = new Date(startDate);
            let end = new Date(endDate);

            let timeDiff = end - start;
            let dayDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));

            console.log(dayDiff);

            if (dayDiff < 0) {
                alert("종료일은 시작일보다 빠를 수 없습니다")
                $(this).val("")
            } else if (dayDiff > 30) {
                alert("모집 기간은 최대 30일입니다")
            }
        }


    }) //$("body").on("change", "#endDate", function () end

    $("body").on("change", "#studyEndDate", function () {
        let studyStartDate = $("#studyStartDate").val()
        let studyEndDate = $(this).val()

        if (studyStartDate && studyEndDate) {
            let startDate = new Date(studyStartDate);
            let endDate = new Date(studyEndDate);

            let timeDiff = endDate - startDate;
            let dayDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));

            //console.log(dayDiff);

            if (dayDiff < 0) {
                alert("종료일은 시작일보다 빠를 수 없습니다");
                $(this).val("")
            } else if (dayDiff > 100) {
                alert("도전 기간은 최대 100일입니다")
            }
        }
    }) //$("body").on("change", "#endDate", function () end

    $("input[name=title]").keyup(function () {
        let input = $(this).val()
        let maxLength = 50

        $("#countStudyTitle").text(input.length + " / " + maxLength)

        if (input.length > maxLength) {
            alert("최대 " + maxLength + "까지만 가능합니다")
            $(this).val(input.substring(0, maxLength))
            $("#countStudyTitle").text(maxLength + " / " + maxLength)
        }
    })

    $("textarea[name=content]").keyup(function () {
        let input = $(this).val()
        let maxLength = 200

        $("#countStudyContent").text(input.length + " / " + maxLength)

        if (input.length > maxLength) {
            alert("최대 " + maxLength + "까지만 가능합니다")
            $(this).val(input.substring(0, maxLength))
            $("#countStudyContent").text(maxLength + " / " + maxLength)
        }
    })

    function updateStudyApplicationStatus(loginId) {
        $.ajax({
            url: "/study/get-application-status",
            method: "get",
            data: {loginId: loginId},
            cache: false,
            success: function (data) {
                if (data.result === "success") {
                    data.myApplication.forEach(application => {
                        let statusBadge;
                        if (application.status === "대기") {
                            statusBadge = "<div class='d-block badge bg-secondary'>대기중</div>";
                        } else if (application.status === "승인") {
                            statusBadge = "<div class='d-block badge bg-primary'>승인됨</div>";
                        } else if (application.status === "거절") {
                            statusBadge = "<div class='d-block badge bg-dark'>거절됨";
                        }
                        $(`#status-${application.studyboardno}`).append(statusBadge);
                    })
                } else {
                    alert("페이지 새로고침이 필요합니다")
                }
            },
            error: function (status, error) {
                console.log("ajax 요청 실패");
                console.log("상태:" + status);
                console.log("오류:" + error);
            }
        })
    }

    updateStudyApplicationStatus(loginId);


})