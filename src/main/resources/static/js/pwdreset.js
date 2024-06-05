$(function(){
    const patternpwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    
    $("input[name=pwd]").on('keyup', function(){
        const pwd = $(this).val().trim();
        
        if (pwd === "") {
            $("#pwd_message").css('color', 'red').text("비밀번호: 비밀번호는 필수입니다.");
            $(this).focus();
            return false;
        } else if (!patternpwd.test(pwd)) {
            $("#pwd_message").css('color', 'red').text("비밀번호: 8~16자의 영문 대/소문자, 특수문자를 사용해 주세요.(/제외)");
            return false;
        } else {
            $("#pwd_message").css('color', 'green').text("비밀번호: 안전합니다.");
            return true;
        }
    });

    $("input[name=pwdcheck]").on('keyup', function() {
        const pwd_check = $(this).val().trim();
        const pwd = $("input[name=pwd]").val().trim();
        
        if (pwd !== pwd_check) {
            $("#pwdcheckmessage").css('color', 'red').text("비밀번호가 일치하지 않습니다.");
            return false;
        } else if (!patternpwd.test(pwd_check)) {
            $("#pwdcheckmessage").css('color', 'red').text("비밀번호: 8~16자의 영문 대/소문자, 특수문자를 사용해 주세요.(/제외)");
            return false;
        } else {
            $("#pwdcheckmessage").css('color', 'green').text("비밀번호가 일치합니다.");
            return true;
        }
    });
    
    $("form").on('submit', function() {
        const pwd = $("input[name=pwd]").val().trim();
        const pwd_check = $("input[name=pwdcheck]").val().trim();
        
        if (!patternpwd.test(pwd)) {
            $("#pwdmessage").css('color', 'red').text("비밀번호: 8~16자의 영문 대/소문자, 특수문자를 사용해 주세요.(/제외)");
            return false;
        } else if (pwd !== pwdcheck) {
            $("#pwd_checkmessage").css('color', 'red').text("비밀번호가 일치하지 않습니다.");
            return false;
        } else {
            // 폼이 제출되도록 true 반환
            return true;
        }
    });
});
