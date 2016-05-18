/**
 * 用于注册单位、申请人
 * Created by heyiyong on 2015/3/30.
 */
var regs = [];
regs["email"] = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
regs["phone"] = /^13[0-9]{9}|15[012356789][0-9]{8}|18[0-9]{9}|14[57][0-9]{8}|170[059][0-9]{7}|17[678][0-9]{8}$/;
regs["passport_pwd"] = /^[0-9a-zA-Z]{6,20}$/;
regs["user_id"] = /^[0-9A-Za-z_]{6,15}$/;
regs["sfz"] = /(^(\d{17})([0-9]|X|x)$)|(^(\d{15})$)/;
regs["jgz"] = /^[0-9A-Za-z]{7}$/;
regs["dept_code"] = /(^$|^[0-9|A-Z]{8}[-]?[0-9|X]$)/;//组织机构代码
regs['all'] = /.+/;//不检验
var loginType;
var validateJson = {
    user_id: {min: 1, max: 20, regexp: regs['user_id'], required: true},
    passport_pwd: {min: 1, max: 20, regexp: regs['passport_pwd'], required: true},
    rpassport_pwd: {min: 1, max: 20, regexp: regs['passport_pwd'], required: true},
    user_realName: {min: 1, max: 30, regexp: regs['all'], required: true},
    user_cred_code: {min: 1, max: 18, regexp: regs['all'], required: true},
    user_email: {min: 1, max: 60, regexp: regs['email'], required: true},
    user_mobilephone: {min: 1, max: 40, regexp: regs['phone'], required: true},
    check_code: {min: 4, max: 4, regexp: regs['all'], required: true}
};
//获得校验json
function getValidateJson(type) {
    loginType = type;
    if (type == "user") {
        validateJson["user_birthday"] = {min: 1, max: 30, regexp: regs['all'], required: true};
    } else {
        validateJson["dept_code"] = {min: 9, max: 10, regexp: regs['dept_code'], required: true};
        validateJson["dept_name"] = {min: 1, max: 30, regexp: regs['all'], required: true};
        validateJson["dept_addr"] = {min: 1, max: 60, regexp: regs['all'], required: true};
    }
    return validateJson;
}
//检查是否能够注册
function checkCanReg(type) {
    $.ajax({
        url: "/system/reg/canReg.do?type=" + type,
        success: function (data) {
            if (data.res == 0) {
                $.messager.alert("提示", "当前未开放注册，开放的时间为<br>" + data.time1 + " 到 " + data.time2, "info", function () {
                    window.location.href = "/system/page/util/login.shtml";
                });
            }
        }
    });
}
function validatePassportAjax(ajaxCheck) {
    return validateField('user_id', 'vuser_id', '用户名不能为空', 'user_id', '用户名不合法（只包含大小写英文、数字和下划线，长度在6-15之间）', {
        url: "/system/reg/checkUserId.do",
        msg: "用户名已存在，请重新填写",
        ajaxCheck: ajaxCheck
    });
}

function validatePwd(type) {
    if (type == 1) {
        return validateField('passport_pwd', 'vpassport_pwd', '密码不能为空', 'passport_pwd', '密码为6-20位英文或数字');
    } else if (type == 2) {
        if (validateField('passport_pwd', 'vpassport_pwd', '密码不能为空', 'passport_pwd', '密码为6-20位英文或数字')) {
            var passport_pwd = $("#passport_pwd").val();
            var rpassport_pwd = $("#rpassport_pwd").val();
            if (passport_pwd != rpassport_pwd) {
                $("#vrpwd").html("<span>两次密码输入不一致！</span>");
                return false;
            }
            $("#vrpwd").html("");
            return true;
        }
    }
}

function validateSfc(ajaxCheck) {
    var sfzType = $("input[name='user_cred_type']:checked").val();
    if (sfzType == 2) {//军官证
        var regName = 'jgz';
        var errorMsg = '军官证格式有误';
        var nullMsg = '军官证不能为空';
        var jsonMsg = '该军官证已被注册';
        return validateField('user_cred_code', 'vuser_cred_code', nullMsg, regName, errorMsg, {
            url: "/system/reg/checkSfz.do?user_type=" + (loginType == "unit" ? 4 : 5),
            msg: jsonMsg,
            ajaxCheck: ajaxCheck
        });
    } else {//身份证
        var code = $("#user_cred_code").val();
        if (code == "") {
            $("#vuser_cred_code").html("<span>身份证不能为空</span>");
        } else {
            if (validateIdCard(code)) {
                //修改出生日期
                var birth = getUserBirthdayFromCredCode(code);
                $("#user_birthday").val(birth);
                if (ajaxCheck) {
                    var flag = false;
                    $.ajax({
                        url: "/system/reg/checkSfz.do?user_type=" + (loginType == "unit" ? 4 : 5),
                        async: true,
                        type: "post",
                        dataType: "json",
                        data: {val: code},
                        error: function (msg) {
                            $.messager.alert("提示", "超时或系统异常");
                            return false;
                        },
                        success: function (data) {
                            if (data == 0) {
                                $("#vuser_cred_code").html("");
                                flag = true;
                            } else {
                                $("#vuser_cred_code").html("<span>该身份证已被注册<span>");
                                flag = false;
                            }
                        }
                    });
                    return flag;
                } else {
                    $("#vuser_cred_code").html("");
                    return true;
                }
            } else {
                $("#vuser_cred_code").html("<span>身份证格式有误</span>");
            }
        }

    }

}
function validateEmail(ajaxCheck) {
    return validateField('user_email', 'vuser_email', '邮箱不能为空', 'email', '邮箱格式不正确', {
        url: "/system/reg/checkEmail.do",
        msg: "邮箱已存在，请重新填写",
        ajaxCheck: ajaxCheck
    });
}
function validateMobile() {
    return validateField('user_mobilephone', 'vuser_mobilephone', '请输入手机号', 'phone', '手机号格式不正确');
}
function validateName() {
    return validateField('user_realName', 'vrealname', '请输入姓名');
}
//验证用户（公共）模块的输入是否正确
function validateRegUserCommon() {
    return (validatePassportAjax() && validatePwd(1) && validatePwd(2) && validateName() && validateSfc() && validateMobile() && validateEmail());
}
function onSelectCredType() {
    $("input[name='user_cred_type']").bind("click", function () {
        if ($("input[name=user_cred_code]").val() != "") {
            validateSfc(true);
        }
    })
}
//验证API
function validateField(id, vid, nullMsg, regName, errMsg, ajaxJson) {
    var value = $("#" + id).val();
    if (value == "") {
        $("#" + vid).html("<span>" + nullMsg + "</span>");
    } else {
        if (regName) {
            var result = regs[regName].test(value);
            if (!result) {
                $("#" + vid).html("<span>" + errMsg + "</span>");
            } else {
                if (ajaxJson && ajaxJson.ajaxCheck) {
                    var flag = false;
                    $.ajax({
                        url: ajaxJson.url,
                        async: true,
                        type: "post",
                        dataType: "json",
                        data: {val: value},
                        error: function (msg) {
                            $.messager.alert("提示", "超时或系统异常");
                            return false;
                        },
                        success: function (data) {
                            if (data == 0) {
                                $("#" + vid).html("");
                                flag = true;
                            } else {
                                $("#" + vid).html("<span>" + ajaxJson.msg + "<span>");
                                flag = false;
                            }
                        }
                    });
                    return flag;
                } else {
                    $("#" + vid).html("");
                    return true;
                }
            }
        } else {
            $("#" + vid).html("");
            return true;
        }
    }
    return false;
}
//更换验证码
function changeCheckCode() {
    $("#checkCodeImg").attr("src", "/checkCode.do?rand=" + Math.random());
}

/**
 * 检验组织机构代码值
 * @param code
 * @returns {boolean}
 */
function validateDeptCode(code) {
    var status = false;//true false
    if (code.length < 9 || code.length > 10) {
        return {msg: "组织机构代码必须是9位或者10位", status: false};
    }
    var reg = /^$|^[0-9|A-Z]{8}[0-9|X]$/;
    var _reg = /^$|^[0-9|A-Z]{8}-[0-9|X]$/;
    /**
     * 判断组织机构代码是9位还是10位，10位的格式为 XXXXXXXX-M 九位的格式为 XXXXXXXXM
     * @type {number}
     */
    var flag = 0;
    if (reg.test(code)) {
        flag = 1;
    }
    if (flag == 0 && _reg.test(code)) {
        flag = 2;
    }
    if (flag == 0) {
        return {msg: "组织机构代码不正确，必须由大写字母或数字组成", status: false};
    } else {
        var length = code.length;
        if (length == 9) {
            code = code.substring(0, 8) + "-" + code.charAt(8);
        }
        var ws = [3, 7, 9, 10, 5, 8, 4, 2];
        var Str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var Reg = /^([0-9A-Z]){8}-[0-9|X]$/;
        if (!Reg.test(code)) {
            return {msg: "组织机构代码不正确", status: false};
        }
        var Sum = 0;
        for (var i = 0; i < 8; i++) {
            Sum += Str.indexOf(code.charAt(i)) * ws[i];
        }
        var c9 = 11 - (Sum % 11);
        if (c9 == 10) {
            c9 = 'X';
        } else if (c9 == 11) {
            c9 = '0';
        }
        if (c9 != code.charAt(9)) {
            return {msg: "组织机构代码最后一位不正确", status: false};
        }
        return {msg: "", status: true};
    }
}

/*
 * 身份证15位编码规则：dddddd yymmdd xx p
 * dddddd：6位地区编码
 * yymmdd: 出生年(两位年)月日，如：910215
 * xx: 顺序编码，系统产生，无法确定
 * p: 性别，奇数为男，偶数为女
 *
 * 身份证18位编码规则：dddddd yyyymmdd xxx y
 * dddddd：6位地区编码
 * yyyymmdd: 出生年(四位年)月日，如：19910215
 * xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
 * y: 校验码，该位数值可通过前17位计算获得
 *
 * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
 * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
 * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
 * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
 */
function validateIdCard(idCard) {
    //15位和18位身份证号码的正则表达式
    var regIdCard = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    //var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if (regIdCard.test(idCard)) {
        if (idCard.length == 18) {
            var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
            var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (var i = 0; i < 17; i++) {
                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
            }
            var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            var idCardLast = idCard.substring(17);//得到最后一位身份证号码
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
        } else {
            return true;//15位的
        }
    } else {
        return false;
    }
}

//从身份证中获取格式为2015-05-19这种格式的生日
function getUserBirthdayFromCredCode(value) {
    var birthday;
    var date;
    //从身份证中获得出生年月
    if (value.length == 18) {
        date = value.substring(6, 14);
        birthday = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    } else if (value.length == 15) {
        //date = value.substring(6, 12);
        //var y = 19;
        //var year = y + date.substring(0, 2);
        //if ((2015 - parseInt(year)) > 95) {
        //    y = 20;
        //}
        //birthday = y + date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
        date = value.substring(6, 14);
        birthday = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }
    return birthday;
}
