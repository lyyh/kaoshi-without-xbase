/**
 * navigation for base
 */
var state = 0;

$(document).ready(function () {
    /***
     * 登录初始化信息
     */
        //初始化提示工具
    $('[data-toggle="tooltip"]').tooltip();

    /**
     * 框架上部菜单栏的公共功能
     */
        //系统时间
    setInterval('$(".myTime").html(new Date().toLocaleDateString()+"&nbsp;&nbsp"+new Date().getHours()+"时"+new Date().getMinutes()+"分"+new Date().getSeconds()+"秒<br>");', 1000);

    //设置按钮
    $(".settings").click(function () {
        $(".main-block").html("<div class='myJava'></div>");
        alert("您可以在这里设置您的背景音乐");
    });

    //返回首页按钮
    $(".home").click(function () {
        $(".main-block").html("<div class='myJava'></div>");
        $(".location").html("系统首页");
        $(".back").unbind("click");
    });


    /**
     * 框架左侧的导航栏的公共功能按钮
     */
        //新闻公告
    $(".news").click(function () {
        $(".location").html("新闻公告");
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

    //修改密码
    $(".changePassword").click(function () {
        alert("更改密码")
    });

    //注销用户
    $(".cancellation").click(function () {
        if (confirm("您确定要注销用户吗？")) {
            $.ajax({
                url:"/api/passport/authentication.do?action=out",
                type:"post",
                dataType:"json",
                success:function(result){
                    if(result.code=='1001'){
                        window.location.href = "login.html"
                    }else{
                        alert(result.msg);
                    }
                }
            });
        }
    });

    /**
     * 控制左侧导航栏的动画
     */
    //工具条手动伸缩
    $(".pushButton").click(function () {
        if (state == 0) {
            $(".left-block").hide(150);
            $(".left-block-container").hide(150);
            $(".left-tools").show(150);
            $(".left-tools-container").show(150);
            state = 1;
        } else {
            $(".left-tools").hide(150);
            $(".left-tools-container").hide(150);
            $(".left-block").show(150);
            $(".left-block-container").show(150);
            state = 0;
        }
    });

    //鼠标悬浮的动画效果
    $(".navigation ul>li:not('.childFunction')").mouseover(function () {
        $(this).find("span:eq(0)").stop().animate({'font-size': '17px'}, 100);
    }).mouseleave(function () {
        $(this).find("span:eq(0)").stop().animate({'font-size': '13px'}, 100);
    });

    $(".navigation .childFunction>li").mouseover(function () {
        $(this).find("span:eq(0)").stop().animate({'font-size': '17px'}, 100);
    }).mouseleave(function () {
        $(this).find("span:eq(0)").stop().animate({'font-size': '13px'}, 100);
    });
});
