package bearmaps;

/**
 * @author EvenHsia
 * @ClassName ArrayHeapMinPQ.java
 * @Description the ArrayHeap which implements MinPq
 * @createTime 2019-07-10- 16:03
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{

    private int size;

    private class Node{

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
        return null;
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
     * Changes the priority of the given item. Throws NoSuchElementException if the item doesn't exist.
     */

    @Override
    public void changePriority(T item,double priority){

    }

}
