import static org.junit.Assert.*;

import org.junit.Test;
/**
 * @program: lab3
 * @description: test for HS
 * @author: EvenHsia
 * @create: 2019-06-27 20:53
 **/

public class FlikTest {
    @Test
    public void test(){
        assertTrue("127 not same",Flik.isSameNumber(127,127));
        assertTrue("128 not same",Flik.isSameNumber(128,128));
    }
}
