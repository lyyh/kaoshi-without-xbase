<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>重置密码</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="/system/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="/system/easyui/themes/icon.css">
    <link rel="stylesheet" href="/system/css/news.css" />
    <script type="text/javascript" src="/system/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/system/easyui/jquery.easyui.min.js"></script>
    <script language="javascript" type="text/javascript" src="/system/js/common.js"></script>
    <script language="javascript" type="text/javascript" src="/system/js/validate_easyui.js"></script>
    <%--<script type="text/javascript">--%>
    <%--$(document).ready(function(e){--%>
    <%--var passport_id=$('#passport_id').val();--%>
    <%--var active=$('#active').val();--%>

    <%--if(active===''||passport_id===''){--%>
    <%--window.location.href="/";--%>
    <%--}--%>
    <%--});--%>
    <%--</script>--%>
    <c:if test="${empty passport_id}">
        <script type="text/javascript">
            window.location.href="/";
        </script>
    </c:if>
</head>
<body id="bg-wrap">
<div id="header">
    <div class="wrap">
        <div class="logo"></div>
    </div>
</div>
<div id="c-w" class="float-wrapper">
    <div class="title">
        <span class="i-home"></span> <a href="/">首页</a> <span class="i-arrow"></span> <a href="pwdStep1.shtml">填写邮箱</a> <span class="i-arrow"></span> <a href="pwdStep2.shtml">接收验证邮件</a> <span class="i-arrow"></span> <a href="#">找回密码</a> <a href="/" class="r-l">返回首页</a>
    </div>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center'" >
            <form id="user_password_form" style="margin: 3px">
                <input type="hidden" id="passport_id" name="passport_id" value="${passport_id}">
                <input type="hidden" id="active" name="active" value="${active}">
                <table class="table">
                    <tbody>
                    <tr>
                        <td colspan="2" align="center"><img src="/system/img/step3.png"></td>
                    </tr>
                    <tr>
                        <td class="label" align="right" width="420px">新的密码：</td>
                        <td class="value"><input id="inputNewPassword" name="passport_pwd" type="password" class="easyui-validatebox easyui-textbox"
                                                 data-options="required:true,missingMessage:'该输入项为必输项', validType:'length[6,20]'"></td>
                    </tr>
                    <tr>
                        <td class="label" align="right">确认密码：</td>
                        <td class="value"><input name="rpassport_pwd" type="password" class="easyui-validatebox easyui-textbox"
                                                 data-options="required:true,missingMessage:'该输入项为必输项'" validType="same['inputNewPassword']"></td>
                    </tr>
                    </tbody>
                </table>

                <div style="padding:10px 0px 0px 415px;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="updateUserPassword()">保存</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-close',plain:true" onclick="closeCurrWin()">返回</a>
                </div>
            </form>

        </div>
    </div>
</div>
<div class="line" style="margin-top: 20px;"></div>
<!--#include virtual="/system/page/util/copyright.html"-->
</body>
<script>
    var runing=false;
    function updateUserPassword() {
        if (runing){return;}
        runing=true;
        var form = $('#user_password_form');
        if ($(form).form("validate")) {
            loadData("/pwd/updatePwd.do", function (ret) {
                if(ret==="1"){
                    $.messager.alert("提示", "亲爱的用户，密码修改成功！");
                }else if(ret==="-2"){
                    $.messager.alert("提示", "激活码的时间为2个小时，现在已失效！");
                }else{
                    $.messager.alert("提示", "亲爱的用户，密码修改失败！");
                }
            }, {data: form.serializeArray()});
        } else {
            $.messager.alert("提示", "请完善密码后在保存!");
        }
        runing=false;
    }
</script>
</html>