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
        private Character nodechar;
        private boolean iskey;
        private TreeMap<Character,Node> children;

        public Node(char nodechar,boolean iskey){
            this.nodechar=nodechar;
            this.iskey=iskey;
        }
    }

    public MyTrieSet(){
        root=new Node('\0',false);
    }


    /** Clears all items out of Trie */
    @Override
    public void clear(){
        root=new Node('\0',false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key){
        if (key==null|| key.length()==0 || root.children==null){
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

    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    List<String> keysWithPrefix(String prefix){

    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    String longestPrefixOf(String key){

    }

}
