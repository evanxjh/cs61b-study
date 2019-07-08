import java.security.Key;
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
        Node left;             //������
        Node right;            //������
        private K key;         //��
        private  V val;        //ֵ
        private int size;      //�Ըýڵ�Ϊ���������Ľڵ�����
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
     * �ж��Ƿ��ǿյ�BST
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
        //ǰ�����
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
        if (x==null) return null;      //û�ҵ�����null
        int cmp=key.compareTo(x.key);
        if (cmp<0) return get(x.left,key);          //�ȵ�ǰС������������
        else if(cmp>0) return get(x.right,key);     //�ȵ�ǰ������������
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
        root=put(root,key,value);       //����key���ҵ�����£�û�ҵ�������µĽڵ�
    }
    private Node put(Node x,K key,V val){
        //���key���ҵ��������ֵ
        //������key��valΪ�½ڵ㣬�����������
        if (x==null) return new Node(key,val,1);   //�½��ڵ�
        int cmp=key.compareTo(x.key);
        if (cmp<0) x.left=put(x.left,key,val);
        else if(cmp>0) x.right=put(x.right,key,val);
        else x.val=val;
        x.size=size(x.left)+size(x.right)+1;
        return x;
    }
    /**
     * �ҳ�������С���Ľڵ�ͺ��������Ľڵ�min(),max()
     * ����ֵΪK����Node
     */

    public K min(){
        return min(root).key;
    }
    // help method
    private Node min(Node x){
        if (x.left==null) {
            return x;
        }else return x.left;
    }

    public K max(){
        return max(root).key;
    }
    private Node max(Node x){
        if (x.right==null){
            return x;
        }else return x.right;
    }
    /**
     * �ҳ��ȸ����ڵ�С�����ڵ�floor(key)���ͱȸ����ڵ�����С�ڵ�ceiling(key)��
     */
    public K floor(K key){
        Node x=floor(root,key);
        if (x==null) return null;
        return x.key;
    }
    private Node floor(Node x,K key){
        if (x==null) return null;
        int cmp=key.compareTo(x.key);
        if (cmp==0) return x;
        if (cmp<0) return floor(x.left,key);
        Node t=floor(x.right,key);
        if (t==null) return x;
        else         return t;
    }

    public K ceiling(K key){
        Node x=ceiling(root,key);
        if (x==null) return null;
        return x.key;
    }

    private Node ceiling(Node x,K key){
        if (x==null) return null;
        int cmp=key.compareTo(x.key);
        if  (cmp==0) return x;
        if  (cmp>0) return ceiling(x.right,key);
        Node t=ceiling(x.left,key);
        if  (t==null) return x;
        else          return t;
    }

    /**
     * select��������������Ϊk�ļ���������������k��С�����ļ���
     * ����ֵΪK������Node������Ҫhelp method
     */
    public K select(int k){
        return select(root,k).key;
    }
    private Node select(Node x,int k){
        if (x==null) return null;
        int t=size(x.left);
        if (t==k) return x;
        if (k<t)  return select(x.left,k);
        return select(x.right,k-t-1);
    }
    /**
     * rank������select���淽�������ظ�����������
     */
    public int rank(K key){
        return rank(key,root);
    }
    private int rank(K key,Node x){
        if (x==null) return 0;
        int cmp=key.compareTo(x.key);
        if (cmp<0) return rank(key,x.left);
        if (cmp==0) return size(x.left);
        return size(x.left)+1+rank(key,x.right);
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet(){
        Set<K> BSTset=new TreeSet<>();
        for (K k:this){                       //Iterable is awesome��
            BSTset.add(k);
        }
        return BSTset;
    }
    /**
     * remove������Сֵ��remove����������
     * ��С�ڵ��������Ϊ�գ����ڵ��������Ϊ��
     */
    public V removemin(){
        Node toremove=select(root,0);
        root=removemin(root);
        return toremove.val;
    }

    private Node removemin(Node x){
        if (x.left==null)  return x.right;
        x.left=removemin(x.left);
        x.size=size(x.left)+size(x.right)+1;
        return x;
    }

    public V removemax(){
        V toremove=get(select(size(root)-1));
        root=removemax(root);
        return toremove;
    }
    private Node removemax(Node x){
        if (x.right==null) return x.left;
        x.right=removemax(x.right);
        x.size=size(x.left)+size(x.right)+1;
        return x;
    }
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        if (!containsKey(key)) return null;
        V toremove = get(key);
        root=remove(root,key);
        return toremove;
    }

    private Node remove(Node x,K key){
        if (x==null) return null;
        int cmp=key.compareTo(x.key);
        if  (cmp<0) x.left=remove(x.left,key);
        else if (cmp>0) x.right=remove(x.right,key);
        else {
            Node temp=x;
            x=min(temp.right);
            x.right=removemin(temp.right);
            x.left=temp.left;
        }
        x.size=size(x.right)+size(x.left)+1;
        return x;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key ,V value){
        if (!containsKey(key)) return null;
        if (!get(key).equals(value) ) return null;
        return value;
    }
}
