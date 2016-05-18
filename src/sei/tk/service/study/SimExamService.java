package sei.tk.service.study;

/**
 * 模拟考试接口类
 * Created by liuyanhao on 2016/3/25 0025.
 */
public interface SimExamService {
    //通过学生id得到模拟考试testpaperId
    public Long getTestpaperId(Long ppassportId);
}
