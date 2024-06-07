$(function(){
	//회원가입
	$("form[name=join]").on('submit', function(){
		let response = $(this).find("[name=result]").val();
		if(response === 'joinSuccess') {
			alert("회원가입을 축하드립니다.");
		}
	});
	//id keyup 시작
	$("input[name=id]").on('keyup',
		function(){
			const patternid = /^\w{5,15}$/;
			const id = $(this).val().trim();
			//id 유효성 검사
			if(id === "") {
				$("#id_message").css('color' , 'red').text('아이디 : 아이디는 필수 입니다.').show();
				($("input[name=id]")).focus();
				return false;
			} else if(!patternid.test(id)) {
				$("#id_message").css('color' , 'red').text('아이디 : 5~15자의 영문, 소문자, 숫자로만 사용 가능합니다.').show();
				return false;
			}
			//id 유효성 검사
		$.ajax({
				url : "checkid" ,
				data : {"id" : id} ,
				success : function(idck) {
					if (!idck) {
						$("#id_message").hide();
						return true;
					}else {
						$("#id_message").css('color', 'red').text("아이디 : 사용할수 없는 아이디입니다. 다른 아이디를 입력해주세요.").show();
						return false;
					}
				}
		}); //ajaxid end
	})//id keyup end
	
	//pwd 유효성 검사
$("input[name=pwd]").on('keyup',
	function(){
		const patternpwd =/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
		const pwd = $(this).val().trim();
		
		if(pwd === "") {
			$("#pwd_message").css('color' , ' red').html("비밀번호: 비밀번호는 필수입니다.").show();
			($("input[name=pwd]")).focus();
			return false;
		} else if (!patternpwd.test(pwd)) {
			$("#pwd_message").css('color', 'red').html("비밀번호:8~16자의 영문 대/소문자, 특수문자를 사용해 주세요.(/제외) ").show();
			return false;
		}else {
			$("#pwd_message").hide();
			return true;
		}
	});//pwd 유효성 검사	end
	
	//name 유효성 검사
$("input[name=name]").on('keyup',
	function(){
		const patternname = /^[가-힣]{2,4}$/;
		const name = $(this).val().trim();
		if(name === "") {
			$("#name_message").css('color', 'red').html("이름 : 이름은 필수 입니다.").show();
		}else if(!patternname.test(name)){
			$("#name_message").css('color', 'red').text("이름 : 한글 이름 2~4자 이내로 입력하세요.").show();
		}else {
			$("#name_message").hide();
		}
	});//name 유효성 검사 end
	
	//nick 유효성 검사 
$("input[name=nick]").on('keyup' ,
	function(){
		const patternick = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,10}$/;
		const nick = $(this).val().trim();
		if(nick === "") {
			$("#nick_message").css('color', 'red').html("닉네임 : 닉네임은 필수 입니다.").show();
			($("input[name=nick]")).focus();
			return false;
		}else if(!patternick.test(nick)){
			$("#nick_message").css('color', 'red').html("닉네임: 닉네임은 한글, 영문, 숫자만 가능하며 2-10자리 가능합니다.").show();
			return false;
		}//nick 유효성 end
		
		$.ajax ({
				url : "checknick" ,
				data : {"nick" : nick} ,
				success : function(nick) {
					if (!nick) {
						$("#nick_message").hide();
						return true;
					}else {
						$("#nick_message").css('color', 'red').text("닉네임 : 사용할수 없는 닉네임 입니다. 다른 닉네임를 입력해주세요.").show();
						return false;
					}
				}
		}); //ajaxnick end
	});//nick keyup end
	
	//성별 유효성 검사
$("input[name=gender]").click(function(){
		const gender = $("input[name=gender]:checked").val();
	if(!gender) {
		$("#gender_message").css('color', 'red').text('성별 : 성별을 선택하세요.').show();
		return false;
	} else {
		$("#gender_message").css('color','green').text('성별: 체크완료');
		return true;
	}
});//성별 유효성 검사 end

//우편 번호 유효성 검사
$("input[name=addr2]").on('keyup',
	function(){
		const addr2 = $(this).val();
		if(addr2 === "") {
			$("#addr2_message").css('color','red').text("상세주소 : 주소를 입력해주세요.").show();
			return false;
		} else {
			$("#addr2_message").hide();
			return true;
		}
});

//전화번호 유효성 검사
$("input[name=phone]").on('keyup' ,
	function(){
		const phone = $(this).val().trim();
		const patterpho = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
			if(phone === "") {
				$("#pho_message").css('color', 'red').text("전화번호 : 전화번호는 필수 입니다.").show();
				return false;
			} else if(!patterpho.test(phone)) {
				$("#pho_message").css('color', 'red').text("전화번호 :  전화번호 형식에 맞지 않습니다.").show();
				return false;
			}else {
				$("#pho_message").hide();
				return true;
			}
	});//전화번호 유효성 검사 end
	
//이메일 유효성 검사
	let generatedCode = "";
	$(function () {
		$("input[name=email]").on('keyup', function () {
			const email = $(this).val().trim();
			const patternEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

			if (email === "") {
				$("#email_message").css('color', 'red').text("이메일 : 이메일은 필수 입니다.").show();
				$("input[name=email]").focus();
				$("#emailVerifyButton").prop('disabled', true);
				return;
			} else if (!patternEmail.test(email)) {
				$("#email_message").css('color', 'red').text("이메일 : 이메일 형식에 올바르지 않습니다.").show();
				$("#emailVerifyButton").prop('disabled', true);
				return;
			}
			//이메일 중복검사
			$.ajax({
				url: "checkemail",
				data: { "email": email },
				success: function (emck) {
					if (!emck) {
						$("#email_message").hide();
						$("#emailVerifyButton").prop('disabled',false);
					} else {
						$("#email_message").css('color', 'red').text("이메일 : 사용할 수 없는 이메일입니다. 다른 이메일을 입력해주세요.").show();
						$("#emailVerifyButton").prop('disabled', true);
					}
				}
			});
		});
	//이메일 인증코드 발송
		$('#emailVerifyButton').click(function () {
			const email = $('input[name="email"]').val().trim();
			if (email !== "") {
				$.ajax({
					url: "maildelivery",
					type: "get",
					data: { email: email},
					success: function (response) {
						console.log("인증코드"+response);
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
		//이메일인증 코드 비교
		$("input[name=emailcode]").on('keyup', function () {
			const emailcode = $(this).val().trim();
			if (emailcode === generatedCode) {
				$("#emailcode_message").css('color', 'green').text("인증되었습니다.");
				return true;
			} else {
				$("#emailcode_message").css('color', 'red').text("인증코드가 일치하지 않습니다.");
				return false;
			}
		});
	});
	//생년월일 유효성 검사 
	$("input[name=birthdate]").on('input',
	function(){
		const btrdate = $(this).val().trim();
		if(btrdate ==="") {
			$("#birth_message").css('color', 'red').text("생년월일 : 생년월일은 필수 입니다.").show();
			return false;
		}else {
				$("#birth_message").hide();
			return true;
			}
		}); //생년월일 유효성 검사 end
	
		//우편번호 검색 버튼 클릭
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