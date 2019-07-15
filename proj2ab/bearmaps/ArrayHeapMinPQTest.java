package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void testadd(){
        ArrayHeapMinPQ<Character> minheap=new ArrayHeapMinPQ<>();
        minheap.add('g',4);
        minheap.add('o',5);
        minheap.add('d',6);
        minheap.add('x',1);
        minheap.add('j',2);
        minheap.add('h',3);
        assertTrue(minheap.size()==6);
    }

    @Test
    public void testcontain(){
        ArrayHeapMinPQ<Character> minheap=new ArrayHeapMinPQ<>();
        minheap.add('g',4);
        minheap.add('o',5);
        minheap.add('d',6);
        minheap.add('x',1);
        minheap.add('j',2);
        minheap.add('h',3);
        assertTrue(minheap.contains('x'));
        assertTrue(minheap.contains('d'));
        assertFalse(minheap.contains('q'));
    }

    @Test
    public void testremovesmallest(){
        ArrayHeapMinPQ<Character> minheap=new ArrayHeapMinPQ<>();
        minheap.add('g',3);
        minheap.add('o',5);
        minheap.add('d',6);
        minheap.add('x',1);
        minheap.add('j',2);
        minheap.add('h',3);
        assertTrue(minheap.removeSmallest()=='x');
        minheap.removeSmallest();
        assertTrue(minheap.removeSmallest()=='g');
        assertTrue(minheap.removeSmallest()=='h');
    }

    @Test
    public void testgetsmallest(){
        ArrayHeapMinPQ<Character> minheap=new ArrayHeapMinPQ<>();
        minheap.add('q',1);
        minheap.add('r',2);
        minheap.add('c',3);
        minheap.add('x',1);
        minheap.add('j',5);
        minheap.add('h',6);
        assertTrue(minheap.getSmallest()=='q');
        assertFalse(minheap.getSmallest()=='x');
    }
    @Test
    public void testchangepriority(){
        ArrayHeapMinPQ<Character> minheap=new ArrayHeapMinPQ<>();
        minheap.add('q',3);
        minheap.add('r',2);
        minheap.add('c',3);
        minheap.add('x',1);
        minheap.add('j',5);
        minheap.add('h',6);
        minheap.changePriority('q',1);
        assertTrue(minheap.removeSmallest()=='x');
        assertTrue(minheap.getSmallest()=='q');
    }
    public static void main(String[] args){
        ArrayHeapMinPQ<Integer> minheap=new ArrayHeapMinPQ<>();
        long start=System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            minheap.add(i,1000000-i);
        }
        long end=System.currentTimeMillis();
        System.out.println("Total time elapsed:"+(end-start)/1000.0+"seconds.");
        long start1=System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            minheap.changePriority(i*300,i*300);
        }
        long end1=System.currentTimeMillis();
        System.out.println("Total time elapsed:"+(end1-start1)/1000.0+"seconds");

        /**
         * compare to NaiveMinPQ
         */
        NaiveMinPQ<Integer> naivepq=new NaiveMinPQ<>();
        long start2=System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            naivepq.add(i,1000000-i);
        }
        long end2=System.currentTimeMillis();
        System.out.println("Naive total time elapsed:"+(end2-start2)/1000.0+"seconds.");
        long start3=System.currentTimeMillis();
        for (int i=0;i<1000;i++){
           naivepq.changePriority(i*400,i*400);
        }
        long end3=System.currentTimeMillis();
        System.out.println("Naive total time elapsed:"+(end3-start3)/1000.0+"seconds");
    }
}
