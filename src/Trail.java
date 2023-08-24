import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class Trail {
    public static int trailLength = 100;
    public static double trailAngle;
    public static double trailRadius = 0.015;
    public static double trailInterval = 0.05;
    public static int red = 25;
    public static int green = 73;
    public static int blue = 79;
    public static int alpha = 255;


    public static void updateAndDrawTrail(){
        if(Gun.placed){
            trailAngle = Gun.barrelAngle;
            double curX = Gun.x;
            double curY = Gun.y;
            double[] updatedPosition = new double[2];
            alpha = 255;
            for(int i = 0; i < trailLength; ++i){
                curX += (Math.cos(trailAngle))*trailInterval;
                curY += (Math.sin(trailAngle))*trailInterval;
                updatedPosition = BallGame.checkMovingObjectWallCollision(curX, curY, trailAngle, trailRadius, false);
                if(trailAngle != updatedPosition[2]){
                    curX = updatedPosition[0];
                    curY = updatedPosition[1];
                    trailAngle = updatedPosition[2];
                }
                StdDraw.setPenColor(new Color(red, green, blue, alpha));
                StdDraw.filledCircle(curX, curY, trailRadius);
                alpha -= 255/trailLength;
            }
        }
        
    }

}