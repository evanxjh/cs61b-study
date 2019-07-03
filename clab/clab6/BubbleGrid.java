public class BubbleGrid {
    private int[][] grid;
    private int row;
    private int col;
    private int ceiling=0;
    private int[] transdarts;
    private int[][] dir={{-1,0},{0,-1},{1,0},{0,1}};     //�ϣ����£����ĸ�����

    /**
     *Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space.
     */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        row=grid.length;                    //����
        col=grid[0].length;                 //����
    }

    /**
     *��grid�еĶ�ά����ת��Ϊһά��
     */
    private int transpos(int ro,int co ){
        return ro*col+co;
    }

    /**
     *���ھӽ�������
     * r������
     * c������
     */
    private void unionneighbors(int r,int c,UnionFind uff){
        //���ڵ�һ�У�ֱ�Ӻ��컨������
        if (r==0){
            uff.union(transpos(r,c),ceiling);
        }
        //�����ĸ�����
        for (int[] d:dir){
            int newr=r+d[0];
            int newc=c+d[1];
            if (newr<0 || newr==row || newc<0 || newc==col || grid[newr][newc]!=1){
                continue;
            }
            uff.union(transpos(r,c),transpos(newr,newc));               //������¶�����bubble family��
        }
    }

    /**
     *  Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid.
     */
    public int[] popBubbles(int[][] darts) {
        int[] res=new int[darts.length];             //����popBubbles�Ľ��
        UnionFind uf=new UnionFind(row*col+1);
        for (int[] x:darts){                          //���б�darts���еĵط�������Ϊ2����ԭ����bubble������û��bubble.
            if (grid[x[0]][x[1]]==1) {
                grid[x[0]][x[1]] = 2;
            }
        }
        for (int i=0;i<row;i+=1){                     //����������Ƚ���union
            for (int j=0;j<col;j+=1){
                if (grid[i][j]==1){
                    unionneighbors(i,j,uf);
                }
            }
        }
        int count=uf.sizeOf(ceiling);
        for (int i=darts.length-1;i>=0;i-=1 ){        //ʱ�⵹��
            int[] x=darts[i];
            if (grid[x[0]][x[1]]==2) {                //�˴��л��й�
                unionneighbors(x[0], x[1], uf);
                grid[x[0]][x[1]] = 1;
            }
            int newcount = uf.sizeOf(ceiling);
            res[i]=newcount>count?newcount-count-1:0;              //������fall������Ҫ-1,û�仯����˴λ���
            count=newcount;
        }
        return res;
    }
}
