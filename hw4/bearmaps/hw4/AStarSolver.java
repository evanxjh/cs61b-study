package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EvenHsia
 * @ClassName AStarSolver.java
 * @Description shortest path using A* method
 * @createTime 2019-07-17- 15:28
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private SolverOutcome outcome;
    private double solutionweight;
    private LinkedList<Vertex> solution=new LinkedList<>();
    private double timespent;
    private int numstatesexplored;
    private final double INF =Double.POSITIVE_INFINITY;                    //正无穷
    public AStarSolver(AStarGraph<Vertex> input,Vertex start,Vertex end,double timeout){
        Map<Vertex,Vertex> edgeto=new HashMap<>();                         //存储Graph中的黑线，即最短路径edge
        Map<Vertex,Double> distFromStart=new HashMap<>();
        ArrayHeapMinPQ<Vertex> pq=new ArrayHeapMinPQ<>();

        Stopwatch sw=new Stopwatch();
        distFromStart.put(start,0.0);
        pq.add(start,0+input.estimatedDistanceToGoal(start,end));  //插入source vertex
        while (sw.elapsedTime()<=timeout && !pq.isempty()){                //不超时且pq非空
            // check if pq's smallest item is end
            if (pq.getSmallest().equals(end)){
                outcome=SolverOutcome.SOLVED;
                solutionweight=distFromStart.get(end);
                //Add vertex to solution()
                Vertex curVertex=pq.getSmallest();
                solution.addFirst(curVertex);
                while (!curVertex.equals(start)){
                    curVertex = edgeto.get(curVertex);
                    solution.addFirst(curVertex);
                }
                timespent=sw.elapsedTime();
                return;
            }
            Vertex toremove=pq.removeSmallest();                                  //移除最小元素
            numstatesexplored+=1;
            // relax all edges outgoing from p
            List<WeightedEdge<Vertex>> neighboredges=input.neighbors(toremove);
            for (WeightedEdge<Vertex> edge:neighboredges){
                Vertex p= edge.from();                                     //p为当前edge的from
                Vertex q= edge.to();                                       //q为当前edge的to
                double w= edge.weight();
                if (!distFromStart.containsKey(q)){                        //如果是新的节点，则默认距离为无穷大
                    distFromStart.put(q,INF);
                }
                if (distFromStart.get(p)+w<distFromStart.get(q)){
                    distFromStart.put(q,distFromStart.get(p)+w);           //更新q节点的距离
                    edgeto.put(q,p);                                       //将最短路径添加，并且由目的地指向起始点
                    if (pq.contains(q)){
                        pq.changePriority(q,distFromStart.get(q)+input.estimatedDistanceToGoal(q,end));
                    } else{
                        pq.add(q,distFromStart.get(q)+input.estimatedDistanceToGoal(q,end));
                    }
                }
            }
        }
        timespent=sw.elapsedTime();
        solution=new LinkedList<>();
        solutionweight=0;
        if (timespent>timeout) {                                          //两种可能：超时和未解决(即pq为空)
            outcome=SolverOutcome.TIMEOUT;
        } else {
            outcome=SolverOutcome.UNSOLVABLE;
        }
    }

    /**
     * return one of SOLVED,TIMEOUT and UNSOLVED
     */
    @Override
    public SolverOutcome outcome(){
        return  outcome;
    }

    /**
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVED
     */
    @Override
    public List<Vertex> solution(){
        return solution;
    }

    /**
     * The total weight of the given solution,
     * Should return 0 if result was TIMEOUT or UNSOLVED
     */
    @Override
    public double solutionWeight(){
        return solutionweight;
    }

    /**
     * The total number of priority queue dequeue operations
     */
    @Override
    public int numStatesExplored(){
        return numstatesexplored;
    }

    /**
     * The total time spent in seconds by the constructor.
     */
    @Override
    public double explorationTime(){
        return timespent;
    }
}
