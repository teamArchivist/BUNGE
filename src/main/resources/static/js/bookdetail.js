let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

$(function() {

    //console.log(window.location.search);
    const url = new URL(window.location.href);
    const urlParams = url.searchParams;
    const isbn13 = urlParams.get("isbn13")
    const loginId = $("#loginId").text();
    //console.log("loginId : " + loginId);
    //console.log(isbn13);

    //상품 조회 API (상품 리스트 API와 다름)
    $.ajax({
        url: "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx",
        data: {
            "ttbkey" : "ttbyyy24941308001",
            "itemIdType" : "ISBN13",
            "ItemId" : isbn13,
            "output" : "JS",
            "Version" : "20131101",
        },
        dataType : "json",
        cache : false,
        success : function (rdata) {
            //console.log(rdata)
            //console.log(rdata.item[0].subInfo.itemPage)
            let itemPage = rdata.item[0].subInfo.itemPage
            $("#itemPage").text(itemPage)
        }

    })

    $("#addGoal").click(function() {

        const goalData = {
            isbn13 : isbn13,
            id : loginId,
            state : "목표",
            totalpage : parseInt($("#itemPage").text(), 10)
        };

        let answer = confirm("목표로 추가하시겠습니까?")

        if (answer) {
            $.ajax({
                url : "addreadstate",
                method : "POST",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(goalData),
                dataType : "json",
                cache : false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success : function(response) {

                    //console.log("response : " + response.message)

                    if (response.message == "goal success") {
                        alert("해당 도서가 '목표'로 추가되었습니다.");
                        location.href = "bookdetail?isbn13=" + isbn13
                    } else if (response.message == "goal failed") {
                        alert("'목표' 추가에 실패하였습니다.");
                    } else {
                        alert("부적절한 요청입니다.");
                    }
                },
                error : function(error) {
                    alert("부적절한 요청입니다. 목표 목록을 확인해주세요.");
                    console.error("목표 추가 중 오류 발생:", error);
                }
            }) //ajax end
        } //if (answer) end
    }) // $("#addGoal").click

    $("#addChallenge").click(function() {

        const challengeData = {
            isbn13 : isbn13,
            id : loginId,
            state : "도전",
            totalpage : parseInt($("#itemPage").text(), 10)
        };

        let answer = confirm("도전으로 추가하시겠습니까?")

        if (answer) {
            $.ajax({
                url : "addreadstate",
                method : "POST",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(challengeData),
                dataType : "json",
                cache : false,
                beforeSend : function (xhr) {
                    if (header && token) {
                        xhr.setRequestHeader(header, token);
                    }
                },
                success : function(response) {

                    //console.log("response : " + response.message)

                    if (response.message == "challenge success") {
                        alert("해당 도서가 '도전'으로 추가되었습니다.");
                        location.href = "bookdetail?isbn13=" + isbn13
                    } else if (response.message == "challenge failed") {
                        alert("'도전' 추가에 실패하였습니다.");
                    } else {
                        alert("부적절한 요청입니다.");
                    }
                },
                error : function(error) {
                    alert("부적절한 요청입니다. 목표 / 도전 중 하나의 상태만 가능합니다");
                    console.error("도전 추가 중 오류 발생:", error);
                }
            }) //ajax end
        } //if (answer) end

    }) // $("#addGoal").click

})