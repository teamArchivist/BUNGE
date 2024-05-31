$(function() {
    let loginId = $("#loginId").text();
    //console.log(loginId);

    $("body").on("click", "#memowrite", function() {
        let cover = $(this).data("cover")
        let title = $(this).data("title")
        let author = $(this).data("author")
        let categoryName = $(this).data("categoryname")
        let score = $(this).data("score")
        let page = $(this).data("page")
        //console.log(cover)
        //console.log(title)
        //console.log(categoryName)

        $("#modalcover").attr("src", cover);
        $("#modaltitle").text(title);
        $("#modalauthor").text(author);
        $("#modalcategoryName").text(categoryName);
        $("#modalscore").text(score);
        $("#modalpage").text(page);

    })
})