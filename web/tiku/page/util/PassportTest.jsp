<%--
  Created by IntelliJ IDEA.
  User: liuruijie
  Date: 2016/3/25
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <script src="../system/js/jquery.js"></script>
</head>
<body>
<input value="添加学生" type="button" onclick="add()">
<input value="添加教师" type="button" onclick="addTeacher()">
<input value="学生登陆" type="button" onclick="loginStudent()">
<input value="教师登陆" type="button" onclick="login()">
<input value="测试" type="button" onclick="getInfo()">
</body>
<script>
  function add(){
    $.ajax({
      url:"/passport/addStudent.do",
      type:"post",
      dataType:"json",
      data:{
        stuCode:"2014214045",
        stuName:"ywl",
        stuGender:"M",
        stuInstitute:"软件工程学院",
        stuMajor:"软件工程",
        stuClassId:"1301403",
        stuEmail:"657432354@qq.com",
        stuTel:"15340533062",
        stuPassword:"123456",
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function addTeacher(){
    $.ajax({
      url:"/passport/addTeacher.do",
      type:"post",
      dataType:"json",
      data:{
        teaCode:"2014214045",
        teaName:"ywl",
        teaGender:"M",
        teaInstitute:"软件工程学院",
        teaMajor:"软件工程",
        teaEmail:"657432354@qq.com",
        teaTel:"15340533062",
        teaPassword:"123456",
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function loginStudent(){
    $.ajax({
      url:"/passport/login.do",
      type:"post",
      dataType:"json",
      data:{
        username:"2014213880",
        password:"192453",
        type:"student"
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function login(){
    $.ajax({
      url:"/passport/login.do",
      type:"post",
      dataType:"json",
      data:{
        username:"2014213880",
        password:"192453",
        type:"teacher"
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function changePsw(){
    $.ajax({
      url:"/passport/student/changePsw.do",
      type:"post",
      dataType:"json",
      data:{
        oldpsw:"192453",
        newpsw:"96819"
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function getInfo(){
    $.ajax({
      url:"/student/getInfo.do",
      type:"get",
      dataType:"json",
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
  function updateInfo(){
    $.ajax({
      url:"/student/updateInfo.do",
      type:"post",
      dataType:"json",
      data:{
        stuName:"Jerry"
      },
      success:function(data){
        if(data.errorMsg!=null)alert(data.errorMsg);
        else{
          alert("成功！");
        }
      }
    });
  }
</script>
</html>
