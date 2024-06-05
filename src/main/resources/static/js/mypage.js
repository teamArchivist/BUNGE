let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function (){
    console.log($("#loginId").text());
    let id=$("#loginId").text();
    $.ajax({
        url: "myreview",
        data: id ,
        method: "post" ,
        cache: false ,
        beforeSend : function (xhr) {
            if (header && token) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (rdata) {
            //console.log("성공")
            //console.log(rdata)
            console.log(rdata.list);
            console.log(rdata.listcount);
            $(rdata.list).each(function (){
                console.log(this.cover);

            })
        }
    })
})