/**
 * @author EvenHsia
 * @ClassName Deque.java
 * @Description interface of Deque
 * @createTime 2019-06-30- 16:45
 */
public interface Deque<T>{
    public void addFirst(T item);
    public void addLast(T item);
    public boolean isEmpty();
    public int size();
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public void printDeque();
}
