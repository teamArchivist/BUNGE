$(function (){
    $("form[name=loginform]").on('submit', function (){
        const successmessage = $(this).find("[name=successmessage]").val(); // 로그인 성공 메시지// 로그인 실패 메시지
        const loginfail = $(this).find("[name=loginfail]").val(); // 로그인 실패 메시지
        if(successmessage) {
            alert(successmessage);
        }else if(loginfail === 'loginFilMsg'){
            alert("아이디나 비밀번호가 틀렸습니다.");
        }
    })
})