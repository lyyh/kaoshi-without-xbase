/**
 * Created by hukaihe on 2016/5/1.
 * This JS file is designed for some base functions such as fix width of div with
 * some percent of screen's width.
 * Before using the function here,please make sure that you have put jquery in your html file
 * and put this one after your element's position
 */

function setSize(element,widthPercent,heightPercent){
    element.width(screen.width*widthPercent);
    element.height(screen.height*heightPercent);
}
