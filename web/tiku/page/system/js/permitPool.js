/**
 * 权限池相关JS
 * Created by 奕勇 on 15-1-16.
 */
function initPermitPool(enableIds,treegridId) {
    //初始化权限treegrid
    $("#"+treegridId).treegrid({
        lines: true,
        rownumbers: true,
        idField: 'mid',
        treeField: 'name',
        url: "/sys/privilege/permitPool",
        nowrap: false,//内容自动换行
        fit: true,
        columns: [
            [
                {field: 'name', title: '名称', width: 260,
                    formatter: function (value, rowData, rowIndex) {
                        return "<label><input type='checkbox' cbtype='module' id=" + rowData.mid + " pid='" + rowData.pid + "'>" + rowData.name + "</label>";
                    }
                },
                {field: 'permits', title: '功能点', width: 370,
                    formatter: function (value, rowData, rowIndex) {
                        var html = "";
                        if (value != "") {
                            var permitArr = value.split("|_|");//分割成每一个功能点
                            for (var i = 0; i < permitArr.length; i++) {
                                html += "<label><input type='checkbox' cbtype='permit'";
                                var columnArr = permitArr[i].split("|");
                                var id = columnArr[0];//功能点主键
                                var mid = columnArr[1];//所属模块ID
                                var name = columnArr[2];//功能点名称
                                var permitId = columnArr[3];//权限点id
                                //html += "aid='" + id + "' mid='" + mid + "' permitId='" + permitId + "'>"+permitId+" "+name + "</label>   ";
                                html += "aid='" + id + "' mid='" + mid + "' permitId='" + permitId + "'>" + permitId+" "+name + "</label>   ";
                            }
                        }
                        return html;
                    }
                }
            ]
        ],
        toolbar: [
            {
                text: "保存所选",
                iconCls: 'icon-save',
                handler: function () {
                    saveSelectedPermit();
                }
            },
            {
                text: "恢复原始",
                iconCls: 'icon-edit',
                handler: function () {
                    $("input[cbtype=permit]").prop("checked", false);
                    checkedPoolPermitByIds(enableIds);
                    $("#permitGroupDiv a").linkbutton("unselect");
                    //勾选权限组标签
                    checkedPermitGroupByIds(enableGroupIds, "select");
                }
            },
            {
                text: "帮助",
                iconCls: 'icon-help',
                handler: function () {
                    getUserCheckedIds();
                }
            }
        ],
        onLoadSuccess: function () {
            //绑定事件：当点击左侧模块的勾选框，级联勾选其子节点和右侧功能点
            $("input[cbtype=module]").click(function () {
                checkedPoolModule($(this));
            });
            //初始化勾选已经选中的功能点
            initChecked(enableIds);
            //为权限池中每一个checkbox进行监听点击事件（因为这里的每个点击可能会影响权限标签的按下和弹起）
//            if ($("#permitGroupDiv a").length > 0) {
                $("input[cbtype=permit]").bind("click", function () {
                    cascadeCheckPermitGroup();
                });
                $("input[cbtype=module]").bind("click", function () {
                    cascadeCheckPermitGroup();
                });
//            }
        }
    });
}

//级联更新权限组标签的按下和弹起
function cascadeCheckPermitGroup() {
    var currentUserChecked = getUserCheckedIds();//获得当前用户勾选了的permitId
    var userCheckArr = currentUserChecked.split(",");
    //取消选择所有的权限标签
    var $permitGroups = $("#permitGroupDiv a");
    $permitGroups.linkbutton("unselect");
    $permitGroups.each(function (i, item) {
        var ids = $(this).attr("ids");//获得该权限标签包含的哪些权限点ID
        if (ids != "") {
            var idArr = ids.split(",");
            var select = false;
            for (var i = 0; i < idArr.length; i++) {//对于每一个权限标签的permitID都被用户选中
                var flag = false;//标记权限标签的某个ID是否被用户勾选中
                for (var j = 0; j < userCheckArr.length; j++) {
                    if (idArr[i] == userCheckArr[j]) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {//如果对于权限组中某个ID都没有找到，那么直接就是没找到了
                    break;
                } else if (i == (idArr.length - 1)) {//如果是最后一趟都查找成功，那么就可以勾选这个权限标签了
                    select = true;
                }
            }
            if (select) {
                $(this).linkbutton("select");//勾选这个权限组标签
            }
        }
    });
    //自动判断是否勾选左侧模块一栏的框
    checkedModuleAccordingPermit();
}

//递归勾选模块和功能点，currentModule：当前要处理的module
function checkedPoolModule(currentModule) {
    //先获得当前处理的模块的子结点数组
    var mid = $(currentModule).attr("id");
    var children = $("input[pid=" + mid + "]");

    if (children.length == 0) {//当不存在孩子的情况下，代表它是功能模块，直接右侧勾选功能点即可
        checkedPoolPermit(currentModule);
    } else {//如果当前处理的模块有子节点
        //遍历当前模块的子节点
        for (var i = 0; i < children.length; i++) {
            //获得当前处理的结点对象
            var child = children[i];
            //如果当前模块是勾选，那么该子模块也应该要勾选，否则就是反选
            $(child).prop("checked", $(currentModule).prop("checked"));
            //继续递归处理
            checkedPoolModule(child);
        }
    }
}

//勾选功能点，module：所属模块的对象
function checkedPoolPermit(module) {
    //获得该模块下所有的功能点数组
    var mid = $(module).attr("id");
    var children = $("input[mid=" + mid + "]");
    //如果模块是勾选状态，那么所属于它的功能点也要被勾选
    $(children).prop("checked", $(module).prop("checked"));
}

//勾选相应的权限点，ids:权限点的ID逗号形式
function checkedPoolPermitByIds(ids) {
    if (ids != "") {
        var idArr = ids.split(",");
        for (var i = 0; i < idArr.length; i++) {
            $("input[permitId=" + idArr[i] + "]").prop("checked", true);
        }
    }
}

//获取用户点击的所有权限点，返回逗号形式的字符串
function getUserCheckedIds() {
    var permitCbs = $("input[cbtype='permit']");
    var str = "";
    for (var i = 0, j = 0; i < permitCbs.length; i++) {
        var permit = permitCbs[i];
        if ($(permit).prop("checked")) {
            if (j != 0) {
                str += ",";
            }
            j++;
            str += $(permit).attr("permitId");
        }
    }
    return str;
}

//初始化勾选
function initChecked(enableIds) {
    //先勾选功能点这一栏的框
    checkedPoolPermitByIds(enableIds);
    //自动判断是否勾选左侧模块一栏的框
    checkedModuleAccordingPermit();
}

//自动判断右侧是否点击，然后勾选左侧模块的checkbox
function checkedModuleAccordingPermit() {
    //获取所有的功能点checkbox
    var permits = $("input[cbtype='permit']");
    //然后遍历每一个功能点checkbox
    for (var i = 0; i < permits.length; i++) {
        var permit = permits[i];
        var permitMid = $(permit).attr("mid");
        var module = $("input[id=" + permitMid + "]");//功能点对应的模块对象
        if ($(permit).prop("checked") && !$(module).prop("checked")) {//如果当前功能点被勾选并且它的模块框尚未被勾选
            $(module).prop("checked", true);//将它所属模块的框勾上
        }
    }
}

//取消勾选所有treegrid的checkbox
function uncheckedAllPermit() {
    $("input[cbtype=permit]").prop("checked", false);
    $("input[cbtype=module]").prop("checked", false);
}

//获得当前被勾选的权限组标签的个数
function getCheckedPermitGroupCount() {
    var count = 0;
    $("#permitGroupDiv a").each(function (i, item) {
        if ($(this).linkbutton("options").selected) {
            count++;
        }
    });
    return count;
}

//获得当前被勾选的权限组标签的id
function getCheckedPermitGroupIds() {
    var result = "";
    var index = 0;
    $("#permitGroupDiv a").each(function (i, item) {
        if ($(this).linkbutton("options").selected) {
            if (index != 0) {
                result += ",";
            }
            result += $(this).attr("id");
            index++;
        }
    });
    result = result.replace(/pg/gm, "");
    return result;
}

//获得所有被选择的权限组标签的ids拼接后的结果，permitGroup：刚点击的权限组标签，非必须参数
function getCheckedPermitGroupIncludeIds(permitGroup) {
    var result = "";
    $("#permitGroupDiv a").each(function (i, item) {
        if ($(this).linkbutton("options").selected) {
            var ids = $(this).attr("ids");
            result = appendResult(result, ids);
        }
    });
    if (permitGroup) {//如果有
        result = appendResult(result, $(permitGroup).attr("ids"));
    }
    return result;
}

//拼接结果（由getCheckedPermitGroupIncludeIds方法调用）
function appendResult(result, ids) {
    if (result == "") {
        result += ids;
    } else {
        if (ids != "") {
            result += "," + ids;
        }
    }
    return result;
}