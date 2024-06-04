let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {

    let loginId = $("#loginId").text();

    $("body").on("click", ".modifyBtn", function() {
        let no = $(this).data("no")
        let isbn13 = $(this).data("isbn13")
        let linetitle = $(this).data("linetitle")
        let content = $(this).data("content")
        let score = $(this).data("score")
        let created = $(this).data("created")
        let challengeperiod = $(this).data("challengeperiod")

        let modifyReviewData = {
            isbn13 : isbn13
        }

        $.ajax({
            url: "get-modal-book",
            method: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(modifyReviewData),
            dataType: "json",
            cache: false,
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (rdata) {
                //console.log(this.data)
                //console.log(rdata)
                $("#modalcover").attr("src", rdata.cover)
                $("#reviewModalBookTitle").text(rdata.title);
                $("#reviewModalAuthor").text(rdata.author);
                $("#reviewModalCategoryName").text(rdata.categoryName);
                $("#reviewModalScore").text(rdata.score);
                $("#reviewModalPage").text(rdata.page);

                $("#reviewModalId").attr("value", loginId)
                $("#reviewScore").attr("value", score)
                $("input[name='linetitle']").attr("value", linetitle)
                $("textarea[name='content']").text(content)
                $("input[name='no']").attr("value", no)
            }
        })
    })

    $("form").on("keyup", "#reviewScore", function() {
        let value = $(this).val()
        let regValue = /^[1-5]$/

        if (!regValue.test(value)) {
            alert("1~5 사이의 정수만 입력할 수 있습니다")
            $(this).val("");
        }
    })

    $("form").on("keyup", "input[name=linetitle]", function() {
        let lineTitleValue = $(this).val()
        let maxLength = 30

        $("#countLineTitle").text(lineTitleValue.length + " / " + maxLength)

        if (lineTitleValue.length > maxLength) {
            alert("최대 " + maxLength + "자까지만 가능합니다")
            $(this).val(lineTitleValue.substring(0, maxLength))
            $("#countLineTitle").text(maxLength + " / " + maxLength)
        }
    })

    $("form").on("keyup", "textarea[name=content]", function() {
        let reviewContentValue = $(this).val()
        console.log(reviewContentValue)
        let maxLength = 200

        $("#countReviewContent").text(reviewContentValue.length + " / " + maxLength)

        if (reviewContentValue.length > maxLength) {
            alert("최대 " + maxLength + "자까지만 가능합니다")
            $(this).val(reviewContentValue.substring(0, maxLength))
            $("#countReviewContent").text(maxLength + " / " + maxLength)
        }
    })

})