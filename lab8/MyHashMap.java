import edu.princeton.cs.algs4.SequentialSearchST;

import java.util.*;

/**
 * @author EvenHsia
 * @ClassName MyHashMap.java
 * @Description my hashmap
 * @createTime 2019-07-08- 21:07
 */
public class MyHashMap<K,V> implements Map61B<K,V>{
    private static final int INITIAL_SIZE=16;
    private static final double LOADFACTOR=0.75;
    private int size;
    private int threshold;
    private double factor;
    private BucketList<K,V>[] bk;
    /**
     * three constructors of MyHashMap
     */
    MyHashMap(){
        size=0;
        factor=LOADFACTOR;
        threshold= (int) (INITIAL_SIZE*LOADFACTOR);
        bk=new BucketList[INITIAL_SIZE];
        for (int i=0;i<bk.length;i++){
            bk[i]=new BucketList<>();
        }
    }

    MyHashMap(int initialSize){
        size=0;
        threshold=(int) (initialSize*LOADFACTOR);
        factor=LOADFACTOR;
        bk=new BucketList[initialSize];
        /**
         * debug重点，必须姜bk[i]每一项初始化。
         */
        for (int i=0;i<bk.length;i++){
            bk[i]=new BucketList<>();
        }
    }

    MyHashMap(int initialSize,double loadFactor){
        size=0;
        factor=loadFactor;
        threshold=(int) (initialSize*loadFactor);
        bk=new BucketList[initialSize];
        for (int i=0;i<bk.length;i++){
            bk[i]=new BucketList<>();
        }
    }

    /**
     * HashMap中的链表
     */
    private class BucketList<K,V> implements Iterable<K>{
        private Node first;
        private int n;
        public BucketList(){
            n=0;
        }
        public BucketList(BucketList<K,V> chains){         //copy from a BucketList
            this.first=chains.first;
            this.n=chains.n;
        }
        public int size(){
            return this.n;
        }
        public boolean isEmpty(){
            return this.size()==0;
        }

        /**
         * BucketList 中的iterable，方便遍历K
         */
        @Override
        public Iterator<K> iterator(){
            return new bkiterator();
        }

        private class bkiterator implements Iterator<K>{
            Node current;
            public bkiterator(){
                current=first;
            }
            @Override
            public boolean hasNext(){
                return current!=null;
            }
            @Override
            public K next(){
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                Node cur=current;
                current=cur.next;
                return cur.key;
            }
        }


        public boolean contains(K key){
            if (key==null) {throw new IllegalArgumentException("contains() is null");}
            else {
                return get(key)!=null;
            }
        }

        public V get(K key){
            if (key==null) {throw new IllegalArgumentException("get() is null");}
            else {
                for (Node x=this.first;x!=null;x=x.next){
                    if (key.equals(x.key)){
                        return x.val;
                    }
                }
                return null;
            }
        }

        public void add(K key, V val){
            if (key==null) {throw new IllegalArgumentException("put() is null");}
            else if (val==null) {
                this.remove(key);
            }else {
                for (Node x=this.first;x!=null;x=x.next){
                    if (key.equals(x.key)){
                        x.val=val;
                        return;
                    }
                }
                this.first=new Node(key,val,first);            //addfirst
                ++this.n;
            }
        }

        /**
         * 返回被删除的键对应的值
         */
        public V remove(K key){
            if (key==null) {throw new IllegalArgumentException("remove() is null");}
            V toremove =get(key);
            if (toremove==null) return null;
            else {
                this.first=remove(first,key);
                return toremove;
            }
        }

        private Node remove(Node x,K key){
            if (x==null) return null;
            else if (key.equals(x.key)){
                --this.n;
                return x.next;
            }else {
                x.next=remove(x.next, key);
                return x;
            }
        }

        /*  don't be naked?  */
        private class Node{
            private K key;
            private V val;
            private Node next;
            public Node(K key,V val,Node next){
                this.key=key;
                this.val=val;
                this.next=next;
            }
        }
    }


    /**
    * the iterator method
    */
    @Override
    public Iterator<K>  iterator(){
        return  keySet().iterator();
    }

    public int hash(K key){
        return (key.hashCode()&0x7fffffff % this.bk.length);
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear(){
        for (int i=0;i<bk.length;i++){
            bk[i]=new BucketList<>();
        }
        size=0;

    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key){
        if (key== null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key)!=null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key){
        if (key==null)  throw new IllegalArgumentException("argument to get() is null");
        int hashcode=hash(key);
        return bk[hashcode].get(key);
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size(){
        return this.size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value){
        if (value==null) {
            remove(key);
            return;
        }
        if (this.size()>threshold) resize(2*bk.length);
        if (!bk[hash(key)].contains(key)) size++;
        bk[hash(key)].add(key,value);
    }

    public void resize(int l){
        MyHashMap<K,V> newMHMap=new MyHashMap<K,V>(l,factor);        //新的HashMap
        for (int i=0;i<bk.length;i++){
            for ( K key:bk[i] ) {
                newMHMap.put(key, bk[i].get(key));
            }
        }
        this.threshold=(int) (l*factor);
        this.size=newMHMap.size;                //应该不变
        this.bk=newMHMap.bk;                    //主要改变这个
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet(){
        Set<K> allKeys=new HashSet<K>();
        for (int i=0;i<bk.length;i++){
            for (K key:bk[i]){
                allKeys.add(key);
            }
        }
        return allKeys;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        if (key==null) throw new IllegalArgumentException("argument to remove() is null");
        int hashcode=hash(key);
        if (!bk[hashcode].contains(key)) throw new NoSuchElementException("No such key to remove");
        if (bk[hashcode].contains(key)) size--;
        return bk[hashcode].remove(key);
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value){
        if (key==null) throw new IllegalArgumentException("argument to remove() is null");
        int hashcode=hash(key);
        if (!bk[hashcode].contains(key)) throw new NoSuchElementException("No such key to remove");
        else if (bk[hashcode].get(key).equals(value)){
            bk[hashcode].remove(key);
            return value;
        }else return null;
    }
}
