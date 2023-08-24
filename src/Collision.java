import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;

public class Collision{
    public double x;
    public double y;
    public int frameNum = 0;
    public static double startingRadius = 0.05;
    public double[] collisionFrames = new double[]{1.05, 1.15, 1.35, 1.75};

    public Collision(double xCord, double yCord){
        this.x = xCord;
        this.y = yCord;
    }

    public static void drawCollisions(){
        HashSet<Collision> toRemove = new HashSet<Collision>();
        for(Collision collision : BallGame.collisionPoints){
            StdDraw.setPenColor(Color.white);
            StdDraw.circle(collision.x, collision.y, startingRadius*collision.collisionFrames[collision.frameNum]);
            if(collision.frameNum == collision.collisionFrames.length-1){
                toRemove.add(collision);
            }
            else{
                ++collision.frameNum;
            }
        }
        for(Collision collision : toRemove){
            BallGame.collisionPoints.remove(collision);
        }
    }
}