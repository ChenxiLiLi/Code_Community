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
            if (response.code === 200){
                $("#reply_section").hide();
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}