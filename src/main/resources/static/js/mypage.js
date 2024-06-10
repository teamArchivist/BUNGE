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
            $(rdata.list).each(function () {
                console.log(this.cover);
            });
        }
    });
});
