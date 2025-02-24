package javafiles;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

// Calculate centroid which, for convex polygons, is on the correct side of each line formed
// by the sides of the polygon.  Compare which side the centroid is on to which side the point
// being checked is on, and if any side differs, point is not inside the polygon.

public class Ball {
    public static double defaultRadius = 0.05;
    public static double radius = defaultRadius;
    public static double defaultSpeed = 0.032;
    public static double speed = defaultSpeed;
    public static double x;
    public static double y;
    public static double angle;
    public static int trailLength = 10;
    public static double[][] trail = new double[trailLength][3];
    public static double[] animationFrames = new double[]{1.9, 1.7, 1.6, 1.42, 1.35, 1.3, 1.26, 1.23, 1.2, 1.17, 1.14, 1.11, 1.08, 1.05, 1.02};
    public static int animationFrame = 0;
    public static int red = 0;
    public static int green = 255;
    public static int blue = 0;
    public static int alpha = 255;
    public static boolean isActive;
    
    public Ball(double xCord, double yCord, double ang){
        angle = ang;
        x = xCord;
        y = yCord;
        for(double[] curTrail : trail){
            curTrail[0] = xCord;
            curTrail[1] = yCord;
            curTrail[2] = defaultRadius;
        }
        isActive = true;
    }

    public static void updateBall(){
        double[] updatedPosition;
            if(animationFrame != -1){
                speed = defaultSpeed*animationFrames[animationFrame];
                radius = defaultRadius/animationFrames[animationFrame];
            }
            x = x + speed*Math.cos(angle);
            y = y + speed*Math.sin(angle);
            updatedPosition = BallGame.checkMovingObjectWallCollision(x, y, angle, radius, true);
            if(angle != updatedPosition[2]){
                x = updatedPosition[0];
                y = updatedPosition[1];
                angle = updatedPosition[2];
                BallGame.collisionPoints.add(new Collision(x, y));
                if(Barrier.aliveBarrier()){
                    ++BallGame.curScore;
                    if(!(Barrier.overlappingBarrier == null)){
                        Barrier.overlappingBarrier.touched = true;
                    }
                }
            }
            for(int i = trailLength-1; i > 0; --i){
                trail[i][0] = trail[i-1][0];
                trail[i][1] = trail[i-1][1];
                trail[i][2] = trail[i-1][2];
            }
            trail[0][0] = x;
            trail[0][1] = y;
            trail[0][2] = radius;
        
    }

    public static void drawBall(){
        alpha = 255;
        for(int i = 0; i < trailLength; ++i){
            StdDraw.setPenColor(new Color(0, 255, 0, alpha));
            StdDraw.filledCircle(trail[i][0], trail[i][1], trail[i][2]);
            alpha /= 2;
        }
        animationFrame = (animationFrame == animationFrames.length-1) || (animationFrame == -1) ? -1 : animationFrame + 1;
    }
}