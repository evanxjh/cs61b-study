package bearmaps;
import java.util.Comparator;
import java.util.HashMap;
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
    private Node<T>[] pq;                   //store items at indices 1 to n
    private HashMap<T,Integer> itemMap;    //Ϊ��contains��ʱ�临�Ӷ�
    /**
     * constructor of ArrayHeapMinPQ
     */
    public ArrayHeapMinPQ(int initcapacity){
        pq= new Node[initcapacity+1];             //�����������������ʼ��,index��1��ʼ
        size=0;
        itemMap=new HashMap<>();
    }
    public ArrayHeapMinPQ(){
        this(1);
    }

    /**
     * the Node combining item and priority
     */
    private class Node<T> implements Comparable<Node>{
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
        if (contains(item)) throw new IllegalArgumentException();
        if (size==pq.length-1) resize(2*pq.length);        //��0����û�õ�
        Node x=new Node(item,priority);
        pq[++size]=x;
        itemMap.put(item,size);
        swim(size);                                                 //�����ӵ����ϸ�
        assert isMinHeap();
    }

    private void resize(int capacity){
        Node[] temp=new Node[capacity];
        for (int i=1;i<=size;i++){
            temp[i]=pq[i];
        }
        pq=temp;
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
        if (isempty()){
            return false;
        }
        return itemMap.containsKey(item);
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
        if (isempty()){
            throw new NoSuchElementException();
        }
        T res=getSmallest();
        exch(1,size--);                          //������һ�������һ����������size
        sink(1);                                //�Ե�һ��Ԫ���³�
        itemMap.remove(res);                        //���Ƴ���Ԫ�ش�Hashmap��ɾ��
        pq[size+1]=null;
        return res;
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
        if (isempty()) throw new IllegalArgumentException();
        if (!contains(item)) throw new NoSuchElementException();
        int index=itemMap.get(item);
        double oldPriority=pq[index].getPriority();
        pq[index].setPriority(priority);
        if (oldPriority<priority){                       //���ȼ���߱��³���������ϸ�
            sink(index);
        }else{
            swim(index);
        }
    }

    /**********************************************************
     * help method to restore the heap invariant
     **********************************************************/
    //�ϸ�
    private void swim(int k){
        while (k>1 && greater(k/2,k)){
            exch(k/2,k);
            k=k/2;
        }
    }

    //�³�
    private void sink(int k){
        while (2*k<=size){
            int j=2*k;
            if (j<size && greater(j,j+1)) j+=1;          //ѡ�������ӽڵ��еĽ�С��
            if (!greater(k,j)) break;                       //���ڵ�������ӽڵ㶼С���˳�ѭ��
            exch(k,j);
            k=j;
        }
    }

    /**********************************************************
     * Helper method for compare and swaps
     ***********************************************************/
    private boolean greater(int i, int j){
        return pq[i].compareTo(pq[j])>0;
    }

    private void exch(int i,int j){
        Node temp=pq[i];
        pq[i]=pq[j];
        pq[j]=temp;
        itemMap.put(pq[i].getItem(),i);                  //������HashMap������Ӧ�ı�
        itemMap.put(pq[j].getItem(),j);
    }

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
