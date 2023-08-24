import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

// Calculate centroid which, for convex polygons, is on the correct side of each line formed
// by the sides of the polygon.  Compare which side the centroid is on to which side the point
// being checked is on, and if any side differs, point is not inside the polygon.

public class Gun {
    public static double radius = 0.1;
    public static double barrelLength = 0.35;
    public static double barrelWidth = 0.064;
    public static double sideLength = 0.15;
    public static double sideAngleWidth = .37;
    public static double x;
    public static double y;
    public static double barrelAngle = 0;
    public static int animationFrame = -1;
    public static double[] animationFrames = new double[]{1.2, 1.3, 1.5, 1.4, 1.3, 1.2, 1.1};
    public static boolean cooldown = false;
    public static boolean placed = false;
    public static int alpha = 255;
    public static int gunRed = 0;
    public static int gunGreen = 0;
    public static int gunBlue = 0;
    public static int barrelRed = 0;
    public static int barrelGreen = 0;
    public static int barrelBlue = 255;

    public static void updateBarrelAngle(){
        if(placed){
            double dy = StdDraw.mouseY() - y;
            double dx = StdDraw.mouseX() - x;
            barrelAngle = Math.atan2(dy, dx);
        }
        else{
            barrelAngle = Math.atan2(-y, -x);
        }
    }


    public static void draw(){
        double curBarrelLength = barrelLength;
        double curRadius = radius;
        double curSideLength = sideLength;
        double xdif = barrelWidth*Math.cos(Math.PI/2 - barrelAngle)/2;
        double ydif = barrelWidth*Math.sin(Math.PI/2 - barrelAngle)/2;
        if(animationFrame != -1){
            xdif *= animationFrames[animationFrame];
            ydif *= animationFrames[animationFrame];
            //curBarrelLength *= (2.0 - animationFrames[animationFrame]);
            curBarrelLength /= animationFrames[animationFrame];
            curRadius *= animationFrames[animationFrame];
            //curSideLength *= (2.0 - animationFrames[animationFrame]);
            curSideLength /= animationFrames[animationFrame];
        }
        StdDraw.setPenColor(new Color(barrelRed, barrelGreen, barrelBlue, alpha));
        StdDraw.filledPolygon(new double[]{x-xdif, x+xdif, x+curBarrelLength*Math.cos(barrelAngle)+xdif, x+curBarrelLength*Math.cos(barrelAngle)-xdif}, new double[]{y+ydif, y-ydif, y+curBarrelLength*Math.sin(barrelAngle)-ydif, y+curBarrelLength*Math.sin(barrelAngle)+ydif});
        StdDraw.setPenColor(new Color(gunRed, gunGreen, gunBlue, alpha));
        StdDraw.filledCircle(x, y, curRadius);
        double perpAngle = barrelAngle + Math.PI/2;
        double[] sideOneXCords = new double[]{x+Math.cos(perpAngle)*curRadius, x+Math.cos(barrelAngle+sideAngleWidth)*curRadius, x+Math.cos(barrelAngle+sideAngleWidth)*curRadius+Math.cos(barrelAngle)*curSideLength};
        double[] sideOneYCords = new double[]{y+Math.sin(perpAngle)*curRadius, y+Math.sin(barrelAngle+sideAngleWidth)*curRadius, y+Math.sin(barrelAngle+sideAngleWidth)*curRadius+Math.sin(barrelAngle)*curSideLength};
        double[] sideTwoXCords = new double[]{x-Math.cos(perpAngle)*curRadius, x+Math.cos(barrelAngle-sideAngleWidth)*curRadius, x+Math.cos(barrelAngle-sideAngleWidth)*curRadius+Math.cos(barrelAngle)*curSideLength};
        double[] sideTwoYCords = new double[]{y-Math.sin(perpAngle)*curRadius, y+Math.sin(barrelAngle-sideAngleWidth)*curRadius, y+Math.sin(barrelAngle-sideAngleWidth)*curRadius+Math.sin(barrelAngle)*curSideLength};
        //StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.filledPolygon(sideOneXCords, sideOneYCords);
        StdDraw.filledPolygon(sideTwoXCords, sideTwoYCords);
        animationFrame = (animationFrame == animationFrames.length-1) || (animationFrame == -1) ? -1 : animationFrame + 1;
        
    }

}