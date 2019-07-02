package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     * Create new array with capacity elements.
     * first, last, and fillCount should all be set to 0.
     */
    public ArrayRingBuffer(int capacity) {
        rb=(T[]) new Object[capacity];
        last=0;
        first=0;
        fillCount=0;
    }

    /**
     * return the capacity of the ring buffer
     */
    @Override
    public int capacity(){
        return rb.length;
    }
    /**
     * return the fillcount of the ring buffer
     */
    @Override
    public int fillCount(){
        return fillCount;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (!isFull()){
            rb[last]=x;
            fillCount+=1;
            last+=1;
            if (last==capacity()) last=0;
        }else throw new RuntimeException("Ring buffer overflow");
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty())throw new RuntimeException("Ring buffer underflow");
        T x=rb[first];
        rb[first]=null;
        fillCount+=-1;
        first+=1;
        if (first==capacity()) first=0;
        return x;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) throw new RuntimeException("Ring buffer underflow");
        return rb[first];
    }

    @Override
    public boolean haselement(T x){
        int pos=first;
        int num=0;
        while(num<fillCount){
            if (rb[pos]==x) return true;
            if (pos==capacity()) pos=0;
            num+=1;
        }
       // use iterator to do haselement method
        /*for (T z:this){
            if (z==x) return true;
        }*/
        return false;
    }

    /**
     * the iterator of ArrayRingBuffer
     */
    @Override
    public Iterator<T> iterator(){
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T>{
        private int wizPos;
        private int count;
        public ArrayRingBufferIterator(){
            wizPos=first;
            count=0;
        }
        @Override
        public boolean hasNext(){
            return count<fillCount();
        }

        @Override
        public T next(){
            T returnItem=rb[wizPos];
            wizPos=(wizPos+1) % capacity();
            count+=1;
            return returnItem;
        }
    }
    @Override
    public boolean equals(Object o){
        if  (o==null){return false;}
        if (this==o){return true;}
        if (this.getClass()!=o.getClass()){return false;}
        ArrayRingBuffer<T> other=(ArrayRingBuffer<T>) o;
        if (this.fillCount() !=other.fillCount()){return false;}
        Iterator<T> thisIterator=this.iterator();
        Iterator<T> otherIteator=other.iterator();
        while (thisIterator.hasNext() && otherIteator.hasNext()){
            if (thisIterator.next()!=otherIteator.next()){return false;}
        }
        return true;
    }
    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
