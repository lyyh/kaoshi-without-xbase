package sei.tk.service.labour;

/**
 * 删除人工制卷
 * Created by Administrator on 2016/3/15 0015.
 */
public interface LabourDelete {
    //根据mkpaperId删除mkpaper
    public boolean deleteMkpaper(Long testpaperId);
    //根据testpaperId删除testpaper
    public boolean deleteTestpaper(Long testpaperId);
    //根据testpaperId删除testsubject
    public boolean deleteTestsubject(Long testpaperId);
}
