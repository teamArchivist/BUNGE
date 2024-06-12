let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function () {

    let generatedCode = '';

    //이메일 인증코드 발송
    $('#emailVerifyButton').click(function () {
        const email = $('#changeemail1').val().trim();
        console.log(email+"이메일");
        if (email !== "") {
            $.ajax({
                url: "mymaildelivery",
                type: "get",
                data: { email: email },
                success: function (response) {
                    console.log("인증코드" + response);
                    generatedCode = response;
                    $("#emailCode").attr('type', 'text').val('');
                    $("#emailcode_message").text("인증코드가 발송되었습니다.");
                },
                error: function () {
                    $('#email_message').text("인증 코드 발송이 실패했습니다. 다시 시도해주세요.");
                }
            });
        } else {
            $('#email_message').text("이메일을 입력해 주세요.");
            $('#emailCode').hide();
        }
    });

    //이메일 인증 코드 비교
    $("input[name=emailcode]").on('keyup', function () {
        const emailcode = $(this).val().trim();
        if (emailcode === generatedCode) {
            $("#emailcode_message").css('color', 'green').text("인증되었습니다.");
        } else {
            $("#emailcode_message").css('color', 'red').text("인증코드가 일치하지 않습니다.");
        }
    });

    // Example AJAX request (not directly related to email verification)
    console.log($("#loginId").text());
    let id = $("#loginId").text();
    $.ajax({
        url: "myreview",
        data: id,
        method: "post",
        cache: false,
        beforeSend: function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (rdata) {
            console.log(rdata.list);
            console.log(rdata.listcount);
            console.log(rdata.list[0]);
            console.log($(rdata.list[0].list));
            console.log($(rdata.list[0].list.cover));

            for (let i = 0; i < rdata.list.length; i++) {
                $.each(rdata.list[i].list, function(index, item) {
                    let output = "<div class='d-flex mb-4'>"
                        + "<div class='flex-shrink-0'>"
                        + "<img class='img-sm rounded-circle' src='" + item.cover + "' alt='북커버이미지' loading='lazy'>"
                        + "</div>"
                        + "<div class='flex-grow-1 ms-3'>"
                        + "<div class='mb-1'>"
                        + "<a class='h6 btn-link'>" + item.linetitle + "</a>"
                        + "</div>"
                        + "<small class='d-block text-body-secondary mb-2'>" + item.lastmodified + "</small>"
                        + "<p>" + item.content + "</p>"
                        + "<div class='d-flex gap-4 mb-4'>"
                        + "<i class='text-body-secondary demo-pli-heart-2 fs-5 me-2'></i>" + item.countLike
                        + "<i class='text-body-secondary demo-pli-speech-bubble-4 fs-5 me-2'></i>" + item.countReview
                        + "</div>"
                        + "</div>"
                        + "</div>";

                    $("#_dm-tabs-review").append(output);
                });
            }
        }
    });
    $.ajax({
        url: "myinquiry",
        data: id,
        method: "post",
        cache: false,
        beforeSend: function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (rdata) {
            console.log(rdata);
            console.log(rdata.list);
            console.log(rdata.listcount);
            console.log(rdata.list[0].typeName)

        for (let i = 0; i < rdata.list.length; i++) {
        $.each(rdata.list[i], function(index, item) {
            let output ="<div class='d-flex mb-4'>"
                 + "<div class='flex-grow-1 ms-3'>"
                 + "<div class='mb-1>'"
                 + "<a class='h6 btn-link'>"+ +"</a>"
                 + "</div>"
                 + "<div class='d-flex align-items-center position-relative'>"
                 + "<div class='flex-shrink-0'>"
                 + ""
                 + "</div>"
                 + "<div class='flex-grow-1 ms-2'>"
                 + "<a></a>"
                 + "</div>"
                 + "</div>"
                 + "<p>"+item.typeName+"</p>"
                 + "</div>"
                 + "</div>";

            $("#_dm-tabs-inquiry").append(output);
            });
          }
        }
    });

    $("input[name=postcode]").click(function() {
        //window.open('post.html','post','width=500,height=200 ,scrollbars=yes');
        Postcode();
    });//$('#postcode').click

    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function Postcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                console.log(data.zonecode)

                var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 도로명 조합형 주소 변수

                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $("input[name=zipcode]").val(data.zonecode);
                $("input[name=addr1]").val(fullRoadAddr);
            }
        }).open();
    }//function Postcode()
});
