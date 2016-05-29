/**
 * Created by hukaihe on 2016/4/30.
 * child functions of question management
 * which teachers can do with such as recordQuestion are in this JavaScript file
 */

$("document").ready(function () {
    table.page({
        pageurl: "/courseList.do",
        pagedata: {
            page: 1,
            rows: 15,
        }
    });
});
    $("#luti-op").change(function () {
        var type = $("#luti-op option:selected").val();
        var coursetName = $(".search input").val();
        table.page({
            pageurl: "/courseList.do",
            pagedata: {
                courseName: courseName,
                page: 1,
                rows: 15,
            }
        });
    });
    $("#search-btn").click(function () {
        var courseName = $(".search input").val();
        if (courseName == '')courseName = null;
        table.page({
            pageurl: "/courseList.do",
            pagedata: {
                subjectName: courseName,
                page: 1,
                rows: 15,
            }
        });
    });

    //添加科目
    $("#addCourse").click(function () {
        $("#addModal").find(".modal-title").html("新增科目")
        $("#addModal").find(".modal-body").html("");
        $("#addModal").find(".modal-body").html("<div class='form-group' style='margin-top: 10px'><label for='newcourse'>科目</label> <input type='text' class='form-control' id='newcourse' style='width: 200px;display: inline-block'></div>")
        $("#addModal").modal({
            backdrop: "static",
            keyboard: false
        });

        $(".sure").click(function () {
            $.ajax({
                url: "/courseadd.do",
                type: "post",
                date: {
                    courseName: $("#newcourese").val()
                },
                success: function (date) {

                }
            })
        })
    })


    //添加知识点
    $("#addKnopoint").click(function () {
        $.ajax({
            url: "/subjectReference/getAllCourse.do",
            type: "post",
            success: function (data) {
                $("#addModal").find(".modal-body").html("");
                $("#addModal").find(".modal-body").append("选择科目:<select id='courseId' class='form-control input-sm' style='display: inline-block;width: 100px;'>");

                $.each(data, function (index, content) {

                    $("#addModal").find(".modal-body").append("<option value=" + content.courseId + ">" + content.courseName + "</option>");
                })
                $("#addModal").find(".modal-body").append("  </select> <div class ='form-group' style='margin-top: 10px;display: inline-block;'><label for='newknopoint'>知识点</label> <input type='text' class='form-control' id='newknopoint' style='width: 200px;display: inline-block;'> </div>");

            }

        });

        $(".sure").click(function () {
            $.ajax({
                url: "/knopoint/save.do",
                type: "post",
                date: {
                    type: "add",
                    courseId: $("#courseId").val(),
                    knopointName: $("#newknopoint").val()
                },
                success: function (date) {

                }
            })
            $("#addModal").find(".modal-title").html("新增知识点")
            $("#addModal").find(".modal-body").html("");
            $("#addModal").modal({
                backdrop: "static",
                keyboard: false
            });
        })
    })

function setItems(result) {
    $("#neirong").html('');
    var items = result.rows;
    for (var i = 0; i < items.length; i++) {
//            var difficulty=(items[i].levelId==1)?'简单':((items[i].levelId==2)?'中等':'困难');
        $("#neirong").append(
            '<tr><td><input value="' + items[i].courseId + '" type="checkbox" name="checkname"></td><td>' + items[i].courseName + '</td><td>' + items[i].TkKnopoint + '</td></tr>');
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


    


