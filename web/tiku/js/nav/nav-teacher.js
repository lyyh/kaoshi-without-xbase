/**
 * navigation for teacher
 */
$(function () {
    //加载教师的信息
    var teacher = $(".teacher-user[data-toggle='popover']");
    teacher.popover({
        trigger: 'manual',
        placement: 'right',
        title: "<div class='tool-userInfo-title'>用户信息</div>",
        html: 'true',
        content: "<div class='tool-userInfo'></div>"
    }).on("mouseenter", function () {
        var _this = this;
        $(this).popover("show");
        $(this).siblings(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
        });
    }).on("mouseleave", function () {
        var _this = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(_this).popover("hide")
            }
        }, 100);
    });

    //初始化框架上部菜单栏用户消息提示框
    //var myMessage = $(".myMessage");
    //myMessage.popover({
    //    trigger: 'manual',
    //    placement: 'bottom',
    //    title: "<div class='tool-userInfo-title'>消息</div>",
    //    html: 'true',
    //    content: "<div class='tool-userMessage'>韩顺平老师，您好：<br>请您于6月15日前将JAVA考试题目录入完毕</div>"
    //}).on("mouseenter", function () {
    //    var _this = this;
    //    $(this).popover("show");
    //    $(this).siblings(".popover").on("mouseleave", function () {
    //        $(_this).popover('hide');
    //    });
    //}).on("mouseleave", function () {
    //    var _this = this;
    //    setTimeout(function () {
    //        if (!$(".popover:hover").length) {
    //            $(_this).popover("hide")
    //        }
    //    }, 100);
    //});

    //学习管理
    $(".teacher-study-manage").click(function () {
        $(".location").html("学习管理");
        $.ajax({
            type: "get",
            url: "hello world",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {

                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //题目管理
    $(".teacher-question-manage").click(function () {
        $(".location").html("题目管理");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-question-manage.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //试卷管理
    $(".teacher-paper-manage").click(function () {
        $(".location").html("试卷管理");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-paper-manage.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });


    $(".teacher-paper-manage").click(function () {
        $(".childFunction:not(:eq(0))").slideUp(200);
        $(".childFunction:eq(0)").slideToggle(200);
        $(".teacher-paper-manage .glyphicon-chevron-down,.teacher-paper-manage .glyphicon-chevron-up").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
    });
    
    $(".teacher-user-operate").click(
        function () {
            $(".childFunction:not(:eq(1))").slideUp(200);
            $(".childFunction:eq(1)").slideToggle(200);
            $(".teacher-user-operate .glyphicon-chevron-down,.teacher-user-operate .glyphicon-chevron-up").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        }
    );

    //考试安排
    $(".teacher-test-arrange").click(function () {
        $(".location").html("考试安排");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-arrange-test.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //成绩统计
    $(".teacher-grade-analysis").click(function () {
        $(".location").html("成绩统计");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-analyse-page.html",
            dataType: "html",
            beforesenf: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //系统使用说明
    $(".teacher-introduction").click(function () {
        $(".location").html("系统使用说明");
        $.ajax({
            type: "get",
            url: "hello world",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {

                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });
});