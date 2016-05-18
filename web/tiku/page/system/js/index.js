$(document).ready(function () {

    /**
     * 解决IE下jquey easyui datagrid 缓存问题
     */
    $.ajaxSetup ({
        cache: false //关闭AJAX相应的缓存
    });

    /* 菜单与面板 */

    /* jquery easyui无法为tab保存额外数据以区分tab唯一 因此... */
    var tabContext = function () {
        var opendIds = [],
            limit = 16;

        return {
            prefill: function () {
                opendIds.push(-100);
            },
            getIndex: function (id) {
                var index = Lang.getIndexByValue(id, opendIds);
                return index !== undefined ? index : -1;
            },
            getId: function (index) {
                return opendIds[index];
            },
            isOpened: function (id) {
                return this.getIndex(id) !== -1 ? true : false;
            },
            open: function (id) {
                opendIds.push(id);
            },
            close: function (id) {
                var index = Lang.getIndexByValue(id, opendIds);
                if (index !== undefined) {
                    opendIds.splice(index, 1);
                }
            },
            overflow: function () {
                return opendIds.length >= limit;
            },
            LIMIT: limit
        }
    }();

    /* 菜单 */
    var tabs = $("#second-class-top-tab > .easyui-tabs"), index = 0;
    while (tabs.tabs('exists', index++)) {
        tabContext.prefill();
    };

    $('#menu').tree({
        url: '/system/privileges/list.do?&menus_status=1&isTreeAll=1&state=tree',
        animate: true,
        onClick: function (element) {openTab(element);}
    });

    tabs.tabs({
        onClose: function (title, index) {
            tabContext.close(tabContext.getId(index));
        }
    });

    /**
     * @param element { id(唯一识别码), text(标题), url|content(内容) }
     */
    function openTab(element) {
        if (element.url || element.content) {
            if(element.url.indexOf("(")>0){
                eval(element.url);
            }else{
                var tab;
                if (!tabContext.isOpened(element.id)) {
                    if (tabContext.overflow()) {
                        return $.messager.alert("系统提示", '亲爱的用户，系统最多可打开' + tabContext.LIMIT + '个面板！请关闭闲置的面板再试！');
                    }
                    var config = {title: element.text, closable: true};
                    if (element.url) {
                        config.href = element.url;
                        config.tools = [
                            {
                                iconCls: 'icon-mini-refresh',
                                handler: function () {
                                    loadData(element.url, function (_data) {
                                        tabs.tabs('update', {
                                            tab: tab,
                                            options: {content: _data }
                                        });
                                    }, {dataType: "html"});
                                }
                            }
                        ];
                    } else if (element.content) {
                        config.content = element.content;
                    }
                    tabs.tabs("add", config);
                    tab = tabs.tabs('getSelected');
                    tabContext.open(element.id);
                } else {
                    /* 关闭侧边栏 */
                    //var t = tabs.tabs('getSelected');
                    tabs.tabs("select", tabContext.getIndex(element.id));
                }
            }
        }
    }

    $("#menu").niceScroll({styler: "fb", cursorcolor: "rgb(192, 192, 192)", cursorwidth: '6', cursorborderradius: '10px', background: 'rgb(223, 223, 223)', spacebarenabled: false, cursorborder: '', zindex: '1000'});

    /**
     * 当添加no-select，不允许选择
     */
    var noselect = function () {
        var fn = {
            start: function () {
                setInterval(function () {
                    $('.no-select').attr('unselectable', 'on').attr('onselectstart', 'return false;');
                }, 500);
            }
        };

        return fn;
    }();

    noselect.start();

    loadData('/system/user/getUserType.do', function(result) {
        retMessage(result, function () {
            $("#currUserType").val(result.user_type);
        },false);
    },null);
});


/**
 * 系统在线监测
 * @type {number}
 */
function online() {
    $.ajax({
        url: "/system/login/online.do",
        type: 'get',
        dataType: 'json',
        timeout: 3000,
        error: function () {
            $.messager.confirm("系统消息", "亲爱的用户：超时或者系统异常,请重新登陆!", function (r) {
                if(r){
                    window.top.location.href = '/system/page/util/login.shtml';
                }else{
                    setTimeout("online()", 5000);
                }
            });
        },
        success: function (data) {
            if (data == "first") {
                $.messager.show({
                    title: '系统消息',
                    msg: '亲爱的用户：欢迎进入系统...',
                    timeout: 3000,
                    showType: 'slide'
                });

            }
            if (data == returnStatus.noLoin.id) {
                $.messager.confirm("系统消息", "亲爱的用户：账号信息已经失效,请重新登陆!", function (r) {
                    if(r){
                        window.top.location.href = '/system/page/util/login.shtml';
                    }else{
                        setTimeout("online()", 5000);
                    }
                });
            }else{
                setTimeout("online()", 5000);
            }
        }
    });
}
//online();

//修改个人资料
function editProfile() {
    openWinUrl(800,560,'修改个人资料', '/system/page/util/profile.shtml');
}
//修改登录密码
function updatePassword() {
    openWinUrl(376,190,'修改登录密码', '/system/page/util/pwd.shtml');
}

resizeDispatcher.on('resize', Lang.buffer(function () {
    $('.window > .window-body').window("center");
}, 500));
