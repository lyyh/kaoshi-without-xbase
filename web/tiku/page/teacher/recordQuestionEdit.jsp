<%--
  Created by IntelliJ IDEA.
  User: liuruijie
  Date: 2016/4/11
  Time: 23:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/tiku/css/functionParts/recordQuestion.css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="/tiku/ueditor/themes/default/css/ueditor.css">
<style>
    .modal-body {
        height: 295px;
    }

    #updateQuestion {
        margin: 20px 0 0 3%;
    }
    #questionChoice img{
        width: 100px !important;
    }
</style>
<div class="totalContainer">
    <div id="questionDetailsArea">
        <div class="myTip">设置题目通用信息</div>
        <div class="setQuestionDetails">
            <!--a.设置题目类型-->
            <select id="questionType" class="form-control input-sm">
                <option value="1">单项选择题</option>
                <option value="5">多项选择题</option>
                <option value="3">填空题</option>
                <option value="2">判断题</option>
                <option value="4">简答题</option>
            </select>
            <!--b.设置题目难度-->
            <select id="questionLevel" class="form-control questionLevel input-sm">
                <option value="1" ${subject.levelId==1?"selected='selected'":""}>简单</option>
                <option value="2" ${subject.levelId==2?"selected='selected'":""}>中等</option>
                <option value="3" ${subject.levelId==3?"selected='selected'":""}>困难</option>
            </select>
            <!--d.设置知识点-->
            <select id="knowledgePoint" class="form-control knowledgePoint input-sm">
                <option value="通用知识点">通用知识点</option>
            </select>
            <!--c.设置章节-->
            <div class="chapter">
                <input id="chapter" value="${subject.chapterId}" type="number" class="form-control"
                       placeholder="章节数" min="0">

                <div class="chapter-add">章</div>
            </div>
        </div>
    </div>

    <div id="questionContentArea">
        <div class="myTip">设置题干信息</div>
        <div class="setQuestionContent">
            <div id="questionContent"></div>
        </div>
    </div>

    <div id="choiceQuestionArea" name="choiceQuestionArea">
        <div class="myTip">设置选项及正确答案</div>
        <div class="setQuestionChoice">
            <div class=" btn-group btn-group-sm">
                <button id="AddANewChoice" class="btn btn-default" data-toggle="modal"
                        data-target="#addNewChoice">
                <span data-toggle="tooltip" data-placement="right" title="增加一个选项"
                      class="glyphicon glyphicon-plus"></span>
                </button>
                <button id="removeAChoice" class="btn btn-default">
                <span data-toggle="tooltip" data-placement="bottom" title="减少一个选项"
                      class="glyphicon glyphicon-minus"></span>
                </button>
            </div>
            <table id="questionChoice" class="table table-striped questionChoice">
                <tr>
                    <th>选项</th>
                    <th>选项内容</th>
                    <th>编辑选项</th>
                    <th>正确答案</th>
                </tr>
            </table>
        </div>
    </div>

    <div id="blankQuestionArea" name="blankQuestionArea">
        <div class="myTip">设置填空题的正确答案</div>
        <div class="setBlankAnswer">
            <div class=" btn-group btn-group-sm">
                <button id="AddANewBlankAnswer" class="btn btn-default" data-toggle="modal"
                        data-target="#addNewBlankAnswer">
                <span data-toggle="tooltip" data-placement="right" title="增加一个答案"
                      class="glyphicon glyphicon-plus"></span>
                </button>
                <button id="removeABlankAnswer" class="btn btn-default">
                <span data-toggle="tooltip" data-placement="bottom" title="减少一个答案"
                      class="glyphicon glyphicon-minus"></span>
                </button>
            </div>
            <table id="blankAnswer" class="table table-striped blankAnswer">
                <tr>
                    <th>答题区间</th>
                    <th>答案内容</th>
                    <th>编辑答案</th>
                </tr>
            </table>
        </div>
    </div>

    <div id="judgeQuestionArea" name="judgeQuestionArea">
        <div class="myTip">设置判断题的正确答案</div>
        <div class="setJudgeQuestionAnswer">
            <div class=" btn-group btn-group-sm">
                <button id="setAnswerTrue" class="btn btn-default"><span class="glyphicon glyphicon-ok"
                                                                         data-toggle="tooltip"
                                                                         data-placement="right"
                                                                         title="设置这个题的答案为正确"></span></button>
                <button id="setAnswerFalse" class="btn btn-default"><span class="glyphicon glyphicon-remove"
                                                                          data-toggle="tooltip"
                                                                          data-placement="right"
                                                                          title="设置这个题的答案为错误"></span></button>
                <br><br>
                <span>答案：<span id="judgeQuestionAnswer"></span></span>
            </div>
        </div>
    </div>

    <div id="shortAnswerArea" name="shortAnswerArea">
        <div class="myTip">设置简答题正确答案</div>
        <div class="setShortAnswer">
            <div class=" btn-group btn-group-sm">
                <button class="btn btn-default">
                <span data-toggle="tooltip" data-placement="right" title="重新设置正确答案"
                      class="glyphicon glyphicon-repeat"></span>
                </button>
            </div>
            <div id="shortAnswer">
            </div>
        </div>
    </div>

    <div id="questionSolutionArea">
        <div class="myTip">设置答案解析</div>
        <div class="setQuestionSolution">
            <div id="questionSolution"></div>
        </div>
    </div>


    <button id="previewQuestion" class="btn btn-success" data-toggle="modal" data-target="#show-info">
        预览题目
    </button>
    <button id="updateQuestion" class="btn btn-lg btn-primary">确定更新</button>
</div>
<!--模态框模块-->
<!--新增一个选择题选项的模态框-->
<div class="modal fade" id="addNewChoice" tabindex="-1">
    <div class="modal-dialog" style="width: 50%;min-width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    增加选项<span id="choiceNum"></span>
                </h4>
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
            </div>

            <div class="modal-body">
                <div id="newChoice">

                </div>
            </div>

            <div class="modal-footer">
                <%--<span>请保持选项格式的统一性</span>--%>
                <button id="addNewChoiceFS" class="btn sure" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--新增一个填空题答案的模态框-->
<div class="modal fade" id="addNewBlankAnswer" tabindex="-1">
    <div class="modal-dialog" style="width: 50%;min-width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    增加答题区间<span id="blankNum"></span>
                </h4>
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
            </div>

            <div class="modal-body">
                <div id="newBlankAnswer">

                </div>
            </div>

            <div class="modal-footer">
                <%--<span>请保持答案格式的统一性</span>--%>
                <button id="addNewBlankAnswerFS" class="btn sure" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--修改一个选择题选项的模态框-->
<div class="modal fade" id="changeChoice" tabindex="-1">
    <div class="modal-dialog" style="width: 50%;min-width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    修改选项<span id="choiceNumForChange"></span>
                </h4>
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
            </div>

            <div class="modal-body">
                <div id="myChoice">

                </div>
            </div>

            <div class="modal-footer">
                <%--<span>请保持选项格式的统一性</span>--%>
                <button id="changeChoiceFS" class="btn sure" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--修改一个填空题答案的模态框-->
<div class="modal fade" id="changeBlankAnswer" tabindex="-1">
    <div class="modal-dialog" style="width: 50%;min-width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    修改答题区间<span id="blankNumForChange"></span>
                </h4>
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
            </div>

            <div class="modal-body">
                <div id="myBlankAnswer">

                </div>
            </div>

            <div class="modal-footer">
                <%--<span>请保持选项格式的统一性</span>--%>
                <button id="changeBlankAnswerFS" class="btn sure" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--题目预览的模态框-->
<div class="modal fade" id="show-info" tabindex="-1">
    <div class="modal-dialog" style="width: 67%;min-width: 850px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    题目预览
                </h4>
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
            </div>

            <div class="modal-body myQuestion">
                <table id="myQuestion" class="table table-bordered">

                </table>
            </div>

            <div class="modal-footer">
                <%--<span>这将是您录入题库的最终结果</span>--%>
                <button class="btn sure" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<script src="/tiku/ueditor/ueditor.config.js"></script>
<script src="/tiku/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/tiku/ueditor/kityformula-plugin/addKityFormulaDialog.js"></script>
<script type="text/javascript" charset="utf-8" src="/tiku/ueditor/kityformula-plugin/getKfContent.js"></script>
<script type="text/javascript" charset="utf-8" src="/tiku/ueditor/kityformula-plugin/defaultFilterFix.js"></script>
<script src="/tiku/js/child-function/recordQuestion.js"></script>
<script>
    function initKnopoint(courseId,chapterId){
        $.ajax({
            url:"/knopoint/list.do",
            type:"post",
            dataType:"json",
            data:{
                courseId:courseId,
                chapterId:chapterId
            },
            success:function(result){
                if(result.code!='1001'){
                    alert(result.msg);
                    return;
                }
                var items=result.data.rows;
                $("#knowledgePoint").html("");
                for(var i=0;i<items.length;i++){
                    if(items[i]=="${subject.knopoint}"){
                        $("#knowledgePoint").append('<option value="'+items[i].knopointName+'" checked="checked">'+items[i].knopointName+'</option>');
                    }else{
                        $("#knowledgePoint").append('<option value="'+items[i].knopointName+'">'+items[i].knopointName+'</option>');
                    }
                }
            }
        })
    }
    $(function () {
        initKnopoint(1,$("#chapter").val());
        $("#chapter").change(function () {
            var chapterId=$("#chapter").val();
            initKnopoint(1,chapterId);
        });

        changeType(${subject.type});
        questionContent.ready(function () {
            questionContent.setContent('${subject.subjectName}');
        });
        questionSolution.ready(function () {
            questionSolution.setContent('${subject.subjectSolution}');
        });
        <c:choose>
        <c:when test="${subject.type==1}">
        //选择题
        var c_answer = "" + "${subject.answer}";
        <c:forEach var="option" items="${subject.subjectOptions}" varStatus="status">
        $("#questionChoice").append('<tr><td style="vertical-align:middle;">' + choiceLetter[${status.index+1}] + '</td><td>${option}</td><td style="vertical-align:middle;"> <button class="changeAChoice" name=' + choiceLetter[${status.index+1}] + ' data-toggle="modal" data-target="#changeChoice"><span class="glyphicon glyphicon-pencil"></span></button></td><td style="vertical-align:middle;"><input type="checkbox" name=' + choiceLetter[${status.index+1}] + '></td> </tr>');
        if (c_answer == choiceLetter[${status.index+1}]) {
            $("#questionChoice input:eq(${status.index})").prop("checked", "checked");
        }
        </c:forEach>
        </c:when>
        <c:when test="${subject.type==2}">
        //判断题
        if (${subject.answer==1}) {
            $("#judgeQuestionAnswer").html('正确').val(1).removeClass().addClass('answerTrue');
        } else {
            $("#judgeQuestionAnswer").html('错误').val(0).removeClass().addClass('answerFalse');
        }
        </c:when>
        <c:when test="${subject.type==3}">
        //填空题
        <c:forEach var="blank" items="${subject.blankAnswer}" varStatus="status">
        var $newBlankAnswer = $('<tr><td style="vertical-align:middle;">第' + ${status.index+1} +'个答题区间</td><td>${blank}</td><td style="vertical-align:middle;"><button name=' + ${status.index+1} +' data-toggle="modal" data-target="#changeBlankAnswer"><span class="glyphicon glyphicon-pencil"></span></button></td></tr>');
        $("#blankAnswer").append($newBlankAnswer);
        </c:forEach>
        </c:when>
        <c:when test="${subject.type==4}">
        //简答题
        shortAnswer.ready(function () {
            shortAnswer.setContent("${subject.answer}");
        });
        </c:when>
        <c:when test="${subject.type==5}">
        //多选题
        var answer = '${subject.answer}';
        <c:forEach var="option" items="${subject.subjectOptions}" varStatus="status">
        $("#questionChoice").append('<tr><td style="vertical-align:middle;">' + choiceLetter[${status.index+1}] + '</td><td>${option}</td><td style="vertical-align:middle;"> <button class="changeAChoice" name=' + choiceLetter[${status.index+1}] + ' data-toggle="modal" data-target="#changeChoice"><span class="glyphicon glyphicon-pencil"></span></button></td><td style="vertical-align:middle;"><input type="checkbox" name=' + choiceLetter[${status.index+1}] + '></td> </tr>');
        if (answer.indexOf(choiceLetter[${status.index+1}]) != -1) {
            $("#questionChoice input:eq(${status.index})").prop("checked", "checked");
        }
        </c:forEach>
        </c:when>
        </c:choose>

        $("#questionChoice tr").each(function(){
            choiceNum++;
        });

        choiceNum--;

        $("#blankAnswer tr").each(function(){
            blankNum++;
        });

        blankNum--;
    });
    $(".back").click(function () {
        $.ajax({
            type: "get",
            url: "/tiku/page/teacher/teacher-question-manage.html",
            dataType: "html",
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
        });
    })
    /**
     * 题目录入
     * */
    $("#updateQuestion").click(function () {
        var questionType = $("#questionType").val();
        var questionLevel = $("#questionLevel").val();
        var knowledgePoint = $("#knowledgePoint").val();
        var chapter = $("#chapter").val();
        var myQuestionContent = questionContent.getContent();
        var myQuestionSolution = questionSolution.getContent();
        var questionOptions = [];
        var blankAnswers = [];
        var questionAnswer = "";

        if (knowledgePoint == "") {
            knowledgePoint = "通用知识点";
        }
        if (chapter == "") {
            chapter = "0";
        }
        if (myQuestionContent == "") {
            alert('题目内容不得为空');
            return;
        }
        //获得题目选项（选择题）
        if (questionType == 1 || questionType == 5) {
            $('#questionChoice tr:has(td)').each(function (i) {
                questionOptions[i] = $(this).find('td').eq(1).html();
            });

            if (questionOptions.length <= 1) {
                alert('选择题至少有两个选项');
                return;
            }
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
            questionAnswer = blankAnswers;
        } else if (questionType == 2) {
            questionAnswer = $("#judgeQuestionAnswer").val();
        } else if (questionType == 4) {
            questionAnswer = shortAnswer.getContent();
        }

        if (questionAnswer == "") {
            alert('答案不得为空');
            return;
        }

        if (myQuestionSolution == "") {
            if (confirm("为了方便学生学习，强烈推荐您为题目加上答案解析（点击确定返回，点击取消直接录入题目）"))
                return;
        }

        //模拟数据测试
        var a = {
            'courseId': '1',
            'subjectId':${subject.subjectId},
            'chapterId': chapter,
            'knopoint': knowledgePoint,
            'type': questionType,
            'levelId': questionLevel,
            'subjectName': myQuestionContent,
            'subjectOptions': questionOptions,
            'answer': questionAnswer,
            'blankAnswer': blankAnswers,
            'subjectSolution': myQuestionSolution
        };

        $.ajax({
            url: "/subject/add.do",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(a),
            success: function (result) {
                if (result.status != "1001") {
                    alert(result.msg);
                    return;
                } else {
                    alert("修改成功");
                }
                $.ajax({
                    type: "get",
                    url: "/tiku/page/teacher/teacher-question-manage.html",
                    dataType: "html",
                    success: function (result) {
                        $(".main-block").html("");
                        $(".main-block").append(result);
                    }
                });
            }
        });
    });
</script>
