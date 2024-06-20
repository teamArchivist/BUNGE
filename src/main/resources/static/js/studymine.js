let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

window.showReportModal = function (memberId) {
    const reporterId = $("#loginId").text();
    const currentDate = new Date().toISOString().slice(0, 10);

    $("#reportMemberId").val(memberId);
    $("#reporterId").val(reporterId);
    $("#reportDate").val(currentDate);

    $("#reportModal").modal("show");
}

function resetReportModal() {
    document.getElementById("reportForm").reset();
    document.getElementById("reportMemberId").value = '';
    document.getElementById("reporterId").value = '';
    document.getElementById("reportDate").value = new Date().toLocaleDateString();
}

function submitReport() {
    // 모달에서 입력된 신고 정보 가져오기
    const reportMemberId = document.getElementById("reportMemberId").value;
    const reporterId = document.getElementById("reporterId").value;
    console.log(reportMemberId)
    console.log(reporterId)

    // 체크된 신고 사유 가져오기
    const reportReasons = [];
    document.querySelectorAll('input[name="reportreason"]:checked').forEach((checkbox) => {
        reportReasons.push(checkbox.nextElementSibling.textContent.trim());
    });
    console.log(reportReasons)

    // 신고 내용을 JSON 객체로 구성
    const reportData = {
        reporttargetid: reportMemberId,
        reporterid: reporterId,
        reportreason: reportReasons
    };

    // AJAX 요청 보내기
    $.ajax({
        url: '/study/report', // 서버의 신고 처리 엔드포인트
        type: 'POST',
        data: reportData,
        beforeSend: function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function(response) {
            if (response === 1) {
                $('#reportModal').modal('hide');
                alert('신고가 성공적으로 제출되었습니다.');
            }
        },
        error: function(error) {
            alert('신고 제출 중 오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
}

window.showModifyBookModal = function () {
    $("#modifyBookModal").modal("show");
}

window.resetModifyBookModal = function () {
    $("#inputBookTitle").val("");
    $("#inputAuthor").val("");
    $("#searchCondition").empty();
    $("#bookCover").hide();
    $("#searchBookInfo").css("display", "none");
    $("#searchCategoryName").css("display", "none");
    $("#searchPage").css("display", "none");
    $("#searchPubDate").css("display", "none");
    $("#searchCondition").css("display", "none")
}

$(function () {
    let urlParams = new URLSearchParams(window.location.search);
    let studyboardno = urlParams.get("studyboardno");
    let studyStatus = $("#studyStatus").val();

    if (studyStatus === 'end') {
        $("#modifyChallengeBook").attr("disabled", "true");
        $(".report").attr("disabled", "true");
        $(".ban").attr("disabled", "true");
        $("#addEventBtn").attr("disabled", "true");
        $(".deleteEvent").css("display", "none");

    }

    $("#reportModal").on("hidden.bs.modal", function () {
        resetReportModal();
    })

    $("#modifyBookModal").on("hidden.bs.modal", function () {
        resetModifyBookModal();
    });

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
                //console.log(rdata);
                $("#searchCondition").css("display", "block");
                if (rdata.length != 0) {
                    rdata.forEach(subject => {
                        console.log(subject)
                        let option = $("<option>").val(subject.title).text(subject.title).data("cover", subject.cover);
                        $("#searchCondition").append(option);
                        $("#bookCover").attr("src", subject.cover).css("display", "block")

                        $("#searchAuthor").text("지은이 : " + subject.author).css("display", "block")
                        $("#searchCategoryName").text("카테고리 : " + subject.categoryName).css("display", "block")
                        $("#searchPage").text("페이지 : " + subject.page).css("display", "block")
                        $("#searchPubDate").text("출간일 : " + subject.pubDate).css("display", "block")
                        $("#searchBookInfo").css("display", "block")

                        $("#approvalBookTitle").val(subject.title);

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
                } else if (rdata.length == 0) {
                    let option = $("<option>").text("검색 결과가 없습니다").val(null)
                    $("#searchCondition").append(option)
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

    $("body").on("click", "#submitChangeBook", function () {
        alert("책 수정 요청이 전송되었습니다. 리더가 승인하면 도전 책이 변경됩니다.");
        let approvalBookTitle = $("#approvalBookTitle").val();
        let approvalContent = $("#approvalContent").val();

        $.ajax({
            url: "/study/submit-change-book",
            method: "post",
            cache: false,
            beforeSend: function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            data: {
                studyboardno: studyboardno,
                sort: "책수정",
                approvalBookTitle: approvalBookTitle,
                approvalContent: approvalContent,
                proposer: $("#loginId").text(),
            },
            success: function (data) {
                    if (data.status === "success") {
                        location.reload();
                    }
            },
            error: function(status, error) {
                console.log("ajax 요청 실패")
                console.log("상태:" + status)
                console.log("오류:" + error)
                alert("도전 책 변경 요청 중 오류 발생. 다시 시도해주세요")
            }
        })
    }) //$("body").on("click", "#submitChangeBook", function () end

    $("body").on("click", ".approval-content-button", function () {
        let no = $(this).data("no");
        //console.log(no);
        showApprovalContentModal(no);
    });

    function showApprovalContentModal(no) {
        $.ajax({
            url: "/study/get-approval-content",
            method: "GET",
            data: { no: no },
            success: function(data) {
                //console.log(data);
                if (data.status === "success") {
                    let originBookTitle = $("#originBookTitle").val();
                    let originBookCover = $("#originBookCover").val();
                    let originAuthor = $("#originAuthor").val();
                    let originPage = $("#originPage").val();
                    let originPubDate = $("#originPubDate").val();

                    let approval = data.approval;
                    let modalBody =
                        "<div class='row mb-3'>" +
                        "   <div class='col-md-6'><span class='text-decoration-underline fw-bold fs-4'>변경 전</span>" +
                        "       <div class='row'>" +
                        "           <div class='col-md-5'>" +
                        "               <img src='" + originBookCover + "' height='200px'>" +
                        "           </div>" +
                        "           <div class='col-md-5'>" +
                        "               <p>" + originBookTitle + "</p>" +
                        "               <p>" + originAuthor + "</p>" +
                        "               <p>page : " + originPage + "</p>" +
                        "               <p>출간일 : " + originPubDate + "</p>" +
                        "           </div>" +
                        "       </div>" +
                        "   </div>" +
                        "   <div class='col-md-6'><span class='text-decoration-underline fw-bold fs-4'>변경 후</span>" +
                        "       <div class='row'>" +
                        "           <div class='col-md-5'>" +
                        "               <img src='" + approval.cover + "' height='200px'>" +
                        "           </div>" +
                        "           <div class='col-md-5'>" +
                        "               <p id='approvedBookTitle'>" + approval.approvalBookTitle + "</p>" +
                        "               <p>" + approval.author + "</p>" +
                        "               <p>page : " + approval.page + "</p>" +
                        "               <p>출간일 : " + approval.pubdate + "</p>" +
                        "           </div>" +
                        "       </div>" +
                        "   </div>" +
                        "</div>" +
                        "<hr>" +
                        "<div class='row'>" +
                        "   <table class='table table-bordered'>" +
                        "       <thead>" +
                        "           <th class='text-center'>번호</th>" +
                        "           <th class='text-center'>종류</th>" +
                        "           <th class='text-center'>제안내용</th>" +
                        "           <th class='text-center'>제안자</th>" +
                        "           <th class='text-center'>제안일</th>" +
                        "       </thead>" +
                        "       <tbody>" +
                        "           <tr>" +
                        "               <td class='text-center' id='approvalno'>" + approval.no + "</td>" +
                        "               <td class='text-center'>" + approval.sort + "</td>" +
                        "               <td class='text-center'>" + approval.approvalContent + "</td>" +
                        "               <td class='text-center'>" + approval.proposer + "</td>" +
                        "               <td class='text-center'>" + approval.proposeDate + "</td>" +
                        "           </tr>" +
                        "       </tbody>" +
                        "   </table>" +
                        "</div>"

                    $("#approvalBookContent").html(modalBody)
                    $("#approvalContentModal").modal("show");
                } else {
                    alert("데이터를 가져오는 데 실패했습니다. 다시 시도해주세요.");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching approval content:", error);
                alert("데이터를 가져오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    } //function showApprovalContentModal(no) end

    $("body").on("click", "#acceptApproval", function () {
        let no = $("#approvalno").text();
        let approvedBookTitle = $("#approvedBookTitle").text();
        console.log(no);
        console.log(approvedBookTitle);
        let answer = confirm("'확인'을 누르면 목표 책이 변경됩니다");
        if (answer) {
            $.ajax({
                url: "/study/accept-approval",
                method: "post",
                data: {
                    no : no,
                    studyboardno : studyboardno,
                    approvalBookTitle : approvedBookTitle,
                    approvalStatus : "승인"
                },
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("승인 완료")
                        location.reload();
                    } else {
                        alert("승인 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("승인 중 오류 발생. 다시 시도해주세요")
                }

            })

        }
    }) // $("body").on("click", "#acceptApproval", function ()

    $("body").on("click", "#rejectApproval", function () {
        let answer = confirm("'확인'을 누르면 제안된 내용이 거절됩니다")
        let no = $("#approvalno").text();

        if (answer) {
            $.ajax({
                url: "/study/reject-approval",
                method: "post",
                data: {
                    no: no,
                    approvalStatus: "거절"
                },
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function (data) {
                    if (data === 1) {
                        alert("거절 완료")
                        location.reload();
                    } else {
                        alert("거절 실패. 다시 시도해주세요")
                    }
                },
                error: function(status, error) {
                    console.log("ajax 요청 실패")
                    console.log("상태:" + status)
                    console.log("오류:" + error)
                    alert("거절 중 오류 발생. 다시 시도해주세요")
                }
            })
        }
    }) //$("body").on("click", "#rejectApproval", function ()

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


    // 공지사항 목록 로드
        function loadNotices() {
            $.ajax({
                url: '/study/notices/' + studyboardno,
                method: 'GET',
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    const noticeTable = $('#noticeTable');
                    noticeTable.empty();

                    if (response.count === 0) {
                        noticeTable.append('<tr><td colspan="5">공지사항이 없습니다.</td></tr>');
                    } else {
                        response.notices.forEach(notice => {
                            const noticeRow = `
                        <tr>
                            <td style="padding-top: 20px;padding-bottom: 0px;">${notice.noticeNo}</td>
                            <td style="padding-top: 20px;padding-bottom: 0px;"><a href="#" class="notice-title" data-id="${notice.noticeId}">${notice.title}</a></td>
                            <td style="padding-top: 20px;padding-bottom: 0px;">${notice.authorId}</td>
                            <td style="padding-top: 20px;padding-bottom: 0px;">${notice.createdAt}</td>
                            <td>
                            <button class="btn btn-icon btn-sm btn-hover btn-primary dropdown" data-bs-toggle="dropdown"
                                    aria-expanded="false" style="float: right">
                                <i class="demo-pli-dot-horizontal fs-4"></i>
                                    <span class="visually-hidden">Toggle Dropdown</span>
                            </button>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li>
                                        <a href="#" class="dropdown-item editNotice" data-id="${notice.noticeId}">
                                        <i class="demo-pli-pen-5 fs-5 me-2"></i> 수정
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" class="dropdown-item deleteNotice text-danger fw-semibold" data-id="${notice.noticeId}">
                                        <i class="demo-psi-recycling fs-5 me-2"></i> 삭제
                                    </a>
                                    </li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr id="notice-content-${notice.noticeId}" class="notice-content-row" style="display: none;">
                            <td colspan="5"><div class="notice-content">${notice.content}</div></td>
                        </tr>`;
                            noticeTable.append(noticeRow);
                        });

                        // 제목 클릭 이벤트 핸들러 추가
                        $('.notice-title').on('click', function(event) {
                            event.preventDefault();
                            const noticeId = $(this).data('id');
                            const contentRow = $(`#notice-content-${noticeId}`);
                            contentRow.toggle();
                        });

                        // 수정 버튼 클릭 이벤트 핸들러 추가
                        $('.editNotice').on('click', function() {
                            const noticeId = $(this).data('id');
                            loadNoticeForEdit(noticeId);
                        });

                        // 삭제 버튼 클릭 이벤트 핸들러 추가
                        $('.deleteNotice').on('click', function() {
                            const noticeId = $(this).data('id');
                            deleteNotice(noticeId);
                        });
                    }
                },
                error: function(error) {
                    console.error("공지사항 로드에 실패했습니다", error);
                }
            });
        }

        // 공지사항 수정 모달 로드
        function loadNoticeForEdit(noticeId) {
            $.ajax({
                url: '/study/notice/' + noticeId,
                method: 'GET',
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    $('#modalTitle').text('공지사항 수정');
                    $('#noticeLeaderId').val(response.authorId);
                    $('#noticeTitle').val(response.title);
                    $('#noticeContent').val(response.content);
                    $('#charCount').text(response.content.length);

                    $('#addButton').attr('data-action', 'edit').data('id', noticeId).text('수정');

                    $('#noticeModal').modal('show');
                },
                error: function(error) {
                    console.error("공지사항 불러오기에 실패했습니다", error);
                }
            });
        }

    // 공지사항 수정
    function editNotice() {
        const noticeId = $('#addButton').data('id');
        const notice = {
            noticeId: noticeId,
            title: $('#noticeTitle').val(),
            content: $('#noticeContent').val(),
            authorId: $('#noticeLeaderId').val()
        };

        console.log('Sending PUT request to /study/edit-notice with data:', notice);

        $.ajax({
            url: '/study/edit-notice',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(notice),
            beforeSend: function (xhr) {
                if (header && token) {
                    xhr.setRequestHeader(header, token);
                }
            },
            success: function(response) {
                console.log('Response:', response); // 디버깅 로그 추가
                if (response.status === 'success') {
                    alert('공지사항이 수정되었습니다.');
                    $('#noticeModal').modal('hide');
                    loadNotices();
                } else {
                    alert("공지사항 수정에 실패했습니다: " + response.message);
                }
            },
            error: function(error) {
                alert("공지사항 수정에 실패했습니다: " + (error.responseJSON ? error.responseJSON.message : "Unknown error"));
                console.error("Error:", error);
            }
        });
    }

    // 공지사항 삭제
        function deleteNotice(noticeId) {
            if (!confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
                return;
            }

            $.ajax({
                url: '/study/delete-notice/' + noticeId,
                method: 'DELETE',
                beforeSend: function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success: function(response) {
                    if (response.status === 'success') {
                        alert('공지사항이 삭제되었습니다.');
                        loadNotices();
                    } else {
                        alert("공지사항 삭제에 실패했습니다: " + response.message);
                    }
                },
                error: function(error) {
                    alert("공지사항 삭제에 실패했습니다: " + (error.responseJSON ? error.responseJSON.message : "Unknown error"));
                    console.error("Error:", error);
                }
            });
        }

        // 공지사항 추가 모달 설정
        $('#addNoticeButton').on('click', function() {
            $('#modalTitle').text('공지사항 추가');
            $('#addButton').attr('data-action', 'add').removeData('id').text('등록');
            let authorId = $('#loginId').text(); // 로그인된 아이디 가져오기
            $('#noticeLeaderId').val(authorId); // 작성자 필드에 설정

            // 입력 필드 초기화
            $('#noticeTitle').val('');
            $('#noticeContent').val('');
            $('#charCount').text(0); // 글자 수 초기화
            $('#addButtonGroup').show();
            $('#editButtonGroup').hide();

            $('#noticeModal').modal('show');
        });

        // 키업 이벤트 핸들러
        $('#noticeContent').on('keyup', function() {
            let charCount = $(this).val().length;
            $('#charCount').text(charCount);
            console.log('Characters counted:', charCount); // 디버깅 로그 추가
        });

        // 공지사항 등록 및 수정
        $('#addButton').on('click', function() {
            const action = $(this).attr('data-action');

            if (action === 'add') {
                const notice = {
                    title: $('#noticeTitle').val(),
                    content: $('#noticeContent').val(),
                    authorId: $('#noticeLeaderId').val(),
                    studyboardno: studyboardno
                };

                console.log("Sending notice:", notice); // 디버깅 로그 추가

                $.ajax({
                    url: '/study/add-notice',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(notice),
                    beforeSend: function (xhr) {
                        if (header && token) {
                            xhr.setRequestHeader(header, token);
                        }
                    },
                    success: function(response) {
                        if (response.status === 'success') {
                            alert('공지사항이 추가되었습니다.');
                            $('#noticeModal').modal('hide'); // 모달창 닫기
                            loadNotices(); // 공지사항 목록 다시 로드
                        } else {
                            alert("공지사항 추가에 실패했습니다: " + response.message);
                        }
                    },
                    error: function(error) {
                        alert("공지사항 추가에 실패했습니다: " + (error.responseJSON ? error.responseJSON.message : "Unknown error"));
                        console.error("Error:", error); // 서버 응답 로그 출력
                    }
                });
            } else if (action === 'edit') {
                editNotice();
            }
        });

        // 페이지 로드 시 공지사항 목록 로드
        loadNotices();


    $("#showAll").click(function () {
        location.href = "/study/mine-list"
    })

}) //ready end