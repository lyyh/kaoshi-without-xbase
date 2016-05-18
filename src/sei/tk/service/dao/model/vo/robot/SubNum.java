package sei.tk.service.dao.model.vo.robot;

/**
 * Created by Administrator on 2016/3/24.
 */
public class SubNum {
    private Short quetypeId;
    private Long knopointId;
    private Byte levelId;
    private int num;
    private int numOfTotal;
    private int numOfTaken;

    public SubNum(){

    }
    public SubNum(Short quetypeId, long  knopointId, Byte levelId, int num) {
        this.quetypeId = quetypeId;
        this.knopointId = knopointId;
        this.levelId = levelId;
        this.num = num;
        this.numOfTotal=num;
        this.numOfTaken=0;
    }

    public Short getQuetypeId() {
        return quetypeId;
    }

    public void setQuetypeId(Short quetypeId) {
        this.quetypeId = quetypeId;
    }

    public long  getknopointId() {
        return knopointId;
    }

    public void setKnopointId(long  knPointd) {
        this.knopointId = knPointd;
    }

    public Byte getLevelId() {
        return levelId;
    }

    public void setLevelId(Byte levelId) {
        this.levelId = levelId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumOfTotal() {
        return numOfTotal;
    }

    public void setNumOfTotal(int numOfTotal) {
        this.numOfTotal = numOfTotal;
    }

    public int getNumOfTaken() {
        return numOfTaken;
    }

    public void setNumOfTaken(int numOfTaken) {
        this.numOfTaken = numOfTaken;
    }

    public int cmp(SubNum subNum) {
        short typeTemp = subNum.getQuetypeId();
        long  knopointIdTemp = subNum.getknopointId();
        Byte levelTemp = subNum.getLevelId();
        if (this.quetypeId > typeTemp) {
            return 1;
        } else {
            if (this.quetypeId == typeTemp) {
                if (this.knopointId > knopointIdTemp) {
                    return 1;
                } else {
                    if (this.knopointId == knopointIdTemp) {
                        if (this.levelId > levelTemp) {
                            return 1;
                        } else {
                            if (this.levelId.equals(levelTemp)) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        }
    }

    public boolean isAmountLeft() {
        return num > 0 ? true : false;
    }

    public void takeSubject() {
        this.numOfTaken++;
        this.num--;
    }


}
