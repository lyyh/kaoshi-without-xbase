package sei.tk.util.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuruijie on 2016/4/11.
 * 作用于方法上，需要方法的第一个参数为HttpSession
 * 与切面配合进行登陆校验以及权限校验
 * 校验账号是否能够调用方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
    //调用方法所需的角色，值在TkConfig类中获取
    String[] value();
}
