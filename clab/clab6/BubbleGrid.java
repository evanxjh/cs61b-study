public class BubbleGrid {
    private int[][] grid;
    private int row;
    private int col;
    private int ceiling=0;
    private int[] transdarts;
    private int[][] dir={{-1,0},{0,-1},{1,0},{0,1}};     //上，左，下，右四个方向

    /**
     *Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space.
     */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        row=grid.length;                    //行数
        col=grid[0].length;                 //列数
    }

    /**
     *把grid中的二维坐标转换为一维的
     */
    private int transpos(int ro,int co ){
        return ro*col+co;
    }

    /**
     *与邻居建立连接
     * r代表行
     * c代表列
     */
    private void unionneighbors(int r,int c,UnionFind uff){
        //对于第一行，直接和天花板相连
        if (r==0){
            uff.union(transpos(r,c),ceiling);
        }
        //考察四个方向
        for (int[] d:dir){
            int newr=r+d[0];
            int newc=c+d[1];
            if (newr<0 || newr==row || newc<0 || newc==col || grid[newr][newc]!=1){
                continue;
            }
            uff.union(transpos(r,c),transpos(newr,newc));               //将这个孤儿放入bubble family中
        }
    }

    /**
     *  Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid.
     */
    public int[] popBubbles(int[][] darts) {
        int[] res=new int[darts.length];             //储存popBubbles的结果
        UnionFind uf=new UnionFind(row*col+1);
        for (int[] x:darts){                          //所有被darts击中的地方都设置为2，即原来有bubble，现在没有bubble.
            if (grid[x[0]][x[1]]==1) {
                grid[x[0]][x[1]] = 2;
            }
        }
        for (int i=0;i<row;i+=1){                     //对最后的情况先建立union
            for (int j=0;j<col;j+=1){
                if (grid[i][j]==1){
                    unionneighbors(i,j,uf);
                }
            }
        }
        int count=uf.sizeOf(ceiling);
        for (int i=darts.length-1;i>=0;i-=1 ){        //时光倒流
            int[] x=darts[i];
            if (grid[x[0]][x[1]]==2) {                //此处有击中过
                unionneighbors(x[0], x[1], uf);
                grid[x[0]][x[1]] = 1;
            }
            int newcount = uf.sizeOf(ceiling);
            res[i]=newcount>count?newcount-count-1:0;              //自身不算fall，所以要-1,没变化代表此次击空
            count=newcount;
        }
        return res;
    }
}
