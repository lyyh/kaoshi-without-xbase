package sei.tk.service.dao.model.vo.robot;

import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkMkpaperrule;


/**
 * Created by Administrator on 2016/4/22.
 */
public class Robotmk {
    private TkMkpaper tkMkpaper;
    private TkMkpaperrule[] Mkpaperrules;
//    private Short mkpaperScore;
//
//    public Short getMkpaperScore() {
//        return mkpaperScore;
//    }
//
//    public void setMkpaperScore(Short mkpaperScore) {
//        this.mkpaperScore = mkpaperScore;
//    }

    public void setTkMkpaper(TkMkpaper tkMkpaper) {
        this.tkMkpaper = tkMkpaper;
    }
    public TkMkpaper getTkMkpaper() {
        return tkMkpaper;
    }

    public TkMkpaperrule[] getTkMkpaperrules() {
        return Mkpaperrules;
    }

    public void setTkMkpaperrules(TkMkpaperrule[] tkMkpaperrules) {
        this.Mkpaperrules = tkMkpaperrules;
    }
}
