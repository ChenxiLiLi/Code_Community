/**
 * 问题板块一级评论
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment(questionId, 1, content);
}

/**
 * 问题板块的子评论
 */
function subComment(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment(commentId, 2, content);
}

/**
 * 评论板块封装方法
 */
function comment(targetId, type, content){
    //前端也需要做校验，提高反应速度
    if (!context) {
        alert("评论内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "type": type,
            "content": content
        }),
        success: function (response) {
            if (response.code === 200) {
                //追加最新的评论
                window.location.reload();
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

/**
 * 二级评论展示
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var icon = $("#comment-icon");
    comments.toggleClass("in");
    icon.toggleClass("active");
}