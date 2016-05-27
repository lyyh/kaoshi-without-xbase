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
            rows: 15,
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
                rows: 15,
            }
        });
    });
    $("#search-btn").click(function(){
        var subjectName=$(".search input").val();
        var type = $("#luti-op option:selected").val();
        if(subjectName=='')subjectName=null;
        table.page({
            pageurl: "/subject/list.do",
            pagedata: {
                subjectName: subjectName,
                type:type,
                page: 1,
                rows: 15,
            }
        });
    });
    // $(".ques-option p").click(function () {
    //     alert($(this).find("input[type='radio']").attr("checked"))
    //     console.log($(this).siblings().find("input[type='radio']"))
    //     $(this).siblings().find("input[type='radio']").attr("checked","checked");
    //     // $(this).find("input[type='radio']").attr("checked","checked");
    // });
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
        $("#sure").click(function () {
            $.ajax({
                url:"/tiku/page/student/student-study-result.html",
                type:"get",
                dataType:"html",
                // beforeSend:function () {
                //     var a = $("#tip").find(".modal-body");
                //     a.html("<p>正在处理</p>");
                //     $('#tip').modal({backdrop: 'static', keyboard: false});
                // },
                error: function (request) {
                    var a = $("#tip").find(".modal-body");
                    a.html("<p>网络错误</p>");
                    $('#tip').modal({backdrop: 'static', keyboard: false});
                },
                success:function (data) {
                    $("#tip").modal("toggle");
                    $("#tip .modal-footer").html("");
                    $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
                    
                    $(".totalContainer").html("");
                    $(".totalContainer").html(data);
                }
            })
        })
        $("#cancel").click(function () {
            $("#tip").modal("toggle");
            $("#tip .modal-footer").html("");
            $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
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
