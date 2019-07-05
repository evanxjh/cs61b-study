import java.util.*;

/**
 * @author EvenHsia
 * @ClassName BSTMap.java
 * @Description a basic tree-based map
 * @createTime 2019-07-04- 12:47
 */
public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {

    private Node root;            //root node

    /**
     * Represents one node in the BST that stores the key-value pairs
     * in the dictionary.
     */
    private class Node{
        Node left;             //左子树
        Node right;            //右子树
        private K key;         //键
        private  V val;        //值
        private int size;      //以该节点为根的子树的节点总数
        public Node(K key,V value,int size){
            this.key=key;
            this.val=value;
            this.size=size;
        }
    }

    /**
     * Initialize an empty BSTMap
     */
    public BSTMap(){
    }

    /**
     * 判断是否是空的BST
     */
    public boolean isEmpty(){
        return size()==0;
    }

    @Override
    public Iterator<K> iterator(){
        return new BSTMapIter(root);
    }

    /** An iterator that iterates over the keys of the dictionary. */
    private class BSTMapIter implements Iterator<K>{
        private Stack<Node> stack=new Stack<>();
        //前序遍历
        BSTMapIter(Node scr){
            while (scr!=null){
                stack.push(scr);
                scr=scr.left;
            }
        }
        @Override
        public boolean hasNext(){
            return !stack.isEmpty();
        }
        @Override
        public K next(){
            Node cur=stack.pop();
            Node temp=cur.right;
            while (temp!=null) {
                stack.push(temp);
                temp=temp.left;
            }
            return cur.key;
        }
    }


    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key){
        if (key==null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key)!=null;
    }
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key){
        return get(root,key);
    }
    private V get(Node x,K key){
        if (x==null) return null;      //没找到返回null
        int cmp=key.compareTo(x.key);
        if (cmp<0) return get(x.left,key);          //比当前小，往左子树找
        else if(cmp>0) return get(x.right,key);     //比当前大，往右子树找
        else return x.val;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size(){
        return size(root);
    }
    private int size(Node x){
        if (x==null) return 0;
        else return x.size;
    }

    /**
     * remove all of the mapping from the map
     */
    @Override
    public void clear(){
        root=null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     */
    @Override
    public void put(K key,V value){
        root=put(root,key,value);       //查找key，找到则更新，没找到则添加新的节点
    }
    private Node put(Node x,K key,V val){
        //如果key能找到，则更新值
        //否则以key和val为新节点，插入该子树中
        if (x==null) return new Node(key,val,1);   //新建节点
        int cmp=key.compareTo(x.key);
        if (cmp<0) x.left=put(x.left,key,val);
        else if(cmp>0) x.right=put(x.right,key,val);
        else x.val=val;
        x.size=size(x.left)+size(x.right)+1;
        return x;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet(){
        Set<K> BSTset=new TreeSet<>();
        for (K k:this){                       //Iterable is awesome！
            BSTset.add(k);
        }
        return BSTset;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key ,V value){
        throw new UnsupportedOperationException();
    }
}
