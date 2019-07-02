/**
 * @author EvenHsia
 * @ClassName GuitarHero.java
 * @Description just for listen
 * @createTime 2019-07-02- 16:41
 */
import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    public static void main(String[] args) {
        /* create guitar strings, from String keyboard  */
        String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] str=new GuitarString[37];
        for (int i=0;i<str.length;i++){
            str[i]=new GuitarString(CONCERT_A*Math.pow(2,(i-24.0)/12.0));
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                    int index = keyboard.indexOf(key);
                    if (index!=-1){
                    str[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample=0;
            for (int i=0;i<str.length;i++){
                sample+=str[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i=0;i<str.length;i++){
                str[i].tic();
            }
        }
    }
}