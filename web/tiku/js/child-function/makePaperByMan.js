//初始化提示框工具
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

//获得知识点信息
var knowledgePointInfo ;
var data;

$.ajax({
    url:"/rg_getCourseByName.do",
    data:{
        courseName :"java"
    },
    async:false,
    dataType:"json",
    success:function (data) {
        $("#course").val(data.courseName);
        $("#dataInput").val(data.courseId);
    }
});

var courseIdtemp=$("#dataInput").val();
   $.ajax({
       url:"/rg_knpoints.do",
       data:{
           courseId :courseIdtemp
       },
       dataType:"json",
        success:function (data) {
            $.each(data,function(index,objs){
                $("#knowledgePoint").append($("<option value='" + this.knopointId + "'>" + this.knopointName + "</option>"));
            })
            // knowledgePointInfo=data;
        }
   })


// $(knowledgePointInfo).each(function () {
//     $("#knowledgePoint").append($("<option value='" + this.knopointId + "'>" + this.knopointName + "</option>"));
// });


//$.post("getKnowledgePoint.do", $("#course").val(), function (data) {
//    data.each(function () {
//        $("#knowledgePoint").append($("<option value='" + this.knopointId + "'>" + this.knopointName + "</option>"));
//    });
//});

//获取题目信息
$("#search").click(function () {
    $("#questionContainer").empty();
    $.ajax({
        type:"post",
        url:"/rg_getByKpoint.do",
        dataType:"json",
        data:{
            'courseId': $("#dataInput").val(),
            'chapterId': $("#chapter").val(),
            'knopointId': $("#knowledgePoint").val(),
            'levelId': $("#questionLevel").val(),
            'quetypeId': $("#questionType").val()
        },
        success:function (obj) {

            $.each(obj,function (index,content) {
                var subjectId = content.subjectId;
                var addOrNot = 1;
                $("#paperContainer :checkbox").each(function () {
                    if (content.value == subjectId) {
                        addOrNot = 0;
                        return false;
                    } else {
                        addOrNot = 1;
                    }
                });
                 
                if (addOrNot == 1)
                    $("#questionContainer").append('<tr><td><input type="checkbox" name="' + content.quetypeId + '" class="form-control" value="' + content.subjectId + '"> <div name="questionContent">' + content.subjectName + '</div><div name="questionOptions">' + content.subjectOption + '</div></td></tr>');
                if (addOrNot == 0)
                    $("#questionContainer").append('<tr><td><input type="checkbox" name="' + content.quetypeId + '" class="form-control" value="' + content.subjectId + '" checked> <div name="questionContent">' + content.subjectName + '</div><div name="questionOptions">' + content.subjectOption + '</div></td></tr>');

                addOrNot = 0;
            })


        }
    })
    
    
});

//选中题目前的复选框将题目加入试卷，取消之将题目从试卷中移除
$("#questionContainer").on("change", ":checkbox", function () {
    var questionNum = this.value;
    var queType = parseInt(this.name) - 1;
    var addLineOrNot = 0;
    var removeLineOrNot = 0;

    if (this.checked) {
        //区分各种类型题目
        $("#paperContainer :checkbox").each(function () {
            if (this.name == queType) {
                addLineOrNot = 0;
                return false;
            } else {
                addLineOrNot = 1;
            }
        });

        if (addLineOrNot == 1) {
            $('<td></td>').appendTo("#paperContainer table tr[name]:eq(" + queType + ")");
            addLineOrNot = 0;
        }

        $(this).parent().parent().clone(true).insertBefore("#paperContainer table tr[name]:eq(" + queType + ")");
        var questionScore = $(".setDefaultScoreModal input[name=" + this.name + "]").val();
        var scoreForEach = $('<div class="scoreDe"> <input name="' + questionNum + '" class="form-control" type="number" min="1" value="' + questionScore + '"> <div class="scoreDeAdd">分</div></div>')
        scoreForEach.insertAfter($("#paperContainer table :checkbox[value='" + questionNum + "']+div"));
    } else {
        $("#paperContainer :checkbox[value='" + questionNum + "']").parent().parent().remove();

        $("#paperContainer :checkbox").each(function () {
            if (parseInt(this.name-1) == queType) {
                removeLineOrNot = 0;
                return false;
            } else {
                removeLineOrNot = 1;
            }
        });

        if (removeLineOrNot == 1||!$("#paperContainer :checkbox[name]").length) {
            $("#paperContainer table tr[name]:eq(" + queType + ") td").remove();
            removeLineOrNot = 0;
        }
    }

    getScore();
});

$("#paperContainer").on("change", ":checkbox", function () {
    var questionNum = this.value;
    var removeLineOrNot = 0;
    var queType = parseInt(this.name) - 1;

    if (window.confirm("您确定要将这个题目从试卷中删除吗？")) {
        $("#paperContainer :checkbox[value='" + questionNum + "']").parent().parent().remove();
        $("#paperContainer :checkbox").each(function () {
            if (parseInt(this.name-1) == queType) {
                removeLineOrNot = 0;
                return false;
            } else {
                removeLineOrNot = 1;
            }
        });

        if (removeLineOrNot == 1||!$("#paperContainer :checkbox[name]").length) {
            $("#paperContainer table tr[name]:eq(" + queType + ") td").remove();
            removeLineOrNot = 0;
        }
        $("#questionContainer :checkbox[value='" + questionNum + "']")[0].checked = false;
    }
    else {
        this.checked = true;
    }
    getScore();
});

//设置每种类型题目的默认分数
$("#choiceQueScore").change(function () {
    $("#paperContainer :checkbox[name=1]").parent().children().children().val(this.value);
    getScore();
});

$("#judgementQueScore").change(function () {
    $("#paperContainer :checkbox[name=2]").parent().children().children().val(this.value);
    getScore();
});

$("#blankQueScore").change(function () {
    $("#paperContainer :checkbox[name=3]").parent().children().children().val(this.value);
    getScore();
});

$("#answerQueScore").change(function () {
    $("#paperContainer :checkbox[name=4]").parent().children().children().val(this.value);
    getScore();
});

$("#paperContainer").on("change", "input", function () {
    getScore();
});

//计算已选题目的总分
function getScore() {
    var score = 0;
    $("#paperContainer :checkbox").each(function () {
        score += parseInt($(this).parent().children().children("input").val());
    });

    $("#score").html(score);
}

//生成试卷
$("#makePaper").click(function () {
    var scoreWarningCondition = $("#scoreWarningCondition").val();
    var totalScore = $("#totalScore").val();
    var myScore = parseInt($("#score").html());

    if (scoreWarningCondition == 1) {
        if (myScore > totalScore) {
            if (!confirm("已选定题目的总分高于您设置的总分，是否继续制卷？")) {
                return;
            }
        }
    } else if (scoreWarningCondition == 2) {
        if (!confirm("已选定题目的总分低于您设置的总分，是否继续制卷？")) {
            return;
        }
    } else if (scoreWarningCondition == 3) {
        if (myScore != totalScore) {
            if (!confirm("已选定题目的总分不满足您设置的总分，是否继续制卷？")) {
                return;
            }
        }
    } else if (scoreWarningCondition == 4) {
        if (myScore < totalScore) {
            alert("已选定题目的总分低于您设置的总分，制卷失败");
            return;
        }
    } else if (scoreWarningCondition == 5) {
        if (myScore > totalScore) {
            alert("已选定题目的总分高于您设置的总分，制卷失败");
            return;
        }
    } else if (scoreWarningCondition == 6) {
        if (myScore != totalScore) {
            alert("已选定题目的总分不满足您设置的总分，制卷失败");
            return;
        }
    }

    var subjectInfo = "[";

    $("#paperContainer :checkbox").each(function () {
        var subjectId = this.value;
        var score = parseInt($(this).parent().children().children("input").val());
        var total = "{subjectId:" + subjectId + ",scoreForEach:" + score + "},";
        subjectInfo += total;
    });
    
    if(subjectInfo!="[") {
        subjectInfo = subjectInfo.substring(0, subjectInfo.length - 1) + "]";
    }else{
        subjectInfo="";
        alert(subjectInfo)
    }

    var paperInfo = {
        'courseName': $("#course").val(),
        'teacherName': $("#teacherName").val(),
        'semesterTerm': $("#semesterYear").val()+","+$("#semesterTerm").val(),
        'duration': $("#duration").val(),
        'questionAndScore': subjectInfo,
        'score':$("#score").html(),
        'note':$("#note").val(),
        'mkpaperType':"1"
    };

    // $.post("/labour/createPaper.do", paperInfo,
    //     success:function(data) {
    //     alert(data);
    // });
    $.ajax({
        url:"/labour/createPaper.do",
        dataType:"json",
        type:"post",
        data:paperInfo,
        success:function(data){
            alert(data.msg);
        }
    })
});


