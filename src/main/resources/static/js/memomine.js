let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {
    let loginId = $("#loginId").text();
    //console.log(loginId);

    $("body").on("click", "#memowrite", function() {
        let cover = $(this).data("cover")
        let isbn13 = $(this).data("isbn13")
        let title = $(this).data("title")
        let author = $(this).data("author")
        let categoryName = $(this).data("categoryname")
        let score = $(this).data("score")
        let page = $(this).data("page")

        //console.log(cover)
        //console.log(title)
        //console.log(categoryName)

        $("#modalcover").attr("src", cover);
        $("#modaltitle").text(title);
        $("#isbn13").attr("value", isbn13);
        $("#modalauthor").text(author);
        $("#modalcategoryName").text(categoryName);
        $("#modalscore").text(score);
        $("#modalpage").text(page);
        $("#cover").attr("value", cover);

        const checkRemainData = {
            isbn13 : isbn13,
            id : loginId,
        };

        $.ajax({
            url: "countremainpage",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(checkRemainData),
            dataType: "json",
            chche: false,
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function(rdata) {
                //console.log(this.data)
                //console.log("ajax성공")
                console.log(rdata)
                $("#modalremainpage").text(rdata);
            },
            error: function(error) {
                $("#modalremainpage").text("");
            }
        }) //ajax end
    }) //$("body").on(click) end ...
}) //ready end