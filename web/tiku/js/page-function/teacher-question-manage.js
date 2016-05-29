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
               // courseId: $("#luti-op option:selected").val(),
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
    $(".btn-delete").click(function () {
        var checks = $(".table tr").find("input[name=checkname]"),
            n = 0;
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked)
                n++;
        }
        if (n == 0) {
            var a = $("#tip").find(".modal-body");
            a.html("<p>请您对一行或多行数据进行删除</p>");
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
                $.ajax({
                    url: "/subject/delete.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        subjectId: ids
                    },
                    success: function (result) {
                        var a = $("#tip").find(".modal-body");
                        if (result.code == '2001') {
                            alert("请登录！");
                        } else if(result.code=='1001') {
                            a.html("<p>删除成功</p>");
                            $('#delete').modal("hide");
                            $('#tip').modal({backdrop: 'static', keyboard: false});
                            $('#delete').on('hidden.bs.modal', function () {
                                $.ajax({
                                    type: "get",
                                    url: "/tiku/page/teacher/teacher-question-manage.html",
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
                        }else{
                            alert(result.msg);
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

    $(".btn-show").click(function () {
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
                success: function (result) {
                    if (result == null) {
                        alert("请登录！");
                        return;
                    }
                    $("#show-info .modal-body").html("");
                    $("#show-info .modal-body").append(result);
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

    //t = setTimeout(function () {
    //    var _modal = $(".modal-dialog");
    //    _modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 8)}, 300)
    //}, 200);
    //题目录入
    $(".teacher-recordQuestion").click(function () {
        $(".location").html("题目管理<span class='glyphicon glyphicon-chevron-right'></span>题目录入");
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/recordQuestion.html",
            dataType: "html",
            beforeSend: function () {
                $(".main-block").html("<div class='myJava'>数据加载中...</div>")
            },
            success: function (result) {
                myContent = result;
                $(".main-block").html("");
                //先引入CSS文件，0.01秒后再引入页面，防止一瞬间的页面崩溃
                $(".main-block").html('<link href="/tiku/css/functionParts/recordQuestion.css" rel="stylesheet"/>');
                setTimeout("$('.main-block').append(myContent)", 100);
            },
            error: function () {
                $(".main-block").html("<div class='wrong404'></div>")
            }
        });
    });
    $(".btn-edit").click(function () {
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
            $.ajax({
                type: "get",
                url: "/subject/editPage.do",
                dataType: "html",
                data: {
                    subjectId: check.val()
                },
                beforeSend: function () {
                    $(".main-block").html("<div class='myJava'>数据加载中...</div>")
                },
                success: function (result) {
                    myContent = result;
                    $(".main-block").html("");
                    //先引入CSS文件，0.01秒后再引入页面，防止一瞬间的页面崩溃
                    $(".main-block").html('<link href="/tiku/css/functionParts/recordQuestion.css" rel="stylesheet"/>');
                    setTimeout("$('.main-block').append(myContent)", 100);
                },
                error: function () {
                    $(".main-block").html("<div class='wrong404'></div>")
                }
            });
        }
    });

    //加载所有题型
    $.ajax({
        url: "/subjectReference/getAllType.do",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code != '1001') {
                alert(result.msg);
            }
            $("#luti-op").html("<option value='-1'>全部</option>");
            for (var i = 0; i < result.data.length; i++) {
                $("#luti-op").append('<option value="' + result.data[i].quetypeId + '">' + result.data[i].quetypeName + '</option>');
            }
        }
    })
});

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    for (var i = 0; i < items.length; i++) {
        var difficulty = (items[i].levelId == 1) ? '简单' : ((items[i].levelId == 2) ? '中等' : '困难');
        $("#neirong").append('<tr><td><input value="' + items[i].subjectId + '" type="checkbox" name="checkname"></td><td>' + items[i].subjectId + '</td><td> <div class="neirong-title">' + items[i].subjectName + '</div></td><td>' + items[i].passportName + '</td><td>' + items[i].knopoint + '</td><td>' + difficulty + '</td><td>' + items[i].createTimeString + '</td></tr>');
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
