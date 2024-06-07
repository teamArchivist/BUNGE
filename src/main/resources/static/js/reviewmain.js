let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {

    let id = $("#loginId").text();
    let page = 1;

    $("body").on("click", ".modifyBtn", function() {
        let no = $(this).data("no")
        let isbn13 = $(this).data("isbn13")
        let linetitle = $(this).data("linetitle")
        let content = $(this).data("content")
        let score = $(this).data("score")
        let created = $(this).data("created")
        let challengeperiod = $(this).data("challengeperiod")

        let modifyReviewData = {
            no : no,
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

                $("#reviewModalId").attr("value", id)
                $("#reviewScore").attr("value", score)
                $("input[name='linetitle']").attr("value", linetitle)
                $("textarea[name='content']").text(content)
                $("input[name='no']").attr("value", no)
            }
        })
    }) //modifyBtn end

    $("body").on("click", ".deleteBtn", function() {
        let no = $(this).data("deleteno");

        let deleteReviewData = {
            no : no
        }
        let answer = confirm("정말 삭제 하시겠습니까?")
        if (answer) {

            $.ajax({
                url : "delete-review",
                method : "post",
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify(deleteReviewData),
                dataType : "json",
                cache : false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success : function (rdata) {
                    console.log(rdata)
                    if (rdata == 1) {
                        alert("삭제 완료")
                        location.href="main";
                    } else {
                        alert("삭제 실패")
                    }
                }
            })
        }
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

    $("body").on("click", ".comment-toggle", function() {
        let reviewno = $(this).data("review-no");
        let commentSection = $("#commentSection" + reviewno);

        if (commentSection.is(":visible")) {
            $("#commnetInput" + reviewno).val("")
            commentSection.hide()
        } else {
            commentSection.show()
            getCommList(reviewno, 1);
        }
    }) // end

    window.addComment = function(button) {
        //console.log("여기");
        let reviewno = $(button).data('review-no');
        let contentselector = $("#commentInput" + reviewno);
        let content = contentselector.val();
        //console.log(content)

        if (content) {
            $.ajax({
                url: '/review/add-comment',  // 서버의 댓글 추가 엔드포인트
                method: 'post',
                data: {
                    id : id,
                    reviewno: reviewno,
                    content: content
                },
                cache: false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    // 댓글 추가 성공 시 UI 업데이트
                    contentselector.val('');  // 입력 필드 초기화
                    if (response == 1) {
                        getCommList(reviewno, 1)
                    }
                },
                error: function() {
                    alert('댓글을 추가하는 중 오류가 발생했습니다.');
                }
            });
        } else {
            alert('댓글을 입력하세요.');
        }
    } //end

    function getCommList(reviewno, currentPage) {
        $.ajax({
            url: "/review/get-comment-list",
            type: "post",
            data: {
                no : reviewno,
                page : currentPage
            },
            dataType: "json",
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function(rdata) {
                $("#commentListCount" + reviewno).text(rdata.listcount)
                $(".reviewComment-list").empty()
                //console.log(rdata.list)
                if (rdata.listcount > 0) {
                    $(rdata.list).each(function () {
                        let output = ''
                        let button = ''

                        if (id == this.id) {
                            button  = "<button type='button' class='btn btn-icon btn-outline-light rounded-circle btn-xs ml update-comm-btn'"
                                + "            data-updatecommno='" + this.no + "' data-updatecommcontent='" + this.content + "'"
                                + "            data-reviewno='" + this.reviewno + "'>"
                                + "<i class='demo-pli-pencil'></i>"
                                + "</button>"
                                + "&nbsp;&nbsp;"
                                + "<button type='button' class='btn btn-icon btn-outline-danger rounded-circle btn-xs ml delete-comm-btn'" +
                                "          data-deletecommno='" + this.no + "' data-reviewno='" + this.reviewno + "'>"
                                + "<i class='demo-pli-trash'></i>"
                                + "</button>"
                        }

                        output += "<div class='row align-items-start mb-3' id='comment-area-" + this.no + "'>"
                            + "<div class='col-sm-2 text-center' style='font-size:8px'>"
                            + "<img src='../img/profile-photos/1.png' class='img-xs rounded-circle'>"
                            + "</div>"
                            + "<div class='col-sm-9'>"
                            + "<div class='row align-items-center' style='font-size:8px'>"
                            + this.id + "(" + this.created + ")" + "&nbsp;&nbsp;"
                            + button
                            + "</div>"
                            + "<div class='row' id='comm-content-area" + this.no +"'>"
                            + this.content
                            + "</div>"

                        $("#commentList" + reviewno).append(output)
                    }) //each end
                } //if end
                else {
                    $("#commentList" + reviewno).append("<span>등록된 댓글이 없습니다</span>")
                }
            } //success end
        }) //ajax end
    } //function getCommList(reviewno, currentPage) end

    $("body").on("click", ".likeBtn", function() {
        //console.log($(this).data("review-no"))
        let reviewno = $(this).data("review-no");

        $.ajax({
            url: "review-like",
            method: "post",
            data: {reviewno, id},
            cache: false,
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (rdata) {
                //console.log("성공")
                //console.log(rdata)
                $("#likeCount" + reviewno).text(rdata.likeCount)
                if (rdata.result == 1) {
                    $("#heart" + reviewno).attr("name", "heart")
                } else if (rdata.result == 0) {
                    $("#heart" + reviewno).attr("name", "heart-outline")
                }
            },
            error: function(status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
                alert("좋아요 처리 중 오류가 발생했습니다. 다시 시도해주세요")
            }
        }) //ajax end
    }) //likeBtn click end

    $.ajax({
        url: "check-review-like-list",
        method: "post",
        data: {id},
        cache: false,
        beforeSend : function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (rdata) {
            //console.log(rdata)
            $(rdata).each(function () {
                //console.log(this.reviewno)
                $("#heart" + this.reviewno).attr("name", "heart")
            })
        },
        error: function(status, error) {
            console.log("ajax 요청 실패")
            console.log("상태:" + status)
            console.log("오류:" + error)
        }
    })

    $("body").on("click", ".update-comm-btn", function() {
        //console.log($(this).data("updatecommno"))
        //console.log($(this).data("updatecommcontent"))
        let no = $(this).data("updatecommno")
        let content = $(this).data("updatecommcontent")
        let reviewno = $(this).data("reviewno")
        let inputValue = "";

        $(".update-comm-btn").attr("disabled", "disabled")
        $("#commentInput" + reviewno).val(content);
        $("#comment-area-" + no).css("border", "dotted");
        $("#comment-add-btn").hide();
        $("button[data-deletecommno='" + no + "']").after("<span style='display:contents;'>(수정중)</span>");
        $("#commentInput" + reviewno).after("<button class='btn btn-icon btn-danger btn-xs rounded-circle flex-shrink-0 updateCancelBtn' type='button'><ion-icon name='close-outline'></ion-icon>")
        $("#commentInput" + reviewno).after("<button class='btn btn-icon btn-success btn-xs rounded-circle flex-shrink-0 updateSubmitBtn' type='button'><ion-icon name='checkmark-outline'></ion-icon>")

        $(".reviewComment-input").keyup(function(event) {
            inputValue = $(this).val()
            $("#comm-content-area" + no).text(inputValue)
        })

        $("body").on("click", ".updateCancelBtn", function() {
            $(".update-comm-btn").attr("disabled", false)
            $("#comment-area-" + no).css("border", "none")
            $("#comm-content-area" + no).text(content)
            $(".reviewComment-input").val("")
            $("#comment-add-btn").show();
            $(".delete-comm-btn").next().css("display","none")
            $(".updateCancelBtn").hide();
            $(".updateSubmitBtn").hide();
        })

        $(".updateSubmitBtn").click(function () {
            let content = inputValue
            console.log(content)

            $.ajax ({
                url: "update-comm",
                method: "post",
                data: {no, content},
                cache: false,
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (rdata) {
                    if (rdata == 1) {
                        $(".reviewComment-input").val("")
                        $("#comment-add-btn").show();
                        $(".delete-comm-btn").next().css("display","none")
                        $(".updateCancelBtn").hide();
                        $(".updateSubmitBtn").hide();
                        $(".reviewComment-input").off("keyup");
                    } else {
                        alert("댓글 내용 수정 중 오류가 발생했습니다. 다시 시도해주세요");
                    }

                },
                error: function(status, error) {
                    console.log("ajax 요청 실패");
                    console.log("상태:" + status);
                    console.log("오류:" + error);
                    alert("댓글 내용 수정 중 오류가 발생했습니다. 다시 시도해주세요");
                }
            }) // ajax end
        }) //$(".updateSubmitBtn").click end
    })

    $("body").on("click", ".delete-comm-btn", function() {
        let reviewno = $(this).data("reviewno")
        let no = $(this).data("deletecommno")
        let answer = confirm("정말 삭제 하시겠습니까?");
        if (answer) {
            $.ajax({
                url: "delete-comm",
                method: "post",
                data: {no},
                cache: false,
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (rdata) {
                    //console.log(rdata)
                    if (rdata == 1) {
                        alert("댓글 삭제 성공");
                        getCommList(reviewno, page)
                    } else {
                        alert("댓글 삭제 실패, 다시 한번 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("댓글 삭제 중 오류가 발생했습니다. 다시 시도해주세요")
                }
            }) // ajax end
        } // if end
    }) // $("body").on("click", ".delete-comm-btn", function() end


}) //ready end