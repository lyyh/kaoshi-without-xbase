package sei.tk.service.exam.vo;

/**
 * Created by liuruijie on 2016/6/4.
 */
public class Analyse {
    private String name;//名字
    private int score;//自己的分数
    private int myRankInClass;//自己的班级排名
    private int myRankInGrade;//自己的年纪排名

    private int classRankInGrade;//班级的年纪排名
    private double classAvgScore;//班级的平均分
    private double classFailPercent;//班级的挂科率

    private int classFailNum;//挂掉的人数
    private int classPassNum;//合格到优秀之间的人数
    private int classGreatNum;//优秀的人数

    private String grad;//年级
    private String klass;//班级

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMyRankInClass() {
        return myRankInClass;
    }

    public void setMyRankInClass(int myRankInClass) {
        this.myRankInClass = myRankInClass;
    }

    public int getMyRankInGrade() {
        return myRankInGrade;
    }

    public void setMyRankInGrade(int myRankInGrade) {
        this.myRankInGrade = myRankInGrade;
    }

    public int getClassRankInGrade() {
        return classRankInGrade;
    }

    public void setClassRankInGrade(int classRankInGrade) {
        this.classRankInGrade = classRankInGrade;
    }

    public double getClassFailPercent() {
        return classFailPercent;
    }

    public void setClassFailPercent(double classFailPercent) {
        this.classFailPercent = classFailPercent;
    }

    public int getClassFailNum() {
        return classFailNum;
    }

    public void setClassFailNum(int classFailNum) {
        this.classFailNum = classFailNum;
    }

    public int getClassPassNum() {
        return classPassNum;
    }

    public void setClassPassNum(int classPassNum) {
        this.classPassNum = classPassNum;
    }

    public int getClassGreatNum() {
        return classGreatNum;
    }

    public void setClassGreatNum(int classGreatNum) {
        this.classGreatNum = classGreatNum;
    }

    public double getClassAvgScore() {
        return classAvgScore;
    }

    public void setClassAvgScore(double classAvgScore) {
        this.classAvgScore = classAvgScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
        this.grad="20"+klass.substring(3,5);
    }
}
