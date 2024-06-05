let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {

    let loginId = $("#loginId").text();
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

                $("#reviewModalId").attr("value", loginId)
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
                    id : loginId,
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
                if (rdata.listcount > 0) {
                    $(rdata.list).each(function () {
                        let output = ''
                        let button = ''

                        if (loginId == this.id) {
                            button  = "<button type='button' class='btn btn-icon btn-outline-light rounded-circle btn-xs ml'>"
                                + "<i class='demo-pli-pencil'></i>"
                                + "</button>"
                                + "<button type='button' class='btn btn-icon btn-outline-danger rounded-circle btn-xs ml'>"
                                + "<i class='demo-pli-trash'></i>"
                                + "</button>"
                        }

                        output += "<div class='row align-items-start mb-3'>"
                            + "<div class='col-sm-2 text-center' style='font-size:8px'>"
                            + "<img src='../img/profile-photos/1.png' class='img-xs rounded-circle'>"
                            + "</div>"
                            + "<div class='col-sm-9'>"
                            + "<div class='row align-items-center' style='font-size:8px'>"
                            + this.id + "(" + this.created + ")" + "&nbsp;&nbsp;"
                            + button
                            + "</div>"
                            + "<div class='row'>"
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


}) //ready end