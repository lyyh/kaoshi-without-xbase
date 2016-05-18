/**
 * Created by 风中男子 on 2016-05-02.
 */
var myContent;

$("document").ready(function () {
    table.page({
        pageurl: "/ExamInfo/getAllExamInfo.do",
        pagedata: {
            page: 1,
            rows: 15,
        }
    });

    //安排考试
    $(".btn-arrange-new").click(function () {
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-arrange-new.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("数据加载中...")
            },
            success: function (result) {
                $(".main-block").html("");
                $(".main-block").append(result);
            },
            error: function () {
                $(".main-block").html("网络错误...")
            }
        });
    })

    $(".btn-delete").click(function () {
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>只能选择一行数据</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else {
            var b = $("#delete").find(".modal-body");
            b.html("<p>您确定要删除选中的" + "<font color='#d9534f'>" + n + "</font>" + "行吗?</p>")
            $('#delete').modal({backdrop: 'static', keyboard: false});
            $(".btn-delete-modal").click(function () {
                var ids = new Array();
                for (var i = 0; i < $(".table tr input:checked").length; i++) {
                    var ch = $(".table tr input:checked:eq(" + i + ")");
                    ids.push(ch.val());
                }
                check = $(".table tr input:checked");
                $.ajax({
                    url: "/TeaSch/delExamSch.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        testscheduleId: check.val()
                    },
                    error: function () {
                        alert("请登录！");
                    },
                    success: function (data) {
                        var a = $("#tip").find(".modal-body");
                        if (data == null) {
                            alert("请登录！");
                        } else {
                            a.html("<p>删除成功</p>");
                            $('#delete').modal("hide");
                            $('#tip').modal({backdrop: 'static', keyboard: false});
                            $('#delete').on('hidden.bs.modal', function () {
                                $.ajax({
                                    type: "get",
                                    url: "tiku/page/teacher/teacher-arrange-test.html",
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
                            })
                        }
                        $('.btn-delete-modal').unbind();
                    },
                    error: function (request) {
                        var a = $("#tip").find(".modal-body");
                        a.html("<p>网络错误</p>");
                        $('#tip').modal({backdrop: 'static', keyboard: false});
                    }
                });
            });
        }
    });

    $(".btn-arrange-show").click(function () {
        var check;
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>请您选择一行数据</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else {
            check = $(".table tr input:checked");
            $("#show-info .modal-body").html("加载中...");
            $.ajax({
                url: "/subject/detailsPage.do",
                type: "get",
                dataType: "html",
                data: {
                    subjectId: check.val()
                },
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

    //修改
    $(".btn-arrange-edit").click(function () {
        var check,
            checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>只能选择一行数据</p>");
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
                    testscheduleId: check.val()
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

                    $("#testStarttime").val(data.data.testStarttime);
                    $("#testEndtime").val(data.data.testEndtime);
                }
            });
            $('#arrange-edit-info').modal({backdrop: 'static', keyboard: false});
        }
    });

    //确认修改考试
    $(".btn-edit-sure").click(function () {
        var a = {
            'testpaperId': $("#testpaperId").val(),
            'testStarttime': $("#testStarttime").val(),
            'testEndtime': $("#testEndtime").val(),
            'courseId': $("#courseId").val(),
            'createPpassportId': $("#createPpassportId").val(),
            'newStarttime':$("#newStarttime").val(),
            'newEndtime':$("#newEndtime").val(),
        };
        $.ajax({
            type: "post",
            url: "/TeaSch/editExamSch.do",
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

});

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    for (var i = 0; i < items.length; i++) {
        var difficulty = ((items[i].testpaperType == 0) ? '正式考试' : '模拟考试');
        $("#neirong").append('<tr><td><input value="' + items[i].testscheduleId + '" type="checkbox" name="checkname"></td><td>'+ items[i].testscheduleId +'</td><td>' + items[i].courseName + '</td><td>' + items[i].teaName + '</td><td>' + items[i].mkpaperTerm + '</td><td>' + items[i].mkpaperScore + '</td><td>' + items[i].testStarttime +'</td><td>' + items[i].testEndtime +'</td><td>' + difficulty + '</td></tr>');
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

