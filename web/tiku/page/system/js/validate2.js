!function(){
    if('undefined' === typeof $) {
        throw 'Jquery is required.';
    }

    /* extend String trim method */
    if(!String.prototype.trim) {
        String.prototype.trim = function(){
            return this.replace(/(^\s+)|(\s+$)/g,'');
        }
    }
    
    var V = function(formSelector, validate){

        var hold = [];
        
        $(formSelector + ' input').each(function(index){
            var $obj = $(this);

            if(validate.hasOwnProperty($obj.attr('name'))) {
                $obj.bind('focus',function(e){
                    focus(e, $obj);
                }).bind('propertychange',function(e){
                    input(e, $obj);
                }).bind('input',function(e){
                    input(e, $obj);
                }).bind('paste',function(e){
                    input(e, $obj);
                }).bind('cut',function(e){
                    input(e, $obj);
                }).bind('blur',function(e){
                    blur(e, $obj);
                });

                hold.push({$obj: $obj});
            }
        });

        $(formSelector + ' select').each(function(){
            var $obj = $(this),
                flag = true;

            if(validate.hasOwnProperty($obj.attr('name'))) {
                $obj.bind('change', function(){

                    if(validate[$obj.attr('name')].not && validate[$obj.attr('name')].not === $obj.find('option:selected').index()) {
                        flag = false;
                    }
                    validate[$obj.attr('name')].ok = flag;
                });
            }
        });

        return {
            check: submit,
            validate: function(){
                for(var i = 0, length = hold.length; i < length; i++){
                    blur(undefined, hold[i].$obj);
                }
            }
        }

        function focus(e, $obj){
            cursorEnd($obj);
        }
        
        function blur(e ,$obj){
            $obj.val($obj.val().trim());
            if(!input(e, $obj)){
                $obj.addClass('validate-err');
            } else {
                $obj.removeClass('validate-err');
            }
        }
        
        function input(e, $obj){
            var val = $obj.val(),
                flag = true;

            if(validate[$obj.attr('name')] && (validate[$obj.attr('name')].required || (val !== '' && !validate[$obj.attr('name')].required))){
                if(validate[$obj.attr('name')].min) {
                    if(validate[$obj.attr('name')].min > val.length) {
                        flag = false;
                    }
                }
                if(validate[$obj.attr('name')].max) {
                    if(validate[$obj.attr('name')].max < val.length) {
                        flag = false;
                    }
                }
                if(validate[$obj.attr('name')].regexp) {
                    if(!val.match(validate[$obj.attr('name')].regexp)) {
                        flag = false;
                    }
                }
                if(validate[$obj.attr('name')].equalTo){
                    if($('#' + validate[$obj.attr('name')].equalTo).val() !== val) {
                        flag = false;
                    }
                }
            }
            validate[$obj.attr('name')].ok = flag;
            return flag;
        }

        function cursorEnd($obj){
            if($obj[0].createTextRange){
                var a =$obj[0].createTextRange();
                a.moveStart('character',$obj[0].value.length);
                a.collapse(true);
                a.select();
            }
        }

        function submit(){
            var flag = true;
            for(var i in validate){
                if(validate.hasOwnProperty(i) && !validate[i].ok) {
                    flag = false;
                }
            }
            return flag;
        }
    };
    
    window.V = V;
}();