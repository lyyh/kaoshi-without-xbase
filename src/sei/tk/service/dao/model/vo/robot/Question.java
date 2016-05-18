package sei.tk.service.dao.model.vo.robot;

import java.util.LinkedList;

/**
 * Created by kurileo on 2016/3/16.
 *
 */
public class Question {
    LinkedList<Integer> targetList;
    private int courseId;
    private Long knopointId;
    private Short quetypeId;
    private Byte levelId;
    private int amount;

    public Question(int courseId, Short quetypeId, long  knopointId, Byte levelId, int amount,LinkedList<Integer> targetList) {
        this.courseId = courseId;
        this.knopointId = knopointId;
        this.quetypeId = quetypeId;
        this.levelId = levelId;
        this.amount = amount;
        this.targetList=targetList;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getKnopointId() {
        return knopointId;
    }

    public void setKnopointId(long knopointId) {
        this.knopointId = knopointId;
    }

    public Short getQuetypeId() {
        return quetypeId;
    }

    public void setQuetypeId(Short quetypeId) {
        this.quetypeId = quetypeId;
    }

    public Byte getLevelId() {
        return levelId;
    }

    public void setLevelId(Byte levelId) {
        this.levelId = levelId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LinkedList<Integer> getTargetList() {
        return targetList;
    }

    public void setTargetList(LinkedList<Integer> targetList) {
        this.targetList = targetList;
    }
}