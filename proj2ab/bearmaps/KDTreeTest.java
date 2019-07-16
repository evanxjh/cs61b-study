package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r=new Random(500);
    private static KDTree buildTree(){
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        return new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void nearesttest(){
        KDTree kd=buildTree();
        Point actual=kd.nearest(0,7);
        Point excepted=new Point(1,5);
        assertEquals(excepted,actual);
    }

    /**
     * test the runtime of K-D tree
     */
    private Point randomPoint(){
        double x=r.nextDouble();
        double y=r.nextDouble();
        return new Point(x,y);
    }

    private List<Point> randomPoints(int N){
        List<Point> res=new ArrayList<>();
        for (int i=0;i<N;i++){
            res.add(randomPoint());
        }
        return  res;
    }

    private void testWithAndQueries(int pointcount,int querycount){
        List<Point> points=randomPoints(pointcount);
        NaivePointSet naiveps=new NaivePointSet(points);
        KDTree kd=new KDTree(points);
        for (int i=0;i<querycount;i++){
            Point actual=naiveps.nearest(points.get(i).getX(),points.get(i).getY());
            Point expected=kd.nearest(points.get(i).getX(),points.get(i).getY());
            assertEquals(expected,actual);
        }

    }

    @Test
    public void test1000PointsAnd200Queries(){
        int pointcount=1000;
        int queries=200;
        testWithAndQueries(1000,200);
    }

    /**
     * compare the runtime
     */
    @Test
    public void comparetheruntime(){
        int totalnum=100000;
        int totalqueries=10000;
        List<Point> comparepoints=randomPoints(totalnum);
        NaivePointSet naiveps=new NaivePointSet(comparepoints);
        KDTree kd=new KDTree(comparepoints);
        List<Point> queryList=randomPoints(totalqueries);
        long start=System.currentTimeMillis();
        for (Point p:queryList){
            naiveps.nearest(p.getX(),p.getY());
        }
        long end=System.currentTimeMillis();
        long timepast1=end-start;
        System.out.println("NaivePointSet:"+totalnum+" points and "+totalqueries+" queries elapsed "+(end-start)/1000.0+" seconds");
        start=System.currentTimeMillis();
        for (Point p:queryList){
            kd.nearest(p.getX(),p.getY());
        }
        end=System.currentTimeMillis();
        long timepast2=end-start;
        System.out.println("K-D Tree:"+totalnum+" points and "+totalqueries+" queries elapsed "+(end-start)/1000.0+" seconds");
        System.out.println("K-D Tree is "+timepast1/timepast2+" times faster than NaivePointSet");
    }

}
