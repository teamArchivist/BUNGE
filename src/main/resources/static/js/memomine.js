let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {
    let loginId = $("#loginId").text();
    //console.log(loginId);
    let reviewIsbn13List = [];

    $.ajax({
        url: "/review/get-all-reviews",
        method: "post",
        cache: false,
        beforeSend: function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (rdata) {
            //console.log(rdata)
            for (let i=0; i<rdata.length; i++) {
                reviewIsbn13List.push(rdata[i].isbn13)
            }
            //console.log(reviewIsbn13List);
            showButtons(reviewIsbn13List);
        }
    });

    function showButtons(reviewIsbn13List) {
        $("button#addReview").each(function() {
            let reviewIsbn13 = $(this).attr("data-reviewisbn13");
            //console.log(reviewIsbn13)
            if (reviewIsbn13List.includes(reviewIsbn13)) {
                //console.log("hi");
                $(this).hide();

                let reviewModifyBtn = $("<button>", {
                    type: 'button',
                    class: 'btn btn-warning rounded-pill',
                    text: '수정하기',
                    click: function() {
                        location.href = "/review/main"
                    }
                });

                $(this).parent().append(reviewModifyBtn);
            }
        })
    }

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

    $("body").on("click", "#addMemo", function() {
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
            cache: false,
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
        let input = $(this).val();
        let regulation = /^[1-9][0-9]{0,2}$/

        if (!regulation.test(input)) {
            alert("0 ~ 999 양의 정수만 입력할 수 있습니다.");
            $(this).val("");
        } else if (parseInt($("#modalremainpage").text()) - parseInt(input) < 0) {
            alert("남아 있는 페이지보다 클 수 없습니다.");
            $(this).val("");
        }
    })

    $("body").on("keyup", "#_dm-Title", function() {
        let input = $(this).val()
        //console.log(input)
        let maxLength = 30
        //console.log(lineTitleValue);

        $("#countMemoTitle").text(input.length + " / " + maxLength)

        if (input.length > maxLength) {
            alert("최대 " + maxLength + "자까지만 가능합니다")
            $(this).val(input.substring(0, maxLength))
            $("#countMemoTitle").text(maxLength + " / " + maxLength)
        }
    })

    $("body").on("keyup", "#memoContent", function() {
        let input = $(this).val()
        //console.log(input)
        let maxLength = 500
        //console.log(lineTitleValue);

        $("#countMemoContent").text(input.length + " / " + maxLength)

        if (input.length > maxLength) {
            alert("최대 " + maxLength + "자까지만 가능합니다")
            $(this).val(input.substring(0, maxLength))
            $("#countMemoContent").text(maxLength + " / " + maxLength)
        }
    })

    $("body").on("click", "#changeReadState", function() {
        let isbn13 = $(this).data("isbn13")
        let title = $(this).data("title")
        let page = $(this).data("page")
        let changeReadStateData = {
            isbn13 : isbn13,
            id : loginId
        }

        let answer = confirm("'" + title + "' 총 " + page + " page 입니다. 도전 하시겠습니까?")

        if (answer) {
            $.ajax ({
                url : "change-read-state",
                method : "post",
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify(changeReadStateData),
                dataType : "json",
                cache : false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success : function(rdata) {
                    console.log(this.data)
                    console.log(rdata)
                    if (rdata = 1) {
                        alert("도전 도서로 변경되었습니다. 완독을 응원합니다!")
                        location.href = "mine"
                    }
                }
            })
        }
    })  //$("body").on("click", "#changeReadState", ... end

    $("body").on("click", "#addReview", function() {
        let reviewIsbn13 = $(this).data("reviewisbn13")
        let reviewBookTitle = $(this).data("reviewbooktitle")
        let reviewAuthor = $(this).data("reviewauthor")
        let reviewCategoryName = $(this).data("reviewcategoryname")
        let reviewCover = $(this).data("reviewcover")
        let reviewRegitDate = $(this).data("reviewregitdate")
        let reviewPage = $(this).data("reviewpage")
        let reviewScore = $(this).data("reviewscore")

        $("#reviewModalId").attr("value", loginId);
        $("#reviewModalCover").attr("src", reviewCover);
        $("#modalInputIsbn13").attr("value", reviewIsbn13)
        $("#modalInputCover").attr("value", reviewCover)
        $("#reviewModalBookTitle").text(reviewBookTitle);
        $("#reviewModalAuthor").text(reviewAuthor);
        $("#reviewModalCategoryName").text(reviewCategoryName);
        $("#reviewModalScore").text(reviewScore + " 점")
        $("#reviewModalPage").text(reviewPage + " page")

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
            //console.log(lineTitleValue);

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
    }) //$("body").on(click) end ...





}) //ready end