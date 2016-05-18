/**
 * 调用的入口页面会提供以下两个变量
 *  var privilegeJson = ${requestScope.json};//模块的权限点json
    var permitIdListJson = ${requestScope.permitIdListJson};//能操作的permitId json
 * Created by lenovo on 2015/1/26.
 */
function initPrivilege(actions,privilegeJson,permitIdListJson) {
    log("调用了initPrivilege");

    actions.each(function (i, item) {//遍历以priv开头的原件
        item = $(item);
        var btnCode = item.attr("code");
        log("btnCode:"+btnCode);

        for (var i = 0; i < privilegeJson.length; i++) {
            var privilege = privilegeJson[i];
            if (privilege.code == btnCode) {
                if (!hasPrivilege(privilege.permitId,permitIdListJson)) {
                    item.off('click');
                    item.on('click', function (e) {
                        hasNoPrivilege();
                    });
                }
            }
        }
    });
}

//提示没有权限
function hasNoPrivilege() {
    $.messager.alert("系统提示","你没有权限");
}

//判断用户对某个功能点是否有权限，返回true/false
function hasPrivilege(permitId,permitIdListJson) {
    var has = false;
    for (var i = 0; i < permitIdListJson.length; i++) {
        var pid = permitIdListJson[i];
        if (pid == permitId) {
            has = true;
            break;
        }
    }
    return has;
}