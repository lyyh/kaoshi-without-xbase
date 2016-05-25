/**
 * 定义题型规则对象
 */
var singleChoiceQuestion;
var choiceQuestion;
var blankQuestion;
var judgeQuestion;
var shortAnswerQuestion;
var singleChoiceQuestionObj = function () {
    this.questionTypeId = 1;
    this.questionAmount = 0;
    this.questionLevelId = 1;
    this.questionScore = 1;
    this.totalScoreForType = 0;
    this.knowledgePointId = [];
    this.knowledgePoint = "";
    this.knowledgePointIdStr = "";
};
var blankQuestionObj = function () {
    this.questionTypeId = 2;
    this.questionAmount = 0;
    this.questionLevelId = 1;
    this.questionScore = 1;
    this.totalScoreForType = 0;
    this.knowledgePointId = [];
    this.knowledgePoint = "";
    this.knowledgePointIdStr = "";
};
var judgeQuestionObj = function () {
    this.questionTypeId = 3;
    this.questionAmount = 0;
    this.questionLevelId = 1;
    this.questionScore = 1;
    this.totalScoreForType = 0;
    this.knowledgePointId = [];
    this.knowledgePoint = "";
    this.knowledgePointIdStr = "";
};
var shortAnswerQuestionObj = function () {
    this.questionTypeId = 4;
    this.questionAmount = 0;
    this.questionLevelId = 1;
    this.questionScore = 1;
    this.totalScoreForType = 0;
    this.knowledgePointId = [];
    this.knowledgePoint = "";
    this.knowledgePointIdStr = "";
};
var choiceQuestionObj = function () {
    this.questionTypeId = 5;
    this.questionAmount = 0;
    this.questionLevelId = 1;
    this.questionScore = 1;
    this.totalScoreForType = 0;
    this.knowledgePointId = [];
    this.knowledgePoint = "";
    this.knowledgePointIdStr = "";
};
/**
 * 加载提示框工具与对象初始化
 */
$(function () {
    $('[data-toggle="tooltip"]').tooltip();

    singleChoiceQuestion = new singleChoiceQuestionObj();
    blankQuestion = new blankQuestionObj();
    judgeQuestion = new judgeQuestionObj();
    shortAnswerQuestion = new shortAnswerQuestionObj();
    choiceQuestion = new choiceQuestionObj();

    initKnowledgePoint();
});
/**
 * 初始化可选的题型规则
 */
$("#addNewRule").click(function () {
    $(this).blur();
    //先取消掉所有复选框的选中状态。
    $("#myPaper :checkbox:checked").attr("checked", false);

    if (singleChoiceQuestion.questionAmount != 0 && choiceQuestion.questionAmount != 0 && blankQuestion.questionAmount != 0 && judgeQuestion.questionAmount != 0 && shortAnswerQuestion.questionAmount != 0) {
        alert("暂无题目类型规则可以添加");
        return;
    }

    $("#questionType").empty();
    $("#questionType").append($("<option value='1'>单项选择题</option><option value='5'>多项选择题</option><option value='2'>填空题</option><option value='3'>判断题</option><option value='4'>简答题</option>"));

    if (singleChoiceQuestion.questionAmount != 0) {
        $("#questionType option[value=1]").remove();
    }
    if (choiceQuestion.questionAmount != 0) {
        $("#questionType option[value=5]").remove();
    }
    if (blankQuestion.questionAmount != 0) {
        $("#questionType option[value=2]").remove();
    }
    if (judgeQuestion.questionAmount != 0) {
        $("#questionType option[value=3]").remove();
    }
    if (shortAnswerQuestion.questionAmount != 0) {
        $("#questionType option[value=4]").remove();
    }

    $("#newRuleModal").modal('show');
});

/**
 * 用户一次只能选中一个题型规则
 */
$("#myPaper").on("click", ":checkbox", function () {
    $("#myPaper :checkbox").attr("checked", false);
    this.checked = true;
});
/**
 * 知识点初始化
 */
    $.ajax({
        url:"/knopoint/list.do",
        type:"post",
        dataType:"json",
        data:{
            courseId:1,
       },
        success:function(result){
            if(result.code!='1001'){
                return;
            }
            var items=result.data.rows;
            $(items).each(function () {
                $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
            })
            }})

//当去掉任意一个知识点后，【全部知识点】对应的复选框被取消掉；当全部知识点被选中后【全部知识点】对应的复选框被选中
$("#myKnowledgePoint").on("click", "td :checkbox", function () {
    if ($('#myKnowledgePoint td :checkbox:checked').length == $('#myKnowledgePoint td :checkbox').length) {
        $('#myKnowledgePoint th :checkbox')[0].checked = true;
    } else
        $('#myKnowledgePoint th :checkbox').attr('checked', false);
});
//点击全部知识点的复选框选中所有知识点，取消之则取消所有知识点
$("#allKnowledgePoint").click(function () {
    if (this.checked) {
        $("#myKnowledgePoint :checkbox").each(function () {
            this.checked = true;
        });
    }
    else
        $("#myKnowledgePoint :checkbox:checked").attr("checked", false);
});
/**
 * 用户选择每个题型规则的知识点并添加到知识点的文本框中
 */
$("#addKnowledgeFS").click(function () {
    var knowledgePoint = "";

    if ($("#myKnowledgePoint th :checkbox:checked").length != 0) {
        knowledgePoint = '全部知识点 ';
    } else
        $("#myKnowledgePoint td :checkbox:checked").each(function () {
            knowledgePoint += $(this).parent().parent().children().eq(0).html() + " | ";
        });

    knowledgePoint = knowledgePoint.substring(0, knowledgePoint.length - 2);

    $("#knowledgePoint").val(knowledgePoint);
    $("#knowledgePointFC").val(knowledgePoint);
});
/**
 * 确定添加新的题目类型规则
 */
$("#addNewRuleFS").click(function () {
    $(this).blur();
    var questionTypeId = $("#questionType").val();

    var questionType = $("#questionType option:selected").html();
    var questionAmount = $("#questionAmount").val();
    var questionScore = $("#questionScore").val();
    var questionLevelId = $("#questionLevel").val();
    var questionLevel = $("#questionLevel option:selected").html();
    var totalScoreForType = parseInt(questionAmount) * parseInt(questionScore);
    var knowledgePoint = "";
    var knowledgePointIdStr = "";
    var knowledgePointId = [];

    if (questionAmount <= 0) {
        $("#questionAmount").val(1);
        alert("题型规则的题目总数不得小于0");
        return;
    }

    if (questionScore <= 0) {
        alert("题型规则中每个题目的分数不得小于1");
        $("#questionScore").val(1);
        return;
    }

    if ($("#myKnowledgePoint td :checkbox:checked").length == 0) {
        alert("请您选择题型规则制卷的知识点");
        return;
    }

    if ($("#myKnowledgePoint th :checkbox:checked").length != 0) {
        knowledgePoint = '全部知识点 ';
    } else
        $("#myKnowledgePoint td :checkbox:checked").each(function () {
            knowledgePoint += $(this).parent().parent().children().eq(0).html() + " | ";
        });

    $("#myKnowledgePoint td :checkbox:checked").each(function (i) {
        knowledgePointId[i] = this.value;
    });

    $(knowledgePointId).each(function (i) {
        knowledgePointIdStr += knowledgePointId[i] + ",";
    });
    knowledgePointIdStr = knowledgePointIdStr.substring(0, knowledgePointIdStr.length - 1);

    knowledgePoint = knowledgePoint.substring(0, knowledgePoint.length - 2);

    switch (questionTypeId) {
        case '1':
        {
            singleChoiceQuestion = new singleChoiceQuestionObj();
            singleChoiceQuestion.questionAmount = questionAmount;
            singleChoiceQuestion.questionScore = questionScore;
            singleChoiceQuestion.questionTypeId = 1;
            singleChoiceQuestion.questionLevelId = questionLevelId;
            singleChoiceQuestion.totalScoreForType = totalScoreForType;
            singleChoiceQuestion.knowledgePointId = knowledgePointId;
            singleChoiceQuestion.knowledgePoint = knowledgePoint;
            singleChoiceQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '2':
        {
            blankQuestion = new blankQuestionObj();
            blankQuestion.questionAmount = questionAmount;
            blankQuestion.questionScore = questionScore;
            blankQuestion.questionTypeId = 2;
            blankQuestion.questionLevelId = questionLevelId;
            blankQuestion.totalScoreForType = totalScoreForType;
            blankQuestion.knowledgePointId = knowledgePointId;
            blankQuestion.knowledgePoint = knowledgePoint;
            blankQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '3':
        {
            judgeQuestion = new judgeQuestionObj();
            judgeQuestion.questionAmount = questionAmount;
            judgeQuestion.questionScore = questionScore;
            judgeQuestion.questionTypeId = 3;
            judgeQuestion.questionLevelId = questionLevelId;
            judgeQuestion.totalScoreForType = totalScoreForType;
            judgeQuestion.knowledgePointId = knowledgePointId;
            judgeQuestion.knowledgePoint = knowledgePoint;
            judgeQuestion.knowledgePointIdStr = knowledgePointIdStr;

        }
            break;
        case '4':
        {
            shortAnswerQuestion = new shortAnswerQuestionObj();
            shortAnswerQuestion.questionAmount = questionAmount;
            shortAnswerQuestion.questionScore = questionScore;
            shortAnswerQuestion.questionTypeId = 4;
            shortAnswerQuestion.questionLevelId = questionLevelId;
            shortAnswerQuestion.totalScoreForType = totalScoreForType;
            shortAnswerQuestion.knowledgePointId = knowledgePointId;
            shortAnswerQuestion.knowledgePoint = knowledgePoint;
            shortAnswerQuestion.knowledgePointIdStr = knowledgePointIdStr;

        }
            break;
        case '5':
        {
            choiceQuestion = new choiceQuestionObj();
            choiceQuestion.questionAmount = questionAmount;
            choiceQuestion.questionScore = questionScore;
            choiceQuestion.questionTypeId = 5;
            choiceQuestion.questionLevelId = questionLevelId;
            choiceQuestion.totalScoreForType = totalScoreForType;
            choiceQuestion.knowledgePointId = knowledgePointId;
            choiceQuestion.knowledgePoint = knowledgePoint;
            choiceQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
    }

    var $newRule = $('<tr><td><input type="checkbox" name="myRule" value="' + questionTypeId + '"</td><td>' + questionType + '</td><td>' + questionAmount + '个</td><td>' + questionScore + '分</td>' +
        '<td value=' + questionLevelId + '>' + questionLevel + '</td><td><button type="button" data-toggle="popover"><div class="knowledgePointFH">' + knowledgePoint + '</div><span class="glyphicon glyphicon-eye-open"></span></button> </td><td>' + totalScoreForType + '分</td></tr>');

    $newRule.insertBefore($("#myPaper tr:last"));

    //初始化题型规则的模态框
    $("#questionAmount").val(1);
    $("#questionScore").val(1);
    $("#questionLevel").val(1);

    initTools();
    getTotal();
    initKnowledgePoint();

    $("#newRuleModal").modal('hide');
});
/**
 * 删除一个题型规则
 */
$("#removeRule").click(function () {
    $(this).blur();
    var questionTypeId = $("#myPaper :checkbox:checked").val();

    if ($("#myPaper :checkbox:checked").length == 0) {
        alert("请选择要删除的规则");
        return;
    }

    if (confirm("您确定要删除所选规则吗？")) {
        $("#myPaper :checkbox:checked").parent().parent().remove();
    }

    switch (questionTypeId) {
        case '1':
        {
            singleChoiceQuestion.questionAmount = 0;
        }
            break;
        case '2':
        {
            blankQuestion.questionAmount = 0;
        }
            break;
        case '3':
        {
            judgeQuestion.questionAmount = 0;
        }
            break;
        case '4':
        {
            shortAnswerQuestion.questionAmount = 0;
        }
            break;
        case '5':
        {
            choiceQuestion.questionAmount = 0;
        }
            break;
    }

    getTotal();
});
/***
 * 编辑一个题型规则前加载数据到编辑题型模态框中
 */
$("#editRule").click(function () {
    $(this).blur();

    if ($("#myPaper :checkbox:checked").length == 0) {
        alert("请选择一种题目类型规则以查看");
        return;
    }

    //先清空知识点列表，然后再根据题型生成选中的知识点列表
    $("#myKnowledgePoint tr:has(td)").remove();

    var questionTypeId = $("#myPaper :checkbox:checked").val();
    switch (questionTypeId) {
        case '1':
        {
            $("#questionTypeFC option[value=" + questionTypeId + "]").attr("selected", true);
            $("#questionAmountFC").val(singleChoiceQuestion.questionAmount);
            $("#questionScoreFC").val(singleChoiceQuestion.questionScore);
            $("#questionLevelFC option[value=" + singleChoiceQuestion.questionLevelId + "]").attr("selected", true);
            $("#knowledgePointFC").val(singleChoiceQuestion.knowledgePoint);

            $(knowledgePointData).each(function () {
                var kid = this.knopointId;
                var addOrNot = 1;

                for (var i = 0; i < singleChoiceQuestion.knowledgePointId.length; i++) {
                    if (singleChoiceQuestion.knowledgePointId[i] == kid) {
                        addOrNot = 0;
                        break;
                    } else {
                        addOrNot = 1;
                    }
                }

                if (addOrNot == 1)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
                if (addOrNot == 0)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));

            });
        }
            break;
        case '2':
        {
            $("#questionTypeFC option[value=" + questionTypeId + "]").attr("selected", true);
            $("#questionAmountFC").val(blankQuestion.questionAmount);
            $("#questionScoreFC").val(blankQuestion.questionScore);
            $("#questionLevelFC option[value=" + blankQuestion.questionLevelId + "]").attr("selected", true);
            $("#knowledgePointFC").val(blankQuestion.knowledgePoint);
            $(knowledgePointData).each(function () {
                var kid = this.knopointId;
                var addOrNot = 1;

                for (var i = 0; i < blankQuestion.knowledgePointId.length; i++) {
                    if (blankQuestion.knowledgePointId[i] == kid) {
                        addOrNot = 0;
                        break;
                    } else {
                        addOrNot = 1;
                    }
                }

                if (addOrNot == 1)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
                if (addOrNot == 0)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));

            });

        }
            break;
        case '3':
        {
            $("#questionTypeFC option[value=" + questionTypeId + "]").attr("selected", true);
            $("#questionAmountFC").val(judgeQuestion.questionAmount);
            $("#questionScoreFC").val(judgeQuestion.questionScore);
            $("#questionLevelFC option[value=" + judgeQuestion.questionLevelId + "]").attr("selected", true);
            $("#knowledgePointFC").val(judgeQuestion.knowledgePoint);
            $(knowledgePointData).each(function () {
                var kid = this.knopointId;
                var addOrNot = 1;

                for (var i = 0; i < judgeQuestion.knowledgePointId.length; i++) {
                    if (judgeQuestion.knowledgePointId[i] == kid) {
                        addOrNot = 0;
                        break;
                    } else {
                        addOrNot = 1;
                    }
                }

                if (addOrNot == 1)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
                if (addOrNot == 0)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));

            });

        }
            break;
        case '4':
        {
            $("#questionTypeFC option[value=" + questionTypeId + "]").attr("selected", true);
            $("#questionAmountFC").val(shortAnswerQuestion.questionAmount);
            $("#questionScoreFC").val(shortAnswerQuestion.questionScore);
            $("#questionLevelFC option[value=" + shortAnswerQuestion.questionLevelId + "]").attr("selected", true);
            $("#knowledgePointFC").val(shortAnswerQuestion.knowledgePoint);
            $(knowledgePointData).each(function () {
                var kid = this.knopointId;
                var addOrNot = 1;

                for (var i = 0; i < shortAnswerQuestion.knowledgePointId.length; i++) {
                    if (shortAnswerQuestion.knowledgePointId[i] == kid) {
                        addOrNot = 0;
                        break;
                    } else {
                        addOrNot = 1;
                    }
                }

                if (addOrNot == 1)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
                if (addOrNot == 0)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));

            });

        }
            break;
        case '5':
        {
            $("#questionTypeFC option[value=" + questionTypeId + "]").attr("selected", true);
            $("#questionAmountFC").val(choiceQuestion.questionAmount);
            $("#questionScoreFC").val(choiceQuestion.questionScore);
            $("#questionLevelFC option[value=" + choiceQuestion.questionLevelId + "]").attr("selected", true);
            $("#knowledgePointFC").val(choiceQuestion.knowledgePoint);
            $(knowledgePointData).each(function () {
                var kid = this.knopointId;
                var addOrNot = 1;

                for (var i = 0; i < choiceQuestion.knowledgePointId.length; i++) {
                    if (choiceQuestion.knowledgePointId[i] == kid) {
                        addOrNot = 0;
                        break;
                    } else {
                        addOrNot = 1;
                    }
                }

                if (addOrNot == 1)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
                if (addOrNot == 0)
                    $('<tr><td>' + this.knopointName + '</td><td><input type="checkbox" checked value=' + this.knopointId + '></td></tr>').insertAfter($('#myKnowledgePoint thead'));
            });
        }
            break;
    }
    $("#editRuleModal").modal('show');
});
/**
 * 确定修改一个题型的规则
 */
$("#editNewRuleFS").click(function () {
    var questionTypeId = $("#myPaper :checkbox:checked").val();
    var questionAmount = $("#questionAmountFC").val();
    var questionScore = $("#questionScoreFC").val();
    var questionLevelId = $("#questionLevelFC").val();
    var questionLevel = $("#questionLevelFC option:selected").html();
    var totalScoreForType = parseInt(questionAmount) * parseInt(questionScore);
    var knowledgePoint = "";
    var knowledgePointId = [];
    var knowledgePointIdStr="";

    if (questionAmount <= 0) {
        $("#questionAmount").val(1);
        alert("题型规则的题目总数不得小于0");
        return;
    }
    if (questionScore <= 0) {
        alert("题型规则中每个题目的分数不得小于1");
        $("#questionScore").val(1);
        return;
    }
    if ($("#myKnowledgePoint td :checkbox:checked").length == 0) {
        alert("请您选择题型规则制卷的知识点");
        return;
    }

    if ($("#myKnowledgePoint th :checkbox:checked").length != 0) {
        knowledgePoint = '全部知识点 ';
    } else
        $("#myKnowledgePoint td :checkbox:checked").each(function () {
            knowledgePoint += $(this).parent().parent().children().eq(0).html() + " | ";
        });

    $("#myKnowledgePoint td :checkbox:checked").each(function (i) {
        knowledgePointId[i] = this.value;
    });

    $(knowledgePointId).each(function(i){
        knowledgePointIdStr+= knowledgePointId[i]+",";
    });

    knowledgePointIdStr = knowledgePointIdStr.substring(0, knowledgePointIdStr.length - 1);

    knowledgePoint = knowledgePoint.substring(0, knowledgePoint.length - 2);

    switch (questionTypeId) {
        case '1':
        {
            singleChoiceQuestion = new singleChoiceQuestionObj();
            singleChoiceQuestion.questionAmount = questionAmount;
            singleChoiceQuestion.questionScore = questionScore;
            singleChoiceQuestion.questionTypeId = 1;
            singleChoiceQuestion.questionLevelId = questionLevelId;
            singleChoiceQuestion.totalScoreForType = totalScoreForType;
            singleChoiceQuestion.knowledgePointId = knowledgePointId;
            singleChoiceQuestion.knowledgePoint = knowledgePoint;
            singleChoiceQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '2':
        {
            blankQuestion = new blankQuestionObj();
            blankQuestion.questionAmount = questionAmount;
            blankQuestion.questionScore = questionScore;
            blankQuestion.questionTypeId = 2;
            blankQuestion.questionLevelId = questionLevelId;
            blankQuestion.totalScoreForType = totalScoreForType;
            blankQuestion.knowledgePointId = knowledgePointId;
            blankQuestion.knowledgePoint = knowledgePoint;
            blankQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '3':
        {
            judgeQuestion = new judgeQuestionObj();
            judgeQuestion.questionAmount = questionAmount;
            judgeQuestion.questionScore = questionScore;
            judgeQuestion.questionTypeId = 3;
            judgeQuestion.questionLevelId = questionLevelId;
            judgeQuestion.totalScoreForType = totalScoreForType;
            judgeQuestion.knowledgePointId = knowledgePointId;
            judgeQuestion.knowledgePoint = knowledgePoint;
            judgeQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '4':
        {
            shortAnswerQuestion = new shortAnswerQuestionObj();
            shortAnswerQuestion.questionAmount = questionAmount;
            shortAnswerQuestion.questionScore = questionScore;
            shortAnswerQuestion.questionTypeId = 4;
            shortAnswerQuestion.questionLevelId = questionLevelId;
            shortAnswerQuestion.totalScoreForType = totalScoreForType;
            shortAnswerQuestion.knowledgePointId = knowledgePointId;
            shortAnswerQuestion.knowledgePoint = knowledgePoint;
            shortAnswerQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
        case '5':
        {
            choiceQuestion = new choiceQuestionObj();
            choiceQuestion.questionAmount = questionAmount;
            choiceQuestion.questionScore = questionScore;
            choiceQuestion.questionTypeId = 5;
            choiceQuestion.questionLevelId = questionLevelId;
            choiceQuestion.totalScoreForType = totalScoreForType;
            choiceQuestion.knowledgePointId = knowledgePointId;
            choiceQuestion.knowledgePoint = knowledgePoint;
            choiceQuestion.knowledgePointIdStr = knowledgePointIdStr;
        }
            break;
    }

    $("#myPaper :checkbox[value=" + questionTypeId + "]").parent().parent().children().eq(2).html(questionAmount + "分");
    $("#myPaper :checkbox[value=" + questionTypeId + "]").parent().parent().children().eq(3).html(questionScore + "分");
    $("#myPaper :checkbox[value=" + questionTypeId + "]").parent().parent().children().eq(4).html(questionLevel).val(questionLevelId);
    $("#myPaper :checkbox[value=" + questionTypeId + "]").parent().parent().children().eq(5).html('<button type="button" data-toggle="popover"><div class="knowledgePointFH">' + knowledgePoint + '</div><span class="glyphicon glyphicon-eye-open"></span></button>').val(questionLevelId);
    $("#myPaper :checkbox[value=" + questionTypeId + "]").parent().parent().children().eq(6).html(totalScoreForType + "分");
    initTools();
    getTotal();
    initKnowledgePoint();
});
/**
 * 制卷
 */
$("#makePaper").click(function () {
    var totalScore = choiceQuestion.totalScoreForType + shortAnswerQuestion.totalScoreForType + singleChoiceQuestion.totalScoreForType + blankQuestion.totalScoreForType + judgeQuestion.totalScoreForType+"";


    var details = {
        'courseId': $("#course")[0].name,//$("#course")[0].name,
        'mkpaperTerm': $("#semesterYear").val() + $("#semesterTerm").val(),
        'mkpaperScore': totalScore,
        'mkpaperExtime': $("#duration").val(),  //$("#duration").val(),
        'paperName': $("#paperTip").val()
    };

    var rule = [
        {
            'quetypeId': singleChoiceQuestion.questionTypeId,
            'knopointId': singleChoiceQuestion.knowledgePointIdStr,
            'levelId': singleChoiceQuestion.questionLevelId,
            'mkpaperruleScore': singleChoiceQuestion.questionScore,
            'mkpaperruleAmount': singleChoiceQuestion.questionAmount
        },
        {
            'quetypeId': blankQuestion.questionTypeId,
            'knopointId': blankQuestion.knowledgePointIdStr,
            'levelId': blankQuestion.questionLevelId,
            'mkpaperruleScore': blankQuestion.questionScore,
            'mkpaperruleAmount': blankQuestion.questionAmount
        },
        {
            'quetypeId': judgeQuestion.questionTypeId,
            'knopointId': judgeQuestion.knowledgePointIdStr,
            'levelId': judgeQuestion.questionLevelId,
            'mkpaperruleScore': judgeQuestion.questionScore,
            'mkpaperruleAmount': judgeQuestion.questionAmount
        },
        {
            'quetypeId': shortAnswerQuestion.questionTypeId,
            'knopointId': shortAnswerQuestion.knowledgePointIdStr,
            'levelId': shortAnswerQuestion.questionLevelId,
            'mkpaperruleScore': shortAnswerQuestion.questionScore,
            'mkpaperruleAmount': shortAnswerQuestion.questionAmount
        },
        {
            'quetypeId': choiceQuestion.questionTypeId,
            'knopointId': choiceQuestion.knowledgePointIdStr,
            'levelId': choiceQuestion.questionLevelId,
            'mkpaperruleScore': choiceQuestion.questionScore,
            'mkpaperruleAmount': choiceQuestion.questionAmount
        }];

    var paperInfo = {
        "tkMkpaper": details,
        "tkMkpaperrules": rule
    };

    console.log([details, singleChoiceQuestion, blankQuestion, judgeQuestion, shortAnswerQuestion, choiceQuestion]);

    $.ajax({
        type: "POST",
        url: "/robot/robotMkpaper.do",
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(paperInfo),
        success: function (date) {
            if(date ==true)
           alert("生成试卷成功");
            else
            alert("生成试卷失败")
         }
    });
});
/**
 * 随时更新试卷总分、题目个数等信息
 */
function getTotal() {
    var totalAmount = parseInt(choiceQuestion.questionAmount) + parseInt(singleChoiceQuestion.questionAmount) + parseInt(blankQuestion.questionAmount) + parseInt(shortAnswerQuestion.questionAmount) + parseInt(judgeQuestion.questionAmount);
    var totalScore = choiceQuestion.totalScoreForType + shortAnswerQuestion.totalScoreForType + singleChoiceQuestion.totalScoreForType + blankQuestion.totalScoreForType + judgeQuestion.totalScoreForType;

    $("#myPaper tr:last").children().eq(1).html("题目总个数： " + totalAmount + " 个");
    $("#myPaper tr:last").children().eq(2).html("试卷总分： " + totalScore + " 分");
}
/**
 * 更新知识点模态框的信息
 */
function initKnowledgePoint() {
    $("#myKnowledgePoint :checkbox").each(function () {
        this.checked = true;
    });
    $("#knowledgePoint").val("全部知识点");
}
/**
 * 初始化提示框工具
 */
function initTools() {
    $('[data-toggle="popover"]').each(function () {
        var element = $(this);
        element.popover({
            trigger: 'manual',
            placement: 'right',
            title: "知识点提示",
            html: 'true',
            content: $(this).children().eq(0).html()
        }).on("mouseenter", function () {
            var _this = this;
            $(this).popover("show");
            $(this).siblings(".popover").on("mouseleave", function () {
                $(_this).popover('hide');
            });
        }).on("mouseleave", function () {
            var _this = this;
            setTimeout(function () {
                if (!$(".popover:hover").length) {
                    $(_this).popover("hide")
                }
            }, 100);
        });
    });
}
