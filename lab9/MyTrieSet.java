import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
/**
 * @author EvenHsia
 * @ClassName MyTrieSet.java
 * @Description Trie Set  the TreeMap version
 * @createTime 2019-07-09- 19:47
 */

public class MyTrieSet implements TrieSet61B{
    /**
     * the node of the TrieSet
     */
    Node root;     //root of trie
    private int n;                    //number of keys in trie
    private class Node{
        private boolean iskey;                              //不需要一个成员变量来代表char因为TreeMap已经包含这个功能
        private TreeMap<Character,Node> children;

        public Node(){
            children=new TreeMap<Character, Node>();      //必须给children进行初始化
        }

    }

    public MyTrieSet(){
        root=new Node();                 //必须给root进行初始化
    }


    /** Clears all items out of Trie */
    @Override
    public void clear(){
        root=new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key){
        if (key==null|| key.length()==0 ){
            return false;
        }
        Node current=root;
        Node next=null;
        for (int i=0;i<key.length();i++){
            char c=key.charAt(i);
            next=current.children.get(c);    // get()方法包括contains()，确认有且，返回下一个节点
            if (next==null){                 //
                return false;
            }
            current=next;
        }
        return current.iskey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key){
        if (key==null || key.length()==0 ) return;
        Node current=root;
        for (int i=0;i<key.length();i++){
            char c =key.charAt(i);
            if ( !current.children.containsKey(c)){
                current.children.put(c,new Node());
            }
            current=current.children.get(c);          //驶向下一个节点
        }
        current.iskey=true;
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix){
        Node startNode =get(root,prefix,0);
        List<String> result=new ArrayList();
        if (startNode.iskey) result.add(prefix);
        for (Character key:startNode.children.keySet()){
            Node curNode=startNode.children.get(key);
            keysWithPrefix(curNode,key,prefix,result);
        }
        return result;
    }

    private void keysWithPrefix(Node x,Character xkey, String word,List<String > res){
        if (x.iskey){
            res.add(word+xkey);
        }
        for (Character key :x.children.keySet()){
            Node nextNode=x.children.get(key);
            keysWithPrefix(nextNode,key, word + xkey, res);
        }
    }
    /**
     * 返回以x作为根节点的子trie中与key相关联的值
     */
    public Node get(Node x,String key,int d){
        if (key==null || x==null) return null;
        if (d==key.length()) return x;
        char c=key.charAt(d);
        return get(x.children.get(c),key,d+1);
    }
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    /*
     *  循环的方法
     */
/*    @Override
    public String longestPrefixOf(String key){
        StringBuilder longestPrefix=new StringBuilder();
        Node current=root;
        for (int i=0;i<key.length();i++){
            char c=key.charAt(i);
            if (!current.children.containsKey(c)){
                return longestPrefix.toString();
            }else{
                longestPrefix.append(c);
                current=current.children.get(c);
            }
        }
        return longestPrefix.toString();
    }*/

    /*
     * 递归的方法
     */
    @Override
    public String longestPrefixOf(String key){
        int length=search(root,key,0,0);
        return key.substring(0,length+1);
    }
    /**
     * the help method of longestPrefixOf's recursion version
     * x为当前节点
     * key为要查询的字符串
     * d为带查询的字符在key中的位置
     * length为前缀的长度
     */
    private int search(Node x,String key,int d,int length){
        if (d==key.length()) return length;               //防止越界
        char c=key.charAt(d);
        if  (!x.children.containsKey(c)) return length;    //表示当前字符未找到，返回当前的长度
        if (x.children.get(c).iskey)  length=d;         //如果当前字符的iskey为真，则更新长度
        return search(x.children.get(c),key,d+1,length);
    }
}
