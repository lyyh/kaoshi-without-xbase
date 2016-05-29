/**
 * Created by hukaihe on 2016/4/30.
 * child functions of paper management
 * which teachers can do with such as makePaperByMan are in this JavaScript file
 */
var myContent;
var t_data;
$("document").ready(function () {
    // 加载 学科 默认是java
    $.ajax({
        url: "/rg_getCourseList.do",
        dataType: "json",
        success: function (data) {
            $("#luti-op").html("");
            $.each(data, function (index, objs) {
                $("#luti-op").append(
                    "<option value=\"" + objs.courseId + "\">" + objs.courseName + "</option> "
                );

            })
        }
    });

    //加载表格
    table.page({
        pageurl: "/paper/selectallpaper.do",
        pagedata: {
            courseId:$("#luti-op option:selected").val(),
            page: 1,
            rows: 15
        }
    });

    //删除试卷
    $(".btn-delete").click(function () {
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>只能选择一个</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else {
            var b = $("#delete").find(".modal-body");
            b.html("<p>您确定要删除吗?</p>")
            $('#delete').modal({backdrop: 'static', keyboard: false});
            $(".btn-delete-modal").click(function () {
                var ids = new Array();
                for (var i = 0; i < $(".table tr input:checked").length; i++) {
                    var ch = $(".table tr input:checked:eq(" + i + ")");
                    ids.push(ch.val());
                }
                $.ajax({
                    url: "/paper/deletepaper.do",
                    type: "post",
                    dataType: "json",
                    data: {testpaperId:ids[0]} ,
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
                                    url: "/tiku/page/teacher/teacher-paper-manage.html",
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
                    error: function (requrest) {
                        var a = $("#tip").find(".modal-body");
                        a.html("<p>网络错误</p>");
                        $('#tip').modal({backdrop: 'static', keyboard: false});
                    }
                });
            });
        }
    })

    //查看试卷信息
    $(".btn-show").click(function () {
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        var tempdata = $(".table tr input:checked").val();
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n != 1) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>只能选择一个</p>");
            $('#tip').modal({backdrop: 'static', keyboard: false});
        }
        else{
            $.ajax({
                url: "/paper/selectpaperbyId.do",
                data: {
                    testpaperId: tempdata
                },
                success: function (data) {

                    $("#datatables").html("");
                    $("#datatables").append(
                        "<tr>"
                        + " <th colspan=\"5\" style=\"border: none\">"
                        + " <h2>"
                        + data.mkpaperTerm
                        + "</h2>"
                        + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>科目:" + data.courseName + "</td>"
                        + "<td>试卷编号:" + data.testpaperId + "</td>"
                        + "<td>出卷人:" + data.passportName + "</td>"
                        + "<td>时长:" + data.mkpaperExtime + "</td>"
                        + "<td>总分:" + data.mkpaperScore + "</td>"
                        + "</tr>"
                    );
                    $.each(data.subjects, function (index, objs) {
                        $("#SubList").append(
                            "<p>" + objs.subjectName + "</p>"
                            + "<p>" + objs.subjectOption + "</p>"
                        );

                    })
                    $("#datamemo").html("");
                    $("#datamemo").append(
                        "<p>" + data.paperName + "</p>"
                    )
                }

            })

            $('#show-info').modal({backdrop: 'static', keyboard: false});
        }
        /**/
        //从后台去获取查看的数据



    })

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

    //模态框调整
    t = setTimeout(function () {
        var _modal = $(".modal-dialog");
        _modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 8)}, 300)
    }, 200);

    //人工制卷
    $(".teacher-makePaperByMan").click(function () {
        $(".location").html("试卷管理<span class='glyphicon glyphicon-chevron-right'></span>人工制卷");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/makePaperByMan.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                myContent = result;
                addPassportName(1);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });

    //机器制卷
    $(".teacher-makePaperByMachine").click(function () {
        $(".location").html("试卷管理<span class='glyphicon glyphicon-chevron-right'></span>机器制卷");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/makePaperByMachine.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                myContent = result;
                addPassportName(2);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });


    function addPassportName(num) {
        $.ajax({
            type: "get",
            url: "/mtr.do",
            dataType: "json",
            success: function (data) {
                t_data = data;
                $(".main-block").html("");
                if (num == 1)
                    $(".main-block").html('<link href="/tiku/css/functionParts/makePaperByMan.css" rel="stylesheet"/>');
                if (num == 2)
                    $(".main-block").html('<link href="/tiku/css/functionParts/makePaperByMachine.css" rel="stylesheet"/>');
                setTimeout("$('.main-block').append(myContent);$('.main-block').find('#teacherName').val(t_data);myContent=''", 100);
            }
        })
    }

    //修改
    $(".btn-edit").click(function () {
        var check,
            checks = $(".table tr").find("input[name=checkname]"),
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
            $.ajax({
                type: "get",
                url: "/tiku/page/teacher/makePaperByManEdit.html", //这个要使用 jsp的方式
                dataType: "html",
                data: {
                    //check.val()
                 },
                beforeSend: function () {
                    $(".main-block").html("<div class='myJava'>数据加载中...</div>")
                },
                success: function (result) {
                    if (result == null) {
                        alert("请登录！");
                        return;
                    }

                    var myContent = result;
                    $(".main-block").html("");
                    $(".main-block").html('<link href="/tiku/css/functionParts/makePaperByMan.css" rel="stylesheet"/>');
                    setTimeout("$('.main-block').append(myContent)", 10);
                },
                error: function () {
                    $(".main-block").html("<div class='wrong404'></div>")
                }
            });
        }
    })

    // //加载所有科目
    // $.ajax({
    //     url: "",
    //     type: "get",
    //     dataType: "json",
    //     success: function (data) {
    //         $("#luti-op").html("<option value='-1'>全部</option>");
    //         for (var i = 0; i < data.length; i++) {
    //             $("#luti-op").append('<option value="">' + +'</option>');
    //         }
    //     }
    // })

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
});

//分页
function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    for (var i = 0; i < items.length; i++) {
//            var difficulty=(items[i].levelId==1)?'简单':((items[i].levelId==2)?'中等':'困难');
        $("#neirong").append(
            '<tr><td><input value="' + items[i].testpaperId + '" type="checkbox" name="checkname"></td><td>' + items[i].testpaperId + '</td><td>' + items[i].passportName + '</td><td>' + items[i].mkpaperTerm + '</td><td>' + items[i].mkpaperScore + '</td><td>' + items[i].timeString + '</td><td>' + items[i].paperName + '</td></tr>');
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

