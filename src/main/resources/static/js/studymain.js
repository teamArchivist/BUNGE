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
                        let option = $("<option>").val(subject.cover).text(subject.title);
                        $("#searchCondition").append(option);
                    })

                    $("#searchCondition").change(function() {
                        let selectedCover = $(this).val();
                        if (selectedCover) {
                            $("#bookCover").attr("src", selectedCover).show()
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

    $("#addRule").click(function() {
        console.log(i)

        if (i < 5) {
            let output = "<div class='input-group mb-2'>"
                                + "<input th:name='rule' type='text' class='form-control'>"
                                + "<button type='button' class='btn btn-danger deleteBtn'>삭제</button>"
                                + "</div>";

            $("#ruleArea").append(output);
            i++;
        } else {
            alert("규칙은 5개까지만 설정할 수 있습니다")
        }

    })

    $("body").on("click", ".deleteBtn", function() {
        $(this).parent().remove();
        i--
    })

    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("createStudyForm").addEventListener("submit", function (event) {
            event.preventDefault();

            let form = event.target;
            let formData = new FormData(form);

            fetch("/study/create-study", {
                method: "post",
                body: formData,
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')
                }
            })
                .then(response => response.json())
                .then(data => {
                    alert(data.message);
                    if (data.status === "success") {
                        form.reset();
                        location.href = "/study/main";
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("스터디 생성 중 오류가 발생했습니다. 다시 시도해주세요.");
                });
        })
    })

})