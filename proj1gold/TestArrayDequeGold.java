/**
 * @program: proj1gold
 * @description: test file
 * @author: EvenHsia
 * @create: 2019-07-01 20:29
 **/
import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testarraydeque(){
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads=new ArrayDequeSolution<>();
        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            Integer numtoadd=StdRandom.uniform(10);
            if (numberBetweenZeroAndOne < 0.3) {
                sad.addLast(numtoadd);
                ads.addLast(numtoadd);
                System.out.println("addLast("+numtoadd+")");
            } else if (numberBetweenZeroAndOne<0.6){
                sad.addFirst(numtoadd);
                ads.addFirst(numtoadd);
                System.out.println("addFirst("+numtoadd+")");
            } else if (numberBetweenZeroAndOne<0.8){
                if (sad.size()==0 || ads.size()==0) {
                    System.out.println("size()="+0);
                }else {
                    Integer actual=sad.removeFirst();
                    Integer expect=ads.removeFirst();
                    assertEquals("removeFirst()",actual,expect);
                }
            } else if (numberBetweenZeroAndOne<1){
                if (sad.size()==0 || ads.size()==0) {
                    System.out.println("size()="+0);
                }else {
                    Integer actual=sad.removeLast();
                    Integer expect=ads.removeLast();
                    assertEquals("removeLast()",actual,expect);
                }
            }
        }
    }
}
