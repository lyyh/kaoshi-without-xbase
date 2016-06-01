/**
 * Created by ywl on 2016/6/1.
 */


$("document").ready(function () {
    table.page({
        pageurl: "/quecoll/selectAll.do",
        pagedata: {
            page: 1,
            rows: 15,
        }
    });
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
         url: "/quecoll/selectbyid.do",
         type: "get",
         dataType: "json",
         data: {
             quecollId: check.val()
         },
         success: function (result) {
         if (result == null) {
         alert("请登录！");
         return;
         }
             var difficulty=(result.levelId==1)?'简单':((result.levelId==2)?'中等':'困难');
         $("#show-info .modal-body").html("");
         $("#show-info .modal-body").append(result);

             $(".content").html("");
             $(".content").append("<div class='form-group'> <label>错题编号</label> <p>"+result.quecollId+"</p> </div> <div class='form-group'> <label>题干</label> <p>"+result.subjectName+result.subjectOption+"</p> </div> <div class='form-group'> <label>知识点</label> <p>"+result.knopointName+"</p> </div> <div class='form-group'> <label>难度</label> <p>"+difficulty+"</p> </div> <div class='form-group'> <label>您的答案</label> <p>"+result.quecollAnswer+"</p> </div> <div class=‘form-group’> <label>正确答案</label> <p>"+result.subjectAnswer+"</p> </div> <div class='form-group'> <label>解析</label> <p>"+result.subjectSolution+"</p> </div>")

         }
         });
        $('#show-infoml').modal({backdrop: 'static', keyboard: false});
    }
});

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

function setItems(result) {
    $("#neirong").html('');
    var items = result.data.rows;
    //alert(items);    console.log(items)
    for (var i = 0; i < items.length; i++) {
        var difficulty=(items[i].levelId==1)?'简单':((items[i].levelId==2)?'中等':'困难');
        $("#neirong").append(
            '<tr><td><input value="' + items[i].quecollId + '" type="checkbox" name="checkname"></td><td>' + items[i].quecollId + '</td><td>' + items[i].courseName + '</td><td>' + items[i].subjectName + '</td><td>' + items[i].knopointName+ '</td><td>'+difficulty+'</td></tr>');
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