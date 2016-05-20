<%--
  Created by IntelliJ IDEA.
  User: liuruijie
  Date: 2016/4/11
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table table-bordered table-info">
  <tr><td class="td-title" colspan="4">题目通用</tr>
  <tr>
    <td class="my-th">科目</td><td>&nbsp;&nbsp;${subject.course}</td>
    <td class="my-th">题目类型</td><td>&nbsp;&nbsp;
    <c:if test="${subject.type==1}">${"选择题"}</c:if>
    <c:if test="${subject.type==2}">${"判断题"}</c:if>
    <c:if test="${subject.type==3}">${"填空题"}</c:if>
    <c:if test="${subject.type==4}">${"简答题"}</c:if>
    <c:if test="${subject.type==5}">${"多项选择题"}</c:if>
  </td>
  </tr>
  <tr>
    <td class="my-th">难度</td><td>&nbsp;&nbsp;
    <c:if test="${subject.levelId==1}">${"简单"}</c:if>
    <c:if test="${subject.levelId==2}">${"中等"}</c:if>
    <c:if test="${subject.levelId==3}">${"困难"}</c:if>
  </td>
    <td class="my-th">章节</td><td>&nbsp;&nbsp;${subject.chapterId}</td>
  </tr>
  <tr>
    <td class="my-th">知识点</td>
    <td>&nbsp;&nbsp;${subject.knopoint}</td>
    <td class="my-th">出题人</td>
    <td>&nbsp;&nbsp;${subject.passportName}</td>
  </tr>
  <tr><td class="td-title" colspan="4">题目内容</td></tr>
  <tr>
    <td colspan="4">
      ${subject.subjectName}<br>
      <c:if test="${subject.type==1 or subject.type==5}">
        <%--题目选项:<br>--%>
        <c:forEach var="option" varStatus="status" items="${subject.subjectOptions}">
          ${option}<br>
        </c:forEach>
      </c:if></td>
  </tr>
  <tr><td class="td-title" colspan="4">参考答案</tr>
  <tr>
    <td colspan="4">
      <c:choose>
        <c:when test="${subject.type==3}">
          <c:forEach var="b_Answer" items="${subject.blankAnswer}" varStatus="status">
            第${status.index+1}空答案：${b_Answer}<br>
          </c:forEach>
        </c:when>
        <c:when test="${subject.type==2}">
          ${subject.answer==0?"false":"true"}
        </c:when>
        <c:otherwise>
          ${subject.answer}
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
  <tr><td class="td-title" colspan="4">答案解析</tr>
  <tr>
    <td colspan="4">
      ${subject.subjectSolution}
    </td>
  </tr>
</table>