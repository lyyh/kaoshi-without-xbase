/**
 * navigation for student
 */
var myContent;

$(function(){
    //加载学生的信息
    var student = $(".student-user[data-toggle='popover']");
    student.popover({
        trigger: 'manual',
        placement: 'right',
        title: "<div class='tool-userInfo-title'>用户信息</div>",
        html: 'true',
        content: "<div class='tool-userInfo'>胡凯赫<br>2014213952<br>1301415班</div>"
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
    var myMessage = $(".myMessage");
    myMessage.popover({
        trigger: 'manual',
        placement: 'bottom',
        title: "<div class='tool-userInfo-title'>消息</div>",
        html: 'true',
        content: "<div class='tool-userMessage'>胡凯赫同学，您好：<br>请您于6月27日在软件实验室B231参加Java考试</div>"
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

    //显示导航栏的子功能
    $(".student-user-operate").click(
        function () {
            $(".childFunction:not(:eq(0))").slideUp(200);
            $(".childFunction:eq(0)").slideToggle(200);
            $(".navigation .glyphicon-chevron-down,.navigation .glyphicon-chevron-up").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        }
    );

    //考试系统
    $(".student-exam").click(function () {
        $.ajax({
            type: "get",
            url: "/tiku/page/student/student-exam-page.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                myContent=result;
                $(".main-block").html("");
                //先引入CSS文件，0.1秒后再引入页面，防止一瞬间的页面崩溃
                $(".main-block").html('<link href="/tiku/css/functionParts/exam.css" rel="stylesheet"/>');
                setTimeout("$('.main-block').append(myContent)", 100);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //学习系统
    $(".student-study").click(function () {
        $.ajax({
            type: "get",
            url: "page/student-study-page.html",
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
});