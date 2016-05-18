package sei.tk.service.dao.model.vo.paper;

import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkMkpaperrule;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestsubject;

/**
 * Created by ywl on 2016/5/4.
 */
public class PaperUp {
    private TkMkpaper tkMkpaper;
    private TkTestsubject tkTestsubject;
    private TkTestpaper tkTestpaper;

    public TkMkpaper getTkMkpaper() {
        return tkMkpaper;
    }

    public void setTkMkpaper(TkMkpaper tkMkpaper) {
        this.tkMkpaper = tkMkpaper;
    }

    public TkTestsubject getTkTestsubject() {
        return tkTestsubject;
    }

    public void setTkTestsubject(TkTestsubject tkTestsubject) {
        this.tkTestsubject = tkTestsubject;
    }

    public TkTestpaper getTkTestpaper() {
        return tkTestpaper;
    }

    public void setTkTestpaper(TkTestpaper tkTestpaper) {
        this.tkTestpaper = tkTestpaper;
    }
}
