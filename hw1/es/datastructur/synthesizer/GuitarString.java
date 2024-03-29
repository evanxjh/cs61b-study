package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer=new ArrayRingBuffer((int) Math.round(SR/frequency));
        while (!buffer.isFull()){
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        double[] temp=new double[buffer.capacity()];
        for (int i=0;i<buffer.capacity();i++){
            buffer.dequeue();
            double r=Math.random()-0.5;
            // different from each other
            while (buffer.haselement(r)){
                r=Math.random()-0.5;
            }
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double rm=buffer.dequeue();
        buffer.enqueue(DECAY*0.5*(rm+buffer.peek()));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}

