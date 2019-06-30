/**
 * @program: proj0
 * @description: run simulation
 * @author: EvenHsia
 * @create: 2019-06-27 12:45
 **/
public class NBody {
    public static double readRadius(String addr){
        In in= new In(addr);
        String firstline=in.readLine();
        return in.readDouble();
    }

    public static Body[] readBodies(String addr1){
        In in=new In(addr1);
        int num=in.readInt();
        double radius=in.readDouble();                    /*   为什么"String secondline=in.readLine(); "不能通过编译？   */
        Body[] b=new Body[num];
        for (int i=0;i<num;i++){
            double xxP=in.readDouble();
            double yyP=in.readDouble();
            double xxV=in.readDouble();
            double yyV=in.readDouble();
            double mass=in.readDouble();
            String img=in.readString();
            b[i]=new Body(xxP,yyP,xxV,yyV,mass,img);
        }
        return b;
    }


    public static void main(String[] args){
        double T=Double.parseDouble(args[0]);
        double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        Body[] bodies=NBody.readBodies(filename);
        double r=NBody.readRadius(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        double time=0;
        while (time<=T){
            double[] xForce=new double[bodies.length];
            double[] yForce=new double[bodies.length];
            for (int j=0;j<bodies.length;j++){
                xForce[j]=bodies[j].calcNetForceExertedByX(bodies);
                yForce[j]=bodies[j].calcNetForceExertedByY(bodies);
            }
            for (int k=0;k<bodies.length;k++){
                bodies[k].update(dt,xForce[k],yForce[k]);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");
            for (int i=0;i<bodies.length;i++){
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time+=dt;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
