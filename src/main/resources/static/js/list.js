
/*
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
 
 function go(page) {
	 const limit = $('#viewcount').val();
	 //const data = `limit=${limit}&state=ajax&page=${page}`;
	  const data = {limit:limit, state:"ajax", page:page}
	   ajax(data);
 }

function setPaging(href,digit){
	let active = "";
	let gray = "";
	
	if(href == "") {
		if(isNaN(digit)){//이전&nbsp; 또는 다음&nbsp;
			gray="gray"; //11,20번 라인 처럼 href 속성이 없고  <a>요소의 textnode가 숫자인 경우
		}else {
			active = "active"; //12,19번 라인 처럼 href 속성이 없고  <a>요소의 textnode가 숫자인 경우
		}
	}
	let output = `<li class="page-item ${active}">`;
	//let anchor = "<a class="page-link + gray + "'" href +">" + digit + "</a></li>"";
	let anchor = `<a class="page-link ${gray}" ${href}>${digit}</a></li>`;
	output += anchor;
	return output;
}

function ajax(sdata) {
	console.log(sdata)
	
	$.ajax({
		type : "GET",
		data : sdata,
		url	 : "list_ajax",
		dataType : "json",
		beforeSend : function(xhr)
        {   //데이터를 전송하기 전에 헤더에 csrf값을 설정합니다.
        	xhr.setRequestHeader(header, token);			
        },
		cache : false,
		success : function(data) {
			$("#viewcount").val(data.limit);
			$("thead").find("span").text("글 개수 : " + data.listcount);
			
			if (data.listcount > 0) { // 총 갯수가 0보다 큰 경우
			$("tbody").remove();
			let num =  data.listcount - (data.page -1) * data.limit;
			console.log(num)
			let output = "<tbody>";
			$(data.boardlist).each(
				function(index, item) {
					output +='<tr><td>' + (num--) + '</td>'
					const blank_count = item.board_re_lev * 2 + 1;
					let blank = '&nbsp;';
					for (let i=0; i < blank_count; i++) {
						
					}
					
					let img = "";
					if (item.board_re_lev > 0) {
						img = "<img src='image/line.gif'>";
					}
					
					let subject = item.board_subject;
					if (subject.length >= 20) {
						subject = subject.substr(0,20) + "..."; //0부터 20개의 부분 문자열 추출
					}
					
					output += "<td><div>" + blank + img
					output += ' <a href="detail?num=' + item.board_num + '">';
					output += subject + '<span class="gray">['+item.cnt+']</span></a></div></td>'
					output += '<td><div>' + item.board_name + '</div></td>'
					output += '<td><div>' + item.board_date + '</div></td>'
					output += '<td><div>' + item.board_readcount + '</div></td></tr>'
				})
				
			output += "</tbody>"
			$('table').append(output) //table 완성
			
			$(".pagination").empty(); //페이징 처리 영역 내용 제거
			output = "";
			
			let digit = '이전&nbsp;'
			let href = "";
			if (data.page > 1) {
				href ='href=javascript:go(' + (data.page -1) + ')';
			}
			output += setPaging(href, digit);
			
			for (let  i= data.startpage; i <= data.endpage; i ++) {
				digit = i;
				href = "";
				if(i !=data.page) 	{
				href = 'href=javascript:go(' + i + ')';
			}
			output += setPaging(href, digit);
		}
			
			digit = '&nbsp;다음&nbsp;';
			href = "";
			if(data.page < data.maxpage) 	{
				href = 'href=javascript:go(' + (data.page +1) + ')';
			}
			
			output += setPaging(href, digit);
			
			$('.pagination').append(output);
			}
		}, 
		error : function() {
			console.log('에러')
		}
	})
}
			*/
$(function() {
	$("#inquiry_add").click(function(){
		location.href="add-form";
	})

	$("#memberId").val($("#loginId").text());
	
//	$("#viewcount").change(function (){
//		go(1);//보여줄 페이지를 1페이지로 설정합니다.
//	});
})