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
function subComment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment(commentId, 2, content);
}

/**
 * 评论板块封装方法
 */
function comment(targetId, type, content) {
    //前端也需要做校验，提高反应速度
    if (!content) {
        alert("评论内容不能为空~~");
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
    var subComments = $("#comment-" + id);
    var icon = $("#comment-icon");
    subComments.toggleClass("in");
    icon.toggleClass("active");
    //点击之后绘制页面
    if (icon.hasClass("active") && subComments.children().length === 1) {
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var mediaLine = $("<hr/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                });

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement).append(mediaLine);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-top"
                }).append(mediaElement);
                //将绘制的页面追加到subComments上，展示子评论的内容
                subComments.prepend(commentElement);
            });
        });
    }
}

/**
 * 展示所有标签
 */
function showTags() {
    $("#all-tags").show();
}

/**
 * 选中标签并添加进input
 */
function selectdTag(e) {
    //获取当前的input的值
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (!previous) {
        //input为空
        $("#tag").val(value);
    } else {
        //input不为空
        var str = previous.split(",");
        if (str.indexOf(value) === -1) {
            $("#tag").val(previous + ',' + value);
        }
    }
}