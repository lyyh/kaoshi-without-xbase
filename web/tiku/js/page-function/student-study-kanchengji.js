/**
 * Created by liuruijie on 2016/6/2.
 */

$(function(){

    $.ajax({
        url:"/afterExam/myExam.do",
        type:"post",
        dataType:"json",
        success:function(result){
            $("#neirong").html("");
            if(result.code!="1001"){
                alert(result.msg);
                return
            }
            var items = result.data;

            for(var i=0;i<items.length;i++){
                $("#neirong").append('<tr><td>'+(i+1)+'</td><td>'+items[i].scheduleId+'</td><td>'+items[i].courseName+'</td><td>'+items[i].term+'</td><td>'+items[i].maxScore+'</td><td>'+items[i].score+'</td></tr>');
            }
        }
    });

});