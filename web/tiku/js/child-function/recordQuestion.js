/**
 * 初始化工具模块
 * */
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

/**
 *UEditor编辑框区域模块
 * */
//编写题干内容的UEditor
var questionContent = UE.getEditor('questionContent', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//编写简答题答案的UEditor
var shortAnswer = UE.getEditor('shortAnswer', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//设置答案解析的UEditor
var questionSolution = UE.getEditor('questionSolution', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//新增一个选择题选项的UEditor
var newChoice = UE.getEditor('newChoice', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//新增一个填空题答题区间的UEditor
var newBlankAnswer = UE.getEditor('newBlankAnswer', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//修改一个选择题选项的UEditor
var myChoice = UE.getEditor('myChoice', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

//修改一个填空题答题区间的UEditor
var myBlankAnswer = UE.getEditor('myBlankAnswer', {
    toolbars: [[
        'undo', 'redo', '|', 'bold', 'italic', 'underline', 'justifycenter', '|', 'kityformula', 'date', 'time',
        'simpleupload', 'spechars', 'insertvideo', 'formatmatch', "|", 'inserttable', 'deletetable', 'insertparagraphbeforetable',
        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergeright', 'mergedown'
    ]]
});

/**
 * 题型选择模块
 * */

$("#questionType").change(function () {
    changeType(this.value);
});

/**
 * 改变题型
 */
function changeType(type) {
    $('#questionType option[value="' + type + '"]').attr("selected", "selected");
    $(".totalContainer>div[name]").hide(0);
    if (type == 1 || type == 5) {
        $("#choiceQuestionArea").show(0);
    } else if (type == 3) {
        $("#blankQuestionArea").show(0);
    } else if (type == 2) {
        $("#judgeQuestionArea").show(0);
    } else if (type == 4) {
        $("#shortAnswerArea").show(0);
    }
}

/**
 * 章节选择动态获取知识点
 */
$("#chapter").change(function () {
    $.get("/subjectReference/getKnopoint.do"
        , {courseId: 1, chapterId: $("#chapter").val()}
        , function (data) {
            $("#knowledgePointList").html("");
            for (var i = 0; i < data.length; i++) {
                $("#knowledgePointList").append("<option>" + data[i].knopointName + "</option>");
            }
        });
});

/**
 * 选择题模块
 * */
//新增一个选项
var choiceLetter = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
var choiceNum = 1;

$("#AddANewChoice").click(function () {
    $("#choiceNum").html(choiceLetter[choiceNum]);
});

$("#addNewChoiceFS").click(function () {
    if (newChoice.getContent() == "") {
        alert("选项不得为空");
        return;
    }
    var $newChoice = $('<tr><td style="vertical-align:middle;">' + choiceLetter[choiceNum] + '</td><td>' + newChoice.getContent() + '</td><td style="vertical-align:middle;"> <button class="changeAChoice" name=' + choiceLetter[choiceNum] + ' data-toggle="modal" data-target="#changeChoice"><span class="glyphicon glyphicon-pencil"></span></button></td><td style="vertical-align:middle;"><input type="checkbox" name=' + choiceLetter[choiceNum] + '></td> </tr>')
    $("#questionChoice").append($newChoice);
    choiceNum++;
    newChoice.setContent("<p></p>");
});

//删除一个选项
$("#removeAChoice").click(function () {
    if ($("#questionChoice tr td").length) {
        if (window.confirm("您确定要删除最后一个选项（选项" + choiceLetter[choiceNum - 1] + "）吗？")) {
            $("#questionChoice tr:last").remove();
            choiceNum--;
        }
    } else {
        alert("已经没有任何的选项");
    }

});

//修改一个选项
var $choiceToChange = "";

$("#questionChoice").on("click", ":button", function () {
    $choiceToChange = $(this);
    $("#choiceNumForChange").html(this.name);
    myChoice.setContent($(this).parent().parent().children().eq(1).html());
});

$("#changeChoiceFS").click(function () {
    if (myChoice.getContent() == "") {
        alert("选项不得为空");
        return;
    }
    $choiceToChange.parent().parent().children().eq(1).html(myChoice.getContent());
    myChoice.setContent("<p></p>");
});

//设置正确答案
$("#questionChoice").on("click", ":checkbox", function () {
    var questionType = $('#questionType').val();

    if (questionType == 1) {
        $('#questionChoice :checkbox').removeAttr("checked");
        this.checked = true;
    }
});

//当用户将题型由多项选择题转成单项选择题时，如果有多个选项被设置为正确答案，系统将会将答案清空
$("#questionType").change(function () {
    if (this.value == 1) {
        var l = $("#questionChoice :checkbox:checked").length;
        if (l >= 2) {
            alert("单项选择题只支持一个选项作为正确答案，请您重新设置正确答案");
            $('#questionChoice :checkbox').removeAttr("checked");
        }
    }
});

/**
 * 填空题模块
 * */

//新增一个答案
var blankNum = 1;

$("#AddANewBlankAnswer").click(function () {
    $("#blankNum").html(blankNum);
});

$("#addNewBlankAnswerFS").click(function () {
    if (newBlankAnswer.getContent() == "") {
        alert("答案不得为空");
        return;
    }
    var $newBlankAnswer = $('<tr><td style="vertical-align:middle;">第' + blankNum + '个答题区间</td><td>' + newBlankAnswer.getContent() + '</td><td style="vertical-align:middle;"><button name=' + blankNum + ' data-toggle="modal" data-target="#changeBlankAnswer"><span class="glyphicon glyphicon-pencil"></span></button></td></tr>');
    $("#blankAnswer").append($newBlankAnswer);
    blankNum++;
    newBlankAnswer.setContent("<p></p>");
});
//删除一个答案
$("#removeABlankAnswer").click(function () {
    if ($("#blankAnswer tr td").length) {
        if (window.confirm("您确定要删除最后一个答题区间（答题区间" + (blankNum - 1) + "）吗？"))
            $("#blankAnswer tr:last").remove();
        blankNum--;
    } else {
        alert("已经没有任何答案");
    }

});
//修改一个答案
var $blankAnswerToChange = "";

$("#blankAnswer").on("click", ":button", function () {
    $blankAnswerToChange = $(this);
    $("#blankNumForChange").html(this.name);
    myBlankAnswer.setContent($(this).parent().parent().children().eq(1).html());
});

$("#changeBlankAnswerFS").click(function () {
    if (myBlankAnswer.getContent() == "") {
        alert("答案不得为空");
        return;
    }
    $blankAnswerToChange.parent().parent().children().eq(1).html(myBlankAnswer.getContent());
    myBlankAnswer.setContent("<p></p>");
});

/**
 * 判断题模块
 * */
//设置正确答案为True
$("#setAnswerTrue").click(function () {
    $("#judgeQuestionAnswer").html('正确').val(1).removeClass().addClass('answerTrue');
});

//设置正确答案为false
$("#setAnswerFalse").click(function () {
    $("#judgeQuestionAnswer").html('错误').val(0).removeClass().addClass('answerFalse');
});


/**
 * 预览题目及试卷提交模块
 * */
$("#previewQuestion").click(function () {
    var questionType = $("#questionType").val();
    var questionLevel = $("#questionLevel option:selected").html();
    var knowledgePoint = $("#knowledgePoint").val();
    if (knowledgePoint == "") {
        knowledgePoint = "通用知识点";
    }
    var chapter = $("#chapter").val();
    if (chapter == "") {
        chapter = "通用章节";
    }
    //var course=$().val();
    //var teacher=$().val();
    var que_type = $("#questionType option:selected").html();
    var myQuestionContent = questionContent.getContent();
    var myQuestionSolution = questionSolution.getContent();
    var questionOptions = [];
    var blankAnswers = [];
    var questionOption = "";
    var questionAnswer = "";

    //获得题目选项（选择题）
    $('#questionChoice tr:has(td)').each(function (i) {
        questionOptions[i] = $(this).find('td').eq(1).html();
    });

    for (var i = 0; i < questionOptions.length; i++) {
        questionOption += choiceLetter[i + 1] + ".&nbsp;&nbsp;" + questionOptions[i] + '<br>';
    }

    //获取题目答案
    if (questionType == 1 || questionType == 5) {
        $('#questionChoice :checkbox:checked').each(function (i) {
            questionAnswer += this.name;
        });
    } else if (questionType == 3) {
        $('#blankAnswer tr:has(td)').each(function (i) {
            blankAnswers[i] = $(this).find('td').eq(1).html();
        });

        for (var i = 0; i < blankAnswers.length; i++) {
            questionAnswer += "答题区间" + (i + 1) + ":&nbsp;&nbsp;" + blankAnswers[i] + '<br>';
        }
    } else if (questionType == 2) {
        questionAnswer = $("#judgeQuestionAnswer").html();
    } else if (questionType == 4) {
        questionAnswer = shortAnswer.getContent();
    }

    if (questionType == 1 || questionType == 5)
        $("#myQuestion").html('<tr><td class="td-title" colspan="4">题目通用</td></tr><tr><td class="my-th">科目</td><td>&nbsp;&nbsp;java</td><td class="my-th">题目类型</td><td>&nbsp;&nbsp;' + que_type + '</td></tr> <tr><td class="my-th">难度</td><td>&nbsp;&nbsp;' + questionLevel + '</td><td class="my-th">章节</td><td>&nbsp;&nbsp;' + chapter + '</td></tr> <tr><td class="my-th">知识点</td><td>&nbsp;&nbsp;' + knowledgePoint + '</td><td class="my-th">出题人</td><td>&nbsp;&nbsp;教师1</td></tr> <tr><td class="td-title" colspan="4">题目内容</td></tr> <tr><td colspan="4">' + myQuestionContent +'<br>'+ questionOption + '</td></tr> <tr><td class="td-title" colspan="4">参考答案</td></tr>  <tr><td colspan="4">' + questionAnswer + '</td></tr> <tr><td class="td-title" colspan="4">答案解析</td></tr> <tr><td colspan="4">' + myQuestionSolution + '</td></tr>');
});

function initial() {
    questionContent.setContent("<p></p>");
    questionSolution.setContent("<p></p>");
    shortAnswer.setContent("<p></p>");
    newChoice.setContent("<p></p>");
    newBlankAnswer.setContent("<p></p>");
    myChoice.setContent("<p></p>");
    myBlankAnswer.setContent("<p></p>");
    $("#questionChoice tr:eq(0)~*").empty();
    $("#blankAnswer tr:eq(0)~*").empty();
    choiceNum = 1;
    blankNum = 1;
}

