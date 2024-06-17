$(function() {
    let params = new URLSearchParams(window.location.search);
    let page = params.get('page') || 1;
    let limit = params.get('limit') || 5;

    function memberlistcount(totalCount, currentPage, pageSize) {
        const totalPage = Math.ceil(totalCount / pageSize);
        const startPage = Math.max(1, currentPage - 5);
        const endPage = Math.min(totalPage, currentPage + 4);

        $("#pagination").empty();

        if (totalCount != 0) {
            if (currentPage == 1) {
                $("#pagination").append(`<li class="page-item"><a class="page-link disabled">이전</a></li>`);
            } else {
                $("#pagination").append(`<li class="page-item"><a href="#" data-page="${currentPage - 1}" class="page-link">이전</a></li>`);
            }

            for (let i = startPage; i <= endPage; i++) {
                $("#pagination").append(`<li class="page-item ${i === currentPage ? 'active' : ''}"><a href="#" data-page="${i}" class="page-link">${i}</a></li>`);
            }

            if (currentPage < totalPage) {
                $("#pagination").append(`<li class="page-item"><a href="#" data-page="${currentPage + 1}" class="page-link">다음</a></li>`);
            } else {
                $("#pagination").append(`<li class="page-item"><a class="page-link disabled">다음</a></li>`);
            }
        }

        // 페이지 링크 클릭 이벤트
        $("#pagination a").click(function (event) {
            event.preventDefault();
            let selectedPage = $(this).data("page");
            memberlistto(selectedPage, pageSize);
        });
    }

    memberlistto(page, limit);
    function memberlistto(page =1, limit=5) {
    $.ajax({
        url: "memberlistto",
        method: "get",
        data: {page: page,
                limit: limit},
        cache: false,
        success: function (rdata) {
            if(rdata.memberlistcount == 0) {
                let sentence ='조회된 회원이 없습니다.'
                $("#memberlist").html(sentence)
            }else {
            console.log(rdata.memberlist);
            let output='';
            $(rdata.memberlist).each(function () {
                let profileSrc = '/upload' + this.profile;
                 output += "<tr>"
                    + "<td><img class='img-sm rounded' src='"+ profileSrc + "'alt='thumbs'></td>"
                    + "<td><a class='btn-link text-body-emphasis text-decoration-underline text-truncate mb-0'>" + this.id + "</a></td>"
                    + "<td><span class='text-nowrap text-body-secondary'>" + this.name + "</span></td>"
                    + "<td>" + this.nick + "</td>"
                    + "<td><a class='btn-link text-nowrap'>" + this.addr1 + "</a></td>"
                    + "<td></td>"
                    + "<td>+"+this.role+"</td>"
                    + "<td>"
                    + "<div class='text-nowrap text-center'>"
                    + "<a class='btn btn-icon btn-sm btn-hover bg-body-tertiary' data-bs-toggle='modal' data-bs-target='#authority'>"
                    + "<i class='demo-pli-gear fs-5'></i></a>"
                    + "</div></td>"
                    + "</tr>"

            });
                $("#memberlist").html(output);
                //페이징 처리
                memberlistcount(rdata.memberlistcount, page, limit);
            }
        }
    })
}

});