$(function() {

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
       url: "memberlistto" ,
       method: "get" ,
       cache: false ,
       success: function (rdata) {
           console.log(rdata);
           $(rdata).each(function (){
            let output ="<tr>"
                + "<td><img class='img-sm rounded' src='"+this.profile+"' alt='thumbs'></td>"
                + "<td><a class='btn-link text-body-emphasis text-decoration-underline text-truncate mb-0'>"+this.id+"</a></td>"
                + "<td><span class='text-nowrap text-body-secondary'>"+this.name+"</span></td>"
                + "<td>"+this.nick+"</td>"
                + "<td><a class='btn-link text-nowrap'>"+this.addr1+"</a></td>"
                + "<td>맴버권한</td>"
                + "<td>"
                + "<div class='text-nowrap text-center'>"
                + "<a class='btn btn-icon btn-sm btn-hover bg-body-tertiary' data-bs-toggle='modal' data-bs-target='#authority'>"
                + "<i class='demo-pli-gear fs-5'></i></a>"
                +"</div></td>"
                + "</tr>"
               $("#memberlist").append(output);
           });
       }
   })

});