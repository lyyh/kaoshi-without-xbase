var runing=false;
var buttons = {
    "2": "<a name=\"button_edit\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-edit',plain:true\" onclick=\"showEdit(this)\">编辑</a>\n",
    "3": "<a name=\"button_delete\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-cancel',plain:true\" onclick=\"deleteRow(true)\">删除</a>\n",
    "4": ""
};
var returnStatus = {
    fail: {id: '000010', message: '操作失败！'},
    success: {id: '000009', message: '操作成功！'},
    noLoin: {id: '000011', message: '用户未登陆，请登陆后再试！'},
    noPrivilege: {id: '000008', message: '权限不够，不能进行操作！'},
    dataError: {id: '001007', message: '数据有误！'},
    boshiSuccess:{id: '001012', message: '操作成功！<br>温馨提示：1、请及时查看申报资格审核是否通过<br>2、请下载申请书保存'},
    dutyLow:{id: '100017', message:'培育项目申请人职称必须在讲师以上'},
    recommendLess:{id: '100018', message:'请完成所有项目的评审后再提交评审结果！'}
};

/**
 *
 * @param ret:返回的所有数据
 * @param callback:如果返回的数据是成功状态，即status=000009，则回掉这个函数
 * @param isMessage:是否显示成功提示信息
 * @returns {boolean}:如果是成功的数据，则返回true，否则返回false
 */
function retMessage(ret, callback, isMessage) {
    if(typeof(ret)=="string") {
        ret = eval("("+ret+")");
    }
    if (ret.status == returnStatus.success.id) {
        if (isMessage) {
            $.messager.alert("提示", returnStatus.success.message);
        }
        callback && callback();
        return true;
    } else if (ret.status == returnStatus.fail.id) {
        $.messager.alert("提示", returnStatus.fail.message);
    } else if (ret.status == returnStatus.noLoin.id) {
        $.messager.alert("提示", returnStatus.noLoin.message);
        window.top.location.href = '/';
    } else if (ret.status == returnStatus.noPrivilege.id) {
        $.messager.alert("提示", returnStatus.noPrivilege.message);
    } else if (ret.status == returnStatus.dataError.id) {
        $.messager.alert("提示", returnStatus.dataError.message);
    } else if (ret.status == returnStatus.boshiSuccess.id) {
        if (isMessage) {
            $.messager.alert("提示", returnStatus.boshiSuccess.message);
        }
        callback && callback();
        return true;
    } else if(ret.status == returnStatus.dutyLow.id){
        $.messager.alert("提示", returnStatus.dutyLow.message);
    } else if (ret.status == returnStatus.recommendLess.id){
        $.messager.alert("提示", returnStatus.recommendLess.message);
    } else {
        $.messager.alert("提示", ret.message);
    }
    return false;
}
/**
 * 查询入口
 * @param obj
 */
function searchShow(obj) {
    getCurrSearch().show(obj);
}

/**
 * 全局，初始化表格属性att
 * @param query:json格式参数
 * @param noLoad:是否现在就加载数据
 * @param win:所在的窗口对象，如果没有则默认为当前窗口
 * @param gridName:显示数据的控件名称，默认名称为 winDataList
 * @param gridButtons:显示数据的控件按钮名称，默认名称为 winDataListButtons
 */
function listBind(query,noLoad, win, gridName, gridButtons) {
    if (!win)win = getCurrWin();
    if (!gridName)gridName = "winDataList";
    if (!gridButtons)gridButtons = "winDataListButtons";
    var dg = win.find("[name=" + gridName + "]");
    dg.data("att", query);

    if(query.search) {//绑定查询
        var _search = new searcher(query.search);
        _search.getAPI().on('search', refresh);
        dg.data("search", _search);
    }
    if (dg.hasClass("treegrid")) {
        dg.treegrid({toolbar: win.find("[name=" + gridButtons + "]")});
        setTimeout(function(){dg.treegrid("options").url = query.listUrl;if(!noLoad)dg.treegrid("load");},0);

    } else if (dg.hasClass("datagrid")) {
        dg.datagrid({toolbar: win.find("[name=" + gridButtons + "]")});
        setTimeout(function(){dg.datagrid("options").url = query.listUrl},0);
        if(!noLoad)dg.datagrid("load");
    }
    if(query.afterListBind){query.afterListBind(dg);}
}

/**
 * 在查看窗口中点击编辑按钮
 * @param obj:当前按钮对象
 */
function showEdit(obj) {
    if (runing){return;}
    runing=true;
    setVal(getObj(obj, "option_type", null), "edit");
    var pp = $(obj).parent();
    $(obj).parent().empty();
    pp.append($("<a name=\"button_save\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save',plain:true\" onclick=\"save()\">保存</a>")).append("<a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-close',plain:true\" onclick=\"closeCurrWin()\">关闭</a>");
    $.parser.parse(pp);
    $("form td p").remove();
    $("form").removeClass("hide");

    if(getParentQuery().afterEdit){
        getParentQuery().afterEdit();
    }
    runing=false;
}
/**
 * 添加按钮事件
 */
function addRow() {
    if (runing){return;}
    runing=true;
    var query = getCurrQuery();
    query.param['option_type'] =  "add";
    var temp=getCurrWin().find('[name=winEditHtml]').val();
    if(query.beforAdd){
        temp=query.beforAdd(temp);
        if(temp==null)return;
    }
    openWin(query.windowWidth, query.windowHeight, "新增", temp.replace("<#buttons/>", "<a name=\"button_save\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save',plain:true\" onclick=\"save()\">保存</a>\n"), null, null, null);
    if (getParentQuery().afterAdd) {getParentQuery().afterAdd();}
    runing=false;
}

/**
 * 查看和编辑行公有
 * @param type 操作类型,查看为show，编辑为edit
 * @param title 窗口标题名称
 * @param gridName 数据表格名称
 */
function showRow(type, title, gridName) {
    if (runing){return;}
    runing=true;
    if (!type) {type = "show";}
    if (!title) {title = "查看";}
    if (!gridName)gridName = "winDataList";

    var table = getCurrWin().find("[name=" + gridName + "]");
    var row;
    if (table.hasClass("treegrid")) {
        row = table.treegrid("getSelections");
    } else if (table.hasClass("datagrid")) {
        row = table.datagrid("getSelections");
    }
    if (!row || row.length == 0) {
        $.messager.alert("提示", "请先选择一行再" + title + "！");
        runing=false;
        return;
    }
    if (row.length > 1){
        $.messager.alert("提示", "只能选择一行进行操作！");
        return;
    }
    var query = getCurrQuery();
    query.param['option_type'] =  type;
    var param="";
    $.each(query.param,function(index,elem){
        param=param+"&"+index+"="+elem;
    });
    loadData(query.show + "?id=" + row[0][query.keyField] + param, function (result) {
        retMessage(result,
            function () {
                var temp= getCurrWin().find("[name=winEditHtml]").val().replace("<#buttons/>", ((type == "show") ? getButtons(result["privilege"]) : "<a name=\"button_save\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save',plain:true\" onclick=\"save()\">保存</a>\n"));
                if(query.beforShow){temp=query.beforShow(type,temp,result);if(temp==null)return;}
                openWin(query.windowWidth, query.windowHeight, title, temp, null, null, function (obj) {
                    onLoad(obj, type, result, function(){
                        if(type === "edit"){
                            if(query.afterEdit)query.afterEdit(result);
                        } else if(type === "show") {
                            if(query.afterShow)query.afterShow(result);
                        }
                    });
                });
            });
    });
    runing=false;
}

/**
 * 删除数据
 * @param isShow_del:布尔型，关闭前端的窗口。用于在查看窗口中删除当条数据
 * @param gridName:数据表格名称
 */
function deleteRow(isShow_del, gridName) {
    if (runing){return;}
    runing=true;
    if (!gridName)gridName = "winDataList";
    var table=null;
    if (!isShow_del) {
        table = getCurrWin();
    } else {
        table = getParentWin();
    }
    table = table.find("[name=" + gridName + "]");
    var row;
    if (table.hasClass("treegrid")) {
        row = table.treegrid("getSelections");
    } else if (table.hasClass("datagrid")) {
        row = table.datagrid("getSelections");
    }
    if (!row || row.length == 0) {
        runing=false;
        $.messager.alert("提示", "请先选择一行再删除！");
        return;
    }

    var query = getParentQuery();
    var obj={};
    if(query.param){
        $.extend(obj,{id: row[0][table.data("att").keyField]},query.param);
    }else{
        $.extend(obj,{id: row[0][table.data("att").keyField]});
    }

    $.messager.confirm("删除", "确定要删除此信息吗？", function (flag) {
        if (flag) {
            if (isShow_del == true) {
                closeCurrWin();
            }
            var query = getCurrQuery();
            $.ajax({
                type: "get",
                url: table.data("att").del,
                //url: query.delete,
                data: obj,
                success: function (ret) {
                    retMessage(ret, function () {
                        if(query.afterDel)query.afterDel();
                        refresh();
                    }, true)
                }
            });
        }
    });
    runing=false;
}

/**
 * 保存form方法
 */
function save() {
    if (runing){return;}
    runing=true;
    var query = getParentQuery();
    if (query.beforeSave) {//保存前处理
        if(!query.beforeSave()){
            runing=false;
            return;
        }
    }
    var form = getCurrWin().find("form");
    if ($(form).form("validate")) {
        loadData(getParentQuery().save, function (ret) {
            retMessage(ret, function () {
                if(query.afterSave)query.afterSave();
                closeCurrWin();
                refresh();
            }, true);
        }, {data: form.serializeArray()});
    } else {
        $.messager.alert("提示", "数据没有输入完整，请输入完后再保存!");
    }
    runing=false;
}
/**
 * 获得编辑框中的操作按钮
 * @param privilegId
 * @returns {string}
 */
function getButtons(privilegId) {
    var Buttons = "";
    if (privilegId) {
        var pp = privilegId.split(",");
        for (var i in pp) {
            Buttons = Buttons + buttons[pp[i]];
        }
    }
    return Buttons;
}

/**
 * 给指定对象设置值
 * @param obj
 * @param value
 */
function setVal(obj, value) {
    if (obj) {
        if(obj.length!=1){return;}
        if (obj.hasClass("easyui-textbox")||obj.hasClass("easyui-numberbox")) {
            obj.textbox("setValue", value);
        } else if (obj.hasClass("easyui-combobox")) {
            obj.combobox("select", value);
        } else if (obj.hasClass("easyui-tree")) {
            obj.tree({data: value});
        } else if (obj.hasClass("easyui-datagrid")) {
            obj.datagrid({data: value});
        } else {
            obj.val(value);
        }
    }
}
/**
 * 获取指定控件内的值
 * @param obj:控件的对象实例
 * @param value:如果没有，返回的缺省值
 */
function getValText(obj, value) {
    if (obj) {
        if (obj.hasClass("easyui-combobox")) {
            return obj.combobox("getText");
        }
    }
    return value;
}
/**
 * 通过名字获得指定对象，要区别easyUI的input框和基本的input框
 * @param obj:当前按钮对象
 * @param name:要查找的对象名称
 * @param typeName:要查找的对象类型
 * @returns :返回找到的对象
 */
function getObj(obj, name, typeName) {
    var kk = $(obj).parent();
    var temp;
    for (var i = 0; i <= 100; i++) {
        temp = kk.find("[" + (typeName || "name") + "=" + name + "]");
        if (temp[0]) {
            return temp;
        } else {
            kk = kk.parent();
        }
    }
}

/**
 * 获取url中指定的值，在使用
 * @param url
 * @param name
 * @returns {Array|{index: number, input: string}|*|string}
 */
function getUrlParam(url, name) {
    var matched = url.match(new RegExp("\\?(.*\\&)*" + name + "=([^\\&]+)"));
    return matched && matched[2] || '';
}

/**
 * 查看或者编辑时数据填充
 * @param type:当前类型，是查看show或者编辑edit
 * @param ret:后台返回的json表单数据
 * @param callback:填充完成后会调函数
 */
function dataFilling(type, ret, callback) {
    var w = $("div[name=mypopWin]");
    if (!ret)return;
    for (var dd in ret) {
        if (dd != "status" && dd != "privilege") {
            var form = $(w[w.length - 1]).find("form[name=" + dd + "]");
            for (var key in ret[dd]) {
                var textboxname = form.find("[textboxname=" + key + "]");
                if (!textboxname.length) {
                    textboxname = form.find("[name=" + key + "]");
                }
                setVal(textboxname, ret[dd][key]);
                if (type == "show") {
                    form.addClass("hide");
                    textboxname.parents("td").append("<p>" + getValText(textboxname, ret["obj"][key]) + "</p>");
                }
            }
        }
    }
    if (callback) {callback();}
}

/**
 * 设置当前操作类型
 * @param obj
 * @param type
 * @param data
 */
function onLoad(obj, type, data, callback) {
    obj.find("[name=option_type]").val(type);//设置type字段的值
    dataFilling(type, data, callback);
}
/**
 * 清空表格控件内的所有数据
 * @param objname:表格控件名称或者实例
 * @param win:窗口实例
 */
function clearDataGrid(objname,win){
    if (!win) {win = getCurrWin();}
    if (!objname) {objname = "winDataList";}
    win = win.find("[name=" + objname + "]");
    if (win.hasClass("treegrid")) {
        win.treegrid("loadData",{total:0,rows:[]});
    } else if (win.hasClass("datagrid")) {
        win.datagrid("loadData",{total:0,rows:[]});
    }
}
/**
 * 设置查询参数，将当前窗口的按钮工具条内的查询条件放到该窗口的param变量中
 * @param objname:表格控件名称或者实例
 * @param win:窗口实例
 */
function setParam(objname,win){
    if (!win) {win = getCurrWin();}
    if (!objname) {objname = "winDataList";}
    win = win.find("[name=" + objname + "]");
    var input;
    //var temp={};
    var param=getCurrQuery().param;
    if (win.hasClass("treegrid")) {
        input=win.treegrid("options").toolbar.find("input[name]");
    } else if (win.hasClass("datagrid")) {
        input=win.datagrid("options").toolbar.find("input[name]");
    }
    //var input=win.datagrid("options").toolbar.find("input[name]");
    $(input).each(function(index, elmt){
        //temp[$(this).attr("name")] = $(this).val();
        param[$(this).attr("name")] = $(this).val();
    });
}
/**
 * 刷新指定列表
 * objname:要刷新的datagrid或treegrid控件的名称或者实例
 * win:窗口实例
 */
function refresh(objname, win) {
    if (objname instanceof $) {
        win = objname;
    } else {
        if (!win) {win = getCurrWin();}
        if (!objname) {objname = "winDataList";}
        win = win.find("[name=" + objname + "]");
    }
    var temp;
    if(getCurrQuery().param){
        temp=getCurrQuery().param;
    }
    if(getCurrSearch()) {
        var searchStr = $.toJSON(getCurrSearch().getAPI().getFilter());
        if (searchStr) {
            //temp["searchObj"]=searchStr;
            temp.searchObj = searchStr;
        }
    }
    if (win.hasClass("treegrid")) {
        win.treegrid("reload",temp).treegrid("unselectAll");
    } else if (win.hasClass("datagrid")) {
        win.datagrid("load",temp).datagrid("unselectAll");
    }
}

/**
 * 关闭当前窗口
 */
function closeCurrWin() {
    getCurrWin().window('close');
}
/**
 * 获得当前窗口
 */
function getCurrWin() {
    var _w = $("div[name=mypopWin]");
    if (_w.length >= 1) {
        return $(_w[_w.length - 1]);
    } else {
        return $('#xiongtab').tabs('getSelected');
    }
}

/**
 * 获得当前查询参数,当检测窗口是否打开的时候，当前窗口还没打开，而是父窗口
 * @param name:窗口内datagrid或treegrid控件的名称
 * @returns {*}
 */
function getCurrQuery(name) {
    if (!name) {name = "winDataList";}
    return getCurrWin().find("[name=" + name + "]").data("att");
}

/**
 * 获得当前查询参数，当保存或者删除的时候，其实本窗口已经打开，此时需要父窗口的相关参数
 * @param name:窗口内datagrid或treegrid控件的名称
 * @returns {*}
 */
function getParentQuery(name) {
    if (!name) {name = "winDataList";}
    return getParentWin().find("[name=" + name + "]").data("att");
}
/**
 * 获得查询窗口实例
 *@param name:窗口内datagrid或treegrid控件的名称
 */
function getCurrSearch(name) {
    if (!name) {name = "winDataList";}
    return getCurrWin().find("[name=" + name + "]").data("search");
}

/**
 * 获得当前窗口的上一级，系父窗口，如果没有父窗口，则返回选中的tabs窗口
 * 如果写了name返回该
 * @param index:窗口序号，0:取最底层窗口，>0时最底层窗口为0，从底层窗口网上查找；<0时当前窗口为0，从当前窗口往下查找
 */
function getParentWin(index) {
    if(index){
        if(index==0){
            return $('#xiongtab').tabs('getSelected');
        }else if(index>0){
            var _w = $("div[name=mypopWin]");
            return $(_w[(index-1)]);
        }else{
            var _w = $("div[name=mypopWin]");
            if (_w.length > 1) {
                return $(_w[_w.length + (index-1)]);
            }
        }
    }
    var _w = $("div[name=mypopWin]");
    if (_w.length > 1) {
        return $(_w[_w.length - 2]);
    } else {
        return $('#xiongtab').tabs('getSelected');
    }
}
/**
 * 显示格式化，显示可用或者不可用图标
 * @param value
 * @returns {string}
 */
function formatProgress(value){
    return "<img src='/system/easyui/myicons/"+((value==1)?"ok":((value==2)?"wait":"no"))+".png'/>";
}
/**
 *
 * @param url:url地址
 * @param multiple:是否多选，true:多选,false:单选
 * @param isLeaf:是否只选择叶子节点，true:只选择叶子节点,false:可选择叶子节点和父节点
 * @param idField:取得节点后id值放的控件,多个值间用逗号分隔
 * @param textField:取得节点后文本值放的控件,多个值间用逗号分隔
 * @param callback:取得值后关闭窗口前的回调函数
 * @param moreField:扩展，暂时没有
 * @constructor
 */
function ChoiceBaseDialog(url,multiple,isLeaf,idField,textField,callback,moreField) {
    if(!multiple)multiple=false;
    if(!isLeaf)isLeaf=false;

    $("<div name='ChoiceBaseDialog'></div>").dialog({
        width: 350,
        height: 400,
        modal: true,
        title: "选择",
        content: $("<ul name='ChoiceBaseDialogTree' class='tree' data-options=\"valueField:'id',textField:'text',lines:true,animate:true,checkbox:true,onlyLeafCheck:"+isLeaf+", method:'get',cascadeCheck:false,url:'" + url + "'\"></ul>").tree({onCheck: function (node,checked) {
            if(checked) {
                if (!multiple) {
                    var tree= getObj(this, "ChoiceBaseDialogTree");
                    var nodes = tree.tree('getChecked');
                    if(nodes){
                        $(nodes).each(function(index,elem){
                            if(elem!=node){
                                tree.tree('uncheck', elem.target);
                            }
                        });
                    }
                }
            }
        },onSelect: function (node) {
            getObj(this, 'ChoiceBaseDialogTree', null).tree('check', node.target);
        },onDblClick:function (node){
            getObj(this, 'ChoiceBaseDialogTree', null).tree('check', node.target);
            $('#dialog_ok').linkbutton().click();
        }}),
        buttons: [
            {
                id:'dialog_ok',
                text: '确定',
                iconCls: 'icon-add',
                handler: function () {
                    var nodes = getObj(this, "ChoiceBaseDialogTree").tree('getChecked');
                    if (nodes&&nodes.length ==0) {
                        nodes = getObj(this, "ChoiceBaseDialogTree").tree('getSelected');
                    }
                    if (nodes&&nodes.length>0) {
                        var id="";
                        var text = "";
                        $(nodes).each(function(index,elem){
                            id +=","+elem.id;
                            text +=","+elem.text;
                        });
                        if(id!="")id=id.substring(1);
                        if(text!="")text=text.substring(1);
                        setVal(idField, id);
                        setVal(textField, text);
                        if(callback)callback(this);
                        $(this).parent().parent().find("[name=ChoiceBaseDialog]").dialog('destroy');
                    } else {
                        $.messager.alert("提示", "请先选择一个后才能继续！");
                    }

                }
            },
            {
                text: '取消',
                iconCls: 'icon-close',
                handler: function () {
                    $(this).parent().parent().find("[name=ChoiceBaseDialog]").dialog('destroy');
                }
            }
        ]
    });
}

/**
 * 用于选择部门内人员，界面分为左右两栏
 * @param title1:左栏名称，通常是部门名称
 * @param url1:左栏的url地址
 * @param title2:右栏名称，通常是部门内人员
 * @param url2:右栏的url地址
 * @param multiple:是否多选，true多选，false只能单选
 * @param idField:取得节点后id值放的控件,多个值间用逗号分隔
 * @param textField:取得节点后文本值放的控件,多个值间用逗号分隔
 * @param callback:取得值后关闭窗口前的回调函数
 * @param moreField:扩展，暂时没有
 * @constructor
 */

function ChoiceDeptPersonDialog(title1,url1,title2,url2,multiple,idField,textField,callback,moreField) {
    if(!multiple)multiple=false;
    var content =$("<div class='easyui-layout' data-options='fit:true'><div data-options=\"region:'west',split:true,collapsible:false\" title='"+title1+"' style='width:50%'><ul name='dept_tree' data-options=\"valueField:'id',textField:'text',lines:true,animate:true,checkbox:true,cascadeCheck:false\"></ul></div>"
        +"<div data-options=\"region:\'center\',split:true,collapsible:false\" title='"+title2+"' style='width:50%'><ul name=\"person_Tree\" data-options=\"valueField:'id',textField:'text',lines:true,animate:true,checkbox:true,cascadeCheck:false\"></ul></div></div>");
    var leftTree=content.find('[name=dept_tree]');
    var rightTree=content.find('[name=person_Tree]');
    leftTree.tree({
        url:url1,
        onCheck: function (node, checked) {
            var nodes = leftTree.tree('getChecked');
            var text="";
            if(nodes.length>0){
                for (var i = 0; i < nodes.length; i++) {
                    text += nodes[i]["id"];
                    if (i != (nodes.length - 1)) {
                        text += ",";
                    }
                }
                rightTree.tree({url: url2+ text});
            }else{
                rightTree.tree('loadData',{});
            }
        },
        onSelect: function (node) {
            leftTree.tree('check', node.target);
        }
    });

    rightTree.tree({onCheck: function (node,checked) {
        if(checked) {
            if (!multiple) {
                var nodes = rightTree.tree('getChecked');
                if(nodes){
                    $(nodes).each(function(index,elem){
                        if(elem!=node){
                            rightTree.tree('uncheck', elem.target);
                        }
                    });
                }
            }
        }
    },onSelect: function (node) {
        rightTree.tree('check', node.target);
    },onDblClick:function (node){
        rightTree.tree('check', node.target);
        $('#dialog_ok1').linkbutton().click();
    }});


    $("<div name='ChoiceBaseDialog1'></div>").dialog({
        width: 550,
        height: 400,
        modal: true,
        title: "选择",
        content:content,
        buttons: [
            {
                id:'dialog_ok1',
                text: '确定',
                iconCls: 'icon-add',
                handler: function () {
                    var nodes = rightTree.tree('getChecked');
                    if (nodes.length==0) {
                        nodes = rightTree.tree('getSelected');
                    }
                    if (nodes.length>0) {
                        var id="";
                        var text = "";
                        $(nodes).each(function(index,elem){
                            id +=","+elem.id;
                            text +=","+elem.text;
                        });
                        if(id!="")id=id.substring(1);
                        if(text!="")text=text.substring(1);
                        setVal(idField, id);
                        setVal(textField, text);
                        if(callback)callback(this);
                        $(this).parent().parent().find("[name=ChoiceBaseDialog1]").dialog('destroy');
                    } else {
                        $.messager.alert("提示", "请先选择一个后才能继续！");
                    }

                }
            },
            {
                text: '取消',
                iconCls: 'icon-close',
                handler: function () {
                    $(this).parent().parent().find("[name=ChoiceBaseDialog1]").dialog('destroy');
                }
            }
        ]
    });
}


function AuditDialog(system_id,moduleCode,document_id) {
    loadData("/system/auditShow.do?system_id="+system_id+"&moduleCode="+moduleCode+"&document_id="+document_id,function (result) {
        retMessage(result,
            function () {
                $("<div id='AuditDialog' name='AuditDialog'></div>").dialog({
                    width: 400,
                    height: 210,
                    modal: true,
                    title: "审核",
                    content: "<form id='audit_form' name='audit_form' method='post' class='c-form'><input type='hidden' name='uuid' value='"+((result['audit_form']['uuid'])?result['audit_form']['uuid']:"")+"'><input type='hidden' name='system_id' value='"+system_id+"'><input type='hidden' name='moduleCode' value='"+moduleCode+"'><input type='hidden' name='module_id' value='"+result['audit_form']['module_id']+"'><input type='hidden' name='document_id' value='"+document_id+"'>"
                    +"<table class='table'><tr><td class='label'>审核状态：</td><td class='value'><select name='audit_result' class='easyui-combobox easyui-validatebox' data-options=\"required:true,editable:false,missingMessage:'请选择审核状态',onSelect:function(){if($(this).parent().find('[textboxname=audit_result]').combobox('getValue')==1){$(this).parent().parent().parent().find('[textboxname=audit_advice]').textbox('setValue','审核通过、符合条件')}else{$(this).parent().parent().parent().find('[textboxname=audit_advice]').textbox('setValue','')}}\"><option value='1'"+((result['audit_form']['audit_result']==1)?" selected":"")+">审核通过</option><option value='3'"+((result['audit_form']['audit_result']==3)?" selected":"")+">审核未通过</option></select></td></tr>"
                    +"<tr><td class='label' style='height:80px'>审核意见：</td><td class='value'><input class='easyui-textbox easyui-validatebox' type='text' name='audit_advice' value='"+((result['audit_form']['audit_result']==1||result['audit_form']['audit_result']==null)?((result['audit_form']['audit_advice'])?result['audit_form']['audit_advice']:"审核通过、符合条件"):'')+"' maxlength='100' data-options=\"multiline:true,validType:'maxLength[60]'\" style='height:80px'/></td></tr></table></form>",
                    buttons: [
                        {
                            id:'dialog_ok',
                            text: '确定',
                            iconCls: 'icon-add',
                            handler: function () {
                                if ($('#audit_form').form('validate')) {
                                    loadData("/system/auditSave.do", function (ret) {
                                        retMessage(ret, function () {
                                            $('#AuditDialog').dialog('destroy');
                                            refresh();
                                        }, true);
                                    }, {data: $('#audit_form').serializeArray()});
                                } else {
                                    $.messager.alert('提示', '数据没有输入完整，请输入完后再保存!');
                                }
                            }
                        },
                        {
                            text: '取消',
                            iconCls: 'icon-close',
                            handler: function () {
                                $('#AuditDialog').dialog('destroy');
                                //$(this).parent().parent().find("[name=AuditDialog]").dialog('destroy');
                            }
                        }
                    ]
                });
            }

        );
    });


}
/**
 * 参数：json格式{title:'',url:'',windowWidth:'',windowHeight:''}
 * 公有的弹出框
 * @param title
 * @param url
 */
function commonWin(param) {
    var table = getCurrWin().find("[name=winDataList]");
    var row = null;
    if (table.hasClass("treegrid")) {
        row = table.treegrid("getSelections");
    } else if (table.hasClass("datagrid")) {
        row = table.datagrid("getSelections");
    }
    if (!row) {
        $.messager.alert("提示", "请先选择一行再操作！");
        return;
    }
    if (row.length == 0) {
        $.messager.alert("提示", "请先选择一行再操作！");
        return;
    }

    var query = getCurrQuery();
    if (param.isSubModule) {
        loadData(query.common + "?id=" + row[0][query.keyField], function (result) {
            retMessage(result,
                function () {
                    openWinUrl(param.windowWidth, param.windowHeight, param.title, param.url + "?id=" + row[0][query.keyField], null, null, null);
                }
            );
        })
    } else {
        openWinUrl(param.windowWidth, param.windowHeight, param.title, param.url + "?id=" + row[0][query.keyField], null, null, null);
    }
}
/**
 * 打开一个窗口
 * @param width
 * @param height
 * @param title
 * @param content
 * @param name
 * @param id
 * @param onLoad
 */
function openWin(width, height, title, content, name, id, onLoad) {
    if (!name) {
        name = "mypopWin";
    }
    if (!id) {
        id = "";
    } else {
        id = "id='" + id + "'";
    }
    $("<div " + id + " name='" + name + "'/>").window({
        width: width, height: height, title: title, modal: true, shadow: true,
        closed: false, closable: true,
        minimizable: false,
        collapsible: false,
        content: content,
        onBeforeOpen: function () {
            if (onLoad) {
                onLoad($(this));
            }
        },
        onLoad: function () {
            if (onLoad) {
                onLoad($(this));
            }
        },
        onClose: function () {
            $(this).window('destroy')
        }
    });
}


/**
 * 打开一个窗口
 * @param width
 * @param height
 * @param title
 * @param url
 * @param name
 * @param id
 * @param onLoad
 */
function openWinUrl(width, height, title, url, name, id, onLoad) {
    if (!name) {
        name = "mypopWin";
    }

    if (!id) {
        id = "";
    } else {
        id = "id='" + id + "'";
    }

    $("<div " + id + " name='" + name + "'/>").window({
        width: width, height: height, title: title, modal: true, shadow: true,
        closed: false, closable: true,
        minimizable: false,
        collapsible: false,
        href: url,
        onLoad: function () {
            if (onLoad) {
                onLoad($(this));
            }
        },
        onClose: function () {
            $(this).window('destroy')
        }
    });
}


/**
 * 全局ajax过滤
 */
function globalAjaxFilter(response) {
    var assumeJson;
    try {
        if (response && (response.replace(/^\s*/, '').charAt(0) === '{' || response.replace(/^\s*/, '').charAt(0) === '[')) {
            assumeJson = eval("(" + response + ")");
        } else {
            throw undefined;
        }
    } catch (e) {
        assumeJson = {};
    }
    if (typeof assumeJson === 'object' && assumeJson.status === '000011') {
        $.messager.alert("提示", '登陆过期，3秒后将跳转到登陆页...');
        setTimeout(function () {location.href = '/';}, 3000);
        return {forward: false};           //是否完成ajax
    } else {
        return {forward: true};            //是否完成ajax
    }
}

function loadData(url, call_back, data) {
    jQuery.ajax({
        type: (data && data.method) ? data.method : 'post',
        url: url,
        data: (data && data.data) ? data.data : null,
        dataType: (data && data.dataType) ? data.dataType : 'json',
        cache: (data && data.cache) ? data.cache : false,
        timeout: 60000,
        beforeSend: function (XMLHttpRequest) {},
        success: function (data) {call_back && call_back(data);},
        complete: function (XMLHttpRequest, textStatus) {},
        error: function (XMLHttpRequest, textStatus, errorThrown){call_back && call_back(null);}
    });
}


/*
 dataGrid编辑
 */
function bindEditGridSetVal(id,func){
    $(id).data("setVal",func);
}
function bindEditGridEditVal(id,func){
    $(id).data("editVal",func);
}
function endEditing(id){
    var editIndex=$(id).data("rowIndex");
    if (editIndex==null){return true;}
    if ($(id).datagrid('validateRow', editIndex)){
        $(id).data("setVal")(id,editIndex);
//            var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'system_id'});
//            $('#dg').datagrid('getRows')[editIndex]['system_name'] = $(ed.target).combobox('getText');
//
//            ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'dept_id'});
//            $('#dg').datagrid('getRows')[editIndex]['dept_name'] = $(ed.target).combotree('getText');
        $(id).datagrid('endEdit', editIndex);
        $(id).data("rowIndex",null);
        return true;
    } else {
        return false;
    }
}
function onClickRow(id,index){
    var editIndex=$(id).data("rowIndex");
    if (editIndex != index){
        if (endEditing(id)){
            //beginEdit(id,index);
            $(id).data("editVal")(id,index);
//                var systemId = $(id).datagrid('getRows')[index]['system_id'];
//                $(id).datagrid('selectRow', index).datagrid('beginEdit', index);
//                var comboTree = $(id).datagrid('getEditor', {index:index,field:'dept_id'});
//                $(comboTree.target).combotree({
//                    url: '/system/dept/list.do?system_id='+systemId,
//                    required: true,
//                    onLoadSuccess:function(node, data) {
//                        var deptId = $(id).datagrid('getRows')[index]['dept_id'];
//                        $(comboTree.target).combotree("setValue", deptId);
//                        var deptName = $(id).datagrid('getRows')[index]['dept_name'];
//                        $(comboTree.target).combo('setText', deptName);
//                    }
//                });
            $(id).data("rowIndex",index);
        } else {
            $(id).datagrid('selectRow', editIndex);
        }
    }
}
function append(id){
    if (endEditing(id)){
        $(id).datagrid('appendRow',{});
        $(id).data("rowIndex",$(id).datagrid('getRows').length-1);
        var editIndex=$(id).data("rowIndex");
        $(id).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
    }
}
function removeit(id){
    var editIndex=$(id).data("rowIndex");
    if (editIndex==null){return;}
    $(id).datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
    $(id).data("rowIndex",null);
}
function accept(id){
    if (endEditing(id)){
        $(id).datagrid('acceptChanges');
    }
}

//////////审核
function auditRow() {
    var table = getCurrWin().find("[name=winDataList]");
    var row;
    if (table.hasClass("treegrid")) {
        row = table.treegrid("getSelections");
    } else if (table.hasClass("datagrid")) {
        row = table.datagrid("getSelections");
    }
    if (!row || row.length == 0) {
        $.messager.alert("提示", "请先选择一行再操作！");
        return;
    }
    var query = getParentQuery();
    AuditDialog(query.audit.system_id,query.audit.moduleCode,row[0][query.keyField]);
}

$.fn.tree.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled=opt.idFiled || 'id'
        ,textFiled = opt.textFiled || 'text'
        ,parentField=opt.parentField || '_parentId';
    if (parentField) {
        var i,l,treeData = [],tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

/**
 * 解决IE下jquey easyui datagrid 缓存问题
 */
$.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
});