package sei.tk;

import sei.tk.util.LittleUtil;

/**
 * Created by liuruijie on 2016/5/6.
 */
public class Test {
    public static void main(String[] args){
        String html="<p>lalalala<br><input type='text'></p>";
        System.out.println(LittleUtil.removeOuterTagP(html));
    }
}
