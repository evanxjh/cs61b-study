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
        private boolean iskey;                              //����Ҫһ����Ա����������char��ΪTreeMap�Ѿ������������
        private TreeMap<Character,Node> children;

        public Node(){
            children=new TreeMap<Character, Node>();      //�����children���г�ʼ��
        }

    }

    public MyTrieSet(){
        root=new Node();                 //�����root���г�ʼ��
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
            next=current.children.get(c);    // get()��������contains()��ȷ�����ң�������һ���ڵ�
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
            current=current.children.get(c);          //ʻ����һ���ڵ�
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
     * ������x��Ϊ���ڵ����trie����key�������ֵ
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
     *  ѭ���ķ���
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
     * �ݹ�ķ���
     */
    @Override
    public String longestPrefixOf(String key){
        int length=search(root,key,0,0);
        return key.substring(0,length+1);
    }
    /**
     * the help method of longestPrefixOf's recursion version
     * xΪ��ǰ�ڵ�
     * keyΪҪ��ѯ���ַ���
     * dΪ����ѯ���ַ���key�е�λ��
     * lengthΪǰ׺�ĳ���
     */
    private int search(Node x,String key,int d,int length){
        if (d==key.length()) return length;               //��ֹԽ��
        char c=key.charAt(d);
        if  (!x.children.containsKey(c)) return length;    //��ʾ��ǰ�ַ�δ�ҵ������ص�ǰ�ĳ���
        if (x.children.get(c).iskey)  length=d;         //�����ǰ�ַ���iskeyΪ�棬����³���
        return search(x.children.get(c),key,d+1,length);
    }
}
