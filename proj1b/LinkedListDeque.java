/**
 * @program: proj1a
 * @description: Lists
 * @author: EvenHsia
 * @create: 2019-06-27 21:16
 **/
public class LinkedListDeque<T> implements Deque<T>{

    /** the inner part of LinkedListDeque  */
    private class StuffNode{
        public StuffNode prev;
        public StuffNode next;
        public T item;

        /** constructor for a StuffNode */
        public StuffNode(T i){
            item=i;
            prev=null;
            next=null;
        }
    }

    private StuffNode cirSentinel;
    private int size;

   /** constructor for an empty LinkedListDeque */
    public LinkedListDeque(){
        cirSentinel=new StuffNode(null);
        cirSentinel.next=cirSentinel;
        cirSentinel.prev=cirSentinel;
        size=0;
    }

    /** constructor for a deep copy of another LinkedListDeque */
    public LinkedListDeque(LinkedListDeque other){
        cirSentinel=new StuffNode(null);
        cirSentinel.next=cirSentinel;
        cirSentinel.prev=cirSentinel;
        size=0;
        for (int i=0;i<other.size();i++){
            addLast((T) other.get(i));                                /**   必须把other的类型转换为T     */
        }

    }

    /**  add a new item to the front of the deque*/
    @Override
    public void addFirst(T item){
        StuffNode x=new StuffNode(item);
        x.next=cirSentinel.next;
        cirSentinel.next.prev=x;
        cirSentinel.next=x;
        x.prev=cirSentinel;
        size+=1;
    }

    /**  add a new item to the back of the deque */
    @Override
    public void addLast(T item){
        StuffNode x=new StuffNode(item);
        x.prev=cirSentinel.prev;
        cirSentinel.prev.next=x;
        cirSentinel.prev=x;
        x.next=cirSentinel;
        size+=1;
    }

    /** judge if the deque is empty, which has been implemented in Deque */
//    public boolean isEmpty(){
//        return size==0;
//    }

    /** return the number of items in the deque */
    @Override
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque(){
        StuffNode p=cirSentinel.next;
        while (p!=cirSentinel){
            System.out.print(p.item);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst(){
        if (isEmpty()){
            return null;
        }
        T first=cirSentinel.next.item;
        cirSentinel.next.next.prev=cirSentinel;
        cirSentinel.next=cirSentinel.next.next;
        size+=-1;
        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        T last=cirSentinel.prev.item;
        cirSentinel.prev.prev.next=cirSentinel;
        cirSentinel.prev=cirSentinel.prev.prev;
        size+=-1;
        return last;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    @Override
    public T get(int index){
        if (index+1>size){
            return null;
        }
        int i=0;
        StuffNode p=cirSentinel;
        while (i<index){
            p=p.next;
            i++;
        }
        return p.item;
    }

    /** Get the item with the recursion  */
    public T getRecursive(int index){
        if (index+1>size){
            return null;
        }
        StuffNode cur=cirSentinel;
        return getHelper(index,cur);
    }

    public T getHelper(int index,StuffNode p){
        if (index==0){
            return p.next.item;
        }
        return getHelper(index-1,p.next);
    }
}
