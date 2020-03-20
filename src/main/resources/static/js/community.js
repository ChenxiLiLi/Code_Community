/**
 * 问题板块的回复功能
 */
function post() {
    var questionId = $("#question_id").val();
    var context = $("#comment_content").val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "type": 1,
            "content": context
        }),
        success: function (response) {
            if (response.code === 200) {
                $("#reply_section").hide();
            } else {
                if (response.code === 2003) {
                    window.localStorage.setItem("isClosed", "true");
                    var makeSure = window.confirm(response.message);
                    if (makeSure) {
                        window.open("https://github.com/login/oauth/authorize?client_id=1098b0f922ff4ba70fec" +
                            "&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                    } else {
                        alert(response.message);
                    }
                }
            }
        },
        dataType: "json"
    });
}