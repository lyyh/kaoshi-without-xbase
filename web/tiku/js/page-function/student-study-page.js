/**
 * Created by hukaihe on 2016/4/30.
 * child functions of question management
 * which teachers can do with such as recordQuestion are in this JavaScript file
 */
var myContent;
op = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];

function getBlankAnswers(answer){
    var arr=answer.split("@#%");
    console.log(arr );
    var num=0;
    for(var i=0;i<arr.length;i++){
        if(arr[i]!=''){
            num++;
        }
    }
    return num;
}

function checkAnswers(){
    var answercheck=[];
    answercheck[0]=[];//正确
    answercheck[1]=[];//错误
    for(var i= 0, j=0,k=0;i<sbjs.length;i++){
        if(sbjs[i].quetypeId==3){

        }else{
            if(sbjs[i].subjectAnswer==$('.question:eq('+i+') input:checked').val()){
                answercheck[0][j++]=i+1;
            }else{
                answercheck[1][k++]=i+1;
            }
        }
    }
    return answercheck;
}

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
    
    //查看解析
    $(".check-analysis").click(function () {
        for(var i=0;i<sbjs.length;i++){
            if(sbjs[i].quetypeId==3){
                var answers=sbjs[i].subjectAnswer.split('@#%');
                //console.log(answers);
                for(var j= 0,k=0;j<getBlankAnswers(sbjs[i].subjectAnswer);j++){
                    if(answers[j]!=''){
                        //alert(answers[j]);
                        $('.question:eq('+i+')').find('.inputAs:eq('+k+')').find('.sta-answer').append(answers[j]);
                        k++;
                    }
                }
                $('.question:eq('+i+') .analysis').html(sbjs[i].subjectSolution);
            }else if(sbjs[i].quetypeId==2){
                $('.question:eq('+i+') .sta-answer').html(sbjs[i].subjectAnswer==1?'正确':'错误');
                $('.question:eq('+i+') .analysis').html(sbjs[i].subjectSolution);
            }else{
                $('.question:eq('+i+') .sta-answer').html(sbjs[i].subjectAnswer);
                $('.question:eq('+i+') .analysis').html(sbjs[i].subjectSolution);
            }
        }
        $("#show-result").modal("toggle");
    })
});

function setItems(result) {
    $(".subjects").html('');
    var items = result.data.rows;
    sbjs=[];
    //var type=$("#selectTypeId option:selected").html();
    for(var i= 0,r=0;i<items.length;i++,r++){
        sbjs[r]={
            subjectId:items[i].subjectId,
            quetypeId:items[i].quetypeId,
            subjectAnswer:items[i].subjectAnswer,
            subjectSolution:items[i].subjectSolution
        };
        if(items[i].quetypeId==1){
            var options="";
            var soptions=items[i].subjectOption.split("@#%");
            for(var j= 0,k=0;j<soptions.length;j++){
                if(soptions[j]!=""){
                    options+='<div>'+op[k]+'.<input type="radio" value="'+op[k]+'" name="radio_'+i+'">'+soptions[j]+'</div>';
                    k++;
                }
                //alert(options);
            }
            $(".subjects").append('<div id="que'+i+'" class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(单选题)</span><span class="sta-answer"> </h3> </div> <div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option">'+options+'</div><div class="analysis"></div></div>');
        }else if(items[i].quetypeId==5){
            var options="";
            var soptions=items[i].subjectOption.split("@#%");
            for(var j= 0,k=0;j<soptions.length;j++){
                if(soptions[j]!=""){
                    options+='<div>'+op[k]+'.<input type="checkbox" value="'+op[k]+'" name="checkbox_'+i+'">'+soptions[j]+'</div>';
                    k++;
                }
                //alert(options);
            }
            $(".subjects").append('<div id="que'+i+'" class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(多选题)</span><span class="sta-answer"></span> </h3> </div> <div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option">'+options+'</div><div class="analysis"></div></div>');
        }else if(items[i].quetypeId==2){
            $(".subjects").append('<div id="que'+i+'" class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(判断题)</span><span class="sta-answer"></span></h3></div><div class="ques-name">'+items[i].subjectName+'</div><div class="ques-option"><p><input type="radio" value="1" name="radio_'+i+'">正确</p> <p><input type="radio" value="0" name="radio_'+i+'">错误</p> </div><div class="analysis"></div> </div>')
        }else if(items[i].quetypeId==3){
            var blankNum=getBlankAnswers(items[i].subjectAnswer);
            //alert(blankNum);
            var blanks="";
            for(var j=0;j<blankNum;j++){
                blanks+='<div class="inputAs">'+(j+1)+'.<input type="text"><span class="sta-answer"></span></div>'
            }
            $(".subjects").append('<div class="question"><div class="ques-header"><h3><span class="ques-id">习题'+(i+1)+'</span><span class="ques-type">(填空题)</span></h3></div><div class="ques-name">'+items[i].subjectName+'</div><div class="ques-blank">'+blanks+'</div><div class="analysis"></div> </div>');
        }else{
            r--;
        }
    }
    $('<div class="btn btn-success submit">提交</div>').appendTo(".container");
    //增加选择框的效果
    $(".ques-option div").click(function () {
        if($(this).find("input").attr("type")=="radio") {
            $(this).find("input[type=radio]").prop("checked", true);
        }else if($(this).find("input").attr("type") == "checkbox"){
            //alert($(this).find("input[type=checkbox]").prop("checked"))
            if($(this).find("input[type=checkbox]").prop("checked")){
                $(this).find("input[type=checkbox]").attr("checked",false);
            }
            $(this).find("input[type=checkbox]").attr("checked",true);
        }
    });
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

            var answercheck=checkAnswers();
            $('.ques-correct').html('正确:');
            for(var i=0;i<answercheck[0].length;i++){
                $('.ques-correct').append('<a>第'+answercheck[0][i]+'题<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> </a>');
            }

            $('.ques-wrong').html('错误:');
            for(var i=0;i<answercheck[1].length;i++){
                $('.ques-wrong').append('<a class="false">第'+answercheck[1][i]+'题<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>');
            }

            $("#tip").modal("toggle");
            $("#tip .modal-footer").html("");
            $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
            $("#show-result").modal({
                backdrop:"static",
                keyboard:false
            })
        });

        //是否提交(取消按钮)
        $("#cancel").click(function () {
            $("#tip").modal("toggle");
            $("#tip .modal-footer").html("");
            $("#tip .modal-footer").html("<button type='button' class='btn bg-primary' id='sure' data-dismiss='modal'>确定</button>");
        })
    })
}
