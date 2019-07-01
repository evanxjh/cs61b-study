import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testisPalindrome(){
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("ycy"));
        assertTrue(palindrome.isPalindrome("shhxlxhhs"));
        assertFalse(palindrome.isPalindrome("xjhgod"));
    }

    @Test
    public void testoffbyOne(){
        OffByOne offbyone=new OffByOne();
        assertFalse(palindrome.isPalindrome("aB",offbyone));
        assertTrue(palindrome.isPalindrome("flake",offbyone));
        assertFalse(palindrome.isPalindrome("xjh",offbyone));
        assertTrue(palindrome.isPalindrome("&xyw%",offbyone));
    }

    @Test
    public void testoffbyN(){
        OffByN offbyN=new OffByN(5);
        assertTrue(palindrome.isPalindrome("acef",offbyN));
        assertTrue(palindrome.isPalindrome("%xyz)",offbyN));
        assertFalse(palindrome.isPalindrome("kaceBl",offbyN));
    }
}
//  Uncomment this class once you've created your Palindrome class.