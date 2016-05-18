/**
 * Created by liuyanhao on 3/5/16.
 */
$(".subject-option p").on("click",function () {
    $(this).siblings("[class='on']").removeClass();
    $(this).attr("class","on");
    $(this).find("input").prop("checked","checked");
})