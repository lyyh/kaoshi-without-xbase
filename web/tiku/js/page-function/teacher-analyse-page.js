/**
 * Created by liuyanhao on 5/5/16.
 */
/**
 * Created by hukaihe on 2016/4/30.
 * child functions of question management
 * which teachers can do with such as recordQuestion are in this JavaScript file
 */
var myContent,move = false  ,time,tipPosition = $(".analysis-tip");

$("document").ready(function () {


    table.page({
        pageurl: "/subject/list.do",
        pagedata: {
//                courseId: $("#luti-op option:selected").val(),
            page: 1,
            rows: 15,
        }
    });
    $("#luti-op").change(function () {
        var type = $("#luti-op option:selected").val();
        table.page({
            pageurl: "/subject/list.do",
            pagedata: {
                type: type,
                page: 1,
                rows: 15,
            }
        });
    });

    //全选
    $(".checkall").click(function () {
        if (this.checked) {
            $("input[name='checkname']").each(function () {
                this.checked = true;
            });
        } else {
            $("input[name='checkname']").each(function () {
                this.checked = false;
            });
        }
    });

    $(".neirong tr").click(function () {
        $("#show-info .modal-body").html("加载中...");
        $.ajax({
            url: "/tiku/page/teacher/teacher-analysis.jsp",
            // type: "get",
            dataType: "html",
            // data: {
            //     stuId: $(this).attr("value")
            // },
            success: function (data) {
                if (data == null) {
                    alert("请登录！");
                    return;
                }
                $("#show-info .modal-body").html("");
                $("#show-info .modal-body").append(data);
            }
        });
        $('#show-info').modal({backdrop: 'static', keyboard: false});
        
    });
    
    
    $(".neirong td").mouseover(function(e) {
            time = setTimeout(function () {
                e = e ? e : window.event;
                tipPosition.css("display", "inline");
                tipPosition.css("left", (e.pageX + 8) + "px");
                tipPosition.css("top", (e.pageY + 8) + "px");
            }, 500);
    })
    $(".neirong td").mouseout(function () {
        tipPosition.css("display","none");
        clearTimeout(time);
    })
    //加载所有题型
    // $.ajax({
    //     url: "/subjectReference/getAllType.do",
    //     type: "get",
    //     dataType: "json",
    //     success: function (result) {
    //         if (result.code != 1) {
    //             alert(result.msg);
    //         }
    //         $("#luti-op").html("<option value='-1'>全部</option>");
    //         for (var i = 0; i < result.data.length; i++) {
    //             $("#luti-op").append('<option value="' + result.data[i].classId + '">' + result.data[i].classId + '</option>');
    //         }
    //     }
    // })
});

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;

    for (var i = 0; i < items.length; i++) {
        items.examRes == 1 ? items.examRes = "是" : items.examRes = "否";
        $("#neirong").append('<tr><td>' + items[i].courseName + '</td><td>' + items[i].classId + '</td><td>' + items[i].stuId + '</td><td>' + items[i].stuName + '</td><td>' + items[i].score + '</td><td>' + items[i].examRes + '</td><td>' + 1 + '</tr>');
    }
    // //表格选择
    // $("#neirong tr").click(function (e) {
    //     var input = $(this).find("input[name=checkname]");//获取checkbox
    //     if ($(e.target).attr("type") != "checkbox") {
    //         //判断当前checkbox是否为选中状态
    //         if (input.is(":checked")) {
    //             input.prop("checked", false);
    //         } else {
    //             input.prop("checked", true);
    //         }
    //     }
    // })
}
