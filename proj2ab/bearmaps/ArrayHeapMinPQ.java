package bearmaps;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * @author EvenHsia
 * @ClassName ArrayHeapMinPQ.java
 * @Description the ArrayHeap which implements MinPq
 * @createTime 2019-07-10- 16:03
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{

    private int size;                    //number of items on priority queue
    private Node[] pq;                   //store items at indices 1 to n

    /**
     * constructor of ArrayHeapMinPQ
     */
    public ArrayHeapMinPQ(int initcapacity){
        pq= (Node[]) new Object[initcapacity+1];             //泛型数组必须这样初始化,index从1开始
        size=0;
    }
    public ArrayHeapMinPQ(){
        this(1);
    }





    /**
     * the Node combining item and priority
     */
    private class Node implements Comparable<Node>{
        private T item;
        private double priority;
        Node(T item,double priority){
            this.item=item;
            this.priority=priority;
        }
        public T getItem(){
            return item;
        }
        public double getPriority(){
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
        @Override
        public int compareTo(Node other){
            if (other==null) {
                return -1;
            }
            return Double.compare(this.getPriority(),other.getPriority());
        }
        @Override
        public boolean equals(Object o){
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((Node) o).getItem().equals(this.getItem());
            }
        }
        @Override
        public int hashCode(){
            return this.getItem().hashCode();
        }
    }










    /**
     * add items into the PQ
     * Throw an IllegalArgumentException if item is already present.
     * Assume never add null
     */
    @Override
    public void add(T item,double priority){

    }

    /**
     * initialize the array to a priority queue
     */
    public ArrayHeapMinPQ(Node[] nodes){
        size=nodes.length;
        pq=(Node[]) new Object[size+1];
        for (int i=0;i<size;i++)
            pq[i+1]=nodes[i];
        for (int k=size/2;k>0;k--)
            sink(k);
        assert isMinHeap();
    }
    /**
     *  return true if the PQ contains the given item
     */
    @Override
    public boolean contains(T item){
        return false;
    }

    /**
     * Returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     */
    @Override
    public T getSmallest(){
        if (isempty()){
            throw new NoSuchElementException();
        }
        return pq[1].getItem();
    }

    /**
     * Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     */
    @Override
    public T removeSmallest(){
        return null;
    }

    /**
     * Returns the number of items in the PQ.
     */
    @Override
    public int size(){
        return this.size;
    }

    /**
     *  return true if this priority queue is empty
     */
    public boolean isempty(){
        return size==0;
    }

    /**
     * Changes the priority of the given item. Throws NoSuchElementException if the item doesn't exist.
     */

    @Override
    public void changePriority(T item,double priority){

    }

    /**********************************************************
     * help method to restore the heap invariant
     **********************************************************/


    /**********************************************************
     * Helper method for compare and swaps
     ***********************************************************/

    private boolean isMinHeap(){
        return isMinHeap(1);
    }
    private boolean isMinHeap(int k){
        if (k>size) return true;
        int left=2*k;
        int right=2*k+1;
        if (left<=size && greater(k,left)) return false;
        if (right<=size && greater(k,right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }
}
