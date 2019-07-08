import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        Random r=new Random();
        BST<Integer> bst=new BST<Integer>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        int num=0;
        while (bst.size()<5000){
            int toadd=r.nextInt(6000);
            if (!bst.contains(toadd)) {
                bst.add(toadd);
                num+=1;
                double thisY = (double) bst.height / num;
                xValues.add(num);
                yValues.add(thisY);
                y2Values.add(ExperimentHelper.optimalAverageDepth(num));
            }
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("average depth of mine BST", xValues, yValues);
        chart.addSeries("average depth of an optimal BST", xValues, y2Values);
        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
    }

    public static void experiment3() {
    }

    public static void main(String[] args) {
        experiment1();
    }
}
