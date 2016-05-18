/* extend String trim method */
if(!String.prototype.trim) {
    String.prototype.trim = function(){
        return this.replace(/(^\s+)|(\s+$)/g,'');
    }
}

function log(data){
    if(window.console) {
        console.log(data);
    }
}

function visible(obj,visible,duration,callback){
    if(visible)
        if($.support.opacity) {
            obj.stop().fadeIn(duration,callback);
        }
        else{
            obj.show();
            callback && callback();
        }
    else
    if($.support.opacity) {
        obj.stop().fadeOut(duration,callback);
    }
    else{
        obj.hide();
        callback && callback();
    }
}


var ids = [];

function getUniqueId(length){                   //注意：如果范围太小将死循环
    var id = "L" + random(length);
    while(find(id,ids))
        id = random(length);
    ids.push(id);
    return id;
}

function find(value,target){
    for(var i in target){
        if(target[i] == value)
            return true;
    }
    return false;
}

function random(length){
    var min = Math.pow(10,length - 1),
        max = min*10 - 1,
        result = Math.round(min + Math.random()*(max - min));
    return result;
}

var Lang = {
    getIndexByValue: function(value, array){
        for(var i = 0, length = array.length; i < length; i++){
            if(array[i] === value) {
                return i;
            }
        }
    },
    getItemByKeyValue: function(key, value, array){
        for(var i = 0, length = array.length; i < length; i++){
            if(array[i][key] == value) {
                return array[i];
            }
        }
    },
    snapshot: function(array){
        var array2 = [];

        for(var i = 0, length = array.length; i < length; i++){
            array2.push(array[i]);
        }

        return array2;
    },
    deepCopy: function (object) {
        var returnObject;

        if(!object) {
            return object;
        } else if(object instanceof Array) {
            returnObject = [];

            for(var i = 0, length = object.length; i < length; i++){
                returnObject.push(this.deepCopy(object[i]));
            }
        } else if(object.nodeType || object.jquery || typeof object === 'string' || typeof object === 'number' || typeof object === 'function') {
            return object;
        } else {
            returnObject = {};

            for(var i in object) {
                if(object.hasOwnProperty(i)) {
                    returnObject[i] = this.deepCopy(object[i]);
                }
            }
        }

        return returnObject;
    },
    merge:function (target, origin) {
        for (var attribute in origin) {
            if (origin.hasOwnProperty(attribute)) {
                target[attribute] = origin[attribute];
            }
        }
    },
    setAttr: function(array, attrs){
        for(var i = 0, length = array.length; i < length; i++){
            for(var attr in attrs) {
                if(attrs.hasOwnProperty(attr)) {
                    array[i][attr] = attrs[attr];
                }
            }
        }
    },
    defineClass:function defineClass(constructor, parent, properties, statics, isSingleton) {
        if (isSingleton) {
            var oldConstructor = constructor,
                instance;
            constructor = function () {
                if (instance) {
                    return instance;
                }
                oldConstructor.apply(this, arguments);
                instance = this;
            }
        }
        constructor.prototype = parent ? new parent() : {};
        Lang.merge(constructor.prototype, properties);
        Lang.merge(constructor, statics);
        constructor.prototype.constructor = constructor;
        constructor.prototype.parent = parent;
        constructor.parent = parent;
        constructor.borrow = function(methodName, context, args){
            var oldParent;

            if(typeof methodName === "object") {
                args = context;
                context = methodName;
            }
            oldParent = context.parent;
            context.parent = parent;

            if(typeof methodName === "string") {
                constructor.prototype[methodName].apply(context, args || []);
            } else {
                constructor.apply(context, args || []);
            }
            context.parent = oldParent;
        };
        return constructor;
    },
    buffer: function(fn, ms, context){
        var prevTimeStamp = new Date().getTime(),
            buffered = 0,
            timeout;

        return function(){
            var current = new Date().getTime(),
                args = arguments;

            if(current - prevTimeStamp < ms) {
                if(buffered) {
                    clearTimeout(timeout);
                }
                timeout = setTimeout(function(){
                    prevTimeStamp = current;
                    fn.apply(context, args);
                    buffered --;
                }, ms - current + prevTimeStamp);
                buffered ++;
            } else {
                prevTimeStamp = current;
                fn.apply(context, args);
            }
        }
    }
};

var toEventDispatchable = function(){
    var eventDispatchable = Lang.defineClass(function(){
        this.listener = {};
    }, Object, {
        on: function(type, listener){
            if(!(type in this.listener)) {
                this.listener[type] = [];
            }
            this.listener[type].push(listener);
        },
        off: function(type, listener){
            if(type in this.listener) {
                var listeners = this.listener[type];

                if(!listener) {
                    this.listener[type] = [];
                } else {
                    for(var i = 0, length = listeners.length; i < length; i++){
                        if(listeners[i] === listener) {
                            listeners.splice(i, 1);
                            length --;
                        }
                    }
                }
            }
        },
        dispatch: function(type, data){
            if(this.listener[type]) {
                var listeners = Lang.snapshot(this.listener[type]);
                for(var i = 0; i < listeners.length; i++){
                    listeners[i].call(this, data);
                }
            }
        }
    }, {});

    return function(childClass){
        return Lang.defineClass(childClass, eventDispatchable, {}, {});
    };
}();

var resizeDispatcher = new (toEventDispatchable(function(){
    var win = $(window),
        data = {
            w: win.width(),
            h: win.height()
        },
        that = this;

    win.bind("resize",function(){
        data = {
            w: win.width(),
            h: win.height()
        };
        that.dispatch('resize', data);
    });

    this.getData = getData;

    function getData(){
        return data;
    }
}));

function addStyle(cssText){
    var cssStyle = document.createElement('style'),
        cssTextNode = document.createTextNode(cssText);

    cssStyle.type = 'text/css';

    try {
        cssStyle.appendChild(cssTextNode);
    } catch (e) {
        cssStyle.styleSheet.cssText = cssText;
    }

    document.getElementsByTagName('head')[0].appendChild(cssStyle);
}
function childOf(child, parent){
    while(child && child !== parent && child !== document.body) {
        child = child.parentNode;
    }
    return child !== document.body;
}