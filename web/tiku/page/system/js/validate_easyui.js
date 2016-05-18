$.extend($.fn.validatebox.defaults.rules, {
    minLength: { // 判断最小长度
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '最少输入 {0} 个字符。'
    },
    length: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "内容长度介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确(正确格式如：13450774432)'
    },
    phoneOrMobile: {//验证手机或电话
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '请填入手机或电话号码,如13688888888或020-8888888'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    floatOrInt: {// 验证是否为小数或整数
        validator: function (value) {
            return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
        },
        message: '请输入数字，并保证格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^d{0,}(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确(正确如：453384319)'
    },
    integer: {// 验证整数
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    chinese: {// 验证中文
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
//			return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    carNo: {
        validator: function (value) {
            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
        },
        message: '车牌号码无效（例：粤J12350）'
    },
    carenergin: {
        validator: function (value) {
            return /^[a-zA-Z0-9]{16}$/.test(value);
        },
        message: '发动机型号无效(例：FG6H012345654584)'
    },
    email: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的电子邮件账号(例：abc@126.com)'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    }, department: {
        validator: function (value) {
            return /^[0-9]*$/.test(value);
        },
        message: '请输入部门排序号(例：1)'
    }, same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    }
});
$.extend($.fn.validatebox.defaults.rules, {
    checkSingle: {//检查唯一性
        validator: function (value, param) {
            var type = getCurrWin().find("input[name=option_type]").val();
            if (type == "add") {
                var checkR = $.ajax({
                    async: false,
                    cache: false,
                    type: 'post',
                    url: param[0],
                    data: {val: value}
                }).responseText;
                return checkR === "0";
            } else {
                return true;
            }
        },
        message: '{1}已被注册'
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    //同时验证了身份证和军官证的格式
    checkSfzJgzCorrect: {
        validator: function (value, param) {
            var cred_type = getCurrWin().find("input[name=user_cred_type]").val();
            if(cred_type==1) {//身份证
                //15位和18位身份证号码的正则表达式
                if(value.length==18) {
                    value = value.toUpperCase();
                }
                var regIdCard = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
                //如果通过该验证，说明身份证格式正确，但准确性还需计算
                if (regIdCard.test(value)) {
                    if (value.length == 18) {
                        var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                        var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                        var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                        for (var i = 0; i < 17; i++) {
                            idCardWiSum += value.substring(i, i + 1) * idCardWi[i];
                        }
                        var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                        var idCardLast = value.substring(17);//得到最后一位身份证号码
                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (idCardMod == 2) {
                            if (idCardLast == "X" || idCardLast == "x") {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                            return idCardLast == idCardY[idCardMod];
                        }
                    }else  if (value.length == 15) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return /^[0-9A-Za-z]{6,8}$/.test(value);
            }
        },
        message: '请根据所选的证件类型输入正确格式的证件号'
    }
});
$.extend($.fn.validatebox.defaults.rules, {
    //验证是否选择了用户类型
    userTypeInput: {
        validator: function (value, param) {
            var win = getCurrWin();
            if(win.find("input[name=sfz_holder]").val()!="") {//sfz_holder不为空，则代表现在是编辑
                //编辑的话肯定是已经选择了用户类型了，所以返回true
                return true;
            }
            var user_type = win.find("input[name=user_type]").val();
            return user_type!="";
        },
        message: '请先选择用户类型再填写'
    }
});
$.extend($.fn.validatebox.defaults.rules, {
    //同时验证了身份证和军官证
    sfzJgzUniqueAjax: {
        validator: function (value, param) {
            var win = getCurrWin();
            //获取当前添加/修改的用户类型
            var user_type = win.find("input[name=user_type]").val();
            var sfz_holder = win.find("input[name=sfz_holder]").val();

            modifyUserCredCode(value, win);

            if(sfz_holder.toUpperCase()==value.toUpperCase()) {//如果修改后跟修改之前的一样，则不需要进行ajax验证
                return true;
            }
            //发送ajax请求
            var flag = true;
            $.ajax({
                url:  "/system/reg/checkSfz.do?user_type="+user_type,
                async: false,
                type: "post",
                data: {val: value},
                success: function (data) {
                    if (data == 0) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });
            return flag;
        },
        message: '证件号码已存在，请重新输入'
    }
});

//value：身份证号码, win：当前窗口对象
function modifyUserCredCode(value, win) {
    //修改出生日期
    if(value.length>=15) {//大于等于15位的才是身份证号码，如果是军官证，这里是忽略的
        var birthday = getUserBirthdayFromCredCode(value);
        win.find("input[textboxname=user_birthday]").textbox('setValue', birthday);
        win.find("input[name=user_birthday]").val(birthday);
    }//修改出生日期结束
}

//从身份证中获取格式为2015-05-19这种格式的生日
function getUserBirthdayFromCredCode(value) {
    var birthday;
    var date;
    //从身份证中获得出生年月
    if(value.length==18) {
        date = value.substring(6,14);
        birthday = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
    } else if(value.length==15) {
        //date = value.substring(6,12);
        //var y = 19;
        //var year = y + date.substring(0,2);
        //if((2015 - parseInt(year)) > 95) {
        //    y = 20;
        //}
        //birthday = y + date.substring(0,2)+"-"+date.substring(2,4)+"-"+date.substring(4,6);
        date = value.substring(6, 14);
        birthday = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }
    return birthday;
}

$.extend($.fn.validatebox.defaults.rules, {
    //同时验证了身份证和军官证的格式
    checkzyrCorrect: {
        validator: function (value, param) {
            if(value.length==18||value.length==15) {//身份证
                //15位和18位身份证号码的正则表达式
                if(value.length==18) {
                    value = value.toUpperCase();
                }
                var regIdCard = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
                //如果通过该验证，说明身份证格式正确，但准确性还需计算
                if (regIdCard.test(value)) {
                    if (value.length == 18) {
                        var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                        var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                        var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                        for (var i = 0; i < 17; i++) {
                            idCardWiSum += value.substring(i, i + 1) * idCardWi[i];
                        }
                        var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                        var idCardLast = value.substring(17);//得到最后一位身份证号码
                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (idCardMod == 2) {
                            if (idCardLast == "X" || idCardLast == "x") {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                            return idCardLast == idCardY[idCardMod];
                        }
                    }else  if (value.length == 15) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return /^[0-9A-Za-z]{6,8}$/.test(value);
            }
        },
        message: '请输入正确格式的证件号'
    }
});