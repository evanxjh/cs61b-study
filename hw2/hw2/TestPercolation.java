package hw2;
import org.junit.Test;
/**
 * @author EvenHsia
 * @ClassName TestPercolation.java
 * @Description test for percolation
 * @createTime 2019-07-03- 21:48
 */
public class TestPercolation {
    @Test
    public void sometests(){
        Percolation g=new Percolation(3);
        for (int i=0;i<g.grid.length;i++){
            for (int j=0;j<g.grid[0].length;j++){
                System.out.print(g.grid[i][j]);
            }
            System.out.println();
        }
    }
}
