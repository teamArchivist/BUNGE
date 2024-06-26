$(function (){

    $(".submitBtn").prop("disabled", true);
    $("#emailVerifyButton").prop("disabled",true);

$("form").on('submit', function(event) {
    event.preventDefault();  // 기본 폼 제출 방지

    let isValid =  checkpwd() || checkcnick() || checkaddr() || checkpho() || checkemail() || emailcodecheck;
//  checkcnick() || checkaddr() || checkpho() ||
    if (!isValid) {
        $(".submitBtn").prop('disabled', true); // submitButton 비활성화
    } else {
        $(".submitBtn").prop('disabled', false);// submitButton 활성화
        this.submit(); // 폼 제출
    }
});

$("input").on("input", function (){

    let isValid = checkpwd() || checkcnick() || checkaddr() || checkpho() || checkemail() || emailcodecheck;

    if(!isValid){
        $(".submitBtn").prop("disabled",true);

    } else {
        $(".submitBtn").prop("disabled", false);
    }
})
    $("#pwdchange").on('keyup',checkpwd);//pwd 유효성 검사

    $("#nickchange").on('keyup',checkcnick);//nick 유효성 검사

    $("#addrchange").on('keyup',checkaddr);// 주소 유효성 검사

    $("#phochange").on('keyup' ,checkpho);// 핸드폰 유효성 검사

    $("#changeemail1").on('keyup',checkemail);// 이메일 유효성 검사

    $("#emailCode").on('keyup',emailcodecheck);//이메일 인증 검사

let generatedCode = '';
let isValid = false;

    function checkpwd(){
        const patternpwd =/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
        const pwd = $("#pwdchange").val().trim();
        if(pwd === "") {
            $("#pwd_message").css('color' , ' red').html("비밀번호: 변경할 비밀번호을 입력하세요.").show();
            isValid = false;
        } else if (!patternpwd.test(pwd)) {
            $("#pwd_message").css('color', 'red').html("비밀번호:8~16자의 영문 대/소문자, 특수문자를 사용해 주세요.(/제외) ").show();
            isValid = false;
        }else {
            $("#pwd_message").hide();
            isValid = true;
        }
        return isValid;
    }

    function checkcnick(){
        const patternick = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,10}$/;
        const nick = $("#nickchange").val().trim();
        if(nick === "") {
            $("#nick_message").css('color', 'red').html("닉네임 : 변경할 닉네임을 입력하세요.").show();
            return false;
        }else if(!patternick.test(nick)){
            $("#nick_message").css('color', 'red').html("닉네임: 닉네임은 한글, 영문, 숫자만 가능하며 2-10자리 가능합니다.").show();
            return false;
        }//nick 유효성 end

        let isValid = false;
        $.ajax ({
            url : "checknick" ,
            data : {"nick" : nick} ,
            async: false ,
            success : function(nick) {
                if (!nick) {
                    $("#nick_message").hide();
                    isValid = true;
                }else {
                    $("#nick_message").css('color', 'red').text("닉네임 : 사용할수 없는 닉네임 입니다. 다른 닉네임를 입력해주세요.").show();
                    isValid = false;
                }
            }
        }); //ajaxnick end
        return isValid;
    }//end

    function checkaddr(){
        const addr2 = $("#addrchange").val();
        if(addr2 === "") {
            $("#addr2_message").css('color','red').text("변경할 주소를 입려하세요.").show();
            isValid = false;
        } else {
            $("#addr2_message").hide();
            isValid = true;
        }
        return isValid;
    };

    function checkpho(){
        const phone = $("#phochange").val().trim();
        const patterpho = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{5})$/;
        if(phone === "") {
            $("#pho_message").css('color', 'red').text("전화번호 : 변경할 전화번호를 입력하세요.").show();
            isValid = false;
        } else if(!patterpho.test(phone)) {
            $("#pho_message").css('color', 'red').text("전화번호 : 전화번호 형식에 맞지 않습니다.").show();
            isValid = false;
        }else {
            $("#pho_message").hide();
            isValid = true;
        }
        return isValid;
    };//전화번호 유효성 검사 end

    function checkemail() {
        const email = $("#changeemail1").val().trim();
        const patternEmail = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
        if (email === "") {
            $("#email_message").css('color', 'red').text("이메일 : 변경할 이메일을 입력하세요.").show();
            $("#emailVerifyButton").prop('disabled', true);
            return false;

        }else if (!patternEmail.test(email)) {
            $("#email_message").css('color', 'red').text("이메일 : 이메일 형식에 올바르지 않습니다.").show();
            $("#emailVerifyButton").prop('disabled', true);
            return false;
        }
        //이메일 중복검사
        $.ajax({
            url: "checkemail",
            data: { "email": email },
            success: function (emck) {
                if (!emck) {
                    $("#email_message").hide();
                    $("#emailVerifyButton").prop('disabled',false);
                    $(".submitBtn").prop("disabled",false);
                    isValid = true;
                } else {
                    $("#email_message").css('color', 'red').text("이메일 : 사용할 수 없는 이메일입니다. 다른 이메일을 입력해주세요.").show();
                    $("#emailVerifyButton").prop('disabled', true);
                    $(".submitBtn").prop("disabled",true);
                    isValid = false;
                }
            }
        });
        return isValid;
    };

    //이메일 인증코드 발송
    $('#emailVerifyButton').click(function () {
        const email = $('#changeemail1').val().trim();
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
                    return;
                },
                error: function () {
                    $('#email_message').text("인증 코드 발송이 실패했습니다. 다시 시도해주세요.");
                    return;
                }
            });
        } else {
            $('#email_message').text("이메일을 입력해 주세요.");
            $('#emailCode').hide();
            return;
        }
    });

    //이메일 인증 코드 비교
  function emailcodecheck(){
        const emailcode = $("#emailCode").val().trim();
        if (emailcode === generatedCode) {
            $("#emailcode_message").css('color', 'green').text("인증되었습니다.");
        } else {
            $("#emailcode_message").css('color', 'red').text("인증코드가 일치하지 않습니다.");
        }
    };

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