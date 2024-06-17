let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {
   $.ajax({
       url: "studylist" ,
       method: "get" ,
       cache: false ,
       beforeSend: function(xhr) {
           if(header && token) {
               xhr.setRequestHeader(header, token);
           }
       },
       success: function (rdata) {
           console.log(rdata);
           $(rdata).each(function (){
            let output ="<tr>"
               + "<td><a class='btn-link'>"+ this.title+"</a></td>"
               + "<td>" + this.id +"</td>"
               + "<td><span class='text-body'><i class='demo-pli-clock'></i>"+this.studystatus+"</span></td>"
               + "<td class='text-center fs-5'>"
               + "<div class='progress'>"
               + "<div class='progress-bar' role='progressbar'>"+'진행률'+"</div>"
               + "</div>"
               + "</td>"
               + "<td>"+this.challengestart+"</td>"
               + "<td>"+this.challengeend+"</td>"
               + "<td class='text-center'>"+ this.categoryName.substring(this.categoryName.length - 8)+"</td>"
               + "</tr>"
               $("#studylist").append(output);
           });
       }
   })

});