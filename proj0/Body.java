public class Body {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;
    public Body(double xP,double yP,double xV,double yV,double m,String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }
    public Body(Body b){
     /** method one great! */
        this(b.xxPos,b.yyPos,b.xxVel,b.yyVel,b.mass,b.imgFileName);
     /** method two easy  */
//        xxPos=b.xxPos;
//        yyPos=b.yyPos;
//        xxVel=b.xxVel;
//        yyVel=b.yyVel;
//        mass=b.mass;
//        imgFileName=b.imgFileName;
    }

    public double calcDistance(Body x){
        return Math.sqrt(((this.xxPos-x.xxPos)*(this.xxPos-x.xxPos)+(this.yyPos-x.yyPos)*(this.yyPos-x.yyPos)));
    }

    public double calcForceExertedBy(Body x){
        double r=this.calcDistance(x);
        return 6.67e-11*this.mass*x.mass/(r*r);
    }

    public double calcForceExertedByX(Body x){
        double f=this.calcForceExertedBy(x);
        return (x.xxPos-this.xxPos)/this.calcDistance(x)*f;
    }

    public double calcForceExertedByY(Body y){
        double f=this.calcForceExertedBy(y);
        return (y.yyPos-this.yyPos)/this.calcDistance(y)*f;
    }

    public double calcNetForceExertedByX(Body[] allbody){
        double Netx=0;
        for (int i=0;i<allbody.length;i++){
            if (!(this.equals(allbody[i]))) {
                Netx +=this.calcForceExertedByX(allbody[i]);
            }
        }
        return Netx;
    }

    public double calcNetForceExertedByY(Body[] allbody){
        double Nety=0;
        for (int i=0;i<allbody.length;i++){
            if (!(this.equals(allbody[i]))) {
                Nety +=this.calcForceExertedByY(allbody[i]);
            }
        }
        return Nety;
    }

    public void update(double dt,double Netfx,double Netfy){
        double ax,ay;
        ax=Netfx/this.mass;
        ay=Netfy/this.mass;
        this.xxVel+=ax*dt;
        this.yyVel+=ay*dt;
        this.xxPos+=dt*xxVel;
        this.yyPos+=dt*yyVel;
    }
    public void draw(){
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(this.xxPos,this.yyPos,"images/"+this.imgFileName);
    }
}
