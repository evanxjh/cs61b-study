/**
 * @author EvenHsia
 * @ClassName OffByN.java
 * @Description A class for off-by-N comparators
 * @createTime 2019-06-30- 16:39
 */
public class OffByN implements CharacterComparator{
    int offNum;
    public OffByN(int N){
        offNum=N;
    }
    @Override
    public boolean equalChars(char x,char y){
        int diff=x-y;
        if (Math.abs(diff)<=offNum){
            return true;
        }else{
            return false;
        }
    }
}
