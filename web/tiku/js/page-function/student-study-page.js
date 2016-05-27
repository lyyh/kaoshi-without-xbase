/**
 * Created by hukaihe on 2016/4/30.
 * child functions of question management
 * which teachers can do with such as recordQuestion are in this JavaScript file
 */
var myContent;

function getCourses(getKno){
    $.ajax({
        url:"/subjectReference/getAllCourse.do",
        type:"get",
        dataType:"json",
        success:function(result){
            $("#selectCourseId").html("");
            var items=result.data;
            for(var i=0;i<items.length;i++) {
                $("#selectCourseId").append('<option value="' + items[i].courseId + '">' + items[i].courseName + '</option>');
            }
            getKno();
        }
    });
}

function bindBeginstudy(){
    $("#beginstudy").click(function(){
        var courseId=$("#selectCourseId option:selected").val();
        var knoid=$("#selectKnopointId option:selected").val();
        var quetypeId=$("#selectTypeId option:selected").val();
        if(quetypeId==0){
            quetypeId=null;
        }
        if(knoid==0){
            knoid=null;
        }
        table.page({
            pageurl: "/study/getsubjects.do",
            pagedata: {
                courseId:courseId,
                knopointId:knoid,
                quetypeId:quetypeId,
                page: 1,
                rows: 10,
            }
        });
    });
}

function getKnopoints(bindBtn){
    var courseId=$("#selectCourseId option:selected").val();
    $.ajax({
        url:"/subjectReference/getKnopoint.do",
        type:"get",
        dataType:"json",
        data:{
            courseId:courseId,
        },
        success:function(result){
            var items=result.data;
            $("#selectKnopointId").html("<option value='0'>全部</option>");
            for(var i=0;i<items.length;i++) {
                $("#selectKnopointId").append('<option value="' + items[i].knopointId + '">' + items[i].knopointName + '</option>');
            }
            bindBtn();
        }
    });
}

$("document").ready(function () {

    getCourses(function(){getKnopoints(bindBeginstudy)});

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
    $(".container").html('');
    var items = result.data.rows;
    sbjs=[];
    var type=$("#selectTypeId option:selected").html();
    for(var i=0;i<items.length;i++){
        sbjs[items[i].subjectId]={
            subjectId:items[i].subjectId,
            subjectAnswer:items[i].subjectAnswer,
            subjectSolution:items[i].subjectSolution
        };
        if(items[i].quetypeId==1){
            var options="";
            var soptions=items[i].subjectOption.split("@#%");
            for(var j=0;j<soptions.length;j++){
                if(soptions[j]!=""){
                    options+='<div><input type="radio" name="radio_'+i+'">'+soptions[j]+'</div>';
                }
                //alert(options);
            }
            $(".container").append('<div class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(单选题)</span> </h3> </div> <div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option">'+options+'</div><div class="analysis"></div></div>');
        }else if(items[i].quetypeId==5){
            var options="";
            var soptions=items[i].subjectOption.split("@#%");
            for(var j=0;j<soptions.length;j++){
                if(soptions[j]!=""){
                    options+='<div><input type="checkbox" name="checkbox_'+i+'">'+soptions[j]+'</div>';
                }
                //alert(options);
            }
            $(".container").append('<div class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(多选题)</span><span class="sta-answer"></span> </h3> </div> <div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option">'+options+'</div><div class="analysis"></div></div>');
        }else if(items[i].quetypeId==2){
            $(".container").append('<div class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(判断题)</span><span class="sta-answer"></span></h3></div><div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option"><p><input type="radio" value="1" name="radio_'+i+'">正确</p> <p><input type="radio" value="0" name="radio_'+i+'">错误</p> </div><div class="analysis"></div> </div>')
        }else if(items[i].quetypeId==3){
            var blankNum=items[i].subjectAnswer.split("@#%").length;
            var blanks="";
            for(var j=0;j<blankNum;j++){
                blanks+='<div>'+(j+1)+'.<input type="text"></div>'
            }
            $(".container").append('<div class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(填空题)</span></h3></div><div class="ques-name">'+items[i].subjectName+'</div><div class="ques-blank">'+blanks+'</div><div class="analysis"></div> </div>');
        }
    }
    //增加选择框的效果
    $(".ques-option div").click(function () {
        if($(this).find("input").attr("type")=="radio") {
            $(this).find("input[type=radio]").prop("checked", true);
        }else if($(this).find("input").attr("type") == "checkbox"){
            alert($(this).find("input[type=checkbox]").prop("checked"))
            if($(this).find("input[type=checkbox]").prop("checked")){
                $(this).find("input[type=checkbox]").attr("checked",false);
            }
            $(this).find("input[type=checkbox]").attr("checked",true);
        }
    });
}
