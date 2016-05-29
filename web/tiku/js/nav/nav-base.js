/**
 * navigation for base
 */
var state = 0;

$(document).ready(function () {
    /**
     * 动态生成菜单
     */
    function createMenu() {
        $.ajax({
            url:"/api/modules/menuList.do",
            type:"get", 
            dataType:"json",
            success:function (result) {
                if(result.code=='2001'){
                    alert(result.msg);
                    window.location.href="/tiku/page/util/login.html";
                    return;
                }else if(result.code!='1001'){
                    alert(result.msg);
                    return;
                }
                
                //有二级菜单的一级菜单位置
                var position = 0;

                //初始化菜单
                $("#nav-init").html("");
                var menus = "<ul><li class='pushButton'><span class='glyphicon glyphicon-chevron-left'></span></li>";
                $.each(result["data"],function (index,item) {
                    
                    if(item["children"].length!=0) {
                        menus += "<li title='"+item["url"]+"' value='"+(position++)+"' class='parMenu'><span class='"+item["moduleCode"]+"'></span><span class='functionName'>"+item["text"]+"</span><span class='glyphicon glyphicon-chevron-down'></span></li>"
                        menus += "<li class='childFunction'><ul>"
                        $.each(item["children"],function (index,data) {
                            menus += "<li title='"+data["url"]+"' class='childMenu'><span class='"+data["moduleCode"]+"'></span><span class='functionName'>"+data["text"]+"</span></li>"
                        })
                        menus += "</ul></li>";
                    }else {
                        menus += "<li title='" + item["url"] + "' class='parMenu'><span class='" + item["moduleCode"] + "'></span><span class='functionName'>" + item["text"] + "</span></li>"
                    }
                });

                menus += "</ul>";
                $("#nav-init").append(menus);

                //一级菜单点击事件
                $(".parMenu").click(function () {
                    var pos = $(this).attr("value");
                    if(pos != undefined){
                        $(".childFunction:not(:eq("+pos+"))").slideUp(200);
                        $(".childFunction:eq("+pos+")").slideToggle(200);
                        $(this).find(".glyphicon-chevron-down,.glyphicon-chevron-up").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
                    }
                    var url = $(this).attr("title");
                    menuPage(url);
                })
                
                //二级菜单点击事件
                $(".childMenu").click(function () {
                    var url = $(this).attr("title");
                    menuPage(url);
                })

                // //收缩后的菜单
                // $("#nav").html("");
                //

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
            }
        });
    }
    createMenu();
    
    function  menuPage(pageUrl) {
        if(pageUrl == "#"){
            return;
        }else if(pageUrl == "loginOut"){
            loginOut();
        }
        $.ajax({
            url:pageUrl,
            type:"get",
            dataType:"html",
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
        })
    }

    function loginOut() {
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
    }

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

    //修改密码
    $(".changePassword").click(function () {
        alert("更改密码")
    });

});
