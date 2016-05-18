$(document).ready(function(){
    var noselect = function(){
        var fn = {
            start: function(){
                setInterval(function(){
                    $('.no-select').attr('unselectable', 'on').attr('onselectstart', 'return false;');
                }, 500);
            }
        };

        return fn;
    }();

    noselect.start();
});