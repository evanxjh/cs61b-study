package bearmaps;


import java.util.ArrayList;
import java.util.List;

/**
 * @author EvenHsia
 * @ClassName NaivePointSet.java
 * @Description a naive linear-time solution
 * @createTime 2019-07-15- 20:51
 */
public class NaivePointSet implements PointSet{
    private List<Point> points;
    public NaivePointSet(List<Point> points){
        this.points=points;
    }
    @Override
    public Point nearest(double x,double y){
        Point targerpoint=new Point(x,y);
        Point nearestpoint=points.get(0);
        double shortestdis=Point.distance(targerpoint,nearestpoint);
        for (Point currentpoint:points){
            double currentdis=Point.distance(currentpoint,targerpoint);
            if (currentdis<shortestdis){
                shortestdis=currentdis;
                nearestpoint=currentpoint;
            }
        }
        return nearestpoint;
    }

    public static void main(String[] args){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.print("x:"+ret.getX()+"        y:"+ret.getY()); // evaluates to x=3.3 y=4.4

    }
}
