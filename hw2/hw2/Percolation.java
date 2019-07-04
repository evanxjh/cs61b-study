package hw2;
import java.lang.*;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 0-blocked
 * 1-open
 */
public class Percolation {
    int[][] grid;
    int n;          //grid size
    int Nopensites; //number of open sites
    int[][] dir ={{-1,0},{0,-1},{1,0},{0,1}};
    WeightedQuickUnionUF uf;
    WeightedQuickUnionUF ufnobottom;
    /**
     * create N-by-N grid,with all sites initially blocked
     */
    public Percolation(int N){
        if (N<=0) throw new IndexOutOfBoundsException("N must be bigger than 0!");
        grid=new int[N][N];
        for (int[]i :grid ){
            for (int j=0;j<i.length;j++){
                i[j]=0;
            }
        }
        n=N;
        uf=new WeightedQuickUnionUF(n*n+2);
        ufnobottom=new WeightedQuickUnionUF(n*n+1);
        Nopensites=0;
    }
    /**
     * validate the validity of (row,col)
     */
    private void validate(int row,int col){
        if (row<0 || row>n-1 || col<0 || col >n-1) throw new IndexOutOfBoundsException("check your row or col");
    }
    /**
     * transfer xy to 1D
     */
    private int xyTo1D(int r,int c){
        return r*n+c;
    }
    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row,int col){
        validate(row,col);
        if (row==0) {          //0 is the virtual top
            uf.union(xyTo1D(row,col),0);
            ufnobottom.union(xyTo1D(row,col),0);
        }
        if (row==n-1) {        //n*n+1 is the virtual bottom
            uf.union(xyTo1D(row,col),n*n+1);
        }
        for (int[] d :dir){
            int newrow=row+d[0];
            int newcol=col+d[1];
            if (newrow<0 || newrow>n-1 || newcol<0 || newcol>n-1 || !isOpen(newrow,newcol)){ continue;}
            uf.union(xyTo1D(row,col),xyTo1D(newrow,newcol));
            ufnobottom.union(xyTo1D(row,col),xyTo1D(newrow,newcol));

        }
        grid[row][col]=1;
        Nopensites+=1;

    }
    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row,int col){
        validate(row,col);
        return grid[row][col]==1;
    }
    /**
     * is the site (row,col) full?
     */
    public boolean isFull(int row,int col){
        validate(row,col);
        return ufnobottom.connected(xyTo1D(row,col),0);
    }
    /**
     *  number of open sites
     */
    public int numberOfOpenSites(){
        return Nopensites;
    }
    /**
     * does the system percolate?
     */
    public boolean percolates(){
        return uf.connected(0,n*n+1);
    }
    /**
     * use for unit testing(not required)
     */
    public static void main(String[] args){

    }
}
