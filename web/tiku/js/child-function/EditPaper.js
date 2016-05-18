$.ajax({
        url: "/subject/myPaperURL.do",
        type: "post",
        dataType: "json",
        data: {
            subjectId: ids
        },
        beforeSend: function () {
            $("#paperContainer tr[name='choiceQuestion']").html("数据加载中,请耐心等候...")
        },
        success: function (data) {

            data.each(function () {
                var questionNum = this.subject_Id;
                var queType = this.subject_type;
                var queContent = this.subject_name;
                var options = this.subject_option;
                var addLineOrNot = 0;
                var removeLineOrNot = 0;
                $("#questionContainer").append('<tr><td><input type="checkbox" name="' + queType + '" class="form-control" value="' + questionNum + '"> <div name="questionContent">' + queContent + '</div><div name="questionOptions">' + options + '</div></td></tr>');

                $("#paperContainer :checkbox").each(function () {
                    if (this.name == queType) {
                        addLineOrNot = 0;
                        return false;
                    } else {
                        addLineOrNot = 1;
                    }
                });

                if (addLineOrNot == 1) {
                    $('<td></td>').appendTo("#paperContainer table tr[name]:eq(" + queType + ")");
                    addLineOrNot = 0;
                }

                $(this).parent().parent().clone(true).insertBefore("#paperContainer table tr[name]:eq(" + queType + ")");

                var questionScore = $(".setDefaultScoreModal input[name=" + this.name + "]").val();
                var scoreForEach = $('<div class="scoreDe"> <input name="' + questionNum + '" class="form-control" type="number" min="1" value="' + questionScore + '"> <div class="scoreDeAdd">分</div></div>')
                scoreForEach.insertAfter($("#paperContainer table :checkbox[value='" + questionNum + "']+div"));
            });
        },
        error: function () {
            $("#paperContainer tr[name='choiceQuestion']").html("网络错误...")
        }
    }
);
