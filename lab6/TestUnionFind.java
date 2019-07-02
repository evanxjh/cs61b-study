import edu.princeton.cs.algs4.UF;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author EvenHsia
 * @ClassName TestUnionFind.java
 * @Description test for UnionFind
 * @createTime 2019-07-02- 21:29
 */
public class TestUnionFind {
    UnionFind Uf=new UnionFind(6);
    @Test
    public void sometest(){
        Uf.union(0,1);
        Uf.union(2,3);
        Uf.union(1,2);
        assertEquals(3,Uf.find(0));

        assertEquals(-4,Uf.parent(3));
        assertEquals(4, Uf.sizeOf(0));
        assertEquals(4, Uf.sizeOf(1));
        assertEquals(4, Uf.sizeOf(2));
        assertEquals(4, Uf.sizeOf(3));
        assertTrue(Uf.connected(2,0));
    }
}
