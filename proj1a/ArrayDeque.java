/**
 * @program: proj1a
 * @description: Array
 * @author: EvenHsia
 * @create: 2019-06-27 21:16
 **/
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    /**   head 中有元素 */
    private int head;
    /**  tail 中没有元素，是最后一个有元素的后面一个 */
    private int tail;
    public static final int Defaultsize=100;
    /** create an empty ArrayDeque with given size */
    public ArrayDeque(int Arrsize){
        items=(T[]) new Object[Arrsize];
        size=0;
        head= (int) Math.floor(Arrsize/2);
        tail=head+1;
    }

    /** create an ArrayDeque with default size */
    public ArrayDeque(){
        items=(T[]) new Object[Defaultsize];
        size=0;
        head=(int) Math.floor(Defaultsize/2);
        tail=head+1;
    }

    /** create a deep copy of another ArrayDeque */
    public ArrayDeque(ArrayDeque other){

    }

    /** make a deep copy of the given ArrayDeque  */
    public void arraycopy(T[] src,T[] dst){
        if (tail>head){
            System.arraycopy(src,head,dst,0,size);           //去除原数组头部的空格
        }else{
            System.arraycopy(src,head,dst,0,items.length-head);//不需要+1，因为items.length本来就比数组末位大1
            System.arraycopy(src,0,dst,items.length-head,tail);
        }
        head=0;
        tail=size;
    }
    /**  resize the array to the target capacity*/
    private void resize(int capacity){
        T[] a= (T[]) new Object[capacity];
        arraycopy(items,a);
        items=a;
    }

    /**  add a new item to the front of the deque*/
    public void addFirst(T val){
        if (size>Math.floor(items.length/2)){
            resize(2*size);
        }
        if (val==null){
            throw new NullPointerException();
        }
        head-=1;
        if (head<0){
            head+=items.length;
        }
        items[head]=val;
        size+=1;
    }

    /**  add a new item to the back of the deque */
    public void addLast(T val){
        if (size>Math.floor(items.length/2)){
            resize(2*size);
        }
        if (val==null){
            throw new NullPointerException();
        }
        items[tail]=val;
        tail+=1;
        if (tail>=items.length){
            tail=0;
        }
        size+=1;
    }

    /** judge if the deque is empty  */
    public boolean isEmpty(){
        return size==0;
    }

    /** return the number of items in the deque */
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        resize(items.length);                       //数组重新排列，从零号位置开始
        for (int i=0;i<size;i++){
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst(){
        if (size<Math.floor(items.length/4)){
            resize( (int) Math.floor(items.length/2));
        }
        T first=items[head];
        if (first==null){
            return null;
        }
        items[head]=null;
        head+=1;
        if (head>=items.length) {
            head = 0;
        }
        size-=1;
        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast(){
        if (size<Math.floor(items.length/4)){
            resize( (int) Math.floor(items.length/2));
        }
        T last=items[tail];
        if (last==null){
            return null;
        }
        tail-=1;
        if (tail<0){
            tail+=items.length;
        }
        items[tail]=null;
        size-=1;
        return last;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index){
        if (size==0 || index>=size){
            return null;
        }
        resize(items.length);
        return items[index-1];
    }
}
