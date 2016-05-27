/**
 * Created by hukaihe on 2016/4/30.
 * child functions of question management
 * which teachers can do with such as recordQuestion are in this JavaScript file
 */
var myContent;

$("document").ready(function () {
    table.page({
        pageurl: "/subject/list.do",
        pagedata: {
            page: 1,
            rows: 10,
        }
    });
    $("#luti-op").change(function () {
        var type = $("#luti-op option:selected").val();
        var subjectName=$(".search input").val();
        table.page({
            pageurl: "/subject/list.do",
            pagedata: {
                type: type,
                subjectName: subjectName,
                page: 1,
                rows: 10,
            }
        });
    });

    //搜索框
    $(".begin").click(function(){
        var subjectName=$(".search input").val();
        var type = $("#luti-op option:selected").val();
        if(subjectName=='')subjectName=null;
        table.page({
            pageurl: "/subject/list.do",
            pagedata: {
                subjectName: subjectName,
                type:type,
                page: 1,
                rows: 10,
            }
        });
    });
    // $(".ques-option p").click(function () {
    //     alert($(this).find("input[type='radio']").attr("checked"))
    //     console.log($(this).siblings().find("input[type='radio']"))
    //     $(this).siblings().find("input[type='radio']").attr("checked","checked");
    //     // $(this).find("input[type='radio']").attr("checked","checked");
    // });
    //提交
    $(".submit").click(function () {
        $("#tip .modal-body").html("");
        $("#tip .modal-body").html("确定要提交吗");
        $("#tip .modal-footer").html("");
        $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure'>确定</button>"+
        "<button type='button' class='btn btn-default' id='cancel'>取消</button>")
        $("#tip").modal({
            keyboard:false,
            backdrop:"static"
        })

        //是否提交(确认按钮)
        $("#sure").click(function () {
            $.ajax({
                url:"/tiku/page/student/student-study-page.html",
                type:"get",
                dataType:"html",
                beforeSend:function () {
                    $("#tip .modal-body").html("");
                    $("#tip .modal-body").html("正在处理");
                },
                error: function (request) {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function (data) {
                    $("#tip").modal("toggle");
                    $("#tip .modal-footer").html("");
                    $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
                    $("#show-result").modal({
                        backdrop:"static",
                        keyboard:false
                    })
                }
            })
        })
        
        //是否提交(取消按钮)
        $("#cancel").click(function () {
            $("#tip").modal("toggle");
            $("#tip .modal-footer").html("");
            $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
        })
    })
    
    //查看解析
    $(".check-analysis").click(function () {
        $.ajax({
            url:"/tiku/page/student/student-study-result.html",  //这里要修改
            type:"post",
            dataType:"json",
            data:{
                
            },
            beforeSend:function () {
                $("#show-result .modal-body").html("");
                $("#show-result .modal-body").html("正在处理");
            },
            error: function (request) {
                var a = $("#show-result").find(".modal-body");
                a.html("<p>网络错误</p>");
                $('#show-result').modal({backdrop: 'static', keyboard: false});
            },
            success:function (data) {
                $("#show-result").modal("toggle");
                //我是交互
            }
        })
    })
});

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    for (var i = 0; i < items.length; i++) {
        var difficulty = (items[i].levelId == 1) ? '简单' : ((items[i].levelId == 2) ? '中等' : '困难');
        $("#neirong").append('<tr><td><input value="' + items[i].subjectId + '" type="checkbox" name="checkname"></td><td>' + items[i].subjectId + '</td><td>' + items[i].subjectName + '</td><td>' + items[i].passportName + '</td><td>' + items[i].knopoint + '</td><td>' + difficulty + '</td><td>' + items[i].createTimeString + '</td></tr>');
    }
    //表格选择
    $("#neirong tr").click(function (e) {
        var input = $(this).find("input[name=checkname]");//获取checkbox
        if ($(e.target).attr("type") != "checkbox") {
            //判断当前checkbox是否为选中状态
            if (input.is(":checked")) {
                input.prop("checked", false);
            } else {
                input.prop("checked", true);
            }
        }
    })
}
