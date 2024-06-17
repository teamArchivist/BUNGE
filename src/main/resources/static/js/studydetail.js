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
        let commentList = $('#commentList');
        commentList.empty(); // 기존 댓글 삭제

        comments.forEach(comment => {
            let commentHtml = `
                <div class="d-flex mb-2">
                    <div class="flex-shrink-0">
                        <img class="img-sm rounded-circle" src="/img/profile-photos/10.png" alt="Profile" loading="lazy">
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <div class="mb-1">
                            <a class="h6 btn-link">${comment.id}</a>
                            <small class="ms-2 text-body-secondary mb-0">${comment.created}</small>
                            &nbsp;
                            <button class="btn btn-icon btn-outline-primary btn-xs rounded-circle">
                                <ion-icon name="settings-outline"></ion-icon>
                            </button>
                            <button class="btn btn-icon btn-outline-warning btn-xs rounded-circle">
                                <ion-icon name="trash-outline"></ion-icon>
                            </button>
                        </div>
                        <span>${comment.content}</span>
                        <a class="btn btn-xs btn-outline-light">Reply</a>
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
                    applicantTd.innerHTML = `<img src="/img/profile-photos/1.png" class="img-sm rounded-circle border">
                                             <span>${application.applicationId}</span>`;
                    row.appendChild(applicantTd);

                    let reasonTd = document.createElement("td");
                    reasonTd.innerText = application.applicationContent;
                    row.appendChild(reasonTd);

                    let requestDateTd = document.createElement("td");
                    requestDateTd.innerText = application.requestdate;
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
                    actionsTd.className = "status-area text-center";
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
                    // 승인 상태 업데이트
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

    $("#startStudy").click(function() {
        let leaderId = $("#leaderId").text();
        let studystart = $("#challengeDate").data("challengestart");
        let studyend = $("#challengeDate").data("challengeend");
        let studyperiod = $("#challengeDate").data("challengeperiod");
        let booktitle = $("#booktitle").text();
        let memberIds = [];
        $(".member-id").each(function () {
            memberIds.push($(this).text().trim());
        });

        console.log(memberIds);

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
                                alert("스터디 도전이 시작되었습니다. 꼭 완독하세요!")
                                $("#startStudy").text("진행중").attr("disabled", true)
                                $("#studyApprove").remove()
                                $("#updateStudy").remove()
                                $("#deleteStudy").remove()
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
                        async: false,
                        success: function (data) {
                            if (data.status === "error") {
                                alert("참여 인원이 포함되지 않았습니다. 다시 시도해주세요.")
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


    })

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
                $("#studyApprove").remove()
                $("#updateStudy").remove()
                $("#deleteStudy").remove()
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


}) //ready end

