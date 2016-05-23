package sei.tk.service.dao.model.vo.robot;

import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkMkpaperrule;


/**
 * Created by Administrator on 2016/4/22.
 */
public class Robotmk {
    private TkMkpaper tkMkpaper;
    private TkMkpaperrule[] Mkpaperrules;

    public TkMkpaper getTkMkpaper() {
        return tkMkpaper;
    }

    public void setTkMkpaper(TkMkpaper tkMkpaper) {
        this.tkMkpaper = tkMkpaper;
    }

    public TkMkpaperrule[] getTkMkpaperrules() {
        return Mkpaperrules;
    }

    public void setTkMkpaperrules(TkMkpaperrule[] tkMkpaperrules) {
        this.Mkpaperrules = tkMkpaperrules;
    }
}
