/**
 * Created by hukaihe on 2016/4/30.
 * exam for student
 */
//模拟的数据
var queInfo = [
    {
        "chapterId": 1,
        "knopointId": 1,
        "score": 3,
        "subjectId": 21123,
        "subjectName": "public class A {<br>static boolean foo(char c){<br>System.out.print(c);<br>return true;<br>}<br>public static void main(String[] args){<br>int i = 0;<br>for(foo('A');<br> foo('B') && i < 2; foo('C')){i ++;foo('D');<br>}<br>}<br>}<br>上面代码运行的结果是(  )",
        "subjectOptions": ["ABCDABCD", "ABDCBDCB", "Compilation fails.", "An exception is thrown at runtime"],
        "type": 1
    },
    {
        "chapterId": 1,
        "knopointId": 1,
        "score": 3,
        "subjectId": 623312,
        "subjectName": " 下面那个是JSP中的内置对象?",
        "subjectOptions": ["require", "Math", "Date", "RegularExpression"],
        "type": 1
    },
    {
        "blankamount": 2,
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 832223,
        "subjectName": "GC是什么______ ,为什么要有GC______",
        "type": 2
    },
    {
        "blankamount": 2,
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 9223344,
        "subjectName": "接口是否可继承接口____,抽象类是否可实现接口____,抽象类是否可继承实体类____",
        "type": 2
    },
    {
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 91182234,
        "subjectName": "接口是否可继承接口?",
        "type": 3
    },
    {
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 9332112,
        "subjectName": "String是最基本的数据类型吗?",
        "type": 3
    },
    {
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 9334444,
        "subjectName": "请简要介绍JAVA中的Collection FrameWork",
        "type": 4
    },
    {
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 9112234,
        "subjectName": "两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，这句话对不对?",
        "type": 3
    },
    {
        "chapterId": 1,
        "knopointId": 2,
        "score": 3,
        "subjectId": 9223112,
        "subjectName": "写出垃圾回收的优点和原理。并考虑2种回收机制",
        "type": 4
    },

    {
        "chapterId": 1,
        "knopointId": 1,
        "score": 3,
        "subjectId": 223231,
        "subjectName": "下面不是sleep()和wait()的区别的是",
        "subjectOptions": ["sleep(): 是使线程停止一段时间的方法", "是线程交互时，如果线程对一个同步对象x 发出一个wait()调用，该线程会暂停执行，被调对象进入等待状态，直到被唤醒或等待时间到。", "sleep()可能会造成线程死锁", "sleep()的优先级高于wait()"],
        "type": 5
    },
    {
        "chapterId": 1,
        "knopointId": 1,
        "score": 3,
        "subjectId": 21123123,
        "subjectName": "下面属于int和Integer的区别的是",
        "subjectOptions": ["int是java的原始数据类型，Integer是java为int提供的封装类。", "Java为每个原始类型提供了封装类。引用类型和原始类型的行为完全不同，并且它们具有不同的语义", "对象引用实例变量的缺省值为 null，而原始类型实例变量的缺省值与它们的类型有关。", "引用类型和原始类型用作某个类的实例数据时所指定的缺省值。"],
        "type": 5
    }
];
var paperInfo = {
    "course": "java",
    "subAmount": [{"amount": 2, "type": 1}, {"amount": 2, "type": 2}, {"amount": 3, "type": 3}, {
        "amount": 2,
        "type": 4
    }, {"amount": 2, "type": 5}],
    "term": "大二",
    "testpaperId": 1,
    "totalScore": 100,
    "totalSubAmount": 6,
    "totalTime": 120
};
testpaperId=null;
testscheduleId=null;
$(function(){
    $.ajax({
        url:"/exam/isInExam.do",
        type:"post",
        dataType:"json",
        success:function(result){
            if(result.code="1001"){
                if(result.data=="true"){
                    alert("您的考试还在进行中，请继续考试");
                    $('.exam-ready').hide(100);
                    $('.exam-load').show(200);
                    $(".start-exam").click();
                }else{
                    $.ajax({
                        url:"/ExamInfo/listMyExam.do",
                        type:"post",
                        dataType:"json",
                        success:function(result){
                            if(result.code!="1001"){
                                alert(result.msg);
                                return;
                            }
                            $("#neirong").html("");
                            var items=result.data;
                            for(var i=0;i<items.length;i++){
                                $("#neirong").append('<tr><td><input type="checkbox" name="testscheduleId" value="'+items[i].testscheduleId+'"><input type="hidden" name="testpaperId" value="'+items[i].testpaperId+'"></td><td>'+items[i].courseName+'</td><td>'+items[i].testStarttime+'</td><td>'+items[i].testEndtime+'</td><td>'+items[i].mkpaperTerm+'</td></tr>');
                            }
                            console.log(result);
                            $('.btn-show').click(function(){
                                var ainput=$("#neirong input[name=testscheduleId]:checked");
                                testscheduleId=ainput.val();
                                testpaperId=ainput.next().val();

                                var checks = $(".table tr").find("input[name=testscheduleId]"),
                                    n = 0;
                                //console.log(checks);
                                for (var i = 0; i < checks.length; i++) {
                                    if (checks[i].checked)
                                        n++;
                                }
                                if (n != 1) {
                                    var a = $("#tip").find(".modal-body");
                                    a.html("<p>必须选择一个</p>");
                                    $('#tip').modal({backdrop: 'static', keyboard: false});
                                }
//        else if($(".table tr input:checked").val() != "2"){
//            var a = $("#tip").find(".modal-body");
//            a.html("<p>未到考试时间</p>");
//            $('#tip').modal({backdrop: 'static', keyboard: false});
//        }
                                else{
                                    $('.exam-ready').hide(100);
                                    $('.exam-load').show(200);
                                }

                            })

                            //表格选择
                            $("#neirong tr").click(function (e) {
                                var input = $(this).find("input[name=checkname]");//获取checkbox
                                if ($(e.target).attr("type") != "checkbox") {
                                    //判断当前checkbox是否为选中状态
                                    if (input.is(":checked")) {
                                        input.prop("checked", false);
                                    } else {
                                        input.prop("checked", true);
                                    }
                                }
                            })
                        }
                    });
                }
            }
        }
    })

});

$(".start-exam").click(function () {
    initial_exam(function () {
        $(".left-block").hide(150);
        $(".left-block-container").hide(150);
        $(".left-tools").show(150);
        $(".left-tools-container").show(150, function () {
            $(".myModal").fadeIn(1000);
            //控制伸缩栏的状态
            state = 1;
            $.ajax({
                url:"/api/passport/selfPassportInfo.do",
                type:"get",
                dataType:"json",
                success:function(result){
                    if(result.code=="1001"){
                        $(".student-name .student-name").append(result.data.userName);
                        $(".student-num .student-num").append(result.data.userId);
                    }
                }
            });
        });
    });
});

$(".hand-in").click(function () {
    if (confirm("您确定要提交试卷吗？")) {
        submitPaper();
    }
});

//展示全部题型
$(".quickType li[value=0]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").show();
});
//展示所有的单项选择题
$(".quickType li[value=1]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").hide();
    $(".single-choice-que").show();
});
//展示所有的填空题
$(".quickType li[value=3]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").hide();
    $(".blank-que").show();
});
//展示所有的判断题
$(".quickType li[value=2]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").hide();
    $(".judge-que").show();
});
//展示所有的简答题
$(".quickType li[value=4]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").hide();
    $(".short-answer-que").show();
});
//展示所有的多项选择题
$(".quickType li[value=5]").click(function () {
    $('.quickType li').css("background-color","#3C3F41");
    $(this).css("background-color","#4B6EAF");
    $(".paper-block>ul").hide();
    $(".choices-que").show();
});


function initial_exam(fn) {
    //alert(testscheduleId+","+testpaperId);
    $.ajax({
        url:"/exam/initExam.do",
        type:"post",
        dataType:"json",
        data:{
            scheduleId:testscheduleId,
            testpaperId:testpaperId
        },
        success:function(result){
            if(result.code=="1001"){
                fn();
                getTestInfo();
            }else{
                alert(result.msg);
                //window.location.reload();
            }
        }
    })
}

function getTestInfo() {
    $.ajax({
        url:"/exam/examInfo.do",
        type:"post",
        dataType:"json",
        success: function (result) {
            if(result.code=="1001"){
                paperInfo=result.data;
                var course = paperInfo.course;
                var duration = paperInfo.totalTime;
                var totalScore = paperInfo.totalScore;
                var questionAmount = 0;

                $(".course").html(course);
                $(".duration").html(duration + "分钟");
                $(".total-score").html(totalScore + "分");
                $(paperInfo.subAmount).each(function () {
                    $(".quickType li[value=" + this.type + "] span").html(this.amount);
                    questionAmount += this.amount;
                });
                $(".quickType li[value=0] span").html(questionAmount);
                getQuestions();
            }else{
                alert(result.msg)
            }
        }
    });
}

function getQuestions() {
    var item = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];

    $.ajax({
        url:"/exam/questionInfo.do",
        type:"post",
        dataType:"json",
        success: function (result) {
            if(result.code=="1001"){
                queInfo=result.data;
                $(queInfo).each(function () {
                    switch (this.type) {
                        case 1:
                        {
                            var options = "<ul class='options'>";
                            for (var i = 0; i < this.subjectOptions.length; i++) {
                                options += "<li><input type='radio' name=" + this.subjectId + " value=" + (i + 1) + ">" + item[i] + ". " + this.subjectOptions[i] + "</li>"
                            }
                            options += "</ul>";
                            $(".single-choice-que").append("<li value=" + this.subjectId + " name=" + this.type + "><label></label>." + this.subjectName + options + "</li>");
                        }
                            break;
                        case 3:
                        {
                            var blanks = "<ul class='blanks'>";
                            for (var i = 0; i < this.blankamount; i++) {
                                blanks += "<li><input type='text' class='form-control' name=" + this.subjectId + " placeholder='第" + (i + 1) + "答题区间'></li>"
                            }
                            blanks += "</ul>";
                            $(".blank-que").append("<li value=" + this.subjectId + " name=" + this.type + "><label></label>." + this.subjectName + blanks + "</li>");
                        }
                            break;
                        case 2:
                        {
                            var options = "<ul class='options'><li><input type='radio' value='1' name=" + this.subjectId + ">正确</li><li><input type='radio' value='0' name=" + this.subjectId + ">错误</li></ul>";
                            $(".judge-que").append("<li value=" + this.subjectId + " name=" + this.type + "><label></label>." + this.subjectName + options + "</li>");
                        }
                            break;
                        case 4:
                        {
                            $(".short-answer-que").append("<li value=" + this.subjectId + " name=" + this.type + "><label></label>." + this.subjectName + "<br><textarea class='form-control'></textarea></li>");
                        }
                            break;
                        case 5:
                        {
                            var options = "<ul class='options'>";
                            for (var i = 0; i < this.subjectOptions.length; i++) {
                                options += "<li><input type='checkbox' name=" + this.subjectId + " value=" + (i + 1) + ">" + item[i] + ". " + this.subjectOptions[i] + "</li>"
                            }
                            options += "</ul>";
                            $(".choices-que").append("<li value=" + this.subjectId + " name=" + this.type + "><label></label>." + this.subjectName + options + "</li>");
                        }
                            break;
                    }
                });
                //生成题目编号
                $(".paper-block>ul>li").each(function (i) {
                    $(this).find('label').html(i + 1);
                });
                getOddTime();
                //如果用户不小心中途退出系统（比如退出浏览器、断电），系统帮助他自动填充之前保存的答案
                setAnswers();
            }else{
                alert(result.msg);
            }
        }
    });

}

//获取学生选择题（多选题、单选题）和判断题的答案并存入本地的浏览器
$(".paper-block").on("click", ".options li", function () {
    if (!$(this).children().eq(0)[0].checked)
        $(this).children().eq(0)[0].checked = true;
    else {
        $(this).children().eq(0)[0].checked = false;
    }

    var que_id = $(this).parent().parent().val();
    var que_answer = "";
    var que_type = $(this).parent().parent().attr("name");

    $(this).parent().find('input:checked').each(function () {
        que_answer += this.value;
    });

    var answer_info = [que_type, que_answer];

    localStorage.setItem(que_id, JSON.stringify(answer_info));
});
//获取学生填空题的答案并存入浏览器
$(".blank-que").on("change", ".blanks :text", function () {
    var que_id = $(this).parent().parent().parent().val();
    var que_answer = [];
    var que_type = $(this).parent().parent().parent().attr("name");

    $(this).parent().parent().find(":text").each(function (i) {
        que_answer[i] = this.value;
    });

    var answer_info = [que_type, JSON.stringify(que_answer)];

    localStorage.setItem(que_id, JSON.stringify(answer_info));
});
//获取学生简答题的答案并存入浏览器
$(".short-answer-que").on("change", "textarea", function () {
    var que_id = $(this).parent().val();
    var que_answer = this.value;
    var que_type = $(this).parent().attr("name");
    var answer_info = [que_type, que_answer];

    localStorage.setItem(que_id, JSON.stringify(answer_info));
});

//将学生存储的答案填入试题中
function setAnswers() {
    for (var i = 0; i < localStorage.length; i++) {
        var que_id = localStorage.key(i);
        var myInfo = JSON.parse(localStorage.getItem(que_id));
        var que_type = myInfo[0];
        switch (que_type) {
            case "1":
            {
                $(".single-choice-que li[value="+que_id+"] :radio[value="+myInfo[1]+"]").attr("checked",true)
            }
                break;
            case "3":
            {
                var blank_answer=JSON.parse(myInfo[1]);
                for(var t=0;t<blank_answer.length;t++){
                    $(".blank-que li[value="+que_id+"] :text:eq("+t+")").val(blank_answer[t]);
                }
            }
                break;
            case "2":
            {
                $(".judge-que li[value="+que_id+"] :radio[value="+myInfo[1]+"]").attr("checked",true)
            }
                break;
            case "4":
            {
                $(".short-answer-que li[value="+que_id+"] textarea").val(myInfo[1]);
            }
                break;
            case "5":
            {
                var option_answer=myInfo[1].split("");
                for(var t=0;t<option_answer.length;t++){
                    $(".choices-que li[value="+que_id+"] :checkbox[value="+option_answer[t]+"]").attr("checked",true)
                }
            }
                break;
        }
    }
}
//计时器
function timedCount(data) {
    data = data - 1000;
    if (data <= 0) {
        data = 0;
        $(".test-user-info .test-time").html(MillisecondToDate(data));
        //clearInterval(ttt);
        alert("考试时间到，系统将会自动为您提交试卷");
        submitPaper();
        return;
    }
    $(".odd-time").html(MillisecondToDate(data));
    ttt = setTimeout("timedCount(" + (data) + ")", 1000);
}

function MillisecondToDate(msd) {//毫秒转分钟秒钟格式
    var d = new Date(msd);
    var h = d.getUTCHours();
    var m = d.getUTCMinutes();
    var s = d.getUTCSeconds();
    var time = ((h - 10) < 0 ? "0" + h : h) + ":" + ((m - 10) < 0 ? "0" + m : m) + ":" + ((s - 10) < 0 ? "0" + s : s);
    return time;
}

//获取考试剩余时间,并开始计时
function getOddTime() {
    $.ajax({
        url: "/exam/getOddTime.do",
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.code == "1001") {
                timedCount(result.data);
            } else {
                alert(result.msg);
            }
        }
    });
}

//提交试卷
function submitPaper(){
    var item = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
    var subjectInfo=new Array();
    for (var i = 0; i < localStorage.length; i++) {
        var que_id = localStorage.key(i);
        var myInfo = JSON.parse(localStorage.getItem(que_id));
        var que_type = myInfo[0];
        var subject=new Object;
        subject["number"]=i;
        switch (que_type) {
            case "1":
            {
                subject["type"]=1;
                subject["stuAnswer"]=item[myInfo[1]-1];
            }
                break;
            case "3":
            {
                subject["type"]=3;
                var blank_answer=JSON.parse(myInfo[1]);
                subject["stuBlankAnswer"]=blank_answer;
            }
                break;
            case "2":
            {
                subject["type"]=2;
                subject["stuAnswer"]=myInfo[1];
            }
                break;
            case "4":
            {
                subject["type"]=4;
                subject["stuAnswer"]=myInfo[1];
            }
                break;
            case "5":
            {
                subject["type"]=5;
                var answer="";
                var option_answer=myInfo[1].split("");
                for(var t=0;t<option_answer.length;t++){
                    answer+=item[option_answer[t]-1];
                }
                subject["stuAnswer"]=answer;
            }
                break;
        }
        subjectInfo.push(subject);
    }
    $.ajax({
        url:"/exam/submitPaper.do",
        type:"post",
        dataType:"json",
        contentType:"application/json",
        data:JSON.stringify(subjectInfo),
        success: function (result){
            alert(result.msg);
            $(".myModal").fadeOut(1000);
            localStorage.clear();
            $(".paper-block>ul").empty();
        }
    });
}

