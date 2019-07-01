/**
 * @author EvenHsia
 * @ClassName Palindrome.java
 * @Description A class for palindrome operation
 * @createTime 2019-06-30- 16:37
 */
public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> res=new LinkedListDeque<Character>();
        for (int i=0;i<word.length();i++){
            res.addLast(word.charAt(i));
        }
        return res;
    }
    public boolean isPalindrome(String word){
        Deque<Character> target=wordToDeque(word);
        return isPalindromeHelper(target);
    }
    private boolean isPalindromeHelper(Deque<Character> x){
        if (x.size()==0 || x.size()==1 ) return true;
        Character first=x.removeFirst();
        Character last=x.removeLast();
        if (first != last) return false;
        return isPalindromeHelper(x);
    }

    public boolean isPalindrome(String word,CharacterComparator cc){
        Deque<Character> target=wordToDeque(word);
        return isPalindromeHelper(target,cc);
    }

    private boolean isPalindromeHelper(Deque<Character> x,CharacterComparator ccc){
        if (x.size()==0 || x.size()==1 ) return true;
        Character first=x.removeFirst();
        Character last=x.removeLast();
        if (!ccc.equalChars(first,last)) return false;
        return isPalindromeHelper(x,ccc);
    }
}
