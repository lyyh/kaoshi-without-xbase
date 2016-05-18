/**
 * 搜索框
 * 采用new的方式
 * @param search: [
 *   {
 *      field:'字段名',                            (*)
 *      title:'显示名称',                          (*)
 *      c_dataType:'string'|'number|'date',        (*)
 *      c_lengthMax: number(默认300),
 *      c_lengthMin: number|(默认1）,
 *      c_regexp: 正则表达式(date与number有默认格式),
 *      c_range: ['值', '值2', '值3']
 *   },
 *   ...
 * ]
 * @returns
 * {
 *      getAPI: {
 *          on: function(type, callback:function(条件))，
 *          getFilter: function //返回搜索条件
 *      },
 *      show:function(按钮) //初始化
 * }
 *
 * 条件：[
 *   {
 *      field:      '字段名',
 *      multi:      '条件结合'(and | or | and not | or not),
 *      operator:   '操作符'(> | < | = | ~ | ~= ), // ~为介于，~=为包含
 *      value0:     '值',
 *      value1:     '值2' //operator为~时此值有效
 *   },
 *   ...
 * ]
 *
 */
var searcher = function(search){
    var api = new (toEventDispatchable(function(){
        this.getFilter = function(){return [];}
    }));

    return {
        getAPI: function(){
            return api;
        },
        show: function(btnSelector){
            btnSelector.onclick = null;

            var btn = $(btnSelector),
            offsetParent = btn,
            searchFrame = $($('#widget-search-tmpl').html()),
            close = searchFrame.find('.title .my-btn2-close'),
            operations = [
                {name: '大于', operator: '>', label: 1},
                {name: '包含', operator: '~=', label: 1},
                {name: '等于', operator: '=', label: 1},
                {name: '小于', operator: '<', label: 1},
                {name: '介于', operator: '~', label: 2}
            ],
            multi = [
                {name: '并且', operator: ' and '},
                {name: '或者', operator: ' or '},
                {name: '并且不', operator: ' and not '},
                {name: '或者不', operator: ' or not '}
            ],
            table =  searchFrame.find('.my-table'),
            addBtn = searchFrame.find('.my-btn3-add'),
            searchBtn = searchFrame.find('.my-btn3-search');

        while((offsetParent = offsetParent.parent()).css('position') !== 'relative' && offsetParent[0] !== document.body) { }

        offsetParent.append(searchFrame.hide());

        resizeDispatcher.on('resize', Lang.buffer(computeWidgetPosition, 500));

        function computeWidgetPosition(immediate){
            var sw = searchFrame.width();

            setTimeout(function(){
                var pw = offsetParent.width(),
                    pol = offsetParent.offset().left,
                    pot = offsetParent.offset().top,
                    bw = btn.width(),
                    bol = btn.offset().left,
                    bot = btn.offset().top;

                if(pw + pol - bol > sw) {
                    searchFrame.css('right', '').css('left', bol - pol).css('top', bot - pot);
                } else {
                    searchFrame.css('left', '').css('right', 14).css('top', bot - pot);
                }
            }, immediate === true ? 0 : 500);
        }

        btn.click(function(){
            searchFrame.show();
            computeWidgetPosition(true);
            setTimeout(function(){
                $(document).bind('click', tmp);
                close.bind('click', tmp);

                function tmp(e){
                    var $target = $(e.target);

                    if(!(childOf(e.target, searchFrame[0]) || $target.parents('.combo-panel').length) || e.currentTarget === close[0]) {
                        searchFrame.hide();
                        $(document).unbind('click', tmp);
                        close.unbind('click', tmp);
                    } else {
                        computeWidgetPosition(true);
                    }
                }
            }, 0);
        });

        addBtn.click(function(){
            var tr = $('#widget-search-column-tmpl').tmpl({
                column: search,
                operation: operations,
                multi: multi
            });

            searchFrame.find('.empty-tip').hide();
            table.append(tr);

            /* reset search data */
            tr.data('fieldObj', search[0]).data('operator', operations[0].operator).data('multi', multi[0].operator).data('value0', '').data('value1', '');

            tr.find('> td:nth-child(1) .dropdown-menu > li:eq(0)').click();
        });

        searchBtn.click(function(){
            if(searchFrame.find('tr').length && !searchFrame.find('.validate-err').length) {
                //api.dispatch('search', api.getFilter());
                api.dispatch('search');
                close.click();
            }
        });

        function initRow($tr){
            /* reset search data */
            $tr.data('value0', '').data('value1', '');

            var fieldObj = $tr.data('fieldObj'),
                dataType = fieldObj.c_dataType,
                dataRange = fieldObj.c_range,
                regexp = fieldObj.c_regexp,
                max = fieldObj.c_lengthMax,
                min = fieldObj.c_lengthMin,
                itemAmount = $tr.data('operator') === '~' ? 2 : 1,
                inputWrap = $('<div class="fix-wrap"></div>');

            $tr.find('td:nth-child(2) .dropdown-menu li').each(function(index, elmt){
                var $elmt = $(elmt);

                if(dataType === 'string') {
                    if($elmt.attr('operator') === '~=' || $elmt.attr('operator') === '=') {
                        $elmt.show();
                    } else {
                        $elmt.hide();
                    }
                } else {
                    if($elmt.attr('operator') === '~=') {
                        $elmt.hide();
                    } else {
                        $elmt.show();
                    }
                }
            });

            $tr.find('td:nth-child(3)').empty();
            $tr.find('td:nth-child(3)').append(inputWrap);

            for(var i = 0, item; i < itemAmount; i++){
                do {
                    if(i > 0) {
                        inputWrap.append($('<span class="separator">至</span>'));
                    }
                    if(dataRange) {
                        item = $('#widget-search-select-tmpl').tmpl({
                            column: dataRange
                        });
                        break;
                    }
                    else if(dataType === 'date') {
                        item = $('<input>');
                        !function(item, $tr, i){
                            setTimeout(function(){
                                item.datebox({
                                    required: true,
                                    missingMessage: "请完善信息",
                                    formatter: function (date) {
                                        var y = date.getFullYear(),
                                            m = date.getMonth() + 1,
                                            d = date.getDate();
                                        return y + "年" + (m < 10 ? ("0" + m) : m) + "月" + (d < 10 ? ("0" + d) : d) + "日";
                                    },
                                    onSelect: function(date){
                                        var y = date.getFullYear(),
                                            m = date.getMonth() + 1,
                                            d = date.getDate(),
                                            value = y + "年" + (m < 10 ? ("0" + m) : m) + "月" + (d < 10 ? ("0" + d) : d) + "日";

                                        $tr.data('value' + i, value);
                                        validate($tr);
                                    }
                                });
                            }, 0);
                        }(item, $tr, i);
                        break;
                    }
                    else if(dataType === 'string' || dataType === 'number' || dataType === 'phone') {
                        item = $('<input c_index="' + i + '" placeholder="请输入">');
                    }
                } while (0);

                inputWrap.append($('<div c_index="' + i + '" class="align-wrap' + (itemAmount > 1 ? ' double' : '') + '">').append(item));

                setTimeout(function(){
                    $tr.find('> td:nth-child(3) .align-wrap:eq(0) > input, > td:nth-child(3) .align-wrap:eq(0) .textbox-text').focus();
                }, 0);
            }
        }

        searchFrame.delegate('tr > td:nth-child(3) .align-wrap > input', 'input', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .align-wrap > input', 'propertychange', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .align-wrap > input', 'paste', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .align-wrap > input', 'cut', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .align-wrap > input', 'blur', function(){
            tmp($(this));
        });

        /* range */
        searchFrame.delegate('tr > td:nth-child(3) .dropdown-menu li', 'click', function(){
            tmp($(this));
        });

        /* date */
        searchFrame.delegate('tr > td:nth-child(3) .textbox-text', 'input', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .textbox-text', 'propertychange', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .textbox-text', 'paste', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .textbox-text', 'cut', function(){
            tmp($(this));
        });
        searchFrame.delegate('tr > td:nth-child(3) .textbox-text', 'blur', function(){
            tmp($(this));
        });

        function tmp($this) {
            var $tr = $this.parents('tr');
            if($this.is('li')) {
                var alignWrap = $this.parents('.align-wrap');
                $tr.data('value' + alignWrap.attr('c_index'), $this.find('a').html());
            } else if($this.is('input')) {
                var index = $this.attr('c_index') !== undefined ? $this.attr('c_index') : $this.parents('.align-wrap').attr('c_index');
                $tr.data('value' + index, $this.val());
            }
            validate($tr);
        }

        function validate($tr){
            var fieldObj = $tr.data('fieldObj'),
                dataType = fieldObj.c_dataType,
                dataRange = fieldObj.c_range,
                regexp = fieldObj.c_regexp,
                max = fieldObj.c_lengthMax || 300,
                min = fieldObj.c_lengthMin || 1,
                itemAmount = $tr.data('operator') === '~' ? 2 : 1,
                errView = $tr.find('> td:nth-child(3) .fix-wrap'),
                flag = true,
                errType = '';

            /* 默认正则表达式 */
            if(!regexp) {
                if(dataType === 'number') {
                    regexp = /^\d+(\.\d+)?$/;
                } else if(dataType === 'date') {
                    regexp = /^\d{4}年\d{1,2}月\d{1,2}日$/;
                }
            }

            for(var i = 0, value = [], length = itemAmount; i < length; i++){
                value[i] = $tr.data('value' + i);
                if((min !== null && value[i].length < min) || (max !== null && value[i].length > max)) {
                    flag = false;
                    errType = '长度在' + min + '至' + max + '之间';
                } else if(regexp && !regexp.test(value[i])) {
                    flag = false;
                    errType = '请输入正确的格式';
                }
            }

            if(flag && itemAmount > 1) {
                if(dataType === 'number') {
                    if(parseFloat(value[0]) >= parseFloat(value[1])) {
                        flag = false;
                        errType = '范围错误';
                    }
                } else if(dataType === 'date') {
                    var d1 = /^(\d{4})年(\d{1,2})月(\d{1,2})日$/.exec(value[0]),
                        d2 = /^(\d{4})年(\d{1,2})月(\d{1,2})日$/.exec(value[1]),
                        i = 1, domainIndex = 1, flag2;

                    while(i < 4) {
                        if(domainIndex === i && parseFloat(d1[i]) < parseFloat(d2[i])) {
                            flag2 = true;
                            break;
                        }
                        if(parseFloat(d1[i]) <= parseFloat(d2[i])) {
                            domainIndex ++;
                        }
                        i ++;
                    }

                    if(!flag2) {
                        flag = false;
                        errType = '范围错误';
                    }
                }
            }

            if(flag) {
                errView.removeClass('validate-err');
            } else {
                errView.addClass('validate-err');
            }
            return flag;
        }

        searchFrame.delegate('tr > td .dropdown-menu > li', 'click', function(){
            $(this).parent().parent().find('.txt').html($(this).find('a').html());
        });

        searchFrame.delegate('tr > td:nth-child(1) .dropdown-menu > li', 'click', function(){
            $(this).parents('tr').data('fieldObj', search[$(this).index()]);
            initRow($(this).parents('tr'));
            for(var i = 0, lis = $(this).parents('tr').find('td:nth-child(2) .dropdown-menu li'), length = lis.length; i < length; i++){
                var $li = $(lis[i]);
                if($li.css('display') !== 'none') {
                    $li.click();
                    break;
                }
            }
        });

        searchFrame.delegate('tr > td:nth-child(2) .dropdown-menu > li', 'click', function(){
            var parent = $(this).parents('tr');

            parent.data('operator', $(this).attr('operator'));
            initRow($(this).parents('tr'));
        });

        searchFrame.delegate('tr > td:nth-child(4) .dropdown-menu > li', 'click', function(){
            $(this).parents('tr').data('multi', $(this).attr('operator'));
        });

        searchFrame.delegate('tr > td:nth-child(4) .close', 'click', function(){
            $(this).parents('tr').remove();
            if(!searchFrame.find('tr').length) {
                searchFrame.find('.empty-tip').show();
                api.dispatch('search', api.getFilter());
            }
        });

        api.getFilter = function(){
            var filter = [];
            searchFrame.find('tr').each(function(){
                var $this = $(this),
                    type = $this.data('fieldObj').c_dataType,
                    value0 = $this.data('value0'), value1 = $this.data('value1');

                if(type === 'date') {
                    value0 = value0.replace(/年|月/g, '-').replace(/日/g, '');
                    value1 = value1.replace(/年|月/g, '-').replace(/日/g, '');
                }

                if((type === "string" || type === "date") && $this.data('operator') !== "~=") {
                    value0 = "'" + value0 +"'";
                    value1 = "'" + value1 +"'";
                }

                filter.push({
                    field: $this.data('fieldObj').field,
                    operator: $this.data('operator'),
                    multi: $this.data('multi'),
                    value0: value0,
                    value1: value1
                });
            });

            return filter;
        };

        return api;
    }
    }
};

/* ========================================================================
 * Bootstrap: dropdown.js v3.0.2
 * http://getbootstrap.com/javascript/#dropdowns
 * ========================================================================
 * Copyright 2013 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ======================================================================== */

!function ($) { "use strict";

    // DROPDOWN CLASS DEFINITION
    // =========================

    var backdrop = '.dropdown-backdrop'
    var toggle   = '[data-toggle=dropdown]'
    var Dropdown = function (element) {
        var $el = $(element).on('click.bs.dropdown', this.toggle)
    }

    Dropdown.prototype.toggle = function (e) {
        var $this = $(this)

        if ($this.is('.disabled, :disabled')) return

        var $parent  = getParent($this)
        var isActive = $parent.hasClass('open')

        clearMenus()

        if (!isActive) {
            if ('ontouchstart' in document.documentElement && !$parent.closest('.navbar-nav').length) {
                // if mobile we we use a backdrop because click events don't delegate
                $('<div class="dropdown-backdrop"/>').insertAfter($(this)).on('click', clearMenus)
            }

            $parent.trigger(e = $.Event('show.bs.dropdown'))

            if (e.isDefaultPrevented()) return

            $parent
                .toggleClass('open')
                .trigger('shown.bs.dropdown')

            $this.focus()
        }

        return false
    }

    Dropdown.prototype.keydown = function (e) {
        if (!/(38|40|27)/.test(e.keyCode)) return

        var $this = $(this)

        e.preventDefault()
        e.stopPropagation()

        if ($this.is('.disabled, :disabled')) return

        var $parent  = getParent($this)
        var isActive = $parent.hasClass('open')

        if (!isActive || (isActive && e.keyCode == 27)) {
            if (e.which == 27) $parent.find(toggle).focus()
            return $this.click()
        }

        var $items = $('[role=menu] li:not(.divider):visible a', $parent)

        if (!$items.length) return

        var index = $items.index($items.filter(':focus'))

        if (e.keyCode == 38 && index > 0)                 index--                        // up
        if (e.keyCode == 40 && index < $items.length - 1) index++                        // down
        if (!~index)                                      index=0

        $items.eq(index).focus()
    }

    function clearMenus() {
        $(backdrop).remove()
        $(toggle).each(function (e) {
            var $parent = getParent($(this))
            if (!$parent.hasClass('open')) return
            $parent.trigger(e = $.Event('hide.bs.dropdown'))
            if (e.isDefaultPrevented()) return
            $parent.removeClass('open').trigger('hidden.bs.dropdown')
        })
    }

    function getParent($this) {
        var selector = $this.attr('data-target')

        if (!selector) {
            selector = $this.attr('href')
            selector = selector && /#/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') //strip for ie7
        }

        var $parent = selector && $(selector)

        return $parent && $parent.length ? $parent : $this.parent()
    }


    // DROPDOWN PLUGIN DEFINITION
    // ==========================

    var old = $.fn.dropdown

    $.fn.dropdown = function (option) {
        return this.each(function () {
            var $this = $(this)
            var data  = $this.data('dropdown')

            if (!data) $this.data('dropdown', (data = new Dropdown(this)))
            if (typeof option == 'string') data[option].call($this)
        })
    }

    $.fn.dropdown.Constructor = Dropdown


    // DROPDOWN NO CONFLICT
    // ====================

    $.fn.dropdown.noConflict = function () {
        $.fn.dropdown = old
        return this
    }


    // APPLY TO STANDARD DROPDOWN ELEMENTS
    // ===================================

    $(document)
        .on('click.bs.dropdown.data-api', clearMenus)
        .on('click.bs.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
        .on('click.bs.dropdown.data-api'  , toggle, Dropdown.prototype.toggle)
        .on('keydown.bs.dropdown.data-api', toggle + ', [role=menu]' , Dropdown.prototype.keydown)

}(jQuery);