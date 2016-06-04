<%--
  Created by IntelliJ IDEA.
  User: liuyanhao
  Date: 6/5/16
  Time: AM10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table table-bordered table-info">
    <thead>
    <tr>
        <th colspan="2">个人分析</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>学生姓名:${analyse.name}</td>
        <td>考试成绩:${analyse.score}分</td>
        <%--<td>学生姓名:${student.name}</td>--%>
        <%--<td>考试成绩:${student.score}</td>--%>
    </tr>
    <tr>
        <td>班级排名:${analyse.myRankInClass}</td>
        <td>年级排名:${analyse.myRankInGrade}</td>
        <%--<td>班级排名:${student.classRank}</td>--%>
        <%--<td>年级排名:${student.gradeRank}</td>--%>
    </tr>
    </tbody>
</table>
<table class="table table-bordered table-info">
    <thead>
    <tr>
        <th colspan="3">班级分析</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>班级的年级排名:${analyse.classRankInGrade}</td>
        <td>班级的平均分:${analyse.classAvgScore}</td>
        <td>班级挂科率:${analyse.classFailPercent*100}%</td>
        <%--<td>班级的年级排名:${stuClass.gradeRank}</td>--%>
        <%--<td>班级的平均分:${stuClass.average}</td>--%>
        <%--<td>班级挂科率:${stuClass.passRate}</td>--%>
    </tr>
    <tr>
        <td colspan="3" style="text-align: center">班级的分数段</td>
    </tr>
    <tr>
        <td>60分以下</td>
        <td>60分到80分</td>
        <td>80分到100分</td>
    </tr>
    <tr>
        <td>${analyse.classFailNum}人</td>
        <td>${analyse.classPassNum}人</td>
        <td>${analyse.classGreatNum}人</td>
    </tr>
    </tbody>
</table>

<%--</tbody>--%>
<%--</table>--%>
<%--<tr>--%>
<%--<td>题目类型:--%>
<%--<c:if test="${subject.type==1}">${"选择题"}</c:if>--%>
<%--<c:if test="${subject.type==2}">${"判断题"}</c:if>--%>
<%--<c:if test="${subject.type==3}">${"填空题"}</c:if>--%>
<%--<c:if test="${subject.type==4}">${"简答题"}</c:if>--%>
<%--<c:if test="${subject.type==5}">${"多项选择题"}</c:if>--%>
<%--</td>--%>
<%--<td>难度:--%>
<%--<c:if test="${subject.levelId==1}">${"简单"}</c:if>--%>
<%--<c:if test="${subject.levelId==2}">${"中等"}</c:if>--%>
<%--<c:if test="${subject.levelId==3}">${"困难"}</c:if>--%>
<%--难度--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>所属章节:第${subject.chapterId}章</td>--%>
<%--<td>知识点:${subject.knopoint}</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>出题人:${subject.passportName}</td>--%>
<%--<td>出题时间:${subject.createTimeString}</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td colspan="2">题目内容：--%>
<%--<br>--%>
<%--${subject.subjectName}--%>
<%--<c:if test="${subject.type==1 or subject.type==5}">--%>
<%--<br>题目选项:<br>--%>
<%--<c:forEach var="option" varStatus="status" items="${subject.subjectOptions}">--%>
<%--${option}<br>--%>
<%--</c:forEach>--%>
<%--</c:if>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td colspan="2">答案：--%>
<%--<br>--%>
<%--<c:choose>--%>
<%--<c:when test="${subject.type==3}">--%>
<%--<c:forEach var="b_Answer" items="${subject.blankAnswer}" varStatus="status">--%>
<%--${status.index+1}.${b_Answer}<br>--%>
<%--</c:forEach>--%>
<%--</c:when>--%>
<%--<c:when test="${subject.type==2}">--%>
<%--${subject.answer==0?"false":"true"}--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--${subject.answer}--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td colspan="2">--%>
<%--题目解析:<br>--%>
<%--${subject.subjectSolution}--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>

<%--</tr>--%>