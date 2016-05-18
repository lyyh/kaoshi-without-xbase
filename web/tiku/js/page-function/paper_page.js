$("document").ready(function(){
    var allNum = 55, //总共的条数
        curentPage = 1, //当前页数
        pageNum = 15,   //每页条数
        allPage = Math.ceil(allNum/pageNum),//总页数
        ul = $(".pagination");
    ul.html("<li class='up-all'><a href='#'>&lt;&lt;</a></li><li class='up'><a href='#'>&lt;</a> </li>" +
        "<li class='curentPagenum'><a>当前"+curentPage+"/"+allPage+"页</a></li>" +
        "<li class='down'><a href='#'>&gt;</a> </li> <li class='down-all'><a href='#'>&gt;&gt;</a></li>" +
        "<li><a>共"+allNum+"条</a></li>")
    //上一页
    $(".up").find("a").click(function(){
        if (curentPage == 1){
            var a = $("#tip").find(".modal-body");
            a.html("<p>当前已是第一页</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else{
            $.ajax({
                type:"post",
                url:"",
                datatype:"json",
                data:"",
                error:function(){
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function(){
                    $("#neirong").html('<tr><td><input type="checkbox" name="checkname"></td><td>1111</td><td>马云</td><td>无</td><td>无</td><td>无</td></tr>');
                    curentPage = curentPage - 1;
                    ul.find('.curentPagenum').html("<a>当前"+curentPage+"/"+allPage+"页</a>");
                }
            })
        }
    })

    //第一页
    $(".up-all").find("a").click(function(){
        if (curentPage == 1){
            var a = $("#tip").find(".modal-body");
            a.html("<p>当前已是第一页</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else{
            $.ajax({
                type:"post",
                url:"",
                datatype:"json",
                data:"",
                error:function(){
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function(){
                    $("#neirong").html('<tr><td><input type="checkbox" name="checkname"></td><td>1111</td><td>马云</td><td>无</td><td>无</td><td>无</td></tr>');
                    curentPage = 1;
                    ul.find('.curentPagenum').html("<a>当前"+curentPage+"/"+allPage+"页</a>");
                }
            })
        }
    })

    //下一页
    $(".down").find("a").click(function(){
        if (curentPage == allPage){
            var a = $("#tip").find(".modal-body");
            a.html("<p>当前已是第一页</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else{
            $.ajax({
                type:"post",
                url:"",
                datatype:"json",
                data:"",
                error:function(){
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function(){
                    $("#neirong").html('<tr><td><input type="checkbox" name="checkname"></td><td>1111</td><td>马云</td><td>无</td><td>无</td><td>无</td></tr>');
                    curentPage = curentPage + 1;
                    ul.find('.curentPagenum').html("<a>当前"+curentPage+"/"+allPage+"页</a>");
                }
            })
        }
    })

    //最后一页
    $(".down-all").find("a").click(function(){
        if (curentPage == allPage){
            var a = $("#tip").find(".modal-body");
            a.html("<p>当前已是第一页</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else{
            $.ajax({
                type:"post",
                url:"",
                datatype:"json",
                data:"",
                error:function(){
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function(){
                    $("#neirong").html('<tr><td><input type="checkbox" name="checkname"></td><td>1111</td><td>马云</td><td>无</td><td>无</td><td>无</td></tr>');
                    curentPage = allPage;
                    ul.find('.curentPagenum').html("<a>当前"+curentPage+"/"+allPage+"页</a>");
                }
            })
        }
    })
})
