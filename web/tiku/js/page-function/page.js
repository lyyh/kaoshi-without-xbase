//分页参数
var allNum = 0, //总共的条数
    curentPage = 1, //当前页数
    pageNum = 15,   //每页条数
    allPage = Math.ceil(allNum / pageNum),  //总页数
    ul = $(".pagination");
var table = {

    //配置
    config:{
        pageurl:"",
        pagedata:{
            courseId: "id",
            page: 1,
            rows: 15,
        },
    },

    //分页
    page: function (newconfig) {

        if(typeof newconfig === "object"){
            this.config = newconfig;
        }
        //初始化
        $.ajax({
            type:"post",
            url:this.config.pageurl,
            dataType: "json",
            data:this.config.pagedata,
            success: function (result) {
                if(result.code=='2001'){
                    alert(result.msg);
                    window.location.href="/tiku/page/util/login.html";
                    return;
                }else if(result.code!='1001'){
                    alert(result.msg);
                    return;
                }
                curentPage=table.config.pagedata.page;
                allNum = result.data.total;                    //总条数
                allPage = Math.ceil(allNum / pageNum);  //总页数
                ul.html("<li class='up-all'><a href='#'>&lt;&lt;</a></li><li class='up'><a href='#'>&lt;</a> </li>" +
                "<li class='curentPagenum'><a>当前" + curentPage + "/" + allPage + "页</a></li>" +
                "<li class='down'><a href='#'>&gt;</a> </li> <li class='down-all'><a href='#'>&gt;&gt;</a></li>" +
                "<li><a>共" + allNum + "条</a></li>")

                //上一页
                $(".up").find("a").click(function () {
                    if (curentPage <= 1) {
                        var a = $("#tip").find(".modal-body");
                        a.html("<p>当前已是第一页</p>");
                        $('#tip').modal({backdrop: 'static', keyboard: false});
                    }
                    else{
                        curentPage = curentPage - 1;
                        table.config.pagedata.page = curentPage;
                        table.page(table.config);
                    }
            })

                //第一页
            $(".up-all").find("a").click(function () {
                if (curentPage == 1) {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>当前已是第一页</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                }
                else {
                    curentPage = 1;
                    table.config.pagedata.page = curentPage;
                    table.page(table.config);
                }
            })

             //下一页
            $(".down").find("a").click(function () {
                if (curentPage >= allPage) {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>当前已是最后一页</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                }
                else {
                    curentPage = curentPage + 1;
                    table.config.pagedata.page = curentPage;
                    table.page(table.config);
                }
            })

            //最后一页
            $(".down-all").find("a").click(function () {
                if (curentPage >= allPage) {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>当前已是最后一页</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                }
                else {
                    curentPage = allPage;
                    table.config.pagedata.page = curentPage;
                    table.page(table.config);
                }
            })
                setItems(result);
                ul.find('.curentPagenum').html("<a>当前" + curentPage + "/" + allPage + "页</a>");
            }
        });
        function ajaxload(){
            $.ajax({
                type: "post",
                url: this.config.pageurl,
                dataType: "json",
                data: this.config.pagedata,
                error: function () {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success: function (result) {
                    setItems(result);
                    ul.find('.curentPagenum').html("<a>当前" + curentPage + "/" + allPage + "页</a>");
                }
            })
        }
    }
};