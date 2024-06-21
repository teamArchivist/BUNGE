$(function () {
    let studyboardno = $("#commentAddBtn").data("studyboardno")
    let id = $("#loginId").text();

    getStudyCommentList(studyboardno)
    checkEnddate(studyboardno)

    let countStudyComm = $("#countStudyComm").val();
    //console.log(countStudyComm)

    $("#toggleComments").on("click", function() {
        let commentsSection = $("#commentsSection");
        if (commentsSection.is(":visible")) {
            commentsSection.hide();
            getStudyCommentList()
        } else {
            commentsSection.show();
        }
    });

    window.addComment = function(button) {

        let studyboardno = $(button).data("studyboardno");
        let contentselector = $("#commentInput");
        let content = contentselector.val();
        let id = $("#loginId").text()

        if (content) {
            $.ajax({
                url: "/study/add-board-comment",
                method: "post",
                data: {
                    id: id,
                    studyboardno: studyboardno,
                    content: content
                },
                cache: false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    //console.log(response)
                    if (response.status === "success") {
                        getStudyCommentList(studyboardno);
                        alert(response.message)
                        contentselector.val('')
                    } else {
                        alert(response.message)
                    }
                },
                error: function (status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("댓글 입력 과정 중 에러 발생했습니다. 다시 시도해주세요")
                }
            })
        }
    } // window.addComment = function(button) end

    function getStudyCommentList(studyboardno) {
        $.ajax({
            url: "/study/get-board-comment",
            method: "post",
            data: { no : studyboardno },
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (response) {
                if (response.studyCommList) {
                    renderComments(response.studyCommList)
                    $("#toggleComments").text("댓글 보기 (" + response.studyCommListCount + ")");
                }
            },
            error: function (status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
            }
        })
    }

    function renderComments(comments) {
        console.log(comments);
        let commentList = $('#commentList');
        commentList.empty(); // 기존 댓글 삭제

        comments.forEach(comment => {
            let buttonHtml = '';

            if (comment.id === id) {
                buttonHtml += ` <button class="btn btn-icon btn-outline-warning btn-xs rounded-circle deleteCommBtn" data-deletecommno="${comment.no}">
                                    <ion-icon name="trash-outline"></ion-icon>
                                </button>`;
            }

            let commentHtml = `
                <div class="d-flex mb-2">
                    <div class="flex-shrink-0">
                        <img class="img-sm rounded-circle" src="/upload${comment.profile}" alt="Profile" loading="lazy">
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <div class="mb-1">
                            <a class="h6 btn-link">${comment.id}</a>
                            <small class="ms-2 text-body-secondary mb-0">${comment.created}</small>
                            ${buttonHtml}
                        </div>
                        <span>${comment.content}</span>
                    </div>
                </div>`;

            commentList.append(commentHtml);
        });
    }

    const submitApplicationButton = document.getElementById("submitApplicationButton");

    submitApplicationButton.addEventListener("click", function () {
        const studyboardno = document.getElementById("studyBoardNo").value;
        const applicationContent = document.getElementById("applicationReason").value;
        const applicationId = document.getElementById("loginId").textContent;

        if (applicationContent) {
            $.ajax({
                url: "/study/apply-study",
                method: "post",
                data: {
                    studyboardno: studyboardno,
                    applicationId: applicationId,
                    applicationContent: applicationContent,
                },
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    if (response.status === "success") {
                        alert(response.message);
                        $("#applicationModal").modal("hide");
                        location.reload()
                    } else {
                        alert(response.message);
                    }
                },
                error: function (status, error) {
                    console.log("ajax 요청 실패");
                    console.log("상태:" + status);
                    console.log("오류:" + error);
                    alert("스터디 신청 중 에러가 발생했습니다. 다시 시도해주세요.");
                }
            });
        } else {
            alert("신청 사유를 입력하세요")
        }
    })

    $("#exampleModal").on("show.bs.modal", function (event) {
        let studyboardno = document.getElementById("studyBoardNo").value;

        fetch(`/study/get-applications?studyboardno=${studyboardno}`)
            .then(response => response.json())
            .then(data => {
                let applicationsList = document.getElementById("applicationsList");
                applicationsList.innerHTML = '';

                data.forEach(application => {
                    let row = document.createElement("tr");

                    let applicantTd = document.createElement("td");
                    applicantTd.innerHTML = `<img src="/upload${application.profile}" class="img-sm rounded-circle border">
                                             <span>${application.applicationId}</span>`;
                    row.appendChild(applicantTd);

                    let reasonTd = document.createElement("td");
                    reasonTd.innerText = application.applicationContent;
                    row.appendChild(reasonTd);

                    let requestDateTd = document.createElement("td");
                    requestDateTd.innerText = application.requestdate;
                    requestDateTd.className = "text-nowrap";
                    row.appendChild(requestDateTd);

                    let actionsTd = document.createElement("td");
                    if (application.status === "승인") {
                        actionsTd.innerHTML = `<button class="btn btn-link btn-sm" onclick="cancelApplication(${application.no})">승인취소</button>`;
                    } else if (application.status === "거절") {
                        actionsTd.innerHTML = `<button class="btn btn-link btn-sm" onclick="cancelReject(${application.no})">거절취소</button>`;
                    } else {
                        actionsTd.innerHTML = `<button class="btn btn-primary rounded-pill btn-xs" onclick="approveApplication(${application.no})">승인</button>
                                               &nbsp;<button class="btn btn-danger rounded-pill btn-xs" onclick="rejectApplication(${application.no})">거절</button>`;
                    }
                    actionsTd.className = "status-area text-center text-nowrap";
                    row.appendChild(actionsTd);

                    applicationsList.appendChild(row);
                });
            })
            .catch(error => {
                console.error("Error fetching applications:", error);
            });
    });

    window.approveApplication = function (applicationNo) {
        fetch(`/study/approve-application`, {
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify({ no: applicationNo, status: "승인" })
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert("승인 성공");
                    let applicationRow = $(`button[onclick="approveApplication(${applicationNo})"]`).closest('tr');
                    applicationRow.find('.status-area').html("<button class='btn btn-link btn-sm' onclick=cancelApplication(" + applicationNo +")>승인취소</button>");
                } else {
                    alert("승인 실패. 다시 시도해주세요");
                }
            })
            .catch(error => {
                console.log("Error:", error);
                alert("승인 중 오류가 발생했습니다. 다시 시도해주세요");
            });
    };

    window.rejectApplication = function (applicationNo) {
        fetch(`/study/reject-application`, {
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify({ no: applicationNo, status: "거절"})
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert("승인이 거절 되었습니다")
                    let applicationRow = $(`button[onclick="rejectApplication(${applicationNo})"]`).closest('tr');
                    applicationRow.find('.status-area').html("<button class='btn btn-link btn-sm' onclick=cancelReject(" + applicationNo + ")>거절취소</button>");
                } else {
                    alert("승인 거절 실패. 다시 시도해주세요")
                }
            })
            .catch(error => {
                console.log("Error:", error);
                alert("거절 중 오류가 발생했습니다. 다시 시도해주세요");
            });
    };

    window.cancelApplication = function (applicationNo) {
        let answer = confirm("정말 취소하시겠습니까?")
        if (answer) {
            fetch(`/study/cancel-approve`,{
                method: "post",
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': token
                },
                body: JSON.stringify({no: applicationNo, status: "대기"})
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        alert("승인이 취소 되었습니다")
                        let applicationRow = $(`button[onclick="cancelApplication(${applicationNo})"`).closest('tr');
                        applicationRow.find('.status-area').html(`<button class="btn btn-primary rounded-pill btn-xs" onclick="approveApplication(${applicationNo})">승인</button>
                                               &nbsp;<button class="btn btn-danger rounded-pill btn-xs" onclick="rejectApplication(${applicationNo})">거절</button>`)
                    } else {
                        alert("승인 취소 실패. 다시 시도해주세요")
                    }
                })
                .catch(error => {
                    console.log("Error:", error);
                    alert("취소 중 오류가 발생했습니다. 다시 시도해주세요");
                })
        }
    }

    window.cancelReject = function (applicationNo) {
        let answer = confirm("정말 취소하시겠습니까?")
        if (answer) {
            fetch(`/study/cancel-reject`, {
                method: "post",
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': token
                },
                body: JSON.stringify({no: applicationNo, status: "대기"})
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        alert("거절이 취소 되었습니다")
                        let applicationRow = $(`button[onclick="cancelReject(${applicationNo})"]`).closest('tr');
                        applicationRow.find('.status-area').html(`<button class="btn btn-primary rounded-pill btn-xs" onclick="approveApplication(${applicationNo})">승인</button>
                                               &nbsp;<button class="btn btn-danger rounded-pill btn-xs" onclick="rejectApplication(${applicationNo})">거절</button>`)
                    } else {
                        alert("거절 취소 실패. 다시 시도해주세요")
                    }
                })
                .catch(error => {
                    console.log("Error:", error);
                    alert("취소 중 오류가 발생했습니다. 다시 시도해주세요")
                })
        }
    }

    $.ajax({
        url: "/study/check-application",
        method: "post",
        data: { studyboardno:studyboardno, applicationId:id },

        beforeSend : function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (data) {
            if (data === 1) {
                $("#application").after("<button id='cancelApplication' class='btn btn-danger text-nowrap'>신청 취소</button>")
                $("#application").remove();
            }
        },
        error: function (status, error) {
            console.log("ajax 요청 실패");
            console.log("상태:" + status);
            console.log("오류:" + error);
        }
    })

    function deleteStudyManagement(studyboardno) {
        $.ajax({
            url: "/study/delete-studymanagement",
            method: "post",
            data: {studyboardno : studyboardno},
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (data) {
                alert("도전 시작이 원활히 진행되지 않았습니다. 다시 시도해주세요");
            },
            error: function (status, error) {
                console.log("ajax 요청 실패");
                console.log("상태:" + status);
                console.log("오류:" + error);
            }
        })
    }

    $("#startStudy").click(function() {
        let leaderId = $("#leaderId").text();
        let studystart = new Date(); // 현재 날짜로 설정
        let studyperiod = $("#challengeDate").data("challengeperiod");
        let booktitle = $("#booktitle").text();
        let memberIds = [];
        $(".member-id").each(function () {
            memberIds.push($(this).text().trim());
        });

        console.log(memberIds);

        function formatDate(date) {
            let dd = String(date.getDate()).padStart(2, '0');
            let mm = String(date.getMonth() + 1).padStart(2, '0');
            let yyyy = date.getFullYear();
            return yyyy + '-' + mm + '-' + dd;
        }

        studystart = formatDate(studystart);
        let studyend = new Date();
        studyend.setDate(studyend.getDate() + studyperiod);
        studyend = formatDate(studyend);

        $.ajax({
            url: "/study/get-study-info",
            method: "post",
            data: {
                no: studyboardno,
            },
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (data) {
                if (data.approved == 0) {
                    alert("참여 인원은 최소 1명 이상이어야 합니다");
                } else {
                    let answer = confirm("도전을 시작하시겠습니까? 도전기간 : " + data.challengeperiod + "일, 참여인원 : " + data.approved + "명 입니다."  )
                    if (answer) {
                        $.ajax({
                            url: "/study/start-study",
                            method: "post",
                            data: {
                                studyboardno: studyboardno,
                                studystart: studystart,
                                studyend: studyend,
                                studyperiod: studyperiod,
                                leaderId: leaderId,
                                booktitle: booktitle,
                                studystatus: 'progress',
                            },
                            beforeSend : function (xhr) {
                                if (header && token) {
                                    xhr.setRequestHeader(header, token);
                                }
                            },
                            success: function (data) {
                                if (data == 1) {
                                    $.ajax({
                                        url: "/study/create-study-member",
                                        method: "post",
                                        data: {
                                            studyboardno : studyboardno,
                                            memberIds : memberIds.join(',')
                                        },
                                        beforeSend : function (xhr) {
                                            if (header && token) {
                                                xhr.setRequestHeader(header, token);
                                            }
                                        },
                                        success: function (data) {
                                            if (data.status === "error") {
                                                deleteStudyManagement(studyboardno);
                                            } else {
                                                $.ajax({
                                                    url: "/study/update-challenge-date",
                                                    method: "post",
                                                    data: {
                                                        no: studyboardno,
                                                        challengestart: studystart,
                                                        challengeend: studyend,
                                                    },
                                                    beforeSend : function (xhr) {
                                                        if (header && token) {
                                                            xhr.setRequestHeader(header, token);
                                                        }
                                                    },
                                                    success: function (data) {
                                                        if (data !== 1) {
                                                            deleteStudyManagement(studyboardno);
                                                        } else {
                                                            $.ajax({
                                                                url: "/study/update-studyboard-state",
                                                                method: "post",
                                                                data: {no: studyboardno},
                                                                beforeSend: function (xhr) {
                                                                    if (header && token) {
                                                                        xhr.setRequestHeader(header, token);
                                                                    }
                                                                },
                                                                success: function (data) {
                                                                    if (data === 1) {
                                                                        alert("스터디 도전이 시작되었습니다. 꼭 완독하세요!")
                                                                        $("#startStudy").text("진행중").attr("disabled", true)
                                                                        $("#studyApprove").remove()
                                                                        $("#updateStudy").remove()
                                                                        $("#deleteStudy").remove()
                                                                        location.reload();
                                                                    } else {
                                                                        deleteStudyManagement(studyboardno);
                                                                    }

                                                                },
                                                                error: function (status, error) {
                                                                    console.log("ajax 요청 실패");
                                                                    console.log("상태:" + status);
                                                                    console.log("오류:" + error);
                                                                    deleteStudyManagement(studyboardno);
                                                                }
                                                            })
                                                        }
                                                    } // success end
                                                }) // ajax end
                                            }
                                        },
                                        error: function (status, error) {
                                            console.log("ajax 요청 실패");
                                            console.log("상태:" + status);
                                            console.log("오류:" + error);
                                            deleteStudyManagement(studyboardno);
                                        }
                                    }) //ajax end

                                } else {
                                    alert("도전 시작 중 에러가 발생했습니다. 다시 시도해주세요")
                                }
                            },
                            error: function (status, error) {
                                console.log("ajax 요청 실패");
                                console.log("상태:" + status);
                                console.log("오류:" + error);
                            }
                        })
                    }
                } //else end
            }, //success end
            error: function (status, error) {
                console.log("ajax 요청 실패");
                console.log("상태:" + status);
                console.log("오류:" + error);
            }
        })
    });

    function checkEnddate(studyboardno) {
        $.ajax({
            url: "/study/check-enddate",
            method: "post",
            data: {no: studyboardno},
            cache: false,
            beforeSend : function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (data) {
                //console.log(data)
                if (data) {
                    $("#startStudy").after("<span>(모집기간 종료!! 도전시작 버튼을 눌러주세요)</span>")
                    $("#application").prop("disabled", true).text("모집 종료")
                    $.ajax({
                        url: "/study/update-enroll-status",
                        method: "post",
                        data: {no : studyboardno},
                        cache: false,
                        beforeSend : function (xhr) {
                            if (header && token) {
                                xhr.setRequestHeader(header, token);
                            }
                        },
                        success: function (data) {
                            if (data == 1) {
                                console.log("상태 update 성공")
                                $("#enrollStatus").empty();
                                $("#enrollStatus").append("<div class='d-block badge bg-danger form-control-plaintext'>마감</div><p></p>")
                            } else {
                                console.log("상태 update 실패")
                            }
                        },
                        error: function (status, error) {
                            console.log("ajax 요청 실패");
                            console.log("상태:" + status);
                            console.log("오류:" + error);
                        }
                    })
                }
            },
            error: function (status, error) {
                console.log("ajax 요청 실패");
                console.log("상태:" + status);
                console.log("오류:" + error);
            }
        })
    }

    $.ajax({
        url: "/study/check-study-status",
        method: "post",
        cache: false,
        data: {studyboardno : studyboardno},
        beforeSend : function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (data) {
            if (data.studystatus === "progress") {
                $("#startStudy").text("진행중").attr("disabled", true)
                $("#enrollComment").text("(진행중)")

                $("#application").remove();
                $("#studyApprove").remove();
                $("#updateStudy").remove();
                $("#deleteStudy").remove();
                $("#cancelApplication").remove();

            } else if (data.studystatus === "end") {
                $("#startStudy").text("종료").attr("disabled", true)
                $("#enrollComment").text("(종료)")

                $("#application").remove();
                $("#studyApprove").remove();
                $("#updateStudy").remove();
                $("#deleteStudy").remove();
                $("#cancelApplication").remove();
            }

        },
        error: function (status, error) {
            console.log("ajax 요청 실패");
            console.log("상태:" + status);
            console.log("오류:" + error);
        }
    })

    $("body").on("click", "#cancelApplication", function() {
        let answer = confirm("정말 취소하시겠습니까?")
        if (answer) {
            $.ajax({
                url: "/study/cancel-application",
                method: "post",
                cache: "false",
                data: {
                    applicationId : id,
                    studyboardno : studyboardno
                },
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data == 1) {
                        location.reload();
                    }
                },
                error: function (status, error) {
                    console.log("ajax 요청 실패");
                    console.log("상태:" + status);
                    console.log("오류:" + error);
                }
            })
        }
    }) //$("body").on("click", "#cancelApplication", function() end


    $(".deleteEvent").click(function () {
        let no = $(this).data("eventno");
        //console.log(no);
        let answer = confirm("정말로 일정을 삭제하시겠습니까?")
        if (answer) {
            $.ajax({
                url: "/study/delete-study-event",
                method: "post",
                data: { no : no },
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("이벤트 삭제 완료");
                        location.reload();
                    } else {
                        alert("이벤트 삭제 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("이벤트 삭제 실패. 다시 시도해주세요")
                }
            })
        }
    }) // $("#deleteEvent").click(function () end

    $("#deleteStudy").click(function() {
        let answer = confirm("정말 삭제하시겠습니까?")
        if (answer) {
            $.ajax({
                url: "/study/delete-study",
                method: "post",
                data: { no : studyboardno },
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("모집글 삭제 성공")
                        location.href="/study/main"
                    } else {
                        alert("모집글 삭제 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("모집글 삭제 실패. 다시 시도해주세요")
                }
            })
        }
    }) // $("#deleteStudy").click(function()

    $("#updateAim").click(function () {
        if ($(this).text() === "취소") {
            $(this).text("수정").removeClass("bg-danger").addClass("bg-warning");
            $("#originBookArea").show();
            $("#changeBookArea").empty();
            $("#submitUpdateBook").remove();
        } else {
            $(this).text("취소").removeClass("bg-warning").addClass("bg-danger");
            $(this).after("<span class='badge bg-success' id='submitUpdateBook'>제출</span>")
            let originBookCover = $("#originBookCover").attr("src")
            $("#originBookArea").hide();
            let output =
                "<div class='input-group mb-3'>" +
                "   <input type='text' class='form-control' placeholder='책 제목을 입력하세요(최대 20자)' maxlength='20' id='inputBookTitle'>" +
                "   <button type='button' class='btn btn-success' id='searchUpdateBook'>검색</button>" +
                "</div>" +
                "<select class='form-select mb-3' id='searchCondition' name='booktitle' style='display:none;'>" +
                "</select>" +
                "<div class='row mb-3'>" +
                "   <div id='searchedBookCover' style='display:none'>" +
                "       <span>수정 전</span><img id='originBookCoverShow' src='' alt='Book Cover' width='100px' height='150px' style='display:none;'>" +
                "       <span>수정 후</span><img id='bookCover' src='' alt='Book Cover' width='100px' height='150px' style='display:none;'>" +
                "   </div>" +
                "</div>" +
                "<input type='hidden' name='booktitle' id='submittedBookTitle'>" +
                "<input type='hidden' name='bookcover' id='inputBookCover'>"

            $("#changeBookArea").append(output)
        }

        $("#searchCondition").change(function () {
            let selectedOption = $(this).find("option:selected");
            let selectedOptionValue = selectedOption.val();
            let selectedCover = selectedOption.data("cover");

            console.log(selectedOptionValue);

            if (selectedCover) {
                $("#searchedBookCover").show();
                $("#bookCover").attr("src", selectedCover).show()
                $("#originBookCoverShow").attr("src", $("#originBookCover").attr("src")).show()
                $("#submittedBookTitle").val(selectedOptionValue);
                $("#inputBookCover").val(selectedCover);
            } else {
                $("#bookCover").hide();
            }
        }) // $("#searchCondition").change(function () end

        $("#submitUpdateBook").click(function() {
            let booktitle = $("#submittedBookTitle").val();
            let bookcover = $("#inputBookCover").val();

            $.ajax({
                url: "/study/update-enroll-book",
                method: "post",
                data: {
                    no:studyboardno,
                    booktitle : booktitle,
                    bookcover : bookcover
                },
                cache: false,
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("수정 성공")
                        location.reload();
                    } else {
                        alert("수정 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("책 수정 중 오류가 발생했습니다. 다시 시도해주세요")
                }
            })
        }) //$("#submitUpdateBook").click(function() end

    }) // $("#updateAim").click(function () end


    $("body").on("click", "#searchUpdateBook", function () {
        let title = $("#inputBookTitle").val()

        $.ajax({
            url: "search-book",
            method: "post",
            data: {title},
            cache: false,
            beforeSend: function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function (data) {
                console.log(data)
                if (data.length != 0) {
                    data.forEach(subject => {
                        let option = $("<option>").val(subject.title).text(subject.title).data("cover", subject.cover);
                        $("#searchCondition").append(option).css("display", "block")
                    })
                } else {
                    $("#inputBookTitle").val("검색 결과가 없습니다. 다른 검색어를 입력하세요");
                }
            },
            error: function(status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
                alert("책 검색 중 오류가 발생했습니다. 다시 시도해주세요")
            }
        })
    }) // $("body").on("click", "#searchUpdateBook", function () end

    $("body").on("click", ".deleteCommBtn", function() {
        //console.log($(this).data("deletecommno"));
        let no = $(this).data("deletecommno");
        let answer = confirm("정말 삭제하시겠습니까?")
        if (answer) {
            $.ajax({
                url: "/study/delete-comm",
                method: "post",
                data: {no : no},
                cache: false,
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("댓글 삭제 완료")
                        getStudyCommentList(studyboardno)
                    } else {
                        alert("댓글 삭제 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("댓글 삭제 중 오류가 발생했습니다. 다시 시도해주세요")
                }
            })
        }
    }) //    $("body").on("click", ".deleteCommBtn", function() end

}) //ready end

