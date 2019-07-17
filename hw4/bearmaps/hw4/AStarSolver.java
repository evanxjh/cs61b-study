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
    private final double INF =Double.POSITIVE_INFINITY;                    //������
    public AStarSolver(AStarGraph<Vertex> input,Vertex start,Vertex end,double timeout){
        Map<Vertex,Vertex> edgeto=new HashMap<>();                         //�洢Graph�еĺ��ߣ������·��edge
        Map<Vertex,Double> distFromStart=new HashMap<>();
        ArrayHeapMinPQ<Vertex> pq=new ArrayHeapMinPQ<>();

        Stopwatch sw=new Stopwatch();
        distFromStart.put(start,0.0);
        pq.add(start,0+input.estimatedDistanceToGoal(start,end));  //����source vertex
        while (sw.elapsedTime()<=timeout && !pq.isempty()){                //����ʱ��pq�ǿ�
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
            Vertex toremove=pq.removeSmallest();                                  //�Ƴ���СԪ��
            numstatesexplored+=1;
            // relax all edges outgoing from p
            List<WeightedEdge<Vertex>> neighboredges=input.neighbors(toremove);
            for (WeightedEdge<Vertex> edge:neighboredges){
                Vertex p= edge.from();                                     //pΪ��ǰedge��from
                Vertex q= edge.to();                                       //qΪ��ǰedge��to
                double w= edge.weight();
                if (!distFromStart.containsKey(q)){                        //������µĽڵ㣬��Ĭ�Ͼ���Ϊ�����
                    distFromStart.put(q,INF);
                }
                if (distFromStart.get(p)+w<distFromStart.get(q)){
                    distFromStart.put(q,distFromStart.get(p)+w);           //����q�ڵ�ľ���
                    edgeto.put(q,p);                                       //�����·����ӣ�������Ŀ�ĵ�ָ����ʼ��
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
        if (timespent>timeout) {                                          //���ֿ��ܣ���ʱ��δ���(��pqΪ��)
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
