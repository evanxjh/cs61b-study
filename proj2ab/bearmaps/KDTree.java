package bearmaps;

import java.util.List;
import java.util.Comparator;
/**
 * @author EvenHsia
 * @ClassName KDTree.java
 * @Description k-dtree for proj2b
 * @createTime 2019-07-16- 12:27
 */
public class KDTree{

    private Node root;
    private static final boolean HORIZONTAl=false;      //水平为假，竖直为真
    /**
     * constructor of KDTree
     */
    public KDTree(List<Point> points){
        for (Point p:points){
            root=add(root,p,HORIZONTAl);
        }
    }

    /**
     *  add point into k-d tree
     */
    public Node add(Node x,Point point,boolean dimension){
        if (x==null) return new Node(point,dimension);            //新建节点，并换方向
        if (x.getPoint().equals(point)) return x;                 //重复则直接返回
        Comparator<Point> pointComparator=(Point i,Point j)->{
            if (dimension==HORIZONTAl){
                double diff=i.getX()-j.getX();
                return (diff<0)?-1:1;                             //等于包含在右侧
            } else{
                double diff=i.getY()-j.getY();
                return (diff<0)?-1:1;                             //等于包含在上侧
            }
        };

        if (pointComparator.compare(point,x.getPoint())<0) {
            x.left=add(x.getLeft(),point,!dimension);
        }else {
            x.right=add(x.getRight(),point,!dimension);
        }
        return x;
    }

    public Point nearest(double x,double y){
        Point goal=new Point(x,y);
        return nearest(root,goal,root.getPoint());
    }

    private Point nearest(Node x,Point goal,Point best){
        if (x==null){                                       //找到最后返回当前最佳
            return best;
        }
        double bestDist=Point.distance(best,goal);
        double currDist=Point.distance(x.getPoint(),goal);
        if (Double.compare(currDist,bestDist)<0){           //距离更小，更新节点
            best=x.getPoint();
        }
        Comparator<Point> pointComparator=(Point i,Point j)->{
            if (x.getspiltDim()==HORIZONTAl){
                double diff=i.getX()-j.getX();
                return (diff<0)?-1:1;                             //等于包含在右侧
            } else{
                double diff=i.getY()-j.getY();
                return (diff<0)?-1:1;                             //等于包含在上侧
            }
        };
        Node goodSide;
        Node badSide;
        if (pointComparator.compare(goal,x.getPoint())<0){
            goodSide=x.getLeft();
            badSide=x.getRight();
        } else {
            goodSide=x.getRight();
            badSide=x.getLeft();
        }
        best=nearest(goodSide,goal,best);
        if (isWorthSeraching(x,goal,best)){
            best=nearest(badSide,goal,best);
        }
        return best;
    }

    private boolean isWorthSeraching(Node x,Point goal,Point best){
        double bestDist=Point.distance(goal,best);
        double badSideDist;
        if (x.getspiltDim()==HORIZONTAl){
            badSideDist=Point.distance(goal,new Point(x.getPoint().getX(),goal.getY()));
        } else {
            badSideDist=Point.distance(goal,new Point(goal.getX(),x.getPoint().getY()));
        }
        return Double.compare(badSideDist,bestDist)<0;
    }
    /**
     * the Node in k-d tree,reference from lab7
     */
    private class Node{
        private Node left;
        private Node right;
        private Point point;
        private boolean spiltDim;

        public Node(Point point,boolean spiltDim){
            this.left=null;
            this.right=null;
            this.point=point;
            this.spiltDim=spiltDim;
        }
        /*
         * some methods for private parameter
         */
        public Point getPoint(){
            return this.point;
        }

        public Node getLeft(){
            return this.left;
        }

        public Node getRight(){
            return this.right;
        }

        public boolean getspiltDim(){
            return this.spiltDim;
        }
    }
}
