package sei.tk.util;

import sei.tk.service.dao.model.TkQuetype;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkTestsubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/16.
 */
public class TkClassifyUtil {
    //讲题目按题型归类，然后再分别打乱顺序
    public static void subjectClassify(List<TkSubject> subjectList,List<TkQuetype> quetypeList,List<TkTestsubject> tkTestsubjectList){
        //List<TkSubject> finalSubjectList=new ArrayList<>();
        List<TkSubject>[] allKindsSubjects=new List[quetypeList.size()];
        for(int i=0;i<allKindsSubjects.length;i++){
            allKindsSubjects[i]=new ArrayList<>();
            for(int j=0;j<subjectList.size();j++){
                if(subjectList.get(j).getQuetypeId()==quetypeList.get(i).getQuetypeId()){
                    allKindsSubjects[i].add(subjectList.get(j));
                    subjectList.remove(subjectList.get(j));
                    j--;
                }
            }
        }
        //打乱顺序
        for(List<TkSubject> subjects:allKindsSubjects) {
            TkSubject[] temp= (TkSubject[]) RandomUtil.unOrder(subjects.toArray(new TkSubject[subjects.size()]));
            subjects.clear();
            subjects.addAll(Arrays.asList(temp));
        }
        //整理好的列表赋值给传入列表
        for(List<TkSubject> subjects:allKindsSubjects){
            for(TkSubject subject:subjects){
                subjectList.add(subject);
            }
        }
        for(int i=0;i<subjectList.size();i++){
            for(int j=0;j<tkTestsubjectList.size();j++){
                if(subjectList.get(i).getSubjectId()==tkTestsubjectList.get(j).getSubjectId()){
                    TkTestsubject temp=tkTestsubjectList.get(i);
                    tkTestsubjectList.set(i,tkTestsubjectList.get(j));
                    tkTestsubjectList.set(j,temp);
                }
            }
        }
    }
}
