let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {
    let loginId = $("#loginId").text();
    //console.log(loginId);

    $("body").on("click", ".updateMemo", function() {
        let thisno = $(this).data("thisno")
        let thiscover = $(this).data("thiscover")
        let thisreadpage = $(this).data("thisreadpage")
        let thisispublic = $(this).data("thisispublic")
        let thisisbn13 = $(this).data("thisisbn13")
        let thistitle = $(this).data("thistitle")
        let thiscontent = $(this).data("thiscontent")

        $("#thismodalcover").attr("src", thiscover)
        $("#thisreadpage").attr("value", thisreadpage)
        $("#thisloginid").attr("value", loginId)

        if (thisispublic == "Y") {
            $("option[value='Y']").attr("selected", "true")
        } else if (thisispublic == "N") {
            $("option[value='N']").attr("selected", "true")
        }

        $("#thistitle").attr("value", thistitle)
        $("#thiscontent").text(thiscontent)
        $("#thisisbn13").attr("value", thisisbn13)
        $("#thisno").attr("value", thisno)
    })

    $("body").on("click", ".deleteMemo", function() {
        let deleteno = $(this).data("thisno")
        let deleteisbn13 = $(this).data("thisisbn13")
        let deletereadpage = $(this).data("thisreadpage")

        let answer = confirm("정말 삭제하시겠습니까?");

        if (confirm) {
           let deleteMemoData = {
               no : deleteno,
               isbn13 : deleteisbn13,
               id : loginId,
               readpage : deletereadpage
           }

           $.ajax({
               url: "delete-memo",
               method: "post",
               contentType: "application/json; charset=utf-8",
               data: JSON.stringify(deleteMemoData),
               cache: false,
               beforeSend : function (xhr) {
                   if (header && token) {
                       xhr.setRequestHeader(header, token);
                   }
               },
               success: function (rdata) {
                   //console.log(this.data)
                   //console.log(rdata)

                   if (rdata == 1) {
                       alert("삭제 성공")
                       location.href = "mine"
                   } else {
                       alert("삭제 실패")
                   }
               }
           })
       }
    })

    $("body").on("click", "#writeMemo", function() {
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
            url: "count-remain-page",
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

    $("body").on("keyup", "#_dm-inputPages", function() {
        if (parseInt($("#modalremainpage").text()) - parseInt($("#_dm-inputPages").val()) < 0) {
            alert("남아 있는 페이지보다 클 수 없습니다.");
            $("#_dm-inputPages").val("");
        }
    })
}) //ready end