import java.util.ArrayList;
import java.util.List;


/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> bearres;
    private List<Bed> bedres;
    public BnBSolver(List<Bear> bears, List<Bed> beds) {
     /* //N^2 version
        bedres=new ArrayList<>();
        bearres=new ArrayList<>();
        for (Bear bear:bears){
            for (Bed bed:beds){
                if (bear.compareTo(bed)==0){
                    bedres.add(bed);
                    beds.remove(bed);
                    break;
                }
            }
            bearres.add(bear);
        }*/
        bearres=quicksort(bears,beds).getBears();
        bedres=quicksort(bears,beds).getBeds();
    }

    /**
     * 根据熊的pivot分段，目前熊的pivot取的第一项，可增加方法取随机项
     * 返回与熊pivot大小相等的bed，作为床pivot
     */
    public static Bed partition(List<Bear>bears,List<Bed> beds,Bear pivotbear,List<Bear>bearleft,List<Bear>bearright,List<Bed> bedleft,List<Bed> bedright){
        Bed pivotbed=beds.get(0);
        for (Bed bed:beds){
            int cmp=bed.compareTo(pivotbear);
            if (cmp<0) bedleft.add(bed);
            else if (cmp>0) bedright.add(bed);
            else pivotbed=bed;
        }
        for (Bear bear:bears){
            int cmp=bear.compareTo(pivotbed);
            if (cmp<0) bearleft.add(bear);
            else if (cmp>0) bearright.add(bear);
            else ;
        }
        return  pivotbed;
    }
    /**
     *
     * @param bearleft   熊pivot左侧的bear
     * @param pivotbear  熊的pivot
     * @param bearright  熊pivot右侧的bear
     * @param bedleft    床pivot左侧的bed
     * @param pivotbed   床的pivot
     * @param bedright   床pivot右侧的bed
     * @return           分别将bear和bed连接，并返回结合体
     */
    public BnB combine(List<Bear> bearleft,Bear pivotbear,List<Bear> bearright,List<Bed> bedleft,Bed pivotbed,List<Bed> bedright){
        BnB res=new BnB();
        for (Bear bear:bearleft){
            res.bears.add(bear);
        }
        res.bears.add(pivotbear);
        for (Bear bear:bearright){
            res.bears.add(bear);
        }
        for (Bed bed:bedleft){
            res.beds.add(bed);
        }
        res.beds.add(pivotbed);
        for (Bed bed:bedright){
            res.beds.add(bed);
        }
        return res;
    }

    /**
     *
     * @param bears    待排序的bears
     * @param beds     待排序的beds
     * @return   bears和beds的结合体
     */
    public BnB quicksort(List<Bear> bears,List<Bed> beds){
        if (bears.isEmpty() || bears.size()==1) return new BnB(bears,beds);
        List<Bear> bearleft=new ArrayList<>();
        List<Bear> bearright=new ArrayList<>();
        List<Bed> bedleft=new ArrayList<>();
        List<Bed> bedright=new ArrayList<>();
        Bear pivotbear=bears.get(0);                                  //暂时取第一个
        Bed pivotbed=partition(bears,beds,pivotbear,bearleft,bearright,bedleft,bedright);
        BnB bnbleft=quicksort(bearleft,bedleft);
        BnB bnbright=quicksort(bearright,bedright);
        return combine(bnbleft.getBears(),pivotbear,bnbright.getBears(),bnbleft.getBeds(),pivotbed,bnbright.getBeds());
    }

    /**
     * 将bears和beds结为一体，成为quicksort的返回值
     */
    private class BnB{
        private List<Bear> bears;
        private List<Bed> beds;
        BnB(List<Bear> bears,List<Bed> beds){
            this.bears=bears;
            this.beds=beds;
        }
        BnB(){
            this.bears=new ArrayList<>();
            this.beds=new ArrayList<>();
        }
        public List<Bear> getBears(){
            return this.bears;
        }
        public List<Bed> getBeds(){
            return this.beds;
        }
    }
    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return bearres;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return bedres;
    }
}
