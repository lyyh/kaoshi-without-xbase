/**
 * Created by 风中男子 on 2016-05-02.
 */
var myContent;

$("document").ready(function () {
    table.page({
        pageurl: "/ExamInfo/getAllPaperInfo.do",
        pagedata: {
            page: 1,
            rows: 15,
        }
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

    t = setTimeout(function () {
        var _modal = $(".modal-dialog");
        _modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 8)}, 300)
    }, 200);

    //安排考试
    $(".btn-arrange-new").click(function () {
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>只能选中一行数据</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else {
            check = $(".table tr input:checked");
            $("#show-info .modal-body").html("加载中...");
            $.ajax({
                url: "/ExamInfo/getPaperInfoDetails.do",
                type: "post",
                dataType: "json",
                data: {
                    testpaperId: check.val()
                },
                success: function (data) {
                    if (data == null) {
                        alert("请登录！");
                        return;
                    }
                    $("#testpaperId").val(check.val());
                    $("#teaName").val(data.data.teaName);
                    $("#mkpaperTerm").val(data.data.mkpaperTerm);
                    $("#testpaperType").val(data.data.testpaperType);
                    $("#mkpaperScore").val(data.data.mkpaperScore);
                    $("#createPpassportId").val(data.data.createPpassportId);
                    $("#courseId").val(data.data.courseId);
                }
            });
            $('#arrange-new').modal({backdrop: 'static', keyboard: false});
        }
    });

    //确定添加考试安排
    $(".btn-edit-sure").click(function () {
        var a = {
            'testpaperId': $("#testpaperId").val(),
            'testStarttime': $("#testStarttime").val(),
            'testEndtime': $("#testEndtime").val(),
            'courseId': $("#courseId").val(),
            'createPpassportId': $("#createPpassportId").val(),
        };
        $.ajax({
            type: "post",
            url: "/TeaSch/addExamSch.do",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(a),
            beforeSend: function () {
                $(".main-block").html("数据加载中...")
            },
            success: function (data) {
                if (data == null) {
                    alert("请登录！");
                    return;
                }
                alert("添加成功！");
                return;
            },
            error: function () {
                $(".main-block").html("网络错误...")
            }
        });
    });

    //返回
    $(".back").click(function () {
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-arrange-test.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                $(".back").unbind("click");
                $(".location").html("考试安排");
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

});

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    for (var i = 0; i < items.length; i++) {
        var difficulty = ((items[i].testpaperType == 0) ? '正式考试' : '模拟考试');
        $("#neirong").append('<tr><td><input value="' + items[i].testpaperId + '" type="checkbox" name="checkname"></td><td>' + items[i].testpaperId + '</td><td>' + items[i].teaName + '</td><td>' + items[i].mkpaperTerm + '</td><td>' + difficulty + '</td><td>' + items[i].mkpaperScore + '</td></tr>');
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
    });
}

