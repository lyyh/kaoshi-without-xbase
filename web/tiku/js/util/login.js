$("body").keydown(function (event) {
    if (event.keyCode == 13) {
        login()
    }
});

$(".login-button").click(
    function () {
        login()
    }
);

function login() {
    var studentnum = $(".login-student-num"),
        passwordnum = $(".login-password-num"),
        erroinfo = $(".erro-info");

    var params = $("#login-form").serialize();
    if (studentnum.val() == "") {
        studentnum.attr('id', 'warning-studentnum');
        erroinfo.html("<p class='erro'>请输入正确的学号</p>");
        studentnum.focus();
    }
    else {
        studentnum.attr('id', '');
        erroinfo.html("");
        passwordnum.focus();
        if (passwordnum.val() == "") {
            passwordnum.attr('id', 'warning-passwordnum');
            erroinfo.html("<p class='erro'>请输入正确的密码</p>");
            passwordnum.focus();
        }
        else {
            passwordnum.attr('id', '');
            $.ajax({
                type: "post",
                url: "/api/passport/authentication.do?action=in",
                data: params,
                erro: function () {
                    alert("网络繁忙，请稍后再试");
                },
                //返回一个标识符，前面一个判断是错误，后面是正确
                success: function (result) {
                    if (result.code!='1001') {
                        studentnum.attr('id', 'warning-studentnum');
                        passwordnum.attr('id', 'warning-passwordnum');
                        erroinfo.html("<p class='erro'>"+result.msg+"</p>");
                    }
                    else {
                        //alert("登陆成功")
                        window.location.href = "/tiku/page/util/index.html";
                    }
                }
            })
        }
    }
}