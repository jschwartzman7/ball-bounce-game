import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class LevelBarriers{

    public static LinkedList<Barrier> levelOneBarriers = new LinkedList<Barrier>();
    public static LinkedList<Barrier> levelTwoBarriers = new LinkedList<Barrier>();
    public static HashMap<Integer, LinkedList<Barrier>> levelBarriers = new HashMap<Integer, LinkedList<Barrier>>();

    public LevelBarriers(){
        double length = 0.74;
        double dist = 0.35;
        levelOneBarriers.add(new Barrier(new double[]{-length/2, 0, length/2}, new double[]{-length/(2*Math.sqrt(3)), length/Math.sqrt(3), -length/(2*Math.sqrt(3))}));
        double[][] wallOne = createWall(0, -length/(2*Math.sqrt(3))-dist, length, length/6, 0);
        levelOneBarriers.add(new Barrier(wallOne[0], wallOne[1]));
        double[][] wallTwo = createWall(-length/4+Math.cos(5*Math.PI/6)*dist, length/(4*Math.sqrt(3))+Math.sin(5*Math.PI/6)*dist, length, length/6, Math.PI/3);
        levelOneBarriers.add(new Barrier(wallTwo[0], wallTwo[1]));
        double[][] wallThree = createWall(length/4+Math.cos(Math.PI/6)*dist, length/(4*Math.sqrt(3))+Math.sin(Math.PI/6)*dist, length, length/6, -Math.PI/3);
        levelOneBarriers.add(new Barrier(wallThree[0], wallThree[1]));
        //double distance = Math.hypot((length/4+Math.cos(Math.PI/6)*length/2)-(length/4), (length/(4*Math.sqrt(3))+Math.sin(Math.PI/6)*length/2)-(length/(4*Math.sqrt(3))));
        
        levelBarriers.put(1, levelOneBarriers);

        double[][] wall = createWall(.3, -.3, .6, .06, 0);
        levelTwoBarriers.add(new Barrier(wall[0], wall[1]));
        wall = createWall(.3, .3, .6, .06, 0);
        levelTwoBarriers.add(new Barrier(wall[0], wall[1]));
        wall = createWall(-.3, 0, .6, .06, 0);
        levelTwoBarriers.add(new Barrier(wall[0], wall[1]));
        wall = createWall(-.3, .6, .6, .06, 0);
        levelTwoBarriers.add(new Barrier(wall[0], wall[1]));
        levelBarriers.put(2, levelTwoBarriers);
    }

    public static double[][] createWall(double centerX, double centerY, double length, double width, double angle){
        double perpAngle = angle + Math.PI/2;
        double[] xCords = new double[]{centerX-Math.cos(angle)*length/2+Math.cos(perpAngle)*width/2, centerX-Math.cos(angle)*length/2-Math.cos(perpAngle)*width/2, centerX+Math.cos(angle)*length/2-Math.cos(perpAngle)*width/2, centerX+Math.cos(angle)*length/2+Math.cos(perpAngle)*width/2};
        double[] yCords = new double[]{centerY-Math.sin(angle)*length/2+Math.sin(perpAngle)*width/2, centerY-Math.sin(angle)*length/2-Math.sin(perpAngle)*width/2, centerY+Math.sin(angle)*length/2-Math.sin(perpAngle)*width/2, centerY+Math.sin(angle)*length/2+Math.sin(perpAngle)*width/2};
        return new double[][]{xCords, yCords};
    }

    public static void main(String[] args) {
       
    }
}