$(function() {

	/*
		헤더의 
		<span id="loginid">${pinfo.username}</span>님(로그아웃)
		깂을 글쓴이 값으로 설정합니다.
	*/
	console.log($("#loginId").text());
	$("#memberId").val($("#loginId").text());

	
//	$("#upfile").change(function(){
//		console.log($(this).val())
//		const inputfile = $(this).val().split('\\');
//		$('#filevalue').text(inputfile[inputfile.length -1]);
//	});
	
	//submit 버튼 클릭할 때 이벤트 부분
	$("form[name=inquiryform]").submit(function(){
		if($.trim($("#title").val()) == "") {
		alert("제목을 입력하세요.");
		$("#title").focus();
		return false;
	}

		// 다양한 빈 태그들을 확인하기 위한 정규 표현식
		var emptyContentPattern = /^(\s*<p><br><\/p>\s*|\s*<div><br><\/div>\s*|\s*<h[1-6]><br><\/h[1-6]>\s*)+$/;

		if (emptyContentPattern.test(content) || content === "") {
			alert("내용을 입력하세요.");
			$("#_dm-quillCustomToolbar").focus();
			return false;
		}

	// if($.trim($("#_dm-quillCustomToolbar").find('p').text()) == "") {
	// 	alert("내용을 입력하세요.");
	// 	$("#_dm-quillCustomToolbar").focus();
	// 	return false;
	// }
	
	if($.trim($("#email").val()) == "") {
		alert("이메일을 입력하세요.");
		$("#email").focus();
		return false;
	}



	if($.trim($("#typeId").val()) == 0) {
		alert("문의유형을 선택하세요.");
		$("#typeId").focus();
		return false;
	}
  });
 
	$(".btn-danger").click(function(){
		 history.go(-1);
		 });

});