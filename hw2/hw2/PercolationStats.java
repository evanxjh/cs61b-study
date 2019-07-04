package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    int times;
    Percolation per;
    double[] x;
    /**
     * perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N,int T,PercolationFactory pf){
        if (N<=0 || T<=0) throw new IllegalArgumentException();
        times=T;
        x=new double[T];
        for (int i=0;i<times;i+=1){
            int opennum=0;
            per=pf.make(N);
            while (!per.percolates()){
                int row= StdRandom.uniform(N);
                int col= StdRandom.uniform(N);
                if (!per.isOpen(row,col)){
                    per.open(row,col);
                    opennum+=1;
                }

            }
            x[i]= (double) opennum/(N*N);
        }
    }
    /**
     * sample mean of percolation threshold
     */
    public double mean(){
        double sum=0;
        for (int i=0;i<times;i+=1){
            sum+=x[i];
        }
        return sum/times;
    }
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev(){
        double sum=0;
        double u=mean();
        for (int i=0;i<times;i+=1){
            sum+=(x[i]-u)*(x[i]-u);
        }
        return Math.sqrt(sum/(times-1));
    }
    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLow(){
        return mean()-1.96*stddev()/Math.sqrt(times);
    }
    /**
     * high endpoint of 95%confidence interval
     */
    public double confidenceHigh(){
        return mean()+1.96*stddev()/Math.sqrt(times);
    }
    public static void main(String args[]){
        PercolationFactory pf=new PercolationFactory();
        PercolationStats p=new PercolationStats(200,30,pf);
        System.out.println(p.mean());
        System.out.println(p.stddev());
        System.out.println(p.confidenceLow());
        System.out.println(p.confidenceHigh());
    }
}
