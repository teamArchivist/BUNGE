$(function () {

    $("#bookadd").click(function() {
        let searchword = prompt("책 제목을 입력하세요")

        $.ajax ({
            url : "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx",
            data : {"ttbkey" : "ttbyyy24941308001",
                "Query" : searchword,
                "QueryType" : "Title",
                "SearchTarget" : "Book",
                "start" : "1",
                "MaxResults" : "18",
                "Sort" : "Accuracy",
                "Cover" : "Big",
                "output" : "JS",
                "Version" : "20131101"},
            dataType : "json",
            cache : false,
            success: function(rdata) {

                $("#searchresult").empty()

                let i=0
                $(rdata.item).each(function() {
                    let subject = rdata.item.at(i)
                    let output = "<div class='col-sm-6 col-xl-2 mb-3'>" +
                                        "<div class='card mb-3 hv-grow-parent h-100'>" +
                                        "  <img class='card-img-top' src=" + subject.cover + " loading='lazy' height='430px'>" +
                                        "  <div class='text-center mt-4'>" +
                                        "       <a href='#' class='h5 mb-3'>" + subject.title.substring(0,22) + '...' + "</a>" +
                                        "       <p class='text-justify text-opacity-75 mb-0'>" + subject.author + "</p>" +
                                        "       <p class='text-justify text-opacity-75 mb-0'>" + subject.pubDate + "</p>" +
                                        "  </div>" +
                                        "  <div class='card-body'>" +
                                        "       <p class='text-justify mb-3'>" + subject.categoryName + "</p>" +
                                        "       <p class='text-justify mb-0'>" + subject.description + "</p>" +
                                        "  </div>" +
                                        "</div>" +
                                        "</div>"

                    $('#searchresult').append(output)
                    i++
                })
            }
        })

    })



})

